package RoboRace;

import COSC3P40.midi.MidiManager;
import COSC3P40.sound.SoundManager;
import java.util.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class GameMaster {

	private int numberPlayers;
	private Robot[] robots;
	private Port[] ports;
	private Board board;
	private CardFactory cardFactory;
	
	public GameMaster(int numberPlayers, String[] names, Port[] ports) {
		this.numberPlayers = numberPlayers;
		this.ports = ports;
		robots = new Robot[numberPlayers];
		for (int i=0; i<numberPlayers; i++)
			robots[i] = new Robot(names[i],2*i+1);
		Factory factory = Factory.load("factory.xml");
		board = new Board(factory,numberPlayers,robots);
		for (Port p : ports) p.send(board.toXMLString());
		cardFactory = new CardFactory();
	}
	
	public void run() throws InvalidMidiDataException, IOException, MidiUnavailableException {
            
            
		boolean done = false;
		CardList[] pCardList  = new CardList[numberPlayers];
		for(int i=0; i<numberPlayers; i++)
			pCardList[i] = new CardList();
		CardList phaseList = new CardList();
		EventCounter counter = new EventCounter();
		EventList events = new EventList();
		int phase;
		while (!done) {
			board.revitalize();
			for (int i=0; i<numberPlayers; i++) {
				pCardList[i].clear();
				for (int j=0; j<7; j++)
					pCardList[i].add(cardFactory.createCard());
				ports[i].send(pCardList[i].toXMLString());
			};
			for (int i=0; i<numberPlayers; i++) {
				pCardList[i] = CardList.read(new StringReader(ports[i].recieve()));
			};
			phase = 0;
			counter.reset();
			events.clear();
			while (phase < 5 && !done) {
				phase++;
				phaseList.clear();
				for (int i=0; i<numberPlayers; i++)
					phaseList.add(pCardList[i].get(phase-1));
				Collections.sort(phaseList);
				for (Card card : phaseList) {
					int player = -1;
					for (int i=0; i<numberPlayers; i++) 
						if (pCardList[i].get(phase-1) == card) 
							player = i;
					Robot robot = robots[player];
					if (robot.isAlive())
						card.execute(counter,events,robot,board);
				};
				for (Robot robot : robots) {
					if (robot.isAlive()) {
						Location location = board.getLocation(robot.getLocation());
						location.effect(counter,events,phase,robot,board);
					};
				};
				done |= events.containsVictory();
			};
			for(Port p : ports) p.send(events.toXMLString());
		};
		for(Port p : ports)	
			try {
				p.close();
			} catch (Exception e) {};
                        
	}
	
}
		