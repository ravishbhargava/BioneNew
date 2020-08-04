package com.bione.ui.home.banner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;


public class CounsellorAdapter extends RecyclerView.Adapter<CounsellorAdapter.MyViewHolder> {

    private String type = "Upcoming";
    private Context mContext;


    public CounsellorAdapter(final String type) {
        this.type = type;
    }

    public CounsellorAdapter(final Context mContext, final String type) {
        this.type = type;
        this.mContext = mContext;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;


        public MyViewHolder(View v) {
            super(v);
            view = v;

        }
    }

    @NonNull
    @Override
    public CounsellorAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gallery, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
//        holder.tvType.setText(counsellorList.get(position).getGeneticType());
//        holder.tvName.setText(counsellorList.get(position).getCustomerName());

    }


    @Override
    public int getItemCount() {
     return 3;
    }





}
