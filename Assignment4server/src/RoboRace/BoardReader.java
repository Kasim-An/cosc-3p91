package RoboRace;

import COSC3P40.xml.*;
import org.w3c.dom.*;
import java.util.*;

import static COSC3P40.xml.XMLTools.*;

public class BoardReader implements XMLNodeConverter<Board> {
	
	private RobotReader robotReader;
	private FactoryReader factoryReader;
	
	public BoardReader() {
		robotReader = new RobotReader();
		factoryReader = new FactoryReader();
	}

	public Board convertXMLNode(Node node) {
		Board result = null;
		if (node.getNodeName().equals("board")) {
			List<Node> childNodes = getChildNodes(node);
			if (childNodes.size() == 2) {
				Factory factory = null;
				int numberRobots = 0;
				Robot[] robots = null;
				for(Node node2 : childNodes) {
					if (node2.getNodeName().equals("factory"))
						factory = factoryReader.convertXMLNode(node2);
					if (node2.getNodeName().equals("robots")) {
						numberRobots = getIntAttribute(node2,"number");
						robots = new Robot[numberRobots];
						List<Node> robNodes = getChildNodes(node2);
						if (robNodes.size() == numberRobots) {
							for(int i=0; i<numberRobots; i++)
								robots[i] = robotReader.convertXMLNode(robNodes.get(i));
						};
					};
					result = new Board(factory,numberRobots,robots);
				};
			};
		};		
		return result;
	}
	
}