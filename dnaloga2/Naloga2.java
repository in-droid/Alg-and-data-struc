package dnaloga2;

import java.util.Scanner;

@SuppressWarnings("unchecked")
class CollectionException extends Exception {
    public CollectionException(String msg) {
        super(msg);
    }
}
@SuppressWarnings("unchecked")
class Array {
    private static int DEFAULT_CAPACITY = 64;
    static final String ERR_MSG_INDEX_OUT_OF_BOUNDS = "Index is out of bounds";
   private int size, capacity, last;
   private int[] a;

    public Array() {
        a =  new int[DEFAULT_CAPACITY];
        size = 0;
        last = 0;
        capacity = DEFAULT_CAPACITY;

    }
    /*
    static Array merge(Array a, Array b) throws CollectionException {
        Array rez = new Array();
        for (int i = 0; i < a.size(); i++) {
            rez.append(a.getElement(i));
        }

        for (int i = 0; i < b.size; i++) {
            rez.append(b.getElement(i));
        }
        return rez;

    }
     */

    void resize() {
        capacity = 2 * size();
        int[] temp = new int [capacity];
        for (int i = 0; i < size; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }

    public int size() {
        return size;
    }

    public void append(int x) {
        if(size == capacity){
            resize();
        }
        a[size] = x;
        size++;
    }

    public int getElement(int i) throws CollectionException {
        if(i >= size || i < 0){
            throw new CollectionException(ERR_MSG_INDEX_OUT_OF_BOUNDS);
        }
        else return a[i];
    }

    public void setElement(int i, int el) throws CollectionException {
        if(i >= size || i < 0){
            throw new CollectionException(ERR_MSG_INDEX_OUT_OF_BOUNDS);
        }
        else a[i] = el;
    }

    public void swap(int i, int j) throws CollectionException {
        if(i < 0 || i >= size || j < 0 || j >= size){
            throw new CollectionException(ERR_MSG_INDEX_OUT_OF_BOUNDS);
        }
        else {
            int temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }
    }

    public Array slice(int start, int end) throws CollectionException {
        Array rez = new Array();
        for (int i = start; i <= end; i++) {
            rez.append(this.getElement(i));
        }
        return rez;
    }

    @Override
    public String toString() {
        StringBuilder rez = new StringBuilder();
        for (int i = 0; i < this.size(); i++) {
            rez.append(a[i]).append(" ");
        }
        return rez.toString();
    }
}



@SuppressWarnings("unchecked")
class Sorter{
    private boolean trace;
    private String method;
    private boolean up;
    private int swaps, comparisons;

    Sorter(String mode, String method, String order){
        this.trace = mode.equals("trace");
        this.method = method;
        this.up = order.equals("up");
        this.swaps = 0;
        this.comparisons = 0;
    }

    public int getSwaps() {
        return swaps;
    }
    public int getComparisons() {
        return comparisons;
    }

    public void setSwaps(int s) {
        this.swaps = s;
    }
    public void setComparisons(int s) {
        this.comparisons = s;
    }

    public boolean isTrace() {
        return this.trace;
    }
    public boolean isUp() {
        return this.up;
    }

    public void setUp(boolean up){
        this.up = up;
    }



