package izziv6;

public class Oseba implements Comparable<Object> {

    private static int atr;
    private String ime, priimek;
    private int letoR;
    private final static String[] IMENA = {"Andrej", "Janez", "Ivan", "Martin", "Ana", "Berta", "Cilka",
            "Tom", "Angela", "Maja", "Viktor", "Julia", "Josip", "Viktorija", "John"};
    private final static String[] PRIIMKI = {"Novak", "Horvat", "Oblak", "Zupan", "Hribar", "Broz", "Jug", "Merkel",
            "Travolta", "Nikolov", "Lennon", "Jelen", "Hanks", "Hopkins", "Roberts"};

    private static final int MIN_LETO_ROJSTVA = 1920;
    private static final int MAX_LETO_ROJSTVA = 2020;

   // private final static int[] LETO_ROJSTVA = fillLetoRojstva();
    /*
    private static int[] fillLetoRojstva(){
        int[] rez = new int[101];
        for (int i = 0; i <= 100; i++) {
            rez[i] = i + 1920;
        }
        return rez;
    }
     */
   public static int getAtr() {
       return atr;
   }

    public static void setAtr(int atr) {
        Oseba.atr = atr;
    }

    Oseba(){
        this.ime = IMENA[(int)(Math.random() * IMENA.length)];
        this.priimek = PRIIMKI[(int)(Math.random() * PRIIMKI.length)];
        this.letoR =MIN_LETO_ROJSTVA + (int)(Math.random() * ((MAX_LETO_ROJSTVA - MIN_LETO_ROJSTVA) + 1));
    }

    @Override
    public String toString(){
       switch (atr){
           case 0:
               return this.ime;
           case 1:
               return this.priimek;
           case 2:
               return String.valueOf(this.letoR);

           default:
               return "";
       }
    }

    @Override
    public int compareTo(Object o) {
        switch (atr){
            case 0:
                return this.ime.compareTo(((Oseba)(o)).ime);
            case 1:
                return this.priimek.compareTo(((Oseba)(o)).priimek);
            case 2:
                return this.letoR - ((Oseba)(o)).letoR;

            default:
                return 0;

        }
    }
}

