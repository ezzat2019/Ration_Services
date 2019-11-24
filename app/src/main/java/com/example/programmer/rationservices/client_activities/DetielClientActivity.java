package com.example.programmer.rationservices.client_activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.programmer.rationservices.R;
import com.example.programmer.rationservices.models.ClientItem;
import com.example.programmer.rationservices.scan_activities.QRActivity;

public class DetielClientActivity extends AppCompatActivity {


    // ui
    private Toolbar toolbar;
    // var
    int i = 0; // fot test
    private String detielOfVCard;
    private ClientItem clientItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detiel_client);
        clientItem = new ClientItem();

        checkIntentData();
        testAndFillVCard();

        fillToolBar();
    }

    private void fillToolBar() {
        toolbar = findViewById(R.id.toolbar_detiel);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void checkIntentData() {
        if (getIntent().hasExtra(QRActivity.RESULT_ID)) {
            detielOfVCard = getIntent().getExtras().getString(QRActivity.RESULT_ID, "");

        } else {
            showDialogError();
        }
        if (detielOfVCard == null || detielOfVCard.equals("")) {
            showDialogError();
        }
    }

    private void testAndFillVCard() {
        Log.d("eeeeeee", detielOfVCard);

        String[] detiel = detielOfVCard.split(":");

        for (String s : detiel) {
            String[] detie1 = s.split(";");
            for (String ss : detie1) {
                Log.d("aaaaa", ss + " num" + i++);
                if (i == 6)
                    clientItem.setName(ss);
                else if (i == 11) {
                    clientItem.setStreet(ss);
                } else if (i == 12) {

                    clientItem.setCity(ss);
                } else if (i == 13) {
                    clientItem.setId_card(ss);
                } else if (i == 20)
                    clientItem.setPhoneNumber(ss);
                else if (i == 25) {
                    clientItem.setEmail(ss);
                }


            }


        }


        Log.d("qqqqq", "email " + clientItem.getEmail());
        Log.d("qqqqq", "phone " + clientItem.getPhoneNumber());
        Log.d("qqqqq", "city " + clientItem.getCity());
        Log.d("qqqqq", "street " + clientItem.getStreet());
        Log.d("qqqqq", "name " + clientItem.getName());
        Log.d("qqqqq", "id_card " + clientItem.getId_card());

    }

    private void showDialogError() {
        new AlertDialog.Builder(DetielClientActivity.this).setTitle("There's a problem!")
                .setMessage("no data avalible try scan again!")
                .setPositiveButton("try again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onBackPressed();

                    }
                }).create().show();
    }
}
