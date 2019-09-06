package COSC3P40.xml;

import org.w3c.dom.Node;

public interface XMLNodeConverter<E> {
	
	public E convertXMLNode(Node node);
	
}