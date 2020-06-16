package administrator;

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

import java.util.ArrayList;
import java.util.List;

import helper.Constant;
import helper.DbManager;
import helper.MySqliteHelper;
import helper.employeeSearch;

/**
 * Created by 鹿若 on 2018/2/14.
 */

public class AdministratorLogin extends Activity {
    private MySqliteHelper helper;
    private EditText username;
    private EditText password;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.administrator_login);
        helper= DbManager.getHelper(this);
    }
    public void Adminlogin(View view){
        username = findViewById(R.id.AUsername);
        password = findViewById(R.id.APassword);
        String Username = username.getText().toString();
        String Password = password.getText().toString();
        if(Username!=null&&Password!=null) {
            if (login(Username, Password)) {
                startActivity(new Intent(this, AdministratorHome.class));
                AdministratorLogin.this.finish();
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
        String sql = "select * from "+Constant.TABLE_Admin+" where "+Constant.ADMIN_USERNAME+"=? and "+Constant.ADMIN_PASSWORD+"=?";
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