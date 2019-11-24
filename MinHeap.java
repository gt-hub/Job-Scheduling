public class MinHeap {

    MinHeapNode[] minHeap = new MinHeapNode[2000];
    int heapSize=-1;

    private int parent(int ind)
    {
        return ind / 2;
    }

    private void swap(int a, int b){
        MinHeapNode tempNode;
        tempNode = minHeap[a];
        minHeap[a] = minHeap[b];
        minHeap[b] = tempNode;
    }

    public MinHeapNode addNode(int execTime, RBNode rbNode){
        MinHeapNode minHeapNode = new MinHeapNode(execTime);
        minHeapNode.setRBNode(rbNode);
        rbNode.setMinHeapNode(minHeapNode);

        minHeap[++heapSize] = minHeapNode;

        int current = heapSize;
        int flag=1;
        while (minHeap[current].execTime <= minHeap[parent(current)].execTime && current!=parent(current) && flag==1) {

            if (minHeap[current].execTime < minHeap[parent(current)].execTime) {
                swap(current, parent(current));
                current = parent(current);
                flag++;
            } else if (minHeap[current].execTime == minHeap[parent(current)].execTime && minHeap[current].getBuildingNum() < minHeap[parent(current)].getBuildingNum()) {
                swap(current, parent(current));
                current = parent(current);
                flag++;
            }
            flag--;
        }
        return minHeapNode;
    }

    public void increaseKey(int ind, int val){
        minHeap[ind].execTime+=val;
        minHeapify(ind);
    }

    public MinHeapNode getMin(){
        if(heapSize>=0)
            return minHeap[0];
        else
            return null;
    }

    public int getIndex(MinHeapNode minHeapNode){
        for(int i=0;i<=heapSize;i++)
            if(minHeap[i]==minHeapNode)
                return i;
        return -1;
    }

    public void remove(int ind){
        MinHeapNode min = minHeap[ind];
        minHeap[ind] = minHeap[heapSize--];
        minHeapify(ind);
    }

    private void minHeapify(int pos){
        int left = getLeftChild(pos);
        int right = getRightChild(pos);
        int smallest;

        if(left<=heapSize && minHeap[left].getExecTime() <= minHeap[pos].getExecTime()){
            if(minHeap[left].getExecTime() == minHeap[pos].getExecTime()){
                if(minHeap[left].getBuildingNum()< minHeap[pos].getBuildingNum())
                    smallest = left;
                else
                    smallest = pos;
            }else{
                smallest = left;
            }
        }else
            smallest = pos;

        if(right<=heapSize && minHeap[right].getExecTime() <= minHeap[smallest].getExecTime()){
            if(minHeap[right].getExecTime() == minHeap[smallest].getExecTime() && minHeap[right].getBuildingNum() < minHeap[smallest].getBuildingNum())
                smallest = right;
            else if (minHeap[right].getExecTime() < minHeap[smallest].getExecTime())
                smallest = right;
        }

        if(smallest!=pos){
            swap(pos, smallest);
            minHeapify(smallest);
        }
    }

    private boolean isLeaf(int ind)
    {
        return ind >=((heapSize+1) / 2) && ind <= heapSize;
    }

    private int getLeftChild(int ind)
    {
        return (2 * ind) + 1;
    }

    private int getRightChild(int ind)
    {
        return (2 * ind) + 2;
    }

    public void print(){
        //System.out.println(heapSize);
        for(int i=0;i<=heapSize;i++){
            System.out.println(minHeap[i].execTime + " " + minHeap[i].getBuildingNum());
        }
    }

}
