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
import com.bione.model.reportMyMicro.mygut.Microbe;

import java.util.ArrayList;
import java.util.List;

import static com.bione.R.drawable.drawable_rectangle_high;
import static com.bione.R.drawable.drawable_rectangle_low;
import static com.bione.R.drawable.drawable_rectangle_nd;

public class MicrobesAdapter extends RecyclerView.Adapter<MicrobesAdapter.SelectViewHolder> {

    private List<Microbe> microbeList;

    // Constructor
    public MicrobesAdapter(ArrayList<Microbe> microbeList) {
        this.microbeList = microbeList;

    }

    @NonNull
    @Override
    public SelectViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        // Here we inflate the corresponding// layout of the child item
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_gut_microbes, viewGroup, false);

        return new SelectViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull SelectViewHolder selectViewHolder, int position) {

        if (microbeList.get(position).getStatus().equals("High")) {
            selectViewHolder.tvRight.setTextColor(R.color.high_color);
            selectViewHolder.left.setBackgroundResource(drawable_rectangle_high);
        } else if (microbeList.get(position).getStatus().equals("Low")) {
            selectViewHolder.tvRight.setTextColor(R.color.low_color);
            selectViewHolder.left.setBackgroundResource(drawable_rectangle_low);
        } else {
            selectViewHolder.tvRight.setTextColor(R.color.colorPrimary);
            selectViewHolder.left.setBackgroundResource(drawable_rectangle_nd);
        }

        selectViewHolder.tvLeft.setText("" + microbeList.get(position).getAbPercentage() + "%");
        selectViewHolder.tvCenter.setText("" + microbeList.get(position).getTaxa());
        selectViewHolder.tvRight.setText("" + microbeList.get(position).getOutcome());
        selectViewHolder.tvBottom.setText("" + microbeList.get(position).getStatus());


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
        private AppCompatTextView tvCenter;
        private AppCompatTextView tvLeft;
        private AppCompatTextView tvRight;
        private AppCompatTextView tvBottom;


        SelectViewHolder(View itemView) {
            super(itemView);
            left = itemView.findViewById(R.id.left);
            tvCenter = itemView.findViewById(R.id.tvCenter);
            tvLeft = itemView.findViewById(R.id.tvLeft);
            tvRight = itemView.findViewById(R.id.tvRight);
            tvBottom = itemView.findViewById(R.id.tvBottom);

        }
    }

}