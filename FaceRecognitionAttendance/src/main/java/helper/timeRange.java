package helper;

/**
 * Created by 鹿若 on 2018/3/6.
 */

public class timeRange {
    private String time;
    private String late;


    public timeRange(String time, String late) {
        this.time = time;
        this.late = late;
    }

    @Override
    public String toString() {
        return "Current check-in time start at: "+time+"\nConsider as late check-in after "+late;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLate() {
        return late;
    }

    public void setLate(String late) {
        this.late = late;
    }
}
