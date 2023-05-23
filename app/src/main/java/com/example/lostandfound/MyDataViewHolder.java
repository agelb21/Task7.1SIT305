package com.example.lostandfound;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


public class MyDataViewHolder extends RecyclerView.ViewHolder{
    private final TextView myDataItemView;


    private MyDataViewHolder(View itemView) {
        super(itemView);
        myDataItemView = itemView.findViewById(R.id.textView);
    }

    public void bind (String text) {
        myDataItemView.setText(text);
    }

    static MyDataViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item,parent,false);
        return new MyDataViewHolder(view);
    }


}
