package izziv2;

public class LinkedSequence<T> implements Sequence<T> {
    class Node {
        T value;
        Node next, prev;

        Node(T value, Node next, Node prev){
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
        Node(){
            this.value = null;
            this.next = null;
            this.prev = null;
        }
    }
    private Node first;
    private int size;

    LinkedSequence(){
        this.first = null;
        this.size = 0;
    }

    @Override
    public String toString(){
        StringBuffer rez = new StringBuffer();
        rez.append("[");
        if (size > 0) {
            Node p = first;
            rez.append(p.value);
            p = p.next;
            while (p != null){
                rez.append("; ").append(p.value);
                p = p.next;
            }
            rez.append("]");
        }
        return rez.toString();

    }

    private Node index(int i) throws CollectionException {
        Node rez = first;
        if(isEmpty()){
            throw new CollectionException(ERR_MSG_EMPTY);

        }
        if(i < 0 || i > size - 1){
            throw new CollectionException(ERR_MSG_INDEX);
        } else{
            for (int j = 0; j < i; j++) {
                rez = rez.next;
            }
        }
        return rez;
    }

    @Override
    public T get(int i) throws CollectionException {
        Node rez = index(i);
        return rez.value;
    }

    private Node getLast() throws CollectionException {
        if(isEmpty())
            throw new CollectionException(ERR_MSG_EMPTY);
        Node p = first;
        while (p.next != null)
            p = p.next;
        return p;
    }

    @Override
    public void add(T x) throws CollectionException {
        if(this.isEmpty()){
            first = new Node(x, null, null);
        } else {
            Node last = getLast();
            last.next = new Node(x, null, last);
        }
        size++;

    }

    @Override
    public void insert(int i, T x) throws CollectionException {
            if((isEmpty() && i== 0) || i == size)
                add(x);
            else if( i == 0){
                first = new Node(x, first, null);
                first.next.prev = first;
                size ++;
            } else {
                Node newNode = new Node();
                newNode.value = x;
                Node curr = index(i);
                curr.prev.next = newNode;
                newNode.prev = curr.prev;
                newNode.next = curr;
                curr.prev = newNode;
                size ++;
            }

    }

    @Override
    public T set(int i, T x) throws CollectionException {
        Node node = index(i);
        T o = node.value;
        node.value = x;
        return o;
    }

    @Override
    public T delete(int i) throws CollectionException {
        T rez;
        if(isEmpty()){
            throw new CollectionException(ERR_MSG_EMPTY);
        } else if(i == 0){
            rez = first.value;
            first = first.next;
            first.prev = null;
            size --;
        } else if(i == size - 1){
            Node temp = getLast();
            rez = temp.value;
            temp.prev.next = null;
            temp.prev = null;
            size --;
        } else {
            Node temp = index(i);
            temp.prev.next = temp.next;
            temp.next.prev = temp.prev;
            temp.next = null;
            temp.prev = null;
            rez = temp.value;
            size --;
        }
        return  rez;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }


}