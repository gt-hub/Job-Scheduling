/**
 * @author Gowtham
 * Class for the MinHeap, with the insert(), get_minimum() and remove() functionalities. The MinHeap consists of MinHeapNode's.
 */
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
        int flag=1;     //To know if any swap operation takes place. If yes, we'll be re-iterating through the loop.

        while (minHeap[current].execTime <= minHeap[parent(current)].execTime && current!=parent(current) && flag==1) {     //If the execution time of the node is less than equal to parent and flag set to 1.
            if (minHeap[current].execTime < minHeap[parent(current)].execTime) {
                swap(current, parent(current));     //If execution time of parent is greater than child we swap.
                current = parent(current);
                flag++;     //We increment the flag so that we re-iterate.
            } else if (minHeap[current].execTime == minHeap[parent(current)].execTime && minHeap[current].getBuildingNum() < minHeap[parent(current)].getBuildingNum()) {
                swap(current, parent(current));     //If execution time of parent is equal to child, but building number of child is less then we swap.
                current = parent(current);
                flag++;     //We increment the flag so that we re-iterate.
            }
            flag--;     //We decrement that flag so that if there isn't any swap, we break the loop.
        }
        return minHeapNode;
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
     * To remove the node of given index from Minheap.
     * @param ind: The index of node to be removed from Minheap.
     */
    public void remove(int ind){
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

        if(left<=heapSize && minHeap[left].getExecTime() <= minHeap[pos].getExecTime()){        //If execution time of left child is less than or equal to element.
            if(minHeap[left].getExecTime() == minHeap[pos].getExecTime()){
                if(minHeap[left].getBuildingNum()< minHeap[pos].getBuildingNum())
                    smallest = left;        //If execution time of left child is equal to element, but building number of left child is less than parent, then we set the smallest to left child.
                else
                    smallest = pos;     //If execution time of left child is equal to element, but building number of left child is not less than parent, then we set the smallest to element.
            }else{
                smallest = left;        //If execution time of left child is equal to element,  we set the smallest to left child.
            }
        }else
            smallest = pos;     //If execution time of left child is greater than element, we set the smallest to element.

        if(right<=heapSize && minHeap[right].getExecTime() <= minHeap[smallest].getExecTime()){     //If execution time of left child is less than or equal to element.
            if(minHeap[right].getExecTime() == minHeap[smallest].getExecTime() && minHeap[right].getBuildingNum() < minHeap[smallest].getBuildingNum())
                smallest = right;       //If execution time of right child is equal to element, but building number of left child is less than element, then we set the smallest to left child.
            else if (minHeap[right].getExecTime() < minHeap[smallest].getExecTime())
                smallest = right;       //If execution time of right child is equal to element, we set the smallest to right child.
        }

        if(smallest!=pos){      //If the element is not the smallest, then we swap it smallest and repeat min-heapify on the smallest.
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


}
