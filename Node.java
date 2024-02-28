
/**
 * a node class with a parent, rightChild and leftChild pointer Node and data of type T
 */
public class Node <T extends Comparable<T>> {

    public T data = null;
    public Node<T> parent;
    public Node<T> rightChild;
    public Node<T> leftChild;

    public Node(T data){
        this.data = data;
        this.rightChild = null;
        this.leftChild = null;
    }

    public T getData(){
        return data;
    }

    public Node<T> getRightChild(){
        return rightChild;
    }
    public Node<T> getLeftChild(){
        return leftChild;
    }
    public Node<T> getParent(){return parent;}
    public void setRightChild(Node<T> node) {
        rightChild = node;
    }
    public void setLeftChild(Node<T> node) {
        leftChild = node;
    }
    public void setParent(Node<T> node) {
        parent = node;
    }
    public void setData(T item) {
        data = item;
    }
}
