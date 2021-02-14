package izziv2;

public class Oseba {
    String ime, priimek;
    int letoR;
    private final static String[] IMENA = {"Andrej", "Janez", "Ivan", "Martin", "Ana", "Berta", "Cilka",
            "Tom", "Angela", "Maja", "Viktor", "Julia", "Josip", "Viktorija", "John"};
    private final static String[] PRIIMKI = {"Novak", "Horvat", "Oblak", "Zupan", "Hribar", "Broz", "Jug", "Merkel",
                                             "Travolta", "Nikolov", "Lennon", "Jelen", "Hanks", "Hopkins", "Roberts"};

    private final static int[] LETO_ROJSTVA = fillLetoRojstva();

    private static int[] fillLetoRojstva(){
        int[] rez = new int[101];
        for (int i = 0; i <= 100; i++) {
            rez[i] = i + 1920;
        }
        return rez;
    }

    Oseba(){
        this.ime = IMENA[(int)(Math.random() * IMENA.length)];
        this.priimek = PRIIMKI[(int)(Math.random() * PRIIMKI.length)];
        this.letoR = LETO_ROJSTVA[(int)(Math.random() * LETO_ROJSTVA.length)];
    }

    @Override
    public String toString(){
        return String.format("%s %s, %d",this.ime, this.priimek, this.letoR);
    }
}
