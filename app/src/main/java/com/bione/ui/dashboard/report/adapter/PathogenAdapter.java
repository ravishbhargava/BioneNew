package com.bione.ui.dashboard.report.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.model.reportMyMicro.mygut.Pathogen;

import java.util.ArrayList;
import java.util.List;

import static com.bione.R.drawable.drawable_rectangle_high;
import static com.bione.R.drawable.drawable_rectangle_low;
import static com.bione.R.drawable.drawable_rectangle_nd;

public class PathogenAdapter extends RecyclerView.Adapter<PathogenAdapter.SelectViewHolder> {

    private List<Pathogen> pathogenList;
    private Context mContext;

    // Constructor
    public PathogenAdapter(Context mContext, ArrayList<Pathogen> selectedProductDataList) {
        this.pathogenList = selectedProductDataList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public SelectViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        // Here we inflate the corresponding// layout of the child item
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_gut_pathogen, viewGroup, false);

        return new SelectViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull SelectViewHolder selectViewHolder, int position) {

        if (pathogenList.get(position).getOutcome().equals("HIGH")) {
            selectViewHolder.tvRight.setTextColor(mContext.getResources().getColor(R.color.high_color));
            selectViewHolder.left.setBackgroundResource(drawable_rectangle_high);
        } else if (pathogenList.get(position).getOutcome().equals("LOW")) {
            selectViewHolder.tvRight.setTextColor(mContext.getResources().getColor(R.color.low_color));
            selectViewHolder.left.setBackgroundResource(drawable_rectangle_low);
        } else {
            selectViewHolder.tvRight.setTextColor(mContext.getResources().getColor(R.color.not_define_color));
            selectViewHolder.left.setBackgroundResource(drawable_rectangle_nd);
        }

        selectViewHolder.tvLeft.setText("" + pathogenList.get(position).getAbPercentage() + "%");
        selectViewHolder.tvCenter.setText("" + pathogenList.get(position).getPathogen());
        selectViewHolder.tvRight.setText("" + pathogenList.get(position).getOutcome());


    }

    @Override
    public int getItemCount() {
        return pathogenList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    // This class is to initialize// the Views present// in the child RecyclerView
    class SelectViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout left;
        private AppCompatTextView tvCenter;
        private AppCompatTextView tvLeft;
        private AppCompatTextView tvRight;


        SelectViewHolder(View itemView) {
            super(itemView);
            left = itemView.findViewById(R.id.left);
            tvCenter = itemView.findViewById(R.id.tvCenter);
            tvLeft = itemView.findViewById(R.id.tvLeft);
            tvRight = itemView.findViewById(R.id.tvRight);

        }
    }

}