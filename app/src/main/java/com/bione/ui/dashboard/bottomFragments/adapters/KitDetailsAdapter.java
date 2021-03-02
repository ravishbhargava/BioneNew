package com.bione.ui.dashboard.bottomFragments.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.model.CrouselData;


import java.util.ArrayList;


public class KitDetailsAdapter extends RecyclerView.Adapter<KitDetailsAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<CrouselData> crouselDataArrayList;
    private String openType = "WebView"; //PdfView
    private int kitOrderSize = 0;

    public KitDetailsAdapter(final Context mContext, final ArrayList<CrouselData> crouselDataArrayList, final int kitOrderSize) {
        this.crouselDataArrayList = crouselDataArrayList;
        this.mContext = mContext;
        this.kitOrderSize = kitOrderSize;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;
        //        public View viewShaded;
        public RelativeLayout llVisible;
        public ImageView image;
        public AppCompatTextView tvHeading;
        public AppCompatTextView tvDetail;
//        public AppCompatTextView tvText;

        public MyViewHolder(View v) {
            super(v);
            view = v;
//            viewShaded = v.findViewById(R.id.viewShaded);
            image = v.findViewById(R.id.image);
            tvHeading = v.findViewById(R.id.tvHeading);
            tvDetail = v.findViewById(R.id.tvDetail);
//            tvText = v.findViewById(R.id.tvText);
            llVisible = v.findViewById(R.id.llVisible);

        }
    }

    @NonNull
    @Override
    public KitDetailsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gallery2, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        holder.llVisible.setVisibility(View.VISIBLE);
        holder.tvHeading.setText(crouselDataArrayList.get(position).getHeading());
        holder.tvDetail.setText(crouselDataArrayList.get(position).getText());
        holder.image.setImageResource(crouselDataArrayList.get(position).getDrawable());

        holder.llVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(crouselDataArrayList.get(position).getUrl()));
                mContext.startActivity(browserIntent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return crouselDataArrayList.size();
    }

//    private void openMyMicroBiome() {
//        Intent intent = new Intent(mContext, MyMicroBiome.class);
//        mContext.startActivity(intent);
//    }
//
//    private void openPDFView(final int position) {
//        crouselDataArrayList.get(position).setChecked(true);
//
//        Intent intent = new Intent(mContext, PDFViewActivity.class);
//        intent.putExtra("position", position);
//        intent.putExtra("openType", openType);
//        intent.putParcelableArrayListExtra("array", crouselDataArrayList);
////        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        mContext.startActivity(intent);
//    }

}
