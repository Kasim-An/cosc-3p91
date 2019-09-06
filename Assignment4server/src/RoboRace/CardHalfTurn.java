package RoboRace;

import java.awt.*;
import COSC3P40.graphics.ImageManager;

public class CardHalfTurn extends Card {
	
	public CardHalfTurn(int priority) {
		super(priority);
		image = ImageManager.getInstance().loadImage("Cards/Halfturn.png");
		Graphics g = image.getGraphics();
		g.drawString("Priority: "+ getPriority(),15,15);
		g.dispose();
	}

	public void execute(EventCounter counter, EventList events, Robot robot, Board board) {
		events.add(new HalfturnEvent(counter,robot.getLocation()));
		robot.halfturn();
		counter.increase();
	}
	
	public String toXMLString() {
		return "<halfturn priority=\"" + getPriority() + "\"/>";
	}
	
}