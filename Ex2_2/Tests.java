package taskMunAts.Ex2.Ex2_2;

import org.junit.jupiter.api.*;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;



public class Tests {
    public static final Logger logger = LoggerFactory.getLogger(Tests.class);

    @BeforeAll
    static void runOnceBefore(){
        System.out.println("The test is starting now...");
        System.out.println("--------------------------------------------------");
    }
    @BeforeEach
    void initialize() {
        System.out.println("Start a new Test");
    }

    @AfterEach
    void afterEveryTest() {
        System.out.println("This test is overed");
        System.out.println("--------------------------------------------------");
    }

    @AfterAll
    static void runOnceAfter(){
        System.out.println("The test is done successfully");
    }

    /**
     * The test we have given
     */
    @Test
    public void partialTest(){
        System.out.println("partialTest");
        CustomExecutor customExecutor = new CustomExecutor();
        var task = Task.createTask(()->{
            int sum = 0;
            for (int i = 1; i <= 10; i++) {
                sum += i;
            }
            return sum;
        }, TaskType.COMPUTATIONAL);
        var sumTask = customExecutor.submit(task);
        final int sum;
        try {
            sum = sumTask.get(1, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        logger.info(()-> "Sum of 1 through 10 = " + sum);
        Callable<Double> callable1 = ()-> {
            return 1000 * Math.pow(1.02, 5);
        };
        Callable<String> callable2 = ()-> {
            StringBuilder sb = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            return sb.reverse().toString();
        };
        // var is used to infer the declared type automatically
        var priceTask = customExecutor.submit(()-> {
            return 1000 * Math.pow(1.02, 5);
        }, TaskType.COMPUTATIONAL);
        var reverseTask = customExecutor.submit(callable2, TaskType.IO);
        logger.info(()-> "Current maximum priority = " +
                customExecutor.getCurrentMax());
        final Double totalPrice;
        final String reversed;
        try {
            totalPrice = priceTask.get();
            reversed = reverseTask.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        logger.info(()-> "Reversed String = " + reversed);
        logger.info(()->String.valueOf("Total Price = " + totalPrice));
        logger.info(()-> "Current maximum priority = " +
                customExecutor.getCurrentMax());
        customExecutor.gracefullyTerminate();
    }

    /**
     * This test check if the task with the top priority happens before those with lower priority
     */
    @Test
    public void priorityTest() {
        System.out.println("priorityTest");
        //Creates new CustomExecutor
        CustomExecutor c = new CustomExecutor();

        //Creates two Callable objects
        Callable<String> callable1 = (() -> {
            Thread.sleep(1000);
            System.out.println("OTHER");
            return "OTHER";
        });
        Callable<String> callable2 = (() -> {
            Thread.sleep(1000);
            System.out.println("COMPUTATIONAL");
            return "COMPUTATIONAL";
        });

        //Creates 22 tasks with different priority
        Task<String> task1 = Task.createTask(callable1, TaskType.OTHER);
        Task<String> task2 = Task.createTask(callable1, TaskType.OTHER);
        Task<String> task3 = Task.createTask(callable1, TaskType.OTHER);
        Task<String> task4 = Task.createTask(callable1, TaskType.OTHER);
        Task<String> task5 = Task.createTask(callable1, TaskType.OTHER);
        Task<String> task6 = Task.createTask(callable1, TaskType.OTHER);
        Task<String> task7 = Task.createTask(callable1, TaskType.OTHER);
        Task<String> task8 = Task.createTask(callable1, TaskType.OTHER);
        Task<String> task9 = Task.createTask(callable1, TaskType.OTHER);
        Task<String> task10 = Task.createTask(callable1, TaskType.OTHER);
        Task<String> task11 = Task.createTask(callable1, TaskType.OTHER);
        Task<String> task12 = Task.createTask(callable1, TaskType.OTHER);
        Task<String> task13 = Task.createTask(callable2, TaskType.COMPUTATIONAL);
        Task<String> task14 = Task.createTask(callable2, TaskType.COMPUTATIONAL);
        Task<String> task15 = Task.createTask(callable2, TaskType.COMPUTATIONAL);
        Task<String> task16 = Task.createTask(callable2, TaskType.COMPUTATIONAL);
        Task<String> task17 = Task.createTask(callable2, TaskType.COMPUTATIONAL);
        Task<String> task18 = Task.createTask(callable2, TaskType.COMPUTATIONAL);
        Task<String> task19 = Task.createTask(callable2, TaskType.COMPUTATIONAL);
        Task<String> task20 = Task.createTask(callable2, TaskType.COMPUTATIONAL);
        Task<String> task21 = Task.createTask(callable2, TaskType.COMPUTATIONAL);
        Task<String> task22 = Task.createTask(callable2, TaskType.COMPUTATIONAL);

        //Check the setPriority method
        System.out.println("Before the setPriority method: "+ task2.priorityValue());
        task2.getTypePriority().setPriority(2);
        System.out.println("After the setPriority method:"+ task2.getTypePriority().getPriorityValue());

        //Saving all the future results
        Future<String> result1 = c.submit(task1);
        Future<String> result2 = c.submit(task2);
        Future<String> result3 = c.submit(task3);
        Future<String> result4 = c.submit(task4);
        Future<String> result5 = c.submit(task5);
        Future<String> result6 = c.submit(task6);
        Future<String> result7 = c.submit(task7);
        Future<String> result8 = c.submit(task8);
        Future<String> result9 = c.submit(task9);
        Future<String> result10 = c.submit(task10);
        Future<String> result11 = c.submit(task11);
        Future<String> result12 = c.submit(task12);
        Future<String> result13 = c.submit(task13);
        Future<String> result14 = c.submit(task14);
        Future<String> result15 = c.submit(task15);
        Future<String> result16 = c.submit(task16);
        Future<String> result17 = c.submit(task17);
        Future<String> result18 = c.submit(task18);
        Future<String> result19 = c.submit(task19);
        Future<String> result20 = c.submit(task20);
        Future<String> result21 = c.submit(task21);
        Future<String> result22 = c.submit(task22);

        //Check the CurrentMax priority in the queue and the CounterType array
        logger.info(() ->"Maximum priority in the queue is: " + String.valueOf(c.getCurrentMax()));
        logger.info(() -> "The counter array is:" + Arrays.toString(c.getCounterType()));

        //Check the CurrentMax priority in the queue and the CounterType array after sleep method
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            logger.error(() -> e.getMessage());
        }
        logger.info(() ->"Maximum priority in the queue is: " + String.valueOf(c.getCurrentMax()));
        logger.info(() -> "The counter array is:" + Arrays.toString(c.getCounterType()));

        //Check the CurrentMax priority in the queue and the CounterType array after sleep method
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            logger.error(() -> e.getMessage());
        }
        logger.info(() ->"Maximum priority in the queue is: " + String.valueOf(c.getCurrentMax()));
        logger.info(() -> "The counter array is:" + Arrays.toString(c.getCounterType()));

        //Check the CurrentMax priority in the queue and the CounterType array after sleep method
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            logger.error(() -> e.getMessage());
        }
        logger.info(() ->"Maximum priority in the queue is: " + String.valueOf(c.getCurrentMax()));
        logger.info(() -> "The counter array is:" + Arrays.toString(c.getCounterType()));

        //Check the CurrentMax priority in the queue and the CounterType array after sleep method
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            logger.error(() -> "Eror" + e.getMessage());
        }
        logger.info(() ->"Maximum priority in the queue is: " + String.valueOf(c.getCurrentMax()));
        logger.info(() -> "The counter array is:" + Arrays.toString(c.getCounterType()));

