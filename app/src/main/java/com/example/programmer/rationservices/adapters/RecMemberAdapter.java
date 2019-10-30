package com.example.programmer.rationservices.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.programmer.rationservices.R;
import com.example.programmer.rationservices.models.FamilyClientItem;
import com.example.programmer.rationservices.ui.OnItemRecycleViewClickListner;

import java.util.List;

public class RecMemberAdapter extends RecyclerView.Adapter<RecMemberAdapter.VH> {
    public static final String UPDATE = "update";
    public static final String DEL = "del";


    private static OnItemRecycleViewClickListner onItemRecycleViewClickListner;
    private static boolean b;
    private List<FamilyClientItem> familyClientItemList;

    public RecMemberAdapter(OnItemRecycleViewClickListner onItemRecycleViewClickListner) {
        RecMemberAdapter.onItemRecycleViewClickListner = onItemRecycleViewClickListner;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.member_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        holder.bindData(familyClientItemList.get(position));
    }

    public void addList(List<FamilyClientItem> familyClientItems, boolean b) {
        this.familyClientItemList = familyClientItems;
        RecMemberAdapter.b = b;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return familyClientItemList.size();
    }

    static class VH extends RecyclerView.ViewHolder {

        private EditText ed_id, ed_name, ed_age;
        private Button btn_update, btn_del;

        public VH(@NonNull View itemView) {
            super(itemView);

            ed_id = itemView.findViewById(R.id.ed_id_member);
            ed_name = itemView.findViewById(R.id.ed_name_member);
            ed_age = itemView.findViewById(R.id.ed_age_member);

            btn_del = itemView.findViewById(R.id.btn_del_member);
            btn_update = itemView.findViewById(R.id.btn_update_member);


        }

        public void bindData(final FamilyClientItem familyClientItem) {
            ed_name.setText(familyClientItem.getName());
            ed_id.setText(familyClientItem.getId_card());
            ed_age.setText(familyClientItem.getAge());
            if (b) {
                btn_del.setVisibility(View.GONE);
                btn_update.setVisibility(View.GONE);


            } else {
                btn_del.setVisibility(View.VISIBLE);
                btn_del.setVisibility(View.VISIBLE);
            }

            btn_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemRecycleViewClickListner.onClick(familyClientItem.getId_card(),
                            familyClientItem.getName(), familyClientItem.getAge(), RecMemberAdapter.UPDATE);
                }
            });
            btn_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemRecycleViewClickListner.onClick(familyClientItem.getId_card(),
                            familyClientItem.getName(), familyClientItem.getAge(), RecMemberAdapter.DEL);
                }
            });
        }
    }
}
