package helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 鹿若 on 2018/1/30.
 */

public class DbManager {
    private static MySqliteHelper helper;
    public static MySqliteHelper getHelper(Context context){
        if(helper==null){
            helper = new MySqliteHelper(context);
        }
        return helper;
    }

    public static void execSQL(SQLiteDatabase db, String sql){
        int amount = 0;
        if(db != null){
            if(sql != null && !"".equals(sql)){
                db.execSQL(sql);
            }
        }
    }

    public static List<managerSearch> managerSearch(Cursor cursor){
        List<managerSearch> list = new ArrayList<>();
        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex(Constant.MANAGER_USERNAME));
            String password = cursor.getString(cursor.getColumnIndex(Constant.MANAGER_PASSWORD));
            managerSearch managerSearch=new managerSearch(name,password);
            list.add(managerSearch);
        }

        return list;
    }

    public static List<timeRange> timeRange(Cursor cursor){
        List<timeRange> list = new ArrayList<>();
        while (cursor.moveToNext()){
            String time = cursor.getString(cursor.getColumnIndex(Constant.TIMERANGE_TIME));
            String late = cursor.getString(cursor.getColumnIndex(Constant.TIMERANGE_LATETIME));
            timeRange timeRange=new timeRange(time,late);
            list.add(timeRange);
        }
        return list;
    }

    public static List<employeeSearch> employeeToList(Cursor cursor){
        List<employeeSearch> list = new ArrayList<>();
        while(cursor.moveToNext()){
            String ID = cursor.getString(cursor.getColumnIndex(Constant.EMPLOYEE_ID));
            String name = cursor.getString(cursor.getColumnIndex(Constant.EMPLOYEE_NAME));
            String password = cursor.getString(cursor.getColumnIndex(Constant.EMPLOYEE_Password));
            String department = cursor.getString(cursor.getColumnIndex(Constant.EMPLOYEE_DEPARTMENT));
            String position = cursor.getString(cursor.getColumnIndex(Constant.EMPLOYEE_POSITION));
            String facetoken =cursor.getString(cursor.getColumnIndex(Constant.EMPLOYEE_FACEID));
            employeeSearch employeeSearch=new employeeSearch(ID,name,password,department,position,facetoken);
            list.add(employeeSearch);

        }
        return list;

    }

    public static List<attendanceManager> allSearchCM(Cursor cursor){
        List<attendanceManager> list = new ArrayList<>();
        while (cursor.moveToNext()){
            String date = cursor.getString(cursor.getColumnIndex(Constant.ATTENDANCE_DATE));
            String inTime = cursor.getString(cursor.getColumnIndex(Constant.ATTENDANCE_CHECKINTIME));
            String outTime = cursor.getString(cursor.getColumnIndex(Constant.ATTENDANCE_CHECKOUTTIME));
            String Eid = cursor.getString(cursor.getColumnIndex(Constant.ATTENDANCE_EMPLOYEEID));
            String Ename = cursor.getString(cursor.getColumnIndex(Constant.ATTENDANCE_EMPLOYEEName));
            String lateTime = cursor.getString(cursor.getColumnIndex(Constant.ATTENDANCE_LATETIME));

            attendanceManager allSearchCM=new attendanceManager(date,inTime,outTime,Eid,Ename,lateTime);
            list.add(allSearchCM);
        }
        return list;
    }
}
