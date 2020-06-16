package administrator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.huang.frattendance.R;

/**
 * Created by 鹿若 on 2018/2/16.
 */

public class AdministratorHome extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.administrator_page);

    }
    public void ChangePassword(View view){
        startActivity(new Intent(this, AdminChangePassword.class));
    }

    public void AddManager(View view){
        startActivity(new Intent(this,AddManager.class));
    }
    public void DeletManager(View view){
        startActivity(new Intent(this,DeleteManager.class));
    }

}
