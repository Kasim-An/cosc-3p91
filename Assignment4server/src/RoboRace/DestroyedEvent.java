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

public class DestroyedEvent extends GameEvent {
	
	public DestroyedEvent(EventCounter counter, int x, int y) {
		super(counter,x,y);
	}
	
	public DestroyedEvent(EventCounter counter, Point p) {
		super(counter,p.x,p.y);
	}
	
	public void execute(Board board) {
		getRobot(board).destroyed();
                Mysoundmanager mm=new Mysoundmanager();
            mm.playexplosion();
	}

	public String toXMLString() {
		return "<destroyed " + attrXMLString() + "/>";
	}
	
}