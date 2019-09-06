package RoboRace;

import java.util.*;
import org.w3c.dom.*;
import COSC3P40.xml.*;

import static COSC3P40.xml.XMLTools.getChildNodes;

public class CardListReader implements XMLNodeConverter<CardList> {
	
	private CardReader cardReader;
	
	public CardListReader() {
		this.cardReader = new CardReader();
	}
	
	public CardList convertXMLNode(Node node) {
		CardList result = new CardList();
		if (node.getNodeName().equals("cards")) {
			List<Node> list = getChildNodes(node);
			for(Node n : list)
				result.add(cardReader.convertXMLNode(n));
		};
		return result;	
	}
	
}