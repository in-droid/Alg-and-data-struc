package izziv1;

class ArrayDeque<T> implements Deque<T>, Stack<T>, Sequence<T> {
    private static final int DEFAULT_CAPACITY = 64;
    private T[] a;
    private int front, back, size;

    public ArrayDeque() {
        a = (T[]) (new Object[DEFAULT_CAPACITY]);
        front = 0;
        back = 0;
        size = 0;
    }

    private int next(int i) {
        return (i + 1) % DEFAULT_CAPACITY;
    }

    private int prev(int i) {
        return (DEFAULT_CAPACITY + i - 1) % DEFAULT_CAPACITY;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean isFull() {
        return size == DEFAULT_CAPACITY;
    }

    @Override
    public int size() {
        return size;
    }


    @Override
    public String toString() {
        StringBuffer rez = new StringBuffer();
        rez.append("[");
        if (size > 0) {
            rez.append(a[front].toString());
            for (int i = 0; i < size - 1; i++) {
                rez.append("," + a[next(front + i)].toString() + "");
            }
            rez.append("]");
        }
        return rez.toString();
    }

    @Override
    public void push(T x) throws CollectionException {
        if (isFull()) {
            throw new CollectionException(ERR_MSG_FULL);
        } else {
            a[back] = x;
            back = next(back);
            size++;

        }
    }

    @Override
    public T top() throws CollectionException {
        if (isFull()) {
            throw new CollectionException(ERR_MSG_FULL);
        } else {
            return a[prev(back)];
        }
    }

    @Override
    public T pop() throws CollectionException {
        if (isEmpty()) {
            throw new CollectionException(ERR_MSG_EMPTY);
        } else {
            back = prev(back);
            T o = a[back];
            a[back] = null;
            size--;
            return o;
        }
    }


    @Override
    public T front() throws CollectionException {
        if (isEmpty()) {
            throw new CollectionException(ERR_MSG_EMPTY);
        }
        return a[front];
    }

    @Override
    public T back() throws CollectionException {
        return top();
    }

    @Override
    public void enqueue(T x) throws CollectionException {
        push(x);

    }

    @Override
    public void enqueueFront(T x) throws CollectionException {
        if (isFull()) {
            throw new CollectionException(ERR_MSG_FULL);
        } else {
            front = prev(front);
            a[front] = x;
            size++;
        }

    }

    @Override
    public T dequeue() throws CollectionException {
        if (isEmpty()) {
            throw new CollectionException(ERR_MSG_EMPTY);
        } else {
            T o = a[front];
            a[front] = null;
            front = next(front);
            size--;
            return o;
        }
    }

    @Override
    public T dequeueBack() throws CollectionException {
        return pop();
    }

    private int index(int i){
        return (front + i) % DEFAULT_CAPACITY;
    }

    @Override
    public T get(int i) throws CollectionException {
        if(isEmpty()){
            throw new CollectionException(ERR_MSG_EMPTY);
        }
        if(i < 0 || i >= size){
            throw new CollectionException(ERR_MSG_INDEX);
        }
        return a[index(i)];
    }

    @Override
    public void add(T x) throws CollectionException {
        push(x);
    }
}
