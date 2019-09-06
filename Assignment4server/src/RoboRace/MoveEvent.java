package RoboRace;

import java.awt.Point;

public class MoveEvent extends GameEvent {
	
	private Direction direction;
	
	public MoveEvent(EventCounter counter, int x, int y, Direction direction) {
		super(counter,x,y);
		this.direction = direction;
	}
	
	public MoveEvent(EventCounter counter, Point p, Direction direction) {
		this(counter,p.x,p.y,direction);
	}
	
	public void execute(Board board) {
		getRobot(board).move(direction);
	}
		
	public String toXMLString() {
		return "<move " + attrXMLString() + " direction=\"" + direction + "\"/>";
	}
	
	
}