package izziv5;

public class LinkedHeapPQ<T extends Comparable> implements PriorityQueue<T> {
    class Node{
        T item;
        Node left, right, parent;

        Node(T item, Node left, Node right, Node parent) {
            this.item = item;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }

    }
    private int size;
    private Node first, last;

    int comparisons, movements;
    public LinkedHeapPQ(){
        size = 0;
        first = null;
        last = null;
        comparisons = 0;
        movements = 0;
    }

    public int getComparisons() {
        return comparisons;
    }

    public int getMovements() {
        return movements;
    }


    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }
    private void siftUp(Node n){
        while (n != first){
            if(n.parent.item.compareTo(n.item) >= 0) {
                comparisons++;
                break;
            }
            swap(n.parent, n);

            /*
            T temp = n.item;
            n.item = n.parent.item;
            n.parent.item = temp;

             */

            n = n.parent;
        }
    }
    private void siftDown(Node p) {
        Node c = p.left;
        while (c != last && c != null){
            if(p.right != null && p.right.item.compareTo(p.left.item) >= 0){
                c = p.right;
                comparisons++;
            }
            if(p.item.compareTo(c.item) >= 0) {
                comparisons++;
                break;
            }
            swap(p,c);
            /*
            T temp = p.item;
            p.item = c.item;
            c.item = temp;
             */
            p = c; c = p.left;
        }

    }

    @Override
    public T front() throws CollectionException {
        return first.item;
    }

    @Override
    public void enqueue(T x) {
        if(isEmpty()){
            first = new Node(x, null, null, null);
            size++;
            last = first;
        }
        else {
            size++;
            String directions = Integer.toBinaryString(size);
             last = traverse(directions);
            last.item = x;
            siftUp(last);
        }
    }
    private Node traverse(String directions) {
        directions = directions.substring(1);
       // System.out.println(directions);
        Node n = first;
        for (int i = 0; i <directions.length() ; i++) {
            if(directions.charAt(i) == '0'){
                if(n.left == null){
                    n.left = new Node(null, null, null, n);
                }
                n = n.left;

            }
            else {
                if(n.right == null){
                    n.right = new Node(null, null, null, n);;
                }
                n = n.right;
            }

        }
        return n;
    }
    private void swap(Node x, Node y){
        T temp = x.item;
        x.item = y.item;
        y.item = temp;
        movements += 3;
    }

    @Override
    public T dequeue() throws CollectionException {
        if (isEmpty())
            throw new CollectionException(ERR_MSG_EMPTY);
        else if(size == 1){
            T x = first.item;
            size--;
            first = null;
            last = null;
            return x;

        }
        else {
            T rez = first.item;
            last = traverse(Integer.toBinaryString(size));
            swap(first, last);

            if(last.parent.left == last){
                last.parent.left = null;
            }
            else {
                last.parent.right = null;
            }

            last = null;
            size--;
            last = traverse(Integer.toBinaryString(size));
            siftDown(first);

            return rez;

        }
    }
    private String preorder(Node n) {
        if(n != null){
            return n.item + " " + preorder(n.left) + " " + preorder(n.right);
        }
        return "";
    }
    public String print(){
        return preorder(this.first);
    }


}
