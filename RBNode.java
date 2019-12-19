public class RBNode {

    enum Color {Red, Black};
    RBNode left;        //Left child of the given node.
    RBNode right;       //Right child of the given node.
    RBNode parent;      //Parent of the given node.
    Color color;        //color of the given node.

    int buildNum;       //Building number of a given node.
    int totalTime;      //total time for construction of the given node.
    int execTime;       //total executed time of the given node.

    MinHeapNode minHeapNode;        // Corresponding min-heap node.

    /**
     * Constructor to set the building number and total time for a newly created red-black node.
     * @param buildNum: Building number to be set.
     * @param totalTime: Total time for the completion of building.
     */
    RBNode(int buildNum, int totalTime){
        this.buildNum = buildNum;
        this.totalTime = totalTime;
    }

    /**
     * To set a given node color to be black.
     */
    void setBlack(){
        color = Color.Black;
    }


    /**
     * To set a given node color to be red.
     */
    void setRed(){
        color = Color.Red;
    }

    /**
     * To check if given node is black.
     * @return: True if node is black else false.
     */
    boolean isBlack() {
        return color == Color.Black;
    }


    /**
     * To check if given node is red.
     * @return: True if node is red else false.
     */
    boolean isRed() {
        return color == Color.Red;
    }

    /**
     * To check if the given node is root.
     * @return: True if the given node is root else false.
     */
    boolean isRoot() {
        return parent == null;
    }

    /**
     * To get the grandparent of a given node.
     * @return: Returns grandparent if exists else returns null.
     */
    RBNode getGrandparent() {
        return parent!=null? parent.parent : null;
    }


    /**
     * To get the uncle of a given node.
     * @return: Returns uncle if exists else returns null.
     */
    RBNode getUncle() {
        RBNode grandparent = getGrandparent();
        return grandparent==null ? null : ( parent == grandparent.right ? grandparent.left : grandparent.right);
    }

    /**
     * To get the sibling of a given node.
     * @return: Returns sibling.
     */
    RBNode sibling() {
        return this == parent.left ? parent.right : parent.left;
    }

    /**
     * To set the corresponding Minheap Node
     * @param minHeapNode: Corresponding minheap node.
     */
    public void setMinHeapNode(MinHeapNode minHeapNode){
        this.minHeapNode = minHeapNode;
    }

    /**
     * To get the total time for a given node.
     * @return: total time for that building
     */
    public int getTotalTime() {
        return totalTime;
    }


    /**
     * To get the executed time for a given node.
     * @return: Executed time for that building from corresponding min-heap node.
     */
    public int getExecTime() {
        if(minHeapNode!=null){
            execTime = minHeapNode.getExecTime();
            return  execTime;
        }
        return 0;
    }
}
