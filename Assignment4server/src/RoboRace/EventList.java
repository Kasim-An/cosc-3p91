package RoboRace;

import COSC3P40.xml.*;
import java.util.*;
import java.io.*;

public class EventList extends LinkedList<GameEvent> implements XMLObject {
	
	public static EventList read(Reader input) {
            XMLReader<EventList> reader = new XMLReader<EventList>();
            reader.setXMLSchema("events.xsd");
            reader.setXMLNodeConverter(new EventListReader());
            return reader.readXML(input);
	}
	
	public boolean containsVictory() {
            boolean contained = false;
            for(GameEvent e : this) 
            	contained |= e instanceof VictoryEvent;
            return contained;
	}
	
	public String getWinner() {
		for(GameEvent e : this) 
			if (e instanceof VictoryEvent) 
				return ((VictoryEvent) e).getName();
		return null;
	}
		
	public void execute(Board board) {
		int counter = 1;
		int action = 1;
		for(int i=0; i<size(); i++) {
			GameEvent event = get(i);
			if (event.getStepValue() > counter) {
				counter++;
				board.waitOnRobots();
				if (event.getActionValue() > action) {
					action++;
					try {
                                            Thread.sleep(1000);
					} catch(Exception e) {};
				};
			};
			event.execute(board);
			if (event instanceof VictoryEvent)
				return;
		};
	}
	
	public String toXMLString() {
		String result = "<events>\n";
		for(GameEvent event : this)
			result += event.toXMLString() + "\n";
		return result + "</events>";
	}
	
}