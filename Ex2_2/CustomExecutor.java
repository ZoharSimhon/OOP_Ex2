package taskMunAts.Ex2.Ex2_2;

import java.util.Arrays;
import java.util.concurrent.*;

/**
 * This class presents a new type of {@code ThreadPoolExecutor} that supports a priority queue tasks
 * (Each task in the queue is of type Task). <br>
 * This class inherits attributes and methods from the {@code ThreadPoolExecutor} class. <br>
 * @author Linor Ronen & Zohar Simhon
 */
public class CustomExecutor extends ThreadPoolExecutor {

    private static int NumOfCores= Runtime.getRuntime().availableProcessors();
    //presents the number of tasks that are waiting for being done
    //The first value - represents the counter of COMPUTATIONAL tasks
    //The second value - represents the counter of IO tasks
    //The third value - represents the counter of OTHER tasks
    private int [] counterType = {0,0,0};

    /**
     * <p>
     * An empty constructor.<br>
     * This function uses the super's constructor.
     * </p>
     * <p>
     * <u>{@code param:}</u> <br>
     * <u>corePoolSize</u> - NumOfCores/2 - the minimum number of threads that we can use <br>
     * <u>maximumPoolSize</u> - NumOfCores-1 - the maximum number of threads that we can use in order to save one to
     * Java Virtual Machine <br>
     * <u>{@code keepAliveTime}</u> - 300 MILLISECONDS - represents the maximum time that a thread can be in the queue
     * without getting any mission <br>
     * <u>{@code workQueue}</u> - a PriorityBlockingQueue that manage all the tasks that waiting for be done.
     * </p>
     */
    public CustomExecutor() {
        super(NumOfCores/2, NumOfCores-1,
                300, TimeUnit.MILLISECONDS,
                new PriorityBlockingQueue<>() );
    }

    /**
     *Getters:
     */

    /**
     * Get function
     * @return this.counterType - an array that present the number of task for each value of priority
     */
    public int[] getCounterType() {
        return counterType;
    }
    /**
     * Get function
     * @return the important priority that waiting in the queue <br>
     * The function will return 0, if there is not any task in the queue.
     */
    public int getCurrentMax() {
        if (counterType[0] > 0)
            return 1;
        if (counterType[1] > 0)
            return 2;
        if (counterType[2] > 0)
            return 3;
        return 0;
    }

    /**
     * This function submits new task to the priority task queue. <br>
     * So, we update the queue accordingly. <br>
     * This function uses the {@code execute} function which we inherit from the {@code ThreadPoolExecutor} class.
     * @param task a {@code Task<T>} object
     * @return ftask - a {@code Future<T>} object, represents the pending results of the task.
     */
    public <T> Future<T> submit (Task<T> task){
        if (task == null || task.getCallable() == null)
            throw new NullPointerException();
        task.setISWaiting(true);

        counterType[task.priorityValue()-1]++;
        RunnableFuture<T> ftask = newTaskFor(task);
        execute(ftask);
        return ftask;
    }

    /**
     * This function submits new task to the priority task queue. <br>
     * We get two parameters, so first, we create a new task object by using the appropriate constructor. <br>
     * Then, we call the submit method that we have done before.
     * @param tCallable {@code Callable<T>} object, represents the task we want to make.
     * @param typePriority {@code TaskType} object, represents the priority value of the current task
     * @return ftask - a {@code Future<T>} object, represents the pending results of the task.
     */
    public <T> Future<T> submit (Callable<T> tCallable, TaskType typePriority){
        Task<T> task = Task.createTask(tCallable, typePriority);
        return submit(task);
    }

    /**
     * This function submits new task to the priority task queue. <br>
     * We get two parameters, so first, we create a new task object by using the appropriate constructor. <br>
     * Then, we call the submit method that we have done before.
     * @param tCallable {@code Callable<T>} object, represents the task we want to make.
     * @return ftask - a {@code Future<T>} object, represents the pending results of the task.
     */
    @Override
    public <T> Future<T> submit (Callable<T> tCallable){
        Task<T> task = Task.createTask(tCallable);
        return submit(task);
    }

    /**
     * This method is invoked before executing the given Runnable in the given thread.<br>
     * This method is invoked by thread t that will execute task r, and may be used to re-initialize ThreadLocals, or to perform logging.
     * The task that will be executed is the task with the nax priority. <br>
     * So, we update the queue accordingly
     * @param t The thread that will run task r
     * @param r The task that will be executed
     */
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        int priority = getCurrentMax();
        if (1<=priority && priority<=3)
            counterType[priority-1]--;
    }

    /**
     * This method returns a {@code RunnableFuture} for the given callable task.
     * @param callable the callable task being wrapped
     * @param <T> the type of the callable's result
     * @return a {@code RunnableFuture<T>} which, when run, will call the
     * underlying callable and which, as a {@code Future}, will yield
     * the callable's result as its result and provide for
     * cancellation of the underlying task
     */
    @Override
    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        int currPriority = getCurrentMax();
        //default
        TaskType type = TaskType.IO;
        if (1<=currPriority && currPriority<=3)
            type.setPriority(currPriority);
        return Task.createTask(callable, type);
    }

    /**
     * By calling this method we: <br>
     * First, don't allow insertion of additional tasks into the queue <br>
     * Second, Complete all tasks remaining in the queue <br>
     * Third, terminate all tasks currently in progress in the CustomExecutor's threads collection<br>
     * In order to do this, we use the shutdown and the awaitTermination methods
     * that we  inherited a from the {@code ThreadPoolExecutor} class
     */
    public void gracefullyTerminate(){
        try {
            super.awaitTermination(5,TimeUnit.SECONDS);
            super.shutdown();
            System.out.println("done");
        }
        catch (InterruptedException ex) {
            ex.getMessage();
        }
    }

    /**
     * @param o a general object
     * @return <u>true</u> - if o and the current {@code CustomExecutor} are equals or if o's getCounterType
     * is equal to this.getCounterType<br>
     * <u>false</u> - otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomExecutor)) return false;
        CustomExecutor that = (CustomExecutor) o;
        return Arrays.equals(getCounterType(), that.getCounterType());
    }

    /**
     *  The main purpose ot this method  is to facilitate hashing in hash tables, which are used by data structures.
     *  The method will return the same hash code for two {@code Task<T>}  objects, only if they are equals.
     * @return int value that is associated with the {@code this.typePriority, this.tCallable}.
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(getCounterType());
    }

}


