package Manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.huang.frattendance.R;

/**
 * Created by 鹿若 on 2018/1/30.
 */

public class ManagerHomePage extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_home_page);
    }
    public void AddEmployee(View view){
        startActivity(new Intent(this, AddEmployee.class));
    }

    public void AttendanceReport(View view){startActivity(new Intent(this, AttendanceReport.class));}

    public void ChangePassword(View view){
        Intent i=getIntent();
        String username = i.getStringExtra("Manager");
        Intent intent = new Intent(ManagerHomePage.this,ManagerChangePassword.class);
        intent.putExtra("Manager",username);
        startActivity(intent);
    }

    public void setTimeRange(View view){
        startActivity(new Intent(this, SetTimeRange.class));
    }

    public void UpdateAndDelete(View view){startActivity(new Intent(this, EmployeeUpdateAndDelete.class));}
}
