package com.bione.ui.home;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bione.R;
import com.bione.db.CommonData;
import com.bione.model.CommonResponse;
import com.bione.model.customerdata.Customer;
import com.bione.model.salesdetail.Data;
import com.bione.model.salesdetail.SalesDetail;
import com.bione.network.ApiError;
import com.bione.network.CommonParams;
import com.bione.network.ResponseResolver;
import com.bione.network.RestClient;
import com.bione.ui.Counselling.MyCounsellingFragment;
import com.bione.ui.base.BaseActivity;
import com.bione.ui.home.dashboard.DashboardFragment;
import com.bione.ui.onboarding.Splash;
import com.bione.utils.Log;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.navigation.NavigationView;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;
import com.zoho.salesiqembed.ZohoSalesIQ;

import java.io.File;
import java.util.List;

import io.paperdb.Paper;

import static com.bione.utils.AppConstant.PARAM_CUSTOMERID;
import static com.bione.utils.AppConstant.PARAM_EMAIL;
import static com.bione.utils.AppConstant.PARAM_MOBILE;
import static com.bione.utils.AppConstant.PARAM_OTP;


public class MainActivity extends BaseActivity {

    private static final Float END_SCALE = 0.7f;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView ivProfile;
    private TextView txtName, txtPhone, tvLogout;
    private Toolbar toolbar;
//    private FloatingActionButton fab;

    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_DASH = "Dashboard";
    private static final String TAG_PROFILE = "Profile";
    private static final String TAG_CHAT = "Chat";
    private static final String TAG_FAQ = "Faq";
    private static final String TAG_SESSION = "Session";
    private static final String TAG_CUSTOMER_RECEIPT = "Payment Receipt";
    //    private static final String TAG_SETTINGS = "settings";
    public static String CURRENT_TAG = TAG_DASH;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    private String category = "";

    private Dialog dialog;
    private LinearLayout topView;
    private LinearLayout bottomView;

    private int increment = 0;

    private ResideMenu resideMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        tvLogout = findViewById(R.id.tvLogout);

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("Are you sure?")
                        .setPositiveButton(R.string.text_yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, final int which) {
//                                Toast.makeText(getApplicationContext(), "Yes", Toast.LENGTH_SHORT).show();
                                Paper.book().destroy();
                                Intent intent = new Intent(MainActivity.this, Splash.class);
                                // set the new task and clear flags
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.text_no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
//                                Toast.makeText(getApplicationContext(), "No", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = navHeader.findViewById(R.id.name);
        txtPhone = navHeader.findViewById(R.id.phone);

//        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        ivProfile = (ImageView) navHeader.findViewById(R.id.ivProfile);

        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);


        // load nav menu header data
        loadNavHeader();

        if (CommonData.getUserData().getMobilenumber() == null || CommonData.getUserData().getMobilenumber().equals("")) {
            openDialog();
        }

        isSalesPerson();
        // initializing navigation menu
        setUpNavigationView();


        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_DASH;
            loadHomeFragment();
        }
