package com.example.admin.augscan;

import static com.example.admin.augscan.R.id.monitorbtn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewInventory extends AppCompatActivity {
    private int counttotalnoofitem = 0;
    DatabaseReference mdatabaseReference;
    private FirebaseAuth firebaseAuth;
    private TextView totalnoofitem;

public static String device="";
Button computer,laptop,cpu,printer,hub,monitor,switches,router,mouse,keyboard,headphones,ups,other;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_view_inventory2);


        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser users = firebaseAuth.getCurrentUser();
        String finaluser = users.getEmail();
        String resultemail = finaluser.replace(".", "");
        mdatabaseReference = FirebaseDatabase.getInstance().getReference("Users").child(resultemail).child("Items");

        computer = findViewById(R.id.computer);
        laptop = findViewById(R.id.Laptop);
        cpu = findViewById(R.id.CPU);
        printer = findViewById(R.id.printerbtn);
        hub = findViewById(R.id.Hubbtn);
        monitor = findViewById(monitorbtn);
        switches = findViewById(R.id.switchbtn);
        router = findViewById(R.id.routerbtn);
        mouse = findViewById(R.id.mousebtn);
        keyboard = findViewById(R.id.keyboardbtn);
        ups = findViewById(R.id.upsbtn);
        headphones = findViewById(R.id.headphonebtn);
        other = findViewById(R.id.other);
        totalnoofitem = findViewById(R.id.totalnoitem);

        mdatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    counttotalnoofitem = (int) dataSnapshot.getChildrenCount();
                    totalnoofitem.setText(Integer.toString(counttotalnoofitem));
                }else{
                    totalnoofitem.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

  computer.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {
         device="Computer";
         startActivity(new Intent(ViewInventory.this, viewInventoryActivity.class));
     }
 });
        laptop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                device="Laptop";
                startActivity(new Intent(ViewInventory.this, viewInventoryActivity.class));
            }
        });
        cpu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                device="Cpu";
                startActivity(new Intent(ViewInventory.this, viewInventoryActivity.class));
            }
        });
        printer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                device="Printer";
                startActivity(new Intent(ViewInventory.this, viewInventoryActivity.class));
            }
        });
        hub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                device="Hub";
                startActivity(new Intent(ViewInventory.this, viewInventoryActivity.class));
            }
        });
        monitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                device="monitor";
                startActivity(new Intent(ViewInventory.this, viewInventoryActivity.class));
            }
        });
        switches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                device="switch";
                startActivity(new Intent(ViewInventory.this, viewInventoryActivity.class));
            }
        });
        ups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                device="ups";
                startActivity(new Intent(ViewInventory.this, viewInventoryActivity.class));
            }
        });
        router.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                device="router";
                startActivity(new Intent(ViewInventory.this, viewInventoryActivity.class));
            }
        });
        mouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                device="mouse";
                startActivity(new Intent(ViewInventory.this, viewInventoryActivity.class));
            }
        });
        keyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                device="keyboard";
                startActivity(new Intent(ViewInventory.this, viewInventoryActivity.class));
            }
        });
        headphones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                device="headset";
                startActivity(new Intent(ViewInventory.this, viewInventoryActivity.class));
            }
        });
        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                device="other";
                startActivity(new Intent(ViewInventory.this, viewInventoryActivity.class));
            }
        });
    }
}