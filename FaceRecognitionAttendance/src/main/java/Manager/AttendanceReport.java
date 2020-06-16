package Manager;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.huang.frattendance.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import helper.Constant;
import helper.DbManager;
import helper.MySqliteHelper;
import helper.attendanceManager;
import helper.employeeSearch;

/**
 * Created by 鹿若 on 2018/1/30.
 */

public class AttendanceReport extends Activity {
    private MySqliteHelper helper;
    private TextView tv;
    private EditText search;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd");
    private String currentMonth;
    private String lastMonth;
    private String pastTmonth;
    private Date Current;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendance_report);
        helper= DbManager.getHelper(this);
        initViews();

    }
    public void initViews() {
        search=findViewById(R.id.ATRsearch);
        tv=findViewById(R.id.ARManager);
    }
    public void CMAall(View view){
        SQLiteDatabase db = helper.getWritableDatabase();
        boolean count = false;
        Date currenMonth;
        Calendar calendar = Calendar.getInstance();
        Current = new Date(System.currentTimeMillis());
        calendar.setTime(Current);
        currenMonth = calendar.getTime();
        currentMonth = sdf.format(currenMonth);
        Cursor cursor = db.query(Constant.TABLE_Attendance, null, Constant.ATTENDANCE_DATE + "=?",
                new String[]{currentMonth}, null, null, null);
        List<attendanceManager> list = DbManager.allSearchCM(cursor);
        tv.setMovementMethod(new ScrollingMovementMethod());
        String aa="";
        int i = 0;
        int a = 0;
        int b = 0;
        for (attendanceManager e : list) {
            i++;
            count = true;
        }
        String[] report =new String[i+1];
        for (attendanceManager e : list){
            report[a]=e.toString();
            a++;
        }
        while (report[b]!=null){
            aa=aa+report[b];
            b++;
        }
        tv.setText(aa);
        if(count== false){
            tv.setText("The attendance record form "+currentMonth+" until now is not empty.");
        }
        db.close();
    }

    public void PMAall(View view){
        SQLiteDatabase db = helper.getWritableDatabase();
        boolean count = false;
        Date lastM;
        Calendar calendar = Calendar.getInstance();
        Current = new Date(System.currentTimeMillis());
        calendar.setTime(Current);
        calendar.add(calendar.MONTH, -1);
        lastM = calendar.getTime();
        lastMonth = sdf.format(lastM);
        Cursor cursor = db.query(Constant.TABLE_Attendance, null, Constant.ATTENDANCE_DATE + ">=?",
                new String[]{lastMonth}, null, null, null);
        List<attendanceManager> list = DbManager.allSearchCM(cursor);
        tv.setMovementMethod(new ScrollingMovementMethod());
        String aa="";
        int i = 0;
        int a = 0;
        int b = 0;
        for (attendanceManager e : list) {
            i++;
            count = true;
        }
        String[] report =new String[i+1];
        for (attendanceManager e : list){
            report[a]=e.toString();
            a++;
        }
        while (report[b]!=null){
            aa=aa+report[b];
            b++;
        }
        tv.setText(aa);
        if(count== false){
            tv.setText("The attendance record form "+currentMonth+" until now is not empty.");
        }
        db.close();
    }
    public void PTMAall(View view){
        SQLiteDatabase db = helper.getWritableDatabase();
        boolean count = false;
        Date PtM;
        Calendar calendar = Calendar.getInstance();
        Current = new Date(System.currentTimeMillis());
        calendar.setTime(Current);
        calendar.add(calendar.MONTH, -2);
        PtM = calendar.getTime();
        pastTmonth = sdf.format(PtM);
        Cursor cursor = db.query(Constant.TABLE_Attendance, null, Constant.ATTENDANCE_DATE + ">=?",
                new String[]{pastTmonth}, null, null, null);
        List<attendanceManager> list = DbManager.allSearchCM(cursor);
        tv.setMovementMethod(new ScrollingMovementMethod());
        String aa="";
        int i = 0;
        int a = 0;
        int b = 0;
        for (attendanceManager e : list) {
            i++;
            count = true;
        }
        String[] report =new String[i+1];
        for (attendanceManager e : list){
            report[a]=e.toString();
            a++;
        }
        while (report[b]!=null){
            aa=aa+report[b];
            b++;
        }
        tv.setText(aa);
        if(count== false){
            tv.setText("The attendance record form "+currentMonth+" until now is not empty.");
        }
        db.close();

    }
    public void CMAe(View view){
        SQLiteDatabase db = helper.getWritableDatabase();
        boolean count = false;
        String id= search.getText().toString();
        Date currenMonth;
        Calendar calendar = Calendar.getInstance();
        Current = new Date(System.currentTimeMillis());
        calendar.setTime(Current);
        currenMonth = calendar.getTime();
        currentMonth = sdf.format(currenMonth);
        Cursor cursor = db.query(Constant.TABLE_Attendance, null, Constant.ATTENDANCE_DATE + "=? and "+Constant.ATTENDANCE_EMPLOYEEID+"=?",
                new String[]{currentMonth,id}, null, null, null);
        List<attendanceManager> list = DbManager.allSearchCM(cursor);
        tv.setMovementMethod(new ScrollingMovementMethod());
        String aa="";
        int i = 0;
        int a = 0;
        int b = 0;
        for (attendanceManager e : list) {
            i++;
            count = true;
        }
        String[] report =new String[i+1];
        for (attendanceManager e : list){
            report[a]=e.toString();
            a++;
        }
        while (report[b]!=null){
            aa=aa+report[b];
            b++;
        }
        tv.setText(aa);
        if(count== false){
            tv.setText("The attendance record form "+currentMonth+" until now is not empty, or employee ID incorrect.");
        }
        db.close();
    }
    public void PMAe(View view){
        SQLiteDatabase db = helper.getWritableDatabase();
        boolean count = false;
        Date lastM;
        String id= search.getText().toString();
        Calendar calendar = Calendar.getInstance();
        Current = new Date(System.currentTimeMillis());
        calendar.setTime(Current);
        calendar.add(calendar.MONTH, -1);
        lastM = calendar.getTime();
        lastMonth = sdf.format(lastM);
        Cursor cursor = db.query(Constant.TABLE_Attendance, null, Constant.ATTENDANCE_DATE + ">=? and "+Constant.ATTENDANCE_EMPLOYEEID+"=?",
                new String[]{lastMonth,id}, null, null, null);
        List<attendanceManager> list = DbManager.allSearchCM(cursor);
        tv.setMovementMethod(new ScrollingMovementMethod());
        String aa="";
        int i = 0;
        int a = 0;
        int b = 0;
        for (attendanceManager e : list) {
            i++;
            count = true;
        }
        String[] report =new String[i+1];
        for (attendanceManager e : list){
            report[a]=e.toString();
            a++;
        }
        while (report[b]!=null){
            aa=aa+report[b];
            b++;
        }
        tv.setText(aa);
        if(count== false){
            tv.setText("The attendance record form "+lastMonth+" until now is not empty, or employee ID incorrect.");
        }
        db.close();
    }
    public void PTMAe(View view){
        SQLiteDatabase db = helper.getWritableDatabase();
        boolean count = false;
        Date lastM;
        String id= search.getText().toString();
        Calendar calendar = Calendar.getInstance();
        Current = new Date(System.currentTimeMillis());
        calendar.setTime(Current);
        calendar.add(calendar.MONTH, -2);
        lastM = calendar.getTime();
        lastMonth = sdf.format(lastM);
        Cursor cursor = db.query(Constant.TABLE_Attendance, null, Constant.ATTENDANCE_DATE + ">=? and "+Constant.ATTENDANCE_EMPLOYEEID+"=?",
                new String[]{lastMonth,id}, null, null, null);
        List<attendanceManager> list = DbManager.allSearchCM(cursor);
        tv.setMovementMethod(new ScrollingMovementMethod());
        String aa="";
        int i = 0;
        int a = 0;
        int b = 0;
        for (attendanceManager e : list) {
            i++;
            count = true;
        }
        String[] report =new String[i+1];
        for (attendanceManager e : list){
            report[a]=e.toString();
            a++;
        }
        while (report[b]!=null){
            aa=aa+report[b];
            b++;
        }
        tv.setText(aa);
        if(count== false){
            tv.setText("The attendance record form "+lastMonth+" until now is not empty, or employee ID incorrect.");
        }
        db.close();
    }
}
