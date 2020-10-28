package com.bione.ui.schedulecall;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.db.CommonData;
import com.bione.model.Slots;
import com.bione.model.availableSlots.ListItem;
import com.bione.model.availableSlots.Slot;
import com.bione.network.ApiError;
import com.bione.network.CommonParams;
import com.bione.network.ResponseResolver;
import com.bione.network.RestClient;
import com.bione.ui.base.BaseActivity;
import com.bione.ui.schedulecall.adapter.CalendarAdapter;
import com.bione.ui.schedulecall.adapter.SlotsAdapter;
import com.bione.utils.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.bione.utils.AppConstant.PARAM_DATE;
import static com.bione.utils.AppConstant.SUCCESS;

public class ScheduleNow extends BaseActivity {

    private AppCompatImageView ivBack;

    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView recyclerViewHorizontal;
    private CalendarAdapter calendarAdapter;
    private List<MyCalendar> calendarList = new ArrayList<>();


    private AppCompatTextView tvSelectedSlot;
    private AppCompatTextView tvScheduleNow;

    private RecyclerView recyclerView;
    private SlotsAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ListItem> availableSlots;
    private ArrayList<Slots> arrayTimeSlots = new ArrayList<>();

    private String counsellorName = "counsellorName";
    private String geneticType = "";
    private String selectedText = "";
    private String selectedDateToPass = "";

    private String dayToPass;
    private String dateToPass;
    private String monthToPass;
    private String yearToPass;

    private Context mContext;
    private String bookingId = "";

    private String currentDateSlot = "";
    private String currentTimeSlot = "";