//        residemenu();
    }

    private void residemenu() {
        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.color.colorPrimary);
        resideMenu.attachToActivity(this);
        resideMenu.setScaleValue(0.6f);
        resideMenu.setShadowVisible(false);

        // create menu items;
        String titles[] = {"Home", "Profile", "Calendar", "Settings"};
        int icon[] = {R.drawable.ic_facebook,
                R.drawable.ic_facebook,
                R.drawable.ic_facebook,
                R.drawable.ic_facebook};

        for (int i = 0; i < titles.length; i++) {
            ResideMenuItem item = new ResideMenuItem(this, icon[i], titles[i]);
            item.setOnClickListener(this);
            resideMenu.addMenuItem(item, ResideMenu.DIRECTION_RIGHT); // or  ResideMenu.DIRECTION_RIGHT
        }

        resideMenu.setMenuListener(new ResideMenu.OnMenuListener() {
            @Override
            public void openMenu() {
                Toast.makeText(getApplicationContext(), "Menu is opened!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void closeMenu() {
                Toast.makeText(getApplicationContext(), "Menu is closed!", Toast.LENGTH_SHORT).show();
            }
        });
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_LEFT);
    }

    /***
     * Load navigation menu header information
     * like background image, profile image
     * name, website, notifications action view (dot)
     */
    private void loadNavHeader() {

        // name, phone
        Customer customer = CommonData.getUserData();
        if (customer.getFirstname() != null) {
            if (customer.getLastname() != null) {
                txtName.setText(customer.getFirstname() + " " + customer.getLastname());
            } else {
                txtName.setText(customer.getFirstname());
            }
        }
        if (customer.getMobilenumber() != null) {
            txtPhone.setText("+" + customer.getMobilenumber());
        }

        if (CommonData.getUserPhoto() != null) {
            File photoFile = new File(CommonData.getUserPhoto().get(0).getThumbnailSmallPath());

            Glide.with(getApplicationContext())
                    .load(photoFile)
                    .apply(RequestOptions.circleCropTransform())
                    .into(ivProfile);
        }


    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // show or hide the fab button
//            toggleFab();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // show or hide the fab button
//        toggleFab();

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:

                // DASH fragment
                DashboardFragment dashboardFragment = new DashboardFragment();
//                dashboardFragment.
                return dashboardFragment;

            case 1:
                // PROFILE fragment
                ProfileFragment profileFragment = new ProfileFragment();
                return profileFragment;

            case 2:
                // Session fragment
                MyCounsellingFragment sessionFragment = new MyCounsellingFragment();
                return sessionFragment;

            case 3:
                // CHAT fragment
                ZohoSalesIQ.Chat.show();
                ChatFragment chatFragment = new ChatFragment();
                return chatFragment;
//                CustomerReceiptFragment customerReceiptFragment = new CustomerReceiptFragment();
//                return customerReceiptFragment;

            case 4:
                // FAQ fragment
                FaqFragment faqFragment = new FaqFragment();
                return faqFragment;

            case 5:
                PaymentReceiptFragment paymentReceiptFragment = new PaymentReceiptFragment();
                return paymentReceiptFragment;
//                Intent intent = new Intent(MainActivity.this, CustomerReceiptFragment.class);
//                startActivity(intent);

            default:
                return new DashboardFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void hideShowItem(boolean isShow) {
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_receipt).setVisible(isShow);
    }

    private void setUpNavigationView() {


        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;

                    case R.id.nav_dashboard:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_DASH;
                        break;

                    case R.id.nav_profile:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_PROFILE;
                        break;

                    case R.id.nav_session:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_SESSION;
                        break;

                    case R.id.nav_chat:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_CHAT;
                        break;

                    case R.id.nav_faq:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_FAQ;
                        break;

                    case R.id.nav_receipt:
                        navItemIndex = 5;
                        CURRENT_TAG = TAG_CUSTOMER_RECEIPT;

                        break;

                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);

                loadNavHeader();
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

//        CoordinatorLayout contentView = findViewById(R.id.contentView);
//        drawer.setScrimColor(getResources().getColor(R.color.colorPrimary));
//        drawer.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
//            @Override
//            public void onDrawerSlide(View drawerView, float slideOffset) {
//                super.onDrawerSlide(drawerView, slideOffset);
//
//                final Float diffScaleFloat = slideOffset * (1 - END_SCALE);
//                final Float offsetScale = 1 - diffScaleFloat;
//                contentView.setScaleX(offsetScale);
//                contentView.setScaleY(offsetScale);
//
//                final Float xOffset = drawerView.getWidth() * slideOffset;
//                final Float xOffsetDiff = contentView.getWidth() * diffScaleFloat / 2;
//                final Float xTranslation = xOffset - xOffsetDiff;
//                contentView.setTranslationX(xTranslation);
//
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_DASH;
                loadHomeFragment();
                return;
            }
        }

        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//