    private boolean compareInsert(int a, int b, boolean up) {
        this.comparisons++;
        if(up){
            return a > b;
        }
        else return a < b;
    }
    private void printTrace(Array a, int i) {
        for (int k = 0; k <= i; k++) {
            try {
                System.out.print(a.getElement(k) + " ");
            }
            catch (CollectionException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.print("| ");

        for (int k = i + 1; k < a.size(); k++) {
            try {
                System.out.print(a.getElement(k) + " ");
            }
            catch (CollectionException e){
                System.out.println(e.getMessage());
            }
        }
        System.out.print("\n");
    }

    private void printTraceQuickSort(Array a, int left, int right, int r) throws CollectionException {
        for (int i = left; i <= right; i++) {
            if(i == r || i == r + 1){
                System.out.print("| ");
            }
            System.out.print(a.getElement(i) + " ");
        }
        if(right == r) {
            System.out.print("| ");
        }
        System.out.print("\n");
    }

    public void insert(Array a, boolean bucket) throws CollectionException {
        int j;
        if (this.trace && !bucket) {
            System.out.println(a);
        }
        for (int i = 1; i < a.size(); i++) {
            j = i;
            int k = a.getElement(i);
            this.swaps++;
                while (j > 0 && compareInsert(a.getElement(j - 1), k, this.up)) {
                    a.setElement(j, a.getElement(j - 1));
                    this.swaps ++;
                    j--;
                }
                a.setElement(j, k);
                this.swaps++;

            if (this.trace) {
                printTrace(a, i);
            }
        }
    }
    public void select(Array a) throws CollectionException {
        int m;
        boolean cond;

        if(this.trace) {
            System.out.println(a);
        }

        for (int i = 0; i < a.size() - 1; i++) {
            m = i;
            for (int j = i + 1; j < a.size(); j++) {
                if(this.up) {
                    cond = a.getElement(j) < a.getElement(m);
                }
                else {
                    cond = a.getElement(j) > a.getElement(m);
                }
                this.comparisons++;
                if(cond) {
                    m = j;
                }

            }
            a.swap(i, m);
            this.swaps += 3;

            if(this.trace) {
                printTrace(a, i);
            }
        }
    }

    private int partition(Array a, int left, int right) throws CollectionException {
        int p = a.getElement(left);
        this.swaps++;
        int l = left, r = right + 1;
        boolean cond1, cond2;

        while (true) {
            do {
                l++;
                if(this.up) {
                    cond1 = a.getElement(l) < p;
                }
                else {
                    cond1 = a.getElement(l) > p;
                }
                this.comparisons++;
            } while (cond1 && l < right);

            do {
                r--;
                if(this.up) {
                    cond2 = a.getElement(r) > p;
                }
                else {
                    cond2 = a.getElement(r) < p;
                }
                this.comparisons++;
            } while (cond2);
            if (l >= r){
                break;
            }
            a.swap(l, r);
            this.swaps += 3;
        }
        a.swap(left, r);
        this.swaps += 3;
        return r;
    }

    public void quicksort(Array a, int left, int right) throws CollectionException {
        if (left >= right) {
            return;
        }
        int r = partition(a, left, right);
        if(this.trace) {
            printTraceQuickSort(a, left, right, r);
        }
        quicksort(a, left, r - 1);
        //printTrace(a, r);
        quicksort(a, r + 1, right);
        //printTrace(a, r);
    }


    public Array mergeSort(Array a) throws CollectionException {
        //System.out.println(a);
        if(a.size() <= 1) return a;
        int middle = (a.size() - 1) / 2;
        if(this.trace) {
            printTrace(a, middle);
        }
        Array left = mergeSort(a.slice(0, middle));
        Array right = mergeSort(a.slice(middle + 1, a.size() - 1));
        Array rez  = merge(left, right);
        if(this.trace) {
            System.out.println(rez);
        }
        return rez;
    }

    private Array merge(Array a, Array b) throws CollectionException {
       // System.out.println(mergeSort(a));
        boolean cond;
        Array c = new Array();
        int k = a.size();
        int l = b.size();
        int i = 0, j = 0;
        while (i < k && j < l) {
            if(this.up){
                cond = a.getElement(i) <= b.getElement(j);
            }
            else {
                cond = a.getElement(i) >= b.getElement(j);
            }
            this.comparisons++;

            if(cond) {
                c.append(a.getElement(i++));
            }
            else {
                c.append(b.getElement(j++));
            }
            this.swaps += 2;
        }
        while (i < k) {
            c.append(a.getElement(i++));
            this.swaps += 2;
        }
        while (j < l) {
            c.append(b.getElement(j++));
            this.swaps +=2;
        }
        return c;
    }


    private void siftDown(Array a, int p, boolean max, int last) throws CollectionException {
        int c = 2 * p + 1;
        boolean cond1 = true, cond2;
        while (c <= last) {
            if( c + 1 <= last ) {
                if(max) {
                    cond1 = a.getElement(c + 1) > a.getElement(c);
                }
                else {
                    cond1 = a.getElement(c + 1) < a.getElement(c);
                }
                this.comparisons++;
            }
            if((c + 1 <= last) && cond1) {
                c++;
            }
            this.comparisons++;
            if(max) {
                cond2 = a.getElement(p) >= a.getElement(c);
            }
            else {
                cond2 = a.getElement(p) <= a.getElement(c);
            }
            if(cond2) {
                break;
            }
            a.swap(p, c);
            if(p != c)
                this.swaps += 3;
            p = c; c = 2 * p + 1;
        }
    }

    public void heap(Array a) throws CollectionException {
        if(this.trace) {
            System.out.println(a);
        }
        int last = a.size() - 1;
        for (int i = a.size() / 2 - 1; i > -1; i--) {
            siftDown(a, i, this.up, last);
        }

        while (last > 0) {
            if(this.trace) {
                printTrace(a, last);
            }
            a.swap(0, last);
            this.swaps += 3;
            last--;
            siftDown(a,0, this.up, last);

        }
        if(this.trace) {
            printTrace(a, last);
        }
    }

    private int radixStart(Array a, boolean start, int m) throws CollectionException {
        int[] c = new int[10];
        int[] tmp = new int[a.size()];
        int max = 0;
        for (int i = 0; i < a.size(); i++) {
            int el = a.getElement(i);
            if(start) {
                if (Math.floor(Math.log10(el)) > max) {
                    max = (int) Math.floor(Math.log10(el));
                }
            }
            if(this.up) {
                c[el / m % 10]++;
            }
            else {
                c[9 - ((el / m) % 10)]++;
            }
        }
        for (int i = 1; i < c.length; i++) {
            c[i] += c[i - 1];
        }
        for (int i = a.size() - 1; i > -1; i--) {
            int index;
            if(this.up) {
                index = --c[(a.getElement(i) / m) % 10];
            }
            else {
                index = --c[9 -((a.getElement(i) / m) % 10)];
            }
            tmp[index] = a.getElement(i);
        }
        for (int i = 0; i < tmp.length; i++) {
            a.setElement(i, tmp[i]);
        }

        if(this.trace) {
            System.out.println(a);
        }
        this.swaps += 2 * a.size();
        this.comparisons += 2 * a.size();
        return max;
    }


    public void radix(Array a) throws CollectionException {
        if(this.trace) {
            System.out.println(a);
        }

        int m = radixStart(a, true, 1);
        int byDigit = 1;
        for (int i = 0; i < m; i++) {
            byDigit *= 10;
            radixStart(a, false, byDigit);

        }
       // System.out.println(a);
    }

    private int[] findMaxMin(Array a) throws CollectionException {
        int max = 0;
        int min = 0;
        boolean cond1 = false;
        for (int i = 1; i < a.size(); i++) {
            this.comparisons += 2;
            if (a.getElement(i) < a.getElement(min)) {
                min = i; this.comparisons --;
            } else if (a.getElement(i) > a.getElement(max)) {
                max = i;
               // this.comparisons++;
            }
           // this.comparisons++;

        }
        return new int[] {a.getElement(max), a.getElement(min)};
    }

    public void bucket(Array a) throws CollectionException {
        if(this.trace) {
            System.out.println(a);
        }

        int numberOfBuckets = a.size() / 2;
        int[] c = new int[numberOfBuckets];
        int[] tmp = new int[a.size()];
        int[] maxMin = findMaxMin(a);
        int max = maxMin[0], min = maxMin[1];
        double v = (double) (max - min + 1) / (numberOfBuckets * 1.0);

        for (int i = 0; i < a.size(); i++) {
            int x = a.getElement(i), ix;
            //this.comparisons++;
            if(this.up) {
                ix = (int) Math.floor( (x - min) /  v);
            }
            else {
                ix = (c.length - 1) - (int) Math.floor((float) (x - min) /  v);
            }
            if(ix > c.length - 1) {
                ix = c.length - 1;
            }
            if(ix < 0){

                ix = 0;
            }
            c[ix]++;
        }

        for (int i = 1; i < c.length; i++) {
            c[i] += c[i - 1];
        }

        for (int i = a.size() - 1; i > -1; i--) {
            int index,  ix;
            if(this.up) {
                ix = (int) Math.floor((float) (a.getElement(i) - min) / v);
            }
            else {
                ix = (c.length - 1) - (int) Math.floor((float) (a.getElement(i) - min) / v);
            }


            if(ix > c.length - 1){
                ix = c.length - 1;
            }
            if(ix > c.length - 1){
                ix = c.length - 1;
            }
            if(ix < 0){
                ix = 0;
            }

            //this.comparisons++;
            index = --c[ix];
            tmp[index] = a.getElement(i);
            //this.swaps++;
        }

        for (int i = 0; i < tmp.length; i++) {
            a.setElement(i, tmp[i]);
            //this.swaps++;
        }

        if(this.trace) {
            int k = 1;
            for (int i = 0; i < a.size(); i++) {
                while(k < c.length && i == c[k]) {
                    System.out.print("| ");
                    k++;
                }
                System.out.print(a.getElement(i) + " ");
            }
            System.out.print("\n");
        }
        this.swaps += 2 * a.size();
        this.comparisons += 2 * a.size();
        this.insert(a, true);

    }


    public void bubble(Array a) throws CollectionException {
        boolean swapMade;
        if(this.trace){
            System.out.println(a);
        }
        int lastSwap = 0;
        for (int i = 1; i < a.size(); i++) {
            boolean cond;
            swapMade = false;
            for (int j = a.size() - 1; j >= i ; j--) {
                    if(this.up) {
                        cond = a.getElement(j - 1) > a.getElement(j);
                    }
                    else {
                        cond = a.getElement(j - 1) < a.getElement(j);
                    }
                    this.comparisons++;
                    if(cond) {
                        a.swap(j, j - 1);
                        this.swaps += 3;
                        swapMade = true;
                        lastSwap = j;
                    }

            }
            if(swapMade)
                i = lastSwap;
            else {
                i = a.size() - 1;
            }
            /*
            if(this.trace) {
                printTrace(a, i - 1);
            }
             */
        }
    }
}

@SuppressWarnings("unchecked")
public class Naloga2 {
    /*
     static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
     */
    static String printArray(int[] a) {
        StringBuilder rez = new StringBuilder();
        for (int j : a) {
            rez.append(j).append(" ");
        }
        return rez.toString();
    }
    static void counter(Sorter sorter, Array array) throws CollectionException {
        System.out.print(sorter.getSwaps() + " " + sorter.getComparisons());

        sorter.setSwaps(0); sorter.setComparisons(0);
        sorter.insert(array, false);
        System.out.print(" | " + sorter.getSwaps() + " " + sorter.getComparisons());

        sorter.setSwaps(0); sorter.setComparisons(0);
        sorter.setUp(!sorter.isUp());
        sorter.insert(array, false);
        System.out.print(" | " + sorter.getSwaps() + " " + sorter.getComparisons());
    }

    public static void main(String[] args) {
        Array array = new Array();
        Scanner sc = new Scanner(System.in);
        Scanner line;
        String[] secondLineSplitted;
        while (sc.hasNext()) {
            String firstLine = sc.nextLine();
            line = new Scanner(firstLine);
            String mode = line.next();
            String method = line.next();
            //System.out.println(method);
            String order = line.next();
            Sorter sorter = new Sorter(mode, method, order);

            String secondLine = sc.nextLine();
            //String[] secondLineSplitted = secondLine.split(" ");
            line = new Scanner(secondLine);
            while (line.hasNextInt()) {
                array.append(line.nextInt());
            }

            try {
                switch (method) {
                    case "insert":
                        sorter.insert(array, false);
                        if (!sorter.isTrace()) {
                            counter(sorter, array);
                        }
                        break;

                    case "select":
                        sorter.select(array);
                        if (!sorter.isTrace()) {
                            System.out.print(sorter.getSwaps() + " " + sorter.getComparisons());

                            sorter.setSwaps(0);
                            sorter.setComparisons(0);
                            sorter.select(array);
                            System.out.print(" | " + sorter.getSwaps() + " " + sorter.getComparisons());

                            sorter.setSwaps(0);
                            sorter.setComparisons(0);
                            sorter.setUp(!sorter.isUp());
                            sorter.select(array);
                            System.out.print(" | " + sorter.getSwaps() + " " + sorter.getComparisons());
                        }
                        break;

                    case "bubble":
                        sorter.bubble(array);
                        if (!sorter.isTrace()) {
                            System.out.print(sorter.getSwaps() + " " + sorter.getComparisons());

                            sorter.setSwaps(0);
                            sorter.setComparisons(0);
                            sorter.bubble(array);
                            System.out.print(" | " + sorter.getSwaps() + " " + sorter.getComparisons());

                            sorter.setSwaps(0);
                            sorter.setComparisons(0);
                            sorter.setUp(!sorter.isUp());
                            sorter.bubble(array);
                            System.out.print(" | " + sorter.getSwaps() + " " + sorter.getComparisons());
                        }
                        break;

                    case "heap":
                        sorter.heap(array);
                        if (!sorter.isTrace()) {
                            System.out.print(sorter.getSwaps() + " " + sorter.getComparisons());

                            sorter.setSwaps(0);
                            sorter.setComparisons(0);
                            sorter.heap(array);
                            System.out.print(" | " + sorter.getSwaps() + " " + sorter.getComparisons());

                            sorter.setSwaps(0);
                            sorter.setComparisons(0);
                            sorter.setUp(!sorter.isUp());
                            sorter.heap(array);
                            System.out.print(" | " + sorter.getSwaps() + " " + sorter.getComparisons());
                        }
                        break;

                    case "merge":
                        if (sorter.isTrace()) {
                            System.out.println(array);
                        }
                        Array tempArray = sorter.mergeSort(array);
                        if (!sorter.isTrace()) {
                            System.out.print(sorter.getSwaps() + " " + sorter.getComparisons());

                            sorter.setSwaps(0);
                            sorter.setComparisons(0);
                            sorter.mergeSort(tempArray);
                            System.out.print(" | " + sorter.getSwaps() + " " + sorter.getComparisons());

                            sorter.setSwaps(0);
                            sorter.setComparisons(0);
                            sorter.setUp(!sorter.isUp());
                            sorter.mergeSort(tempArray);
                            System.out.print(" | " + sorter.getSwaps() + " " + sorter.getComparisons());
                        }
                        break;

                    case "quick":
                        // System.out.println(sorter.isUp());
                        if (sorter.isTrace()) {
                            System.out.println(array);
                        }
                        sorter.quicksort(array, 0, array.size() - 1);
                        if (sorter.isTrace()) {
                            System.out.println(array);
                        }
                        if (!sorter.isTrace()) {
                            System.out.print(sorter.getSwaps() + " " + sorter.getComparisons());

                            sorter.setSwaps(0);
                            sorter.setComparisons(0);
                            sorter.quicksort(array, 0, array.size() - 1);
                            System.out.print(" | " + sorter.getSwaps() + " " + sorter.getComparisons());

                            sorter.setSwaps(0);
                            sorter.setComparisons(0);
                            sorter.setUp(!sorter.isUp());
                            sorter.quicksort(array, 0, array.size() - 1);
                            System.out.print(" | " + sorter.getSwaps() + " " + sorter.getComparisons());
                        }

                    case "radix":
                        sorter.radix(array);
                        if (!sorter.isTrace()) {
                            System.out.print(sorter.getSwaps() + " " + sorter.getComparisons());

                            sorter.setSwaps(0);
                            sorter.setComparisons(0);
                            sorter.radix(array);
                            System.out.print(" | " + sorter.getSwaps() + " " + sorter.getComparisons());

                            sorter.setSwaps(0);
                            sorter.setComparisons(0);
                            sorter.setUp(!sorter.isUp());
                            sorter.radix(array);
                            System.out.print(" | " + sorter.getSwaps() + " " + sorter.getComparisons());
                        }
                        break;

                    case "bucket":
                        sorter.bucket(array);
                        if (!sorter.isTrace()) {
                            System.out.print(sorter.getSwaps() + " " + sorter.getComparisons());

                            sorter.setSwaps(0);
                            sorter.setComparisons(0);
                            sorter.bucket(array);
                            System.out.print(" | " + sorter.getSwaps() + " " + sorter.getComparisons());

                            sorter.setSwaps(0);
                            sorter.setComparisons(0);
                            sorter.setUp(!sorter.isUp());
                            sorter.bucket(array);
                            System.out.print(" | " + sorter.getSwaps() + " " + sorter.getComparisons());
                        }
                        break;


                    default:
                        System.out.println("Wrong method");
                }
            } catch (CollectionException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
