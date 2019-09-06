package RoboRace;

import COSC3P40.xml.*;
import org.w3c.dom.*;

import static COSC3P40.xml.XMLTools.*;

public class DecorationReader implements XMLNodeConverter<Decoration> {
	
	public Decoration convertXMLNode(Node node) {
		Decoration decoration = null;
		String name = node.getNodeName();
		if (name.equals("crusher")) {
			int phase1 = getIntAttribute(node,"phase1");
			Attr attr = (Attr) node.getAttributes().getNamedItem("phase2");
			if (attr != null)
				decoration = new Crusher(phase1,Integer.valueOf(attr.getValue()));
			else
				decoration = new Crusher(phase1);
		};
		if (name.equals("pusher")) {
			Direction direction = (Direction) getEnumAttribute(Direction.class,node,"direction");
			int phase1 = getIntAttribute(node,"phase1");
			int phase2 = getIntAttribute(node,"phase2");
			Attr attr = (Attr) node.getAttributes().getNamedItem("phase3");
			if (attr != null)
				decoration = new Pusher(direction,phase1,phase2,Integer.valueOf(attr.getValue()));
			else
				decoration = new Pusher(direction,phase1,phase2);
		};	
		return decoration;
	}
}