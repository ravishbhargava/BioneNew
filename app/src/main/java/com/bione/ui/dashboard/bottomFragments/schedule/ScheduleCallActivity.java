package com.bione.ui.dashboard.bottomFragments.schedule;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.model.Slots;
import com.bione.model.availableSlots.ListItem;
import com.bione.model.availableSlots.Slot;
import com.bione.network.ApiError;
import com.bione.network.CommonParams;
import com.bione.network.ResponseResolver;
import com.bione.network.RestClient;
import com.bione.ui.base.BaseActivity;
import com.bione.ui.dashboard.bottomFragments.schedule.adapter.CalendarAdapter;
import com.bione.ui.dashboard.bottomFragments.schedule.adapter.SlotsAdapter;
import com.bione.utils.CustomTypefaceSpan;
import com.bione.utils.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.bione.utils.AppConstant.PARAM_DATE;
import static com.bione.utils.AppConstant.SUCCESS;

public class ScheduleCallActivity extends BaseActivity {

    private RelativeLayout relReport;
    private RelativeLayout relSmartDiet;
    private RelativeLayout relFood;

    private LinearLayout llReport;
    private LinearLayout llSmartDiet;
    private LinearLayout llFood;

    private TextView tvReport;
    private TextView tvSmartDiet;
    private TextView tvFood;

    private AppCompatImageView ivBack;

    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView recyclerViewHorizontal;
    private CalendarAdapter calendarAdapter;
    private List<MyCalendar> calendarList = new ArrayList<>();


    private RecyclerView recyclerView;
    private SlotsAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ListItem> availableSlots;
    private ArrayList<Slots> arrayTimeSlots = new ArrayList<>();


    private String currentDateSlot = "";
    private String currentTimeSlot = "";
    private String actualTime = "";
    private String selectedDateToPass = "";
    private String selectedText = "";

    private String dayToPass;
    private String dateToPass;
    private String monthToPass;
    private String yearToPass;

