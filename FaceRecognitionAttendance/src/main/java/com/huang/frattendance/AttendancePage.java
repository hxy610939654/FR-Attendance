package com.huang.frattendance;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.ConsumerIrManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.BoringLayout;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import helper.Constant;
import helper.DbManager;
import helper.MySqliteHelper;
import helper.timeRange;


public class AttendancePage extends Activity implements SurfaceHolder.Callback {

    private Camera myCamera;
    private SurfaceView myPreview;
    private SurfaceHolder myHolder;

    private Camera.PictureCallback mPictureCallback =new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File tempFile = new File("/sdcard/temp.png");
            try {
                FileOutputStream fos = new FileOutputStream(tempFile);
                fos.write(data);
                fos.close();
                Intent intent = new Intent(AttendancePage.this,Result.class);
                intent.putExtra("picPath",tempFile.getAbsolutePath());
                startActivity(intent);
                AttendancePage.this.finish();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendance_page);
        initViews();
    }



    public void initViews() {
        myPreview= findViewById(R.id.preview);
        myHolder = myPreview.getHolder();
        myHolder.addCallback(this);
    }

    public void Capture(View view){
        Camera.Parameters parameters = myCamera.getParameters();
        parameters.setPictureFormat(ImageFormat.JPEG);
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        myCamera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                if(success){
                    myCamera.takePicture(null,null,mPictureCallback);
                }
            }
        });
    }




    @Override
    protected void onResume() {
        super.onResume();
        if(myCamera == null){
            myCamera = getCamera();
            if(myHolder != null){
                setStartPreview(myCamera,myHolder);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }

    public Camera getCamera(){
        Camera camera;
        camera = Camera.open(1);
        return camera;
    }

    public void setStartPreview(Camera camera,SurfaceHolder holder){
        try {
            camera.setPreviewDisplay(holder);
            //camera.setDisplayOrientation(90);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void releaseCamera(){
        if(myCamera != null) {
            myCamera.setPreviewCallback(null);
            myCamera.stopPreview();
            myCamera.release();
            myCamera = null;
        }
    }



    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        setStartPreview(myCamera,myHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        myCamera.stopPreview();
        setStartPreview(myCamera,myHolder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        releaseCamera();
    }


}
