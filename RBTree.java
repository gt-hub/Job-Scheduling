/**
 * @author Gowtham
 * Class for the Red-Black tree, with the insert, delete and find functionalities. The RBTree consists of RBNode's.
 */
public class RBTree {

    RBNode root;        // Root of Red-Black tree.

    /**
     * To set the root of RBTree.
     * @param node: The node to be set as the root.
     */
    public void setRoot(RBNode node){
        root = node;
        if(node!=null)
            node.setBlack();
    }

    /**
     * To determine if a given node is black or null.
     * @param node: Given node.
     * @return: Returns true if the given node is Black/Null.
     */
    public boolean isBlackOrNull(RBNode node){
        return node==null||node.isBlack();
    }

    /**
     * To determine if a given node is red and not null.
     * @param node: Given node
     * @return: Returns true if the given node is Red and not Null.
     */
    public boolean isRedAndNotNull(RBNode node){
        return node!=null && node.isRed();
    }

    /**
     * Set the color of given node to black if it's not null.
     * @param node: Given node.
     */
    public void setBlackIfNotNull(RBNode node){
        if(node!=null)
            node.setBlack();
    }

    /**
     * To check if the given node is a left-child of it's parent.
     * @param node: The given node to be checked.
     * @return: True if the node is a left child of it's parent.
     */
    public boolean isLeftChild(RBNode node){
        return node == node.parent.left;
    }

    /**
     * To check if the given node is a right-child of it's parent.
     * @param node: The given node to be checked.
     * @return:  True if the node is a right child of it's parent.
     */
    public boolean isRightChild(RBNode node){
        return node == node.parent.right;
    }

    /**
     * TO find a node of the given building-Number in the Tree.
     * @param buildNum: Building-Number of the node to be discovered.
     * @return: Node with the given building number
     */
    public RBNode findNode(int buildNum) {
        RBNode node = root;
        while (node != null) {
            if (node.buildNum == buildNum) {        //If the building number of the node is same as the required number, we return the node.
                return node;
            } else if (buildNum<node.buildNum) {        //Else we traverse through the left child of the node.
                node = node.left;
            } else {
                node = node.right;      //Else we next traverse through the right child of the node.
            }
        }
        return null;
    }

    /**
     * To rotate a given node towards left
     * @param node: Node to be rotated left.
     */
    private void leftRotate(RBNode node) {
        if (node != null) {
            RBNode right = node.right;
            transplant(node, right);        //We copy the right child of the node to the node.
            if(right==null)
                node.right=null;
            else
                node.right=right.left;      //We make the left child of the node's right child as the node's right child.
            if (right != null) {
                if (right.left != null) {
                    right.left.parent = node;       //We make the parent of the left child of the node's right child as the node.
                }
                right.left = node;
            }
            node.parent = right;        //We make the right child of the node as the node's parent.
        }
    }

    /**
     * To rotate a given node towards right.
     * @param node: Node to be rotated right.
     */
    private void rightRotate(RBNode node) {
        if (node != null) {
            RBNode left = node.left;
            transplant(node, left);     //We copy the left child of the node to the node.
            if(left==null)
                node.left=null;
            else
                node.left = left.right;     //We make the right child of the node's left child as the node's left child.
            if (left != null) {
                if (left.right != null) {
                    left.right.parent = node;       //We make the parent of the right child of the node's left child as the node.
                }
                left.right = node;
            }
            node.parent = left;         //We make the left child of the node as the node's parent.
        }
    }

    /**
     * To replace the given node with a replacement node.
     * @param node: The node to be replaced.
     * @param replacementNode: The node we are replacing with.
     */
    private void transplant(RBNode node, RBNode replacementNode) {
        if (node.isRoot()) {
            setRoot(replacementNode);       //If the root is null, then we set the node that's replacing this node as root.
        } else {
            if (node == node.parent.right) {
                node.parent.right = replacementNode;        //If the node is right child of it's parent, then update the right child of it's parent with replacement node.
            } else {
                node.parent.left = replacementNode;     //If the node is left child of it's parent, then update the left child of it's parent with replacement node.
            }
        }
        if (replacementNode != null) {
            replacementNode.parent = node.parent;       //We set the parent of the replacement node to the parent of the node.
        }
    }

    /**
     * To insert a new building into the Red-Black tree.
     * @param buildNum: Building number of a newly added building.
     * @param totalTime: Total time needed for the construction of new building.
     */
    public RBNode addNode(int buildNum, int totalTime){
        RBNode afterBSTInsert = insertBST( root, new RBNode(buildNum,totalTime));
        if(afterBSTInsert!=null)        // If the given node already exists in the tree we need not perform further operations on that node.
            rbInsertFixup(afterBSTInsert);      //After inserting the node in the tree, we perform fixup operations to fix the red-black property of the tree.
        return afterBSTInsert;
    }

