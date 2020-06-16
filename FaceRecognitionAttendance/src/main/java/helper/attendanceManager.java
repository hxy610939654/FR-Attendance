package helper;

/**
 * Created by 鹿若 on 2018/3/7.
 */

public class attendanceManager {
    private String attendance_date;
    private String attendance_inTime;
    private String attendance_outTime;
    private String attendance_Eid;
    private String attendance_Ename;
    private String attendance_lateTime;

    public attendanceManager(String attendance_date, String attendance_inTime, String attendance_outTime, String attendance_Eid, String attendance_Ename, String attendance_lateTime) {
        this.attendance_date = attendance_date;
        this.attendance_inTime = attendance_inTime;
        this.attendance_outTime = attendance_outTime;
        this.attendance_Eid = attendance_Eid;
        this.attendance_Ename = attendance_Ename;
        this.attendance_lateTime = attendance_lateTime;
    }

    @Override
    public String toString() {
        return "Attendance date: "+attendance_date+"\nEmployeeID: "+
                attendance_Eid+"\nEmployee name:"+attendance_Eid+"\nCheck-in time:"+
                attendance_inTime+"\nCheck-out time:"+attendance_outTime+"\nLate time:"+attendance_lateTime+"\n\n";
    }

    public String getAttendance_date() {return attendance_date;}

    public void setAttendance_date(String attendance_date) {this.attendance_date = attendance_date;}

    public String getAttendance_inTime() {return attendance_inTime;}

    public void setAttendance_inTime(String attendance_inTime) {this.attendance_inTime = attendance_inTime;}

    public String getAttendance_outTime() {return attendance_outTime;}

    public void setAttendance_outTime(String attendance_outTime) {this.attendance_outTime = attendance_outTime;}

    public String getAttendance_Eid() {return attendance_Eid;}

    public void setAttendance_Eid(String attendance_Eid) {this.attendance_Eid = attendance_Eid;}

    public String getAttendance_Ename() {return attendance_Ename;}

    public void setAttendance_Ename(String attendance_Ename) {this.attendance_Ename = attendance_Ename;}

    public String getAttendance_lateTime() {return attendance_lateTime;}

    public void setAttendance_lateTime(String attendance_lateTime) {this.attendance_lateTime = attendance_lateTime;}
}
