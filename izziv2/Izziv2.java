package izziv2;

public class Izziv2 {

    static int numberOfPeople(int max) {
        return (int)(Math.random() * max);
    }
    static void randomEntry(LinkedSequence<Oseba> seq) throws CollectionException {
        int choose = (int) (3 * Math.random()); //add, insert, set
        switch (choose){
            case 0: {
                Oseba temp = new Oseba();
                seq.add(temp);
                System.out.println("ADD: We added :" + temp);
                break;
            }
            case 1: {
               int rnd = (int) (Math.random() * seq.size());
               Oseba temp = new Oseba();
                seq.insert(rnd, temp);
                System.out.println("INSERT: We inserted " + temp + " at the index " + rnd);
                break;
            }
            case 2: {
                int rnd = (int) (Math.random() * seq.size());
                Oseba temp = new Oseba();
                Oseba o = seq.set(rnd, temp);
                System.out.println("SET: We set " + temp + " at the place of " + o + " with index " + rnd );
                break;
            }

        }
    }

    static void randomDelete(LinkedSequence<Oseba> seq) throws CollectionException {
        int maxDeletes = (int)(Math.random() * seq.size() / 2);
        for (int i = 0; i < maxDeletes; i++) {
            int rnd = (int)(Math.random() * seq.size());
           Oseba x = seq.delete(rnd);
            System.out.println("DELETE: We deleted " + x +" at index " + rnd);
        }

    }

    public static void main(String[] args) {
        LinkedSequence<Oseba> seq = new LinkedSequence<>();
        try {
            Oseba o1 = new Oseba();
            Oseba o2 = new Oseba();
            System.out.println("ADD: First we add: " + o1 + " and " + o2);
            seq.add(o1); seq.add(o2);
            for (int i = 0; i < numberOfPeople(12) ; i++) {
                randomEntry(seq);
            }
            System.out.println("The size of the sequence is: " +seq.size());
            randomDelete(seq);
            System.out.println("-----------");
            System.out.println(seq);
        }catch (CollectionException e){
            System.out.println(e.getMessage());
        }
    }
}
