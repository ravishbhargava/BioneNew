package com.bione.ui.schedulecall.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.bione.ui.home.dashboard.craousel.PDFViewActivity;
import com.bione.ui.mymicrobiome.MyMicroBiome;
import com.bione.ui.schedulecall.CategorySelect;

import java.util.ArrayList;


public class CrousellBookCallAdapter extends RecyclerView.Adapter<CrousellBookCallAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<CrouselData> crouselDataArrayList;
    private String openType = "WebView"; //PdfView
    private int checkedPosition = -1;
    private CategorySelect.OnItemClickListenerSelected listener;
//    private int kitOrderSize = 0;

    public CrousellBookCallAdapter(final Context mContext, final ArrayList<CrouselData> crouselDataArrayList,
                                   int checkedPosition, CategorySelect.OnItemClickListenerSelected listener) {
        this.crouselDataArrayList = crouselDataArrayList;
        this.mContext = mContext;
        this.checkedPosition = checkedPosition;
        this.listener = listener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;
        //        public View viewShaded;
        public CardView llVisible;
        public ImageView ivGeneticInner;
        public ImageView ivGenetic;
        public AppCompatTextView tvHeading;
//        public AppCompatTextView tvDetail;
//        public AppCompatTextView tvText;

        public MyViewHolder(View v) {
            super(v);
            view = v;
//            viewShaded = v.findViewById(R.id.viewShaded);
            ivGeneticInner = v.findViewById(R.id.ivGeneticInner);
            ivGenetic = v.findViewById(R.id.ivGenetic);
            tvHeading = v.findViewById(R.id.tvHeading);
//            tvDetail = v.findViewById(R.id.tvDetail);
//            tvText = v.findViewById(R.id.tvText);
            llVisible = v.findViewById(R.id.llVisible);

        }
    }

    @NonNull
    @Override
    public CrousellBookCallAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_crousel_category, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        if (position == 0 || position == (crouselDataArrayList.size() - 1)) {
            holder.llVisible.setVisibility(View.GONE);
        } else {

            holder.llVisible.setVisibility(View.VISIBLE);
            holder.tvHeading.setText(crouselDataArrayList.get(position).getHeadingTest());
            holder.ivGeneticInner.setImageResource(crouselDataArrayList.get(position).getDrawableTest());

        }

//        if (crouselDataArrayList.get(position).isIschecked()) {
        if (checkedPosition == position) {
            holder.ivGenetic.setImageResource(R.drawable.ic_selected_circle);
        } else {
            holder.ivGenetic.setImageResource(R.drawable.ic_unselected_circle);
        }

        holder.llVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.ivGenetic.setImageResource(R.drawable.ic_selected_circle);

                if (checkedPosition != position) {
                    notifyItemChanged(checkedPosition);
                    listener.onItemClickSelected(position);
                    checkedPosition = position;
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return crouselDataArrayList.size();
    }

    public int getCheckedPosition() {
        return checkedPosition;
    }

    private void openMyMicroBiome() {
        Intent intent = new Intent(mContext, MyMicroBiome.class);
        mContext.startActivity(intent);
    }

    private void openPDFView(final int position) {
        Intent intent = new Intent(mContext, PDFViewActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("openType", openType);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }


}
