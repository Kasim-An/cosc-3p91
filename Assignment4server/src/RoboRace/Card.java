package RoboRace;

import java.awt.*;
import COSC3P40.xml.*;

public abstract class Card implements Comparable<Card>, XMLObject {

	private int priority;
	protected Image image;
	
	public Card(int priority) {
		this.priority = priority;
	}

	public int getPriority() {
		return priority;
	}
	
	public int compareTo(Card c) {
		return c.priority - priority;
	}
	
	public abstract void execute(EventCounter counter, EventList events, Robot robot, Board board);
	
	public Image getImage() {
		return image;
	}
	
}