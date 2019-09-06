package RoboRace;

import COSC3P40.sound.SoundManager;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;

public class BumpEvent extends GameEvent {
	
	private Direction direction;
	
	public BumpEvent(EventCounter counter, int x, int y, Direction direction) {
		super(counter,x,y);
		this.direction = direction;
	}
	
	public BumpEvent(EventCounter counter, Point p, Direction direction) {
		this(counter,p.x,p.y,direction);
	}
	
	public void execute(Board board) {
            Mysoundmanager mm=new Mysoundmanager();
            mm.playbump();
	}
	
	public String toXMLString() {
		return "<bump " + attrXMLString() + " direction=\"" + direction + "\"/>";
	}
	
}