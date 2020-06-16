package employee;

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

public class EmployeeChangePassword extends Activity {
    private MySqliteHelper helper;
    private EditText EoPassword;
    private EditText EnPassword;
    private EditText ErnPassword;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_password_change);
        helper= DbManager.getHelper(this);
    }
    public void EChangePassword(View view){
        EoPassword = findViewById(R.id.EoPassword);
        EnPassword = findViewById(R.id.EnPassword);
        ErnPassword= findViewById(R.id.EnrPassword);
        Intent i =getIntent();
        String oPassword = EoPassword.getText().toString();
        String nPassword = EnPassword.getText().toString();
        String RnPassword = ErnPassword.getText().toString();
        String username = i.getStringExtra("Employee");
        if(oPassword!=null&&nPassword!=null&&RnPassword!=null) {
            if(!oPassword.equals(nPassword)&&nPassword.equals(RnPassword)) {
                if (compare(username,oPassword)) {
                    ContentValues cv =new ContentValues();
                    cv.put(Constant.EMPLOYEE_Password, nPassword);
                    SQLiteDatabase db = helper.getWritableDatabase();
                    int count = db.update(Constant.TABLE_Employee,cv,Constant.EMPLOYEE_ID+"=?",new String[]{username});
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
        String sql = "select * from "+Constant.TABLE_Employee+" where "+Constant.EMPLOYEE_ID+"=? and "+Constant.EMPLOYEE_Password+"=?";
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
