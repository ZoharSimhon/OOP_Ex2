package taskMunAts.Ex2.Ex2_2;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;


/**
 * This class implements the {@code Comparable <Task<T>>} interface and the {@code Callable<T>} interface.  <br>
 * This class inherits attributes and methods from the {@code FutureTask<T>} class. <br>
 * By creating an object from this cals, we can create a new task that will be done at some point.
 * @author Linor Ronen & Zohar Simhon
 */
public class Task<T> extends FutureTask<T> implements Comparable <Task<T>>, Callable<T>{
    private TaskType typePriority;//represents the priority of the task
    private Callable<T> tCallable; //represents the function of the task
    private boolean isWaiting= false;

    /**
     * A private constructor - gets two parameters and copies them.
     * This function uses the super's constructor. <br>
     * @param typePriority {@code TaskType} object, represents the priority value of the current task
     * @param tCallable {@code Callable<T>} object, represents the task we want to make.
     */
    private Task(TaskType typePriority, Callable<T> tCallable) {
        super(tCallable);
        this.tCallable =tCallable;
        this.typePriority = typePriority;
    }

    /**
     * A private constructor - gets only one parameter and copies it. <br>
     *  This function uses the super's constructor
     * The {@code typePriority} gets a default priority - {@code IO} priority.
     * @param tCallable {@code Callable<T>} object
     */
    private Task(Callable<T> tCallable) {
        super(tCallable);
        this.tCallable =tCallable;
        //default priority
        this.typePriority = TaskType.IO;
    }

    /**
     * Getters functions
     */

    /**
     * Get function
     * @return this.tCallable - {@code Callable<T>} object
     */
    public <T> Callable<T> getCallable(){
        return (Callable<T>) this.tCallable;
    }

    /**
     * Get function
     * @return this.typePriority - {@code TaskType} object
     */
    public TaskType getTypePriority(){
        return this.typePriority;
    }

    /**
     * Get function
     * @return this.isWaiting - {@code boolean} object
     */
    public boolean getISWaiting (){
        return this.isWaiting;
    }

    /**
     * Setters functions
     */

    /**
     * Set function
     * @param tCallable returns the current value of {@code this.tCallable}
     */
    public void setCallable(Callable<T> tCallable){
        this.tCallable =tCallable;
    }


    /**
     * Set function
     * @param isWaiting returns the current value of {@code this.isWaiting}
     */
    public void setISWaiting (boolean isWaiting){
        this.isWaiting = isWaiting;
    }

    /**
     * Set function
     * @param typePriority returns the current value of {@code this.typePriority}
     */
    public void setTypePriority(TaskType typePriority){
        this.typePriority =typePriority;
    }

    /**
     * This function creates a new task by using the constructor function. <br>
     * This is the only way to create a new Task<T> object. (because the constructor methods are private) <br>
     * The function gets two parameters:
     * @param typePriority {@code TaskType} object, represents the priority value of the current task
     * @param tCallable {@code Callable<T>} object
     * @return a new Task<T> object
     */
    public static <T> Task<T> createTask(Callable<T> tCallable, TaskType typePriority){
        return new Task<T>(typePriority, tCallable);
    }

    /**
     * This function creates a new task by using the constructor function. <br>
     * This is the only way to create a new Task<T> object. (because the constructor methods are private) <br>
     * The function gets one parameter, and the {@code typePriority} gets a default value.
     * @param tCallable {@code Callable<T>} object
     * @return a new Task<T> object
     */
    public static <T> Task<T> createTask(Callable<T> tCallable){
        return new Task<T>(tCallable);
    }

    /**
     * The function returns the number of the priority.
     * @return 1 if the {@code typePriority} is {@code COMPUTATIONAL} <br>
     *         2 if the {@code typePriority} is {@code IO} <br>
     *         3 if the {@code typePriority} is {@code OTHER} <br>
     *         -1 otherwise
     */
    public int priorityValue(){
        switch (this.typePriority.toString()){
            case "Computational Task":
                return 1;
            case "IO-Bound Task":
                return 2;
            case "Unknown Task":
                return 3;
            default:
                return -1;
        }
    }

    /**
     * This is the function from the {@code Callable<T>} interface. <br>
     * This function computes a result, or catch an exception if unable to do so.
     * @return a generic value that represent the computed result
     */
    @Override
    public T call(){
        try{
            T t= this.tCallable.call();
            //this.isWaiting= false;
            return t;
        }
        catch(Exception exception){
            System.out.println("call() throws Exception");
            return  null;
        }
    }

    /**
     * This is the function from the {@code  Comparable <Task<T>>} interface. <br>
     * This function gets another Task<T> and compare it to the current Task<T>. <br>
     * The comparison made by  the priority value.
     * @param other another {@code Task<T>} object
     * @return 1 when other's {@code priorityValue} is greater than this {@code priorityValue}. <br>
     *        -1 when other's {@code priorityValue} is smaller than this {@code priorityValue}. <br>
     *         0 when other's {@code priorityValue} is equal to this {@code priorityValue}.
     */
    @Override
    public int compareTo(Task<T> other) {
        int diff = this.priorityValue() - other.priorityValue();
        return Integer.signum(diff);
    }

    /**
     * @param o a general object
     * @return <u>true</u> - if o and the current {@code Task<T>} are equals or if the o's typePriority and o's tCallable
     * are equals to this.typePriority and this.tCallable.<br>
     * <u>false</u> - otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task<?> task = (Task<?>) o;
        return this.typePriority == task.getTypePriority() && this.tCallable.equals(task.tCallable);
    }

    /**
     *  The main purpose ot this method  is to facilitate hashing in hash tables, which are used by data structures.
     *  The method will return the same hash code for two {@code Task<T>}  objects, only if they are equals.
     * @return int value that is associated with the {@code this.typePriority, this.tCallable}.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.typePriority, this.tCallable);
    }
}



