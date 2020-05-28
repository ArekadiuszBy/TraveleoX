package com.example.traveleoo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.nio.channels.InterruptedByTimeoutException;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    //Call variables
    private static final int REQUEST_CALL = 1; //permission check
    Button btHelp;
    String helpNumber = "666";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar jako actionbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        Button buttonlogin = (Button) findViewById(R.id.buttonLogin);
        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogin();
            }
        });

        Button buttonregister = (Button) findViewById(R.id.buttonRegister);
        buttonregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegister();
            }
        });

        Button button = (Button) findViewById(R.id.buttontimer);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimer();
            }
        });

        Button buttonmap = (Button) findViewById(R.id.mapAPI);
        buttonmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMaps();
            }
        });

        Button notepad = (Button) findViewById(R.id.notes);
        notepad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNotepad();
            }
        });

        Button browser = (Button) findViewById(R.id.browserbutton);
        browser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBrowser();
            }
        });

        btHelp = findViewById(R.id.help);

        btHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });
    }

    private void makePhoneCall(){
        String number = helpNumber.toString();
        if(number.trim().length() > 0) {       //trim removes empty spaces

            //permission to make call
            //Jeśli się nie powiedzie
            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            }
            //Jeśli się powiedzie
            else {
                String dial ="tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }
        else {        //Na przyszłość do wprowadzania innych numerów
            Toast.makeText(MainActivity.this, "Enter phone number", Toast.LENGTH_SHORT).show();
        }
    }

    //Ctrl + O
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            }
            else {
                //przed this nie trzeba pisać MainActivity, bo nie jesteśmy w tej klasie
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_login: {
                Intent LoginFragment = new Intent(this, LoginFragment.class);
                startActivity(LoginFragment);
                break;
            }
            case R.id.nav_register: {
                Intent Register = new Intent(this, Register.class);
                startActivity(Register);
                break;
            }
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;                //select the item after click
    }

    @Override       //Zamykanie Drawera (pasek)
        public void onBackPressed(){
            if(drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }
            else {
                super.onBackPressed();
            }
        }


    //Login and Register
    public void openLogin(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
    public void openRegister(){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }


    public void openTimer(){
        Intent intent = new Intent(this, Timerr.class);
        startActivity(intent);
    }


    public void openMaps()
    {
        Intent intent = new Intent(this, MapsAct.class);
        startActivity(intent);
    }

    public void openNotepad()
    {
        Intent intent = new Intent(this, Notepad.class);
        startActivity(intent);
    }

    public void openBrowser()
    {
        Intent intent = new Intent(this, Browser.class);
        startActivity(intent);
    }
    
}
