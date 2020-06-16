package administrator;

import android.app.Activity;
import android.content.ContentValues;
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

public class AddManager extends Activity {
    private EditText mUsername;
    private EditText mPassword;
    private EditText RmPassword;
    private MySqliteHelper helper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_manager);
        helper= DbManager.getHelper(this);
    }
    public void AddManager (View view){
            mUsername = findViewById(R.id.mUsername);
            mPassword = findViewById(R.id.mPassword);
            RmPassword = findViewById(R.id.RmPassword);
            String Username = mUsername.getText().toString();
            String Password = mPassword.getText().toString();
            String RPassword = RmPassword.getText().toString();
        if (Username != null && mPassword != null && RmPassword != null) {
            if (Password.equals(RPassword)) {
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(Constant.MANAGER_USERNAME, Username);
                values.put(Constant.MANAGER_PASSWORD, Password);
                long result = db.insert(Constant.TABLE_Manager, null, values);
                if (result > 0)
                {
                    Toast.makeText(this, "New Manager added!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "The manager username is already exist.", Toast.LENGTH_SHORT).show();
                }
                db.close();
            } else {
                Toast.makeText(this, "The password you entered is not the same! Please try again.", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Please enter the username, password and ConfirmPassword.", Toast.LENGTH_SHORT).show();
        }


    }
}
