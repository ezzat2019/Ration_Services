package com.example.programmer.rationservices.scan_activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.programmer.rationservices.admin_activities.AdminOperationUpdateActivity;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    public static final String RESULT_ID = "result";

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
        Intent intent = new Intent(getApplicationContext(), AdminOperationUpdateActivity.class);
        intent.putExtra(RESULT_ID, "user");
        intent.putExtra(ScanAdminActivity.RESULT_ADMIN_UPDATE, result.getText());
        startActivity(intent);
        finish();

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
