import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;

public class PrimaryMessageBubble extends MessageBubble
{
	public PrimaryMessageBubble(String message)
	{
		super(message, Color.WHITE, new Color(68, 68, 68));
		
		add(getMessageTextArea(), BorderLayout.EAST);
	}
}