    /**
     * To delete a building from red-black tree.
     * @param buildNum: Building number of the node to be deleted.
     * @return: True if node is deleted successfully.
     */
    public boolean remove(int buildNum){
        return deleteBST(buildNum);
    }

    /**
     * TO insert the new node into an appropriate position in the tree and set it's parent.
     * @param parent: root of the tree
     * @param node: new Node
     * @return: new Node inserted in appropriate position with it's parent set.
     */
    private RBNode insertBST(RBNode parent, RBNode node){
        if(parent == null){
            node.setBlack();
        }else{
            if(node.buildNum == parent.buildNum){
                return null;
            }
            else if(node.buildNum < parent.buildNum){       //If the building number is less than the node, we traverse through the left child of the node.
                if(parent.left==null){      //If left child is null, then we make new node as left child.
                    parent.left = node;
                    node.parent = parent;
                } else{
                    node = insertBST(parent.left, node);
                }
            }else{          //If the building number is greater than the node, we traverse through the right child of the node.
                if(parent.right==null){     //If left child is null, then we make new node as left child.
                    parent.right = node;
                    node.parent = parent;
                } else{
                    node = insertBST(parent.right, node);
                }
            }
        }
        return node;
    }

    /**
     * To perform Red-Black tree validations on the newly inserted node and make necessary changes such that it obeys Red-Black tree properties.
     * @param node: The newly inserted node.
     */
    public void rbInsertFixup(RBNode node){
        if(root == null){
            setRoot(node);
        }
        else
            insertNewNode(node);
    }

    /**
     * To assign color to the newly inserted node and perform the required operations.
     * @param node: newly inserted node
     */
    public void insertNewNode(RBNode node){
        if(node.isRoot()){      //If the newly inserted node is root, we set it's color as black.
            node.setBlack();
        }
        else{
            node.setRed();      //For any newly inserted node other than the root, we make it's color as red.
            insertIsParentRed(node);
        }
    }

    /**
     * To check if the parent of newly inserted node is Black. If yes, we need not make any changes, else we need to make appropriate changes.
     * @param node: newly inserted node.
     */
    public void insertIsParentRed(RBNode node){
        if(node.parent.isRed())
            insertWithRedParent(node);
    }

    /**
     * If the parent of the node is Red, we check for the color of uncle node. If it's red, we recolor the parent and the uncle. Then we recolor the grandparent and recheck.
     * @param node: newly inserted node
     */
    public void insertWithRedParent(RBNode node){
        RBNode grandparent = node.getGrandparent();
        RBNode uncle = node.getUncle();
        if(uncle != null && uncle.isRed()){     //We recolor the uncle and parent of the node and re-propagate the grandparent.
            grandparent.setRed();
            uncle.setBlack();
            node.parent.setBlack();
            insertNewNode(grandparent);
        }else
            insertWithBlackUncle(node);
    }

    /**
     * If the parent in Red and uncle is Black/Null, then we perform the required rotations. In this method we perform RL and LR cases.
     * @param node: newly inserted node.
     */
    public void insertWithBlackUncle(RBNode node){
        RBNode grandparent = node.getGrandparent();

        if (isRightChild(node) && node.parent == grandparent.left) {        //We perform left rotation when the node and parent are both left children.
            leftRotate(node.parent);
            node = node.left;
        } else if (isLeftChild(node) && node.parent == grandparent.right) {     //We perform right rotation when the node and parent are both right children.
            rightRotate(node.parent);
            node = node.right;
        }
        insertWithBlackUncleLLAndRRRotations(node);
    }

    /**
     * If the parent is Red and uncle is Black/Null, then we perform the required rotations. In this method we perform RR and LL cases.
     * @param node: newly inserted node.
     */
    public void insertWithBlackUncleLLAndRRRotations(RBNode node){
        RBNode grandparent = node.getGrandparent();
        grandparent.setRed();
        node.parent.setBlack();
        if (isLeftChild(node) && node.parent == grandparent.left) {     //If the node is left Child and it's parent is also left child, we perform right rotation.
            rightRotate(grandparent);
        } else if (isRightChild(node) && node.parent == grandparent.right) {       //If the node is right Child and it's parent is also right child, we perform left rotation.
            leftRotate(grandparent);
        }
    }

