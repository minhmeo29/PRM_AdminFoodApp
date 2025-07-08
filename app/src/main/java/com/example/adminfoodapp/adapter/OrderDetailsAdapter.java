package com.example.adminfoodapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.adminfoodapp.R;

import java.util.ArrayList;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.OrderDetailsViewHolder> {

    private Context context;
    private ArrayList<String> foodNames;
    private ArrayList<String> foodImages;
    private ArrayList<Integer> foodQuantitys;
    private ArrayList<String> foodPrices;

    public OrderDetailsAdapter(Context context,
                               ArrayList<String> foodNames,
                               ArrayList<String> foodImages,
                               ArrayList<Integer> foodQuantitys,
                               ArrayList<String> foodPrices) {
        this.context = context;
        this.foodNames = foodNames;
        this.foodImages = foodImages;
        this.foodQuantitys = foodQuantitys;
        this.foodPrices = foodPrices;
    }

    @NonNull
    @Override
    public OrderDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_detail_item, parent, false);
        return new OrderDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailsViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return foodNames.size();
    }

    public class OrderDetailsViewHolder extends RecyclerView.ViewHolder {

        TextView foodName;
        TextView foodQuantity;
        TextView foodPrice;
        ImageView foodImage;

        public OrderDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.foodName);
            foodQuantity = itemView.findViewById(R.id.foodQuantity);
            foodPrice = itemView.findViewById(R.id.foodPrice);
            foodImage = itemView.findViewById(R.id.foodImage);
        }

        public void bind(int position) {
            foodName.setText(foodNames.get(position));
            foodQuantity.setText(String.valueOf(foodQuantitys.get(position)));
            foodPrice.setText(foodPrices.get(position));

            Uri uri = Uri.parse(foodImages.get(position));
            Glide.with(context).load(uri).into(foodImage);
        }
    }
}
