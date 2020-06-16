package Manager;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.huang.frattendance.R;
import com.megvii.cloud.http.CommonOperate;
import com.megvii.cloud.http.FaceOperate;
import com.megvii.cloud.http.FaceSetOperate;
import com.megvii.cloud.http.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.List;

import helper.Constant;
import helper.DbManager;
import helper.MySqliteHelper;
import helper.employeeSearch;

/**
 * Created by 鹿若 on 2018/1/30.
 */

public class EmployeeUpdateAndDelete extends Activity {
    private EditText eSearch;    private String Search;
    private EditText eID;
    private EditText ePassword;
    private EditText eName;
    private EditText eDepartment;
    private EditText ePosition;
    private MySqliteHelper helper;
    private ImageView mPhoto;
    private static final int PICK_CODE = 0X110;
    private String mCurrentPhotoStr;
    private Bitmap mPhotoImg;
    String face_token = "";
    String addFaceResult =null;
    String faceIDresult =null;
    String userID=null;
    boolean check=false;
    boolean upLoad=false;
    boolean select=false;
    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_update_delete_page);
        helper=DbManager.getHelper(this);
        initView();
    }
    private void initView() {
        mPhoto = findViewById(R.id.previewUpdate);
    }
    public void DeleteEmployee(View view) {
        SQLiteDatabase db = helper.getWritableDatabase();
        eSearch = findViewById(R.id.Search);
        Search = eSearch.getText().toString();
        if(Search!=null){
            int count = db.delete(Constant.TABLE_Employee,Constant.EMPLOYEE_ID+"=?",new String[]{Search});
            if(count>0){
                Toast.makeText(this,"The employee ID: "+Search+" has been deleted.",Toast.LENGTH_LONG).show();
                db.close();
            }
            else {
                Toast.makeText(this,"The employee ID: "+Search+" is not exist.",Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(this,"Deletion is not succeed, please check the ID number and try again!",Toast.LENGTH_LONG).show();
        }    }

    public void Search(View view) {
        SQLiteDatabase db = helper.getWritableDatabase();
        eSearch = findViewById(R.id.Search);
        Search = eSearch.getText().toString();
        textView = findViewById(R.id.TV);
        boolean count = false;
        textView.setMovementMethod(new ScrollingMovementMethod());
        Cursor cursor = db.query(Constant.TABLE_Employee, null, Constant.EMPLOYEE_ID + "=?",
                new String[]{Search}, null, null, null);
            List<employeeSearch> list = DbManager.employeeToList(cursor);
                for (employeeSearch e : list) {
                    textView.setText(e.toString());
                    count = true;
                }
                if(count== false){
                    textView.setText("Employee "+Search+" is not exists.");
                }
        db.close();
    }

    public void M_updateEmployee(View view){

        if (Search!=null){
            try {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            eSearch = findViewById(R.id.Search);
                            Search = eSearch.getText().toString();
                            eID = findViewById(R.id.EID);
                            ePassword = findViewById(R.id.EPassword);
                            eName = findViewById(R.id.EName);
                            eDepartment = findViewById(R.id.Department);
                            ePosition = findViewById(R.id.Position);
                            String ID = eID.getText().toString();
                            String Password = ePassword.getText().toString();
                            String Name = eName.getText().toString();
                            String Department = eDepartment.getText().toString();
                            String Position = ePosition.getText().toString();
                            textView = findViewById(R.id.updateMassage);
                            FaceOperate faceOperate = new FaceOperate(Constant.KEY,Constant.SECRET,false);
                            Response setFaceID = faceOperate.faceSetUserId(face_token,Search);
                            faceIDresult = new String(setFaceID.getContent());
                            JSONObject json = new JSONObject(faceIDresult);
                            String faceToken = json.getString("user_id");
                            if(faceToken==null){
                                textView = findViewById(R.id.updateMassage);
                                textView.setText("Please provide correct information or picture!");
                            }
                            Log.e("AddFace Result", "3333333333333333333333333 faceID got!\n"+faceIDresult);
                            ContentValues cv =new ContentValues();
                                if (ID != null) {
                                    cv.put(Constant.EMPLOYEE_ID, ID);
                                    cv.put(Constant.EMPLOYEE_FACEID, ID);
                                }
                                if (Password != null) {
                                    cv.put(Constant.EMPLOYEE_Password, Password);
                                }
                                if (Name != null) {
                                    cv.put(Constant.EMPLOYEE_NAME, Name);
                                }
                                if (Department != null) {
                                    cv.put(Constant.EMPLOYEE_DEPARTMENT, Department);
                                }
                                if (Position != null) {
                                    cv.put(Constant.EMPLOYEE_POSITION, Position);
                                }
                                if (ID == null && Password == null && Name == null && Department == null && Position == null) {
                                    if(faceToken!=null){
                                    textView.setText("Employee information update Success!");}
                                    else {
                                        textView.setText("Please provide correct information or picture!");
                                    }
                                } else {
                                    SQLiteDatabase db = helper.getWritableDatabase();
                                    if (Search==null||cv!=null) {
                                        int count = db.update(Constant.TABLE_Employee, cv, Constant.EMPLOYEE_ID + "=?", new String[]{Search});
                                        if (count > 0) {
                                            textView.setText("Employee information update Success!");
                                        } else {
                                            textView.setText("Failed to create new employee!Please try again!");
                                        }
                                        db.close();
                                    }
                                    else {
                                        textView.setText("Please provide correct information or picture!");
                                    }
                                }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }catch (Exception e){
                e.printStackTrace();
            }

    }
        else {
            textView = findViewById(R.id.updateMassage);
            textView.setText("Please provide correct information or picture!");
        }
    }

    public void CheckPicture(View view){
        if(select) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        CommonOperate commonOperate = new CommonOperate(Constant.KEY, Constant.SECRET, false);
                        String base64 = Base64.encodeToString(getBitmap(mPhotoImg), Base64.NO_WRAP);
                        Response response = commonOperate.detectBase64(base64, 0, null);
                        face_token = getFaceToken(response);
                        Log.e("AddFace Result", "1111111111111111111111111 FaceToken got!\n" + face_token);
                        face_token = getFaceToken(response);
                        if (face_token==null){
                            textView = findViewById(R.id.updateMassage);
                            textView.setText("The picture is invalided, pleas select another picture.");

                        }
                        else {
                            textView = findViewById(R.id.updateMassage);
                            textView.setText("Check completed!");
                            check=true;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            Toast.makeText(this, "Please wait for loading...", Toast.LENGTH_SHORT).show();
        }else{ Toast.makeText(this, "Please select picture for employee.", Toast.LENGTH_SHORT).show();}
    }

    public void Upload(View view){
        if(check) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        FaceSetOperate FaceSet = new FaceSetOperate(Constant.KEY, Constant.SECRET, false);
                        Response addFace = FaceSet.addFaceByOuterId(face_token, "EmployeeFace");
                        addFaceResult = new String(addFace.getContent());
                        JSONObject json = new JSONObject(addFaceResult);
                        String faceToken = json.getString("face_added");
                        Log.e("AddFace Result", "2222222222222222222222222 FaceAdded!\n" + addFaceResult);
                        if(faceToken == null) {
                            textView = findViewById(R.id.addingMassage);
                            textView.setText("The picture is invalided, pleas select another picture.");

                        }else {
                            textView = findViewById(R.id.addingMassage);
                            textView.setText("Check completed!");
                            upLoad=true;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            Toast.makeText(this, "Please wait for loading...", Toast.LENGTH_SHORT).show();
        }else
        {
            Toast.makeText(this, "Please click 'Check' to check the picture.", Toast.LENGTH_SHORT).show();
        }
    }
    public void GetPicture(View view){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_CODE);

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_CODE){
            if(data!=null){
                Uri uri = data.getData();
                Cursor cursor = getContentResolver().query(uri,null,null,null,null);
                cursor.moveToFirst();

                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                mCurrentPhotoStr = cursor.getString(idx);
                cursor.close();

                resizePhoto();
                mPhoto.setImageBitmap(mPhotoImg);
                select=true;
            }
        }
    }

    private void resizePhoto() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(mCurrentPhotoStr,options);

        double retio = Math.max(options.outWidth *1.0d/1024f,options.outHeight *1.0/1024f);

        options.inSampleSize = (int) Math.ceil(retio);

        options.inJustDecodeBounds = false;

        mPhotoImg = BitmapFactory.decodeFile(mCurrentPhotoStr,options);
    }

    private byte[] getBitmap(Bitmap bm){
        Bitmap bitmap = Bitmap.createBitmap(bm,0,0,bm.getWidth(),bm.getHeight());
        ByteArrayOutputStream stream =new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        return stream.toByteArray();

    }
    private String getFaceToken(Response response) throws JSONException {
        if(response.getStatus() != 200){
            return new String(response.getContent());
        }
        String res = new String(response.getContent());
        Log.e("response","22222222222222222222222222\n"+res);
        JSONObject json = new JSONObject(res);
        if(!json.getString("faces").equals("[]")) {
            String faceToken = json.optJSONArray("faces").optJSONObject(0).optString("face_token");
            return faceToken;
        }
        String faceToken=null;
        return faceToken;
    }
}
