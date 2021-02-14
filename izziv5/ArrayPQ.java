package izziv5;


public class ArrayPQ<T extends Comparable> extends AbstractArrayPQ<T> {

     public ArrayPQ(){
         super();
     }


    @Override
    public T front() throws CollectionException {
        if(isEmpty()){
            throw new CollectionException(ERR_MSG_EMPTY);
        }
        else {
            T max = a[0];
            for (int i = 1; i < size; i++) {
                comparisons++;
                if(a[i].compareTo(max) >= 0){
                    max = a[i];
                }
            }
            return max;
        }
    }

    @Override
    public void enqueue(T x) {
         if(size == capacity){
             resize();
         }
         a[size] = x;
         //last++;
         size++;
    }

    @Override
    public T dequeue() throws CollectionException {
       if(isEmpty()){
           throw new CollectionException(ERR_MSG_EMPTY);
       }
       else {

           if(size <= capacity / 3){
               resize();
           }

           T max = a[0];
           int index = 0;
           for (int i = 1; i < size; i++) {
               comparisons++;
               if(a[i].compareTo(max) >= 0){
                   max = a[i];
                   index = i;
               }
           }
           swap(index, size - 1);
           a[size - 1] = null;
           size--;
           return max;
       }
    }


}

