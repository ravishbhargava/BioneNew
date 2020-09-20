package com.bione.ui.home.dashboard.craousel;

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
import com.bione.ui.mymicrobiome.MyMicroBiome;

import java.util.ArrayList;


public class CounsellorAdapter extends RecyclerView.Adapter<CounsellorAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<CrouselData> crouselDataArrayList;
    private String openType = "WebView"; //PdfView

    public CounsellorAdapter(final Context mContext, final ArrayList<CrouselData> crouselDataArrayList) {
        this.crouselDataArrayList = crouselDataArrayList;
        this.mContext = mContext;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;
        public View viewShaded;
        public CardView llVisible;
        public ImageView image;
        public AppCompatTextView tvHeading;
        public AppCompatTextView tvDetail;
        public AppCompatTextView tvText;

        public MyViewHolder(View v) {
            super(v);
            view = v;
            viewShaded = v.findViewById(R.id.viewShaded);
            image = v.findViewById(R.id.image);
            tvHeading = v.findViewById(R.id.tvHeading);
            tvDetail = v.findViewById(R.id.tvDetail);
            tvText = v.findViewById(R.id.tvText);
            llVisible = v.findViewById(R.id.llVisible);

        }
    }

    @NonNull
    @Override
    public CounsellorAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gallery2, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        if (position == 0 || position == (crouselDataArrayList.size() - 1)) {
            holder.llVisible.setVisibility(View.INVISIBLE);
        } else {
            holder.viewShaded.setVisibility(View.GONE);
            holder.llVisible.setVisibility(View.VISIBLE);
            holder.tvHeading.setText(crouselDataArrayList.get(position).getHeading());
            holder.tvText.setText(crouselDataArrayList.get(position).getText());
            holder.image.setImageResource(crouselDataArrayList.get(position).getDrawable());
        }
        //            if (position == crouselDataArrayList.size() - 2) {
//            } else {
//                holder.viewShaded.setVisibility(View.GONE);
//            }
//            holder.tvDetail.setEnabled(false);

        holder.llVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.bione.in"));
//                mContext.startActivity(browserIntent);
                if (position == 2) {
                    Intent intent = new Intent(mContext, MyMicroBiome.class);
                    mContext.startActivity(intent);
                } else {

                    if (position == 4) {
                        openType = "WebView";
                    } else {
                        openType = "WebView";
                    }
                    Intent intent = new Intent(mContext, PDFViewActivity.class);
                    intent.putExtra("position", position);
                    intent.putExtra("openType", openType);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return crouselDataArrayList.size();
    }


}
