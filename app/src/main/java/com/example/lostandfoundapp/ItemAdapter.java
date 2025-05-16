package com.example.lostandfoundapp;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private final Context context;
    private final ArrayList<String> statusList;
    private final ArrayList<String> descriptionList;
    private final ArrayList<Integer> idList;
    private final DBHelper dbHelper;

    public ItemAdapter(Context context, ArrayList<Integer> ids, ArrayList<String> statuses, ArrayList<String> descriptions, DBHelper dbHelper) {
        this.context = context;
        this.idList = ids;
        this.statusList = statuses;
        this.descriptionList = descriptions;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_card, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.tvStatus.setText(statusList.get(position));
        holder.tvDescription.setText(descriptionList.get(position));

        holder.itemView.setOnClickListener(v -> {
            int id = idList.get(position);
            boolean deleted = dbHelper.deleteItem(id);
            if (deleted) {
                Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show();
                idList.remove(position);
                statusList.remove(position);
                descriptionList.remove(position);
                notifyItemRemoved(position);
            } else {
                Toast.makeText(context, "Delete failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return idList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvStatus, tvDescription;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvDescription = itemView.findViewById(R.id.tvDescription);
        }
    }
}