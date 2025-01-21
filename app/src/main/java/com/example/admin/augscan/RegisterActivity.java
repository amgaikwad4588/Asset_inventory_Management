package com.example.admin.augscan;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity
{
    boolean passvisible;
    private EditText editTextName, editTextEmail, editTextPassword, editTextPhone,editTextcPassword;
    public Button UserRegisterBtn;

    public ConnectivityManager connectivityManager;
    public NetworkCapabilities networkCapabilities;
    public Network network;
    private TextView logintt;
    private ProgressBar progressBar;
    public static String pass1="",email1="",name1="";
    private FirebaseAuth mAuth;
    public boolean isConnected;
    private ProgressDialog processDialog;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        network = connectivityManager.getActiveNetwork();
        networkCapabilities = connectivityManager.getNetworkCapabilities(network);
        isConnected = networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);

        editTextName = findViewById(R.id.departmentName);
        editTextEmail = findViewById(R.id.emailRegister);
        editTextPassword = findViewById(R.id.passwordRegister);
        editTextcPassword= findViewById(R.id.confirmPassword);
        UserRegisterBtn= findViewById(R.id.button_register);
        progressBar = (ProgressBar) findViewById(R.id.progressbars);
        progressBar.getIndeterminateDrawable().setColorFilter(0xFFFFFFFF, android.graphics.PorterDuff.Mode.MULTIPLY);

        progressBar.setVisibility(View.GONE);
        logintt=findViewById(R.id.loginT);
        mAuth = FirebaseAuth.getInstance();
        processDialog = new ProgressDialog(this);

        logintt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));

            }
        });

        UserRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
        editTextPassword.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int right=2;
                if(event.getAction()==MotionEvent.ACTION_UP)
                {
                    if (event.getRawX() >= editTextPassword.getRight() - editTextPassword.getCompoundDrawables()[right].getBounds().width()) {
                        int selec=editTextPassword.getSelectionEnd();
                        if(passvisible) {
                            editTextPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.baseline_visibility_off_24,0);
                            editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passvisible=false;
                        } else {
                            editTextPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.baseline_visibility_24,0);
                            editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passvisible=true;
                        }
                        return true;
                    }
                }
                return false;
            }
        });
        editTextcPassword.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int right=2;
                if(event.getAction()==MotionEvent.ACTION_UP)
                {
                    if (event.getRawX() >= editTextcPassword.getRight() - editTextcPassword.getCompoundDrawables()[right].getBounds().width()) {
                        int selec=editTextcPassword.getSelectionEnd();
                        if(passvisible) {
                            editTextcPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.baseline_visibility_off_24,0);
                            editTextcPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passvisible=false;
                        } else {
                            editTextcPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.baseline_visibility_24,0);
                            editTextcPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passvisible=true;
                        }
                        return true;
                    }
                }
                return false;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            Toast.makeText(RegisterActivity.this, "User email alredy exists!", Toast.LENGTH_LONG).show();

        }
    }



    private void registerUser() {
        final String name = editTextName.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String cpassword = editTextcPassword.getText().toString().trim();
        name1=name;
        email1=email;
        pass1=cpassword;
        if (email.isEmpty()) {
            editTextEmail.setError("Enter email.");
            editTextEmail.requestFocus();
            return;
        }
        if (name.isEmpty()) {
            editTextName.setError("Enter department/company name");
            editTextName.requestFocus();
            return;
        }



        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Not a valid email address");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Enter password.");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Password should be more than 6 characters!");
            editTextPassword.requestFocus();
            return;
        }
        if(!password.equals(cpassword)){
            editTextcPassword.setError("Password does not match!");
            editTextcPassword.requestFocus();
            return;
        }

        processDialog.setMessage("Please Wait...");
        processDialog.show();

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {



                            final User user = new User(
                                    name,
                                    email,password

                            );

                            FirebaseUser usernameinfirebase = mAuth.getCurrentUser();
                            String UserID=usernameinfirebase.getEmail();
                            String resultemail = UserID.replace(".","");

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(resultemail).child("UserDetails")
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressBar.setVisibility(View.GONE);
                                            if (task.isSuccessful()) {
                                                processDialog.dismiss();
                                                Toast.makeText(RegisterActivity.this, "Registration Success", Toast.LENGTH_LONG).show();
                                                Intent i ;
                                                i=new Intent(RegisterActivity.this, dashboardActivity.class);
                                                startActivity(i);
                                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                            } else {
                                                Toast.makeText(RegisterActivity.this, "Error occurred! Try again. ", Toast.LENGTH_LONG).show();
                                                processDialog.dismiss();
                                            }
                                        }
                                    });

                        }
                        else {
                            progressBar.setVisibility(View.GONE);
                            if(!isConnected)
                            {
                                Toast.makeText(RegisterActivity.this, "Registration Failed.Make Sure you are connected to Internet. Please try again! ", Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(RegisterActivity.this, "Registered email already exists. Please try again! ", Toast.LENGTH_LONG).show();

                            }


                        }
                    }
                });

    }
}