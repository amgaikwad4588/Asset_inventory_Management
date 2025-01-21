package com.example.admin.augscan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class Otp extends AppCompatActivity {
    EditText input1,input2,input3,input4,input5,input6;
    TextView resendOTP;
    Button button;
    private FirebaseAuth firebaseAuth,auth;
    private String verify,code="",pass="",rname="";
    public  static  String childValue="",finaluser="";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_otp);
        TextView mob = findViewById(R.id.Msg);
        firebaseAuth = FirebaseAuth.getInstance();
        auth = FirebaseAuth.getInstance();
        mob.setText(String.format("+91 %s", getIntent().getStringExtra("mobile")));
        input1 = findViewById(R.id.otp_1);
        input2 = findViewById(R.id.otp_2);
        input3 = findViewById(R.id.otp_3);
        input4 = findViewById(R.id.otp_4);
        input5 = findViewById(R.id.otp_5);
        input6 = findViewById(R.id.otp_6);
        button = findViewById(R.id.verify_buttno);
          setupOtpInput();
        verify = getIntent().getStringExtra("verificationId");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(input1.getText().toString().trim().isEmpty()||input2.getText().toString().trim().isEmpty()||input3.getText().toString().trim().isEmpty()||input4.getText().toString().trim().isEmpty()||input5.getText().toString().trim().isEmpty()||input6.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(Otp.this," Please enter OTP.",Toast.LENGTH_SHORT).show();

                    return;

                }
                 code = input1.getText().toString() +
                        input2.getText().toString()+ input3.getText().toString()+
                        input4.getText().toString()+ input5.getText().toString()+
                        input6.getText().toString();
                signInWithPhoneAuthCredential();

        }
        });
            }
            private void setOtp()
            {
                Logout();
                LoginActivity loginActivity = new LoginActivity();
                switch (loginActivity.getSignInType()) {
                    case "login":
                        // Sign in with login email and password
                        firebaseAuth.signInWithEmailAndPassword(LoginActivity.email, LoginActivity.pass)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        button.setVisibility(View.VISIBLE);
                                        if (task.isSuccessful()) {
                                           } else {
                                            Toast.makeText(Otp.this, "Please enter valid OTP.", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });
                        login();
                        break;
                    case "register":
                        // Sign in with registration email and password
                        firebaseAuth.signInWithEmailAndPassword(RegisterActivity.email1, RegisterActivity.pass1)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        button.setVisibility(View.VISIBLE);
                                        if (task.isSuccessful()) {


                                        } else {
                                            Toast.makeText(Otp.this, "Please enter valid OTP.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                        registerUser();
                        break;
                    case "dashboard":
                        firebaseAuth.signInWithEmailAndPassword(dashboardActivity.finaluser, dashboardActivity.childValue)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        button.setVisibility(View.VISIBLE);
                                        if (task.isSuccessful()) {



                                        } else {
                                            Toast.makeText(Otp.this, "Please enter valid OTP.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                        loginwithdash();
                        break;
                    default:
                        // Handle case where both sets of credentials are null
                        Toast.makeText(Otp.this, "Please enter valid credentials.", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
    private void signInWithPhoneAuthCredential()
    {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(
                verify,code
        );
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // User authentication was successful
                            FirebaseUser user = task.getResult().getUser();
                            Toast.makeText(Otp.this, "Authentication success.",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Otp.this, additemActivity.class);
                            additemActivity.additemtodatabase.setEnabled(true);
                            additemActivity.verifyOtpCheck=true;
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            setOtp();
                        } else {
                            // User authentication failed
                            Toast.makeText(Otp.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

            private void Logout() {
                firebaseAuth.signOut();
                finish();
            }

            public void login() {
                //String pass =Password.getText().toString().trim();
                //email=Email.getText().toString().trim();
                validate(LoginActivity.email, LoginActivity.pass);
            }
    public void loginwithdash() {
        //String pass =Password.getText().toString().trim();
        //email=Email.getText().toString().trim();
        validate(dashboardActivity.finaluser,dashboardActivity.childValue);
    }

            public void validate(String userEmail, String userPassword) {

                if (userEmail.isEmpty() && userPassword.isEmpty()) {
                    //  Toast.makeText(LoginActivity.this, "Please enter email and password.", Toast.LENGTH_SHORT).show();
                } else {
                    //  processDialog.setMessage("Please Wait...");
                    // processDialog.show();

                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    auth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //processDialog.dismiss();
                                //Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(LoginActivity.this, dashboardActivity.class));
                            } else {
                                // Toast.makeText(LoginActivity.this, "Login Failed. Make Sure you are connected to Internet", Toast.LENGTH_SHORT).show();
                                //processDialog.dismiss();
                            }
                        }
                    });
                }
            }

            private void registerUser() {
                validate(RegisterActivity.email1, RegisterActivity.pass1);
            }

            private void setupOtpInput() {
                input1.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (!s.toString().trim().isEmpty()) {
                            input2.requestFocus();
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                input2.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (!s.toString().trim().isEmpty()) {
                            input3.requestFocus();
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                input3.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (!s.toString().trim().isEmpty()) {
                            input4.requestFocus();
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                input4.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (!s.toString().trim().isEmpty()) {
                            input5.requestFocus();
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                input5.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (!s.toString().trim().isEmpty()) {
                            input6.requestFocus();
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        }