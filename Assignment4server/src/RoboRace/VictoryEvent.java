package RoboRace;

import COSC3P40.sound.SoundManager;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class VictoryEvent extends GameEvent {
	
	private String name;
	
	public VictoryEvent(EventCounter counter, int x, int y, String name) {
		super(counter,x,y);
		this.name = name;
	}	
	
	public VictoryEvent(EventCounter counter, Point p, String name) {
		this(counter,p.x,p.y,name);
	}
	
	public void execute(Board board) {
            Mysoundmanager mm=new Mysoundmanager();
            mm.playvictory();
	}
	
	public String getName() {
		return name;
	}
	
	public String toXMLString() {
		return "<victory " + attrXMLString() + " name=\"" + name + "\"/>";
	}

}