import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JSlider;

public class TextSizeWindow extends JFrame
{
	private JSlider slider;
	final int minTextSize = 12;
	final int maxTextSize = 48;
	final int initialTextSize = 24;
	
	public TextSizeWindow()
	{
		setSize(400, 200);
		setLocationRelativeTo(null);
		setTitle("Choose text size");
		setLayout(new BorderLayout());
		
		slider = new JSlider(JSlider.HORIZONTAL, minTextSize, maxTextSize, initialTextSize);
		slider.setMajorTickSpacing(6);
		slider.setMinorTickSpacing(2);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		
		add(slider);
	}
	
	public JSlider getSlider()
	{
		return slider;
	}
}
