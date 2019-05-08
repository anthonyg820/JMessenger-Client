import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SplashPage extends JPanel
{
	private JLabel logo;
	
	public SplashPage()
	{
		setPreferredSize(Constants.minimumAppDimension);
		setLayout(new BorderLayout());
		setBackground(new Color(20, 126, 251));
		
		setLogo();
		
		add(logo, BorderLayout.CENTER);
	}
	
	public void setLogo()
	{
		logo = new JLabel();
		ImageIcon img = new ImageIcon(getClass().getResource("/logo.png"));
		
		logo.setIcon(img);
		logo.setHorizontalAlignment(JLabel.CENTER);
	}
}
