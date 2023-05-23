package com.example.lostandfound;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class MyDataListAdapter extends ListAdapter<MyData, MyDataListAdapter.MyViewHolder> {

    Context context;
    MyDataViewModel myDataViewModel;

    protected MyDataListAdapter(@NonNull DiffUtil.ItemCallback<MyData> diffCallback, Context context, MyDataViewModel myDataViewModel) {
        super(diffCallback);
        this.context = context;
        this.myDataViewModel = myDataViewModel;
    }

    @NonNull
    @Override
    public MyDataListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyDataListAdapter.MyViewHolder holder, int position) {
        MyData current = getItem(position);
        holder.name.setText(current.getListTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), LostFoundItemActivity.class);
                intent.putExtra("listItem",""+ current.getItemListing());
                intent.putExtra("intID", current.getId());
                context.startActivity(intent);
            }
        });


    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView);
        }
    }

    public static class MyDataDiff extends  DiffUtil.ItemCallback<MyData>{

        @Override
        public boolean areItemsTheSame(@NonNull MyData oldItem, @NonNull MyData newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull MyData oldItem, @NonNull MyData newItem) {
            return oldItem.getListTitle().equals(newItem.getListTitle());
        }
    }

}
