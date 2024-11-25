/*
 * @Author: Allison Ye, aye28, 251339668
 * @Date: April 5, 2024
 * @Description: This code creates methods to help implement the methods in QuadrantTree.java
 * Represents a node of the quadrant tree
 */

public class QTreeNode {
	
	//Declare private instance variables
	private int x;
	private int y;
	private int size;
	private int color;
    private QTreeNode parent;
    private QTreeNode[] children;
    
    //Initializes variables
    public QTreeNode() {
        parent = null;
        //Creates an array of size 4 for the "children"
        children = new QTreeNode[4];
        x = 0;
        y = 0;
        size = 0;
        color = 0;
    }
    
    //Initializes variables to the values passed as parameters
    public QTreeNode(QTreeNode[] theChildren, int xcoord, int ycoord, int theSize, int theColor) {
    	children = theChildren;
    	x = xcoord;
    	y = ycoord;
    	size = theSize;
    	color = theColor;
    }
    
    //Checks to see if the x and y coordinates are contained in the quadrant
    public boolean contains(int xcoord, int ycoord) {
    	if ((xcoord >= x && xcoord < x + size) && (ycoord >= y && ycoord < y + size)) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
    //Returns x
    public int getx() {
    	return x;
    }
    
    //Returns y
    public int gety() {
    	return y;
    }
    
    //Returns size
    public int getSize() {
    	return size;
    }
    
    //Returns color
    public int getColor() {
    	return color;
    }
    
    //Returns parent
    public QTreeNode getParent() {
    	return parent;
    }
    
    //Returns children
    public QTreeNode getChild(int index) throws QTreeException{
    	//Make sure the children exists within the array
    	if (children == null || index < 0 || index > 3) {
            throw new QTreeException("Invalid index or null children");
    	}
        return children[index];
    }
    
    //Sets x to a new value
    public void setx(int newx){
    	this.x = newx;
    }
    
    //Sets y to a new value
    public void sety(int newy){
    	this.y = newy;
    }

    //Sets size to a new value
    public void setSize(int newSize){
    	this.size = newSize;
    }

    //Sets color to a new value
    public void setColor(int newColor){
    	this.color = newColor;
    }
    
    //Sets parent to a new value
    public void setParent(QTreeNode newParent) {
        this.parent = newParent;
    }
    
    //Sets the child to a new value
    public void setChild(QTreeNode newChild, int index) {
    	//Make sure the children exists within the array
    	if (children == null || index < 0 || index > 3) {
            throw new QTreeException("Invalid index or null children");
    	}
        
    	children[index] = newChild;
    }
    
    //Checks to see if the children is a leaf
    public boolean isLeaf(){
    	if (children == null)
            return true;
        for (int i = 0; i < children.length; i++) {
            if (children[i] != null)
                return false;
        }
        return true;
    }
}
