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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static android.widget.Toast.LENGTH_SHORT;

//implements TESTY LOGIN
public class Login extends AppCompatActivity implements LoginView
{

    EditText mFullName, mEmail, mPassword;
    Button mLogout;
    ImageButton  mProccessButtonLogin;
    FirebaseAuth fAuth;
    ProgressBar  progressBarLogin;


    private Login presenter;            //           ______________TESTY LOGIN____________
    public Login(Context mMockContext) {
    }
    public Login() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = new LoginPresenter(this, new Login());   //          ______________TESTY LOGIN____________


        //variables connect with xml

        fAuth = FirebaseAuth.getInstance();

        mFullName = findViewById(R.id.fullNameInput);

        //ogólne
        mEmail = findViewById(R.id.emailInput);
        mPassword = findViewById(R.id.passwordInput);

        //Login
        mLogout = findViewById(R.id.loginButtonRegister);                  //przycisk wylogowania
        mProccessButtonLogin = findViewById(R.id.imageButtonLogin);             //Przycisk LOGUJĄCY
        progressBarLogin = findViewById(R.id.progressBarLogin);

        mProccessButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                presenter.onLoginClicked();  //              ______________TESTY LOGIN____________

                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                    //validation
                    if(TextUtils.isEmpty(email)){
                        mEmail.setError("Email is required!");
                        return;
                    }
                    if(TextUtils.isEmpty(password)){
                        mPassword.setError("Password is required!");
                        return;
                    }

                    if(password.length() < 6){
                        mPassword.setError("Password must be longer!");
                        return;
                    }

                    //Progress bar
                    progressBarLogin.setVisibility(View.VISIBLE);

                    // Authenticate the user

                    fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Login.this, "Logged!", LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }
                            else{
                                Toast.makeText(Login.this, "ERROR!  " + task.getException().getMessage(), LENGTH_SHORT).show();
                            }
                        }
                    });
                }
        });

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            if(bundle.getString("login.java") != null) {
                Toast.makeText(getApplicationContext(),"data:" + bundle.getString("login_java"), LENGTH_SHORT).show();
            }
        }



    }
             // LOG OUT activity
    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(Login.this, "Logged out succesfully :)", LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    //                  ______________TESTY LOGIN____________
    @Override
    public String getEmail() {
        return mEmail.getText().toString().trim();
    }

    @Override
    public void showEmailError(int resId) {
        mEmail.setError(getString(resId));
    }

    // __Testy Login 2 sposób (Nie działa jeszcze)__
    @Override
    public void onLoginClicked() {

    }

    @Override
    public String getPassword() {
        return mPassword.getText().toString().trim();
    }

    @Override
    public void showPasswordError(int resId) {
        mPassword.setError(getString(resId));
    }



//     SPRAWDZANIE POPRAWNOSCI PO ZALOGOWANIU SIE
    @Override
    public void startMainActivity() {
        new Intent(getApplicationContext(), MainActivity.class);
    }

    @Override
    public void showLoginError(int resId){
        Toast.makeText(Login.this, getString(resId) , LENGTH_SHORT).show();
    }

}
