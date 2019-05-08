import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class ChatPage extends JPanel
{
	private JTextField outgoing; //Where the user types their message
	private JPanel incoming; //Where all thread messages appear
	private JPanel containerPanel; //The inner 'holder' panel used to make all message bubbles the same size when using BoxLayout
	private JScrollPane scroller;
	private JPanel optionsToolbar;
	private JPanel bottomArea;
	
	private JButton sendButton;
	private JButton optionButton;
	private JButton videoButton;
	
	private final OptionsWindow ow = new OptionsWindow();
	private final TextSizeWindow tsw = new TextSizeWindow();
	
	private final Font outgoingFont = new Font("Tahoma", Font.PLAIN, 24);
	
	public ChatPage()
	{
		setGUI();
		ow.getTextSizeButton().addActionListener(new textSizeButtonListener());
	}
	
	private void setGUI()
	{
		setPreferredSize(Constants.minimumAppDimension);
		setLayout(new BorderLayout());
		
		setIncoming();
		setOutgoing();
		setOptionsToolbar();
		setBottomArea();
		
		add(scroller, BorderLayout.NORTH);
		add(bottomArea, BorderLayout.SOUTH);
	}
	
	private void setIncoming()
	{
		incoming = new JPanel();
		EmptyBorder incomingBorder = new EmptyBorder(10,10,10,10);
		incoming.setBorder(incomingBorder);		
		incoming.setLayout(new BorderLayout()); 
		containerPanel = new JPanel();
		BoxLayout bubbleLayout = new BoxLayout(containerPanel, BoxLayout.Y_AXIS);	
		containerPanel.setLayout(bubbleLayout);
		incoming.add(containerPanel, BorderLayout.NORTH);
		scroller = new JScrollPane(incoming);
		scroller.setPreferredSize(new Dimension(Constants.minimumAppWidth, Constants.minimumAppHeight - (Constants.minimumAppHeight / 10) - 110));
		scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.getVerticalScrollBar().setUnitIncrement(16);
	}
	
	private void setOutgoing()
	{
		outgoing = new JTextField();
		outgoing.setPreferredSize(new Dimension(Constants.minimumAppWidth - 100 - 20, Constants.minimumAppHeight / 10));
		CompoundBorder outgoingBorder = new CompoundBorder(
			    BorderFactory.createLineBorder(new Color(123,123,123), 4, true), 
			    BorderFactory.createEmptyBorder(0,30,0,30));
		outgoing.setBorder(outgoingBorder);
		outgoing.setFont(outgoingFont);
		
		
		sendButton = new JButton("SEND");
		sendButton.setBackground(new Color(20, 126, 251));
		sendButton.setForeground(Color.WHITE);
		sendButton.setPreferredSize(new Dimension(100, Constants.minimumAppHeight / 10));
		sendButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		sendButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
	}
	
	private void setOptionsToolbar()
	{
		optionsToolbar = new JPanel();
		optionsToolbar.setLayout(new BorderLayout());
		optionsToolbar.setBackground(Color.WHITE);
		
			optionButton = new JButton();
			optionButton.setPreferredSize(new Dimension(50, 50));
			optionButton.setBackground(Color.WHITE);
			ImageIcon optionsIconDefault = new ImageIcon(getClass().getResource("/options.png"));
			ImageIcon optionsIconHover = new ImageIcon(getClass().getResource("/optionsHover.png"));
			optionButton.setIcon(optionsIconDefault);
			optionButton.setFocusPainted(false);
			optionButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			optionButton.setBorder(new EmptyBorder(0,0,0,0));
			optionButton.addMouseListener(new toolbarButtonEffectsListener(optionButton, optionsIconHover, optionsIconDefault));
			optionButton.addActionListener(new optionsButtonListener());
			
			videoButton = new JButton();
			videoButton.setPreferredSize(new Dimension(50, 50));
			videoButton.setBackground(Color.WHITE);
			ImageIcon videoIconDefault = new ImageIcon(getClass().getResource("/video.png"));
			ImageIcon videoIconHover = new ImageIcon(getClass().getResource("/videoHover.png"));
			videoButton.setIcon(videoIconDefault);
			videoButton.setFocusPainted(false);
			videoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			videoButton.setBorder(new EmptyBorder(0,0,0,0));
			videoButton.addMouseListener(new toolbarButtonEffectsListener(videoButton, videoIconHover, videoIconDefault));
			
		optionsToolbar.add(optionButton, BorderLayout.EAST);
		optionsToolbar.add(videoButton, BorderLayout.WEST);
	}
	
	private void setBottomArea()
	{
		bottomArea = new JPanel();
		bottomArea.setLayout(new BorderLayout());
		bottomArea.setBackground(Color.WHITE);
		bottomArea.setPreferredSize(new Dimension(Constants.minimumAppWidth, (Constants.minimumAppHeight / 10) + 50));
		
		bottomArea.add(outgoing, BorderLayout.WEST);
		bottomArea.add(sendButton, BorderLayout.EAST);
		bottomArea.add(optionsToolbar, BorderLayout.SOUTH);
	}
	
	public JTextField getOutgoing()
	{
		return outgoing;
	}
	
	public JPanel getIncoming()
	{
		return incoming;
	}
	
	public JScrollPane getScroller()
	{
		return scroller;
	}
	
	public JPanel getContainerPanel()
	{
		return containerPanel;
	}
	
	public JButton getSendButton()
	{
		return sendButton;
	}
	
	public JButton getOptionsButton()
	{
		return optionButton;
	}
	
	public JButton getVideoButton()
	{
		return videoButton;
	}
	
	public JPanel getOptionsToolbar()
	{
		return optionsToolbar;
	}
	
	public JPanel getBottomArea()
	{
		return bottomArea;
	}
	
	public OptionsWindow getOptionsWindow()
	{
		return ow;
	}
	
	public TextSizeWindow getTextSizeWindow()
	{
		return tsw;
	}
	
	
	//---LISTENERS---
	
	public class toolbarButtonEffectsListener extends MouseAdapter
	{
		JButton button;
		ImageIcon iconEnter;
		ImageIcon iconExit;
		
		public toolbarButtonEffectsListener(JButton butt, ImageIcon icEnter, ImageIcon icExit)
		{
			button = butt;
			iconEnter = icEnter;
			iconExit = icExit;
		}

		@Override
		public void mouseEntered(MouseEvent arg0)
		{
			button.setIcon(iconEnter);
		}

		@Override
		public void mouseExited(MouseEvent arg0)
		{
			button.setIcon(iconExit);
		}
	}
	
	public class optionsButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			ow.setVisible(true);
		}
	}
	
	public class textSizeButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			ow.dispatchEvent(new WindowEvent(ow, WindowEvent.WINDOW_CLOSING));
			tsw.setVisible(true);
		}
	}
}
