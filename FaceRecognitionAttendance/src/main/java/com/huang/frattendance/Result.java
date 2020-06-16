package com.huang.frattendance;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.megvii.cloud.http.CommonOperate;
import com.megvii.cloud.http.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import helper.Constant;
import helper.DbManager;
import helper.MySqliteHelper;
import helper.timeRange;

/**
 * Created by 鹿若 on 2018/3/10.
 */

public class Result extends Activity {
    private String SelectTime;
    private String SelectLate;
    private MySqliteHelper helper;
    String userID="";
    String userName ="";
    double confidence;
    private ImageView imageView;
    private String path ="/sdcard/temp.png";
    private Bitmap bitmap;
    private boolean count=false;
    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendance_result);
        helper= DbManager.getHelper(this);
        imageView = findViewById(R.id.PreviewResult);
        Toast.makeText(this, "Please wait for loading...", Toast.LENGTH_SHORT).show();
        resizePhoto();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    CommonOperate commonOperate = new CommonOperate(Constant.KEY, Constant.SECRET, false);
                    byte[] bytes = getBitmap(bitmap);
                    Response res = commonOperate.searchByOuterId(null,null,bytes,"EmployeeFace",1);
                    String string = new String(res.getContent());
                    JSONObject json = new JSONObject(string);
                    Log.e("response","11111111111111111111111111\n"+string);
                    if(!json.getString("faces").equals("[]")){
                        Log.e("response","11111111111111111111111111\n"+string);
                        String confi = json.optJSONArray("results").optJSONObject(0).optString("confidence");
                        userID = json.optJSONArray("results").optJSONObject(0).optString("user_id");
                        Log.e("response","11111111111111111111111111\n"+userID);
                        confidence = Double.parseDouble(confi);
                        Log.e("response","11111111111111111111111111\n"+confidence);
                        textView=findViewById(R.id.result);
                        textView.setText("Loading completed.");

                    }else {
                        textView=findViewById(R.id.result);
                        textView.setText("Picture is invalid, please capture again.");
                    }



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void CheckIn(View view){
        if(confidence>80&&search(userID)){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd");
        Date td = new Date(System.currentTimeMillis());
        String today = sdf.format(td);
        SQLiteDatabase db = helper.getWritableDatabase();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        Date Current = new Date(System.currentTimeMillis());
        String CTime = simpleDateFormat.format(Current);
        Cursor cursor = db.query(Constant.TABLE_TIMERANGE, null, null,
                null, null, null, null);
        List<timeRange> list = DbManager.timeRange(cursor);
        for (timeRange e : list) {
            SelectTime = e.getTime();
            SelectLate = e.getLate();
        }
        int cHour = Current.getHours();
        int cMinut = Current.getMinutes();
        int hour =6;
        int minute = 30;
        if(SelectTime == "06:00:00"){
            hour = 6;
        }else if(SelectTime == "07:00:00"){
            hour = 7;
        }else if(SelectTime == "08:00:00"){
            hour = 8;
        }else if(SelectTime == "09:00:00"){
            hour = 9;
        }else if(SelectTime == "10:00:00"){
            hour = 10;
        }

        if(SelectLate == "00:15:00"){
            minute = 15;
        }else if(SelectLate == "00:30:00"){
            minute = 30;
        }else if(SelectLate == "00:45:00"){
            minute = 45;
        }else if(SelectLate == "01:00:00"){
            minute = 60;
        }

        boolean count;
        int lateMinute;
        int lateHour;
        String lateTime;

        if(cHour==hour){
            if(cMinut<=minute){
                count =true;
                lateTime= "0 hour and 0 minute";
            }else{
                count =false;
                lateMinute=cMinut-minute;
                lateTime ="0 hour and "+lateMinute+" minute";
            }
        }else if (cHour>hour){
            if (cMinut>=minute){
                count =false;
                lateMinute = cMinut-minute;
                lateHour = cHour-hour;
                lateTime =lateHour+" hour and "+lateMinute+" minute";
            }else {
                count =false;
                lateMinute = cMinut+minute;
                lateHour = cHour-hour-1;
                lateTime =lateHour+" hour and "+lateMinute+" minute";
            }
        }else {
            count =true;
            lateTime="0 hour and 0 minute";

        }

        if(!confirm(today,userID)){
            if (count) {
                ContentValues values = new ContentValues();
                values.put(Constant.ATTENDANCE_DATE, today);
                values.put(Constant.ATTENDANCE_CHECKINTIME, CTime);
                values.put(Constant.ATTENDANCE_CHECKOUTTIME, 0);
                values.put(Constant.ATTENDANCE_LATETIME, lateTime);
                values.put(Constant.ATTENDANCE_EMPLOYEEID, userID);
                values.put(Constant.ATTENDANCE_EMPLOYEEName, "huang");
                long result = db.insert(Constant.TABLE_Attendance, null, values);
                if (result > 0) {
                    Toast.makeText(this, "Today is " + today + "\nAttendance taken. You are not late.", Toast.LENGTH_SHORT).show();
                    db.close();
                } else {
                    Toast.makeText(this, "Failed to take attendance. Please try again.", Toast.LENGTH_SHORT).show();
                }
            } else {

                ContentValues values = new ContentValues();
                values.put(Constant.ATTENDANCE_DATE, today);
                values.put(Constant.ATTENDANCE_CHECKINTIME, CTime);
                values.put(Constant.ATTENDANCE_CHECKOUTTIME, 0);
                values.put(Constant.ATTENDANCE_LATETIME, lateTime);
                values.put(Constant.ATTENDANCE_EMPLOYEEID, userID);
                values.put(Constant.ATTENDANCE_EMPLOYEEName, "huang");
                long result = db.insert(Constant.TABLE_Attendance, null, values);
                if (result > 0) {
                    Toast.makeText(this, "Today is " + today + "\nAttendance taken. You are late " + lateTime, Toast.LENGTH_SHORT).show();
                    db.close();
                } else {
                    Toast.makeText(this, "Failed to take attendance. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else {
            Toast.makeText(this, "You already checked-in for today, don't check-in again! ", Toast.LENGTH_SHORT).show();
        }
        db.close();
        }else
            {
                Toast.makeText(this, "Can not find your face in database, Please try a new photo.", Toast.LENGTH_SHORT).show();
            }
    }

    public void CheckOut(View view){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd");
        Date td = new Date(System.currentTimeMillis());
        String today = sdf.format(td);
        SQLiteDatabase db = helper.getWritableDatabase();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        Date Current = new Date(System.currentTimeMillis());
        String CTime = simpleDateFormat.format(Current);
        if(confirm(today,userID)){
            ContentValues values = new ContentValues();
            values.put(Constant.ATTENDANCE_CHECKOUTTIME, CTime);
            int count = db.update(Constant.TABLE_Attendance,values,Constant.ATTENDANCE_DATE+"=? and "+Constant.ATTENDANCE_EMPLOYEEID+"=?",new String[]{today,"123"});
            Toast.makeText(this, "Check-out succeed! Now is "+CTime, Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "You already checked-out for today, don't check-out again!", Toast.LENGTH_SHORT).show();
        }
    }
    public boolean confirm(String today,String employeeID) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "select * from "+ Constant.TABLE_Attendance+" where "+Constant.ATTENDANCE_DATE+"=? and "+Constant.ATTENDANCE_EMPLOYEEID+"=? ";
        Cursor cursor = db.rawQuery(sql,new String[] {today, employeeID});
        if (cursor.moveToFirst()) {
            return true;
        }
        return false;
    }

    public boolean search(String userID){
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "select * from "+ Constant.TABLE_Employee+" where "+Constant.EMPLOYEE_FACEID+"=?";
        Cursor cursor = db.rawQuery(sql,new String[] {userID});
        if (cursor.moveToFirst()){
            return true;
        }
        return false;
    }


    private byte[] getBitmap(Bitmap bm){
        Bitmap bitmap = Bitmap.createBitmap(bm,0,0,bm.getWidth(),bm.getHeight());
        ByteArrayOutputStream stream =new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        return stream.toByteArray();

    }

    private void resizePhoto() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(path,options);

        double retio = Math.max(options.outWidth *1.0d/1024f,options.outHeight *1.0/1024f);

        options.inSampleSize = (int) Math.ceil(retio);

        options.inJustDecodeBounds = false;

        bitmap = BitmapFactory.decodeFile(path,options);
        imageView.setImageBitmap(bitmap);
    }

}
