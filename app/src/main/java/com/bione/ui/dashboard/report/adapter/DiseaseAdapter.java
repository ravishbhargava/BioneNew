package com.bione.ui.dashboard.report.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.model.reportMyMicro.mygut.DiseasePrediction;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DiseaseAdapter extends RecyclerView.Adapter<DiseaseAdapter.SelectViewHolder> {

    private List<DiseasePrediction> diseasePredictionList;
    private Context mContext;

    // Constructor
    public DiseaseAdapter(Context mContext, ArrayList<DiseasePrediction> selectedProductDataList) {
        this.diseasePredictionList = selectedProductDataList;
        this.mContext = mContext;

    }

    @NonNull
    @Override
    public SelectViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        // Here we inflate the corresponding// layout of the child item
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_disease_risk, viewGroup, false);

        return new SelectViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull SelectViewHolder selectViewHolder, int position) {

        if (diseasePredictionList.get(position).getRisk().equals("low")) {
            selectViewHolder.tvRight.setBackgroundResource(R.color.button_color);
//    selectViewHolder.left.setBackgroundResource(drawable_rectangle_nd);
        } else {
            selectViewHolder.tvRight.setBackgroundResource(R.color.low_color);
        }

        selectViewHolder.tvCenter.setText("" + Html.fromHtml(diseasePredictionList.get(position).getDisease()));
        selectViewHolder.tvRight.setText("" + Html.fromHtml(diseasePredictionList.get(position).getRisk()));
        Glide.with(mContext)
                .load(diseasePredictionList.get(position).getImage())
                .circleCrop()
                .into(selectViewHolder.left);

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

        private CircleImageView left;
        private AppCompatTextView tvCenter;
        private AppCompatTextView tvRight;


        SelectViewHolder(View itemView) {
            super(itemView);
            left = itemView.findViewById(R.id.left);
            tvCenter = itemView.findViewById(R.id.tvCenter);
            tvRight = itemView.findViewById(R.id.tvRight);

        }
    }

}