package RoboRace;

import COSC3P40.xml.*;
import org.w3c.dom.*;

import static COSC3P40.xml.XMLTools.*;

public class EventReader implements XMLNodeConverter<GameEvent> {
	
	public GameEvent convertXMLNode(Node node) {
		GameEvent event = null;
		String name = node.getNodeName();
		EventCounter counter = new EventCounter(getIntAttribute(node,"step"),getIntAttribute(node,"action")); 
		int x = getIntAttribute(node,"x");
		int y = getIntAttribute(node,"y");
		if (name.equals("bump"))
			event = new BumpEvent(counter,x,y,(Direction) getEnumAttribute(Direction.class,node,"direction"));
		if (name.equals("halfturn")) 
			event = new HalfturnEvent(counter,x,y);	
		if (name.equals("move")) 
			event = new MoveEvent(counter,x,y,(Direction) getEnumAttribute(Direction.class,node,"direction"));
		if (name.equals("turn")) 
			event = new TurnEvent(counter,x,y,getBoolAttribute(node,"clockwise"));
		if (name.equals("victory")) 
			event = new VictoryEvent(counter,x,y,getStringAttribute(node,"name"));
		if (name.equals("destroyed")) 
			event = new DestroyedEvent(counter,x,y);	
		return event;
	}
}