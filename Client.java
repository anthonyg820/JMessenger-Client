import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.border.CompoundBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Client
{	
	static boolean isNightMode = false;
	
	static ArrayList<MessageBubble> allMessages = new ArrayList(); //Singleton where all messages are added. Used for toggling night mode and altering text
	
	private PrintWriter writer;
	private BufferedReader reader;
	private Socket sock;
	
	private JFrame frame;
	
	private SplashPage sp;
	private ChatPage cp;
	
	private String name;
	private String currentIP;
	
	public static void main(String[] args)
	{
		Client client = new Client();
	}
	
	public Client()
	{
		setNetwork();
		setGUI();
	}
	
	public void setGUI()
	{
		frame =  new JFrame("JMessenger");
		frame.setSize(Constants.minimumAppWidth, Constants.minimumAppHeight);
		frame.setMinimumSize(Constants.minimumAppDimension);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());
		ImageIcon img = new ImageIcon(getClass().getResource("/logoNoText.png"));
		frame.setIconImage(img.getImage());
		frame.addWindowListener(new closeClientListener());
		
		sp = new SplashPage();
		cp = new ChatPage();
		cp.getSendButton().addActionListener(new sendButtonListener());
		cp.getOutgoing().addActionListener(new sendButtonListener());
		cp.getOptionsWindow().getNightMode().addActionListener(new nightModeListener());
		cp.getTextSizeWindow().getSlider().addChangeListener(new textSizeListener());
		
		frame.getContentPane().add(sp, BorderLayout.CENTER);
		frame.setVisible(true);
		
		Timer splashAnim = new Timer(3000, new TimerListener());
		splashAnim.setRepeats(false);		
		splashAnim.start();
	}
	
	public void setNetwork()
	{
		try
		{
			//Get this device's IP
			final DatagramSocket socket = new DatagramSocket();
			socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
			currentIP = socket.getLocalAddress().getHostAddress();
			
			//Set writer
			sock = new Socket(currentIP, 5000);
			
			writer = new PrintWriter(sock.getOutputStream());
			System.out.println("New client just connect to server.");
			
			//Set reader
			InputStreamReader isr = new InputStreamReader(sock.getInputStream());
			reader = new BufferedReader(isr);
			
			//Start separate reader thread
			Thread readerThread = new Thread(new ReaderHandler());
			readerThread.start();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public class ReaderHandler implements Runnable
	{
		public void run()
		{
			String textData;
						
			try
			{
				while((textData = reader.readLine()) != null)
				{
					if(textData.contains("usergoneenogresu#")) //'usergoneenogresu#' is a code that signifies that the clients have been alerted that a user has left the chat
					{
						String[] parts = textData.split("#");
						String userWhoLeft = parts[1];
						
						AlertMessageBubble amb = new AlertMessageBubble(userWhoLeft + " has left the chat", new Color(255, 51, 51));
						cp.getContainerPanel().add(amb);
						allMessages.add(amb);
					}
					else if(textData.contains("userjoinniojresu#")) //'usergoneenogresu#' is a code that signifies that the clients have been alerted that a user has joined the chat
					{
						String[] parts = textData.split("#");
						String userWhoJoined = parts[1];
						
						if(!name.equals(userWhoJoined))
						{
							AlertMessageBubble amb = new AlertMessageBubble(userWhoJoined + " has joined the chat", new Color(0, 204, 102));
							cp.getContainerPanel().add(amb);
							allMessages.add(amb);
						}
					}
					else
					{						
						String[] parts = textData.split("-");
						String nameFromServer = parts[0];
						String message = parts[1];
						
						System.out.println("User " + nameFromServer + " just said: " + message);
						
						if(name.equals(nameFromServer)) //If this is a message the current user has sent
						{
							PrimaryMessageBubble pmb = new PrimaryMessageBubble(message); //Create a white bubble on the right side
							cp.getContainerPanel().add(pmb);
							
							allMessages.add(pmb);
						}
						else //If this is any user other than the current
						{
							SecondaryMessageBubble smb = new SecondaryMessageBubble(nameFromServer, message); //Create a blue bubble on the left side with the sender's name
							cp.getContainerPanel().add(smb);
							
							allMessages.add(smb);
						}
					}
					
					
					cp.getIncoming().repaint();
					cp.getIncoming().revalidate();
					int scrollMax = cp.getScroller().getVerticalScrollBar().getMaximum();
					cp.getScroller().getVerticalScrollBar().setValue(scrollMax);
				}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}
	
	
	//---LISTENERS---
	
	public class closeClientListener extends WindowAdapter
	{
		@Override
		public void windowClosing(WindowEvent e) 
		{
			try
			{
				writer.println("closeesolc#" + name); //'closeesolc' is a special code that tells the server to remove this name from activeUsers arraylist
				writer.flush();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				System.exit(0);
			}
	    }
	}
	
	//Inner class for the send button listener
	public class sendButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			writer.println(name + "-" + cp.getOutgoing().getText());
			writer.flush();
			
			cp.getOutgoing().setText("");
			cp.getOutgoing().requestFocus();
			
			int scrollMax = cp.getScroller().getVerticalScrollBar().getMaximum();
			cp.getScroller().getVerticalScrollBar().setValue(scrollMax);
		}
	}
	
	public class nightModeListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if(isNightMode)
			{
				isNightMode = false;
				cp.getOptionsToolbar().setBackground(Color.WHITE);
				cp.getOptionsButton().setBackground(Color.WHITE);
				cp.getVideoButton().setBackground(Color.WHITE);
				cp.getOutgoing().setBackground(Color.WHITE);
				cp.setBackground(Constants.lightM1);
				cp.getIncoming().setBackground(Constants.lightM1);
				MessageBubble.panelBG = Constants.lightM1;
				
				for(int i = 0; i < allMessages.size(); i++)
				{
					allMessages.get(i).setBackground(Constants.lightM1);
					
					allMessages.get(i).getMessageTextArea().setBorder(new CompoundBorder(
					    BorderFactory.createMatteBorder(4, 10, 10, 10, Constants.lightM1), 
					    BorderFactory.createMatteBorder(15, 15, 15, 15, allMessages.get(i).getBGColor())));
					
					allMessages.get(i).getNameLabel().setForeground(Color.BLACK);
				}
			}
			else
			{
				isNightMode = true;
				cp.getOptionsToolbar().setBackground(Constants.darkM1);
				cp.getOptionsButton().setBackground(Constants.darkM1);
				cp.getVideoButton().setBackground(Constants.darkM1);
				cp.getOutgoing().setBackground(Constants.darkM1);
				cp.setBackground(Constants.darkM2);
				cp.getIncoming().setBackground(Constants.darkM2);
				MessageBubble.panelBG = Constants.darkM2;
				
				for(int i = 0; i < allMessages.size(); i++)
				{
					allMessages.get(i).setBackground(Constants.darkM2);
					
					allMessages.get(i).getMessageTextArea().setBorder(new CompoundBorder(
					    BorderFactory.createMatteBorder(4, 10, 10, 10, Constants.darkM2), 
					    BorderFactory.createMatteBorder(15, 15, 15, 15, allMessages.get(i).getBGColor())));
					
					allMessages.get(i).getNameLabel().setForeground(Color.WHITE);
				}
			}
		}
	}
	
	public class textSizeListener implements ChangeListener
	{

		@Override
		public void stateChanged(ChangeEvent event)
		{
			JSlider slider = (JSlider)event.getSource();
			MessageBubble.textSize = slider.getValue();
			MessageBubble.bubbleFont = new Font(MessageBubble.bubbleFont.getFontName(), Font.PLAIN, MessageBubble.textSize);
			
			for(int i = 0; i < allMessages.size(); i++)
			{
				allMessages.get(i).getMessageTextArea().setFont(MessageBubble.bubbleFont);
			}
		}
		
	}
	
	//Inner class for splash timer
	class TimerListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			frame.getContentPane().remove(sp);
			frame.getContentPane().add(cp, BorderLayout.CENTER);
			frame.repaint();
			frame.revalidate();
			name = JOptionPane.showInputDialog("Enter your name");
			System.out.println(name);
			writer.println("inittini#" + name); //'inittini' is a special code that tells the server to add this text as a name to activeUsers arraylist
			writer.flush();
			System.out.println("FLUSHED");
			cp.getOutgoing().requestFocus();
		}
	}
}