    private String counsellorName = "counsellorName";
    private String geneticType = "My Report";
    private String bookingId = "bookingId";
    private String category = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_call_new);


        Bundle extras = getIntent().getExtras();
        if (extras == null) {
//            newString= null;
        } else {
            geneticType = extras.getString("geneticType");
            counsellorName = extras.getString("counsellorName");
            bookingId = extras.getString("bookingId");
            category = extras.getString("category");

        }


        init();
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

    private void init() {
        tvFood = findViewById(R.id.tvFood);
        tvReport = findViewById(R.id.tvReport);
        tvSmartDiet = findViewById(R.id.tvSmartDiet);

        llFood = findViewById(R.id.llFood);
        llReport = findViewById(R.id.llReport);
        llSmartDiet = findViewById(R.id.llSmartDiet);

        relFood = findViewById(R.id.relFood);
        relReport = findViewById(R.id.relReport);
        relSmartDiet = findViewById(R.id.relSmartDiet);

        ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);

        llFood.setOnClickListener(this);
        llReport.setOnClickListener(this);
        llSmartDiet.setOnClickListener(this);

        selectType(relSmartDiet, relReport, relFood);
        selectTypeText(tvSmartDiet, tvReport, tvFood);
    }

    private void initHorizontalRecyclerView() {
        recyclerViewHorizontal = findViewById(R.id.date_recycler_view);
        recyclerViewHorizontal.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewHorizontal.setLayoutManager(mLayoutManager);
        calendarAdapter = new CalendarAdapter(this, calendarList);
        recyclerViewHorizontal.setAdapter(calendarAdapter);
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

    private void recyclerClick() {
        // row click listener
        recyclerViewHorizontal.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),
                recyclerViewHorizontal, new RecyclerTouchListener.ClickListener() {
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

    private void getCurrentTimeSlot() {
        SimpleDateFormat slotTime = new SimpleDateFormat("hh:mma");
        SimpleDateFormat slotDate = new SimpleDateFormat("yyyy-MM-d");
//        String timeValue = "2020-10-30T01:42:04.899+05:30";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        try {
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(sdf.parse(sdf.format(startCalendar.getTime())));
//            startCalendar.setTime(sdf.parse(timeValue));

            String slotStartDate = slotDate.format(startCalendar.getTime());
            actualTime = slotTime.format(startCalendar.getTime());


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


//            while (endCalendar.after(startCalendar)) {
            String slotStartTime = slotTime.format(startCalendar.getTime());
//            String slotStartDate = slotDate.format(startCalendar.getTime());

            startCalendar.add(Calendar.MINUTE, 30);
            String slotEndTime = slotTime.format(startCalendar.getTime());

            Log.d("DATETime-------->  Time: ", slotStartTime + " - " + slotEndTime + " date: " + slotStartDate);
//            actualTime = slotStartTime;
            currentTimeSlot = slotStartTime + "-" + slotEndTime;
            currentDateSlot = slotStartDate;


//            }

        } catch (ParseException e) {
            // date in wrong format
        }
    }

    private void checkDateEqual() {

        if (currentDateSlot.equals(selectedDateToPass)) {
            Log.d(" current", "selectedDateToPass------" + selectedDateToPass);
            Log.d(" current", "currentDateSlot------" + currentDateSlot);


            if (checkSLotTime("09:00AM", actualTime)) {
                Log.d("checkslotime", "if");

                for (int i = 0; i < arrayTimeSlots.size(); i++) {
                    if (arrayTimeSlots.get(i).getName().equals(currentTimeSlot)) {
                        break;
                    }
                    arrayTimeSlots.get(i).setSelected(true);
                    arrayTimeSlots.get(i).setText("NOT AVAILABLE");
                    Log.d("name", "------" + arrayTimeSlots.get(i).getName());
                }
            } else {
                Log.d("checkslotime", "else");
            }
        } else {
            Log.d("not current", "selectedDateToPass------" + selectedDateToPass);
            Log.d("not current", "currentDateSlot------" + currentDateSlot);
        }

        mAdapter.refreshEvents(arrayTimeSlots);
    }


    private boolean checkSLotTime(String startTimeSlot, String currentTimeSLot) {
//        startTimeSlot = "09:00AM";
//        currentTimeSLot = "08:00AM";
        Log.d("startTimeSlot: " + startTimeSlot, "currentTimeSLot: " + currentTimeSLot);
        try {
            SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
            SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mma");
            Date date1 = parseFormat.parse(startTimeSlot);
            System.out.println(parseFormat.format(date1) + " = " + displayFormat.format(date1));

            Date date2 = parseFormat.parse(currentTimeSlot);
            System.out.println(parseFormat.format(date2) + " = " + displayFormat.format(date2));

            if (parseFormat.format(date2).equals("12:00AM")) {
                System.out.println("12:00AM night time handled");
                return true;
            } else if (date1.compareTo(date2) > 0) {
                System.out.println("Date 1 occurs after Date 2");
                return false;
            } else if (date1.compareTo(date2) < 0) {
                System.out.println("Date 1 occurs before Date 2");
                return true;
            } else if (date1.compareTo(date2) == 0) {
                System.out.println("Both dates are equal");
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private void initRecycler() {
        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        createList();

        // specify an adapter (see also next example)
        mAdapter = new SlotsAdapter(this, arrayTimeSlots, new OnItemClickListener() {
            @Override
            public void onItemClick(String text) {
//                tvSelectedSlot.setText(text);
                Intent intent = new Intent(ScheduleCallActivity.this, CounsellingConfirm.class);
                intent.putExtra("category", category);
                intent.putExtra("bookingId", bookingId);
                intent.putExtra("dayToPass", dayToPass);
                intent.putExtra("dateToPass", dateToPass);
                intent.putExtra("monthToPass", monthToPass);
                intent.putExtra("yearToPass", yearToPass);
                intent.putExtra("geneticType", geneticType);
                intent.putExtra("counsellorName", counsellorName);
                intent.putExtra("selectedDateToPass", selectedDateToPass);
                intent.putExtra("timeToPass", text);
                intent.putExtra("selectedTimeSlot", text);

                startActivityForResult(intent, 101);
//                startActivity(intent);

            }
        });
        recyclerView.setAdapter(mAdapter);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.llReport:
                selectType(relReport, relSmartDiet, relFood);
                selectTypeText(tvReport, tvSmartDiet, tvFood);
                break;


            case R.id.llSmartDiet:
                selectType(relSmartDiet, relReport, relFood);
                selectTypeText(tvSmartDiet, tvReport, tvFood);
                break;


            case R.id.llFood:
                selectType(relFood, relReport, relSmartDiet);
                selectTypeText(tvFood, tvReport, tvSmartDiet);
                break;

            case R.id.ivBack:
                finish();

        }

    }

    private void selectType(RelativeLayout relSelected, RelativeLayout relUnSelected1, RelativeLayout relUnSelected2) {
        relSelected.setBackground(getResources().getDrawable(R.drawable.background_oval_primary));
        relUnSelected1.setBackground(getResources().getDrawable(R.drawable.background_oval_white));
        relUnSelected2.setBackground(getResources().getDrawable(R.drawable.background_oval_white));
    }

    private void selectTypeText(TextView tvSelected, TextView tvUnSelected1, TextView tvUnSelected2) {
        tvSelected.setTextColor(getResources().getColor(R.color.pure_black));

        Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Regular.ttf");
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Bold.ttf");

        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(tvSelected.getText().toString());
        spannableStringBuilder.setSpan(new CustomTypefaceSpan("", font), 0, tvSelected.getText().length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        tvSelected.setText(spannableStringBuilder);

        SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder(tvUnSelected1.getText().toString());
        spannableStringBuilder2.setSpan(new CustomTypefaceSpan("", font2), 0, tvUnSelected1.getText().length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        tvUnSelected1.setText(spannableStringBuilder2);

        SpannableStringBuilder spannableStringBuilder3 = new SpannableStringBuilder(tvUnSelected2.getText().toString());
        spannableStringBuilder3.setSpan(new CustomTypefaceSpan("", font2), 0, tvUnSelected2.getText().length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        tvUnSelected2.setText(spannableStringBuilder3);

        tvUnSelected1.setTextColor(getResources().getColor(R.color.text_black_opacity));
        tvUnSelected2.setTextColor(getResources().getColor(R.color.text_black_opacity));

        selectedText = tvSelected.getText().toString();
        geneticType = tvSelected.getText().toString();
    }

    public interface OnItemClickListener {
        void onItemClick(final String text);
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
                        checkDateEqual();
//                        if (currentDateSlot.equals(selectedDateToPass)) {
//                            Log.d(" current", "selectedDateToPass------" + selectedDateToPass);
//                            Log.d(" current", "currentDateSlot------" + currentDateSlot);
//
//
//                            if (checkSLotTime("09:00AM", actualTime)) {
//                                Log.d("checkslotime", "if");
//
//                                for (int i = 0; i < arrayTimeSlots.size(); i++) {
//                                    if (arrayTimeSlots.get(i).getName().equals(currentTimeSlot)) {
//                                        break;
//                                    }
//                                    arrayTimeSlots.get(i).setSelected(true);
//                                    arrayTimeSlots.get(i).setText("NOT AVAILABLE");
//                                    Log.d("name", "------" + arrayTimeSlots.get(i).getName());
//                                }
//                            } else {
//                                Log.d("checkslotime", "else");
//                            }
//                        } else {
//                            Log.d("not current", "selectedDateToPass------" + selectedDateToPass);
//                            Log.d("not current", "currentDateSlot------" + currentDateSlot);
//                        }
//
//                        mAdapter.refreshEvents(arrayTimeSlots);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    availableSlots = new ArrayList<>();
                    createList();
                    checkDateEqual();
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
