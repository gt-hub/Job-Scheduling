public class MinHeapNode {

    private int buildingNum;
    private int totalTime;
    int execTime;
    RBNode rbNode;

    MinHeapNode(int execTime){
        this.execTime=execTime;
    }

    public void setRBNode(RBNode rbNode){
        this.rbNode = rbNode;
    }

    public int getExecTime() {
        return execTime;
    }

    public int getBuildingNum() {
        if(rbNode!=null){
            buildingNum = rbNode.buildNum;
        }
        return buildingNum;
    }

    public int getTotalTime(){
        if(rbNode!=null){
            totalTime = rbNode.totalTime;
        }
        return totalTime;
    }
}
