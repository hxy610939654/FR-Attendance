package administrator;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.huang.frattendance.R;

import java.util.ArrayList;
import java.util.List;

import helper.Constant;
import helper.DbManager;
import helper.MySqliteHelper;
import helper.employeeSearch;
import helper.managerSearch;

/**
 * Created by 鹿若 on 2018/2/16.
 */

public class DeleteManager extends Activity {
    private MySqliteHelper helper;
    private EditText Musername;
    private EditText aPassword;
    private TextView textView;
    private String username;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_manager);
        helper = DbManager.getHelper(this);
    }
    public void aSearch(View view){
        SQLiteDatabase db = helper.getWritableDatabase();
        Musername = findViewById(R.id.ManagerUsername);
        textView = findViewById(R.id.ManagerShow);
        username = Musername.getText().toString();
        textView.setMovementMethod(new ScrollingMovementMethod());
        boolean count = false;
        Cursor cursor = db.query(Constant.TABLE_Manager, null, Constant.MANAGER_USERNAME + "=? ",
                new String[]{username}, null, null, null);
        List<managerSearch> list = DbManager.managerSearch(cursor);
        for (managerSearch m : list) {
            textView.setText(m.toString());
            count = true;
        }
        if(count== false){
            textView.setText("Manager "+username+" is not exists.");
        }
    }

    public void aDelete(View view){
        SQLiteDatabase db = helper.getWritableDatabase();
        Musername = findViewById(R.id.ManagerUsername);
        aPassword = findViewById(R.id.aPasswordConfirm);
        String password = aPassword.getText().toString();
        String username = Musername.getText().toString();

        if(Confirm(password)){
            if(username!=null){
                int count = db.delete(Constant.TABLE_Manager,Constant.MANAGER_USERNAME+"=?",new String[]{username});
                if(count>0){
                    Toast.makeText(this,"The manager "+username+" has been deleted.",Toast.LENGTH_LONG).show();
                    db.close();
                }
                else {
                    Toast.makeText(this,"The manager "+username+" is not exist.",Toast.LENGTH_LONG).show();
                }
            }
            else {
                Toast.makeText(this,"Deletion is not succeed, please check the username and try again!",Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(this,"Password is incorrect, please check the password and try again!",Toast.LENGTH_LONG).show();
        }
    }

    public boolean Confirm(String password) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "select * from "+Constant.TABLE_Admin+" where "+Constant.ADMIN_PASSWORD+"=?";
        Cursor cursor = db.rawQuery(sql,new String[] {password});
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        return false;
    }


}