//        // show menu only when home fragment is selected
//        if (navItemIndex == 0) {
////            getMenuInflater().inflate(R.menu.main, menu);
//        }
//
//        // when fragment is notifications, load the menu created for notifications
//        if (navItemIndex == 3) {
////            getMenuInflater().inflate(R.menu.notifications, menu);
//        }
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_logout) {
//            Toast.makeText(getApplicationContext(), "Logout user!", Toast.LENGTH_LONG).show();
//            return true;
//        }
//
//        // user is in notifications fragment
//        // and selected 'Mark all as Read'
//        if (id == R.id.action_mark_all_read) {
//            Toast.makeText(getApplicationContext(), "All notifications marked as read!", Toast.LENGTH_LONG).show();
//        }
//
//        // user is in notifications fragment
//        // and selected 'Clear All'
//        if (id == R.id.action_clear_notifications) {
//            Toast.makeText(getApplicationContext(), "Clear all notifications!", Toast.LENGTH_LONG).show();
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    // show or hide the fab
//    private void toggleFab() {
//        if (navItemIndex == 0)
//            fab.show();
//        else
//            fab.hide();
//    }

    private void openDialog() {
        // custom dialog
//        final Dialog dialog = new Dialog(this);
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_otp_common);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setTitle("Title...");

        // set the custom dialog components - text, image and button
        topView = dialog.findViewById(R.id.topView);
        bottomView = dialog.findViewById(R.id.bottomView);

        AppCompatEditText etPhoneNumber = dialog.findViewById(R.id.etPhoneNumber);
        AppCompatEditText etOtp = dialog.findViewById(R.id.etOtp);
        AppCompatTextView tvOtp = dialog.findViewById(R.id.tvOtp);

        AppCompatTextView tvSendOtp = dialog.findViewById(R.id.tvSendOtp);
        AppCompatTextView tvVerify = dialog.findViewById(R.id.tvVerify);

        topView.setVisibility(View.VISIBLE);
        bottomView.setVisibility(View.GONE);

        // if button is clicked, close the custom dialog
        tvSendOtp.setOnClickListener(v -> callVerifyMobile(etPhoneNumber.getText().toString(), false));

        // if button is clicked, close the custom dialog
        tvVerify.setOnClickListener(v -> callVerifyOtp(etPhoneNumber.getText().toString(), etOtp.getText().toString()));

        dialog.show();
    }

    public void callVerifyMobile(final String phoneNumber, final boolean resend) {
        showLoading();
        final CommonParams commonParams = new CommonParams.Builder()
                .add(PARAM_MOBILE, "91" + phoneNumber).build();

        RestClient.getApiInterface().sendOtpRegister(commonParams.getMap()).enqueue(new ResponseResolver<List<CommonResponse>>() {
            @Override
            public void onSuccess(List<CommonResponse> commonResponse) {
                Log.d("onSuccess", "" + commonResponse);
                if (commonResponse.get(0).getStatusCode().equals("200")) {
                    topView.setVisibility(View.GONE);
                    bottomView.setVisibility(View.VISIBLE);
                } else {
                    showErrorMessage(commonResponse.get(0).getMessage());
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

    public void callVerifyOtp(final String phoneNumber, final String otp) {
        showLoading();
        final CommonParams commonParams = new CommonParams.Builder()
//                .add(PARAM_MOBILE, "" + phoneNumber).build();
                .add(PARAM_MOBILE, "91" + phoneNumber)
                .add(PARAM_CUSTOMERID, CommonData.getUserData().getEntityId())
                .add(PARAM_OTP, otp)
                .build();

        RestClient.getApiInterface().verifyMobile(commonParams.getMap()).enqueue(new ResponseResolver<List<CommonResponse>>() {
            @Override
            public void onSuccess(List<CommonResponse> commonResponse) {
                Log.d("onSuccess", "" + commonResponse);
                if (commonResponse.get(0).getStatusCode().equals("200")) {
                    Customer customer = CommonData.getUserData();
                    customer.setMobilenumber("91" + phoneNumber);
                    CommonData.saveUserData(customer);
                    dialog.dismiss();
                } else {
                    showErrorMessage(commonResponse.get(0).getMessage());
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

    public void isSalesPerson() {
//        showLoading();
        final CommonParams commonParams = new CommonParams.Builder()
                .add(PARAM_EMAIL, CommonData.getUserData().getEmail())
                .build();

        RestClient.getApiInterface().isSalesPerson(commonParams.getMap()).enqueue(new ResponseResolver<List<SalesDetail>>() {
            @Override
            public void onSuccess(List<SalesDetail> commonResponse) {
                Log.d("onSuccess", "" + commonResponse);
                if (commonResponse.get(0).getCode() == 200) {
//                    if (commonResponse.get(0).getSalesDetails()) {
                    hideShowItem(true);
                    CommonData.saveSalesData(commonResponse.get(0).toResponseModel(Data.class));
//                    } else {
//                        hideShowItem(false);
//                    }
                } else {
//                    showErrorMessage(commonResponse.get(0).getMessage());
                    hideShowItem(false);
                }
            }

            @Override
            public void onError(ApiError error) {
                Log.d("onError", "" + error);
                showErrorMessage(error.getMessage());
                hideShowItem(false);
            }

            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
                showErrorMessage(throwable.getMessage());
                hideShowItem(false);
            }
        });
    }
}