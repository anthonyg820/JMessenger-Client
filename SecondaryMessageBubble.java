import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;

public class SecondaryMessageBubble extends MessageBubble
{
	public SecondaryMessageBubble(String userName, String message)
	{
		super(message, new Color(20, 126, 251), Color.WHITE);
		
		getNameLabel().setHorizontalTextPosition(JLabel.LEFT);
		getNameLabel().setText(userName);
		add(getNameLabel(), BorderLayout.NORTH);
		add(getMessageTextArea(), BorderLayout.WEST);
	}
}