    /**
     * We remove the node with given building number from the tree.
     * 1. If the node has both and left and right children, then we replace it with max-predecessor.
     * 2. If either left/right child is null, we replace it with the non-null child.
     * 3. If both children are null, we then mark the node for deletion.
     * While copying the node, we also copy the color of the replacing node.
     * @param buildNum: Building number of the node to be deleted.
     * @return: Returns true if the delete operation is successful.
     */
    public boolean deleteBST(int buildNum){
        RBNode node = findNode(buildNum);
        if(node!=null){
            if ( node.right!=null && node.left!=null ) {    //If the node is not leaf node, we cannot directly delete it. Hence, we replace it with a max predecessor node and delete that node.
                node = replaceWithMaxPredecessor(node);
            }
            RBNode child;
            if(node.right == null)      //If one of the children of the node is null, we then replace the node with the non-null node.
                 child = node.left;
            else
                 child = node.right;

            if (node.isBlack()) {
                if (!isBlackOrNull(child)) {
                    node.setRed();
                }
                rbDeleteFixup(node);        //We perform the fix-up operations to satisfy Red-black property of the tree.
            }
            transplant(node, child);        //Copy the child to node.
        }
        return node!=null;
    }


    /**
     * To fix-up the Red-Black tree property after deleting the node from Red-Black tree
     * @param node: The node that's getting deleted.
     *            If the node getting deleted is root we just continue.
     */
    private void rbDeleteFixup(RBNode node) {
        if (node.parent != null) {      //If the node is root we directly delete it.
            deleteWithRedSibling(node);
        }
    }

    /**
     * The node getting deleted has a Red sibling
     * @param node: The node that's getting deleted.
     *            In this case, we change the color of Sibling to Black and parent to Red and rotate the parent towards the deleted node.
     */
    private void deleteWithRedSibling(RBNode node) {
        RBNode nodeSibling = node.getSibling();
        if (isRedAndNotNull(nodeSibling)) {
            nodeSibling.setBlack();     //We swap the colors of parent and sibling,
            node.parent.setRed();
            if (isLeftChild(node)) {        //We rotate the node's parent towards node, by doing based on if the node is left or right child of it's parent.
                leftRotate(node.parent);
            } else {
                rightRotate(node.parent);
            }
        }
        deleteWithParentAndSiblingAndSiblingChildrenBlack(node);
    }

    /**
     * The node getting deleted has black parent, black sibling and even the children of the sibling are black.
     * @param node: The node that's getting deleted.
     *            In this case, we change the color of the sibling to red and apply the Red-Black fix-up operation on the parent of the node.
     */
    private void deleteWithParentAndSiblingAndSiblingChildrenBlack(RBNode node) {
        RBNode nodeSibling = node.getSibling();
        if (node.parent.isBlack() &&
                nodeSibling != null &&
                isBlackOrNull(nodeSibling) &&
                isBlackOrNull(nodeSibling.left) &&
                isBlackOrNull(nodeSibling.right)) {
            nodeSibling.setRed();       //We change the color of sibling to red.
            rbDeleteFixup(node.parent);     //We now perform fix-up operation on the node's parent.
        } else {
            deleteWithRedParentAndBlackSibling(node);
        }
    }

    /**
     * The node getting deleted had Red parent and Black sibling.
     * @param node: The node that's getting deleted.
     *            In this case, we change the color of the parent to Black and the Sibling to Red.
     */
    private void deleteWithRedParentAndBlackSibling(RBNode node) {
        RBNode nodeSibling = node.getSibling();
        if (node.parent.isRed() &&
                nodeSibling != null &&
                isBlackOrNull(nodeSibling) &&
                isBlackOrNull(nodeSibling.left) &&
                isBlackOrNull(nodeSibling.right)) {
            node.parent.setBlack();     //We swap the colors of parent and sibling.
            nodeSibling.setRed();
        } else {
            deleteWithNearSiblingChildRed(node);
        }
    }

    /**
     * The node getting deleted has Black parent, black sibling and the sibling has the child which is near to the deleted node of red color.
     * @param node: The node that's getting deleted.
     *            In this case, we change the color of the child of sibling to Black and sibling to Red and rotate the sibling away from the node getting deleted.
     */
    private void deleteWithNearSiblingChildRed(RBNode node) {
        RBNode nodeSibling = node.getSibling();
        if (isLeftChild(node) &&
                nodeSibling != null &&
                nodeSibling.isBlack() &&
                isRedAndNotNull(nodeSibling.left) &&
                isBlackOrNull(nodeSibling.right)) {
            nodeSibling.setRed();
            if (nodeSibling.left != null) nodeSibling.left.setBlack();
            rightRotate(nodeSibling);       //We rotate the sibling of node to right, if node is left child of it's parent.
        } else if (isRightChild(node) &&
                nodeSibling != null &&
                isBlackOrNull(nodeSibling) &&
                isBlackOrNull(nodeSibling.left) &&
                isRedAndNotNull(nodeSibling.right)) {
            nodeSibling.setRed();
            if (nodeSibling.right != null) nodeSibling.right.setBlack();
            leftRotate(nodeSibling);         //We rotate the sibling of node to left, if node is right child of it's parent.
        }
        deleteWithFarSiblingChildRed(node);
    }

