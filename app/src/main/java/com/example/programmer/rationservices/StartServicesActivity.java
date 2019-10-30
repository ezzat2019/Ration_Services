package com.example.programmer.rationservices;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.programmer.rationservices.admin_activities.AdminActivity;
import com.example.programmer.rationservices.scan_activities.QRActivity;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class StartServicesActivity extends AppCompatActivity {
    public static final int CAMERA_CODE = 5;
    public static final int RC_SIGN_IN = 2;

    // ui
    private ImageButton scan_button;
    private Button btn_login;

    // var
    private List<AuthUI.IdpConfig> providers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_services);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        setupLogin();

        setupAllView();

        onClilkMethods();


    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();

    }

    private void onClilkMethods() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthUI.getInstance().signOut(getBaseContext());
            }
        });


        scan_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if (ContextCompat.checkSelfPermission(StartServicesActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                        ActivityCompat.requestPermissions(StartServicesActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_CODE);
                    } else {
                        Intent intent = new Intent(getApplicationContext(), QRActivity.class);
                        startActivity(intent);
                    }
                } else {
                    Intent intent = new Intent(getApplicationContext(), QRActivity.class);
                    startActivity(intent);
                }


            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(getApplicationContext(), QRActivity.class);
            startActivity(intent);

        } else {

        }
    }

    private void setupLogin() {
        // Choose authentication providers
        providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build()
        );

        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(providers)
                                .build(),
                        RC_SIGN_IN);

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                intent.putExtra("admin", user.getEmail());
                startActivity(intent);
                // ...
            } else {

            }
        }
    }

    private void setupAllView() {
        scan_button = findViewById(R.id.img_btn_scan);

    }
}
