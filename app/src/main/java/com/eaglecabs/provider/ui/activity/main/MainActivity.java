package com.eaglecabs.provider.ui.activity.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chaos.view.PinView;
import com.eaglecabs.provider.BuildConfig;
import com.eaglecabs.provider.MvpApplication;
import com.eaglecabs.provider.MyBackgroundLocationService;
import com.eaglecabs.provider.R;
import com.eaglecabs.provider.base.BaseActivity;
import com.eaglecabs.provider.common.ChatHeadService;
import com.eaglecabs.provider.common.CommonValidation;
import com.eaglecabs.provider.common.ConnectivityReceiver;
import com.eaglecabs.provider.common.GPSTrackers;
import com.eaglecabs.provider.common.PolyUtils;
import com.eaglecabs.provider.common.SharedHelper;
import com.eaglecabs.provider.common.Utilities;
import com.eaglecabs.provider.common.fcm.MyFirebaseMessagingService;
import com.eaglecabs.provider.data.models.VersionStatus;
import com.eaglecabs.provider.data.network.model.Fleet;
import com.eaglecabs.provider.data.network.model.OTPResponse;
import com.eaglecabs.provider.data.network.model.TripResponse;
import com.eaglecabs.provider.data.network.model.User;
import com.eaglecabs.provider.ui.activity.aboutus.AboutActivity;
import com.eaglecabs.provider.ui.activity.bankdetail.BankDetailActivity;
import com.eaglecabs.provider.ui.activity.bankdetail.BankDetails;
import com.eaglecabs.provider.ui.activity.document.DocumentActivity;
import com.eaglecabs.provider.ui.activity.earnings.EarningsActivity;
import com.eaglecabs.provider.ui.activity.fleet.VehiclesActivity;
import com.eaglecabs.provider.ui.activity.help.HelpActivity;
import com.eaglecabs.provider.ui.activity.invite.InviteActivity;
import com.eaglecabs.provider.ui.activity.location_pick.LocationPickActivity;
import com.eaglecabs.provider.ui.activity.notification.NotificationActivity;
import com.eaglecabs.provider.ui.activity.profile.ProfileActivity;
import com.eaglecabs.provider.ui.activity.rate_card.RateCardActivity;
import com.eaglecabs.provider.ui.activity.scheduled.EagleScheduledRidesAcrivity;
import com.eaglecabs.provider.ui.activity.splash.SplashActivity;
import com.eaglecabs.provider.ui.activity.summary.SummaryActivity;
import com.eaglecabs.provider.ui.activity.wallet.WalletActivity;
import com.eaglecabs.provider.ui.activity.your_trips.YourTripActivity;
import com.eaglecabs.provider.ui.bottomsheetdialog.invoice_flow.InvoiceDialogFragment;
import com.eaglecabs.provider.ui.bottomsheetdialog.rating.RatingDialogFragment;
import com.eaglecabs.provider.ui.fragment.incoming_request.IncomingRequestFragment;
import com.eaglecabs.provider.ui.fragment.offline.OfflineFragment;
import com.eaglecabs.provider.ui.fragment.status_flow.StatusFlowFragment;
import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.enums.Display;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import br.com.safety.locationlistenerhelper.core.LocationTracker;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

