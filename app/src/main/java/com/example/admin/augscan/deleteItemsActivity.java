package com.example.admin.augscan;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class deleteItemsActivity extends AppCompatActivity {
    public static TextView resultdeleteview;
    EditText itemcategory;
    private FirebaseAuth firebaseAuth;
    Button scantodelete, deletebtn;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_delete_items);
        firebaseAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        resultdeleteview = findViewById(R.id.barcodedelete);
        scantodelete = findViewById(R.id.buttonscandelete);
        deletebtn= findViewById(R.id.deleteItemToTheDatabasebtn);

        scantodelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ScanCodeActivitydel.class));
            }
        });

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletefrmdatabase();

            }
        });

    }

    public void deletefrmdatabase() {
        String deletebarcodevalue = resultdeleteview.getText().toString();
        String deletecategoryValue = additemActivity.itemcategoryValue;
        final FirebaseUser users = firebaseAuth.getCurrentUser();
        String finaluser = users.getEmail();
        String resultemail = finaluser.replace(".", "");
        if (!TextUtils.isEmpty(deletebarcodevalue)) {
            databaseReference.child(resultemail).child("Items").child(deletebarcodevalue).addListenerForSingleValueEvent(new ValueEventListener() {

                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Item exists in the database, delete it and start the delete_splash activity
                        databaseReference.child(resultemail).child("Items").child(deletebarcodevalue).removeValue();
                        // databaseReference.child(resultemail).child("ItemByCategory").child(deletecategoryValue).child(deletebarcodevalue).removeValue();
                        startActivity(new Intent(deleteItemsActivity.this, delete_splash.class));
                    } else {
                        // Item does not exist in the database, show an error message
                        Toast.makeText(deleteItemsActivity.this, "Item does not exist in the database", Toast.LENGTH_SHORT).show();
                    }
                }

                public void onCancelled(DatabaseError databaseError) {
                    // Handle errors here
                    Toast.makeText(deleteItemsActivity.this, "Problem occurred", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(deleteItemsActivity.this, "Please scan Barcode", Toast.LENGTH_SHORT).show();
        }
    }

}