package com.example.adminfoodapp.adapter;


import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.adminfoodapp.databinding.ItemItemBinding;
import com.example.adminfoodapp.model.AllMenu;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.AddItemViewHolder> {

    private Context context;
    private ArrayList<AllMenu> menuList;
    private DatabaseReference databaseReference;
    private int[] itemQuantities;
    private OnDeleteClickListener onDeleteClickListener;

    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }

    public MenuItemAdapter(Context context, ArrayList<AllMenu> menuList,
                           DatabaseReference databaseReference,
                           OnDeleteClickListener onDeleteClickListener) {
        this.context = context;
        this.menuList = menuList;
        this.databaseReference = databaseReference;
        this.onDeleteClickListener = onDeleteClickListener;

        itemQuantities = new int[menuList.size()];
        for (int i = 0; i < itemQuantities.length; i++) {
            itemQuantities[i] = 1;
        }
    }

    @NonNull
    @Override
    public AddItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemItemBinding binding = ItemItemBinding.inflate(inflater, parent, false);
        return new AddItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AddItemViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    class AddItemViewHolder extends RecyclerView.ViewHolder {

        private final ItemItemBinding binding;

        public AddItemViewHolder(ItemItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int position) {
            AllMenu menuItem = menuList.get(position);
            int quantity = itemQuantities[position];

            binding.foodNameTextView.setText(menuItem.getFoodName());
            binding.priceTextView.setText(menuItem.getFoodPrice());

            if (menuItem.getFoodImage() != null) {
                Uri uri = Uri.parse(menuItem.getFoodImage());
                Glide.with(context).load(uri).into(binding.foodImageView);
            }

            binding.quantityTextVIew.setText(String.valueOf(quantity));

            binding.minusButton.setOnClickListener(v -> decreaseQuantity(position));
            binding.pluseButton.setOnClickListener(v -> increaseQuantity(position));
            binding.deleteButton.setOnClickListener(v -> onDeleteClickListener.onDeleteClick(position));
        }

        private void increaseQuantity(int position) {
            if (itemQuantities[position] < 10) {
                itemQuantities[position]++;
                binding.quantityTextVIew.setText(String.valueOf(itemQuantities[position]));
            }
        }

        private void decreaseQuantity(int position) {
            if (itemQuantities[position] > 1) {
                itemQuantities[position]--;
                binding.quantityTextVIew.setText(String.valueOf(itemQuantities[position]));
            }
        }
    }
}