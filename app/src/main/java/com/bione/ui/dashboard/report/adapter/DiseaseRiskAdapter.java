package com.bione.ui.dashboard.report.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.model.reportMyMicro.mygut.DiseasePrediction;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class DiseaseRiskAdapter extends RecyclerView.Adapter<DiseaseRiskAdapter.SelectViewHolder> {

    private List<DiseasePrediction> diseasePredictionList;
    private Context mContext;

    // Constructor
    public DiseaseRiskAdapter(Context mContext, ArrayList<DiseasePrediction> selectedProductDataList) {
        this.diseasePredictionList = selectedProductDataList;
        this.mContext = mContext;

    }

    @NonNull
    @Override
    public SelectViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        // Here we inflate the corresponding// layout of the child item
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_disease_horiz, viewGroup, false);

        return new SelectViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull SelectViewHolder selectViewHolder, int position) {

//        if (diseasePredictionList.get(position).getRisk().equals("low")) {
//            selectViewHolder.tvRight.setBackgroundResource(R.color.button_color);
////    selectViewHolder.left.setBackgroundResource(drawable_rectangle_nd);
//        } else {
//            selectViewHolder.tvRight.setBackgroundResource(R.color.low_color);
//        }

        selectViewHolder.tvHeading.setText("" + Html.fromHtml(diseasePredictionList.get(position).getDisease()));
//        selectViewHolder.tvRight.setText("" + Html.fromHtml(diseasePredictionList.get(position).getRisk()));
        Glide.with(mContext)
                .load(diseasePredictionList.get(position).getImage())
                .apply(new RequestOptions().override(196, 196))
                .centerCrop()
                .into(selectViewHolder.image);

    }

    @Override
    public int getItemCount() {
        return diseasePredictionList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    // This class is to initialize// the Views present// in the child RecyclerView
    class SelectViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private AppCompatTextView tvHeading;
//        private AppCompatTextView tvRight;


        SelectViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            tvHeading = itemView.findViewById(R.id.tvHeading);
//            tvRight = itemView.findViewById(R.id.tvRight);

        }
    }

}