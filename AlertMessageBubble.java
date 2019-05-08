import java.awt.BorderLayout;
import java.awt.Color;

public class AlertMessageBubble extends MessageBubble
{
	public AlertMessageBubble(String message, Color bgCol)
	{
		super(message, bgCol, Color.WHITE);
		
		add(getMessageTextArea(), BorderLayout.CENTER);
	}
}
