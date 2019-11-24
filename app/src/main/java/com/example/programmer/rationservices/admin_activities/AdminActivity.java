package com.example.programmer.rationservices.admin_activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.programmer.rationservices.R;
import com.example.programmer.rationservices.scan_activities.ScanAdminActivity;
import com.firebase.ui.auth.AuthUI;

public class AdminActivity extends AppCompatActivity {
    public static final int CAMERA_CODE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        buildIntent();

    }

    private void buildIntent() {
        if (getIntent().hasExtra("admin")) {
            String email = (String) getIntent().getExtras().get("admin");
            if (!email.equals("admin@yahoo.com")) {
                AuthUI.getInstance().signOut(getApplicationContext());
                Toast.makeText(this, "you not admin sure your email and try again", Toast.LENGTH_LONG).show();
                finish();
            }

        }
    }

    public void add(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (ContextCompat.checkSelfPermission(AdminActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(AdminActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_CODE);
            } else {
                Intent intent = new Intent(getApplicationContext(), ScanAdminActivity.class);
                intent.putExtra(ScanAdminActivity.RESULT_ADMIN_ADD, "add");
                startActivity(intent);
            }
        } else {
            Intent intent = new Intent(getApplicationContext(), ScanAdminActivity.class);
            intent.putExtra(ScanAdminActivity.RESULT_ADMIN_ADD, "add");
            startActivity(intent);
        }

    }

    public void update(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (ContextCompat.checkSelfPermission(AdminActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(AdminActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_CODE);
            } else {
                Intent intent = new Intent(getApplicationContext(), ScanAdminActivity.class);
                intent.putExtra(ScanAdminActivity.RESULT_ADMIN_UPDATE, "update");
                startActivity(intent);
            }
        } else {
            Intent intent = new Intent(getApplicationContext(), ScanAdminActivity.class);
            intent.putExtra(ScanAdminActivity.RESULT_ADMIN_UPDATE, "update");
            startActivity(intent);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


        } else {

        }
    }
}
