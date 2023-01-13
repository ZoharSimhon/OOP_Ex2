package taskMunAts.Ex2.Ex2_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.lang.Thread.sleep;

public class Ex2_1 {
    /**
     * <p>
     * Method number 1
     * This method accepts: <br>
     * n- natural number representing the number of text files.
     * seed - accepted in method Random.
     * bound- accepted in method nextInt.
     * This method creates n text files on disk and returns an array of file names. <br>
     * First, we create a String-type array (in the name namesOfFiles) at the size of n. <br>
     * Second, we use the Random class. We create randomNumber using the method Random(seed) <br>
     * that creates a new random number generator using a seed (type int). <br>
     * Then we use a for loop which is carried out as long as i<n. <br>
     * The number of lines in each file is a random number obtained using the method nextInt() which accepts the bound parameter. <br>
     * In every iteration :
     * We save the number of lines of the file in lines parameter. <br>
     * Use the File(String pathname) method that creates a new File instance by converting the given pathname string into an abstract pathname.
     * Use the createNewFile() method that atomically creates a new, empty file named by this abstract pathname if and only if a file with this name does not yet exist. <br>
     * Save the file's name in namesOfFiles array. <br>
     * Use the FileWriter(File file) method that constructs a FileWriter object given a File object. <br>
     * Throws: IOException - if the named file exists but is a directory rather than a regular file,does not exist but cannot be created, <br>
     * or cannot be opened for any other reason <br>
     * Use inner for loop that carried out as long as j < lines (lines- number of the lines (the number of lines in the file). <br>
     * In each line write the number of the line using the method write(String) that write some text to the file we created. <br>
     *Finally, we return the namesOfFiles array. <br>
     * </p>
     * <p>
     * @param n - natural number representing the number of text files.
     * @param seed - accepted in method Random.
     * @param bound - accepted in method nextInt.
     * @return namesOfFiles - namesOfFiles array (contains the file names).
     */
    public static String[] createTextFiles(int n, int seed, int bound)  {
        String[] namesOfFiles = new String[n];
        try {
            Random randomNumber = new Random(seed);
            for (int i = 0; i < n; i++) {
                int lines = randomNumber.nextInt(bound);
                File file = new File("file_" + (i + 1) + ".txt");
                file.createNewFile();
                namesOfFiles[i] = file.getName();
                FileWriter fw = new FileWriter(file);
                for (int j = 0; j < lines; j++) {
                    fw.write("The number of this line is: " + i + "\n");
                }
            }
        } catch (IOException ep) {
            ep.printStackTrace();
        }
        return namesOfFiles;
    }

    /**
     * <p>
     * Method number 2
     * This method accepts an array that contains the file names (fileNames) and returns the total number of lines of files . <br>
     * First, measure start time and initialize the variable countLine to be 0. <br>
     * Second, we use a for loop which is carried out as long as i < fileNames.length. <br>
     * In every iteration:
     * Use the method File(fileNames[i]) that creates a new File instance by converting the given pathname string into an abstract pathname.
     * Use the method Scanner(myObj) that constructs a new Scanner that produces values scanned from the specified file.
     * After we enter to a  while loop that lasts as long as : myReader.hasNextLine(). <br>
     * In the while loop we use the method nextLine() that advances this scanner past the current line and returns the input that was skipped.
     * Increase the value of the variable countLine by one. <br>
     * After the while loop we use the close() method that closes the stream and releases any system resources associated with it. <br>
     * Finally, we measure end time, perform printing and return the value of countLine(total number of lines of files). <br>
     * </p>
     * <p>
     * @param fileNames - File naming array.
     * @return countLine - total number of lines of files.
     * </p>
     */

    public static int getNumOfLines(String[] fileNames){
        long start = System.currentTimeMillis();
        int countLine =0;
        for (int i = 0; i < fileNames.length; i++) {
            try {
                File myObj = new File(fileNames[i]);
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    myReader.nextLine();
                    countLine++;
                }
                myReader.close();
            }
            catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("Method number 2: getNumOfLines , number of lines in the file:" + countLine + ",Running time: " + (end - start) + " millisecond.");
        return countLine;
    }

    /**
     * <p>
     * Method number 3
     * This method accepts an array that contains the file names (fileNames) and returns the total number of lines of files . <br>
     * In this method there is a use of Threads. We create the class runThread that extends Thread and in this class <br>
     * we implemented the method run() that calculates the number of lines in the file <br>
     * The Runnable interface is a functional interface and has a single run() method that doesn't accept any parameters or return any values.<br>
     * First we measure start time and create a runThread-type array (in the name linesFileThread) at the size of fileNames's size <br>
     * Secondly,with the help of the for loop we go through this array and and activate the start() method on each member of the array.<br>
     *This method causes the run() method to run. <br>
     * Third we initialize the variable sum to be 0. Then, we use the for loop again in order to activate on each thread the join() method <br>
     * When the join() method is invoked, the current thread stops its execution and the thread goes into the wait state. <br>
     * The current thread remains in the wait state until the thread on which the join() method is invoked has achieved its dead state. <br>
     * If interruption of the thread occurs, then it throws the InterruptedException. <br>
     * In addition, sum up the total number of lines in the files and initialize what is obtained in the sum variable. <br>
     * Finally, we measure end time, perform printing and return the value of sum. <br>
     * </p>
     *<p>
     * @param fileNames -File naming array.
     * @return sum - Total number of lines of files
     * </p>
     */
    public static int getNumOfLinesThreads(String[] fileNames) {
        long start = System.currentTimeMillis();
        runThread[] linesFileThread = new runThread[fileNames.length];
        for (int i = 0; i < linesFileThread.length; i++) {
            linesFileThread[i] = new runThread(fileNames[i]);
            linesFileThread[i].start();
        }
        int sum = 0;
        for (int i = 0; i < linesFileThread.length; i++) {
            try {
                linesFileThread[i].join();
            } catch (InterruptedException ep) {
                ep.printStackTrace();
            }
            sum = sum + linesFileThread[i].getNumLines();
        }
        long end = System.currentTimeMillis();
        System.out.println("Method number 3: getNumOfLinesThreads , number of lines in the file:" + sum + ",Running time: " + (end - start) + " millisecond.");
        return sum;
    }

