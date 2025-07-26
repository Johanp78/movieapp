package com.example.assignmentonepart2;

import androidx.recyclerview.widget.RecyclerView;

import com.example.assignmentonepart2.databinding.ItemLayoutBinding;

public class MyViewHolder extends RecyclerView.ViewHolder {
    ItemLayoutBinding binding;

    public MyViewHolder(ItemLayoutBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
