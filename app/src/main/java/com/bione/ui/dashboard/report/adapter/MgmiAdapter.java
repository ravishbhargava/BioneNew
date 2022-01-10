package com.bione.ui.dashboard.report.adapter;

import android.annotation.SuppressLint;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.model.reportMyMicro.mygut.Mgmi1;

import java.util.ArrayList;
import java.util.List;

import static com.bione.R.drawable.drawable_rectangle_nd;

public class MgmiAdapter extends RecyclerView.Adapter<MgmiAdapter.SelectViewHolder> {

    private List<Mgmi1> pathogenList;

    // Constructor
    public MgmiAdapter(ArrayList<Mgmi1> selectedProductDataList) {
        this.pathogenList = selectedProductDataList;

    }

    @NonNull
    @Override
    public SelectViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        // Here we inflate the corresponding// layout of the child item
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_gut_mgmi, viewGroup, false);

        return new SelectViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull SelectViewHolder selectViewHolder, int position) {


        selectViewHolder.tvRight.setTextColor(R.color.colorPrimary);
        selectViewHolder.left.setBackgroundResource(drawable_rectangle_nd);


        selectViewHolder.tvLeft.setText("" + pathogenList.get(position).getValue());
        selectViewHolder.tvCenter.setText("" + Html.fromHtml(pathogenList.get(position).getName()));
        selectViewHolder.tvRight.setText("" + Html.fromHtml(pathogenList.get(position).getReport()));


    }

    @Override
    public int getItemCount() {
        return pathogenList.size() - 2;
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