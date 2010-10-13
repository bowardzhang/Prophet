/**
 * Repr�sentiert einen Knoten eines Baumes, durch den auf den gesamten Baum zugegriffen werden kann
 * Der Inhalt eines Knoten repr�sentiert dabei die Informationen eines QuestionElement
 * 
 * @author Markus K�ppen, Andreas Hasselberg
 */

package experimentQuestionCreator;

import java.awt.Dimension;
import java.util.Vector;


public class TreeNode {	
	
	private String text;	//Komponententextinhalt - Zeilenumbr�che in html darstellen
	private int model;		//Komponentenart (siehe QuestionElement.java) (-1 wenn keine Komponente)
	private Dimension size;
	private int childCount;	//Anzahl der Kinder
	private Vector<TreeNode> children;	//Kinder
	private TreeNode parent;//Vater
	
	/**
	 * Konstruktor der ein Wurzelelement erschafft
	 */
	public TreeNode() {
		children = new Vector<TreeNode>();
		this.parent = null;
		this.model = -1;
	}

	/**
	 * Konstruktor der ein Frageelement erschafft
	 * @param parent Wurzel
	 * @param text Fragetitel
	 */
	public TreeNode(TreeNode parent, String text) {
		children = new Vector<TreeNode>();
		this.parent = parent;
		this.model = -1;
		this.text = text;
	}

	/**
	 * Konstruktor der eine Komponente erschafft
	 * @param parent Frage
	 * @param text Inhalt (Beschriftung)
	 * @param component Art der Komponente
	 * @param size Gr��e der Komponente
	 */
	public TreeNode(TreeNode parent, String text, int component, Dimension size) {
		children = new Vector<TreeNode>();
		this.parent = parent;
		this.text = text.replace("\n", "<br>");
		this.model = component;
		this.size = size;
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
	
	/**
	 * Liefert den Text (Beschriftung) zur�ck
	 * @return String der den Text reps�sentiert
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * Liefert den Komponententyp als Integer zur�ck (siehe QuestionElement.java)
	 * @return Integer der den Komponententyp repr�sentiert, -1 wenn es keine Komponente ist
	 */
	public int getModel() {
		return model;
	}
	
	/**
	 * Liefert die Gr��e der Komponente zur�ck
	 * @return Breite in Pixeln
	 */
	public Dimension getSize() {
		return size;
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
}