    /**
     * <p>
     * Method number 4
     *  This method accepts an array that contains the file names (fileNames) and returns the total number of lines of files . <br>
     *  In this method there is a use of Thread Pool. We create the  class callThread that implements Callable<Integer> and in this class <br>
     *  we implemented the method call() that calculates the number of lines in the file <br>
     *  The Callable interface is a generic interface containing a single call() method that returns a generic value V . <br>
     *  First we measure start time and create a pool (at the size of fileNames's size) of a fixed number of threads. We are assisted by the Executors class
     *  (a class that provides static services for the creation of threads pools) and the method newFixedThreadPool() <br>
     *  which creates a pool of a fixed number of threads <br>
     *  Second, we create Future<Integer> -type list (in the name resultThread) which will save the future results of the threads <br>
     *  We use in Future<T> interface <br>
     *  Third we create variable result (type- Future<Integer>) and initialize the variable totalNumLines(type- int) to be 0. <br>
     * Fourth, we use a for loop in order to create fileNames's size tasks. For each task, the submit(theTask) method is used. <br>
     * submit(Callable<T> task)- Submits a value-returning task for execution and returns a Future representing the pending results of the task.<br>
     * Each result of a task is saved to a resultThread list (we use the add(result) method). <br>
     * Fifth, we use the for loop again in order to calculates the totalNumLines (total number of lines of files) <br>
     * In this loop we use the get() method. <br>
     * get() -waits if necessary for the computation to complete, and then retrieves its result. <br>
     * The method get() Throws: <br>
     * ExecutionException - if the computation threw an exception. <br>
     * InterruptedException - if the current thread was interrupted while waiting. <br>
     * After that, we activate the shutdown() method (threadPool.shutdown()). <br>
     * shutdown() - Initiates an orderly shutdown in which previously submitted tasks are executed, but no new tasks will be accepted. <br>
     * Finally, we measure end time, perform printing and return the value of totalNumLines.
     * </p>
     * @param fileNames -File naming array.
     * @return totalNumLines- Total number of lines of files.
     */
    public static int getNumOfLinesThreadPool(String[] fileNames){
        long start = System.currentTimeMillis();
        ExecutorService threadPool = Executors.newFixedThreadPool(fileNames.length);
        List<Future<Integer>> resultThread = new ArrayList<Future<Integer>>();
        Future<Integer> result;
        int totalNumLines=0;
        for (int i = 0; i < fileNames.length; i++)
        {
            callThread theTask = new callThread(fileNames[i]);
            result=threadPool.submit(theTask);
            resultThread.add(result);
        }
        for(int i=0;i<resultThread.size();i++)
        {
            try {
                totalNumLines=totalNumLines+resultThread.get(i).get();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        threadPool.shutdown();
        long end = System.currentTimeMillis();
        System.out.println("Method number 4: getNumOfLinesThreadPool , number of lines in the file: " + totalNumLines + ",Running time: " + (end - start) + " millisecond.");
        return totalNumLines;
    }

    /**
     *<p>
     * This method deletes the number of the files according to the value of numberFiles which we have accepted. <br>
     *</p>
     * <p>
     * @param numberFiles
     * </p>
     */
    public static void deleteFiles(int numberFiles) {
        for(int i=1; i<= numberFiles; i++){
            String name = "file_"+i+".txt";
            File file = new File(name);
            file.deleteOnExit();
        }
        System.out.println("All files deleted.");
    }

    /**
     *<p>
     * We call this method from the Ex2_1Test class. <br>
     *</p>
     * <p>
     * @param n - natural number representing the number of text files.
     * @param seed - accepted in method Random.
     * @param bound - accepted in method nextInt
     * @throws InterruptedException
     * </p>
     */
    public static void RunningTime(int n, int seed, int bound) throws InterruptedException {
        String[] filesNames = createTextFiles(n, seed, bound);
        for (int i = 2; i <= 4; i++) {
            System.out.println("----------------------------------------------");
            if (i == 2) getNumOfLines(filesNames);
            if (i == 3) getNumOfLinesThreads(filesNames);
            if (i == 4) getNumOfLinesThreadPool(filesNames);
            System.out.println("Finished test for function: " + i + "\n");
        }
        deleteFiles(n);
        sleep(500);
    }

    public static void main(String [] args) throws IOException {
        String [] nameOfFile = createTextFiles(1000,1,100);
        double start = System.currentTimeMillis();
        getNumOfLines(nameOfFile);
        double end = System.currentTimeMillis();
        System.out.println("The runtime of method 2: "+ (end-start)+ " millisecond\n");
        start = System.currentTimeMillis();
        getNumOfLinesThreads(nameOfFile);
        end = System.currentTimeMillis();
        System.out.println("The runtime of method 3: "+ (end-start) + " millisecond\n");
        start = System.currentTimeMillis();
        getNumOfLinesThreadPool(nameOfFile);
        end = System.currentTimeMillis();
        System.out.println("The runtime of method 4: "+ (end-start) + " millisecond\n");
    }

}

