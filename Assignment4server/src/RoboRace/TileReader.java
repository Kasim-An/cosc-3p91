package RoboRace;

import COSC3P40.xml.*;
import org.w3c.dom.*;

import static COSC3P40.xml.XMLTools.*;

public class TileReader implements XMLNodeConverter<Tile> {
	
	public Tile convertXMLNode(Node node) {
		Tile tile = null;
		String name = node.getNodeName();
		if (name.equals("belt")) 
			tile = new Belt((Direction) getEnumAttribute(Direction.class,node,"direction"));
		if (name.equals("floor")) 
			tile = new Floor();	
		if (name.equals("gear")) 
			tile = new Gear(getBoolAttribute(node,"clockwise"));
		if (name.equals("pit")) 
			tile = new Pit();
		if (name.equals("goal")) 
			tile = new Goal();
		return tile;
	}
}