public class MinHeap {


    MinHeapNode[] minHeap = new MinHeapNode[2001];      // Array for storing the nodes in Minheap.
    int heapSize=-1;        //Initial size of Minheap.

    /**
     * To return the parent of the given Node in minheap.
     * @param ind: The index of the node for which we need to find the parent.
     * @return: The index of the parent of given node.
     */
    public int parent(int ind)
    {
        return (ind-1)/2;
    }

    /**
     * To swap 2 minheap Nodes in the minheap.
     * @param a: Index of first node.
     * @param b: Index of second node.
     */
    private void swap(int a, int b){
        MinHeapNode tempNode;
        tempNode = minHeap[a];
        minHeap[a] = minHeap[b];
        minHeap[b] = tempNode;
    }

    /**
     * To add a new node to the Minheap.
     * @param execTime: Executed time of the newly added node.
     * @param rbNode: Corresponding Red Black node of the newly created node.
     * @return: newly created minheap node.
     */
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

    /**
     * To increase the value of node at given Index in Minheap by a given value.
     * @param ind: The index of node whose value is to be increased.
     * @param val: The value by which the node at the index is to be increased.
     */
    public void increaseKey(int ind, int val){
        minHeap[ind].execTime+=val;
        minHeapify(ind);
    }

    /**
     * To find the minimum Node in minheap
     * @return: returns the first node in Minheap if it's size is greater than 0.
     */
    public MinHeapNode getMin(){
        if(heapSize>=0)
            return minHeap[0];
        else
            return null;
    }

    /**
     * To find the index of a given Minheap Node.
     * @param minHeapNode: The node for which we should find the index in minheap.
     * @return: The index of node in Minheap.
     */
    public int getIndex(MinHeapNode minHeapNode){
        for(int i=0;i<=heapSize;i++)
            if(minHeap[i]==minHeapNode)
                return i;
        return -1;
    }

    /**
     * To remove the node of given index from Minheap.
     * @param ind: The index of node to be removed from Minheap.
     */
    public void remove(int ind){
        //MinHeapNode min = minHeap[ind];
        if(heapSize==-1)
            return;
        minHeap[ind] = minHeap[heapSize--];
        minHeapify(ind);
    }

    /**
     * To perform Min Heapify operation at the given position in minheap.
     * @param pos: The position at which we need to perform Min heapify operation.
     */
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

    /**
     * To get the index of left child of given Node.
     * @param ind: Index of the given node.
     * @return: Index of the left child of the given node.
     */
    private int getLeftChild(int ind)
    {
        return (2 * ind) + 1;
    }

    /**
     * To get the index of right child of given Node.
     * @param ind: Index of the given node.
     * @return: Index of the right child of the given node.
     */
    private int getRightChild(int ind)
    {
        return (2 * ind) + 2;
    }

    public void print(){
        for(int i=0;i<=heapSize;i++){
            System.out.print(" Build No: "+minHeap[i].getBuildingNum() +"Exec Time: "+ minHeap[i].getExecTime() + "Parent Build No: " + minHeap[(parent(i))].getBuildingNum());
        }
        System.out.println();
    }

}
