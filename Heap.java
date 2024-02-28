/**
 * A data structure that represents a max heap.
 * @param <T> : The type of item stored in the heap. It must implement
 *           the Comparable<T> interface, so that the data structure
 *           knows how to compare two items.
 *           Note: String already implements Comparable.
 */
public class Heap<T extends Comparable<T>> {

    private Node<T> rootNode;
    private Node<T> lastNode;
    private int currentSize;

    /**
     * Construct a max heap that is initially empty.
     */
    public Heap() {
        rootNode = null;
        lastNode = null;
        currentSize = 0;
    }

    /**
     * Returns the maximum item in the heap.
     * @return : The maximum item in the heap.
     * @precondition : Heap is not empty.
     */
    public T findMax() throws Exception {
        // if the heap has no nodes/is empty throw an exception
        if (isEmpty()) {
            throw new Exception();
        }

        return rootNode.getData();
    }


    /**
     * Removes the maximum item from this heap.
     * @return : The maximum item, which was removed.
     * @precondition : Heap is not empty.
     */
    public T extractMax() throws Exception {
        // if the heap is empty then throw an exception
        if (isEmpty()) {
            throw new Exception();
        }

        T max = findMax(); // find the maximum value

        // if there is only one node then set the root and last node to null (empty heap)
        if (size() == 1) {
            rootNode = null;
            lastNode = null;
        }
        else {
            Node<T> nextLast = getNewLastNode(); // get the node that will be last next

            // if last node is the left child set that child to null
            if (lastNode.getParent().getLeftChild() == lastNode) {
                lastNode.getParent().setLeftChild(null);
            }
            // Otherwise, set the right child to null
            else {
                lastNode.getParent().setRightChild(null);
            }
            rootNode.setData(lastNode.getData()); // set the root data to the previous last node
            lastNode = nextLast; // set the new last node

            heapifyRemove(); // re-order the heap recursively, starting at the rootNode
        }

        currentSize--; // now that the removal has occurred subtract 1 from the heap's size

        return max;

    }

    /**
     * Reorders the heap after removing the root element.
     */
    private void heapifyRemove() throws InterruptedException {
        heapifyRemove(rootNode);
    }

    /**
     * Reorders the heap after removing the root element
     * @param node
     * @throws InterruptedException
     */
    private void heapifyRemove(Node<T> node) throws InterruptedException {
        // if the node currently being checked is null return
        if (node == null) {
            return;
        }

        Node<T> left = node.getLeftChild();
        Node<T> right = node.getRightChild();
        Node<T> next = null;

        // assign next to the proper node
        if (left != null && right != null) {
            if (left.getData().compareTo(right.getData()) > 0) {
                next = left;
            } else {
                next = right;
            }
        } else if (left != null) {
            next = left;
        }

        // if next is not null AND it's value is greater than the current node's then swap them and then call heapifyRemove again on the node that was just moved downwards
        if (next != null && next.getData().compareTo(node.getData()) > 0) {
            T temp = node.getData();
            node.setData(next.getData());
            next.setData(temp);
            heapifyRemove(next);
        }
    }

    /**
     * Represents the starting case of the recursive function (always starts at the lastNode)
     * @return : the new last node (needed after a removal of the rootNode)
     */
    private Node<T> getNewLastNode() {
        return getNewLastNode(lastNode);
    }

    /**
     * Recursive function that "does the work" for finding the new last node after a removal
     * @param result the current node that is being checked (starts at lastNode)
     * @return :
     */
    private Node<T> getNewLastNode(Node<T> result) {

        // Set result equal to the value gotten from the getParentNode() function
        result = getParentNode(result);

        // if the current node is not equal to the rootNode then set it equal to the left child of its parent (in case it isn't already)
        if (result != rootNode) {
            result = result.getParent().getLeftChild();
        }

        // set the result equal to the rightmost node of the current node (as long as it isn't already there)
        if (result.getRightChild() != null) {
            result = findRightmostNode(result.getRightChild());
        }

        // return the value
        return result;
    }

    /**
     * Helper function for the getNewLastNode function
     * @param node the current node
     * @return : The parent of the new lastNode
     */
    private Node<T> getParentNode(Node<T> node) {
        // IF the node is the rootNode OR it's the right child then return that node
        if (node == rootNode || (node.getParent().getLeftChild() != node)) {
            return node;
        }
        // Otherwise, call the function again on this node's parent
        return getParentNode(node.getParent());
    }

    /**
     * Find the furthest right node from a starting node
     * @param node the position/node to start from
     * @return : the node furthest to the right (that is connected to the initial node)
     */
    private Node<T> findRightmostNode(Node<T> node) {
        if (node.getRightChild() == null) {
            return node;
        }
        return findRightmostNode(node.getRightChild());
    }

    /**
     * Inserts the given item into the Heap.
     * @param item : The item to insert into the heap.
     */
    public void insert(T item) {
        // Create a new node that has the proper value
        Node<T> node = new Node<T>(item);

        // If there is no rootNode then assign this node to the rootNode
        if (rootNode == null) {
            rootNode = node;
            rootNode.setParent(null);
        }
        // Otherwise, find the next appropriate position for the node within the tree and insert it there (must ensure that it remains a complete binary tree)
        else {
            // Find the next parent
            Node<T> nextParent = getNextParent();

            // If there's an open spot as a left child then assign the new node to the left child
            if (nextParent.getLeftChild() == null) {
                nextParent.setLeftChild(node);
                node.setParent(nextParent);
            }
            // Otherwise, assign it to the right child
            else {
                nextParent.setRightChild(node);
                node.setParent(nextParent);
            }
        }
        lastNode = node; // set the most recent node inserted to the lastNode of the heap
        currentSize++; // increment the size of the heap by 1

        heapifyInsert(); // call heapifyInsert to correct the ordering of the heap after the value is inserted to follow the rules of a max heap (each node's parent's value is greater than the node's value)
    }

