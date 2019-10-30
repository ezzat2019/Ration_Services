package com.example.programmer.rationservices.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;


import com.example.programmer.rationservices.MemberActivity;
import com.example.programmer.rationservices.R;
import com.example.programmer.rationservices.admin_activities.AdminOperationAddActivity;
import com.example.programmer.rationservices.models.FamilyClientItem;
import com.example.programmer.rationservices.ui.OnUptadeClickBottomSheet;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Date;

public class AddMemberBottomSheet extends BottomSheetDialogFragment {
    // var
    String name, id, age, date;
    // ui
    private EditText ed_name, ed_id_card, ed_age;
    private Button btn_add;
    private Context context;
    private boolean update;
    private OnUptadeClickBottomSheet onUptadeClickBottomSheet;

    public AddMemberBottomSheet(Context context, boolean update) {

        this.update = update;
        this.context = context;
    }

    public void onClickUpdate(OnUptadeClickBottomSheet onUptadeClickBottomSheet) {
        this.onUptadeClickBottomSheet = onUptadeClickBottomSheet;
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        //Get the content View
        View contentView = View.inflate(getContext(), R.layout.chid_layout, null);


        dialog.setContentView(contentView);
        setupEdtitedText(contentView);

        setupButtonAndEvents(contentView);
        //Set the coordinator layout behavior
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();
        ((View) contentView.getParent()).setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
        //Set callback
        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View view, int i) {

                }

                @Override
                public void onSlide(@NonNull View view, float v) {

                }
            });
        }

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


    private void setupButtonAndEvents(View view) {
        btn_add = view.findViewById(R.id.btn_add_child1);
        if (update) {
            btn_add.setText("update");

            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    name = ed_name.getText().toString();
                    age = ed_age.getText().toString();
                    id = ed_id_card.getText().toString();
                    Date d = new Date();
                    date = d.toString();

                    FamilyClientItem item = new FamilyClientItem(name, id, age, date);
                    AdminOperationAddActivity.databaseReference.child(MemberActivity.id)
                            .child(AdminOperationAddActivity.DB_NAME_MEMBER)
                            .child(id).setValue(item).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            onUptadeClickBottomSheet.onClickUbtade(true);
                            Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
                            dismiss();

                        }
                    });


                }
            });
        } else {
            btn_add.setText("add");

            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    name = ed_name.getText().toString();
                    age = ed_age.getText().toString();
                    id = ed_id_card.getText().toString();
                    Date d = new Date();
                    date = d.toString();

                    FamilyClientItem item = new FamilyClientItem(name, id, age, date);
                    AdminOperationAddActivity.databaseReference.child(AdminOperationAddActivity.id_card)
                            .child(AdminOperationAddActivity.DB_NAME_MEMBER)
                            .child(id).setValue(item).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
                            dismiss();

                        }
                    });


                }
            });
        }

    }

    private void setupEdtitedText(View view) {
        ed_age = view.findViewById(R.id.ed_age_child);
        ed_name = view.findViewById(R.id.ed_name_child);
        ed_id_card = view.findViewById(R.id.ed_id_child);
        if (update) {
            ed_id_card.setEnabled(false);

            ed_name.setText(MemberActivity.name1);
            ed_id_card.setText(MemberActivity.id1);
            ed_age.setText(MemberActivity.age1);
        } else {
            ed_id_card.setEnabled(true);

        }

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }
}
