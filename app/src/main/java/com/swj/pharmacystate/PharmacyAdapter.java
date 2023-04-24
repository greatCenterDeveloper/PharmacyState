package com.swj.pharmacystate;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PharmacyAdapter extends RecyclerView.Adapter<PharmacyAdapter.VH> {

    Context context;
    ArrayList<PharmacyItem> items;

    public PharmacyAdapter(Context context, ArrayList<PharmacyItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        PharmacyItem item = items.get(position);
        holder.tvNum.setText(String.valueOf(item.num));
        holder.tvNum.setTag(item);
        holder.tvPharmacyName.setText(item.name);
        holder.tvPharmacyAddress.setText(item.roadAddr);
        if(item.roadAddr == null) holder.tvPharmacyAddress.setText(item.lotNoAddr);
        holder.tvPharmacyBusinessDay.setText(item.businessDay);
}

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VH extends RecyclerView.ViewHolder {

        TextView tvNum;
        TextView tvPharmacyName;
        TextView tvPharmacyAddress;
        TextView tvPharmacyBusinessDay;

        public VH(@NonNull View itemView) {
            super(itemView);
            tvNum = itemView.findViewById(R.id.tv_num);
            tvPharmacyName = itemView.findViewById(R.id.tv_pharmacy_name);
            tvPharmacyAddress = itemView.findViewById(R.id.tv_pharmacy_address);
            tvPharmacyBusinessDay = itemView.findViewById(R.id.tv_pharmacy_business_day);

            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(context, PharmacyInfoActivity.class);
                PharmacyItem item = (PharmacyItem) tvNum.getTag();
                intent.putExtra("item", item);
                context.startActivity(intent);
            });
        }
    }
}
