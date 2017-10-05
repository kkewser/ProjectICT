import java.util.ArrayList;


/**
 * Created by Kewser on 10/4/17.
 */
public class RandomSeed {



    public static ArrayList<Integer> randomSeed() {
        ArrayList<Integer> seed = new ArrayList<Integer>();
        int max = 550;


        System.out.println("Generate Prime numbers between 1 and " + max);


        // loop through the numbers one by one

        for (int i = 1; i < max; i++) {
            boolean isPrimeNumber = true;
            // check to see if the number is prime
            for (int j = 2; j < i; j++) {

                if (i % j == 0) {
                    isPrimeNumber = false;
                    break; // exit the inner for loop
                }
            }
            // print the number if prime

            if (isPrimeNumber) {
                seed.add(i);
                System.out.print(i + " ");

            }

        }

        //return seed;
        return seed;

    }


}
