package RoboRace;

import java.util.*;
import org.w3c.dom.*;
import COSC3P40.xml.*;

import static COSC3P40.xml.XMLTools.getChildNodes;

public class EventListReader implements XMLNodeConverter<EventList> {
	
	private EventReader eventReader;
	
	public EventListReader() {
		eventReader = new EventReader();
	}
	
	public EventList convertXMLNode(Node node) {
		EventList result = new EventList();
		if (node.getNodeName().equals("events")) {
			List<Node> list = getChildNodes(node);
			for(Node n : list)
				result.add(eventReader.convertXMLNode(n));
		};
		return result;	
	}
	
}