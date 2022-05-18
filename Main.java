/**
 * CLASS: Main (Main.java)
 *
 * DESCRIPTION
 * This program reads a given input file consisting of integers.
 * It will calculate runs - monotonically increasing or decreasing subsequences
 * of numbers contained within - and create an output file containing the total
 * number of runs as well as the number of runs for each k value
 * k = 1, 2, 3.. k=n-1.
 * Note that n denotes the number of integers contained in the input file; a
 * run is a (k+1) length subsequence of monotonically increasing or decreasing
 * integers.
 *
 * COURSE AND PROJECT INFO
 * CSE205 Object Oriented Programming and Data Structures, Summer 2022 C-Session
 * Project Number: p-01
 *
 * AUTHOR: David-McConnell, dmcconn7, dmcconn7@asu.edu
 *
 * Questions to ask:
 * Rules state no instance variables in the class; can I use final int RUNS_UP
 *  or "final String INPUT_FILE = "p01-in.txt" etc
 */
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {

    /**
     * TODO: Consider suppresswarnings unused assignment in run
     */
    final String INPUT_FILE = "p01-in.txt";
    final String OUTPUT_FILE = "p01-runs.txt";
    final int RUNS_UP = 1;
    final int RUNS_DN = 2;

    public static void main(String[] args) {
        /**
         * Runs the code implemented in run()
         */
        new Main().run();
    } // end main method

    private void run() {
        /**
         * This method implements the code to be ran in main
         */
        @SuppressWarnings("UnusedAssignment")   // try block causes
        ArrayList<Integer> list = new ArrayList<>();
        try {
            list = readInputFile(INPUT_FILE);   // populate list with integers
        } // end try
        catch (FileNotFoundException exception) {
            System.out.println("Oops, could not open 'p01-in.txt' for reading. The program is ending.");
            System.exit(-100); // error code specified in project requirements
        } // end catching FileNotFoundException for input file
        ArrayList<Integer> listRunsUpCount = findRuns(list, RUNS_UP); // populate with # of upward runs
        ArrayList<Integer> listRunsDnCount = findRuns(list, RUNS_DN); // populate with # of downward runs
        ArrayList<Integer> listRunsCount = mergeLists(listRunsUpCount, listRunsDnCount); // merge to find total runs
        try {
            writeOutputFile(OUTPUT_FILE, listRunsCount); // write data to output file
        } // end try
        catch (FileNotFoundException exception) {
            System.out.println("Oops, could not open 'p01-runs.txt' for writing. The program is ending.");
            System.exit(-200); // error code specified in project requirements
        } // end catching FileNotFoundException for output file
    } // end run method

    private ArrayList<Integer> findRuns(ArrayList<Integer> pList, int pDir) {
        /**
         * Find runs in the given array list
         *
         * @param pList ArrayList of integers
         * @param pDir Modal signifier; indicates whether finding upward or
         * downward runs
         * @return listRunsCount ArrayList of Integers indicating number of runs
         */
        ArrayList<Integer> listRunsCount = arrayListCreate(pList.size(), 0);
        int i = 0;  // track iterations through pList
        int k = 0;  // run counter
        while (i < pList.size() - 1) {
            if (pDir == RUNS_UP && pList.get(i) <= pList.get(i + 1)) { // Find only increasing values if direction is upward
                k++; // increment runs counter
            } // end if
            else if (pDir == RUNS_DN && pList.get(i) >= pList.get(i + 1)) { // Find only decreasing values if direction is downward
                k++; // increment runs counter
            } // end if
            else {  // record run of given length
                if (k != 0) {
                    listRunsCount.set(k, listRunsCount.get(k) + 1);
                    k = 0;
                } // end if
            } // end else
            i++;
        } // end while
        if (k != 0) {
            listRunsCount.set(k, listRunsCount.get(k) + 1);
        } // end if
        return listRunsCount;
    } // end findRuns method

    private ArrayList<Integer> mergeLists(ArrayList<Integer> pListRunsUpCount, ArrayList<Integer> pListRunsDnCount) {
        /**
         * Merge two array lists by summing their elements
         *
         * @param pListRunsUpCount ArrayList of integers to be merged
         * @param pListRunsDnCount ArrayList of integers to be merged
         * @return listRunsCount Merged ArrayList
         */
        ArrayList<Integer> listRunsCount = arrayListCreate(pListRunsUpCount.size(), 0);
        for (int i = 0; i < pListRunsUpCount.size(); i++) {
            int sum = pListRunsUpCount.get(i) + pListRunsDnCount.get(i);
            listRunsCount.set(i, sum);
        } // end for
        return listRunsCount;
    } // end mergeLists method

    public ArrayList<Integer> arrayListCreate(int pSize, int pInitValue) {
        /**
         * Creates ArrayList<Integer> of given size and initializes elements to
         * given value
         *
         * @param pSize Size of ArrayList to be created
         * @param pInitValue Initialization value
         * @return list Initialized ArrayList<Integer>
         */
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < pSize; i++) {
            list.add(pInitValue);
        } // end for
        return list;
    } // end arrayListCreate method

    private void writeOutputFile(String pFilename, ArrayList<Integer> pListRuns) throws FileNotFoundException {
        /**
         * Creates a file enumerating runs for given k-values
         *
         * @param pFilename File name to be written
         * @param pListRuns ArrayList<Integer> of runs to be written to file
         */
        PrintWriter out = new PrintWriter(pFilename);
        int runsTotal = getArrayListSum(pListRuns); // Fetch sum of runs in ArrayList
        out.println("runs_total: " + runsTotal); // Write sum
        for (int k = 1; k < pListRuns.size(); k++) {
            out.println("runs_" + k + ": " + pListRuns.get(k)); // Write count of each applicable k-value
        } // end for
        out.close();
    } // end writeOutputFile method

    private ArrayList<Integer> readInputFile(String pFilename) throws FileNotFoundException {
        /**
         * Reads a given file and returns ArrayList<Integer> of integers
         * contained within
         *
         * @param pFilename File name to be read
         * @return list ArrayList<Integer> of integers from file
         */
        File inputFile = new File(pFilename);
        Scanner in = new Scanner(inputFile);
        ArrayList<Integer> list = new ArrayList<>();
        while (in.hasNextInt()) {
            list.add(in.nextInt());
        } // end while
        in.close();
        return list;
    } // end readInputFile method

    private Integer getArrayListSum(ArrayList<Integer> list) {
        /**
         * Returns sum of values contained in given ArrayList<Integer>
         *
         * @param list ArrayList<Integer>
         * @return sum Sum of values
         */
        int sum = 0;
        for (Integer value : list) {
            sum += value;
        } // end for
        return sum;
    } // end getArrayListSum method
} // end Main class