package com.example.admin.augscan;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class additemActivity extends AppCompatActivity {

    public static String msg1;
    public EditText itemcategory,itemcategory1,phno;
    public static  boolean verifyOtpCheck;
    String store1 ;
    public TextView itemname,itemname1;
    public TextView itembarcode,itembarcode1;
    EditText etDate,assignedTo;
    DatePickerDialog.OnDateSetListener setListener;
    private FirebaseAuth firebaseAuth;

    private ProgressBar progressBar;
    public static TextView resulttextview;
    public static String dicv,verificationCode="";

    public static Button scanbutton, additemtodatabase,getotp;
    DatabaseReference databaseReference;

    DatabaseReference databaseReferencecat;
    public boolean isset=false;
    public static String itemnameValue="",itembarcodeValue="",phone_num="",itemcategoryValue="",assignedValue="",dateValue="";

    String [] assets={"Computer","CPU","Laptop","Monitor","Keyboard","Mouse","Switch","Router","Hub","Printer","Other","UPS","Headphone","Headset"};
    enum Color {
        RED,
        GREEN,
        BLUE;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);
        firebaseAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReferencecat = FirebaseDatabase.getInstance().getReference("Users");
        resulttextview = findViewById(R.id.barcodeview);
        additemtodatabase = findViewById(R.id.additembuttontodatabase);
        scanbutton = findViewById(R.id.buttonscan);
        itemname = findViewById(R.id.edititemname);
        itemname1 = findViewById(R.id.edititemname);
        itemcategory= findViewById(R.id.editcategory);
        itemcategory1= findViewById(R.id.editcategory);
        //  itemprice = findViewById(R.id.editprice);
        phno = findViewById(R.id.editprice);
        itembarcode= findViewById(R.id.barcodeview);
        itembarcode1= findViewById(R.id.barcodeview);
        etDate=findViewById(R.id.editText);
        assignedTo=findViewById(R.id.assignedTo);
        getotp=findViewById(R.id.otp);
        progressBar = (ProgressBar) findViewById(R.id.progressotp);
        progressBar.getIndeterminateDrawable().setColorFilter(0xFFFFFFFF, android.graphics.PorterDuff.Mode.MULTIPLY);
        progressBar.setVisibility(View.GONE);
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        EditText phoneNumberEditText = findViewById(R.id.editprice);
        String phoneNumberPattern = "^\\d{10}$"; // regular expression pattern for 10-digit phone number
        Pattern pattern = Pattern.compile(phoneNumberPattern);

// Set a text change listener on the phone number EditText to validate the input
        phoneNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Get the input text and check if it matches the pattern
                String inputText = charSequence.toString().trim();
                Matcher matcher = pattern.matcher(inputText);
                boolean isInputValid = matcher.matches();
                // Enable or disable a button based on the input validity
                Button submitButton = findViewById(R.id.otp);
                // submitButton.setEnabled(isInputValid);

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        additemActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,setListener,year,month,day);
                // datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(GREEN));
                datePickerDialog.show();
            }
        });

        setListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayofmonth) {
                month=month+1;
                String date = day+"/"+month+"/"+year;
                etDate.setText(date);

            }
        };
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        additemActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month=month+1;
                        String date = day+"/"+month+"/"+year;
                        etDate.setText(date);
                    }
                },year,month,day);
                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });
        // String result = finaluser.substring(0, finaluser.indexOf("@"));

        itemname.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                store1 = s.toString();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        scanbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ScanCodeActivity.class));
            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,assets);
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.edititemname);
        autoCompleteTextView.setAdapter(adapter);
        additemtodatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                additem();
               /* if(!additemtodatabase.isEnabled())
                {
                    additemtodatabase.setEnabled(false);
                    Toast.makeText(additemActivity.this, "button disabled.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(additemActivity.this, "Please verify otp first.", Toast.LENGTH_SHORT).show();
                }*/

            }
        });

        getotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ph = phno.getText().toString().trim();
                if(ph.isEmpty() || ph.length() < 10) {
                    Toast.makeText(additemActivity.this, "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
                }


                else{
                    additemtodatabase.setEnabled(true);
                    isset=true;
                    progressBar.setVisibility(View.VISIBLE);
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+91"+phno.getText().toString(),
                            60,
                            TimeUnit.SECONDS,
                            additemActivity.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                    Toast.makeText(additemActivity.this,"Phone number verified!",Toast.LENGTH_SHORT).show();
                                    verificationCode = phoneAuthCredential.getSmsCode();
                                    //Intent intent = new Intent(additemActivity.this, Otp.class);
                                    signInWithPhoneAuthCredential(phoneAuthCredential);
                                    progressBar.setVisibility(View.GONE);
                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    Toast.makeText(additemActivity.this,"Failed.",Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }

                                @Override
                                public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    Intent intent = new Intent(additemActivity.this, Otp.class);
                                    String mob = phno.getText().toString();
                                    intent.putExtra("mobile",mob);
                                    intent.putExtra("verificationId",verificationId);
                                    startActivity(intent);
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(additemActivity.this,"Sent.",Toast.LENGTH_SHORT).show();


                                }
                            }
                    );


                }

            }
        });

    }
    public boolean isVerifyOtpCheck()
    {
        additemtodatabase.setEnabled(false);
        if(isset)
        {
            return true;
        }
        else
        {
            additemtodatabase.setEnabled(true);
            Toast.makeText(additemActivity.this,"Please verify OTP first ",Toast.LENGTH_SHORT).show();
            return false;
        }

    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // User authentication was successful
                            FirebaseUser user = task.getResult().getUser();
                        } else {
                            // User authentication failed
                            Toast.makeText(additemActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    // addding item to databse
    public  void additem(){
        itemcategory= findViewById(R.id.editcategory);
        itemnameValue = itemname.getText().toString();
        itembarcodeValue = itembarcode.getText().toString();
        phone_num = phno.getText().toString();
        itemcategoryValue = itemcategory.getText().toString();
        dateValue= etDate.getText().toString();
        assignedValue = assignedTo.getText().toString();
        final FirebaseUser users = firebaseAuth.getCurrentUser();
        String finaluser=users.getEmail();
        String resultemail = finaluser.replace(".","");

        if (itembarcodeValue.isEmpty() ) {
            itembarcode.setError("Please scan a barcode");
            itembarcode.requestFocus();
            return;
        }
        if(itemcategoryValue.isEmpty())
        {
            itemcategory.setError("Please select a category of asset");
            itemcategory.requestFocus();
            return;
        }

        if (itemcategoryValue.isEmpty() ) {
            itemcategory.setError("Enter Model name");
            itemcategory.requestFocus();
            return;
        }
        if (phone_num.isEmpty() ) {
            phno.setError("Enter Phone number");
            phno.requestFocus();
            return;
        }  if (itemnameValue.isEmpty() ) {
            itemname.setError("Enter Asset category ");
            itemname.requestFocus();
            return;
        }
        if (dateValue.isEmpty() ) {
            etDate.setError("Enter Assigned date");
            etDate.requestFocus();
            return;
        }


        if(!TextUtils.isEmpty(itemnameValue)&&!TextUtils.isEmpty(itemcategoryValue)&&!TextUtils.isEmpty(phone_num)&&!TextUtils.isEmpty(dateValue)){

            if(itemnameValue.equalsIgnoreCase(assets[0])||itemnameValue.equalsIgnoreCase(assets[1])||itemnameValue.equalsIgnoreCase(assets[2])) {

                if(isVerifyOtpCheck()) {
                    Intent intent = new Intent(this, Comp_details.class);
                    String ibv = itembarcode.getText().toString();
                    intent.putExtra("barcode", ibv);
                    String icv = itemcategory.getText().toString();
                    intent.putExtra("category", icv);
                    startActivity(intent);
                    Toast.makeText(additemActivity.this," Please fill more details.",Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                if(isVerifyOtpCheck()) {
                    Intent intent1 = new Intent(this, Other_details.class);
                    String ibv1 = itembarcode1.getText().toString();
                    intent1.putExtra("barcode1", ibv1);
                    startActivity(intent1);
                    String icv1 = itemcategory1.getText().toString();
                    intent1.putExtra("category1", icv1);
                    startActivity(intent1);
                    Toast.makeText(additemActivity.this," Please fill more details.",Toast.LENGTH_SHORT).show();
                }
            }
            Intent intent2 = new Intent(this, deleteItemsActivity.class);
            dicv= itemcategory1.getText().toString();
            intent2.putExtra("categoryd",dicv);
            sendBroadcast(intent2);




        }
        else {
            Toast.makeText(additemActivity.this,"Please Fill all the fields",Toast.LENGTH_SHORT).show();
        }

    }


    // logout below
    private void Logout()
    {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(additemActivity.this,LoginActivity.class));
        Toast.makeText(additemActivity.this,"LOGOUT SUCCESSFUL", Toast.LENGTH_SHORT).show();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case  R.id.logoutMenu:{
                Logout();
            }
        }
        return super.onOptionsItemSelected(item);
    }

}