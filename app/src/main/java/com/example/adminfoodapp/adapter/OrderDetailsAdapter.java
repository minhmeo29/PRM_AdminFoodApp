package com.example.adminfoodapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.adminfoodapp.databinding.OrderDetailItemBinding;
import java.util.ArrayList;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.OrderDetailsViewHolder> {
    private final Context context;
    private final ArrayList<String> foodNames;
    private final ArrayList<String> foodImages;
    private final ArrayList<Integer> foodQuantitys;
    private final ArrayList<String> foodPrices;

    public OrderDetailsAdapter(Context context, ArrayList<String> foodNames, ArrayList<String> foodImages, ArrayList<Integer> foodQuantitys, ArrayList<String> foodPrices) {
        this.context = context;
        this.foodNames = foodNames;
        this.foodImages = foodImages;
        this.foodQuantitys = foodQuantitys;
        this.foodPrices = foodPrices;
    }

    @NonNull
    @Override
    public OrderDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OrderDetailItemBinding binding = OrderDetailItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new OrderDetailsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailsViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return foodNames.size();
    }

    class OrderDetailsViewHolder extends RecyclerView.ViewHolder {
        private final OrderDetailItemBinding binding;

        OrderDetailsViewHolder(OrderDetailItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(int position) {
            binding.foodName.setText(foodNames.get(position));
            binding.foodQuantity.setText(String.valueOf(foodQuantitys.get(position)));
            String uriString = foodImages.get(position);
            Uri uri = Uri.parse(uriString);
            Glide.with(context).load(uri).into(binding.foodImage);
            binding.foodPrice.setText(foodPrices.get(position));
        }
    }
} 