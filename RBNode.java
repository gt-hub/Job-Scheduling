public class RBNode {

    enum Color {Red, Black};
    RBNode left;
    RBNode right;
    RBNode parent;
    Color color;

    int buildNum;
    int totalTime;
    int execTime;

    MinHeapNode minHeapNode;

    RBNode(int buildNum, int totalTime){
        this.buildNum = buildNum;
        this.totalTime = totalTime;
    }

    void setBlack(){
        color = Color.Black;
    }

    void setRed(){
        color = Color.Red;
    }

    boolean isBlack() {
        return color == Color.Black;
    }

    boolean isRed() {
        return color == Color.Red;
    }

    boolean isRoot() {
        return parent == null;
    }

    boolean isLeaf() {
        return left == null && right == null;
    }

    RBNode getGrandparent() {
        return parent!=null? parent.parent : null;
    }

    RBNode getUncle() {
        RBNode grandparent = getGrandparent();
        return grandparent==null ? null : ( parent == grandparent.right ? grandparent.left : grandparent.right);
    }

    int size() {
        return 1 + (left == null ? 0 : left.size()) + (right == null ? 0 : right.size());
    }

    RBNode sibling() {
        return this == parent.left ? parent.right : parent.left;
    }

    public void setMinHeapNode(MinHeapNode minHeapNode){
        this.minHeapNode = minHeapNode;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public int getExecTime() {
        if(minHeapNode!=null){
            execTime = minHeapNode.getExecTime();
            return  execTime;
        }
        return 0;
    }
}
