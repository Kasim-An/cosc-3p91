package RoboRace;

import java.util.*;
import java.io.*;
import COSC3P40.xml.*;

public class CardList extends LinkedList<Card> implements XMLObject {
	
	public static CardList read(Reader input) {
		XMLReader<CardList> reader = new XMLReader<CardList>();
        reader.setXMLSchema("cards.xsd");
        reader.setXMLNodeConverter(new CardListReader());
        return reader.readXML(input);
	}
	
	public String toXMLString() {
		String result = "<cards>\n";
		for(Card card : this)
			result += card.toXMLString() + "\n";
		return result + "</cards>";
	}
	
}