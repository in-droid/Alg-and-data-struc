package izziv1;

public class Izziv1 {
    public static void main(String[] args) {
        Stack<Integer> s = new ArrayDeque<>();
        Deque<Integer> d = new ArrayDeque<>();
        Sequence<String> q = new ArrayDeque<>();
        try {
            s.push(1); s.push(2); s.push(3); s.push(4);
            System.out.print("Stack s: ");
            while (!s.isEmpty()){
                System.out.print(s.top() + " ");
                d.enqueue(s.pop());
            }
            for (int i = 0; i < 64; i++) {
                s.push(i);
            }
            System.out.println("\nChecking if s is full: " + s.isFull());
            System.out.println("-------------------");
            System.out.println("Deque");
            d.enqueueFront(0);
            System.out.println("After enqueFront(0), front is: " + d.front());
            System.out.println("Back: " + d.back());
            System.out.println("Deque: " +d);
            System.out.println("Deque front: " +d.dequeue());
            System.out.println("Deque back:" +d.dequeueBack());
            System.out.println("Deque: " +d);
            System.out.print("Looping through elements:");
            while (!d.isEmpty()){
                System.out.print( " " +d.dequeue());
            }
            System.out.println("\nChecking if d.isEmpty(): "+d.isEmpty());
            System.out.println("----------------------");
            System.out.println("Sequence");
            q.add("APS1"); q.add("VAJE"); q.add("Izziv1");
            System.out.println(q.toString());
            System.out.print("Looping through elements:");
            for (int i = 0; i < q.size(); i++) {
                System.out.print(q.get(i) + " ");
            }
        }

        catch (CollectionException e){
            System.out.println(e.getMessage());
        }
    }
}
