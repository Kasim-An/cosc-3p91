package RoboRace;

import javax.swing.ImageIcon;
import javax.swing.*;
import java.beans.*;
import java.awt.*;
import javax.swing.plaf.ColorUIResource;
import COSC3P40.graphics.ImageManager;

public class GameDialogs {
	
	private static ImageIcon logo = null;
	private static Color myred = new Color(230,65,40);
	
	public static String showInputDialog(String title, String text) {
		if (logo==null) {
			logo = new ImageIcon(ImageManager.getInstance().loadImage("logo.gif"));
		}
		JTextField textField = new JTextField(10);
		Object[] array = {text+"\n",textField};
		final JOptionPane pane = new JOptionPane(array,JOptionPane.QUESTION_MESSAGE,JOptionPane.DEFAULT_OPTION,logo,null);
		JFrame frame = new JFrame();
		final JDialog dialog = new JDialog(frame,title,true);
		dialog.setResizable(false);
		dialog.setContentPane(pane);
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		pane.addPropertyChangeListener(
    		new PropertyChangeListener() {
        		public void propertyChange(PropertyChangeEvent e) {
            		String prop = e.getPropertyName();
		            if (dialog.isVisible() 
        		     && (e.getSource() == pane)
             		 && (prop.equals(JOptionPane.VALUE_PROPERTY))) {
                		dialog.setVisible(false);
            		}
       			}
   			});
   		recolor(pane,myred);
   		dialog.pack();
		DisplayMode mode = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
		dialog.setLocation((mode.getWidth()-dialog.getWidth())/2,(mode.getHeight()-dialog.getHeight())/2);
		dialog.setVisible(true);
		frame.dispose();
		return textField.getText();
	}
	
	public static void showMessageDialog(String title, String text) {
		if (logo==null) logo = new ImageIcon("../Images/logo.gif");
		final JOptionPane pane = new JOptionPane(text,JOptionPane.INFORMATION_MESSAGE,JOptionPane.DEFAULT_OPTION,logo,null);
		JFrame frame = new JFrame();
		final JDialog dialog = new JDialog(frame,title,true);
		dialog.setResizable(false);
		dialog.setContentPane(pane);
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		pane.addPropertyChangeListener(
    		new PropertyChangeListener() {
        		public void propertyChange(PropertyChangeEvent e) {
            		String prop = e.getPropertyName();
		            if (dialog.isVisible() 
        		     && (e.getSource() == pane)
             		 && (prop.equals(JOptionPane.VALUE_PROPERTY))) {
                		dialog.setVisible(false);
            		}
       			}
   			});
   		recolor(pane,myred);
   		dialog.pack();
		DisplayMode mode = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
		dialog.setLocation((mode.getWidth()-dialog.getWidth())/2,(mode.getHeight()-dialog.getHeight())/2);
		dialog.setVisible(true);
		frame.dispose();
	}
	
	private static void recolor(Component component, Color color) {
		if (component instanceof JTextField || component instanceof JButton) return;
	    component.setBackground(color);
	    if (component instanceof Container) {
	        Container container = (Container) component;
	        for(int i=0, ub=container.getComponentCount(); i<ub; ++i)
	            recolor(container.getComponent(i), color);
	    }
	}
	
}