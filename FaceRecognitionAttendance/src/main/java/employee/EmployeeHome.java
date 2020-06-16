package employee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.huang.frattendance.R;

import Manager.ManagerChangePassword;
import Manager.ManagerHomePage;

/**
 * Created by 鹿若 on 2018/2/16.
 */

public class EmployeeHome extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_home_page);
    }
    public void CheckAttendance(View view){
        Intent i=getIntent();
        String username = i.getStringExtra("Employee");
        Intent intent = new Intent(EmployeeHome.this, EmployeeSelfCheck.class);
        intent.putExtra("Employee",username);
        startActivity(intent);
    }
    public void EChangePassword(View view){
        Intent i=getIntent();
        String username = i.getStringExtra("Employee");
        Intent intent = new Intent(EmployeeHome.this, EmployeeChangePassword.class);
        intent.putExtra("Employee",username);
        startActivity(intent);
    }
}
