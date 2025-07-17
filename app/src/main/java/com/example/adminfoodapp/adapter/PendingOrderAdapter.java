package com.example.adminfoodapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.adminfoodapp.databinding.PenidnigOrdersItemBinding;
import java.util.List;

public class PendingOrderAdapter extends RecyclerView.Adapter<PendingOrderAdapter.PendingOrderViewHolder> {
    private final Context context;
    private final List<String> customerNames;
    private final List<String> quantity;
    private final List<String> foodImage;
    private final OnItemClicked itemClicked;

    public interface OnItemClicked {
        void onItemClickListener(int position);
        void onItemAcceptClickListener(int position);
        void onItemDispatchClickListener(int position);
    }

    public PendingOrderAdapter(Context context, List<String> customerNames, List<String> quantity, List<String> foodImage, OnItemClicked itemClicked) {
        this.context = context;
        this.customerNames = customerNames;
        this.quantity = quantity;
        this.foodImage = foodImage;
        this.itemClicked = itemClicked;
    }

    @NonNull
    @Override
    public PendingOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PenidnigOrdersItemBinding binding = PenidnigOrdersItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PendingOrderViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingOrderViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return customerNames.size();
    }

    class PendingOrderViewHolder extends RecyclerView.ViewHolder {
        private final PenidnigOrdersItemBinding binding;
        private boolean isAccepted = false;

        PendingOrderViewHolder(PenidnigOrdersItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(int position) {
            binding.customerName.setText(customerNames.get(position));
            binding.pendingOredarQuantity.setText(quantity.get(position));
            String uriString = foodImage.get(position);
            Uri uri = Uri.parse(uriString);
            Glide.with(context).load(uri).into(binding.orderdFoodImage);

            binding.orderedAcceptButton.setText(!isAccepted ? "Accept" : "Dispatch");
            binding.orderedAcceptButton.setOnClickListener(v -> {
                if (!isAccepted) {
                    binding.orderedAcceptButton.setText("Dispatch");
                    isAccepted = true;
                    showToast("Order is accepted");
                    itemClicked.onItemAcceptClickListener(getAdapterPosition());
                } else {
                    int adapterPos = getAdapterPosition();
                    customerNames.remove(adapterPos);
                    notifyItemRemoved(adapterPos);
                    showToast("Order Is dispatched");
                    itemClicked.onItemDispatchClickListener(adapterPos);
                }
            });

            itemView.setOnClickListener(v -> itemClicked.onItemClickListener(position));
        }

        private void showToast(String message) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }
} 