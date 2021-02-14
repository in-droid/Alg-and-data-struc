package izziv5;

public class ArrayHeapPQ <T extends Comparable> extends AbstractArrayPQ<T>  {

    public ArrayHeapPQ(){
        super();
    }

    private void siftUp(int c){
        int p;
        while(c > 0){
            p = (c - 1) / 2;
            comparisons++;
            if(a[p].compareTo(a[c]) >= 0){
                break;
            }
            swap(p, c);
            c = p;
        }
    }

    private void siftDown(int p){
        int c = 2 * p + 1;
        while (c <= size - 1) {
            comparisons++;
            if((c + 1 <= size -1) && a[c + 1].compareTo(a[c]) > 0){
                c++;
            }
            comparisons++;
            if(a[p].compareTo(c) >=0)
                break;
            swap(p, c);
            p = c; c = 2 * p + 1;
        }

    }


    @Override
    public T front() throws CollectionException {
        if(isEmpty())
            throw new CollectionException(ERR_MSG_EMPTY);
        return a[0];
    }

    @Override
    public void enqueue(T x) {
        if(size == capacity){
            resize();
        }
        a[size] = x;
        size++;
        siftUp(size - 1);
    }

    @Override
    public T dequeue() throws CollectionException {
        if(isEmpty())
            throw new CollectionException(ERR_MSG_EMPTY);
        if(size <= capacity / 3){
            resize();
        }
        T x = a[0];
        swap(0, size - 1);
        a[size - 1] = null;
        size--;
        siftDown(0);
        return x;
    }
}