import static com.eaglecabs.provider.MvpApplication.DEFAULT_ZOOM;
import static com.eaglecabs.provider.MvpApplication.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static com.eaglecabs.provider.MvpApplication.PICK_LOCATION_REQUEST_CODE;
import static com.eaglecabs.provider.MvpApplication.mLastKnownLocation;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class MainActivity extends BaseActivity implements MainIView, NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleMap.OnCameraMoveListener, GoogleMap.OnCameraIdleListener, DirectionCallback,
        ConnectivityReceiver.ConnectivityReceiverListener {


    private static final String TAG = "MainActivity";
    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.menu)
    ImageView menu;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.top_layout)
    LinearLayout topLayout;
    @BindView(R.id.lnrLocationHeader)
    LinearLayout lnrLocationHeader;
    @BindView(R.id.lnrNoInternet)
    LinearLayout lnrNoInternet;
    @BindView(R.id.btnGoOffline)
    Button btnGoOffline;
    @BindView(R.id.lblLocationType)
    TextView lblLocationType;
    @BindView(R.id.lblLocationName)
    TextView lblLocationName;
    @BindView(R.id.offline_container)
    FrameLayout offlineContainer;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.gps)
    ImageView gps;
    @BindView(R.id.navigation_img)
    ImageView navigationImg;
    @BindView(R.id.btnInstant)
    Button btnInstant;
    private Intent gpsServiceIntent;
    private boolean mLocationPermissionGranted;
    SupportMapFragment mapFragment;
    GoogleMap googleMap;
    public FusedLocationProviderClient mFusedLocationProviderClient;
    public static boolean myLocationCalculationCheck = false;
    BottomSheetBehavior bottomSheetBehavior;
    ConnectivityReceiver internetReceiver = new ConnectivityReceiver();
    Location serverlatlng;
    AlertDialog otpDialog;
    LinearLayout lnrVerification;
    TextView instantDestination, estimateFare;
    String server_otp = "", user_id_now = "";
    PinView pinView;
    private LocationTracker locationTracker;
    TextView lblMenuName, lblMenuEmail, lblLoginHours, lblWalletMessage;
    CircleImageView imgMenu;
    ImageView imgStatus;
    MainPresenter<MainActivity> presenter = new MainPresenter<>();
    private final LatLng mDefaultLocation = new LatLng(13.077032, 80.236646);
    private static final int CHATHEAD_OVERLAY_PERMISSION_REQUEST_CODE = 101;

    NumberFormat numberFormat;

    String logout_message = "";



    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true);
            setTurnScreenOn(true);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        }
        ButterKnife.bind(this);
        presenter.attachView(this);
        numberFormat = MvpApplication.getNumberFormat();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(internetReceiver, intentFilter);
        registerReceiver(myReceiver, new IntentFilter(MyFirebaseMessagingService.INTENT_FILTER));
        // register connection status listener
        MvpApplication.getInstance().setConnectivityListener(this);


        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerview = navigationView.getHeaderView(0);
        imgMenu = headerview.findViewById(R.id.imgMenu);
        lblMenuName = headerview.findViewById(R.id.lblMenuName);
        lblLoginHours = headerview.findViewById(R.id.login_hours);
        lblWalletMessage = headerview.findViewById(R.id.wallet_message);

        imgStatus = headerview.findViewById(R.id.imgStatus);
        lblMenuEmail = headerview.findViewById(R.id.lblMenuEmail);

        headerview.setOnClickListener(v -> {
            Intent intent = new Intent(activity(), ProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity().startActivity(intent);
        });

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        bottomSheetBehavior = BottomSheetBehavior.from(container);
        bottomSheetBehavior.setHideable(false);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        presenter.getProfile();

//        new AppUpdater(activity())
//                .setUpdateFrom(UpdateFrom.GOOGLE_PLAY)
//                .setDisplay(Display.DIALOG)
//                .setButtonDoNotShowAgain(null)
//                .setButtonDismiss(null)
//                .setCancelable(false)
//                .start();

        showFloatingView(activity(), true);


        locationTracker = new LocationTracker("my.action")
                .setInterval(30)
                .setGps(true)
                .setNetWork(false)
                .start(getBaseContext());
    }

    private String readFromFile(Context context) {

        String ret = "";
        String filepath = "EagleProvider";
        File myExternalFile;
        String myData = "";

        myExternalFile = new File(context.getExternalFilesDir(filepath), "Location_Data.txt");
        if (!myExternalFile.exists()){
            try {
                myExternalFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            FileInputStream fis = new FileInputStream(myExternalFile);
            DataInputStream in = new DataInputStream(fis);
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                myData = myData + strLine;
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("filenot", "found");
        }

        return myData.toString();
    }

    private void startLocationService() {
        //start background location service

        Intent intent = new Intent(this, MyBackgroundLocationService.class);
        ContextCompat.startForegroundService(this, intent);
        //Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();

    }


  private void stopLocationService() {
        //start background location service

        Intent intent = new Intent(this, MyBackgroundLocationService.class);
        stopService(intent);
//        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        checkConnection();
        lblMenuName.setText(SharedHelper.getKey(this, "user_name"));
        lblWalletMessage.setText(SharedHelper.getKey(this, "wallet_message"));
        lblLoginHours.setText(getString(R.string.today_login_hours_, minitueToHourMin(SharedHelper.getKey(activity(), "login_mins", "0"))));
        Glide.with(activity()).load(SharedHelper.getKey(this, "user_avatar")).apply(RequestOptions.placeholderOf(R.drawable.user).dontAnimate().error(R.drawable.user)).into(imgMenu);

        MenuItem walletItem = navView.getMenu().findItem(R.id.nav_wallet);
        walletItem.setTitle(getString(R.string.wallet_, numberFormat.format(Double.valueOf(SharedHelper.getKey(activity(), "wallet_balance", "0.0")))));

        HashMap<String, Object> params = new HashMap<>();
        if (mLastKnownLocation != null) {
            params.put("latitude", mLastKnownLocation.getLatitude());
            params.put("longitude", mLastKnownLocation.getLongitude());
        }

        if (getIntent() != null && getIntent().hasExtra("isNotification")) {
            Log.d("From Notification", "Calling");
            presenter.getTrip(params, false);
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                if (extras.containsKey("isNotification")) {
                    extras.remove("isNotification");
                }
            }
        } else {
            presenter.getTrip(params, true);

        }

        gpsServiceIntent = new Intent(this, GPSTrackers.class);
        startService(gpsServiceIntent);

        getVersionStatus();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(myReceiver);
//        unregisterReceiver(internetReceiver);
//        EventBus.getDefault().unregister(this);
//        if (gpsServiceIntent != null) stopService(gpsServiceIntent);

//        if (locationTracker != null)
//            locationTracker.stopLocationService(this);

    }

    private boolean isServiceRunning() {
        ActivityManager manager =
                (ActivityManager) activity().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service :
                manager.getRunningServices(Integer.MAX_VALUE)) {
            if (GPSTrackers.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_your_trips) {
            startActivity(new Intent(this, YourTripActivity.class));
        } else if (id == R.id.nav_earnings) {
            Log.e("Earning", "inside");
            startActivity(new Intent(MainActivity.this, EarningsActivity.class));
            Log.e("Earning", "outside");
        }else if(id==R.id.nav_rate_card){
            startActivity(new Intent(MainActivity.this, RateCardActivity.class));
        } else if (id == R.id.nav_summary) {
            startActivity(new Intent(MainActivity.this, SummaryActivity.class));
        } else if (id == R.id.nav_scheduled_trips) {
            startActivity(new Intent(MainActivity.this, EagleScheduledRidesAcrivity.class));
        } else if (id == R.id.nav_help) {
            startActivity(new Intent(MainActivity.this, HelpActivity.class));
        } else if (id == R.id.nav_share) {
            shareApp();
        } else if (id == R.id.nav_bank) {
            Intent intent = new Intent(activity(), BankDetailActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity().startActivity(intent);
        } else if (id == R.id.nav_document) {
            startActivity(new Intent(MainActivity.this, DocumentActivity.class));
        } else if (id == R.id.nav_notifications) {
            startActivity(new Intent(MainActivity.this, NotificationActivity.class));
        } else if (id == R.id.nav_wallet) {
            startActivity(new Intent(activity(), WalletActivity.class));
        } else if (id == R.id.nav_invite_referral) {
            startActivity(new Intent(MainActivity.this, InviteActivity.class));
        } else if (id == R.id.nav_aboutus) {
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
        } else if (id == R.id.nav_faq) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(BuildConfig.BASE_URL + "faq"));
            startActivity(i);
        } else if (id == R.id.nav_logout) {
            ShowLogoutPopUp();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public void onCameraIdle() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @Override
    public void onCameraMove() {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        try {
            boolean success = googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json));
        } catch (Resources.NotFoundException e) {
            Log.d("Map:Style", "Can't find style. Error: ");
        }
        this.googleMap = googleMap;
        getLocationPermission();
        updateLocationUI();
        getDeviceLocation();

        boolean serviceRunningStatus = isServiceRunning();

        if (serviceRunningStatus) {
            Intent serviceIntent = new Intent(this, GPSTrackers.class);
            stopService(serviceIntent);
        }
