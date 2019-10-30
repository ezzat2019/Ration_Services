package com.example.programmer.rationservices.scan_activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.programmer.rationservices.admin_activities.AdminOperationAddActivity;
import com.example.programmer.rationservices.admin_activities.AdminOperationUpdateActivity;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanAdminActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    public static final String RESULT_ADMIN_ADD = "admin_add";
    public static final String RESULT_ADMIN_UPDATE = "admin_update";

    // ui
    private ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
    }

    @Override
    public void handleResult(Result result) {
        if (getIntent().hasExtra(ScanAdminActivity.RESULT_ADMIN_ADD)) {
            Intent intent = new Intent(getApplicationContext(), AdminOperationAddActivity.class);
            intent.putExtra(RESULT_ADMIN_ADD, result.getText());
            startActivity(intent);
            finish();

        } else if (getIntent().hasExtra(ScanAdminActivity.RESULT_ADMIN_UPDATE)) {
            Intent intent = new Intent(getApplicationContext(), AdminOperationUpdateActivity.class);
            intent.putExtra(RESULT_ADMIN_UPDATE, result.getText());
            startActivity(intent);
            finish();

        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }
}
