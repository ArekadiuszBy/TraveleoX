package com.example.traveleoo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.util.Arrays;

import static android.widget.Toast.LENGTH_SHORT;

public class Register extends AppCompatActivity implements RegisterView {

    EditText mFullName, mEmail, mPassword, mPassword2;
    Button mRegisterButton;
    ImageButton mProccessButtonRegister;
    FirebaseAuth fAuth;
    ProgressBar progressBarRegister;


    private RegisterPresenter presenter;                                                         //           ______________TESTY REGISTER____________
    public Register(Context mMockContext) {
    }
    public Register() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        presenter = new RegisterPresenter(this, new Register());                       //           ______________TESTY REGISTER____________

        //variables connect with xml

        fAuth = FirebaseAuth.getInstance();

        mFullName = findViewById(R.id.fullNameInput);

        //ogólne
        mEmail = findViewById(R.id.emailInput);
        mPassword = findViewById(R.id.passwordInput);

        //Register
        mPassword2 = findViewById(R.id.passwordInput2);
        mRegisterButton = findViewById(R.id.buttonRegister);               //przycisk przenoszący z rejestracji do logowania
        mProccessButtonRegister = findViewById(R.id.imageButtonRegister);       //Przycisk REJESTRUJĄCY
        progressBarRegister = findViewById(R.id.progressBarRegister);


                                //     If user is already logged -> can't go to Register activity
        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }



        mProccessButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                presenter.onRegisterClicked();                                     //           ______________TESTY REGISTER____________
                
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String password2 = mPassword2.getText().toString().trim();

                //validation
                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is required!");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is required!");
                    return;
                }
                if(TextUtils.isEmpty(password2)){
                    mPassword2.setError("Password is required!");
                    return;
                }

                if(password.length() < 6){
                    mPassword.setError("Password must be longer!");
                    return;
                }
                if(password.compareTo(password2) != password2.compareTo(password))  {
                    mPassword2.setError("Password doesn't match!");
                    return;
                }

                //Progress bar
                progressBarRegister.setVisibility(View.VISIBLE);


                // register the user in FIREBASE
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {               //Jeśli się tworzenie użytkownika powiedzie
                            Toast.makeText(Register.this, "User created.", LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                        else {                                  //Jeśli się tworzenie użytkownika nie powiedzie
                            Toast.makeText(Register.this, "ERROR!  " + task.getException().getMessage(), LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

                                            //______________TESTY REGISTER____________

    @Override
    public String getEmail() {
        return mEmail.getText().toString().trim();
    }

    @Override
    public void showEmailError(int resId) {
        mEmail.setError(getString(resId));
    }

    @Override
    public String getPassword() {
        return mPassword.getText().toString().trim();
    }

    @Override
    public String getPassword2() {
        return mPassword2.getText().toString().trim();
    }

    @Override
    public void onRegisterClicked() {

    }

    @Override
    public void showPasswordError(int resId) {
        mPassword.setError(getString(resId));
    }

    @Override
    public void showPassword2Error(int resId) {
        mPassword2.setError(getString(resId));
    }

    @Override
    public void startMainActivity() {
        new Intent(getApplicationContext(), MainActivity.class);
    }

    @Override
    public void showRegisterError(int resId) {
        Toast.makeText(Register.this, getString(resId) , LENGTH_SHORT).show();
    }

}
