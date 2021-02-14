package dnaloga3;


import java.util.Scanner;

@SuppressWarnings("unchecked")
class CollectionException extends Exception {
    public CollectionException(String msg) {
        super(msg);
    }
}

@SuppressWarnings("unchecked")
interface Collection {
    static final String ERR_MSG_EMPTY = "Collection is empty.";
    static final String ERR_MSG_FULL = "Collection is full.";

    boolean isEmpty();
   // boolean isFull();
    int size();
    //String toString();
    void resize();
}

@SuppressWarnings("unchecked")
interface Deque<T> extends Collection {
    T front() throws CollectionException;
    T back() throws CollectionException;
    void enqueue(T x) throws CollectionException;
    void enqueueFront(T x) throws CollectionException;
    T dequeue() throws CollectionException;
    T dequeueBack() throws CollectionException;
    void resize();
}


@SuppressWarnings("unchecked")
interface Sequence<T> extends Collection {
    static final String ERR_MSG_INDEX = "Wrong index in sequence.";

    T get(int i) throws CollectionException;

    void add(T x) throws CollectionException;

}


@SuppressWarnings("unchecked")
interface Stack<T> extends Collection {
    T top() throws CollectionException;
    void push(T x) throws CollectionException;
    T pop() throws CollectionException;
}


@SuppressWarnings("unchecked")
class ArrayDeque<T> implements Deque<T>, Stack<T>, Sequence<T> {
    private static final int DEFAULT_CAPACITY = 64;
    private T[] a;
    private int front, back, size;
    private int capacity;

    public ArrayDeque() {
        a = (T[]) (new Object[DEFAULT_CAPACITY]);
        front = 0;
        back = 0;
        size = 0;
        capacity = DEFAULT_CAPACITY;
    }

    @Override
    public void resize() {
        capacity = 2 * size();
       // System.out.println(size + " " + back);
        T[] temp = (T[]) (new Object[capacity]);
        for (int i = 0; i < size; i++) {
            temp[i] = a[i];
        }
        back = size;
        a = temp;
    }

    private int next(int i) {
        return (i + 1) % capacity;
    }

    private int prev(int i) {
        return (capacity + i - 1) % capacity;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }


    @Override
    public int size() {
        return size;
    }

    /*
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

     */

    @Override
    public void push(T x)  {
        if (size == capacity) {
            resize();
        }
        a[back] = x;
        back = next(back);
        size++;

    }

