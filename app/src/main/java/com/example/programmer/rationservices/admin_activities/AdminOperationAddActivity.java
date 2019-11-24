package com.example.programmer.rationservices.admin_activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.programmer.rationservices.MemberActivity;
import com.example.programmer.rationservices.R;
import com.example.programmer.rationservices.fragments.AddMemberBottomSheet;
import com.example.programmer.rationservices.models.ClientItem;
import com.example.programmer.rationservices.models.FamilyClientItem;
import com.example.programmer.rationservices.scan_activities.ScanAdminActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;

public class AdminOperationAddActivity extends AppCompatActivity {
    public static final String DB_REF = "clients";
    public static final String DB_NAME_MEMBER = "member_name";

    // ui
    private Toolbar toolbar;
    private EditText ed_name, ed_email, ed_phone, ed_city, ed_street, ed_id_card, ed_balance;
    // var
    private String detielOfVCard;
    private ClientItem clientItem;
    private Double balance;
    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static DatabaseReference databaseReference = database.getReference(DB_REF);
    public static String name, email, phone, city, street, id_card, date;
    public FamilyClientItem familyClientItemList;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_operation_add);

        databaseReference.keepSynced(true);

        clientItem = new ClientItem();

        checkIntentData();

        testAndFillVCard();

        fillToolBar();

        buildAllEditedTextAndFill();

    }

    private void buildAllEditedTextAndFill() {
        ed_balance = findViewById(R.id.ed_blance);
        ed_city = findViewById(R.id.ed_city);
        ed_email = findViewById(R.id.ed_email);
        ed_name = findViewById(R.id.ed_name);
        ed_id_card = findViewById(R.id.ed_id);
        ed_street = findViewById(R.id.ed_street);
        ed_phone = findViewById(R.id.ed_phone);


        city = clientItem.getCity();
        String[] ss = clientItem.getEmail().split("URL");
        if (Patterns.EMAIL_ADDRESS.matcher(ss[0].trim()).matches()) {
            email = ss[0].trim();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(ss[0].trim()).matches()) {
            ed_email.setError("enter corret email sysnatx");


        }

        String[] ss1 = clientItem.getName().split("ORG");
        name = ss1[0].trim();
        id_card = clientItem.getId_card();
        street = clientItem.getStreet();
        String[] ss2 = clientItem.getPhoneNumber().split("TEL");
        phone = ss2[0].trim();

        ed_email.setText(email);
        ed_phone.setText(phone);
        ed_street.setText(street);
        ed_city.setText(city);
        ed_id_card.setText(id_card);
        ed_name.setText(name);

    }

    private void fillToolBar() {
        toolbar = findViewById(R.id.toolbar_add);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void checkIntentData() {
        if (getIntent().hasExtra(ScanAdminActivity.RESULT_ADMIN_ADD)) {
            detielOfVCard = getIntent().getExtras().getString(ScanAdminActivity.RESULT_ADMIN_ADD, "");

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
        new AlertDialog.Builder(AdminOperationAddActivity.this).setTitle("There's a problem!")
                .setMessage("no data avalible try scan again!")
                .setPositiveButton("try again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onBackPressed();

                    }
                }).create().show();
    }

    public void addChild(View view) {
        id_card = ed_id_card.getText().toString().trim();
        AddMemberBottomSheet bottomSheet = new AddMemberBottomSheet(AdminOperationAddActivity.this, false);

        bottomSheet.show(getSupportFragmentManager(), "child");


    }

    public void showChild(View view) {
        Intent intent = new Intent(getApplicationContext(), MemberActivity.class);
        id_card = ed_id_card.getText().toString().trim();
        intent.putExtra("member", id_card);
        startActivity(intent);
    }

    public void addAll(View view) {
        java.util.Date date1 = new Date();
        date = date1.toString();

        HashMap<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("email", email);
        map.put("card_id", id_card);
        map.put("street", street);
        map.put("city", city);
        map.put("date_of_addition", date);
        map.put("phone", phone);

        if (!(ed_balance.getText().toString().trim().equals("") || ed_balance.getText() == null)) {
            balance = Double.parseDouble(ed_balance.getText().toString().trim());
        } else
            balance = 0.0;

        map.put("balance", balance + "");

        databaseReference.child(id_card).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(AdminOperationAddActivity.this, "saved", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
