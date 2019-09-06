package RoboRace;

import java.awt.*;
import COSC3P40.graphics.ImageManager;

public class CardMove extends Card {

	private int steps;

	public CardMove(int priority, int steps) {
	    super(priority);
		this.steps = steps;
		image = ImageManager.getInstance().loadImage("Cards/Move.png");
		Graphics g = image.getGraphics();
		g.drawString("Priority: "+ getPriority(),15,15);
		g.drawString("Move: " +steps,25,120);
		g.dispose();
	}
	
	public void execute(EventCounter counter, EventList events, Robot robot, Board board) {
		for(int i=0; i<steps; i++) {
			if (robot.isAlive()) {
				board.step(counter,events,robot,robot.getDirection());
				counter.increaseStep();
			};
		};
		counter.increaseAction();
	}
	
	public String toXMLString() {
		return "<move priority=\"" + getPriority() + "\" steps=\"" + steps + "\"/>";
	}
	
}