    @Override
    public T top() {
        if(size == capacity){
            resize();
        }
        return a[prev(back)];
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
    public T back() {
        return top();
    }

    @Override
    public void enqueue(T x) {
        push(x);

    }

    @Override
    public void enqueueFront(T x) {
        if (size == capacity) {
            resize();
        }
            front = prev(front);
            a[front] = x;
            size++;
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
        return (front + i) % capacity;
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

    public void set(T x, int i) throws CollectionException {
        if(isEmpty()){
            throw new CollectionException(ERR_MSG_EMPTY);
        }
        if(i < 0 || i >= size){
            throw new CollectionException(ERR_MSG_INDEX);
        }
        a[index(i)] = x;
    }
}


/*
@SuppressWarnings("unchecked")
class Array {
    private static int DEFAULT_CAPACITY = 64;
    static final String ERR_MSG_INDEX_OUT_OF_BOUNDS = "Index is out of bounds";
    private int size, capacity, last;
    private int[] a;

    public Array() {
        a = new int[DEFAULT_CAPACITY];
        size = 0;
        last = 0;
        capacity = DEFAULT_CAPACITY;

    }

    void resize() {
        capacity = 2 * size();
        int[] temp = new int[capacity];
        for (int i = 0; i < size; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }

    public int size() {
        return size;
    }

    public void append(int x) {
        if (size == capacity) {
            resize();
        }
        a[size] = x;
        size++;
    }

    public int getElement(int i) throws CollectionException {
        if (i >= size || i < 0) {
            throw new CollectionException(ERR_MSG_INDEX_OUT_OF_BOUNDS);
        } else return a[i];
    }

    public void setElement(int i, int el) throws CollectionException {
        if (i >= size || i < 0) {
            throw new CollectionException(ERR_MSG_INDEX_OUT_OF_BOUNDS);
        } else a[i] = el;
    }

    public String toString() {
        StringBuilder rez = new StringBuilder();
        for (int i = 0; i < this.size(); i++) {
            rez.append(a[i]).append(" ");
        }
        return rez.toString();
    }
}
 */

class Sorter {
    public static void insert(ArrayDeque<Integer> a) throws CollectionException {
        int j;

        for (int i = 1; i < a.size(); i++) {
            j = i;
            int k = a.get(i);
            while (j > 0 && a.get(j - 1) > k) {
                a.set(a.get(j - 1), j);
                j--;
            }
            a.set(k, j);
        }
    }
    public static void insertSortMatrix(ArrayDeque<ArrayDeque<Integer>> a) throws CollectionException {
        int j;
        for (int i = 1; i < a.size(); i++) {
            j = i;
            ArrayDeque<Integer> k = a.get(i);
            while (j > 0 && a.get(j - 1).get(0) > k.get(0)) {
                a.set(a.get(j - 1), j);
                j--;
            }
            a.set(k, j);
        }
    }
}


@SuppressWarnings("unchecked")
class Graph {
    private int numOfNodes;
    private int[][] matrix;
    private String type;
    private String method;
    private boolean[] visited;
    private int counter;

    //private Array vstopni;
   // private Array izstopni;
    private ArrayDeque<Integer> vstopni;
    private ArrayDeque<Integer> izstopni;
    private int[] parent;

    public Graph(String type, String method, int numOfNodes,
                 ArrayDeque<Integer> connections) throws CollectionException {

        this.numOfNodes = numOfNodes;
        this.method = method;
        this.type = type;
        this.visited = new boolean[this.numOfNodes];
        this.matrix = new int[numOfNodes][numOfNodes];
        this.counter = 0;
        this.parent = new int[numOfNodes];
        this.resetParents();

        this.vstopni = new ArrayDeque<>();
        this.izstopni = new ArrayDeque<>();

        for (int i = 0; i < connections.size() - 1; i += 2) {
           // System.out.println(i + " " + connections.size());
            this.matrix[connections.get(i)][connections.get(i + 1)] = 1;
            if (this.type.equals("undirected")) {
                this.matrix[connections.get(i + 1)][connections.get(i)] = 1;
            }
        }
    }
    public String getMethod() {
        return method;
    }

    private void resetParents() {
        for (int i = 0; i < this.parent.length; i++) {
            this.parent[i] = - 1;
        }
    }


    public void info() {
        int connections = 0;
        for (int i = 0; i < this.numOfNodes; i++) {
            for (int j = 0; j < this.numOfNodes; j++) {
                connections += this.matrix[i][j];
            }
        }
        if (this.type.equals("undirected")) {
            connections /= 2;
        }
        System.out.println(this.numOfNodes + " " + connections);


        for (int i = 0; i < this.numOfNodes; i++) {
            int outDeg = 0, inDeg = 0;
            for (int j = 0; j < this.numOfNodes; j++) {
                outDeg += matrix[i][j];
                inDeg += matrix[j][i];
            }
            if (this.type.equals("undirected")) {
                System.out.println(i + " " + outDeg);
            } else {
                System.out.println(i + " " + outDeg + " " + inDeg);
            }
        }
    }
    
   private int[][] multiply(int[][] pow) {
        int[][] rez = new int[this.numOfNodes][this.numOfNodes];

       for (int i = 0; i < this.numOfNodes; i++) {
           for (int j = 0; j < this.numOfNodes; j++) {
               rez[i][j] = 0;
               for (int k = 0; k < this.numOfNodes; k++) {
                   rez[i][j] += pow[k][j] * this.matrix[i][k];
               }
           }
       }
       return rez;

   }

    public void walks(int n) {
        int[][] rez = new int[this.numOfNodes][this.numOfNodes];

        for (int i = 0; i < this.numOfNodes; i++) {
            for (int j = 0; j < this.numOfNodes; j++) {
                rez[i][j] = this.matrix[i][j];
            }
        }


        for (int i = 1; i < n; i++) {
            rez = multiply(rez);
        }
        printMatrix(rez);
    }

    static void printMatrix(int[][] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    private void countUnvisited() {
        while (this.counter < this.numOfNodes && this.visited[this.counter]){
            this.counter++;
        }
    }

    public void fullDfs(boolean comp) throws CollectionException {
        while (this.counter < this.numOfNodes) {
            dfs(this.counter);
            countUnvisited();
        }
        if(!comp) {
            for (int i = 0; i < this.vstopni.size(); i++) {
                System.out.print(this.vstopni.get(i) + " ");
            }
                System.out.print("\n");
            for (int i = 0; i < this.izstopni.size(); i++) {
                System.out.print(this.izstopni.get(i) + " ");
            }
        }

    }

    public ArrayDeque<ArrayDeque<Integer>> fullBfs(boolean comp) throws CollectionException {
        ArrayDeque<ArrayDeque<Integer>> result = new ArrayDeque<>();
        while (this.counter < this.numOfNodes) {
            if(comp) {
                this.vstopni = new ArrayDeque<>();
                result.push(bfs(this.counter));
            }
            countUnvisited();
        }
        for (int i = 0; i < this.vstopni.size() ; i++) {
            if(!comp)
                System.out.print(this.vstopni.get(i) + " ");
        }
        if(comp)
            return result;
        return null;

    }

    private void dfs(int start) {
        this.vstopni.push(start);

        visited[start] = true;

        for (int i = 0; i < this.matrix.length; i++) {
            if(this.matrix[start][i] == 1 & (!visited[i])) {
                dfs(i);
            }
        }
        this.izstopni.push(start);
    }

    private ArrayDeque<Integer> bfs(int start) throws CollectionException {
        Deque<Integer> q = new ArrayDeque<>();
        int v;

        q.enqueue(start);
        visited[start] = true;

        while (!q.isEmpty()) {
            v = q.dequeue();
            this.vstopni.push(v);
            for (int i = 0; i < this.matrix.length; i++) {
                if(this.matrix[v][i] == 1 & (!visited[i])) {
                    visited[i] = true;
                    q.enqueue(i);
                }
            }
        }
        return this.vstopni;
    }

    private void sp(int start, int end) throws CollectionException {
        Deque<Integer> q = new ArrayDeque<>();
        int v;

        q.enqueue(start);
        visited[start] = true;

        while (!q.isEmpty()) {
            v = q.dequeue();

            if(v == end) {
                System.out.println(end + " " + tracePath(end));
                return;
            }
            this.vstopni.push(v);

            for (int i = 0; i < this.matrix.length; i++) {
                if(this.matrix[v][i] == 1 & (!visited[i])) {
                    visited[i] = true;
                    parent[i] = v;
                    q.enqueue(i);
                }
            }
        }
        System.out.println(end + " " + -1);
    }

    private int tracePath(int end) {
        int node = end, length = 0;
        while (parent[node] != -1) {
            node = parent[node];
            length++;
        }
        return length;
    }

    public void fullSp(int start) throws CollectionException {
        for (int i = 0; i <this.numOfNodes; i++) {
            sp(start, i);
            this.visited = new boolean[this.numOfNodes];
            resetParents();
        }
    }

    private void undirectedComp() throws CollectionException {
        ArrayDeque<ArrayDeque<Integer>> components = fullBfs(true);
        for (int i = 0; i < components.size(); i++) {
            Sorter.insert(components.get(i));
        }
        for (int i = 0; i <  components.size(); i++) {
            for (int j = 0; j <components.get(i).size(); j++) {
                System.out.print(components.get(i).get(j) + " ");
            }
            System.out.print("\n");
        }

    }

    private void directedComp() throws CollectionException {
        ArrayDeque<Integer> reversedExit = new ArrayDeque<>();
        ArrayDeque<ArrayDeque<Integer>> result= new ArrayDeque<>();

        fullDfs(true);
        for (int i = this.izstopni.size() - 1; i > -1; i--) {
            reversedExit.push(this.izstopni.get(i));
        }
        int[][] reversedMatrix = new int[this.numOfNodes][this.numOfNodes];

        for (int i = 0; i < this.numOfNodes; i++) {
            for (int j = 0; j < this.numOfNodes; j++) {
                reversedMatrix[j][i] = this.matrix[i][j];
            }
        }
        for (int i = 0; i < this.numOfNodes; i++) {
            for (int j = 0; j < this.numOfNodes; j++) {
                this.matrix[i][j] = reversedMatrix[i][j];
            }
        }
        this.visited = new boolean[this.numOfNodes];
        for (int i = 0; i < reversedExit.size(); i++) {
            this.vstopni = new ArrayDeque<>();
            this.izstopni = new ArrayDeque<>();
           // this.visited = new boolean[this.numOfNodes];
            if(!this.visited[reversedExit.get(i)]) {
                dfs(reversedExit.get(i));
                result.push(this.izstopni);
            }
        }

        for (int i = 0; i < result.size(); i++) {
            Sorter.insert(result.get(i));
        }
        Sorter.insertSortMatrix(result);

        for (int i = 0; i < result.size(); i++) {
            for (int j = 0; j < result.get(i).size(); j++) {
                System.out.print(result.get(i).get(j) + " ");
            }
            System.out.print("\n");
        }
    }

    public void comp() throws CollectionException {
        if(this.type.equals("undirected")) {
            undirectedComp();
        }
        else {
            directedComp();
        }
    }
}


@SuppressWarnings("unchecked")
public class Naloga3 {


    public static void main(String[] args) throws CollectionException {
        Scanner sc = new Scanner(System.in);
        Scanner line;
        String lineString, type, method;
        int methodArg = 0;
        lineString = sc.nextLine();
        line = new Scanner(lineString);

        type = line.next();
        method = line.next();
        if(method.equals("walks") || method.equals("sp")) {
            methodArg = line.nextInt();
        }

        int nodesNumber = sc.nextInt();
        ArrayDeque<Integer> connections = new ArrayDeque<>();
        sc.nextLine();

        while (sc.hasNextInt()) {
            lineString = sc.nextLine();

            line = new Scanner(lineString);
            connections.push(line.nextInt());
            connections.push(line.nextInt());
        }
        sc.close();
        Graph g = new Graph(type, method, nodesNumber, connections);

        switch (g.getMethod()) {
            case "info":
                g.info();
                break;
            case "walks":
                g.walks(methodArg);
                break;

            case "dfs":
                g.fullDfs(false);
                break;

            case "bfs":
                g.fullBfs(false);
                break;

            case "sp":
                g.fullSp(methodArg);
                break;

            case "comp":
                g.comp();
                break;

        }

    }


}