    /**
     * Returns the node that will be the parent of the new node
     * @return the node that will be the parent of the new node
     */
    private Node<T> getNextParent() {
        // If there is less than 3 levels (height) return the rootNode immediately since it should be the parent
        if (currentSize < 3) {
            return rootNode;
        }
        // Otherwise, call getNextParent() always starting from the lastNode
        return getNextParent(lastNode);
    }

    /**
     * Recursive call that returns the node that will be the parent of the new node
     * @param currNode the node that the function is currently visiting
     * @return the node that will be the parent of the new node
     */
    private Node<T> getNextParent(Node<T> currNode) {
        // If the current node is NOT the rootNode AND it is not the left child then call getNextParent again using this node's parent as the parameter
        if (currNode != rootNode && currNode.getParent().getLeftChild() != currNode) {
            return getNextParent(currNode.getParent());
        }
        // If the current node is not the root node
        if (currNode != rootNode) {
            // Check if the current node's parent node has an open spot in it's right child
            if (currNode.getParent().getRightChild() == null) {
                return currNode.getParent();
            }
            // Otherwise, get the parent node's right child and find the leftmost node of that node
            else {
                currNode = currNode.getParent().getRightChild();
                return findLeftMostNode(currNode);
            }
        }
        // If the current node IS the root node then traverse the tree to find the left most node from the root and make that the next parent
        else {
            currNode = findLeftMostNode(currNode);
        }

        // finally return the node (only reached if the current node was the root node since all others have a return statement)
        return currNode;
    }

    /**
     * Find the node that is furthest to the left from a current spot within the complete binary tree/heap
     * @param node the node to begin at
     * @return : the node that is furthest to the left from the starting node
     */
    private Node<T> findLeftMostNode(Node<T> node) {
        // If the current node's left child is null return the current node
        if (node.getLeftChild() == null) {
            return node;
        }
        // Otherwise, use recursion to continue (this time using the current node's left child)
        return findLeftMostNode(node.getLeftChild());
    }

    /**
     * Correct ordering of the heap when a new element is inserted.  If the lastNode is not null then call the recursive version...starting with lastNode.
     * This represents the starting case.
     */
    private void heapifyInsert() {
        // If the lastNode is not null (no items) AND the lastNode is not the root node (1 item) then call the recursive version of heapifyInsert()...always beginning at the lastNode
        if (lastNode != null && lastNode != rootNode) {
            heapifyInsert(lastNode);
        }
    }

    /**
     * Correct ordering of the heap when a new element is inserted...recursive version
     * @param currNode the node that is currently being checked for re-ordering
     */
    private void heapifyInsert(Node<T> currNode) {
        // If the current node is not the root node, then proceed
        if (currNode != rootNode) {
            Node<T> parent = currNode.getParent();

            // if the current node's parent exists AND it's value is greater than it's parent, then swap the values of the two and call heapifyInsert on the parent to see if it needs to be moved even further up
            if (parent != null && currNode.getData().compareTo(parent.getData()) > 0) {
                T temp = currNode.getData();
                currNode.setData(parent.getData());
                parent.setData(temp);
                heapifyInsert(parent);
            }
        }
    }

    /**
     * Returns whether or not the heap is empty.
     * @return : true if heap is empty and false otherwise.
     */
    public boolean isEmpty() {
        if (rootNode == null) {
            return true;
        }
        return false;
    }

    /**
     * Returns the number of items stored in this heap.
     * @return : the number of items stored in this heap.
     */
    public int size() {
        return currentSize;
    }

    /**
     * Returns the number of levels in this heap.
     * @return : The number of levels in this heap.
     */
    public int height(){
        return height(rootNode);
    }

    /**
     * Returns the number of levels in this heap.
     * @return : The number of levels in this heap.
     */
    private int height(Node<T> node) {
        // Progress down the left side of the heap until the next node would be null (since it represents a complete binary tree doing this will reveal the height of the heap itself)
        if (node.getLeftChild() == null) {
            return 0; // must be 0 in order to return 0 when there is only 1 node
        }
        return height(node.getLeftChild()) + 1;
    }




    // Non-recursive versions of some methods that were used for testing/debugging purposes
//    private void heapifyRemove() {
//        T temp;
//        Node<T> node = rootNode;
//        Node<T> left = node.getLeftChild();
//        Node<T> right = node.getRightChild();
//        Node<T> next;
//
//        if ((left == null) && (right == null)) {
//            next = null;
//        }
//        else if (right == null) {
//            next = left;
//        }
//        else if (left.getData().compareTo(right.getData()) > 0) {
//            next = left;
//        }
//        else {
//            next = right;
//        }
//        temp = node.getData();
//
//        while ((next != null) && (next.getData().compareTo(temp) > 0)) {
//            node.setData(next.getData());
//            node = next;
//            left = node.getLeftChild();
//            right = node.getRightChild();
//
//            if ((left == null) && (right == null)) {
//                next = null;
//            } else if (right == null) {
//                next = left;
//            } else if (left.getData().compareTo(right.getData()) > 0) {
//                next = left;
//            } else {
//                next = right;
//            }
//        }
//        node.setData(temp);
//    }

//    private Node<T> getNewLastNode() {
//        Node<T> result = lastNode;
//
//        while ((result != rootNode) && (result.getParent().getLeftChild() == result)) {
//            result = result.getParent();
//        }
//
//        if (result != rootNode) {
//            result = result.getParent().getLeftChild();
//        }
//
//        while (result.getRightChild() != null) {
//            result = result.getRightChild();
//        }
//
//        return result;
//    }
}


