package RoboRace;

import java.awt.*;
import COSC3P40.xml.*;
import COSC3P40.graphics.*;
import static RoboRace.Direction.*;

public class Robot implements XMLObject {
	
	private String name;
	private Point location;
	private Direction direction;
	private boolean alive;
	private int start;
	private int currentAnimation;
	private float screenX;
	private float screenY;
	private float dx;
    private float dy;
    private boolean rotating;
    private boolean moving;
    private Animation[] animations;
	
	public Robot(String name, int x, int y, Direction direction, boolean alive, int start) {
	    this.name = name;
	    location = new Point(x,y);
		this.direction = direction;
		this.alive = alive;
		this.start = start;
		initialize();
		animations = new AnimationFactory().createRobotAnimations(name);
	}
	
	public Robot(String name, int start) {
	    this(name,start,0,North,true,start);
	}
	
	private void initialize() {
		screenX = 97*location.x+17;
		screenY = 97*location.y+17;
		setCurrentAnimation(direction,true);
		rotating = false;
		moving = false;
		alive = true;
	}
	
	public String getName() {
		return name;
	}
	
	public int getStart() {
		return start;
	}
	
	public void turn(boolean clockwise) {
		setCurrentAnimation(direction,clockwise);
		direction = direction.rotate(clockwise);
		rotating = true;
		animations[currentAnimation].start();
	}
	
	public void halfturn() {
		setCurrentAnimation(direction,null);
		direction = direction.halfturn();
		rotating = true;
		animations[currentAnimation].start();
	}
	
	private void setCurrentAnimation(Direction direction, Boolean clockwise) {
		if (clockwise==null) {
			switch(direction) {
				case North: currentAnimation = 8; 
							break;
				case East:	currentAnimation = 9; 
							break;
				case South: currentAnimation = 10;
							break;
				case West:  currentAnimation = 11;
				            break;
			};
		} else {
			switch(direction) {
				case North: if (clockwise) currentAnimation = 0; else currentAnimation = 4;
							break;
				case East:  if (clockwise) currentAnimation = 1; else currentAnimation = 7;
							break;
				case South: if (clockwise) currentAnimation = 2; else currentAnimation = 6;
							break;
				case West:  if (clockwise) currentAnimation = 3; else currentAnimation = 5;
							break;
			};
		};
	}
	
	public void destroyed() {
		alive = false;
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public void revitalize() {
		location.x = start;
		location.y = 0;
		direction = North;
		initialize();
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public Point getLocation() {
		return location;
	}
	
	public void setLocation(int x, int y) {
		location = new Point(x,y);
	}
	
	public String toXMLString() {
		return "<robot name=\"" + name + "\" x=\"" + location.x + "\" y=\"" + location.y + "\" direction=\"" + direction + "\" alive=\"" + alive + "\" start =\"" + start + "\"/>";
	}
	
	public void move(Direction direction) {
		switch (direction) {
			case North: location.y--;
						dy = (float) -0.2;
						break;
			case East:	location.x++;
						dx = (float) 0.2;
						break;
			case South:	location.y++;
						dy = (float) 0.2;
						break;
			case West:	location.x--;
						dx = (float) -0.2;
		};
		moving = true;
	}
	 
    public synchronized void update(long elapsedTime) {
    	if (rotating) {
    		animations[currentAnimation].update(elapsedTime);
    		if (!animations[currentAnimation].isRunning()) {
    			int n = currentAnimation;
    			setCurrentAnimation(direction,null);
    			animations[n].reset();
    			rotating = false;
    			notify();
    		}
    	};
    	if (moving) {
    		float newX = screenX + dx * elapsedTime;
        	float newY = screenY + dy * elapsedTime;
        	int stopX = 97*location.x+17;
        	int stopY = 97*location.y+17;
        	if ((screenX <= stopX && stopX <= newX) || (newX <= stopX && stopX <= screenX)) {
        		dx = 0;
        		newX = stopX;
       	 	};
        	if ((screenY <= stopY && stopY <= newY) || (newY <= stopY && stopY <= screenY)) {
        		dy = 0;
        		newY = stopY;
        	};
        	screenX = newX;
        	screenY = newY;
        	if (screenX==stopX && screenY==stopY) {
        		moving = false;
        		notify();
        	};
        };
    }
	
	public Image getImage() {
		return animations[currentAnimation].getImage();
	}
	
	public int getScreenX() {
		return (int) screenX;
	}
	
	public int getScreenY() {
		return (int) screenY;
	}

	public synchronized void waitOnRobot() {
    	while (moving || rotating) {
	    	try {
	    		wait();
	    	} catch (Exception ex) {};
	    };
    }
    
}