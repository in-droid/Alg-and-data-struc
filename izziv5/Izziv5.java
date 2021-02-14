package izziv5;

public class Izziv5 {
    static Integer generateNumber(int interval){
        return (int) (Math.random()* interval);
    }

    static double[] test(PriorityQueue<Integer> x) throws CollectionException {
        long startTime = System.nanoTime();
        double denum = 1000000;
        for (int i = 0; i <1000; i++) {
            x.enqueue(generateNumber(100));
        }
        for (int i = 0; i < 500; i++) {
            int choose = generateNumber(3);
            if (choose == 0) {
                x.dequeue();
            } else if (choose == 1) {
                x.front();
            } else {
                x.enqueue(generateNumber(2000));
            }
        }
        double executionTime =  (System.nanoTime() - startTime) / denum;
        return new double[]{executionTime, x.getComparisons(), x.getMovements()};
    }

    public static void main(String[] args) {
        ArrayPQ<Integer> basicArray = new ArrayPQ<>();
        ArrayHeapPQ<Integer> heapPQ = new ArrayHeapPQ<>();
        LinkedHeapPQ<Integer> pseudo = new LinkedHeapPQ<>();
        try {
            /*
            for (int i = 0; i <1000; i++) {
                heapPQ.enqueue(generateNumber(100));
                heapPQ.dequeue();
            }
            for (int i = 0; i < 500; i++) {
                int choose = generateNumber(3);
                if (choose == 0) {
                    heapPQ.dequeue();
                } else if (choose == 1) {
                    heapPQ.front();
                } else {
                    heapPQ.enqueue(generateNumber(2000));
                }
            }

             */
            System.out.println("Implementacija       Čas[ms]   Primerjav     Premikov");
            System.out.println("------------------------------------------------------");
            double[] test1 = test(basicArray);
            System.out.println("ArrayPQ: " + String.format("%19.3f %10d %10d",
                                                           test1[0],(int)test1[1],(int)test1[2]));
            test1 = test(heapPQ);
            System.out.println("ArrayHeapPQ: " + String.format("%15.3f %10d %10d",
                                                               test1[0],(int)test1[1],(int)test1[2]));

            test1 = test(pseudo);
            System.out.println("LinkedHeapPQ: " + String.format("%15.3f %10d %10d",
                                                  test1[0],(int)test1[1],(int)test1[2]));


        }catch (CollectionException e){
            System.out.println(e.getMessage());
        }

        //}
    }
}
