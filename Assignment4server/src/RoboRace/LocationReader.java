package RoboRace;

import COSC3P40.xml.*;
import org.w3c.dom.*;
import java.util.*;

import static COSC3P40.xml.XMLTools.*;

public class LocationReader implements XMLNodeConverter<Location> {
	
	private TileReader tileReader;
	private DecorationReader decoReader;
	
	public LocationReader() {
		tileReader = new TileReader();
		decoReader = new DecorationReader();
	}
	
	public Location convertXMLNode(Node node) {
		Location location = null;
		if (node.getNodeName().equals("location")) {
			List<Node> children = getChildNodes(node);
			Tile tile = tileReader.convertXMLNode(children.get(0));
			Decoration decoration = null;
			if (children.size() > 1 && !children.get(1).getNodeName().equals("walls"))
				decoration = decoReader.convertXMLNode(children.get(1));
			List<Direction> list = new LinkedList<Direction>();
			if (children.get(children.size()-1).getNodeName().equals("walls")) {
				Node node2 = children.get(children.size()-1);
				for(Direction d : Direction.values())
					if (getBoolAttribute(node2,d.toString().toLowerCase())) list.add(d);
			};
			location = new Location(tile,decoration,list);
		};
		return location;
	}
}