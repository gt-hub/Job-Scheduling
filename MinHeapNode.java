public class MinHeapNode {

    private int buildingNum;        //Building number of the given Minheap node.
    private int totalTime;      //Total time for the Minheap node.
    int execTime;       //Current execution time of the Minheap node.
    RBNode rbNode;      // Corresponding Red-black node.

    /**
     * To set the execution time for a newly created minheap node.
     * @param execTime: The execution time that we should be setting for the new node.
     */
    MinHeapNode(int execTime){
        this.execTime=execTime;
    }

    /**
     * To set the Red Black node for a given Minheap node.
     * @param rbNode: The Red black node that should be set.
     */
    public void setRBNode(RBNode rbNode){
        this.rbNode = rbNode;
    }

    /**
     * To get the execution time of a given Minheap node.
     * @return: execution time of the Minheap node.
     */
    public int getExecTime() {
        return execTime;
    }

    /**
     * To get the building number of the given Minheap node.
     * @return: Building Number for the given minheap node from the corresponding Red black node.
     */
    public int getBuildingNum() {
        if(rbNode!=null){
            buildingNum = rbNode.buildNum;
        }
        return buildingNum;
    }

    /**
     * To get the total time of the given Minheap node.
     * @return: Total time for the given minheap node from the corresponding Red black node.
     */
    public int getTotalTime(){
        if(rbNode!=null){
            totalTime = rbNode.totalTime;
        }
        return totalTime;
    }
}