    /**
     * The node getting deleted has Black parent, black sibling and the sibling has the child which is far away from the deleted node and has red color.
     * @param node: The node that's getting deleted.
     *            In this case, we change the color of the child of sibling to Black and rotate the parent towards the node that's getting deleted.
     */
    private void deleteWithFarSiblingChildRed(RBNode node) {
        RBNode nodeSibling = node.getSibling();
        replaceColor(nodeSibling, node.parent);
        setBlackIfNotNull(node.parent);
        if (isLeftChild(node)) {
            setBlackIfNotNull(nodeSibling.right);
            leftRotate(node.parent);        //If the node is left child of it's parent, we rotate the parent towards left.
        } else {
            setBlackIfNotNull(nodeSibling.left);
            rightRotate(node.parent);       //If the node is right child of it's parent, we rotate the parent towards right.
        }
    }

    /**
     * To copy the value of the max predecessor to the given node.
     * @param node: The node to which we want to find the max-predecessor and replace it's values by max-predecessor.
     * @return: returns the max predecessor node which should now be deleted.
     */
    private RBNode replaceWithMaxPredecessor(RBNode node) {
        RBNode predecessor = maxPredecessor(node);
        node.buildNum = predecessor.buildNum;       //We copy all the fields of the node with the values of max-predecessor.
        node.totalTime = predecessor.totalTime;
        node.minHeapNode = predecessor.minHeapNode;
        node.execTime = predecessor.execTime;
        return predecessor;
    }

    /**
     * To find the max predecessor for a given node, so that we can replace it when we delete it.
     * @param node: Node whose max predecessor is required.
     * @return: Max predecessor of the given node.
     */
    private RBNode maxPredecessor(RBNode node) {
        node = node.left;       //We move by one edge to left and continuously traverse through the right edges.
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    /**
     * To swap the color of given node with that of the replacement node.
     * @param node: The node whose color is to be replaced.
     * @param replacement: The node with whose color we should replace.
     */
    private void replaceColor(RBNode node, RBNode replacement){
        if(node!=null&&replacement!=null)
            node.color=replacement.color;
    }

    /**
     * Function to print the building node tuple of a given building number.
     * @param buildNum: Building number
     * @return: String consisting of the building details tuple.
     */
    public String print(int buildNum){
        RBNode temp = findNode(buildNum);       //We find the node in RBTree and print it.
        return temp!=null ? "("+ temp.buildNum +", "+temp.getExecTime()+", "+temp.totalTime+")" : "(0,0,0)";
    }

    /**
     * Function to print the building node tuples between a set of building numbers.
     * @param buildNum1: First building number.
     * @param buildNum2: Second building number.
     * @return: String containing the building tuples to be printed.
     */
    public String print(int buildNum1, int buildNum2){
        StringBuilder ans = new StringBuilder("");
        printRange(root, buildNum1, buildNum2, ans);        //We find the set of nodes in RBTree and print it.
        return ans.length() ==0 ? "(0,0,0)" : ans.toString();
    }

    /**
     * An util function to print the building nodes between a set of building numbers.
     * @param root: root of the red-black tree
     * @param buildNum1: First building number.
     * @param buildNum2: Second building number.
     * @param ans: The string to which we append the values of building nodes.
     */
    public void printRange(RBNode root, int buildNum1, int buildNum2, StringBuilder ans){

        if(root==null)
            return;

        if(buildNum1<root.buildNum)     //If the building-Number 1 is less than current node, we further traverse left.
            printRange(root.left,buildNum1,buildNum2,ans);
        if(root.buildNum>=buildNum1&&root.buildNum<=buildNum2){     //If the building number lies in the range, then we append it to the string.
            if(ans.length()!=0)
                ans.append("," + "("+ root.buildNum +", "+root.getExecTime()+", "+root.totalTime+")" );
            else
                ans.append("("+ root.buildNum +", "+root.getExecTime()+", "+root.totalTime+")" );
        }
        if(buildNum2>root.buildNum)     //If the building-Number 2 is greater than current node, we further traverse right.
            printRange(root.right,buildNum1,buildNum2,ans);
    }

    /**
     * To check if the Red-Black is empty.
     * @return: True if the tree is empty else returns false.
     */
    public boolean isEmpty(){
        return root==null;
    }

}
