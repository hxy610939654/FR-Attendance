package administrator;

import android.app.Activity;
import android.content.ContentValues;
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

public class AdminChangePassword extends Activity {
    private MySqliteHelper helper;
    private EditText AoPassword;
    private EditText AnPassword;
    private EditText RaPassword;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password_admin);
        helper= DbManager.getHelper(this);
    }

    public void AChangePassword(View view){
        AoPassword = findViewById(R.id.OaPassword);
        AnPassword = findViewById(R.id.NaPassword);
        RaPassword = findViewById(R.id.RaPassword);
        String oPassword = AoPassword.getText().toString();
        String nPassword = AnPassword.getText().toString();
        String rPassword = RaPassword.getText().toString();
        if(oPassword!=null&&nPassword!=null&&rPassword!=null) {
            if(!oPassword.equals(nPassword)&&nPassword.equals(rPassword)) {
                if (compare(oPassword)) {
                    ContentValues cv =new ContentValues();
                    cv.put(Constant.ADMIN_PASSWORD, nPassword);
                    SQLiteDatabase db = helper.getWritableDatabase();
                    int count = db.update(Constant.TABLE_Admin,cv,Constant.ADMIN_PASSWORD+"=?",new String[]{oPassword});
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


    public boolean compare(String password) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "select * from "+ Constant.TABLE_Admin+" where "+Constant.ADMIN_PASSWORD+"=?";
        Cursor cursor = db.rawQuery(sql,new String[] {password});
        if (cursor.moveToFirst()) {
            cursor.close();
            db.close();
            return true;
        }
        db.close();
        return false;
    }
}
