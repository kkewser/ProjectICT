import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.System.out;
import static jdk.nashorn.internal.objects.NativeMath.round;

/**
 * Created by KataMar on 03/10/17.
 */
public class Run_simulations {

    public static void main(String args[]) throws java.io.IOException {

        Scanner scanner = new Scanner(System.in);
        //delete possible stats.txt
        File f_old_stats = new File("stats.txt");
        f_old_stats.delete();

        //ASK USER FOR ALL THE IMPORTANT INFO
        out.println("Number of runs of the simulation?");
        int numberOfRuns = scanner.nextInt();

        //ASK USER DIMENSIONS OF THE WORLD
        out.println("Give n, where n x n is the dimension of the matrix representing the world?");
        int size = scanner.nextInt();

        //ASK USER PROBABILITY OF ILL
       /* System.out.println("Probability of infection others?");
        double pill = scanner.nextDouble();
        while (pill < 0 || pill > 1) {
            System.out.println("Probability is out of allowed range 0 < P < 1 . \n Give the correct probability. ");
            pill = scanner.nextDouble();
        }
        */


        //ASK USER PROBABILITY OF DEAD
        out.println("Probability of dying?");
        double pdead = scanner.nextDouble();
        while (pdead < 0 || pdead > 1) {
            out.println("Probability is out of allowed range 0 < P < 1 . \n Give the correct probability. ");
            pdead = scanner.nextDouble();
        }

        //ASK USER THE INTERVAL OF ILL DAYS
        out.println("Give the minimum number of sick days. ");
        int minDays = scanner.nextInt();
        out.println("Give the max number of sick days. ");
        int maxDays = scanner.nextInt();
        while (maxDays < minDays) {
            out.println("The max days you inputed is larger than the inputed minimum days. Give max days again.");
            maxDays = scanner.nextInt();
        }

        //ASK USER COORDINATES OF INFECTED PERSON
        out.println("Coordinates of infected cell in the start of the simulation? ");
        out.println("Give the coordinate of the row.");
        int infectedM = scanner.nextInt();
        while (infectedM > size - 1) {
            out.println("The coordinate number is out of range of the population. Give another coordinate");
            infectedM = scanner.nextInt();
        }
        out.println("Give the coordinate of the column.");
        int infectedN = scanner.nextInt();

        while (infectedN > size - 1) {
            out.println("The coordinate number is out of range of the population. Give another coordinate");
            infectedN = scanner.nextInt();
        }

        // ASK THE USER FOR OUTPUT RESULT TO DISPLAY
        out.println("Output to display choose from the given alternative: ");
        out.println("1-Number of individuals who get infected per day");
        out.println("2-Number of individuals who died per day");
        out.println("3- Number of individuals who have recovered per day:");
        out.println("4-Number of ill per day");
        out.println("5-Accumulated number of infected people per day");
        out.println("6-Accumulated number of deaths per day");
        out.println("7-All");

        int display = scanner.nextInt();

        while (display < 0 && display > 7) {
            out.println("Choose only from given alternative");
            display = scanner.nextInt();
        }

        ArrayList<Double> probabilities = new ArrayList<Double>();
        double p = 0.00;
        while (p < 1) {
            probabilities.add(p);
            p = p + 0.05;

            DecimalFormat df = new DecimalFormat("#.##");
            p = Double.valueOf(df.format(p));
            System.out.println("p = "+p);


        }
        probabilities.add(p);

        //OUTPUT DATA TO A FILE
        File outStats2 = new File("stats.txt");
        FileOutputStream outstream2 = null;
        try{
            outstream2 = new FileOutputStream(outStats2, true);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        PrintStream output2 = new PrintStream(outstream2);
        output2.printf("PROBABILIIES");
        output2.print("   ");
        //average
        output2.printf("AVERAGE");
        output2.print("    ");
        //sd
        output2.printf("SD");
        output2.print("     ");
        //CI lowe bound
        output2.printf("LOWER_BOUND_CI");
        output2.print("   ");
        //CI upper bound
        output2.printf("UPPER_BOUND_CI");
        output2.println("   ");
        output2.close();
        RandomSeed seed=new RandomSeed();
        ArrayList<Integer> differentseed=seed.randomSeed();

     for (int j = 0; j<probabilities.size(); j++) {
            File f_old = new File("data_for_average_of_suffered.txt");

            f_old.delete();
                for (int i = 0; i < numberOfRuns; i++) {
                    LifeCycle life = new LifeCycle();
                    life.run(size, probabilities.get(j) , pdead, minDays, maxDays, infectedM, infectedN, differentseed.get(i),display) ;
                }
         File out3 = new File("data_for_average_of_suffered_for_the_wholeTrail.txt");
         FileOutputStream outstream3 = null;
         try{
             outstream3 = new FileOutputStream(out3, true);
         }catch(FileNotFoundException e){
             e.printStackTrace();
         }

         PrintStream output3 = new PrintStream(outstream3);
         //output.println(numberOfSuffered);
         output3.println("#");

         output3.close();

                Average a = new Average();
                Estimating_S s = new Estimating_S();
                CI ci = new CI();
                double [] interval = ci.conf_interval();
                out.println("Probability P: "+ probabilities.get(j));
                out.println("Average of the population who have suffered the infection:  " + a.average());


                //OUTPUT DATA TO A FILE
                File outStats = new File("stats.txt");
                FileOutputStream outstream = null;
                try{
                    outstream = new FileOutputStream(outStats, true);
                }catch(FileNotFoundException e){
                    e.printStackTrace();
                }
                PrintStream output = new PrintStream(outstream);
             output.print(probabilities.get(j));
                output.print("             ");
                //average
                output.printf("%.2f",a.average());
                output.print("     ");
                //sd
                output.printf("%.2f",s.estimate_S());
                output.print("          ");
                //CI lowe bound
                output.printf("%.2f",interval[0]);
                output.print("           ");
                //CI upper bound
                output.printf("%.2f",interval[1]);
                output.println("   ");
                output.close();

        }





    }

}
