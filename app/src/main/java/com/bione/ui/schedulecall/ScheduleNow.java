package com.bione.ui.schedulecall;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ScheduleNow extends BaseActivity {

    //    private Context mContext;
    private RecyclerView recyclerView;
    private CalendarAdapter mAdapter;
    private List<MyCalendar> calendarList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_call);


        recyclerView = findViewById(R.id.date_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        // horizontal RecyclerView
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)

        Calendar mCalendar = Calendar.getInstance();
        //  mCalendar.get(Calendar.MONTH);
        //  mCalendar.get(Calendar.D)



        recyclerView
                .addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView,
                                           int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        int totalItemCount = mLayoutManager.getChildCount();
                        for (int i = 0; i < totalItemCount; i++){
                            View childView = recyclerView.getChildAt(i);
                            TextView childTextView = (TextView) (childView.findViewById(R.id.tvDay));
                            String childTextViewText = (String) (childTextView.getText());

                            if (childTextViewText.equals("Sun"))
                                childTextView.setTextColor(Color.RED);
                            else
                                childTextView.setTextColor(Color.BLACK);

                        }


                    }
                });


        mAdapter = new CalendarAdapter(this, calendarList);
        recyclerView.setAdapter(mAdapter);
        prepareCalendarData();

        // row click listener
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                MyCalendar calendar = calendarList.get(position);
                TextView childTextView = (TextView) (view.findViewById(R.id.tvDay));

                Animation startRotateAnimation = AnimationUtils.makeInChildBottomAnimation(getApplicationContext());
                childTextView.startAnimation(startRotateAnimation);
                childTextView.setTextColor(Color.CYAN);
                Toast.makeText(ScheduleNow.this, "", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), calendar.getDay() + " is selected!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onLongClick(View view, int position) {
                MyCalendar calendar = calendarList.get(position);

                TextView childTextView = (TextView) (view.findViewById(R.id.tvDay));
                childTextView.setTextColor(Color.BLUE);

                Toast.makeText(getApplicationContext(), calendar.getDate() + "/" + calendar.getDay() + "/" + calendar.getMonth() + "   selected!", Toast.LENGTH_SHORT).show();

            }
        }));

    }

    /**
     * Prepares sample data to provide data set to adapter
     */
    private void prepareCalendarData() {

        // run a for loop for all the next 30 days of the current month starting today
        // initialize mycalendarData and get Instance
        // getnext to get next set of date etc.. which can be used to populate MyCalendarList in for loop

        myCalendarData m_calendar = new myCalendarData(0);

        for (int i = 0; i < 30; i++) {

            MyCalendar calendar = new MyCalendar(m_calendar.getWeekDay(), String.valueOf(m_calendar.getDay()), String.valueOf(m_calendar.getMonth()), String.valueOf(m_calendar.getYear()), i);
            m_calendar.getNextWeekDay(1);

            calendarList.add(calendar);

        }


        // notify adapter about data set changes
        // so that it will render the list with new data

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {

    }
}
