package RoboRace;

import COSC3P40.xml.*;

public abstract class GameEvent implements XMLObject {
	
	private EventCounter counter;
	private int x;
	private int y;
	
	public GameEvent(EventCounter counter, int x, int y) {
		this.counter = counter.clone();
		this.x = x;
		this.y = y;
	}
	
	public int getStepValue() {
		return counter.getStepValue();
	}
	
	public int getActionValue() {
		return counter.getActionValue();
	}
	
	public abstract void execute(Board board);
	
	protected String attrXMLString() {
		return "step=\"" + counter.getStepValue() + "\" action=\"" + counter.getActionValue() +"\" x=\"" + x + "\" y=\"" + y + "\"";
	}
	
	protected Robot getRobot(Board board) {
		return board.robotAt(x,y);
	}
	
}