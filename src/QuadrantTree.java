/*
 * @Author: Allison Ye, aye28, 251339668
 * @Date: April 5, 2024
 * @Description: This code creates a simple application to display images at different resolution
 * Compare and identify similar colors within the image
 */

public class QuadrantTree {
	
	//Declare private instance variables
	private QTreeNode root;
	
	//Build a quadrant tree 
	private QTreeNode buildTree(int[][] thePixels, int x, int y, int size){
		//For the last level and when the nodes are leafs
		if (size == 1) {
			QTreeNode node = new QTreeNode(null, x, y, 1, Gui.averageColor(thePixels, x, y, size));
			return node;
		}
		//Otherwise, recursively build each child node
		else {
			//Create a new size, in base 2
			int newSize = size / 2;
			QTreeNode[] children = new QTreeNode[4];
			
			//Adding the newSize to correspond with the right quadrants for the children
			QTreeNode child0 = buildTree(thePixels, x, y, newSize);
			QTreeNode child1 = buildTree(thePixels, x + newSize, y, newSize);
			QTreeNode child2 = buildTree(thePixels, x, y + newSize, newSize);
			QTreeNode child3 = buildTree(thePixels, x + newSize, y + newSize, newSize);
			
			//Store child nodes into an array, assigning each children its value
			children[0] = child0;
			children[1] = child1;
			children[2] = child2;
			children[3] = child3;

			//Create a parent node for the quadrant
			QTreeNode parent = new QTreeNode(children, x, y, size, Gui.averageColor(thePixels, x, y, size));
			
			//Set the parent for each children
			children[0].setParent(parent);	
			children[1].setParent(parent);
			children[2].setParent(parent);
			children[3].setParent(parent);
			
			return parent;
		
		}
	}
	
	//Adds to linked lists together
	private ListNode<QTreeNode> addList(ListNode<QTreeNode> list1, ListNode<QTreeNode> list2) {
		//Makes sure that neither of the lists are null
		if(list1 == null && list2 == null) {
			return null;
		}
		//If only one list is null, return the other one with items
		else if (list1 == null) {
			return list2;
		}
		else if (list2 == null) {
			return list1;
		}
		//If there are items in both lists, add them together
		else {
			ListNode<QTreeNode> current = list1;
			//Traverse to the end of list1
			while(current.getNext() != null) {
				current = current.getNext();
			}
			//Connect the two lists, setting the end of list1 to the start of list2
			current.setNext(list2);
			return list1;
		}
	}
	
	
	//Build the quadrant tree, calling the buildTree helper method
	public QuadrantTree (int[][] thePixels) {
		root = buildTree(thePixels, 0, 0, thePixels.length);
	}
	
	//Returns the root of the tree
	public QTreeNode getRoot() {
		return this.root;
	}
	
	//Returns the nodes at the specified level
	public ListNode<QTreeNode> getPixels(QTreeNode r, int theLevel){
		//Initialize variables later used in the method
		ListNode<QTreeNode> levelList = null;
		
		//Makes sure that r is not null
		if(r == null) {
			return null;
		}
		//If the level is at the end or is a leaf, return the list of nodes
		else if(theLevel == 0 || r.isLeaf()) {
			levelList = new ListNode<QTreeNode>(r);
			return levelList;
		}
		//Recursively traverse each child node, adding them to levelList
		else {
			for (int i = 0; i < 4; i++) {
				ListNode<QTreeNode> childList = getPixels(r.getChild(i), theLevel - 1);
				levelList = addList(levelList, childList);
			}
			return levelList;
		}	
	}
	
	//Find nodes with matching or similar colors at the given level
	public Duple findMatching (QTreeNode r, int theColor, int theLevel) {
		//Initialize variables later used in the method
		ListNode<QTreeNode> matchingList = null;
		int count = 0;
		
		//Makes sure that r is not null
		if (r == null) {
			Duple result = new Duple(null, 0);
			return result;
		}
		//Checks to see if the level is at the end or is a leaf
		else if (theLevel == 0 || r.isLeaf()) {
			//If the colors match, return the list of matching nodes
			if (Gui.similarColor(r.getColor(), theColor)) {
				matchingList = new ListNode <QTreeNode>(r);
				Duple result = new Duple(matchingList, 1);
				return result;
			}
			//If the color does not match, return an empty list
			else {
				Duple result = new Duple(null, 0);
				return result;
			}	
		}
		//Recursively traverse each child node, adding them to the list if the colors are matching
		else {
			for (int i = 0; i < 4; i++) {
				Duple childList = findMatching (r.getChild(i), theColor, theLevel - 1);
				matchingList = addList(matchingList, childList.getFront());
				count += childList.getCount();
			}
			Duple dupleList = new Duple(matchingList, count);
			return dupleList;
		}
	}

	//Find a node at the given level
	public QTreeNode findNode(QTreeNode r, int theLevel, int x, int y){
		//Makes sure r is not null and contains the coordinates
		if (r == null || !r.contains(x, y)) {
	        return null;
		}
		//If the level is at the end or is a leaf, return r
		else if (theLevel == 0 || r.isLeaf())
	        return r;
		//Recursively traverse each child node, checking to see if the nodes contains the coordinates
		else {
			for (int i = 0; i < 4; i++) {
		        QTreeNode child = r.getChild(i);
		        if (child.contains(x, y)) {
		        	QTreeNode result = findNode(child, theLevel - 1, x, y);
		            return result;
		        }
		    }
		}
	    
	    return null;
	}
}
