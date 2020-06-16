package Manager;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.huang.frattendance.R;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import helper.Constant;
import helper.DbManager;
import helper.MySqliteHelper;
import helper.employeeSearch;
import helper.timeRange;

import static android.os.Build.ID;

/**
 * Created by 鹿若 on 2018/2/16.
 */

public class SetTimeRange extends Activity {
    private MySqliteHelper helper;
    private List<Time> list_time = null;
    private List<Time> list_late = null;
    private Spinner time_Spinner = null;
    private Spinner late_Spinner = null;
    private ArrayAdapter<Time> adapter_time = null;
    private ArrayAdapter<Time> adapter_late = null;
    private TextView textView=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_range_management);
        helper= DbManager.getHelper(this);
        time_Spinner = findViewById(R.id.Time);
        late_Spinner = findViewById(R.id.lateTime);
        time_Spinner.setPrompt("Please select Time for check-in.");
        list_time = new ArrayList<Time>();
        list_time.add(Time.valueOf("06:00:00"));
        list_time.add(Time.valueOf("07:00:00"));
        list_time.add(Time.valueOf("08:00:00"));
        list_time.add(Time.valueOf("09:00:00"));
        list_time.add(Time.valueOf("10:00:00"));

        late_Spinner.setPrompt("Please select Time for late.");
        list_late = new ArrayList<Time>();
        list_late.add(Time.valueOf("00:15:00"));
        list_late.add(Time.valueOf("00:30:00"));
        list_late.add(Time.valueOf("00:45:00"));
        list_late.add(Time.valueOf("01:00:00"));

        adapter_time = new ArrayAdapter<Time>(this,android.R.layout.simple_spinner_dropdown_item,list_time);
        adapter_time.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time_Spinner.setAdapter(adapter_time);

        adapter_late = new ArrayAdapter<Time>(this,android.R.layout.simple_spinner_dropdown_item,list_late);
        adapter_late.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        late_Spinner.setAdapter(adapter_late);

        SQLiteDatabase db = helper.getWritableDatabase();
        textView = findViewById(R.id.TVsetTime);
        Cursor cursor = db.query(Constant.TABLE_TIMERANGE, null, null,
                null, null, null, null);
        List<timeRange> list = DbManager.timeRange(cursor);
        for (timeRange e : list) {
            textView.setText(e.toString());
        }
        db.close();
    }
    public void setTimeRange(View view){
        SQLiteDatabase db = helper.getWritableDatabase();
        String time=time_Spinner.getSelectedItem().toString();
        String late=late_Spinner.getSelectedItem().toString();
        ContentValues cv =new ContentValues();
        cv.put(Constant.TIMERANGE_TIME,time);
        cv.put(Constant.TIMERANGE_LATETIME, late);
        int count = db.update(Constant.TABLE_TIMERANGE,cv,null,null);
        ContentValues st = new ContentValues();
        int count1;
        switch (time) {
            case "06:00:00":
                switch (late){
                    case "00:15:00":
                        st.put(Constant.SELECT_TIME,1);
                        st.put(Constant.SELECT_LATE,1);
                        count1 = db.update(Constant.TABLE_SELECT,st,null,null);
                        if (count1>0){
                            Log.i("tag","select set!");
                        }
                        else {
                            Log.i("tag","select set is not succeed!");
                        }
                        break;
                    case "00:30:00":
                        st.put(Constant.SELECT_TIME,1);
                        st.put(Constant.SELECT_LATE,2);
                        count1 = db.update(Constant.TABLE_SELECT,st,null,null);
                        if (count1>0){
                            Log.i("tag","select set!");
                        }
                        else {
                            Log.i("tag","select set is not succeed!");
                        }
                        break;
                    case "00:45:00":
                        st.put(Constant.SELECT_TIME,1);
                        st.put(Constant.SELECT_LATE,3);
                        count1 = db.update(Constant.TABLE_SELECT,st,null,null);
                        if (count1>0){
                            Log.i("tag","select set!");
                        }
                        else {
                            Log.i("tag","select set is not succeed!");
                        }
                        break;
                    case "01:00:00":
                        st.put(Constant.SELECT_TIME,1);
                        st.put(Constant.SELECT_LATE,4);
                        count1 = db.update(Constant.TABLE_SELECT,st,null,null);
                        if (count1>0){
                            Log.i("tag","select set!");
                        }
                        else {
                            Log.i("tag","select set is not succeed!");
                        }
                        break;
                }
                break;
            case "07:00:00":
                switch (late){
                    case "00:15:00":
                        st.put(Constant.SELECT_TIME,2);
                        st.put(Constant.SELECT_LATE,1);
                        count1 = db.update(Constant.TABLE_SELECT,st,null,null);
                        if (count1>0){
                            Log.i("tag","select set!");
                        }
                        else {
                            Log.i("tag","select set is not succeed!");
                        }
                        break;
                    case "00:30:00":
                        st.put(Constant.SELECT_TIME,2);
                        st.put(Constant.SELECT_LATE,2);
                        count1 = db.update(Constant.TABLE_SELECT,st,null,null);
                        if (count1>0){
                            Log.i("tag","select set!");
                        }
                        else {
                            Log.i("tag","select set is not succeed!");
                        }
                        break;
                    case "00:45:00":
                        st.put(Constant.SELECT_TIME,2);
                        st.put(Constant.SELECT_LATE,3);
                        count1 = db.update(Constant.TABLE_SELECT,st,null,null);
                        if (count1>0){
                            Log.i("tag","select set!");
                        }
                        else {
                            Log.i("tag","select set is not succeed!");
                        }
                        break;
                    case "01:00:00":
                        st.put(Constant.SELECT_TIME,2);
                        st.put(Constant.SELECT_LATE,4);
                        count1 = db.update(Constant.TABLE_SELECT,st,null,null);
                        if (count1>0){
                            Log.i("tag","select set!");
                        }
                        else {
                            Log.i("tag","select set is not succeed!");
                        }
                        break;
                }
                break;
            case "08:00:00":
                switch (late){
                    case "00:15:00":
                        st.put(Constant.SELECT_TIME,3);
                        st.put(Constant.SELECT_LATE,1);
                        count1 = db.update(Constant.TABLE_SELECT,st,null,null);
                        if (count1>0){
                            Log.i("tag","select set!");
                        }
                        else {
                            Log.i("tag","select set is not succeed!");
                        }
                        break;
                    case "00:30:00":
                        st.put(Constant.SELECT_TIME,3);
                        st.put(Constant.SELECT_LATE,2);
                        count1 = db.update(Constant.TABLE_SELECT,st,null,null);
                        if (count1>0){
                            Log.i("tag","select set!");
                        }
                        else {
                            Log.i("tag","select set is not succeed!");
                        }
                        break;
                    case "00:45:00":
                        st.put(Constant.SELECT_TIME,3);
                        st.put(Constant.SELECT_LATE,3);
                        count1 = db.update(Constant.TABLE_SELECT,st,null,null);
                        if (count1>0){
                            Log.i("tag","select set!");
                        }
                        else {
                            Log.i("tag","select set is not succeed!");
                        }
                        break;
                    case "01:00:00":
                        st.put(Constant.SELECT_TIME,3);
                        st.put(Constant.SELECT_LATE,4);
                        count1 = db.update(Constant.TABLE_SELECT,st,null,null);
                        if (count1>0){
                            Log.i("tag","select set!");
                        }
                        else {
                            Log.i("tag","select set is not succeed!");
                        }
                        break;
                }
                break;
            case "09:00:00":
                switch (late){
                    case "00:15:00":
                        st.put(Constant.SELECT_TIME,4);
                        st.put(Constant.SELECT_LATE,1);
                        count1 = db.update(Constant.TABLE_SELECT,st,null,null);
                        if (count1>0){
                            Log.i("tag","select set!");
                        }
                        else {
                            Log.i("tag","select set is not succeed!");
                        }
                        break;
                    case "00:30:00":
                        st.put(Constant.SELECT_TIME,4);
                        st.put(Constant.SELECT_LATE,2);
                        count1 = db.update(Constant.TABLE_SELECT,st,null,null);
                        if (count1>0){
                            Log.i("tag","select set!");
                        }
                        else {
                            Log.i("tag","select set is not succeed!");
                        }
                        break;
                    case "00:45:00":
                        st.put(Constant.SELECT_TIME,4);
                        st.put(Constant.SELECT_LATE,3);
                        count1 = db.update(Constant.TABLE_SELECT,st,null,null);
                        if (count1>0){
                            Log.i("tag","select set!");
                        }
                        else {
                            Log.i("tag","select set is not succeed!");
                        }
                        break;
                    case "01:00:00":
                        st.put(Constant.SELECT_TIME,4);
                        st.put(Constant.SELECT_LATE,4);
                        count1 = db.update(Constant.TABLE_SELECT,st,null,null);
                        if (count1>0){
                            Log.i("tag","select set!");
                        }
                        else {
                            Log.i("tag","select set is not succeed!");
                        }
                        break;
                }
                break;
            case "10:00:00":
                switch (late){
                    case "00:15:00":
                        st.put(Constant.SELECT_TIME,5);
                        st.put(Constant.SELECT_LATE,1);
                        count1 = db.update(Constant.TABLE_SELECT,st,null,null);
                        if (count1>0){
                            Log.i("tag","select set!");
                        }
                        else {
                            Log.i("tag","select set is not succeed!");
                        }
                        break;
                    case "00:30:00":
                        st.put(Constant.SELECT_TIME,5);
                        st.put(Constant.SELECT_LATE,2);
                        count1 = db.update(Constant.TABLE_SELECT,st,null,null);
                        if (count1>0){
                            Log.i("tag","select set!");
                        }
                        else {
                            Log.i("tag","select set is not succeed!");
                        }
                        break;
                    case "00:45:00":
                        st.put(Constant.SELECT_TIME,5);
                        st.put(Constant.SELECT_LATE,3);
                        count1 = db.update(Constant.TABLE_SELECT,st,null,null);
                        if (count1>0){
                            Log.i("tag","select set!");
                        }
                        else {
                            Log.i("tag","select set is not succeed!");
                        }
                        break;
                    case "01:00:00":
                        st.put(Constant.SELECT_TIME,5);
                        st.put(Constant.SELECT_LATE,4);
                        count1 = db.update(Constant.TABLE_SELECT,st,null,null);
                        if (count1>0){
                            Log.i("tag","select set!");
                        }
                        else {
                            Log.i("tag","select set is not succeed!");
                        }
                        break;
                }
                break;
        }

        if(count>0){
            textView = findViewById(R.id.TVsetTime);
            Cursor cursor = db.query(Constant.TABLE_TIMERANGE, null, null,
                    null, null, null, null);
            List<timeRange> list = DbManager.timeRange(cursor);
            for (timeRange e : list) {
                textView.setText(e.toString());
            }
            Toast.makeText(this,"Time range has been set!",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this,"Set time range is not Succeed!",Toast.LENGTH_LONG).show();
        }
        db.close();
    }
}

