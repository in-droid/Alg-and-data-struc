
import java.util.Scanner;
@SuppressWarnings("unchecked")
interface Collection {
    static final String ERR_MSG_EMPTY = "Collection is empty.";
    static final String ERR_MSG_FULL = "Collection is full.";

    boolean isEmpty();
    boolean isFull();
    int size();
    String toString();
}
@SuppressWarnings("unchecked")
class CollectionException extends Exception {
    public CollectionException(String msg) {
        super(msg);
    }
}
@SuppressWarnings("unchecked")
interface Stack<T> extends Collection {
    T top() throws CollectionException;
    void push(T x) throws CollectionException;
    T pop() throws CollectionException;
}
@SuppressWarnings("unchecked")
interface Sequence<T> extends Collection {
    static final String ERR_MSG_INDEX = "Wrong index in sequence.";
    static final int DEFAULT_CAPACITY = 42;
    T get(int i) throws CollectionException;

    void add(T x) throws CollectionException;
}
@SuppressWarnings("unchecked")
class ArrayDeque<T> implements Stack<T>, Sequence<T> {
    private static final int DEFAULT_CAPACITY = 64;
    @SuppressWarnings({"static", "deprecated", "redundant"})
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
        if (size > 0) {
            rez.append(a[front]);
            for (int i = 0; i < size - 1; i++) {
                rez.append(" " + a[next(front + i)].toString());
            }
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
@SuppressWarnings("unchecked")
class Calculator{
    private Sequence<Stack<String>> stackSequence = new ArrayDeque<>();
    private boolean cond;
    private String line;
    private Scanner sc;
    Calculator(String line) throws CollectionException {
        this.line = line;
        sc = new Scanner(line);
        this.cond = false;

        for (int i = 0; i < 42; i++) {
            Stack<String> x = new ArrayDeque<>();
            stackSequence.add(x);

        }
    }
    /*
    public void getMain() throws CollectionException {
        System.out.println(stackSequence.get(0));
    }
     */
    private boolean isInteger(String s){
        try {
            Integer.parseInt(s);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }
    private int factorial(int n){
        if(n == 0)
            return 1;
        else
            return n * factorial(n - 1);
    }

    private void clearStack(int i) throws CollectionException {
        while (!stackSequence.get(i).isEmpty()){
            stackSequence.get(i).pop();
        }
    }
    private void prepisi(Stack<String> s, Stack<String> s1) throws CollectionException {
        while(!s.isEmpty()){
            s1.push(s.pop());
        }
    }
    private void runStack(int i) throws CollectionException {
        Stack<String> temp = new ArrayDeque<>();
        String cmd;
        while (!stackSequence.get(i).isEmpty()) {
            temp.push(stackSequence.get(i).pop());
        }
        while (!temp.isEmpty()){
            cmd = temp.pop();
            stackSequence.get(i).push(cmd);
            command(cmd);
        }
    }

    private void command(String cmd) throws CollectionException {
        switch (cmd) {
            case "echo" : {
                if(stackSequence.get(0).isEmpty()) {
                    System.out.print("\n");
                }
                else {
                    System.out.println(stackSequence.get(0).top());
                }
                break;
            }
            case "pop": {
                stackSequence.get(0).pop();
                break;
            }
            case "dup": {
                String temp = stackSequence.get(0).top();
                stackSequence.get(0).push(temp);
                break;
            }
            case "dup2": {
                String y = stackSequence.get(0).pop();
                String x = stackSequence.get(0).pop();
                stackSequence.get(0).push(x);
                stackSequence.get(0).push(y);
                stackSequence.get(0).push(x);
                stackSequence.get(0).push(y);
                break;
            }
            case "swap": {
                String x = stackSequence.get(0).pop();
                String y = stackSequence.get(0).pop();
                stackSequence.get(0).push(x);
                stackSequence.get(0).push(y);
                break;
            }
            case "char": {
                String x = stackSequence.get(0).pop();
                stackSequence.get(0).push(String.valueOf(((char)Integer.parseInt(x))));
                break;
            }
            case "even": {
                String x = stackSequence.get(0).pop();
                if(Integer.parseInt(x) % 2 == 0 ){
                    stackSequence.get(0).push("1");
                }
                else {
                    stackSequence.get(0).push("0");
                }
                break;
            }
            case "odd" : {
                String x = stackSequence.get(0).pop();
                if(Integer.parseInt(x) % 2 != 0){
                    stackSequence.get(0).push("1");
                }
                else {
                    stackSequence.get(0).push("0");
                }
                break;
            }
            case "!" : {
                String x = stackSequence.get(0).pop();
                stackSequence.get(0).push(Integer.toString(factorial(Integer.parseInt(x))));
                break;
            }
            case "len" : {
                String x = stackSequence.get(0).pop();
                stackSequence.get(0).push(Integer.toString(x.length()));
                break;
            }
            case "<>" : {
                String x = stackSequence.get(0).pop();
                String y = stackSequence.get(0).pop();
                if(Integer.parseInt(x) != Integer.parseInt(y)){
                    stackSequence.get(0).push("1");
                }
                else {
                    stackSequence.get(0).push("0");
                }
                break;
            }
            case "<" : {
                String y = stackSequence.get(0).pop();
                String x = stackSequence.get(0).pop();
                if(Integer.parseInt(x) < Integer.parseInt(y)){
                    stackSequence.get(0).push("1");
                }
                else {
                    stackSequence.get(0).push("0");
                }
                break;
            }
            case "<=" : {
                String y = stackSequence.get(0).pop();
                String x = stackSequence.get(0).pop();
                if(Integer.parseInt(x) <= Integer.parseInt(y)){
                    stackSequence.get(0).push("1");
                }
                else {
                    stackSequence.get(0).push("0");
                }
                break;
            }
            case "==" : {
                String y = stackSequence.get(0).pop();
                String x = stackSequence.get(0).pop();
                if(isInteger(y) && isInteger(x)){
                    if(Integer.parseInt(y) == Integer.parseInt(x)){
                        stackSequence.get(0).push("1");
                    }
                    else {
                        stackSequence.get(0).push("0");
                    }
                }
                else if(y.equals(x)) {
                    stackSequence.get(0).push("1");
                }else {
                    stackSequence.get(0).push("0");
                }
                break;
            }
            case ">=" : {
                String y = stackSequence.get(0).pop();
                String x = stackSequence.get(0).pop();
                if(Integer.parseInt(x) >= Integer.parseInt(y)){
                    stackSequence.get(0).push("1");
                }
                else {
                    stackSequence.get(0).push("0");
                }
                break;
            }
            case ">" : {
                String y = stackSequence.get(0).pop();
                String x = stackSequence.get(0).pop();
                if(Integer.parseInt(x) > Integer.parseInt(y)){
                    stackSequence.get(0).push("1");
                } else {
                    stackSequence.get(0).push("0");
                }
                break;
            }
            case "+" : {
                String y = stackSequence.get(0).pop();
                String x = stackSequence.get(0).pop();
                int rez = Integer.parseInt(x) + Integer.parseInt(y);
                stackSequence.get(0).push(Integer.toString(rez));
                break;
            }
            case "-" : {
                String y = stackSequence.get(0).pop();
                String x = stackSequence.get(0).pop();
                int rez = Integer.parseInt(x) - Integer.parseInt(y);
                stackSequence.get(0).push(Integer.toString(rez));
                break;
            }
            case "*" : {
                String y = stackSequence.get(0).pop();
                String x = stackSequence.get(0).pop();
                int rez = Integer.parseInt(x) * Integer.parseInt(y);
                stackSequence.get(0).push(Integer.toString(rez));
                break;
            }
            case "/" : {
                String y= stackSequence.get(0).pop();
                String x = stackSequence.get(0).pop();
                int rez = (Integer.parseInt(x) / Integer.parseInt(y));
                stackSequence.get(0).push(Integer.toString(rez));
                break;

            }
            case "%" : {
                String y = stackSequence.get(0).pop();
                String x = stackSequence.get(0).pop();
                stackSequence.get(0).push(Integer.toString(
                        Integer.parseInt(x) % Integer.parseInt(y)));
                break;
            }
            case "." : {
                String y = stackSequence.get(0).pop();
                String x = stackSequence.get(0).pop();
                stackSequence.get(0).push(x + y);
                break;
            }

            case "rnd" : {
                String y = stackSequence.get(0).pop();
                String x = stackSequence.get(0).pop();
                String rez = Integer.toString(rnd(Integer.parseInt(x), Integer.parseInt(y)));
                stackSequence.get(0).push(rez);
                break;
            }

            case "then" : {
                String x = stackSequence.get(0).pop();
                this.cond = Integer.parseInt(x) != 0;
                break;
            }

            case "else" : {
                this.cond = !this.cond;
                break;
            }

            case "print" : {
                int i = Integer.parseInt(stackSequence.get(0).pop());
                if(stackSequence.get(i).isEmpty())
                    System.out.print("\n");
                else
                    System.out.println(stackSequence.get(i));
                break;

            }

            case "clear" : {
                int i = Integer.parseInt(stackSequence.get(0).pop());
                clearStack(i);
                break;
            }

            case "run" : {
                int i = Integer.parseInt(stackSequence.get(0).pop());
                runStack(i);
                break;
            }

            case "loop" : {
                int index = Integer.parseInt(stackSequence.get(0).pop());
                int counter = Integer.parseInt(stackSequence.get(0).pop());
                for (int i = 0; i < counter; i++) {
                    runStack(index);
                }
                break;
            }

            case "fun" : {
                int index = Integer.parseInt(stackSequence.get(0).pop());
                int counter = Integer.parseInt(stackSequence.get(0).pop());
                for (int i = 0; i < counter; i++) {
                    stackSequence.get(index).push(sc.next());
                }
                break;
            }

            case "move" : {
                int index = Integer.parseInt(stackSequence.get(0).pop());
                int counter = Integer.parseInt(stackSequence.get(0).pop());
                for (int i = 0; i < counter; i++) {
                    stackSequence.get(index).push(stackSequence.get(0).pop());
                }
                break;
            }

            case "reverse" : {
                int index = Integer.parseInt(stackSequence.get(0).pop());
                Stack<String> temp = new ArrayDeque<>();
                Stack<String> temp2 = new ArrayDeque<>();
                prepisi(stackSequence.get(index), temp);
                prepisi(temp, temp2);
                prepisi(temp2, stackSequence.get(index));
                break;
            }

            default:
                if(cmd.charAt(0) == '?'){
                    if(cond)
                        command(cmd.substring(1));
                }
                else
                stackSequence.get(0).push(cmd);
        }
    }


    public void readLine() throws CollectionException {
        while (sc.hasNext()) {
            String cmd = sc.next();
            command(cmd);

        }
    }
    private int rnd(int x, int y){
        int interval = (y - x) + 1;
        return (int) (Math.random() * (interval)) + x;
    }


}
@SuppressWarnings("unchecked")
public class Dnaloga1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String line;
        try {
            while (sc.hasNext()) {
                line = sc.nextLine();
                Calculator c = new Calculator(line);
                c.readLine();
            }

        }catch (CollectionException e){
            System.out.println(e.getMessage());
        }
    }
}
