package RoboRace;

import COSC3P40.xml.*;
import org.w3c.dom.*;
import java.util.*;

import static COSC3P40.xml.XMLTools.*;

public class FactoryReader implements XMLNodeConverter<Factory> {
	
	private LocationReader locReader;
	
	public FactoryReader() {
		locReader = new LocationReader();
	}
	
	public Factory convertXMLNode(Node node) {
		Factory factory = null;
		if (node.getNodeName().equals("factory")) {
			int xSize = getIntAttribute(node,"xSize");
			int ySize = getIntAttribute(node,"ySize");
			Location[][] grid = new Location[xSize][ySize];
			List<Node> list = getChildNodes(node);
			if (list.size() == xSize*ySize) {
				for(int j=0; j<ySize; j++)
					for(int i=0; i<xSize; i++)
						grid[i][j] = locReader.convertXMLNode(list.get(j*xSize+i));
				factory = new Factory(xSize,ySize,grid);
			};
		};
		return factory;
	}
	
}