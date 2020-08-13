package com.bione.ui.schedulecall;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bione.R;
import com.bione.db.CommonData;
import com.bione.model.CommonResponse;
import com.bione.model.Slots;
import com.bione.model.availableSlots.ListItem;
import com.bione.model.availableSlots.Slot;
import com.bione.network.ApiError;
import com.bione.network.CommonParams;
import com.bione.network.ResponseResolver;
import com.bione.network.RestClient;
import com.bione.ui.base.BaseActivity;
import com.bione.ui.schedulecall.adapter.SlotsAdapter;
import com.bione.utils.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.bione.utils.AppConstant.PARAM_CUSTOMER_ID;
import static com.bione.utils.AppConstant.PARAM_CUSTOMER_NAME;
import static com.bione.utils.AppConstant.PARAM_DATE;
import static com.bione.utils.AppConstant.PARAM_GENETIC_TYPE;
import static com.bione.utils.AppConstant.PARAM_TIME_SLOT;
import static com.bione.utils.AppConstant.SUCCESS;

public class ScheduleNow extends BaseActivity {

    private AppCompatImageView ivBack;

    private RecyclerView recyclerViewHorizontal;
    private CalendarAdapter calendarAdapter;
    private List<MyCalendar> calendarList = new ArrayList<>();

    private String selectedText = "";

    private AppCompatTextView tvSelectedSlot;
    private AppCompatTextView tvScheduleNow;

    private RecyclerView recyclerView;
    private SlotsAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ListItem> availableSlots;
    private ArrayList<Slots> arrayTimeSlots = new ArrayList<>();

    private String geneticType = "Genetic";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_call);

        Log.d("customrer id", "------" + CommonData.getUserData().getEntityId());

        ivBack = findViewById(R.id.ivBack);
        tvSelectedSlot = findViewById(R.id.tvSelectedSlot);
        tvScheduleNow = findViewById(R.id.tvScheduleNow);
        tvScheduleNow.setOnClickListener(this);
        ivBack.setOnClickListener(this);

        availableSlots = new ArrayList<>();
        initRecycler();


        recyclerViewHorizontal = findViewById(R.id.date_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerViewHorizontal.setHasFixedSize(true);
        // use a linear layout manager
        // horizontal RecyclerView
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerViewHorizontal.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)

        Calendar mCalendar = Calendar.getInstance();
        //  mCalendar.get(Calendar.MONTH);
        //  mCalendar.get(Calendar.D)


        calendarAdapter = new CalendarAdapter(this, calendarList);
        recyclerViewHorizontal.setAdapter(calendarAdapter);
        prepareCalendarData();

        selectedText = calendarList.get(0).getDate();
        setScroll(mLayoutManager);

        availableSlotsAPI();

        // row click listener
        recyclerViewHorizontal.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerViewHorizontal, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                MyCalendar calendar = calendarList.get(position);
                TextView childTextView = (TextView) (view.findViewById(R.id.tvDate));

//                Animation startRotateAnimation = AnimationUtils.makeInChildBottomAnimation(getApplicationContext());
//                childTextView.startAnimation(startRotateAnimation);
//                childTextView.setTextColor(Color.BLUE);

//                Toast.makeText(ScheduleNow.this, ""+calendar.getDate(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), calendar.getDay() + " is selected!", Toast.LENGTH_SHORT).show();

                selectedText = calendar.getDate();

                int totalItemCount = mLayoutManager.getChildCount();


                for (int i = 0; i < totalItemCount; i++) {
                    View childView = recyclerViewHorizontal.getChildAt(i);
                    TextView childTextView2 = (childView.findViewById(R.id.tvDate));
                    View vLine = (childView.findViewById(R.id.vLine));
                    String childTextViewText = (String) (childTextView2.getText());

                    if (childTextViewText.equals(selectedText)) {
//                        childTextView2.setTextColor(Color.RED);
                        vLine.setVisibility(View.VISIBLE);
                    } else {
//                        childTextView2.setTextColor(Color.BLACK);
                        vLine.setVisibility(View.GONE);
                    }
                }
