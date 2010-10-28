/**
 * Repr�sentiert einen Knoten eines Baumes, durch den auf den gesamten Baum zugegriffen werden kann
 * Der Inhalt eines Knoten repr�sentiert dabei die Informationen eines QuestionElement
 * 
 * @author Markus K�ppen, Andreas Hasselberg
 */

package questionEditor;

import java.awt.Dimension;
import java.util.Vector;


public class TreeNode {	
	
	//Attributliste
	private Vector<ElementAttribute> attributes;
	
	private String name;	//Name des Knoten
	private int childCount;	//Anzahl der Kinder
	private Vector<TreeNode> children;	//Kinder
	private TreeNode parent;//Vater
	
	/**
	 * Konstruktor der ein Wurzelelement erschafft
	 * @param name Name des Knoten
	 */	
	public TreeNode(String name) {
		this.name = name;
		this.children = new Vector<TreeNode>();
		this.parent = null;	
		this.attributes = new Vector<ElementAttribute>();
	}
	
	/**
	 * Konstruktor f�r einen Unterknoten
	 * @param name Name des Knoten
	 * @param parent Vater des Knoten
	 * @param attributes Attributliste des Knoten
	 */
	public TreeNode(String name, TreeNode parent, Vector<ElementAttribute> attributes) {
		this.name = name;
		this.children = new Vector<TreeNode>();
		this.parent = null;
		
		this.attributes = attributes;
		
	}
	
	/**
	 * Returns the Name of the Node
	 * @return Name of the Node
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set the Name of a Node
	 * @param name the new Name of the Node
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Liefert den Vater des Knoten zur�ck
	 * @return Vaterknoten
	 */
	public TreeNode getParent() {
		return parent;
	}
	
	/**
	 * Testet ob es die Wurzel des Baumes ist
	 * @return true wenn es eine Wurzel ist, sonst false
	 */
	public boolean isRoot() {
		return (parent == null);
	}
	
	/**
	 * F�gt dem Knoten ein Kind hinzu
	 * @param child Knoten der als Kind hinzugef�gt wird 
	 */
	public void addChild(TreeNode child) {
		children.add(child);
		childCount++;
	}
	
	public Vector<ElementAttribute> getAttributes() {
		return attributes;
	}
	
	public void addAttributes(ElementAttribute attribute) {
		this.attributes.add(attribute);
	}
	
	/**
	 * Gibt einen Vector mit allen Kindern zur�ck
	 * @return Vector der alle TreeNode, welche kinder sind, enth�lt
	 */
	public Vector<TreeNode> getChildren() {
		return children;
	}
	
	/**
	 * Gibt spezielles Kind zur�ck
	 * @param index Position an welcher das Kind im Vector steht
	 * @return TreeNode, welcher an der gew�nschten Position im Vector ist
	 */
	public TreeNode getChild(int index) {
		return children.get(index);
	}
	
	public TreeNode getChild(String name) {
		for(TreeNode child : children) {
			if(child.getName().equals(name)) {
				return child;
			}
		}
		return null;
	}
	
	/**
	 * Gibt die Anzahl der kinder dieses Knotens zur�ck
	 * @return Anzahl der Kinder
	 */
	public int getChildCount() {
		return childCount;
	}
	
	/**
	 * Testet ob dieser Knoten ein Blatt ist
	 * @return true wenn ja, false sonst
	 */
	public boolean isLeaf() {
		return (childCount == 0);
	}
	
	public boolean removeChild(String name) {
		int i = 0;
		for(TreeNode child : children) {
			if(child.getName().equals(name)) {
				children.removeElementAt(i);
				return true;
			}
			i++;
		}
		return false;
	}
	
	public String toString() {
		String ret = this.name;
		if(childCount > 0) {
			ret += " { ";
			for(TreeNode child : children) {
				ret += child.toString() + "|";
			}
			ret += " } ";
		}
		return ret;
	}
	
	public boolean nameExist(String name) {
		if(this.name.equals(name)) {
			return true;
		} else {
			for(TreeNode child : children) {
				if(child.nameExist(name)) {
					return true;
				}
			}
		}
		return false;
	}
}
