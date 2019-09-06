package COSC3P40.graphics;

import java.io.File;
import java.awt.Toolkit;
import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Window;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.awt.image.RGBImageFilter;
import java.awt.image.FilteredImageSource;
import javax.swing.SwingUtilities;
import javax.imageio.ImageIO;

import static java.lang.Math.random;
import static java.lang.Math.floor;

/** This class collects some usefull tools.
 **
 ** @author Michael Winter
 **
 ** @version 1.00 (December 2005)
*/
public class ImageManager {
	
	private static ImageManager instance = null;
	
	public static ImageManager getInstance() {
		if (instance == null) instance = new ImageManager();
		return instance;
	}
	
	private Toolkit toolkit; 		// a link to the DefaultToolkit		
	private String imagePath = "";	// the image directory used
	
	private ImageManager() {
		toolkit = Toolkit.getDefaultToolkit();
	}
	
	/** This method sets the ImagePath to the given string. It automatically
	 ** adds "/" if necessary.
	 **
	 ** @param path		the new ImagePath
	*/
	public void setImagePath(String path) {
		imagePath = path;
		if (!imagePath.endsWith("/")) imagePath += "/";
	} //setImagePath
	
	public String getImagePath() {
		return imagePath;
	}
	
	/** This method loads an image from the specified file. The ImagePath is
	 ** automatically added to the file name. 
	 **
	 ** @param file				the file name of the image
	 ** @return BufferedImage   the image if loading was successful, null otherwise
	*/
	public BufferedImage loadImage(String file) {
		try {
			return ImageIO.read(new File(imagePath + file));
		} catch (Exception e) {
			System.out.println(e);
		};
		return null;
	} // loadImage
	
	/** This method saves an image to the specified file. The ImagePath is
	 ** automatically added to the file name. 
	 **
	 ** @param image	the image to be saved
	 ** @param format	the format used, i.e. "jpeg" or "png"
	 ** @param file		the file name of the image
	*/
	public void saveImage(Image image, String format, String file) {
		int coding = (format.equalsIgnoreCase("png")) ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_BGR;
		BufferedImage bufferedImage = new BufferedImage(image.getWidth(null),image.getHeight(null),coding);
		bufferedImage.createGraphics().drawImage(image,0,0,null);
		try {
			ImageIO.write(bufferedImage,format,new File(imagePath + file));
		} catch(Exception e) {
			System.out.println(e);
		};
	} // saveImage
	
	/** This method produces a new image, which by filtering the input image
	 ** using a RGBImageFilter. The default toolkit method createImage is used.
	 **
	 ** @param image			the source image
	 ** @param filter			the RGBImageFilter
	 ** @result BufferedImage	the modified image
	*/
	public BufferedImage filterImage(Image image, RGBImageFilter filter) {
		BufferedImage result = new BufferedImage(image.getWidth(null),image.getHeight(null),BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = result.createGraphics();
		g.drawImage(toolkit.createImage(new FilteredImageSource(image.getSource(),filter)),0,0,null);
		g.dispose();
		return result;
	} // exchangeColor
	
	public BufferedImage imageToBufferedImage(Image image) {
		if (image instanceof BufferedImage) return (BufferedImage) image;
		BufferedImage bufImage = new BufferedImage(image.getWidth(null),image.getHeight(null),BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = bufImage.createGraphics();
		g.drawImage(image,0,0,image.getWidth(null),image.getHeight(null),null);
		g.dispose();
		return bufImage;
	}
	
	/** This method produces a snap shot of the given window. The snap shot is
	 ** actually taken by a robot. That method uses a rectangle corresponding
	 ** to the location and size of the window on the screen. The location
	 ** of the window is computed by the convertPointToScreen method of the
	 ** SwingUtility class.
	 **
	 ** @param window	the window that is "snapshoted"
	 ** @result Image	the snap shot
	*/
	public Image snapShot(Window window) {
		Point origin = new Point(0,0);
		SwingUtilities.convertPointToScreen(origin,window);
		try {
			return new Robot().createScreenCapture(new Rectangle(origin,window.getSize()));
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	} // snapShot

	
}