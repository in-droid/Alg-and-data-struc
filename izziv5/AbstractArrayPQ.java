package izziv5;

abstract class AbstractArrayPQ<T extends Comparable> implements PriorityQueue<T> {
    static int DEFAULT_CAPACITY = 64;
    int size, capacity, last;
    T[] a;

    int comparisons, movements;
    public AbstractArrayPQ() {
        a = (T[]) (new Comparable[DEFAULT_CAPACITY]);
        size = 0;
        last = 0;
        capacity = DEFAULT_CAPACITY;
        comparisons = 0;
        movements = 0;

    }
    public int getComparisons() {
        return comparisons;
    }

    public int getMovements() {
        return movements;
    }
    void resize(){
        capacity = 2 * size();
        T[] temp = (T[]) (new Comparable[capacity]);
        for (int i = 0; i < size; i++) {
            temp[i] = a[i];
            movements++;
        }
        a = temp;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }
    @Override
    public String toString() {
        StringBuilder rez = new StringBuilder();
        rez.append("[");
        if (size > 0) {

            rez.append(a[0].toString());
            for (int i = 1; i < size; i++) {
                rez.append(",").append(a[i].toString());
            }
        }
        rez.append("]");
        return rez.toString();
    }

    void swap(int i, int j){
        T temp = a[i];
        a[i] = a[j];
        a[j] = temp;
        movements +=3;
    }
}
