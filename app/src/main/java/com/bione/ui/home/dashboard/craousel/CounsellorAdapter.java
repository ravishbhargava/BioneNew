package com.bione.ui.home.dashboard.craousel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.model.CrouselData;

import java.util.ArrayList;


public class CounsellorAdapter extends RecyclerView.Adapter<CounsellorAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<CrouselData> crouselDataArrayList;


    public CounsellorAdapter(final Context mContext, final ArrayList<CrouselData> crouselDataArrayList) {
        this.crouselDataArrayList = crouselDataArrayList;
        this.mContext = mContext;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;
        public CardView llVisible;
        public ImageView image;
        public AppCompatTextView tvHeading;
        public AppCompatTextView tvText;

        public MyViewHolder(View v) {
            super(v);
            view = v;
            image = v.findViewById(R.id.image);
            tvHeading = v.findViewById(R.id.tvHeading);
            tvText = v.findViewById(R.id.tvText);
            llVisible = v.findViewById(R.id.llVisible);

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
        if (position == 0 || position == (crouselDataArrayList.size() - 1)) {
            holder.llVisible.setVisibility(View.INVISIBLE);
        } else {

            holder.llVisible.setVisibility(View.VISIBLE);
            holder.tvHeading.setText(crouselDataArrayList.get(position).getHeading());
            holder.tvText.setText(crouselDataArrayList.get(position).getText());
            holder.image.setImageResource(crouselDataArrayList.get(position).getDrawable());
        }

    }


    @Override
    public int getItemCount() {
        return crouselDataArrayList.size();
    }


}
