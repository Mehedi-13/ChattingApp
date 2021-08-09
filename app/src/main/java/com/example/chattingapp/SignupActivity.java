package com.example.chattingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.chattingapp.Models.Users;
import com.example.chattingapp.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class SignupActivity extends AppCompatActivity {

    ActivitySignupBinding binding;
//    private EditText name, email,phone,password;
//    private Button signUp,google,facebook;
//    private TextView alreadyAcc,signUpPhone;
   private   FirebaseAuth fAuth;
    private FirebaseDatabase database;
   private   ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
       // setContentView(R.layout.activity_signup);
        binding=ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getSupportActionBar().hide();
        fAuth= FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

        progressDialog= new ProgressDialog(SignupActivity.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We are creating your account");


//
//        name= findViewById(R.id.userNameReg);
//        email= findViewById(R.id.userMailReg);
//        phone= findViewById(R.id.userPhoneReg);
//        password= findViewById(R.id.userPassReg);
//        signUp= findViewById(R.id.signUpBtn);
//
//        google= findViewById(R.id.googleBtn);
//        facebook= findViewById(R.id.facebookBtn);
//
//        alreadyAcc= findViewById(R.id.alreadyAcc);
//        signUpPhone= findViewById(R.id.signWithPhone);


//        signUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(),SignInActivity.class));
//            }
//        });


        binding.alreadyAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this,SignInActivity.class));
            }
        });








        binding.signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //extract the date from the form



                if (binding.userNameReg.getText().toString().isEmpty()){
                    binding.userNameReg.setError("Required");
                    binding.userNameReg.requestFocus();
                }
                else if (binding.userMailReg.getText().toString().isEmpty()){
                    binding.userMailReg.setError("Required");
                    binding.userMailReg.requestFocus();
                }
                else if (binding.userPhoneReg.getText().toString().isEmpty()){
                    binding.userPhoneReg.setError("Required");
                    binding.userPhoneReg.requestFocus();
                }
                else if (binding.userPassReg.getText().toString().isEmpty()){
                    binding.userPassReg.setError("Required");
                    binding.userPassReg.requestFocus();
                }
                else {

                    progressDialog.show();

                    signUp();

                }





               //  signUp();


                //data is validated
                //register the user using firebase

//                Toast.makeText(RegistrationActivity.this, "Data Validated", Toast.LENGTH_SHORT).show();


            }
        });
    }

    private void signUp() {

        fAuth.createUserWithEmailAndPassword(binding.userMailReg.getText().toString(),binding.userPassReg.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {

                progressDialog.dismiss();

                if (task.isSuccessful()){
                    Users users= new Users(binding.userMailReg.getText().toString(),binding.userPhoneReg.getText().toString()
                            ,binding.userPassReg.getText().toString(),binding.userNameReg.getText().toString());

                    String id=task.getResult().getUser().getUid();
                    database.getReference().child("Users").child(id).setValue(users);


                    Toast.makeText(SignupActivity.this, "Account Create Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @org.jetbrains.annotations.NotNull Exception e) {
                Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //   private void signUp() {

//        String  userName= binding.userNameReg.getText().toString();
//        String  userEmail= binding.userMailReg.getText().toString();
//        String  userPhone= binding.userPhoneReg.getText().toString();
//        String  userPassword= binding.userPassReg.getText().toString();
//
//        if (userName.isEmpty()){
//            binding.userNameReg.setError("Full Name is Required");
//        }
//
//        if (userEmail.isEmpty()){
//            binding.userMailReg.setError("Email is Required");
//        }
//        if (userPhone.isEmpty()){
//            binding.userPhoneReg.setError("Phone Number is Required");
//        }
//
//        if (userPassword.isEmpty()){
//            binding.userPassReg.setError("Password is Required");
//        }

//        fAuth.createUserWithEmailAndPassword(binding.userMailReg.getText().toString(),binding.userPassReg.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
//                if (task.isSuccessful()){
//                   Users users= new Users(binding.userMailReg.getText().toString());
//
//                    Toast.makeText(SignupActivity.this, "Account Create Successfully", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull @org.jetbrains.annotations.NotNull Exception e) {
//                Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });

  //  }
}