        //Checks whether the results were received correctly
        try {
            System.out.println("print all the tasks' result in the order that they created");
            System.out.println(result1.get());
            System.out.println(result2.get());
            System.out.println(result3.get());
            System.out.println(result4.get());
            System.out.println(result5.get());
            System.out.println(result6.get());
            System.out.println(result7.get());
            System.out.println(result8.get());
            System.out.println(result9.get());
            System.out.println(result10.get());
            System.out.println(result11.get());
            System.out.println(result12.get());
            System.out.println(result13.get());
            System.out.println(result14.get());
            System.out.println(result15.get());
            System.out.println(result16.get());
            System.out.println(result17.get());
            System.out.println(result18.get());
            System.out.println(result19.get());
            System.out.println(result20.get());
            System.out.println(result21.get());
            System.out.println(result22.get());
        } catch (Exception e) {
            logger.error(() -> e.getMessage());
        }
        //Terminate the process
        c.gracefullyTerminate();

        //Check the CurrentMax priority in the queue and the CounterType array after the terminate method
        logger.info(() ->"Maximum priority in the queue is: " + String.valueOf(c.getCurrentMax()));
        logger.info(() -> "The counter array is:" + Arrays.toString(c.getCounterType()));

    }

    /**
     * This test checks the submitting of many tasks
     */
    @Test
    public void manyTasksTest(){
        System.out.println("manyTasksTest");
        //Creates new CustomExecutor
        CustomExecutor c = new CustomExecutor();

        //Creates 500 tasks
        for (int i = 0; i < 500; i++) {
            int finalI = i;
            Task<Integer> task;
            Callable<Integer> callable = (() -> {
                Thread.sleep(100);
                return 10* finalI;
            });
            task= Task.createTask(callable,TaskType.OTHER);
            Future<Integer> f = c.submit(task);

            try{
                System.out.println(f.get());
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        //Terminate the CustomExecutor
        c.gracefullyTerminate();
    }

    /**
     *This test the compare method
     */
    @Test
    public void queueTest() {
        System.out.println("queueTest");
        PriorityQueue<Task> queue = new PriorityQueue<Task>();
        Task<Integer> task1 = Task.createTask(() -> {
            Thread.sleep(1000);
            return 1;
        }, TaskType.COMPUTATIONAL);
        Task<Integer> task2 = Task.createTask(() -> {
            Thread.sleep(1000);
            return 1;
        }, TaskType.IO);
        Task<Integer> task3 = Task.createTask(() -> {
            Thread.sleep(1000);
            return 1;
        }, TaskType.OTHER);
        queue.add(task2);
        queue.add(task1);
        queue.add(task3);
        while (!queue.isEmpty())
            System.out.println(queue.poll().priorityValue());

    }

    /**
     * Check the setPriority method
     */
    @Test
    void TaskTypeTest() {
        System.out.println("TaskTypeTest");
        TaskType tt = TaskType.OTHER;
        assertEquals(3, tt.getPriorityValue());
        tt.setPriority(8);
        assertEquals(8, tt.getPriorityValue());
    }

    /**
     * Check all the constructors
     */
    @Test
    void Task_constructors() {
        System.out.println("Task_constructors");
        Callable<Integer> callable = () -> 5*7;

        Task<Integer> t1 = Task.createTask(callable);
        Task<Integer> t2 = Task.createTask(callable, TaskType.OTHER);
        assertEquals(2, t1.priorityValue());
        assertEquals(3, t2.priorityValue());
    }


    @Test
    void Task_compare() {
        System.out.println("Task_compare");
        Callable<Integer> callable = () -> 123;
        Task<Integer> t1 = Task.createTask(callable, TaskType.IO);
        Callable<Integer> callable2 = () -> 456;
        Task<Integer> t2 = Task.createTask(callable2, TaskType.IO);
        Task<Integer> t3 = Task.createTask(callable2, TaskType.COMPUTATIONAL);
        Task<Integer> t4 = Task.createTask(callable2, TaskType.OTHER);
        assertEquals(0, t1.compareTo(t2));
        assertEquals(-1, t2.compareTo(t4));
        assertEquals(1, t2.compareTo(t3));

    }


}
