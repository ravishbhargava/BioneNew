package com.bione.ui.dashboard.report.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.model.reportMyMicro.smartdiet.FoodRecommendation;

import java.util.ArrayList;
import java.util.List;

public class SmartDietHorizAdapter extends RecyclerView.Adapter<SmartDietHorizAdapter.SelectViewHolder> {

    private List<FoodRecommendation> foodRecommendationList;

    // Constructor
    public SmartDietHorizAdapter(ArrayList<FoodRecommendation> foodRecommendationList) {
        this.foodRecommendationList = foodRecommendationList;

    }

    @NonNull
    @Override
    public SelectViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        // Here we inflate the corresponding// layout of the child item
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_smart_diet, viewGroup, false);

        return new SelectViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull SelectViewHolder selectViewHolder, int position) {


        selectViewHolder.tvFoodItem.setText("" + foodRecommendationList.get(position).getMM2019231362FoodItems());
        selectViewHolder.tvFoodServing.setText("" + foodRecommendationList.get(position).getMM2019231362FoodServings());
        selectViewHolder.tvGR.setText("GR" + foodRecommendationList.get(position).getMm2019231362Gr());
        selectViewHolder.tvGM.setText("GM" + foodRecommendationList.get(position).getMm2019231362Gm());


    }

    @Override
    public int getItemCount() {
        return foodRecommendationList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    // This class is to initialize// the Views present// in the child RecyclerView
    class SelectViewHolder extends RecyclerView.ViewHolder {

        private ImageView left;
        private AppCompatTextView tvFoodServing;
        private AppCompatTextView tvFoodItem;
        private AppCompatTextView tvGM;
        private AppCompatTextView tvGR;


        SelectViewHolder(View itemView) {
            super(itemView);
            left = itemView.findViewById(R.id.left);
            tvFoodServing = itemView.findViewById(R.id.tvFoodServing);
            tvFoodItem = itemView.findViewById(R.id.tvFoodItem);
            tvGM = itemView.findViewById(R.id.tvGM);
            tvGR = itemView.findViewById(R.id.tvGR);

        }
    }

}