//        if (!serviceRunningStatus) {
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
//                activity().startService(new Intent(activity(), GPSTrackers.class));
//            } else {
//                Intent serviceIntent = new Intent(activity(), GPSTrackers.class);
//                ContextCompat.startForegroundService(activity(), serviceIntent);
//            }
//        }
    }


    @OnClick({R.id.menu, R.id.nav_view, R.id.btnGoOffline, R.id.navigation_img, R.id.gps})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.menu:
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(Gravity.START);
                }
                break;
            case R.id.nav_view:
                break;
            case R.id.gps:
                if (mLastKnownLocation != null) {
                    LatLng currentLatLng = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, DEFAULT_ZOOM));
                }
                break;
            case R.id.btnGoOffline:
                HashMap<String, Object> map = new HashMap<>();
                map.put("service_status", "offline");
                presenter.providerAvailable(map);
                break;
            case R.id.navigation_img:
                if (lblLocationType.getText().toString().equalsIgnoreCase(getString(R.string.pick_up_location))) {
                    Uri gmmIntentUri = Uri.parse("google.navigation:q=" + DATUM.getSAddress());
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                } else {
                    Uri gmmIntentUri = Uri.parse("google.navigation:q=" + DATUM.getDAddress());
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
                break;
        }
    }


    public void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    private void updateLocationUI() {
        if (googleMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                googleMap.setOnCameraMoveListener(this);
                googleMap.setOnCameraIdleListener(this);
            } else {
                googleMap.setMyLocationEnabled(false);
                googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }


    void getDeviceLocation() {
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        mLastKnownLocation = task.getResult();
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                new LatLng(mLastKnownLocation.getLatitude(),
                                        mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));

                        String s_address = getAddress(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()));
                        if (s_address != null) {
                            RIDE_REQUEST.put("s_address", s_address);
                            RIDE_REQUEST.put("s_latitude", mLastKnownLocation.getLatitude());
                            RIDE_REQUEST.put("s_longitude", mLastKnownLocation.getLongitude());
                        }

                        SharedHelper.putKey(activity(), "current_latitude", String.valueOf(mLastKnownLocation.getLatitude()));
                        SharedHelper.putKey(activity(), "current_longitude", String.valueOf(mLastKnownLocation.getLongitude()));

                    } else {
                        Log.d("Map", "Current location is null. Using defaults.");
                        Log.e("Map", "Exception: %s", task.getException());
                        googleMap.animateCamera(CameraUpdateFactory
                                .newLatLngZoom(mDefaultLocation, 10));
                        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getVersionStatus(){
        HashMap<String, String> map = new HashMap<>();
        map.put("app_type", "2");
        presenter.versionStatus(map);
        System.out.println("LOGGER map : "+map);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                    updateLocationUI();
                    getDeviceLocation();
                }
            }
        }
    }

    private void changeFragment(Fragment fragment) {

        if (isFinishing()) {
            return;
        }

        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, fragment, fragment.getTag());
            transaction.commit();
            btnGoOffline.setVisibility(View.GONE);
        } else {

            for (Fragment fragmentd : getSupportFragmentManager().getFragments()) {
                if (fragmentd instanceof IncomingRequestFragment) {
                    getSupportFragmentManager().beginTransaction().remove(fragmentd).commit();
                }
            }

            container.removeAllViews();
            btnGoOffline.setVisibility(View.VISIBLE);
            lnrLocationHeader.setVisibility(View.GONE);
            googleMap.clear();
            DATUM = null;

            if (googleMap != null && mLastKnownLocation != null) {
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
            }
        }
    }


    private void offlineFragment(String status) {
        Bundle bundle = new Bundle();
        bundle.putString("status", status);
        OfflineFragment fragment = new OfflineFragment();
        fragment.setArguments(bundle);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.offline_container, fragment, fragment.getTag());
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onSuccess(User user) {
        SharedHelper.putKey(this, "user_id", String.valueOf(user.getId()));
        SharedHelper.putKey(this, "user_name", user.getFirstName() + " " + user.getLastName());
        SharedHelper.putKey(this, "user_avatar", BuildConfig.BASE_IMAGE_URL + user.getAvatar());

        SharedHelper.putKey(this, "wallet_balance", String.valueOf(user.getWalletBalance()));
        SharedHelper.putKey(this, "wallet_message", user.getWalletMessage());
        SharedHelper.putKey(this, "login_mins", String.valueOf(user.getLoginMins()));
        MenuItem walletItem = navView.getMenu().findItem(R.id.nav_wallet);
        walletItem.setTitle(getString(R.string.wallet_, numberFormat.format(user.getWalletBalance())));

        lblMenuName.setText(SharedHelper.getKey(this, "user_name"));
        lblMenuEmail.setText(user.getMobile());
        lblWalletMessage.setText(user.getWalletMessage());
        lblLoginHours.setText(getString(R.string.today_login_hours_, minitueToHourMin(String.valueOf(user.getLoginMins()))));

        Glide.with(activity()).load(SharedHelper.getKey(this, "user_avatar")).apply(RequestOptions.placeholderOf(R.drawable.user).dontAnimate().error(R.drawable.user)).into(imgMenu);
        if (!user.getDeviceToken().equals(SharedHelper.getKeyFCM(this, "device_token"))) {
            SharedHelper.clearSharedPreferences(activity());
            finishAffinity();
            startActivity(new Intent(activity(), SplashActivity.class));
        }

        if (user.getStatus().equalsIgnoreCase("approved")) {
            Fleet fleet = user.getFleet();
            if (fleet != null) {
                SharedHelper.putKey(activity(), "fleet_id", String.valueOf(fleet.getId()));
                if (user.getProviderVehicle() != null) {
                    SharedHelper.putKey(activity(), "fleet_vehicle_id", String.valueOf(user.getProviderVehicle().getFleetVehicleId()));
                }
                if (user.getService().isEmpty()) {
                    startActivity(new Intent(activity(), VehiclesActivity.class));
                }
            }
        }

    }

    private String minitueToHourMin(String min) {
        String value = null;
        SimpleDateFormat sdf = new SimpleDateFormat("mm", Locale.getDefault());
        try {
            Date dt = sdf.parse(String.valueOf(min));
            sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
            value = sdf.format(dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return value;
    }

    @Override
    public void onSuccessLogout(Object object) {
        Toast.makeText(activity(), getString(R.string.logout_successfully), Toast.LENGTH_SHORT).show();
        SharedHelper.clearSharedPreferences(activity());
        finishAffinity();
        startActivity(new Intent(activity(), SplashActivity.class));
    }

    @Override
    public void onSuccess(TripResponse tripResponse) {

        String serviceStatus = tripResponse.getServiceStatus();
        String accountStatus = tripResponse.getAccountStatus();

        if (accountStatus.equalsIgnoreCase("approved")) {
            imgStatus.setImageResource(R.drawable.banner_active);
            if (tripResponse.getRequests().isEmpty()) {
                changeFlow("EMPTY");
                btnInstant.setVisibility(View.GONE);
            } else {
                btnInstant.setVisibility(View.GONE);
                time_to_left = tripResponse.getRequests().get(0).getTimeLeftToRespond();
                DATUM = tripResponse.getRequests().get(0).getRequest();
                changeFlow(DATUM.getStatus());
            }

            if (serviceStatus.equalsIgnoreCase("offline")) {
                offlineFragment(serviceStatus);
                stopLocationService();

            } else {
                startLocationService();

                offlineContainer.removeAllViews();
            }

        } else {
            imgStatus.setImageResource(R.drawable.banner_waiting);
            offlineFragment(accountStatus);
        }
    }

    @Override
    public void onSuccessProviderAvailable(Object object) {
        String jsonInString = new Gson().toJson(object);
        try {
            JSONObject jsonObj = new JSONObject(jsonInString);
            if (jsonObj.has("error")){
                Toast.makeText(activity(), jsonObj.optString("error"), Toast.LENGTH_SHORT).show();
                ((MainActivity)activity()).getProfile();


            }else{
                if (jsonObj.has("status") && jsonObj.optString("status").equalsIgnoreCase("online")){
                 startLocationService();
                    //Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT).show();
                }else{
                  stopLocationService();
                }
            }
        } catch (Exception e) {

        }
        offlineFragment("");
        stopLocationService();
    }

    @Override
    public void onSuccessFCM(Object object) {
        Utilities.printV("onSuccessFCM", "onSuccessFCM");
    }

    @Override
    public void onSuccessLocationUpdate(TripResponse tripResponse) {

    }

    @Override
    public void onSuccessServerLocationUpdate(Object object) {

    }

    @Override
    public void onSuccessInstant(OTPResponse object) {
        estimateFare.setText(getString(R.string.estimate_fare_, numberFormat.format(object.getEstimatedFare())));
        pinView.requestFocus();
        lnrVerification.setVisibility(View.VISIBLE);
        server_otp = object.getOtp() + "";
        user_id_now = object.getUser().getId() + "";
    }

    @Override
    public void onSuccessInstantNow(Object object) {

        HashMap<String, Object> params = new HashMap<>();
        if (mLastKnownLocation != null) {
            params.put("latitude", mLastKnownLocation.getLatitude());
            params.put("longitude", mLastKnownLocation.getLongitude());
        }

        presenter.getTrip(params, true);

        otpDialog.cancel();

    }

    @Override
    public void onSuccess(VersionStatus versionStatus) {
        System.out.println("LOGGER getName : "+versionStatus.getData().getName());
        System.out.println("LOGGER getId : "+versionStatus.getData().getId());

        if (versionStatus==null)
            return;


        if (BuildConfig.VERSION_CODE<Integer.parseInt(versionStatus.getData().getVersionCode())){
            if (versionStatus.getData().getFourceUpgrade()==1){
                //force user to upgrade the app
                forceToUpgradeDialog(true);
            }else{
              //  forceToUpgradeDialog(false);

            }

            //Need to handle skip as well

        }
        System.out.println("LOGGER getName : "+versionStatus.toString());
//        System.out.println("LOGGER getVersionName : "+versionStatus.getVersionName());

    }


    private void forceToUpgradeDialog(boolean isForceUpdate) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        //builder.setTitle("Update Available!");
        builder.setCancelable(false);

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.upgrade_view,null);

        TextView title = view.findViewById(R.id.title);
        TextView message = view.findViewById(R.id.message);
        title.setText("New Version Available       "+ BuildConfig.VERSION_NAME);
        message.setText("In order to continue, you must update the Eagle Driver application. This should only take a few moments.\n");
        builder.setView(view);

        //builder.setMessage("In order to continue, you must update the DNA  application. This should only take a few moments.\n");

        builder.setPositiveButton("Update", (dialog, which) -> {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + BuildConfig.APPLICATION_ID)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.eaglecabs.provider")));
            }

//            clearApplicationData();
//            DnaPrefs.putBoolean(this, Constants.LoginCheck, false);
//            LoginManager.getInstance().logOut();


            dialog.dismiss();
        });

        /*if (!isForceUpdate) {
            builder.setNegativeButton("Later", (dialog, which) -> {
                DnaPrefs.putBoolean(MainActivity.this, Constants.SOFT_UPGRADE_SKIP, true);
                dialog.dismiss();
            });
        }
*/
        AlertDialog dialog = builder.show();

        if (isFinishing() && dialog != null) {
            dialog.dismiss();
        }
    }


    public void changeFlow(String status) {
        Utilities.printV("Current status==>", status);
        dismissDialog("INVOICE");
        dismissDialog("RATING");

        switch (status) {
            case "EMPTY":
                changeFragment(null);
                break;
            case "SEARCHING":
                if (!TextUtils.isEmpty(DATUM.getBookingId())) {
                    changeFragment(new IncomingRequestFragment());
                }
                break;
            case "ACCEPTED":
                lblLocationType.setText(R.string.pick_up_location);
                lblLocationName.setText(DATUM.getSAddress());
                lnrLocationHeader.setVisibility(View.VISIBLE);
                changeFragment(new StatusFlowFragment());
                break;
            case "STARTED":
                lblLocationType.setText(R.string.pick_up_location);
                lblLocationName.setText(DATUM.getSAddress());
                lnrLocationHeader.setVisibility(View.VISIBLE);
                changeFragment(new StatusFlowFragment());
                break;
            case "ARRIVED":
                lblLocationType.setText(R.string.pick_up_location);
                lblLocationName.setText(DATUM.getSAddress());
                lnrLocationHeader.setVisibility(View.VISIBLE);
                changeFragment(new StatusFlowFragment());
                break;
            case "PICKEDUP":
                SERVICE_STATUS = "PICKEDUP";
                SharedHelper.putKey(this, "tripStatus", "PICKEDUP");

                lblLocationType.setText(R.string.drop_location);
                lblLocationName.setText(DATUM.getDAddress());
                if (DATUM.getServiceRequired().equalsIgnoreCase("rental")) {
                    lnrLocationHeader.setVisibility(View.GONE);
                } else {
                    lnrLocationHeader.setVisibility(View.VISIBLE);
                }
                changeFragment(new StatusFlowFragment());
                break;
            case "DROPPED":
                lnrLocationHeader.setVisibility(View.GONE);
                InvoiceDialogFragment invoiceDialogFragment = new InvoiceDialogFragment();
                invoiceDialogFragment.show(getSupportFragmentManager(), "INVOICE");
                break;
            case "COMPLETED":
                lnrLocationHeader.setVisibility(View.GONE);
                RatingDialogFragment ratingDialogFragment = new RatingDialogFragment();
                ratingDialogFragment.show(getSupportFragmentManager(), "RATING");

                presenter.getProfile();
                break;
            default:
                break;
        }
    }

    void dismissDialog(String tag) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);

        if (fragment instanceof InvoiceDialogFragment) {
            InvoiceDialogFragment df = (InvoiceDialogFragment) fragment;
            df.dismissAllowingStateLoss();
        }

        if (fragment instanceof RatingDialogFragment) {
            RatingDialogFragment df = (RatingDialogFragment) fragment;
            df.dismissAllowingStateLoss();
        }

    }


    void ShowLogoutPopUp() {

        if (!SharedHelper.getKey(activity(), "fleet_vehicle_id", "").isEmpty()) {
            logout_message = "Are you sure want to handover this vehicle?";
        } else {
            logout_message = getString(R.string.are_sure_you_want_to_logout);
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder
                .setMessage(logout_message)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, (dialog, id) -> {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("id", SharedHelper.getKey(activity(), "user_id"));
                    if (!SharedHelper.getKey(activity(), "fleet_vehicle_id", "").isEmpty()) {
                        map.put("handover_vehicle", SharedHelper.getKey(activity(), "fleet_vehicle_id", ""));
                    }
                    map.put("device_token", SharedHelper.getKeyFCM(this, "device_token"));
                    presenter.logout(map);
                })
                .setNegativeButton(R.string.no, (dialog, id) -> {
                    dialog.cancel();
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            getTripDetails(true);

//            if (intent.hasExtra("providerAction")) {
//                getTripDetails(false);
//            } else {
//                getTripDetails(true);
//            }
        }
    };

    public void getTripDetails(boolean b) {
        HashMap<String, Object> params = new HashMap<>();
        if (mLastKnownLocation != null) {
            params.put("latitude", mLastKnownLocation.getLatitude());
            params.put("longitude", mLastKnownLocation.getLongitude());
        }

        if (b) {
            presenter.getTrip(params, false);
        } else {
            presenter.getTrip(params, true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void getEvent(final Location location) {
        // location.setLatitude(28.787750);
        //location.setLongitude(77.261420);
        mLastKnownLocation = location;

        SharedHelper.putKey(this, "current_latitude", String.valueOf(location.getLatitude()));
        SharedHelper.putKey(this, "current_longitude", String.valueOf(location.getLongitude()));
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        pushNotification(latLng, location);
        presenter.locationUpdateServer(latLng);
    }


//    private void getDistance(double currentLat2, double currentLong2, double mallLat2, double mallLong2) {
//        if (ConnectivityReceiver.isConnected()) {
//           new AsyncTask<Void,Void,Double>() {
//               @Override
//               protected Double doInBackground(Void... voids) {
//
//                   Location loc1 = new Location("");
//                   loc1.setLatitude(currentLat2);
//                   loc1.setLongitude(currentLong2);
//
//                   Location loc2 = new Location("");
//                   loc2.setLatitude(mallLat2);
//                   loc2.setLongitude(mallLong2);
//
//                   return Double.valueOf(loc1.distanceTo(loc2));
//
//
//
//
////                   double theta = lon1 - lon2;
////                   double dist = Math.sin(deg2rad(lat1))
////                           * Math.sin(deg2rad(lat2))
////                           + Math.cos(deg2rad(lat1))
////                           * Math.cos(deg2rad(lat2))
////                           * Math.cos(deg2rad(theta));
////                   dist = Math.acos(dist);
////                   dist = rad2deg(dist);
////                   dist = dist * 60 * 1.1515;
////                   return dist;
//               }
//
//               @Override
//               protected void onPostExecute(Double dist) {
//                   super.onPostExecute(dist);
//                   if (dist>300 && dist<800){
//                       TRIPDISTANCE = TRIPDISTANCE+dist;
//
//                       LAST_LAT = mLastKnownLocation.getLatitude();
//                       LAST_LONG = mLastKnownLocation.getLongitude();
//                        Toast.makeText(MainActivity.this, "Trip Distance is "+DFORMAT.format(TRIPDISTANCE), Toast.LENGTH_SHORT).show();
//
//                   }else {
//                       Toast.makeText(MainActivity.this, "No Distance    "+0, Toast.LENGTH_SHORT).show();
//
//                   }
//
//
//                   //SharedHelper.putKey(MainActivity.this, "totalDist", String.valueOf(TRIPDISTANCE));
//
//               }
//           }.execute();
//        }
//
//    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }


    public void drawRoute(LatLng source, LatLng destination) {
        GoogleDirection.withServerKey(BuildConfig.google_map_key)
                .from(source)
                .to(destination)
                .transportMode(TransportMode.DRIVING)
                .execute(this);
    }

    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {

        if (direction.isOK()) {
            googleMap.clear();
            Route route = direction.getRouteList().get(0);
            if (!route.getLegList().isEmpty()) {
                Leg startLeg = route.getLegList().get(0);
                Leg endLeg = route.getLegList().get(0);
                LatLng origin = new LatLng(startLeg.getStartLocation().getLatitude(), startLeg.getStartLocation().getLongitude());
                LatLng destination = new LatLng(endLeg.getEndLocation().getLatitude(), endLeg.getEndLocation().getLongitude());
                googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.src_icon)).position(origin)).setTag(startLeg);
                googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.des_icon)).position(destination)).setTag(endLeg);
            }

            ArrayList<LatLng> directionPositionList = route.getLegList().get(0).getDirectionPoint();
            googleMap.addPolyline(DirectionConverter.createPolyline(this, directionPositionList, 3, getResources().getColor(R.color.colorAccent)));
            setCameraWithCoordinationBounds(route);

        } else {
            //Toast.makeText(this, direction.getStatus(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onDirectionFailure(Throwable t) {
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void setCameraWithCoordinationBounds(Route route) {
        LatLng southwest = route.getBound().getSouthwestCoordination().getCoordination();
        LatLng northeast = route.getBound().getNortheastCoordination().getCoordination();
        LatLngBounds bounds = new LatLngBounds(southwest, northeast);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
    }

    public void shareApp() {
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            i.putExtra(Intent.EXTRA_TEXT, "Hey Checkout this app, " + getString(R.string.app_name) + "\nhttps://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
            startActivity(Intent.createChooser(i, "choose one"));
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
    }

    private void pushNotification(LatLng latlng, Location location) {

        if (DATUM == null) {
            return;
        }

        if (DATUM.getStatus().equalsIgnoreCase("ACCEPTED") || DATUM.getStatus().equalsIgnoreCase("STARTED") || DATUM.getStatus().equalsIgnoreCase("ARRIVED") ||
                DATUM.getStatus().equalsIgnoreCase("PICKEDUP") || DATUM.getStatus().equalsIgnoreCase("DROPPED")) {

            JsonObject jPayload = new JsonObject();
            JsonObject jData = new JsonObject();

            jData.addProperty("latitude", latlng.latitude);
            jData.addProperty("longitude", latlng.longitude);
            jPayload.addProperty("to", "/topics/" + DATUM.getId());
            jPayload.addProperty("priority", "high");
            jPayload.add("data", jData);
            presenter.sendFCM(jPayload);
        }

        if (DATUM.getStatus().equalsIgnoreCase("PICKEDUP")) {
            if (DATUM.getIsTrack().equalsIgnoreCase("YES")) {
                HashMap<String, Object> map1 = new HashMap<>();
                map1.put("latitude", latlng.latitude);
                map1.put("longitude", latlng.longitude);
                // presenter.calculateDistance(map1, DATUM.getId());
                serverlatlng = location;
            }
        }

    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

    private void showSnack(boolean isConnected) {
        if (isConnected) {
            lnrNoInternet.setVisibility(View.GONE);
        } else {
            lnrNoInternet.setVisibility(View.VISIBLE);
        }
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }


    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) activity().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Log.d("Location Data",readFromFile(this));
    }

    @OnClick(R.id.btnInstant)
    public void onViewClicked() {
        showInstantOTP();
    }


    private void showInstantOTP() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity());
        LayoutInflater inflater = activity().getLayoutInflater();
        View view = inflater.inflate(R.layout.instant_otp_dialog, null);

        lnrVerification = view.findViewById(R.id.lnrVerification);
        Button btnSubmit = view.findViewById(R.id.submit_btn);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        instantDestination = view.findViewById(R.id.instant_destination);
        estimateFare = view.findViewById(R.id.estimate_fare);

        EditText txtPhoneNumber = view.findViewById(R.id.txtPhoneNumber);
        EditText txtName = view.findViewById(R.id.txtName);
        pinView = view.findViewById(R.id.pinView);

        builder.setView(view);
        otpDialog = builder.create();
        otpDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        instantDestination.setOnClickListener(view13 -> {
            Intent destinationIntent = new Intent(MainActivity.this, LocationPickActivity.class);
            destinationIntent.putExtra("desFocus", true);
            startActivityForResult(destinationIntent, PICK_LOCATION_REQUEST_CODE);
        });

        btnSubmit.setOnClickListener(view12 -> {
            if (lnrVerification.getVisibility() == View.VISIBLE) {
                if (server_otp.equalsIgnoreCase(pinView.getText().toString())) {

                    HashMap<String, Object> map = new HashMap<>();
                    map.put("s_latitude", RIDE_REQUEST.get("s_latitude"));
                    map.put("s_longitude", RIDE_REQUEST.get("s_longitude"));
                    map.put("s_address", RIDE_REQUEST.get("s_address"));
                    map.put("d_latitude", RIDE_REQUEST.get("d_latitude"));
                    map.put("d_longitude", RIDE_REQUEST.get("d_longitude"));
                    map.put("d_address", RIDE_REQUEST.get("d_address"));
                    map.put("user_id", user_id_now);
                    presenter.instantRideSendRequest(map);

                } else {
                    Toast.makeText(activity(), R.string.wrong_otp, Toast.LENGTH_SHORT).show();
                }
            } else {
                if (CommonValidation.Validation(txtName.getText().toString().trim())) {
                    Toasty.error(activity(), "Please enter name", Toast.LENGTH_SHORT, true).show();
                } else if (CommonValidation.Validation(txtPhoneNumber.getText().toString().trim())) {
                    Toasty.error(activity(), getString(R.string.invalid_mobile), Toast.LENGTH_SHORT, true).show();
                } else if (CommonValidation.isValidPhone(txtPhoneNumber.getText().toString().trim())) {
                    Toasty.error(activity(), getString(R.string.validmobile), Toast.LENGTH_SHORT, true).show();
                } else if (RIDE_REQUEST.get("d_address") == null) {
                    Toasty.error(activity(), getString(R.string.invalid_drop_address), Toast.LENGTH_SHORT, true).show();
                } else if (RIDE_REQUEST.get("s_address") == null) {
                    Toasty.error(activity(), getString(R.string.invalid_pickup_address), Toast.LENGTH_SHORT, true).show();
                } else {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("mobile", txtPhoneNumber.getText().toString().trim());
                    map.put("name", txtName.getText().toString().trim());

                    map.put("s_latitude", RIDE_REQUEST.get("s_latitude"));
                    map.put("s_longitude", RIDE_REQUEST.get("s_longitude"));
                    map.put("s_address", RIDE_REQUEST.get("s_address"));
                    map.put("d_latitude", RIDE_REQUEST.get("d_latitude"));
                    map.put("d_longitude", RIDE_REQUEST.get("d_longitude"));
                    map.put("d_address", RIDE_REQUEST.get("d_address"));
                    presenter.instantRideEstimateFare(map);
                }
            }

        });

        btnCancel.setOnClickListener(view1 -> otpDialog.dismiss());
        otpDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_LOCATION_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                if (RIDE_REQUEST.containsKey("d_address")) {
                    instantDestination.setText(String.valueOf(RIDE_REQUEST.get("d_address")));
                } else {
                    instantDestination.setText("");
                }
            }
        }

        if (requestCode == CHATHEAD_OVERLAY_PERMISSION_REQUEST_CODE) {
            showFloatingView(activity(), false);
        }

        //new Handler().postDelayed(() -> isCallCheckStatus = true, 3000);
    }

    public void drawDirectionToStop(String overviewPolyline) {
        if (overviewPolyline != null) {
            googleMap.clear();
            PolyUtils polyUtils = new PolyUtils(this, googleMap, overviewPolyline);
            polyUtils.start();
        }
    }


    @SuppressLint("NewApi")
    private void showFloatingView(Context context, boolean isShowOverlayPermission) {
        // API22以下かチェック
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
            startFloatingViewService(activity());
            return;
        }

        if (Settings.canDrawOverlays(context)) {
            startFloatingViewService(activity());
            return;
        }

        if (isShowOverlayPermission) {
            final Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + context.getPackageName()));
            startActivityForResult(intent, CHATHEAD_OVERLAY_PERMISSION_REQUEST_CODE);
        }
    }


    private static void startFloatingViewService(Activity activity) {
        // *** You must follow these rules when obtain the cutout(FloatingViewManager.findCutoutSafeArea) ***
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // 1. 'windowLayoutInDisplayCutoutMode' do not be set to 'never'
            if (activity.getWindow().getAttributes().layoutInDisplayCutoutMode == WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER) {
                throw new RuntimeException("'windowLayoutInDisplayCutoutMode' do not be set to 'never'");
            }
            // 2. Do not set Activity to landscape
            if (activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                throw new RuntimeException("Do not set Activity to landscape");
            }
        }

        final Class<? extends Service> service;
        service = ChatHeadService.class;

        final Intent intent = new Intent(activity, service);
        //intent.putExtra(ChatHeadService.EXTRA_CUTOUT_SAFE_AREA, FloatingViewManager.findCutoutSafeArea(activity));
        ContextCompat.startForegroundService(activity, intent);
    }

    @Override
    public void onBackPressed() {
        MainActivity.this.moveTaskToBack(true);
    }

    public void getProfile() {
        presenter.getProfile();
    }
}
