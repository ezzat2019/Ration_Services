package com.example.programmer.rationservices.admin_activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.programmer.rationservices.MemberActivity;
import com.example.programmer.rationservices.R;
import com.example.programmer.rationservices.models.ClientItem;
import com.example.programmer.rationservices.scan_activities.QRActivity;
import com.example.programmer.rationservices.scan_activities.ScanAdminActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.HashMap;

public class AdminOperationUpdateActivity extends AppCompatActivity {

    public static String id_user;
    int i = 0;
    // ui
    private Toolbar toolbar;
    private EditText ed_name, ed_email, ed_phone, ed_city, ed_street, ed_id_card, ed_balance;
    private CardView cardView;
    private ProgressBar progressBar;
    private Button btn_ubdate, btn_del;
    // var
    private String detielOfVCard;
    private ClientItem clientItem;
    private Double balance;
    private boolean isUser = false;
    private String name, email, phone, city, street, id_card, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_operation_update);
        btn_del = findViewById(R.id.btn_delete);
        btn_ubdate = findViewById(R.id.btn_update);


        Log.d("ccccccc", "oncrete");

        clientItem = new ClientItem();

        cardView = findViewById(R.id.card1);

        progressBar = findViewById(R.id.progress);

        AdminOperationAddActivity.databaseReference.keepSynced(true);

        checkIntentData();

        testAndFillVCard();

        checkClientIsExit();

        fillToolBar();

        buildAllEditedTextAndFill();

        checkUserLogin();
    }



    private void checkUserLogin() {
        if (getIntent().hasExtra(QRActivity.RESULT_ID)) {
            btn_ubdate.setVisibility(View.GONE);
            btn_del.setVisibility(View.GONE);
            isUser = true;
            enabledEditedText(false);


        } else {
            btn_ubdate.setVisibility(View.VISIBLE);
            btn_del.setVisibility(View.VISIBLE);
            isUser = false;
            enabledEditedText(true);

        }
    }

    void showProgress(boolean b) {
        if (b) {
            progressBar.setVisibility(View.VISIBLE);
            cardView.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            cardView.setVisibility(View.VISIBLE);
        }
    }

    void enabledEditedText(boolean b) {

        ed_balance.setEnabled(b);
        ed_city.setEnabled(b);
        ed_email.setEnabled(b);
        ed_id_card.setEnabled(b);
        ed_phone.setEnabled(b);
        ed_name.setEnabled(b);
        ed_street.setEnabled(b);

    }

    private void checkClientIsExit() {


        AdminOperationAddActivity.databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (!dataSnapshot.child(id_card).exists()) {
                    showProgress(true);
                    showDialogError();

                } else {
                    ed_email.setText(dataSnapshot.child(id_card).child("email").getValue().toString());
                    ed_phone.setText(dataSnapshot.child(id_card).child("phone").getValue().toString());
                    ed_street.setText(dataSnapshot.child(id_card).child("street").getValue().toString());
                    ed_city.setText(dataSnapshot.child(id_card).child("city").getValue().toString());
                    ed_name.setText(dataSnapshot.child(id_card).child("name").getValue().toString());
                    ed_balance.setText(dataSnapshot.child(id_card).child("balance").getValue().toString());
                    showProgress(false);


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                showDialogError();
                showProgress(true);

            }
        });
    }

    private void fillToolBar() {
        toolbar = findViewById(R.id.toolbar_update);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void buildAllEditedTextAndFill() {
        ed_balance = findViewById(R.id.ed_blance2);
        ed_city = findViewById(R.id.ed_city2);
        ed_email = findViewById(R.id.ed_email2);
        ed_name = findViewById(R.id.ed_name2);
        ed_id_card = findViewById(R.id.ed_id2);
        ed_street = findViewById(R.id.ed_street2);
        ed_phone = findViewById(R.id.ed_phone2);
        id_card = clientItem.getId_card();
        ed_id_card.setText(id_card);
        ed_id_card.setEnabled(false);


    }

    private void checkIntentData() {
        if (getIntent().hasExtra(ScanAdminActivity.RESULT_ADMIN_UPDATE)) {
            detielOfVCard = getIntent().getExtras().getString(ScanAdminActivity.RESULT_ADMIN_UPDATE, "");

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
        new AlertDialog.Builder(AdminOperationUpdateActivity.this).setTitle("There's a problem!")
                .setMessage("this client not assigned add him and try again ")
                .setPositiveButton("try again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onBackPressed();

                    }
                }).create().show();
    }

    public void addUpdate(View view) {
        java.util.Date date1 = new Date();
        date = date1.toString();
      String date2 = date1.toString();

        name=ed_name.getText().toString().trim();
        email=ed_email.getText().toString().trim();
        street=ed_street.getText().toString().trim();
        city=ed_city.getText().toString().trim();
        phone=ed_phone.getText().toString().trim();


        HashMap<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("email", email);
        map.put("card_id", id_card);
        map.put("street", street);
        map.put("city", city);
        map.put("date_of_last_ubdate", date);
        map.put("phone", phone);

        if (!(ed_balance.getText().toString().trim().equals("") || ed_balance.getText() == null)) {
            balance = Double.parseDouble(ed_balance.getText().toString().trim());
        } else
            balance = 0.0;

        map.put("balance", balance + "");

        AdminOperationAddActivity.databaseReference.child(id_card).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(AdminOperationUpdateActivity.this, "updated", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void deleteClient(View view) {

        new AlertDialog.Builder(AdminOperationUpdateActivity.this)
                .setTitle("Attention please")
                .setMessage("Are you sure to want delete this client !")
                .setPositiveButton("I am sure", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AdminOperationAddActivity.databaseReference.child(id_card).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(AdminOperationUpdateActivity.this, "client is deleted", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                        });

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        }).create().show();
    }

    public void showAllMember(View view) {
        id_user = ed_id_card.getText().toString();

        if (isUser) {

            Intent intent = new Intent(getApplicationContext(), MemberActivity.class);
            intent.putExtra("member", id_card);
            intent.putExtra("is_user", true);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getApplicationContext(), MemberActivity.class);
            intent.putExtra("member", id_card);
            startActivity(intent);
        }

    }
}
