package RoboRace;

import java.awt.*;
import COSC3P40.graphics.ImageManager;

public class CardBack extends Card {

	public CardBack(int priority) {
		super(priority);
		image = ImageManager.getInstance().loadImage("Cards/Back.png");
		Graphics g = image.getGraphics();
		g.drawString("Priority: "+ getPriority(),15,15);
		g.dispose();
	}
	
	public void execute(EventCounter counter, EventList events, Robot robot, Board board) {
		board.step(counter,events,robot,robot.getDirection().halfturn());
		counter.increase();
	}
	
	public String toXMLString() {
		return "<back priority=\"" + getPriority() + "\"/>";
	}
	
}