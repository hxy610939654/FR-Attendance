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
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.huang.frattendance.R;
import com.megvii.cloud.http.CommonOperate;
import com.megvii.cloud.http.FaceOperate;
import com.megvii.cloud.http.FaceSetOperate;
import com.megvii.cloud.http.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.jar.JarException;

import helper.Constant;
import helper.DbManager;
import helper.MySqliteHelper;

import static android.util.Base64.NO_WRAP;

/**
 * Created by 鹿若 on 2018/1/30.
 */

public class AddEmployee extends Activity  {
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
    String ff=null;
    boolean check=false;
    boolean upLoad=false;
    boolean select=false;
    private TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_adding_page);
        helper= DbManager.getHelper(this);
        initView();
    }


    private void initView() {
        mPhoto = findViewById(R.id.imageView);
    }

    public void AddEmployee(View view){
        if(check&&upLoad&&select){

                if (face_token!=null){
                    try {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    eID = findViewById(R.id.EID);
                                    ePassword = findViewById(R.id.EPassword);
                                    eName = findViewById(R.id.EName);
                                    eDepartment = findViewById(R.id.Department);
                                    ePosition = findViewById(R.id.Position);
                                    final String ID = eID.getText().toString();
                                    String Password = ePassword.getText().toString();
                                    String Name = eName.getText().toString();
                                    String Department = eDepartment.getText().toString();
                                    String Position = ePosition.getText().toString();
                                    textView = findViewById(R.id.addingMassage);
                                    FaceOperate faceOperate = new FaceOperate(Constant.KEY,Constant.SECRET,false);
                                    Response setFaceID = faceOperate.faceSetUserId(face_token,ID);
                                    faceIDresult = new String(setFaceID.getContent());
                                    JSONObject json = new JSONObject(faceIDresult);
                                    String faceToken = json.getString("user_id");
                                    Log.e("AddFace Result", "3333333333333333333333333 faceID got!\n"+faceToken);
                                    if(faceToken!=null) {
                                        SQLiteDatabase db = helper.getWritableDatabase();
                                        ContentValues values = new ContentValues();
                                        if (ID != null && Password != null && Name != null && Department != null && Position != null) {
                                            values.put(Constant.EMPLOYEE_ID, ID);
                                            values.put(Constant.EMPLOYEE_Password, Password);
                                            values.put(Constant.EMPLOYEE_NAME, Name);
                                            values.put(Constant.EMPLOYEE_DEPARTMENT, Department);
                                            values.put(Constant.EMPLOYEE_POSITION, Position);
                                            values.put(Constant.EMPLOYEE_FACEID, ID);
                                            long result = db.insert(Constant.TABLE_Employee, null, values);
                                            if (result > 0) {
                                                textView.setText("New employee added!");
                                                db.close();
                                            } else {
                                                textView.setText("Failed to create new employee!Please try again!");
                                            }
                                        } else {
                                            textView.setText("Please provide all Employee information!");
                                        }
                                    }
                                    else {
                                        textView.setText("Failed to create new employee!Please try again!");
                                    }
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }
                            }
                        }).start();

                    }catch (Exception e){
                        e.printStackTrace();
                    }
        }
        else {
            Toast.makeText(this, "Please select picture for employee and upload it.", Toast.LENGTH_SHORT).show();
        }
    }}

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
                        if (face_token==null){

                            textView = findViewById(R.id.addingMassage);
                            textView.setText("The picture is invalided, pleas select another picture.");

                        }else {

                            textView = findViewById(R.id.addingMassage);
                            textView.setText("Check completed!");
                            check = true;
                        }
                        Log.e("AddFace Result", "1111111111111111111111111 FaceToken got!\n" + face_token);

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
                            textView.setText("Upload completed!");
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
        JSONObject json = new JSONObject(res);
        Log.e("response","22222222222222222222222222\n"+res);
        if(!json.getString("faces").equals("[]")){
            String faceToken = json.optJSONArray("faces").optJSONObject(0).optString("face_token");
            return faceToken;
        }
        String faceToken=null;
        Log.e("response","22222222222222222222222222\n"+res);
        return faceToken;
    }

}
