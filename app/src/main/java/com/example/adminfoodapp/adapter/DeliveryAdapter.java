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
import com.example.adminfoodapp.model.OrderDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DeliveryAdapter extends RecyclerView.Adapter<DeliveryAdapter.DeliveryViewHolder> {

    private final ArrayList<OrderDetails> orderList;

    public DeliveryAdapter(ArrayList<OrderDetails> orderList) {
        this.orderList = orderList;
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
        holder.bind(orderList.get(position));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    static class DeliveryViewHolder extends RecyclerView.ViewHolder {

        private final TextView customerName;
        private final TextView statusMoney;
        private final CardView statusColor;
        private final TextView totalPrice;

        public DeliveryViewHolder(@NonNull View itemView) {
            super(itemView);
            customerName = itemView.findViewById(R.id.customerName);
            statusMoney = itemView.findViewById(R.id.statusMoney);
            statusColor = itemView.findViewById(R.id.StatusColor);
            totalPrice = itemView.findViewById(R.id.totalPrice);
        }

        public void bind(OrderDetails order) {
            customerName.setText(order.getUserName() != null ? order.getUserName() : "");
            String paymentStatus = order.isPaymentReceived() ? "received" : "not received";
            statusMoney.setText("Payment: " + paymentStatus);
            if (totalPrice != null) {
                totalPrice.setText("Total: " + (order.getTotalPrice() != null ? order.getTotalPrice() : ""));
            }
            // Đặt màu trạng thái
            int color;
            switch (paymentStatus) {
                case "received":
                    color = Color.parseColor("#4CAF50"); // xanh lá
                    break;
                case "pending":
                    color = Color.parseColor("#FFC107"); // vàng
                    break;
                case "not received":
                default:
                    color = Color.parseColor("#F44336"); // đỏ
                    break;
            }
            statusMoney.setTextColor(color);
            statusColor.setCardBackgroundColor(color);
        }
    }
}
