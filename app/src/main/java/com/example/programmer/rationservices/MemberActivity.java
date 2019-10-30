package com.example.programmer.rationservices;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.programmer.rationservices.adapters.RecMemberAdapter;
import com.example.programmer.rationservices.admin_activities.AdminOperationAddActivity;
import com.example.programmer.rationservices.admin_activities.AdminOperationUpdateActivity;
import com.example.programmer.rationservices.fragments.AddMemberBottomSheet;
import com.example.programmer.rationservices.models.FamilyClientItem;
import com.example.programmer.rationservices.ui.OnItemRecycleViewClickListner;
import com.example.programmer.rationservices.ui.OnUptadeClickBottomSheet;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MemberActivity extends AppCompatActivity implements OnItemRecycleViewClickListner {

    public static String id, id1, name1, age1;
    // ui
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    // var
    private RecMemberAdapter recMemberAdapter;
    private List<FamilyClientItem> list;
    private boolean is_user = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        swipeRefreshLayout = findViewById(R.id.refersh);
        if (getIntent().hasExtra("is_user")) {
            is_user = getIntent().getBooleanExtra("is_user", false);
        }
        AdminOperationAddActivity.databaseReference.keepSynced(true);
        if (getIntent().hasExtra("member")) {
            id = getIntent().getStringExtra("member");
        } else {
            Toast.makeText(this, "there is a proplem please try again!", Toast.LENGTH_LONG).show();
            onBackPressed();
        }

        list = new ArrayList<>();

        setupRecMember();

        buildSwipeLayout();
    }


    private void buildSwipeLayout() {


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
                list = new ArrayList<>();
                swipeRefreshLayout.setRefreshing(true);
                fillRecycle();
            }
        });

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_green_dark, android.R.color.holo_orange_light, android.R.color.holo_purple);
    }

    private void setupRecMember() {
        recyclerView = findViewById(R.id.rec_member);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        recMemberAdapter = new RecMemberAdapter(this);

        fillRecycle();

    }

    private void fillRecycle() {
        swipeRefreshLayout.setRefreshing(true);
        String real_id;
        if (AdminOperationUpdateActivity.id_user != null) {
            real_id = AdminOperationUpdateActivity.id_user;
        } else {
            real_id = AdminOperationAddActivity.id_card;
        }
        AdminOperationAddActivity.databaseReference
                .child(real_id).child(AdminOperationAddActivity.DB_NAME_MEMBER)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            FamilyClientItem familyClientItem = new FamilyClientItem();
                            familyClientItem.setName(snapshot.child("name").getValue().toString());

                            familyClientItem.setId_card(snapshot.child("id_card").getValue().toString());
                            familyClientItem.setAge(snapshot.child("age").getValue().toString());
                            list.add(familyClientItem);


                        }
                        recMemberAdapter.addList(list, is_user);
                        recyclerView.setAdapter(recMemberAdapter);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        }, 700);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(MemberActivity.this, "there is aproplem try again later", Toast.LENGTH_LONG).show();

                    }
                });


    }

    @Override
    public void onClick(final String card_id, String name, String age, String type) {
        id1 = card_id;
        age1 = age;
        name1 = name;
        if (type.equals(RecMemberAdapter.UPDATE)) {
            AddMemberBottomSheet bottomSheet = new AddMemberBottomSheet(MemberActivity.this, true);
            bottomSheet.show(getSupportFragmentManager(), "chid2");

            bottomSheet.onClickUpdate(new OnUptadeClickBottomSheet() {
                @Override
                public void onClickUbtade(boolean test) {
                    list.clear();
                    list = new ArrayList<>();
                    fillRecycle();
                }
            });
        } else if (type.equals(RecMemberAdapter.DEL)) {

            new AlertDialog.Builder(MemberActivity.this)
                    .setTitle("Attention please")
                    .setMessage("Are you sure to want delete this member of client !")
                    .setPositiveButton("I am sure", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            AdminOperationAddActivity.databaseReference.child(AdminOperationAddActivity.id_card).child(AdminOperationAddActivity.DB_NAME_MEMBER)
                                    .child(card_id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    list.clear();
                                    list = new ArrayList<>();
                                    fillRecycle();
                                    Toast.makeText(MemberActivity.this, "member is deleted", Toast.LENGTH_SHORT).show();

                                }
                            });

                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {


                }
            }).create().show();
        } else
            Toast.makeText(this, "fail try again", Toast.LENGTH_SHORT).show();

    }


}

