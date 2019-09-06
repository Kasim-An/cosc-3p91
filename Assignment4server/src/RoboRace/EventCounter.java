package RoboRace;

public class EventCounter implements Cloneable {
	
	private int step;
	private int action;
	
	public EventCounter() {
		step = 1;
		action = 1;
	}
	
	public EventCounter(int step, int action) {
		if (step >= 1) 
			this.step = step;
		else this.step = 1;
		if (action >= 1) 
			this.action = action;
		else this.action = 1;
	}
	
	public EventCounter clone() {
		return new EventCounter(step,action);
	}
	
	public void increase() {
		step++;
		action++;
	}
	
	public void increaseStep() {
		step++;
	}
	
	public void increaseAction() {
		action++;
	}
	
	public int getStepValue() {
		return step;
	}
	
	public int getActionValue() {
		return action;
	}
	
	public void reset() {
		step = 1;
		action = 1;
	}
	
}