package helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by 鹿若 on 2018/1/30.
 */

public class MySqliteHelper extends SQLiteOpenHelper{
    public MySqliteHelper(Context context, String name,
                          SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MySqliteHelper(Context context){
        super (context, Constant.DATABASE_NAME,null,Constant.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("tag","onCreate");
        String Admin = "create table "+Constant.TABLE_Admin+"("+Constant.ADMIN_USERNAME+
                " varchar(50) primary key,"+Constant.ADMIN_PASSWORD+" varchar(50))";
        String Manager = "create table "+Constant.TABLE_Manager+"("+Constant.MANAGER_USERNAME+
                " varchar(50) primary key,"+Constant.MANAGER_PASSWORD+" varchar(50))";
        String Employee = "create table "+Constant.TABLE_Employee+"("+Constant.EMPLOYEE_ID+
                " varchar(50) primary key,"+Constant.EMPLOYEE_NAME+" varchar(50),"+Constant.EMPLOYEE_Password+
                " varchar(50),"+Constant.EMPLOYEE_DEPARTMENT+" varchar(50),"+Constant.EMPLOYEE_POSITION+
                " varchar(50),"+Constant.EMPLOYEE_FACEID+" varchar(50))";
        String Attendance = "create table "+Constant.TABLE_Attendance+"("+Constant.ATTENDANCE_DATE+
                " date,"+Constant.ATTENDANCE_CHECKINTIME+" varchar(50),"+Constant.ATTENDANCE_CHECKOUTTIME+
                " varchar(50),"+Constant.ATTENDANCE_EMPLOYEEID+" varchar(50),"+Constant.ATTENDANCE_EMPLOYEEName+
                " varchar(50),"+Constant.ATTENDANCE_LATETIME+" varchar(50))";
        String TimeRange = "create table "+Constant.TABLE_TIMERANGE+"("+Constant.TIMERANGE_TIME+
                " daytime,"+Constant.TIMERANGE_LATETIME+" daytime)";
        String Select = "create table "+Constant.TABLE_SELECT+"("+Constant.SELECT_TIME+
                " varchar(50),"+Constant.SELECT_LATE+" varchar(50))";
        db.execSQL(Admin);
        db.execSQL(Manager);
        db.execSQL(Employee);
        db.execSQL(Attendance);
        db.execSQL(TimeRange);
        db.execSQL(Select);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.i("tag","onUpgrade");
    }
}