    private String category = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_call);
        mContext = getApplicationContext();
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
//            newString= null;
        } else {
            geneticType = extras.getString("geneticType");
            counsellorName = extras.getString("counsellorName");
            bookingId = extras.getString("bookingId");
            category = extras.getString("category");

        }
        Log.d("geneticType", "---" + geneticType);
        Log.d("counsellorName", "---" + counsellorName);
        Log.d("customrer id", "------" + CommonData.getUserData().getEntityId());


        ivBack = findViewById(R.id.ivBack);
        tvSelectedSlot = findViewById(R.id.tvSelectedSlot);
        tvScheduleNow = findViewById(R.id.tvScheduleNow);
        tvScheduleNow.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        availableSlots = new ArrayList<>();

        if (bookingId != null) {
            tvScheduleNow.setText("Re-schedule");

        } else {
            tvScheduleNow.setText("Schedule Now");
        }

        // horizontal calendar code
        initHorizontalRecyclerView();
        prepareCalendarData();
        setScroll(mLayoutManager);
        setSelectedData(0);
        recyclerClick();

        //Slots list
        initRecycler();
        getCurrentTimeSlot();
        availableSlotsAPI();


    }

    private void initHorizontalRecyclerView() {
        recyclerViewHorizontal = findViewById(R.id.date_recycler_view);
        recyclerViewHorizontal.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewHorizontal.setLayoutManager(mLayoutManager);
        calendarAdapter = new CalendarAdapter(this, calendarList);
        recyclerViewHorizontal.setAdapter(calendarAdapter);
    }

    private void recyclerClick() {
        // row click listener
        recyclerViewHorizontal.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerViewHorizontal, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                setSelectedData(position);
                setCalendarUI(recyclerViewHorizontal, mLayoutManager);

                availableSlotsAPI();
            }

            @Override
            public void onLongClick(View view, int position) {

                setSelectedData(position);
                setCalendarUI(recyclerViewHorizontal, mLayoutManager);

                availableSlotsAPI();
            }
        }));

    }

    private void setSelectedData(int position) {
        MyCalendar calendar = calendarList.get(position);
        selectedDateToPass = calendar.getYear() + "-" + calendar.getMonthNumber() + "-" + calendar.getDate();
        dayToPass = calendar.getDay();
        dateToPass = calendar.getDate();
        yearToPass = calendar.getYear();
        monthToPass = calendar.getMonth();
        try {
            selectedText = calendar.getDate() + "-" + calendar.getMonthName();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void setScroll(RecyclerView.LayoutManager mLayoutManager) {
        recyclerViewHorizontal.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView,
                                   int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                setCalendarUI(recyclerView, mLayoutManager);
            }
        });
    }

    private void setCalendarUI(final RecyclerView recyclerView, final RecyclerView.LayoutManager mLayoutManager) {

        int totalItemCount = mLayoutManager.getChildCount();

        for (int i = 0; i < totalItemCount; i++) {
            View childView = recyclerView.getChildAt(i);
            TextView tvDay = (childView.findViewById(R.id.tvDay));
            TextView tvDate = (childView.findViewById(R.id.tvDate));
            TextView tvMonth = (childView.findViewById(R.id.tvMonth));
            View vLine = (childView.findViewById(R.id.vLine));

            String childTextViewText = (String) (tvDate.getText()) + "-" + (String) (tvMonth.getText());

            if (childTextViewText.equals(selectedText)) {
                tvDay.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvDate.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvMonth.setTextColor(getResources().getColor(R.color.colorPrimary));
//                vLine.setVisibility(View.VISIBLE);
            } else {
                tvDay.setTextColor(getResources().getColor(R.color.black));
                tvDate.setTextColor(getResources().getColor(R.color.black));
                tvMonth.setTextColor(getResources().getColor(R.color.black));
//                vLine.setVisibility(View.GONE);
            }
        }
    }

    private void initRecycler() {
        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        createList();

        // specify an adapter (see also next example)
        mAdapter = new SlotsAdapter(mContext, arrayTimeSlots, new OnItemClickListener() {
            @Override
            public void onItemClick(String text) {
                tvSelectedSlot.setText(text);
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    private void createList() {
        arrayTimeSlots = new ArrayList<>();

        Slots slots1 = new Slots("09:00AM-09:30AM", false, "AVAILABLE");
        Slots slots2 = new Slots("09:30AM-10:00AM", false, "AVAILABLE");
        Slots slots3 = new Slots("10:00AM-10:30AM", false, "AVAILABLE");
        Slots slots4 = new Slots("10:30AM-11:00AM", false, "AVAILABLE");
        Slots slots5 = new Slots("11:00AM-11:30AM", false, "AVAILABLE");
        Slots slots6 = new Slots("11:30AM-12:00PM", false, "AVAILABLE");
        Slots slots7 = new Slots("12:00PM-12:30PM", false, "AVAILABLE");
        Slots slots8 = new Slots("12:30PM-01:00PM", false, "AVAILABLE");
        Slots slots9 = new Slots("01:00PM-01:30PM", false, "AVAILABLE");
        Slots slots10 = new Slots("01:30PM-02:00PM", false, "AVAILABLE");
        Slots slots11 = new Slots("02:00PM-02:30PM", false, "AVAILABLE");
        Slots slots12 = new Slots("02:30PM-03:00PM", false, "AVAILABLE");
        Slots slots13 = new Slots("03:00PM-03:30PM", false, "AVAILABLE");
        Slots slots14 = new Slots("03:30PM-04:00PM", false, "AVAILABLE");
        Slots slots15 = new Slots("04:00PM-04:30PM", false, "AVAILABLE");
        Slots slots16 = new Slots("04:30PM-05:00PM", false, "AVAILABLE");
        Slots slots17 = new Slots("05:00PM-05:30PM", false, "AVAILABLE");
        Slots slots18 = new Slots("05:30PM-06:00PM", false, "AVAILABLE");

        arrayTimeSlots.add(slots1);
        arrayTimeSlots.add(slots2);
        arrayTimeSlots.add(slots3);
        arrayTimeSlots.add(slots4);
        arrayTimeSlots.add(slots5);
        arrayTimeSlots.add(slots6);
        arrayTimeSlots.add(slots7);
        arrayTimeSlots.add(slots8);
        arrayTimeSlots.add(slots9);
        arrayTimeSlots.add(slots10);
        arrayTimeSlots.add(slots11);
        arrayTimeSlots.add(slots12);
        arrayTimeSlots.add(slots13);
        arrayTimeSlots.add(slots14);
        arrayTimeSlots.add(slots15);
        arrayTimeSlots.add(slots16);
        arrayTimeSlots.add(slots17);
        arrayTimeSlots.add(slots18);
    }

    private void prepareCalendarData() {

        // run a for loop for all the next 30 days of the current month starting today
        // initialize mycalendarData and get Instance
        // getnext to get next set of date etc.. which can be used to populate MyCalendarList in for loop

        MyCalendarData m_calendar = new MyCalendarData(0);
        for (int i = 0; i < 30; i++) {
            MyCalendar calendar = new MyCalendar(m_calendar.getWeekDay(),
                    String.valueOf(m_calendar.getDay()),
                    String.valueOf(m_calendar.getMonth()),
                    String.valueOf(m_calendar.getYear()),
                    i);
            m_calendar.getNextWeekDay(1);
            calendarList.add(calendar);
        }
        calendarAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.tvScheduleNow:

                if (mAdapter.getCheckedPosition() != -1) {
                    Intent intent = new Intent(ScheduleNow.this, CounsellingConfirm.class);

                    intent.putExtra("category", category);
                    intent.putExtra("bookingId", bookingId);
                    intent.putExtra("dayToPass", dayToPass);
                    intent.putExtra("dateToPass", dateToPass);
                    intent.putExtra("monthToPass", monthToPass);
                    intent.putExtra("yearToPass", yearToPass);
                    intent.putExtra("geneticType", geneticType);
                    intent.putExtra("counsellorName", counsellorName);
                    intent.putExtra("selectedDateToPass", selectedDateToPass);
                    intent.putExtra("timeToPass", tvSelectedSlot.getText().toString());
                    intent.putExtra("selectedTimeSlot", arrayTimeSlots.get(mAdapter.getCheckedPosition()).name);

                    startActivityForResult(intent, 101);
                } else {
                    showErrorMessage("Please select time slot");
                }
                break;

            case R.id.ivBack:
                finish();
                break;

            default:
                break;
        }
    }


    private void availableSlotsAPI() {
        Log.d("selectedDateToPass", "------" + selectedDateToPass);
        showLoading();
        final CommonParams commonParams = new CommonParams.Builder()
                .add(PARAM_DATE, selectedDateToPass)
                .build();

        RestClient.getApiInterface().availabilitySlots(commonParams.getMap()).enqueue(new ResponseResolver<List<Slot>>() {
            @Override
            public void onSuccess(List<Slot> slots) {

                if (slots.get(0).getCode() == SUCCESS) {
                    try {
                        availableSlots = new ArrayList<>();
                        Log.d(" time slots arrayTimeSlots", " size :: " + slots.get(0).getListItems().size());

                        availableSlots = (ArrayList<ListItem>) slots.get(0).getListItems();
                        createList();
                        for (int i = 0; i < availableSlots.size(); i++) {
                            for (int j = 0; j < arrayTimeSlots.size(); j++) {
                                if (availableSlots.get(i).getTimeSlot().equals(arrayTimeSlots.get(j).name)) {
                                    arrayTimeSlots.get(j).setSelected(true);
                                    arrayTimeSlots.get(j).setText("NOT AVAILABLE");
                                }
                            }
                        }
                        if (currentDateSlot.equals(selectedDateToPass)) {
                            Log.d(" current", "selectedDateToPass------" + selectedDateToPass);
                            Log.d(" current", "currentDateSlot------" + currentDateSlot);
                            for (int i = 0; i < arrayTimeSlots.size(); i++) {
                                if (arrayTimeSlots.get(i).getName().equals(currentTimeSlot)) {
                                    break;
                                }
                                arrayTimeSlots.get(i).setSelected(true);
                                arrayTimeSlots.get(i).setText("NOT AVAILABLE");
                                Log.d("name", "------" + arrayTimeSlots.get(i).getName());

                            }
                        } else {
                            Log.d("not current", "selectedDateToPass------" + selectedDateToPass);
                            Log.d("not current", "currentDateSlot------" + currentDateSlot);
                        }

                        mAdapter.refreshEvents(arrayTimeSlots);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    availableSlots = new ArrayList<>();
                    createList();
                    mAdapter.refreshEvents(arrayTimeSlots);
                }
            }

            @Override
            public void onError(ApiError error) {
                Log.d("onError", "" + error);
                showErrorMessage(error.getMessage());
            }

            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
                showErrorMessage(throwable.getMessage());
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(final String text);
    }

    private void getCurrentTimeSlot() {
//        String timeValue = "2015-10-28T10:37:04.899+05:30";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        try {
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(sdf.parse(sdf.format(startCalendar.getTime())));
//            startCalendar.setTime(sdf.parse(timeValue));

            if (startCalendar.get(Calendar.MINUTE) < 30) {
                startCalendar.set(Calendar.MINUTE, 30);
            } else {
                startCalendar.add(Calendar.MINUTE, 30); // overstep hour and clear minutes
                startCalendar.clear(Calendar.MINUTE);
            }

            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(startCalendar.getTime());

            // if you want dates for whole next day, uncomment next line
            //endCalendar.add(Calendar.DAY_OF_YEAR, 1);
            endCalendar.add(Calendar.HOUR_OF_DAY, 24 - startCalendar.get(Calendar.HOUR_OF_DAY));

            endCalendar.clear(Calendar.MINUTE);
            endCalendar.clear(Calendar.SECOND);
            endCalendar.clear(Calendar.MILLISECOND);

            SimpleDateFormat slotTime = new SimpleDateFormat("hh:mma");
            SimpleDateFormat slotDate = new SimpleDateFormat("yyyy-MM-dd");
//            while (endCalendar.after(startCalendar)) {
            String slotStartTime = slotTime.format(startCalendar.getTime());
            String slotStartDate = slotDate.format(startCalendar.getTime());

            startCalendar.add(Calendar.MINUTE, 30);
            String slotEndTime = slotTime.format(startCalendar.getTime());

            Log.d("DATE", slotStartTime + " - " + slotEndTime + slotStartDate);
            currentTimeSlot = slotStartTime + "-" + slotEndTime;
            currentDateSlot = slotStartDate;
//            }

        } catch (ParseException e) {
            // date in wrong format
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            Log.d("" + getClass().getSimpleName(), "se aaya");
            if (data != null) {
                if (data.getStringExtra("status").equals("Done")) {
                    Intent intent = new Intent();
                    intent.putExtra("status", "Done");
                    setResult(101, intent);
                    finish();
                }
            }
        }
    }
}
