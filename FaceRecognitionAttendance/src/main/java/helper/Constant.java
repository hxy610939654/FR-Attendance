package helper;

import java.security.PublicKey;



public class Constant {
    public final static String KEY = "PnZXGiOLonSmGBgxpL_ztNCQvNvOX7tu";
    public final static String SECRET = "UFmSaJzN4kiE0W1ZeDy2QpEdyPyvmELq";

    public static final String DATABASE_NAME = "FRattendance.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_Admin = "Admin";
    public static final String ADMIN_USERNAME = "Username_admin";
    public static final String ADMIN_PASSWORD = "Password_admin";

    public static final String TABLE_Manager = "Manager";
    public static final String MANAGER_USERNAME ="Username_manager";
    public static final String MANAGER_PASSWORD ="Password_manager";

    public static final String TABLE_Employee = "Employee";
    public static final String EMPLOYEE_ID = "Employee_ID";
    public static final String EMPLOYEE_NAME = "Employee_Name";
    public static final String EMPLOYEE_Password = "Employee_Password";
    public static final String EMPLOYEE_DEPARTMENT = "Employee_Department";
    public static final String EMPLOYEE_POSITION = "Employee_Position";
    public static final String EMPLOYEE_FACEID = "Employee_FaceID";

    public static final String TABLE_Attendance = "Attendance";
    public static final String ATTENDANCE_DATE = "Attendance_Date";
    public static final String ATTENDANCE_CHECKINTIME = "Attendance_CheckInTime";
    public static final String ATTENDANCE_CHECKOUTTIME = "Attendance_CheckOutTime";
    public static final String ATTENDANCE_EMPLOYEEID = "Attendance_EmployeeID";
    public static final String ATTENDANCE_EMPLOYEEName = "Attendance_EmployeeName";
    public static final String ATTENDANCE_LATETIME = "Attendance_LateTime";

    public static final String TABLE_TIMERANGE = "TimeRange";
    public static final String TIMERANGE_TIME = "TimeRange_Time";
    public static final String TIMERANGE_LATETIME = "TimeRange_Late";

    public static final String TABLE_SELECT ="TableSelect";
    public static final String SELECT_TIME="Select_Time";
    public static final String SELECT_LATE="Select_Late";


}
