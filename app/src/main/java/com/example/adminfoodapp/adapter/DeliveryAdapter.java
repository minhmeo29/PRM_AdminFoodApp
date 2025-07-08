package com.example.adminfoodapp.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminfoodapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DeliveryAdapter extends RecyclerView.Adapter<DeliveryAdapter.DeliveryViewHolder> {

    private final ArrayList<String> customerNames;
    private final ArrayList<String> moneyStatuses;

    public DeliveryAdapter(ArrayList<String> customerNames, ArrayList<String> moneyStatuses) {
        this.customerNames = customerNames;
        this.moneyStatuses = moneyStatuses;
    }

    @NonNull
    @Override
    public DeliveryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.delivery_item, parent, false);
        return new DeliveryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryViewHolder holder, int position) {
        holder.bind(customerNames.get(position), moneyStatuses.get(position));
    }

    @Override
    public int getItemCount() {
        return customerNames.size();
    }

    static class DeliveryViewHolder extends RecyclerView.ViewHolder {

        private final TextView customerName;
        private final TextView statusMoney;
        private final CardView statusColor;

        public DeliveryViewHolder(@NonNull View itemView) {
            super(itemView);
            customerName = itemView.findViewById(R.id.customerName);
            statusMoney = itemView.findViewById(R.id.statusMoney);
            statusColor = itemView.findViewById(R.id.StatusColor);
        }

        public void bind(String name, String status) {
            customerName.setText(name);
            statusMoney.setText(status);

            // Chuyển status về lowercase để đồng nhất
            String key = status.toLowerCase().trim();

            // Tạo map màu
            Map<String, Integer> colorMap = new HashMap<>();
            colorMap.put("received", Color.parseColor("#4CAF50"));      // xanh lá
            colorMap.put("not received", Color.parseColor("#F44336"));  // đỏ
            colorMap.put("pending", Color.parseColor("#FFC107"));       // vàng

            int color = colorMap.getOrDefault(key, Color.DKGRAY);

            // Đặt màu
            statusMoney.setTextColor(color);
            statusColor.setCardBackgroundColor(color);
        }
    }
}
