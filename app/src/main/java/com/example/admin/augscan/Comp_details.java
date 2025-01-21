package com.example.admin.augscan;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;


public class Comp_details extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    DatabaseReference databaseReferencecat;
    EditText Device_id;
    Button submit;
    public static String processor = "";
    public static String sysType = "";
    public static String os_ver = "";
    public static String install_RAM = "";
    public static String dev_id = "", imgURL="";
    public static String img = "";
    TextView Processor, System_Type, OS_version, RAM;
    ImageView ImageURL;
    private final int CAMERA_REQ_CODE = 100;
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    private StorageReference mStorageRef;
    private ProgressBar progressBar;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_comp_details);
        Button button = findViewById(R.id.btncam);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent icamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(icamera, CAMERA_REQ_CODE);
                }
                catch(Exception e)
                {
                    Toast.makeText(Comp_details.this, "Please allow permission for camera", Toast.LENGTH_SHORT).show();
                }
            }
        });
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        mStorageRef = storageRef.child("AssetImages");
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReferencecat = FirebaseDatabase.getInstance().getReference("Users");
        mStorageRef = FirebaseStorage.getInstance().getReference();
        Processor = findViewById(R.id.Processor);
        System_Type = findViewById(R.id.SysType);
        OS_version = findViewById(R.id.OS_version);
        RAM = findViewById(R.id.Installed_ram);
        Device_id = findViewById(R.id.Device_id);
        submit = findViewById(R.id.submit);
        ImageURL = findViewById(R.id.ImgCam);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.GONE);
        progressDialog = new ProgressDialog(this);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                additem1();



            }
        });

    }


    protected void onActivityResult(int requestcode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestcode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestcode == CAMERA_REQ_CODE) {
                progressBar.setVisibility(View.VISIBLE);
                progressDialog.setMessage("Please wait while the image is being uploaded...");
                progressDialog.show();
                onCaptureImageResult(data);
                Bitmap img = (Bitmap) data.getExtras().get("data");
                ImageURL.setImageBitmap(img);

            }
        }
    }

    public void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        byte bb[] = bytes.toByteArray();
        ImageURL.setImageBitmap(thumbnail);
        uploadToFirebase(bb);
    }

    void uploadToFirebase(byte[] bb) {

        StorageReference sr = mStorageRef.child("AssetImages/"+additemActivity.itemcategoryValue+"_jpg");

        sr.putBytes(bb)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Toast.makeText(Comp_details.this, "Please wait while the image is being uploaded! ", Toast.LENGTH_SHORT).show();
                        // Image has been successfully uploaded to the Storage
                        sr.getDownloadUrl()
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        // Retrieve the download URL and store it in the Realtime Database
                                        imgURL = uri.toString();
                                    }

                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Comp_details.this, "URL Unavailable", Toast.LENGTH_SHORT).show();
                                    }
                                });
                        progressBar.setVisibility(View.GONE);
                        progressDialog.dismiss();
                        Toast.makeText(Comp_details.this, "Image uploaded successfully!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Comp_details.this, "Failed to upload", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public void additem1() {
//IMAGE DB SHOULD BE ADDED
        processor = Processor.getText().toString();
        sysType = System_Type.getText().toString();
        os_ver = OS_version.getText().toString();
        install_RAM = RAM.getText().toString();
        dev_id = Device_id.getText().toString();
        img = imgURL;

        final FirebaseUser users = firebaseAuth.getCurrentUser();
        String finaluser = users.getEmail();
        String resultemail = finaluser.replace(".", "");
        if (processor.isEmpty() && sysType.isEmpty() && os_ver.isEmpty() && install_RAM.isEmpty() && img.isEmpty()) {
            Processor.setError("It's Empty");
            System_Type.setError("It's Empty");
            OS_version.setError("It's Empty");
            RAM.setError("It's Empty");
            Device_id.setError("It's Empty");
            Processor.requestFocus();
            System_Type.requestFocus();
            OS_version.requestFocus();
            RAM.requestFocus();
            Device_id.requestFocus();

            return;
        }


        if (!TextUtils.isEmpty(processor) && !TextUtils.isEmpty(sysType) && !TextUtils.isEmpty(os_ver) && !TextUtils.isEmpty(install_RAM) && !TextUtils.isEmpty(dev_id) && !TextUtils.isEmpty(img)) {
            if(TextUtils.isEmpty(additemActivity.assignedValue)){
                additemActivity.assignedValue="Not Assigned";
            }
            Items items = new Items(additemActivity.itemnameValue, additemActivity.itemcategoryValue, additemActivity.phone_num, additemActivity.itembarcodeValue,
                    additemActivity.dateValue, additemActivity.assignedValue, processor, dev_id, sysType, os_ver, install_RAM,img);
            databaseReference.child(resultemail).child("Items").child(additemActivity.itembarcodeValue)
                    .setValue(items);
            databaseReferencecat.child(resultemail).child("ItemByCategory").child(additemActivity.
                    itemcategoryValue).child(additemActivity.itembarcodeValue).setValue(items);
            Toast.makeText(Comp_details.this, "Details Added", Toast.LENGTH_SHORT).show();
            Toast.makeText(Comp_details.this, "email pass "+Otp.childValue+" "+Otp.finaluser, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Comp_details.this, additem_splash.class));
        } else {
            Toast.makeText(Comp_details.this, "Please Fill all the fields", Toast.LENGTH_SHORT).show();
        }

    }
}