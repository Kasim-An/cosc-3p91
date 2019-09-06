package RoboRace;

import COSC3P40.xml.*;
import org.w3c.dom.*;

import static COSC3P40.xml.XMLTools.*;

public class CardReader implements XMLNodeConverter<Card> {

	public Card convertXMLNode(Node node) {
		Card result = null;
		int priority = getIntAttribute(node,"priority");
		String name = node.getNodeName();
		if (name.equals("back"))
			result = new CardBack(priority);
		if (name.equals("halfturn"))
			result = new CardHalfTurn(priority);
		if (name.equals("move")) 
			result = new CardMove(priority,getIntAttribute(node,"steps"));
		if (name.equals("turn"))
			result = new CardTurn(priority,getBoolAttribute(node,"clockwise"));
		return result;
	}
	
}