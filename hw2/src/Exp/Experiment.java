package Exp;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *Exp.Experiment builds block for the Exp.ExperimentList
 */
public class Experiment {

    /**
     * This data field explains the experimental setup
     */
    private String setup;
    /**
     * This data field represents the day of start
     */
    private int day;
    /**
     * This data field represents the time of start
     */
    private String time;
    /**
     * This data field indicates whether it is completed or not
     */
    private boolean completed;
    /**
     * This data field represents the output (not a valid value if the experiment is not completed)
     */
    private float accuracy;

    /**
     * Constructor that takes all data fields
     * @param setup explains the experimental setup
     * @param day represents the day of start
     * @param time represents the time of start
     * @param completed indicates whether it is completed or not
     * @param accuracy represents the output
     */
    Experiment(String setup,int day , String time, boolean completed,  float accuracy){
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        Date date = new Date();
        time=dateFormat.format(date);

        this.setup = setup;
        this.day=day;
        this.time=time;
        this.completed=completed;
        this.accuracy=accuracy;
    }
    /**
     * Default constructor does nothing
     */
    Experiment()
    { }

    /**
     * Writes experiment information.
     * @return Return type is a String
     */
    @Override
    public String toString(){
        return "Setup: "+getSetup()+", Day: "+getDay()+", Time:"+getTime()+", Completed:"+getCompleted()+ ", Accuracy: "+getAccuracy();
    }
    /**
     * Setter method for setup.
     * @param setup explains the experimental setup
     */
    public void setSetup(String setup) {
        this.setup = setup;
    }
    /**
     * Getter method for setup
     * @return Return type is a String
     */
    public String getSetup() {
        return setup;
    }
    /**
     * Setter method for day
     * @param day represents the day of start
     */
    public void setDay(int day) {
        this.day = day;
    }
    /**
     * Getter method for day
     * @return Return type is a int
     */
    public int getDay() {
        return day;
    }
    /**
     * Setter method for time.
     * @param time represents the day of start
     */
    public void setTime(String  time) {
        this.time = time;
    }
    /**
     * Getter method for time
     * @return Return type is a String
     */
    public String getTime() {
        return time;
    }
    /**
     * Setter method for completed
     * @param completed indicates whether it is completed or not
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    /**
     * Getter method for completed
     * @return Return type is a boolean
     */
    public boolean getCompleted(){
        return completed;
    }
    /**
     * Setter method for accuracy
     * @param accuracy represents the output
     */
    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }
    /**
     * Getter method for accuracy
     * @return Return type is a float
     */
    public float getAccuracy() {
        return accuracy;
    }
}
