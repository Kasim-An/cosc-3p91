package COSC3P40.xml;

import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.InputSource;


public class XMLReader<E> {
	
    private static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    private static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema"; 
    private static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";
    
    private static String xmlPath = "";
    private static String xsdPath = "";
	
    private DocumentBuilderFactory factory;
    private DocumentBuilder builder = null;
    private Document document = null;
    private XMLNodeConverter<E> converter = null;
    
    public static void setXMLPath(String path) {
	xmlPath = path;
	if (!xmlPath.endsWith("/")) xmlPath += "/";
    }
	
    public static void setXSDPath(String path) {
	xsdPath = path;
	if (!xsdPath.endsWith("/")) xsdPath += "/";
    }
	
    public XMLReader() {
        factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
  	factory.setValidating(true);
	try {
            factory.setAttribute(JAXP_SCHEMA_LANGUAGE,W3C_XML_SCHEMA);
	} catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
	};        
    }
    
    public void setXMLSchema(String schema) {
    	String[] schemas = { xsdPath+schema };
	factory.setAttribute(JAXP_SCHEMA_SOURCE,schemas);
        try {
           builder = factory.newDocumentBuilder();
        } catch (Exception ex) {
           ex.printStackTrace();
           System.exit(1);
        };
    }
    
    public void setXMLNodeConverter(XMLNodeConverter<E> converter) {
    	this.converter = converter;
    }
       
    public E readXML(File f) {
    	checkStatus();
    	try {
	    document = builder.parse(f);
	} catch (Exception ex) {
            ex.printStackTrace();
	};
	return converter.convertXMLNode(document.getFirstChild());
    }
    
    public E readXML(InputSource is) {
	checkStatus();
    	try {
            document = builder.parse(is);
	} catch (Exception ex) {
	    ex.printStackTrace();
	};
	return converter.convertXMLNode(document.getFirstChild());
    }
    
    public E readXML(Reader reader) {
    	return readXML(new InputSource(reader));
    }
    
    public E readXML(InputStream is) {
    	checkStatus();
    	try {
            document = builder.parse(is);
	} catch (Exception ex) {
	    ex.printStackTrace();
        };
	return converter.convertXMLNode(document.getFirstChild());
    }
    
    public E readXML(InputStream is, String systemId) {
    	checkStatus();
    	try {
            document = builder.parse(is,systemId);
	} catch (Exception ex) {
	    ex.printStackTrace();
	};
	return converter.convertXMLNode(document.getFirstChild());
    }
    
    public E readXML(String uri) {
    	checkStatus();
    	try {
            document = builder.parse(xmlPath+uri);
	} catch (Exception ex) {
            ex.printStackTrace();
	};
	return converter.convertXMLNode(document.getFirstChild());
    }	

   private void checkStatus() {
   	if (builder==null) {
            System.out.println("No XMLSchema set.");
            System.exit(1);
   	};
   	if (converter==null) {
            System.out.println("No XMLNodeConverter set.");
            System.exit(1);
   	};
   }
    	
}    