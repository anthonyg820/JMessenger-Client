import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class OptionsWindow extends JFrame
{
	private JButton nightMode;
	private JButton changeTextSize;
	private JButton changeFont;
	private final Dimension buttonDimension = new Dimension(300, 100);
	private final Font buttonFont = new Font("Tahoma", Font.PLAIN, 18);
	private final Color defaultButtonColor = Color.WHITE;
	private final Color hoverButtonColor = Constants.darkM1;
		
	public OptionsWindow()
	{
		setSize(300, 200);
		setLocationRelativeTo(null);
		setTitle("Options");
		getContentPane().setLayout(new GridLayout(0,1));
		
		setButtons();
		
		getContentPane().add(nightMode);
		getContentPane().add(changeTextSize);
		getContentPane().add(changeFont);
	}

	private void setButtons()
	{
		nightMode = new JButton("Toggle night mode");
		nightMode.setFont(buttonFont);
		nightMode.setCursor(new Cursor(Cursor.HAND_CURSOR));
		nightMode.setBackground(defaultButtonColor);
		nightMode.setFocusPainted(false);
		nightMode.addMouseListener(new buttonEffectsListener(nightMode));
		
		changeTextSize = new JButton("Change text size");
		changeTextSize.setFont(buttonFont);
		changeTextSize.setCursor(new Cursor(Cursor.HAND_CURSOR));
		changeTextSize.setBackground(defaultButtonColor);
		changeTextSize.setFocusPainted(false);
		changeTextSize.addMouseListener(new buttonEffectsListener(changeTextSize));
		
		changeFont = new JButton("Change font");	
		changeFont.setFont(buttonFont);
		changeFont.setCursor(new Cursor(Cursor.HAND_CURSOR));
		changeFont.setBackground(defaultButtonColor);
		changeFont.setFocusPainted(false);
		changeFont.addMouseListener(new buttonEffectsListener(changeFont));
	}
	
	public JButton getNightMode()
	{
		return nightMode;
	}
	
	public JButton getTextSizeButton()
	{
		return changeTextSize;
	}
	
	public JButton getChangeFontButton()
	{
		return changeFont;
	}
	
	
	//---LISTENERS---
	
	public class buttonEffectsListener extends MouseAdapter
	{
		JButton button;
		
		public buttonEffectsListener(JButton butt)
		{
			button = butt;
		}

		@Override
		public void mouseEntered(MouseEvent arg0)
		{
			button.setBackground(hoverButtonColor);
		}

		@Override
		public void mouseExited(MouseEvent arg0)
		{
			button.setBackground(defaultButtonColor);
		}		
	}
}
