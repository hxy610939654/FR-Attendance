package com.huang.frattendance;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;



import com.megvii.cloud.http.FaceSetOperate;
import com.megvii.cloud.http.Response;

import administrator.AdministratorLogin;
import helper.Constant;
import helper.DbManager;
import helper.MySqliteHelper;


/**
 * Created by 鹿若 on 2018/1/30.
 */

public class FirstLogin extends Activity{
    private MySqliteHelper helper;
    private TextView tv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_login);
        helper= DbManager.getHelper(this);
    }
    public void createDb (View view){
        tv = findViewById(R.id.infrom);
        SQLiteDatabase db = helper.getWritableDatabase();
        new Thread(new Runnable(){
            public void run() {
                FaceSetOperate FaceSet = new FaceSetOperate(Constant.KEY, Constant.SECRET, false);
                try {
                    Response createFaceSet = FaceSet.createFaceSet("EmployeeFace","EmployeeFace",null,null,null,0);
                    String faceSetResult = new String(createFaceSet.getContent());
                    Log.e("CreatefaceSetResult", faceSetResult);
                    Log.i("tag", "Database initialized!");
                    if (createFaceSet.getStatus() != 200){
                        tv.setText("Due to internet issue Initialization failed, please try again.");
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText("Initialization completed, again thanks for your patient.");
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).start();

    }
    public void createAdmin(View view){
        String time ="08:00:00";
        String late ="00:30:00";
        int Stime = 3;
        int Slate = 2;
        int amount= 0;
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.rawQuery("select * from Admin",null);
        amount=c.getCount();
        switch (view.getId()){
            case R.id.createAdmin:
                if(amount == 0){
                String sql = "insert into "+ Constant.TABLE_Admin+" values('admin','admin')";
                DbManager.execSQL(db,sql);
                ContentValues values = new ContentValues();
                values.put(Constant.TIMERANGE_TIME, time);
                values.put(Constant.TIMERANGE_LATETIME, late);
                db.insert(Constant.TABLE_TIMERANGE,null,values);
                    ContentValues select = new ContentValues();
                    select.put(Constant.SELECT_TIME, Stime);
                    select.put(Constant.SELECT_LATE, Slate);
                    db.insert(Constant.TABLE_SELECT,null,select);
                db.close();
                    startActivity(new Intent(this, AdministratorLogin.class));
                    FirstLogin.this.finish();
                    Log.i("tag","Admin created!");
                }
                else {
                    startActivity(new Intent(this, AdministratorLogin.class));
                    FirstLogin.this.finish();
                    Log.i("tag","Admin already existed!");
                }
        }

    }

}
