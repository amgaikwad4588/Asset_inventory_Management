package com.example.admin.augscan;

import android.app.ProgressDialog;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class Other_details extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    DatabaseReference databaseReferencecat ;
    ImageView ImageURL1;
    Button button1;
    Button button;
    private final int CAMERA_REQ_CODE=100;
    public static String imgURL1="";
    AutoCompleteTextView ConType;
    EditText Company,ModelNo;
    String conType[] = {"Wired", "Bluetooth", "Wifi"};
    public static String img1 = "";
    private StorageReference mStorageRef;
    private ProgressBar progressBar;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_details);
        button1=findViewById(R.id.btcam);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent icamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(icamera, CAMERA_REQ_CODE);
                }
                catch(Exception e)
                {
                    Toast.makeText(Other_details.this, "Please allow permission for camera", Toast.LENGTH_SHORT).show();
                }
            }
        });

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        mStorageRef = storageRef.child("AssetImages");
        firebaseAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReferencecat = FirebaseDatabase.getInstance().getReference("Users");
        ConType = findViewById(R.id.ConType);
        Company = findViewById(R.id.company);
        ModelNo = findViewById(R.id.modelNo);
        button=findViewById(R.id.button);
        ImageURL1 = findViewById(R.id.ImgCam1);
        progressBar = (ProgressBar)findViewById(R.id.progressBar21);
        progressBar.setVisibility(View.GONE);
        progressDialog = new ProgressDialog(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,conType);
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.ConType);

        autoCompleteTextView.setAdapter(adapter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                additem2();
            }
        });

    }

    protected void onActivityResult(int requestcode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestcode, resultCode, data);
        if(resultCode==RESULT_OK)
        {
            if(requestcode==CAMERA_REQ_CODE)
            {
                progressBar.setVisibility(View.VISIBLE);
                progressDialog.setMessage("Please wait while the image is being uploaded...");
                progressDialog.show();
                onCaptureImageResult(data);
                Bitmap img= (Bitmap)data.getExtras().get("data");
                ImageURL1.setImageBitmap(img);
            }
        }
    }
    public void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        byte bb[] = bytes.toByteArray();
        ImageURL1.setImageBitmap(thumbnail);
        uploadToFirebase(bb);
    }

    private void uploadToFirebase(byte[] bb) {
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
                                        imgURL1 = uri.toString();
                                    }

                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Other_details.this, "URL Unavailable", Toast.LENGTH_SHORT).show();
                                    }
                                });

                        progressBar.setVisibility(View.GONE);
                        progressDialog.dismiss();
                        Toast.makeText(Other_details.this, "Image uploaded successfully!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Other_details.this, "Failed to upload", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void additem2()
    {

        String contype = ConType.getText().toString();
        String company = Company.getText().toString();
        String modelno = ModelNo.getText().toString();
        img1 = imgURL1;
        final FirebaseUser users = firebaseAuth.getCurrentUser();
        String finaluser = users.getEmail();
        String resultemail = finaluser.replace(".", "");
        if (contype.isEmpty() && company.isEmpty() && modelno.isEmpty() && img1.isEmpty()) {
            ConType.setError("Enter connection type");
            Company.setError("Enter company name");
            ModelNo.setError("Enter model number");
            //IMAGE ISEMPTY CONDITION SHOULD BE NOW
            ConType.requestFocus();
            Company.requestFocus();
            ModelNo.requestFocus();
            return;
        }

        if (!TextUtils.isEmpty(contype) && !TextUtils.isEmpty(company) && !TextUtils.isEmpty(modelno) && !TextUtils.isEmpty(img1) ) {
            if(TextUtils.isEmpty(additemActivity.assignedValue)){
                additemActivity.assignedValue="Not Assigned";
            }
            Items items  = new Items(additemActivity.itemnameValue,additemActivity.itemcategoryValue,additemActivity.phone_num,
                    additemActivity.itembarcodeValue,additemActivity.dateValue,additemActivity.assignedValue ,contype,company,modelno,img1);
            databaseReference.child(resultemail).child("Items").child(additemActivity.itembarcodeValue)
                    .setValue(items);
            databaseReferencecat.child(resultemail).child("ItemByCategory").child(additemActivity.
                    itemcategoryValue).child(additemActivity.itembarcodeValue).setValue(items);

            Toast.makeText(Other_details.this,  "Details Added", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Other_details.this,additem_splash.class));

        }
        else {
            Toast.makeText(Other_details.this, "Please Fill all the fields", Toast.LENGTH_SHORT).show();
        }

    }
}