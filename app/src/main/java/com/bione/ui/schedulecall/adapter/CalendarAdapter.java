package com.bione.ui.schedulecall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.ui.schedulecall.MyCalendar;

import java.text.ParseException;
import java.util.List;


public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.MyViewHolder> {

    private Context mContext;
    private List<MyCalendar> mCalendar;


    public CalendarAdapter(final Context mContext, List<MyCalendar> mCalendar) {
        this.mContext = mContext;
        this.mCalendar = mCalendar;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;
        public AppCompatTextView tvDate;
        public AppCompatTextView tvDay;
        public AppCompatTextView tvMonth;
        public View vLine;

        public MyViewHolder(View v) {
            super(v);
            view = v;
            tvDate = v.findViewById(R.id.tvDate);
            tvDay = v.findViewById(R.id.tvDay);
            tvMonth = v.findViewById(R.id.tvMonth);
            vLine = v.findViewById(R.id.vLine);

        }
    }

    @NonNull
    @Override
    public CalendarAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_date, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        try {
            holder.tvMonth.setText("" + mCalendar.get(position).getMonthName());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.tvDate.setText("" + mCalendar.get(position).getDate());
        holder.tvDay.setText("" + mCalendar.get(position).getDay());

    }


    @Override
    public int getItemCount() {
        return mCalendar.size();
    }


}
