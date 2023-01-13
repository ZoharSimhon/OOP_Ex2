package taskMunAts.Ex2.Ex2_1;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class Ex2_1Test extends Ex2_1{

    @BeforeAll
    public static void runOnceBefore() {
        System.out.println("The test is starting now...");
        System.out.println("--------------------------------------------------");
    }

    @BeforeEach
    void initialize() {
        System.out.println("Start a new Test");
    }

    @Test
    void createTextFilesTest() {
        System.out.println("createTextFilesTest");
        Throwable exception1 = assertThrows(NegativeArraySizeException.class,
                ()->{Ex2_1.createTextFiles(-800,7,1000);});

        Throwable exception2 = assertThrows(IllegalArgumentException.class,
                ()->{Ex2_1.createTextFiles(115,9,-99999);});

    }

    @Test
    void runningTimeTest() throws InterruptedException {
        System.out.println("runningTimeTest");
        int n = 100;
        int seed = (int)(Math.random()*100);
        int bound = 99999;
        System.out.println("The Number of the files: " + n + ",The value of seed: " + seed + ",The value of bound: " + bound);
        RunningTime(n, seed, bound);
        n=500;
        System.out.println("New Test:\n The Number of the files: " + n + ",The value of seed: " + seed + ",The value of bound: " + bound);
        RunningTime(n, seed, bound);
        n=1000;
        System.out.println("New Test:\n The Number of the files: " + n + ",The value of seed: " + seed + ",The value of bound: " + bound);
        RunningTime(n, seed, bound);
    }

    @AfterEach
    void afterEveryTest() {
        System.out.println("This test is overed");
        System.out.println("--------------------------------------------------");
    }

    @AfterAll
    public static void runOnceAfter_deleteFiles() {
        System.out.println("The test is done successfully");
    }
}
