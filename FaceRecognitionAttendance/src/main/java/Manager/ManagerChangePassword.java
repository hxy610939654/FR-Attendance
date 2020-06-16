package Manager;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.huang.frattendance.R;

import helper.Constant;
import helper.DbManager;
import helper.MySqliteHelper;

/**
 * Created by 鹿若 on 2018/2/16.
 */

public class ManagerChangePassword extends Activity {
    private MySqliteHelper helper;
    private EditText MoPassword;
    private EditText MnPassword;
    private EditText MrnPassword;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password_manager);
        helper= DbManager.getHelper(this);
    }
    public void MchangePassword(View view){
        MoPassword = findViewById(R.id.MoPassword);
        MnPassword = findViewById(R.id.MnPassword);
        MrnPassword= findViewById(R.id.MnPassword);
        Intent i =getIntent();
        String oPassword = MoPassword.getText().toString();
        String nPassword = MnPassword.getText().toString();
        String RnPassword = MrnPassword.getText().toString();
        String username = i.getStringExtra("Manager");
        if(oPassword!=null&&nPassword!=null&&RnPassword!=null) {
            if(!oPassword.equals(nPassword)&&nPassword.equals(RnPassword)) {
                if (compare(username,oPassword)) {
                    ContentValues cv =new ContentValues();
                    cv.put(Constant.MANAGER_PASSWORD, nPassword);
                    SQLiteDatabase db = helper.getWritableDatabase();
                    int count = db.update(Constant.TABLE_Manager,cv,Constant.MANAGER_USERNAME+"=?",new String[]{username});
                    if(count>0){
                        Toast.makeText(this,"Change Password Success!",Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(this,"Change Password is not Succeed!",Toast.LENGTH_LONG).show();
                    }
                    db.close();
                } else {
                    Toast.makeText(this, "Old password is incorrect, please try again!", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(this, "Invalided password, please try again!", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Please enter old password and new password!", Toast.LENGTH_SHORT).show();
        }
    }
    public boolean compare(String username,String password){
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "select * from "+Constant.TABLE_Manager+" where "+Constant.MANAGER_USERNAME+"=? and "+Constant.MANAGER_PASSWORD+"=?";
        Cursor cursor = db.rawQuery(sql,new String[] {username, password});
        if (cursor.moveToFirst()) {
            cursor.close();
            db.close();
            return true;
        }
        db.close();
        return false;

    }
}
