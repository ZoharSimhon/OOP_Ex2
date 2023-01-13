package taskMunAts.Ex2.Ex2_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class runThread extends Thread {
    /**
     * <p>
     * The class runThread extend the Thread class and override the run() method.<br>
     * </p>
     */
    private String nameOfFile; // the name of the file
    private int numLines; // number of lines in a file

    /**
     * A constructive method that accepts the file name (nameOfFile)  and creates a runThread-class object <br>
     * @param nameOfFile - the name of the file
     */
    public runThread(String nameOfFile){
        this.nameOfFile = nameOfFile;
        this.numLines=0;
    }

    /**
     *<p>
     * @return the value of numLines.
     *</p>
     */
    public int getNumLines()
    {
        return this.numLines;
    }

    /**
     *<p>
     * This method calculates the number of lines of the file (linesFileThread[i].start() ).
     * First, we initialize the variable count to 0 and define the variable theLine. <br>
     * Then, use the FileReader(String fileName) method that creates a new FileReader, given the name of the file to read from.<br>
     * Use the BufferedReader method that creates a buffering character-input stream that uses a default-sized input buffer. <br>
     * Use the readLine() method that Reads a line of text. A line is considered to be terminated by any one of a line feed ('\n'), <br>
     * a carriage return ('\r'), or a carriage return followed immediately by a linefeed. <br>
     * Finally updates the value of count in the variable this.numLines. <br>
     * readLine() method return a String containing the contents of the line, not including any line-termination characters, <br>
     * or null if the end of the stream has been reached. This method throws: IOException - If an I/O error occurs. <br>
     * After reading one line, you enter a while loop that lasts as long as : line != null. <br>
     * Increase the value of the variable count by one and again use the readLine() method. <br>
     * After the while loop we use the close() method: <br>
     * BufferedReader close() - Closes the stream and releases any system resources associated with it. <br>
     * Finally,we update the value of the numLines variable to be the value of count variable. <br>
     *</p>
     */
    public void run(){
        int count = 0;
        String theLine;
        try {
            FileReader fr= new FileReader(this.nameOfFile);
            BufferedReader br = new BufferedReader(fr);
            theLine= br.readLine();
            while (theLine!=null){
                count++;
                theLine= br.readLine();
            }
            br.close();
        } catch (IOException ep) {
            ep.printStackTrace();
        }
        this.numLines=count;
    }
}
