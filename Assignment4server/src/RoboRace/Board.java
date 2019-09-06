package RoboRace;

import COSC3P40.sound.SoundManager;
import COSC3P40.xml.*;
import java.awt.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Board implements XMLObject {
	
	private Factory factory;
	private int numberRobots;
	private Robot[] robots;
	
	public static Board read(Reader input) {
		XMLReader<Board> reader = new XMLReader<Board>();
        reader.setXMLSchema("board.xsd");
        reader.setXMLNodeConverter(new BoardReader());
        return reader.readXML(input);
	}
	
	public Board(Factory factory, int numberRobots, Robot[] robots) {
		this.factory = factory;
		this.numberRobots = numberRobots;
		this.robots = robots;
	}
	
	public Location getLocation(int x, int y) {
		return factory.getLocation(x,y);
	}
	
	public Location getLocation(Point p) {
		return factory.getLocation(p);
	}
	
	public Robot robotAt(int x, int y) {
		for(Robot robot : robots)
			if (x == robot.getLocation().x && y == robot.getLocation().y) 
				return robot;
		return null;
	}
	
	public void step(EventCounter counter, EventList events, Robot robot, Direction direction) {
		Point location = robot.getLocation();
		if (factory.hasWall(location,direction)) {
			events.add(new BumpEvent(counter,location,direction));
		} else {
			int x = location.x;
			int y = location.y;
			switch (direction) {
				case North: y--;
							break;
				case East:	x++;
							break;
				case South:	y++;
							break;
				case West:	x--;
			};
			if (x < 0 || x >= factory.getXSize() || y < 0 || y >= factory.getYSize() || getLocation(x,y).isPit()) {
				events.add(new MoveEvent(counter,location,direction));
				robot.setLocation(x,y);
				events.add(new DestroyedEvent(counter,x,y));
				robot.destroyed();
			} else {
				Robot robot2 = robotAt(x,y);
				if (robot2 == null) {
					events.add(new MoveEvent(counter,location,direction)); 
					robot.setLocation(x,y);
				} else {
					step(counter,events,robot2,direction);
					if (x == robot2.getLocation().x && y == robot2.getLocation().y)
						events.add(new BumpEvent(counter,location,direction));
					else {
						events.add(new MoveEvent(counter,location,direction));
						robot.setLocation(x,y);
					};
				};
			};
		};
	}
	
	public void revitalize() {
		for(Robot robot : robots)
			if (!robot.isAlive() && robotAt(0,robot.getStart())==null) 
				robot.revitalize();
	}

	public String toXMLString() {
		String result = "<board>\n";
		result += "<robots number=\"" + numberRobots + "\">\n";
		for(Robot robot : robots) 
			result += robot.toXMLString() + "\n"; 
		result += "</robots>\n";
		result += factory.toXMLString() + "\n";
		return result + "</board>";
	}
	
	public Dimension getDisplaySize() {
		return factory.getDisplaySize();
	}
	
	public void start() {
		factory.start();                
	}
	
	public void update(long delta) {
		factory.update(delta);
		for(Robot robot : robots)
			robot.update(delta);
	}
	
	public void draw(Graphics graphics) {
		factory.draw(graphics);
		for(Robot robot : robots)
			graphics.drawImage(robot.getImage(),robot.getScreenX(),robot.getScreenY(),null);
	}
	
	public void waitOnRobots() {
		for(Robot robot : robots) 
			robot.waitOnRobot();
		
	}
	
}