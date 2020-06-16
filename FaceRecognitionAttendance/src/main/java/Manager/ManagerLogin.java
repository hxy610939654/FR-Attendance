package Manager;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.huang.frattendance.R;

import administrator.AdministratorHome;
import employee.EmployeeLogin;
import helper.Constant;
import helper.DbManager;
import helper.MySqliteHelper;

/**
 * Created by 鹿若 on 2018/1/29.
 */

public class ManagerLogin extends Activity{
    private MySqliteHelper helper;
    private EditText MloginUsername;
    private EditText MloginPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_login);
        helper= DbManager.getHelper(this);
    }
    public void managerlogin(View view){
        MloginUsername = findViewById(R.id.M_login_username);
        MloginPassword = findViewById(R.id.M_login_password);
        String Username = MloginUsername.getText().toString();
        String Password = MloginPassword.getText().toString();
        if(Username!=null&&Password!=null) {
            if (login(Username, Password)) {
                Intent intent = new  Intent(ManagerLogin.this, ManagerHomePage.class);
                intent.putExtra("Manager",Username);
                startActivity(intent);
                ManagerLogin.this.finish();
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
        String sql = "select * from "+ Constant.TABLE_Manager+" where "+Constant.MANAGER_USERNAME+"=? and "+Constant.MANAGER_PASSWORD+"=?";
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
