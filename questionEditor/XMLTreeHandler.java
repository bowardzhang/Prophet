/**
 * this class consist methods which could write an DataTreeNode to an xml file 
 * or read an xml file into a DataTreeNode
 * 
 * @author Markus K�ppen, Andreas Hasselberg
 */

package questionEditor;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLTreeHandler {

	/**
	 * method which adds recursively the childs (to an xml file)
	 * 
	 * @param treeChilds
	 *            childs which should bye added
	 * @param xmlParent
	 *            the parent who should get the childs
	 * @param xmlTree
	 *            the xml-document
	 */
	private static void addChildsToXML(Vector<DataTreeNode> treeChilds,
			Element xmlParent, Document xmlTree) {
		// Kinder hinzuf�gen
		for (DataTreeNode treeChild : treeChilds) {
			Element xmlChild = xmlTree.createElement(treeChild.getName());
			xmlParent.appendChild(xmlChild);
			// Attribute hinzuf�gen
			Vector<ElementAttribute> childAttributes = treeChild
					.getAttributes();
			for (ElementAttribute childAttribute : childAttributes) {
				xmlChild.setAttribute(childAttribute.getName(), childAttribute
						.getContent().toString());
			}
			// evtl. neue Kinder hinzuf�gen
			if (treeChild.getChildCount() > 0) {
				addChildsToXML(treeChild.getChildren(), xmlChild, xmlTree);
			}
		}
	}

	/**
	 * writes an DataTreeNode with his childs into an XML-File
	 * 
	 * @param treeRoot
	 *            DataTreeNode which should be added (with childs)
	 * @param path
	 *            path for the xml-file
	 */
	public static void writeXMLTree(DataTreeNode treeRoot, String path) {
		Document xmlTree = null;
		try {
			// Dokument erstellen
			xmlTree = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.newDocument();
			// Wurzelknoten erschaffen und Attribute hinzuf�gen
			Element xmlRoot = xmlTree.createElement(treeRoot.getName());
			xmlTree.appendChild(xmlRoot);
			Vector<ElementAttribute> rootAttributes = treeRoot.getAttributes();
			for (ElementAttribute ea : rootAttributes) {
				xmlRoot.setAttribute(ea.getName(), ea.getContent().toString());
			}
			// Kinder hinzuf�gen
			if (treeRoot.getChildCount() > 0) {
				addChildsToXML(treeRoot.getChildren(), xmlRoot, xmlTree);
			}
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		}
		// Fragebogen in Datei speichern
		try {
			if (xmlTree != null) {
				TransformerFactory
						.newInstance()
						.newTransformer()
						.transform(new DOMSource(xmlTree),
								new StreamResult(path + ".xml"));
			}
		} catch (TransformerConfigurationException e1) {
			e1.printStackTrace();
		} catch (TransformerException e1) {
			e1.printStackTrace();
		} catch (TransformerFactoryConfigurationError e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * method which adds recursively the childs (to a DataTreeNode file)
	 * 
	 * @param xmlChilds
	 *            the childs which should be added
	 * @param treeParent
	 *            the DataTreeNode which should get the childs
	 */
	private static void addChildsToTree(NodeList xmlChilds,
			DataTreeNode treeParent) {
		// Kinder hinzuf�gen
		for (int i = 0; i < xmlChilds.getLength(); i++) {
			Node xmlChild = xmlChilds.item(i);
			DataTreeNode treeChild = new DataTreeNode(xmlChild.getNodeName());
			// Attribute hinzuf�gen
			NamedNodeMap childAttributes = xmlChild.getAttributes();
			for (int j = 0; j < childAttributes.getLength(); j++) {
				Node childAttribute = childAttributes.item(j);
				treeChild.addAttribute(new ElementAttribute(childAttribute
						.getNodeName(), childAttribute.getNodeValue()));
			}
			// Kind hinzuf�gen
			treeParent.addChild(treeChild, i);
			// evtl. weitere Kinder hinzuf�gen
			if (xmlChild.getChildNodes().getLength() > 0) {
				addChildsToTree(xmlChild.getChildNodes(), treeChild);
			}
		}
	}

	/**
	 * loads an xml file an creates an corresponding DataTreeNode
	 * 
	 * @param path
	 *            path of the file
	 * @return root of the new tree-structure
	 */
	public static DataTreeNode loadXMLTree(String path) {
		DataTreeNode treeRoot = new DataTreeNode("default");
		try {
			// Document lesen
			Document doc = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().parse(new File(path));
			// Wurzel holen
			Node xmlRoot = doc.getFirstChild();
			treeRoot.setName(xmlRoot.getNodeName());
			// Wurzelattribute
			NamedNodeMap rootAttributes = xmlRoot.getAttributes();
			for (int i = 0; i < rootAttributes.getLength(); i++) {
				Node rootAttribute = rootAttributes.item(i);
				treeRoot.addAttribute(new ElementAttribute(rootAttribute
						.getNodeName(), rootAttribute.getNodeValue()));
			}
			// Kinder hinzuf�gen
			addChildsToTree(xmlRoot.getChildNodes(), treeRoot);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return treeRoot;
	}
}
