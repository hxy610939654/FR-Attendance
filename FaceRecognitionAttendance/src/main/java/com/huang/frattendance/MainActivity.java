package com.huang.frattendance;


import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import Manager.ManagerLogin;
import administrator.AdministratorLogin;
import employee.EmployeeLogin;


public class MainActivity extends AppCompatActivity {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getSharedPreferences("firstStart",MODE_PRIVATE);

        if(preferences.getBoolean("firstStart",true)){
            editor = preferences.edit();
            editor.putBoolean("firstStart",false);
            editor.commit();
            startActivity(new Intent(this, FirstLogin.class));
        }

    }
    public void AttendancePage(View view){

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.
                    permission.CAMERA}, 1);
        }
        else if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.
                    permission.WRITE_EXTERNAL_STORAGE},2);
        }
        else {
            startActivity(new Intent(this, AttendancePage.class));
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    AttendancePage(null);
                }
        }
    }
    public void ManagerLogin(View view){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.
                    permission.CAMERA}, 1);
        }
        else if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.
                    permission.WRITE_EXTERNAL_STORAGE},2);
        }
        else {
            startActivity(new Intent(this, ManagerLogin.class));
        }

    }
    public void EmployeeLogin(View view){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.
                    permission.CAMERA}, 1);
        }
        else if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.
                    permission.WRITE_EXTERNAL_STORAGE},2);
        }
        else {
            startActivity(new Intent(this, EmployeeLogin.class));
        }

    }
    public void AdminLogin(View view){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.
                    permission.CAMERA}, 1);
        }
        else if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.
                    permission.WRITE_EXTERNAL_STORAGE},2);
        }
        else {
            startActivity(new Intent(this, AdministratorLogin.class));
        }

    }
}