//                setScroll(mLayoutManager);
            }

            @Override
            public void onLongClick(View view, int position) {
                MyCalendar calendar = calendarList.get(position);

                TextView childTextView = (view.findViewById(R.id.tvDate));

//                childTextView.setTextColor(Color.BLUE);

                Toast.makeText(getApplicationContext(), calendar.getDate() + "/" + calendar.getDay() + "/" + calendar.getMonth() + "   selected!", Toast.LENGTH_SHORT).show();

                selectedText = calendar.getDate();

                int totalItemCount = mLayoutManager.getChildCount();

                for (int i = 0; i < totalItemCount; i++) {
                    View childView = recyclerViewHorizontal.getChildAt(i);
                    TextView childTextView2 = (childView.findViewById(R.id.tvDate));
                    View vLine = (childView.findViewById(R.id.vLine));

                    String childTextViewText = (String) (childTextView2.getText());

                    if (childTextViewText.equals(selectedText)) {
//                        childTextView2.setTextColor(Color.RED);
                        vLine.setVisibility(View.VISIBLE);
                    } else {
//                        childTextView2.setTextColor(Color.BLACK);
                        vLine.setVisibility(View.GONE);
                    }
                }

//                setScroll(mLayoutManager);
            }
        }));

    }

    private void setScroll(RecyclerView.LayoutManager mLayoutManager) {
        recyclerViewHorizontal.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView,
                                   int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int totalItemCount = mLayoutManager.getChildCount();

                for (int i = 0; i < totalItemCount; i++) {
                    View childView = recyclerView.getChildAt(i);
                    TextView childTextView = (childView.findViewById(R.id.tvDate));
                    View vLine = (childView.findViewById(R.id.vLine));

                    String childTextViewText = (String) (childTextView.getText());

                    if (childTextViewText.equals(selectedText)) {
//                        childTextView.setTextColor(Color.RED);
                        vLine.setVisibility(View.VISIBLE);
                    } else {
//                        childTextView.setTextColor(Color.BLACK);
                        vLine.setVisibility(View.GONE);
                    }
                }
            }
        });
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

            MyCalendar calendar = new MyCalendar(m_calendar.getWeekDay(),
                    String.valueOf(m_calendar.getDay()),
                    String.valueOf(m_calendar.getMonth()),
                    String.valueOf(m_calendar.getYear()),
                    i);
            m_calendar.getNextWeekDay(1);

            calendarList.add(calendar);

        }


        // notify adapter about data set changes
        // so that it will render the list with new data

        calendarAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.tvScheduleNow:
                scheduleCallAPI();

                break;

            case R.id.ivBack:
                finish();
                break;

            default:
                break;
        }
    }

    private void initRecycler() {
        recyclerView = findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        arrayTimeSlots = new ArrayList<>();
//        availableSlots = new ArrayList<>();

        Slots slots1 = new Slots("09:00AM-09:30AM", false);
        Slots slots2 = new Slots("09:30AM-10:00AM", false);
        Slots slots3 = new Slots("10:00AM-10:30AM", false);
        Slots slots4 = new Slots("10:30AM-11:00AM", false);
        Slots slots5 = new Slots("11:00AM-11:30AM", false);
        Slots slots6 = new Slots("11:30AM-12:00PM", false);
        Slots slots7 = new Slots("12:00PM-12:30PM", false);
        Slots slots8 = new Slots("12:30PM-01:00PM", false);
        Slots slots9 = new Slots("01:00PM-01:30PM", false);
        Slots slots10 = new Slots("01:30PM-02:00PM", false);
        Slots slots11 = new Slots("02:00PM-02:30PM", false);
        Slots slots12 = new Slots("02:30PM-03:00PM", false);
        Slots slots13 = new Slots("03:00PM-03:30PM", false);
        Slots slots14 = new Slots("03:30PM-04:00PM", false);
        Slots slots15 = new Slots("04:00PM-04:30PM", false);
        Slots slots16 = new Slots("04:30PM-05:00PM", false);
        Slots slots17 = new Slots("05:00PM-05:30PM", false);
        Slots slots18 = new Slots("05:30PM-06:00PM", false);

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


        for (int i = 0; i < availableSlots.size(); i++) {
            for (int j = 0; j < arrayTimeSlots.size(); j++) {
                if (availableSlots.get(i).getTimeSlot().equals(arrayTimeSlots.get(j).name)) {
                    arrayTimeSlots.get(j).setSelected(true);
                }
            }
        }

        // specify an adapter (see also next example)
        mAdapter = new SlotsAdapter(arrayTimeSlots, new OnItemClickListener() {
            @Override
            public void onItemClick(String text) {
                tvSelectedSlot.setText(text);
            }
        });
        recyclerView.setAdapter(mAdapter);
    }


    private void availableSlotsAPI() {
        showLoading();
        final CommonParams commonParams = new CommonParams.Builder()
                .add(PARAM_DATE, "2020-05-09")
//                .add(PARAM_DATE, "09-05-2020")
//                .add(PARAM_DATE, tvCalendarDate.getText().toString())
                .build();

        RestClient.getApiInterface().availabilitySlots(commonParams.getMap()).enqueue(new ResponseResolver<List<Slot>>() {
            @Override
            public void onSuccess(List<Slot> slots) {

                if (slots.get(0).getCode() == SUCCESS) {
                    try {

                        Log.d(" time slots arrayTimeSlots", " size :: " + slots.get(0).getListItems().size());
                        // specify an adapter (see also next example)
                        availableSlots = (ArrayList<ListItem>) slots.get(0).getListItems();
                        initRecycler();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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

    private void scheduleCallAPI() {
        showLoading();
        final CommonParams commonParams = new CommonParams.Builder()
                .add(PARAM_CUSTOMER_NAME, CommonData.getUserData().getFirstname())
                .add(PARAM_CUSTOMER_ID, CommonData.getUserData().getEntityId())
                .add(PARAM_GENETIC_TYPE, geneticType)
                .add(PARAM_DATE, "09-05-2020")
//                .add(PARAM_DATE, tvCalendarDate.getText().toString())
                .add(PARAM_TIME_SLOT, arrayTimeSlots.get(mAdapter.getCheckedPosition()).name)

                .build();

        RestClient.getApiInterface().scheduleCall(commonParams.getMap()).enqueue(new ResponseResolver<List<CommonResponse>>() {
            @Override
            public void onSuccess(List<CommonResponse> commonResponses) {

                if (commonResponses.get(0).getStatusCode().equals("200")) {
                    finish();
                    Intent intent = new Intent(ScheduleNow.this, CounsellingConfirm.class);
                    startActivity(intent);
                } else {
                    showErrorMessage(commonResponses.get(0).getMessage());
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
}
