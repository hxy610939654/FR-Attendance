package employee;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.huang.frattendance.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import helper.Constant;
import helper.DbManager;
import helper.MySqliteHelper;
import helper.attendanceManager;

/**
 * Created by 鹿若 on 2018/1/30.
 */

public class EmployeeSelfCheck extends Activity {
    private MySqliteHelper helper;
    private TextView tv;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd");
    private String currentMonth;
    private String lastMonth;
    private String pastTmonth;
    private Date Current;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_selfcheck_page);
        helper= DbManager.getHelper(this);
    }

    public void ECM(View view){
        Intent id =getIntent();
        String username = id.getStringExtra("Employee");
        tv=findViewById(R.id.TVemployee);
        SQLiteDatabase db = helper.getWritableDatabase();
        boolean count = false;
        Date currenMonth;
        Calendar calendar = Calendar.getInstance();
        Current = new Date(System.currentTimeMillis());
        calendar.setTime(Current);
        currenMonth = calendar.getTime();
        currentMonth = sdf.format(currenMonth);
        Cursor cursor = db.query(Constant.TABLE_Attendance, null, Constant.ATTENDANCE_DATE + "=? and "+Constant.ATTENDANCE_EMPLOYEEID+"=?",
                new String[]{currentMonth,username}, null, null, null);
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
    public void EPM(View view){
        Intent id =getIntent();
        String username = id.getStringExtra("Employee");
        tv=findViewById(R.id.TVemployee);
        SQLiteDatabase db = helper.getWritableDatabase();
        boolean count = false;
        Date currenMonth;
        Calendar calendar = Calendar.getInstance();
        Current = new Date(System.currentTimeMillis());
        calendar.setTime(Current);
        calendar.add(calendar.MONTH, -1);
        currenMonth = calendar.getTime();
        lastMonth = sdf.format(currenMonth);
        Cursor cursor = db.query(Constant.TABLE_Attendance, null, Constant.ATTENDANCE_DATE + ">=? and "+Constant.ATTENDANCE_EMPLOYEEID+"=?",
                new String[]{lastMonth,username}, null, null, null);
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
            tv.setText("The attendance record form "+lastMonth+" until now is not empty.");
        }
        db.close();

    }
    public void EPTM(View view){
        Intent id =getIntent();
        String username = id.getStringExtra("Employee");
        tv=findViewById(R.id.TVemployee);
        SQLiteDatabase db = helper.getWritableDatabase();
        boolean count = false;
        Date currenMonth;
        Calendar calendar = Calendar.getInstance();
        Current = new Date(System.currentTimeMillis());
        calendar.setTime(Current);
        calendar.add(calendar.MONTH, -2);
        currenMonth = calendar.getTime();
        pastTmonth = sdf.format(currenMonth);
        Cursor cursor = db.query(Constant.TABLE_Attendance, null, Constant.ATTENDANCE_DATE + ">=? and "+Constant.ATTENDANCE_EMPLOYEEID+"=?",
                new String[]{pastTmonth,username}, null, null, null);
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
            tv.setText("The attendance record form "+pastTmonth+" until now is not empty.");
        }
        db.close();
    }
}
