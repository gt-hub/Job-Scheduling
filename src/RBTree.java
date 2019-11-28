import java.util.LinkedList;
import java.util.Queue;

public class RBTree {

    RBNode root;

    /**
     * To set the root of RBTree.
     * @param node: The node to be set as the root.
     */
    public void setRoot(RBNode node){
        root = node;
        if(node!=null)
            node.setBlack();
    }

    public boolean isBlackOrNull(RBNode node){
        return node==null||node.isBlack();
    }

    public boolean isRedAndNotNull(RBNode node){
        return node!=null && node.isRed();
    }

    public void setBlackIfNotNull(RBNode node){
        if(node!=null)
            node.setBlack();
    }

    /**
     * TO find a node of the given building-Number in the Tree.
     * @param buildNum: Building-Number of the node to be discovered.
     * @return: Node with the given building number
     */
    public RBNode findNode(int buildNum) {
        RBNode node = root;
        while (node != null) {
            if (node.buildNum == buildNum) {
                return node;
            } else if (buildNum<node.buildNum) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return null;
    }

    /**
     * To rotate a given node towards left
     * @param node: Node to be rotated left.
     */
    private void rotateLeft(RBNode node) {
        if (node != null) {
            RBNode right = node.right;
            replace(node, right);
            node.right = right == null ? null : right.left;
            if (right != null) {
                if (right.left != null) {
                    right.left.parent = node;
                }
                right.left = node;
            }
            node.parent = right;
        }
    }

    /**
     * To rotate a given node towards right.
     * @param node: Node to be rotated right.
     */
    private void rotateRight(RBNode node) {
        if (node != null) {
            RBNode left = node.left;
            replace(node, left);
            node.left = left == null ? null : left.right;
            if (left != null) {
                if (left.right != null) {
                    left.right.parent = node;
                }
                left.right = node;
            }
            node.parent = left;
        }
    }

    /**
     * To replace the given node with a replacement node.
     * @param node: The node to be replaced.
     * @param replacement: The node we are replacing with.
     */
    private void replace(RBNode node, RBNode replacement) {
        if (node.isRoot()) {
            setRoot(replacement);
        } else {
            if (node == node.parent.left) {
                node.parent.left = replacement;
            } else {
                node.parent.right = replacement;
            }
        }
        if (replacement != null) {
            replacement.parent = node.parent;
        }
    }

    /**
     * To insert a new building into the Red-Black tree.
     * @param buildNum: Building number of a newly added building.
     * @param totalTime: Total time needed for the construction of new building.
     */
    public RBNode addNode(int buildNum, int totalTime){
        RBNode afterBSTInsert = insertBST( root, new RBNode(buildNum,totalTime));
        if(afterBSTInsert!=null)// If the given node already exists in the tree we need not perform further operations on that node.
            rbInsert(afterBSTInsert);
        return afterBSTInsert;
    }


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
            else if(node.buildNum < parent.buildNum){
                if(parent.left==null){
                    parent.left = node;
                    node.parent = parent;
                } else{
                    node = insertBST(parent.left, node);
                }
            }else{
                if(parent.right==null){
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
    public void rbInsert(RBNode node){
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
        if(node.isRoot()){
            node.setBlack();
        }
        else{
            node.setRed();
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
        if(uncle != null && uncle.isRed()){
            node.parent.setBlack();
            uncle.setBlack();
            grandparent.setRed();
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

        if (node == node.parent.right && node.parent == grandparent.left) {
            rotateLeft(node.parent);
            node = node.left;
        } else if (node == node.parent.left && node.parent == grandparent.right) {
            rotateRight(node.parent);
            node = node.right;
        }
        insertWithBlackUncleLLAndRRRotations(node);
    }

    /**
     * If the parent in Red and uncle is Black/Null, then we perform the required rotations. In this method we perform RR and LL cases.
     * @param node: newly inserted node.
     */
    public void insertWithBlackUncleLLAndRRRotations(RBNode node){
        RBNode grandparent = node.getGrandparent();
        node.parent.setBlack();
        grandparent.setRed();
        if (node == node.parent.left && node.parent == grandparent.left) {
            rotateRight(grandparent);
        } else if (node == node.parent.right && node.parent == grandparent.right) {
            rotateLeft(grandparent);
        }
    }

    public boolean deleteBST(int buildNum){
        RBNode node = findNode(buildNum);
        if(node!=null){
            if ( node.left!=null && node.right!=null ) {
                node = copyMaxPredecessor(node);
            }
            RBNode child = node.right==null ? node.left : node.right;
            if (node.isBlack()) {
                if (!isBlackOrNull(child)) {
                    node.setRed();
                }
                deleteInRootNodeCase(node);
            }
            replace(node, child);
        }
        return node!=null;
    }

    private void deleteInRootNodeCase(RBNode node) {
        if (node.parent != null) {
            deleteWithRedSibling(node);
        }
    }

    private void deleteWithRedSibling(RBNode node) {
        RBNode sibling = node.sibling();
        if (isRedAndNotNull(sibling)) {
            node.parent.setRed();
            sibling.setBlack();
            if (node == node.parent.left) {
                rotateLeft(node.parent);
            } else {
                rotateRight(node.parent);
            }
        }
        deleteWithParentAndSiblingAndSiblingChildrenBlack(node);
    }

    private void deleteWithParentAndSiblingAndSiblingChildrenBlack(RBNode node) {
        RBNode sibling = node.sibling();
        if (node.parent.isBlack() &&
                sibling != null &&
                isBlackOrNull(sibling) &&
                isBlackOrNull(sibling.left) &&
                isBlackOrNull(sibling.right)) {
            sibling.setRed();
            deleteInRootNodeCase(node.parent);
        } else {
            deleteWithRedParentAndBlackSibling(node);
        }
    }

    private void deleteWithRedParentAndBlackSibling(RBNode node) {
        RBNode sibling = node.sibling();
        if (node.parent.isRed() &&
                sibling != null &&
                isBlackOrNull(sibling) &&
                isBlackOrNull(sibling.left) &&
                isBlackOrNull(sibling.right)) {
            sibling.setRed();
            node.parent.setBlack();
        } else {
            deleteWithNearSiblingChildRed(node);
        }
    }

    private void deleteWithNearSiblingChildRed(RBNode node) {
        RBNode sibling = node.sibling();
        if (node == node.parent.left &&
                sibling != null &&
                sibling.isBlack() &&
                isRedAndNotNull(sibling.left) &&
                isBlackOrNull(sibling.right)) {
            sibling.setRed();
            if (sibling.left != null) sibling.left.setBlack();
            rotateRight(sibling);
        } else if (node == node.parent.right &&
                sibling != null &&
                isBlackOrNull(sibling) &&
                isBlackOrNull(sibling.left) &&
                isRedAndNotNull(sibling.right)) {
            sibling.setRed();
            if (sibling.right != null) sibling.right.setBlack();
            rotateLeft(sibling);
        }
        deleteWithFarSiblingChildRed(node);
    }

    private void deleteWithFarSiblingChildRed(RBNode node) {
        RBNode sibling = node.sibling();
        replaceColor(sibling, node.parent);
        setBlackIfNotNull(node.parent);
        if (node == node.parent.left) {
            setBlackIfNotNull(sibling.right);
            rotateLeft(node.parent);
        } else {
            setBlackIfNotNull(sibling.left);
            rotateRight(node.parent);
        }
    }

    private RBNode copyMaxPredecessor(RBNode node) {
        RBNode predecessor = maxPredecessor(node);
        node.buildNum = predecessor.buildNum;
        node.totalTime = predecessor.totalTime;
        node.minHeapNode = predecessor.minHeapNode;
        node.execTime = predecessor.execTime;
        //node.minPointer = predecessor.minPointer;
        return predecessor;
    }

    private RBNode maxPredecessor(RBNode node) {
        node = node.left;
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    private void replaceColor(RBNode node, RBNode replacement){
        if(node!=null&&replacement!=null)
            node.color=replacement.color;
    }

    public String print(int buildNum){
        RBNode temp = findNode(buildNum);
        return temp!=null ? "("+ temp.buildNum +", "+temp.getExecTime()+", "+temp.totalTime+")" : "(0,0,0)";
    }


    public String print(int buildNum1, int buildNum2){
        StringBuilder ans = new StringBuilder("");
        printRange(root, buildNum1, buildNum2, ans);
        return ans.length() ==0 ? "(0,0,0)" : ans.toString();
    }

    public void printRange(RBNode root, int buildNum1, int buildNum2, StringBuilder ans){

        if(root==null)
            return;

        if(buildNum1<root.buildNum)
            printRange(root.left,buildNum1,buildNum2,ans);
        if(root.buildNum>=buildNum1&&root.buildNum<=buildNum2){
            if(ans.length()!=0)
                ans.append("," + "("+ root.buildNum +", "+root.getExecTime()+", "+root.totalTime+")" );
            else
                ans.append("("+ root.buildNum +", "+root.getExecTime()+", "+root.totalTime+")" );
        }
        if(buildNum2>root.buildNum)
            printRange(root.right,buildNum1,buildNum2,ans);
    }

    public void print(){
        System.out.println("The root is:"+root.buildNum);
        //printInorder(root);

        Queue<RBNode> q = new LinkedList<>();
        q.add(root);
        while(q.size()!=0){
            int len = q.size();
            while(len-->0){
                RBNode temp = q.poll();
                System.out.print(temp.buildNum+": "+temp.color+"   ");
                if(temp.left!=null)
                    q.add(temp.left);
                if(temp.right!=null)
                    q.add(temp.right);
            }
            System.out.println("");
        }
    }

    public boolean isEmpty(){
        return root==null;
    }

    public void printInorder(RBNode root){
        if(root==null)
            return;
        printInorder(root.left);
        System.out.println(root.buildNum + " " + root.color+ " "+ root.totalTime+" "+root.getExecTime());
        printInorder(root.right);
    }

}
