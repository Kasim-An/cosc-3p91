package RoboRace;

import java.awt.Point;

public class HalfturnEvent extends GameEvent {
	
	public HalfturnEvent(EventCounter counter, int x, int y) {
		super(counter,x,y);
	}	
	
	public HalfturnEvent(EventCounter counter, Point p) {
		super(counter,p.x,p.y);
	}
	
	public void execute(Board board) {
		getRobot(board).halfturn();
	}
	
	public String toXMLString() {
		return "<halfturn " + attrXMLString() + "/>";
	}

}