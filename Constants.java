import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

public class Constants
{
	final static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	final static int screenWidth = screenSize.width;
	final static int screenHeight = screenSize.height;
	
	final static int minimumAppHeight = (screenHeight * 5) / 6;
	final static int minimumAppWidth = (minimumAppHeight * 3) / 5;
	final static Dimension minimumAppDimension = new Dimension(Constants.minimumAppWidth, Constants.minimumAppHeight);
	
	final static Color lightM1 = new Color(238,238,238);
	final static Color darkM1 = new Color(204,204,204);
	final static Color darkM2 = new Color(102,102,102);
}
