package RoboRace;

import java.awt.Point;

public class TurnEvent extends GameEvent {
	
	private boolean clockwise;
	
	public TurnEvent(EventCounter counter, int x, int y, boolean clockwise) {
		super(counter,x,y);
		this.clockwise = clockwise;
	}
	
	public TurnEvent(EventCounter counter, Point p, boolean clockwise) {
		this(counter,p.x,p.y,clockwise);
	}
	
	public void execute(Board board) {
		getRobot(board).turn(clockwise);
	}
	
	public String toXMLString() {
		return "<turn " + attrXMLString() + " clockwise=\"" + clockwise + "\"/>";
	}
	
	
}