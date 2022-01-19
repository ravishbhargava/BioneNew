package com.bione.ui.dashboard.report.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;

import java.util.ArrayList;
import java.util.List;

public class GutRestorationAdapter extends RecyclerView.Adapter<GutRestorationAdapter.SelectViewHolder> {

    private List<String> microbeList;

    // Constructor
    public GutRestorationAdapter(ArrayList<String> microbeList) {
        this.microbeList = microbeList;

    }

    @NonNull
    @Override
    public SelectViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        // Here we inflate the corresponding// layout of the child item
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_gut_diet, viewGroup, false);

        return new SelectViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull SelectViewHolder selectViewHolder, int position) {


        selectViewHolder.tvDayTime.setText("" + microbeList.get(position));
//        selectViewHolder.tvCenter.setText("" + microbeList.get(position).getTaxa());
//        selectViewHolder.tvRight.setText("" + microbeList.get(position).getOutcome());
//        selectViewHolder.tvBottom.setText("" + microbeList.get(position).getStatus());


    }

    @Override
    public int getItemCount() {
        return microbeList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    // This class is to initialize// the Views present// in the child RecyclerView
    class SelectViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout left;
        private AppCompatTextView tvDayTime;
//        private AppCompatTextView tvLeft;
//        private AppCompatTextView tvRight;
//        private AppCompatTextView tvBottom;


        SelectViewHolder(View itemView) {
            super(itemView);
            left = itemView.findViewById(R.id.left);
            tvDayTime = itemView.findViewById(R.id.tvDayTime);
//            tvLeft = itemView.findViewById(R.id.tvLeft);
//            tvRight = itemView.findViewById(R.id.tvRight);
//            tvBottom = itemView.findViewById(R.id.tvBottom);

        }
    }

}