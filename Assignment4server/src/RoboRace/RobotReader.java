package RoboRace;

import COSC3P40.xml.*;
import org.w3c.dom.*;

import static COSC3P40.xml.XMLTools.*;

public class RobotReader implements XMLNodeConverter<Robot> {

	public Robot convertXMLNode(Node node) {
		Robot result = null;
		if (node.getNodeName().equals("robot")) {
			String name = getStringAttribute(node,"name");
			int x = getIntAttribute(node,"x");
			int y = getIntAttribute(node,"y");
			Direction direction = (Direction) getEnumAttribute(Direction.class,node,"direction");
			boolean alive = getBoolAttribute(node,"alive");
			int startX = getIntAttribute(node,"start");
			result = new Robot(name,x,y,direction,alive,startX);
		};		
		return result;
	}
	
}