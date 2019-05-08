import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class MessageBubble extends JPanel
{
	private JLabel nameLabel;
	private JTextArea messageTextArea;

	private Color bgColor;
	private Color fgColor;
	
	//The below statics are used to dynamically adjust bubble appearance based on user options (night mode, font, text size)
	static Color panelBG = Constants.lightM1;
	static int textSize = 24;
	static Font bubbleFont = new Font("Arial", Font.PLAIN, textSize);
	
	public MessageBubble(String message, Color bgCol, Color fgCol)
	{
		
		setLayout(new BorderLayout());
		setBackground(panelBG);
		
		bgColor = bgCol;
		fgColor = fgCol;
		
			nameLabel = new JLabel();
			nameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
			nameLabel.setBorder(new EmptyBorder(0,15,0,0));
		
			messageTextArea = new JTextArea(message);
			messageTextArea.setBackground(bgColor);
			messageTextArea.setForeground(fgColor);
			messageTextArea.setOpaque(true);
			messageTextArea.setFont(new Font("Tahoma", Font.PLAIN, textSize));
			messageTextArea.setEditable(false);
			
			CompoundBorder cb = new CompoundBorder(
				    BorderFactory.createMatteBorder(4, 10, 10, 10, panelBG), 
				    BorderFactory.createMatteBorder(15, 15, 15, 15, bgColor));
			
			messageTextArea.setBorder(cb);
	}
	
	public JLabel getNameLabel()
	{
		return nameLabel;
	}
	
	public JTextArea getMessageTextArea()
	{
		return messageTextArea;
	}
	
	public Color getBGColor()
	{
		return bgColor;
	}
	
	public Color getFGColor()
	{
		return fgColor;
	}
}
