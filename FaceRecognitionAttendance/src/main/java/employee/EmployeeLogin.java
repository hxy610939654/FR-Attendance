package employee;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.huang.frattendance.R;

import Manager.ManagerHomePage;
import Manager.ManagerLogin;
import administrator.AdministratorHome;
import administrator.AdministratorLogin;
import helper.Constant;
import helper.DbManager;
import helper.MySqliteHelper;

/**
 * Created by 鹿若 on 2018/1/29.
 */

public class EmployeeLogin extends Activity {
    private MySqliteHelper helper;
    private EditText username;
    private EditText password;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_login);
        helper= DbManager.getHelper(this);
    }
    public void EmployeelogIn(View view){
        username = findViewById(R.id.e_login_id);
        password = findViewById(R.id.e_login_password);
        String Username = username.getText().toString();
        String Password = password.getText().toString();
        if(Username!=null&&Password!=null) {
            if (login(Username, Password)) {
                Intent intent = new  Intent(EmployeeLogin.this, EmployeeHome.class);
                intent.putExtra("Employee",Username);
                startActivity(intent);
                EmployeeLogin.this.finish();
            } else {
                Toast.makeText(this, "Invalid username or password, please try again!", Toast.LENGTH_SHORT).show();

            }
        }
        else {
            Toast.makeText(this, "Please enter username and password!", Toast.LENGTH_SHORT).show();
        }
    }
    public boolean login(String username,String password) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "select * from "+ Constant.TABLE_Employee+" where "+Constant.EMPLOYEE_ID+"=? and "+Constant.EMPLOYEE_Password+"=?";
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
