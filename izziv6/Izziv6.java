package izziv6;

import java.util.Scanner;

public class Izziv6 {
    static boolean up;

    public static boolean isUp() {
        return up;
    }

    static void printArray(Oseba[] a) {
        StringBuilder rez = new StringBuilder();
        for (int i = 0; i < a.length; i++) {
            rez.append(a[i].toString()).append(" ");
        }
        System.out.println(rez.toString());
    }


    public static void setUp(boolean up) {
        Izziv6.up = up;
    }
    static void printTrace(Oseba[] a, int i) {
        for (int k = 0; k <= i; k++) {
            System.out.print(a[k] + " ");
        }
        System.out.print("| ");

        for (int k = i + 1; k < a.length; k++) {
            System.out.print(a[k] + " ");
        }
        System.out.print("\n");
    }

    public static void bubble(Oseba[] a) {
        boolean swapMade;

        for (int i = 1; i < a.length; i++) {
            boolean cond;
            int lastSwap = 0;
            swapMade = false;
            for (int j = a.length - 1; j >= i; j--) {
                if (isUp()) {
                    cond = a[j - 1].compareTo(a[j]) > 0;
                } else {
                    cond = a[j - 1].compareTo(a[j]) < 0;
                }
                if (cond) {
                    Oseba temp = a[j];
                    a[j] = a[j - 1];
                    a[j - 1] = temp;
                    swapMade = true;
                    lastSwap = j;
                }

            }
            if (swapMade)
                i = lastSwap;
            printTrace(a, i - 1);
        }
    }


    public static void main(String[] args) {
        String replay = "R";
        int n;
        Oseba[] osebi;
        Scanner sc = new Scanner(System.in);
        while (replay.equals("R")) {
            System.out.println("Enter the desired length: ");
            n = sc.nextInt();
            osebi = new Oseba[n];
            for (int i = 0; i < n; i++) {
                osebi[i] = new Oseba();
            }
            System.out.println("Enter the attribute that you want to sort by: ");
            Oseba.setAtr(sc.nextInt());

            System.out.println("Do you want to sort in ascending order?(YES/NO)");
            String order = sc.next();
            if(order.equals("YES")){
                setUp(true);
            }
            else if(order.equals("NO")){
                setUp(false);
            }
            else {
                System.out.println("Wrong command.");
            }
            printArray(osebi);
            bubble(osebi);
            printArray(osebi);

            System.out.println("Enter R if you want to replay");
            replay = sc.next();

        }



    }
}
