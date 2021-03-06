package com.eaglecabs.provider.ui.fragment.status_flow;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chaos.view.PinView;
import com.eaglecabs.provider.BuildConfig;
import com.eaglecabs.provider.MyBackgroundLocationService;
import com.eaglecabs.provider.R;
import com.eaglecabs.provider.base.BaseActivity;
import com.eaglecabs.provider.base.BaseFragment;
import com.eaglecabs.provider.common.ConnectivityReceiver;
import com.eaglecabs.provider.common.SharedHelper;
import com.eaglecabs.provider.common.Utilities;
import com.eaglecabs.provider.common.chat.ChatActivity;
import com.eaglecabs.provider.data.network.model.Request_;
import com.eaglecabs.provider.data.network.model.UserGetResponse;
import com.eaglecabs.provider.ui.activity.main.MainActivity;
import com.eaglecabs.provider.ui.bottomsheetdialog.cancel.CancelDialogFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.Executor;

import br.com.safety.locationlistenerhelper.core.AppUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.eaglecabs.provider.MvpApplication.DEFAULT_ZOOM;
import static com.eaglecabs.provider.MvpApplication.mLastKnownLocation;
import static com.eaglecabs.provider.base.BaseActivity.DATUM;
import static com.eaglecabs.provider.base.BaseActivity.TRIPDISTANCE;
import static com.eaglecabs.provider.common.fcm.MyFirebaseMessagingService.INTENT_FILTER;

public class StatusFlowFragment extends BaseFragment implements StatusFlowIView {


    StatusFlowPresenter<StatusFlowFragment> presenter = new StatusFlowPresenter<>();
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_rating)
    RatingBar userRating;
    @BindView(R.id.imgCall)
    ImageView imgCall;
    @BindView(R.id.btnCancel)
    Button btnCancel;
    @BindView(R.id.btnStatus)
    Button btnStatus;
    @BindView(R.id.status_arrived_img)
    CircleImageView statusArrivedImg;
    @BindView(R.id.status_picked_up_img)
    CircleImageView statusPickedUpImg;
    @BindView(R.id.status_finished_img)
    CircleImageView statusFinishedImg;
    @BindView(R.id.user_img)
    CircleImageView userImg;
    @BindView(R.id.imgMsg)
    ImageView imgMsg;
    Unbinder unbinder;
    Request_ data = null;
    AlertDialog otpDialog;
    AlertDialog KmDialog;
    String STATUS = "";
    Context thisContext;

    MainActivity mActivity;
    private String OTP_STATUS = "";

    @Override
    public int getLayoutId() {
        return R.layout.fragment_status_flow;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mActivity = (MainActivity) getActivity();
    }

    @Override
    public View initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        presenter.attachView(this);
        this.thisContext = getContext();
        init();
        return view;
    }

    private void init() {
        data = BaseActivity.DATUM;
        if (data != null) {
            Utilities.printV("data===>", data.getStatus());
            changeFlow(data.getStatus());


            /*if (data.getSLatitude() != null && data.getSLongitude() != null && data.getDLatitude() != null && data.getDLongitude() != null) {
                LatLng origin = new LatLng(data.getSLatitude(), data.getSLongitude());
                LatLng destination = new LatLng(data.getDLatitude(), data.getDLongitude());
                ((MainActivity) thisContext).drawRoute(origin, destination);
            }*/
        }
    }

    @OnClick({R.id.imgCall, R.id.btnCancel, R.id.btnStatus, R.id.imgMsg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgCall:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + data.getUser().getMobile()));
                startActivity(intent);
                break;
            case R.id.btnCancel:
                SharedHelper.putKey(thisContext, "cancel_id", String.valueOf(data.getId()));
                cancelRequestPopup();
                break;
            case R.id.btnStatus:
                if (STATUS.equalsIgnoreCase("PICKEDUP")) {

                    if (data.getOtp() != null) {
                        showOTP();
                    } else {
                        statusUpdateCall(STATUS);
                    }
                } else if (STATUS.equalsIgnoreCase("DROPPED")) {
                    //getDeviceLocation();
                    confirmPopup();
                } else {
                    statusUpdateCall(STATUS);
                }
                break;
            case R.id.imgMsg:
                if (DATUM != null) {
                    Intent i = new Intent(thisContext, ChatActivity.class);
                    i.putExtra("request_id", String.valueOf(DATUM.getId()));
                    i.putExtra("user_id", String.valueOf(DATUM.getUserId()));
                    startActivity(i);
                }
                break;
        }
    }



    private void getDistance(double currentLat2, double currentLong2, double mallLat2, double mallLong2) {
        if (ConnectivityReceiver.isConnected()) {
            new AsyncTask<Void,Void,Double>() {
                @Override
                protected Double doInBackground(Void... voids) {

                    Location loc1 = new Location("");
                    loc1.setLatitude(currentLat2);
                    loc1.setLongitude(currentLong2);

                    Location loc2 = new Location("");
                    loc2.setLatitude(mallLat2);
                    loc2.setLongitude(mallLong2);

                    return Double.valueOf(loc1.distanceTo(loc2));




//                   double theta = lon1 - lon2;
//                   double dist = Math.sin(deg2rad(lat1))
//                           * Math.sin(deg2rad(lat2))
//                           + Math.cos(deg2rad(lat1))
//                           * Math.cos(deg2rad(lat2))
//                           * Math.cos(deg2rad(theta));
//                   dist = Math.acos(dist);
//                   dist = rad2deg(dist);
//                   dist = dist * 60 * 1.1515;
//                   return dist;
                }

                @Override
                protected void onPostExecute(Double dist) {
                    super.onPostExecute(dist);
                    if (dist>300){
                        TRIPDISTANCE = TRIPDISTANCE+dist;
                    }

                    //SharedHelper.putKey(MainActivity.this, "totalDist", String.valueOf(TRIPDISTANCE));

                }
            }.execute();
        }

    }

    public void changeFlow(String status) {

        btnCancel.setVisibility(View.GONE);
        if (data.getUser() != null) {
            UserGetResponse user = data.getUser();
            userName.setText(user.getFirstName() + " " + user.getLastName());
            userRating.setRating(Float.parseFloat(user.getRating()));
            Glide.with(thisContext).load(BuildConfig.BASE_IMAGE_URL + data.getUser().getPicture()).apply(RequestOptions.placeholderOf(R.drawable.user).dontAnimate().error(R.drawable.user)).into(userImg);
        }


        switch (status) {
            case "ACCEPTED":
                btnStatus.setText("ARRIVED");
                btnCancel.setVisibility(View.VISIBLE);
                STATUS = "STARTED";

                if (data != null) {
                    LatLng origin = new LatLng(data.getSLatitude(), data.getSLongitude());
                    LatLng providerLat = new LatLng(Double.valueOf(SharedHelper.getKey(activity(), "current_latitude")), Double.valueOf(SharedHelper.getKey(activity(), "current_longitude")));
                    ((MainActivity) Objects.requireNonNull(getActivity())).drawRoute(providerLat, origin);
                }

                break;
            case "STARTED":
                btnStatus.setText("ARRIVED");
                btnCancel.setVisibility(View.VISIBLE);
                STATUS = "ARRIVED";
                if (data != null) {
                    LatLng origin = new LatLng(data.getSLatitude(), data.getSLongitude());
                    LatLng providerLat = new LatLng(Double.valueOf(SharedHelper.getKey(activity(), "current_latitude")), Double.valueOf(SharedHelper.getKey(activity(), "current_longitude")));
                    ((MainActivity) Objects.requireNonNull(getActivity())).drawRoute(providerLat, origin);
                }
                break;
            case "ARRIVED":
                if (data != null) {
                    ((MainActivity) thisContext).drawDirectionToStop(data.getRouteKey());
                }
                btnStatus.setText("PICKEDUP");
                btnCancel.setVisibility(View.VISIBLE);
                STATUS = "PICKEDUP";
                mActivity.TRIPDISTANCE = 0.0;
                statusArrivedImg.setImageResource(R.drawable.arrived_select);
                statusPickedUpImg.setImageResource(R.drawable.pickup);
                statusFinishedImg.setImageResource(R.drawable.finished);
                break;
            case "PICKEDUP":
                if (data != null) {
                    ((MainActivity) thisContext).drawDirectionToStop(data.getRouteKey());
                }
                btnStatus.setText("END TRIP");
                STATUS = "DROPPED";
                statusArrivedImg.setImageResource(R.drawable.arrived);
                statusPickedUpImg.setImageResource(R.drawable.pickup_select);
                statusFinishedImg.setImageResource(R.drawable.finished);
                break;
            default:
                break;
        }
    }


    void confirmPopup() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity());
        alertDialogBuilder
                .setMessage("Are you sure want to drop?")
                .setCancelable(false)
                .setPositiveButton("YES", (dialog, id) -> {
                    if (!data.getServiceRequired().equalsIgnoreCase("outstation"))
                        statusUpdateCall(STATUS);
                    else showTotalKmDialog();
                })
                .setNegativeButton("NO", (dialog, id) -> dialog.cancel());
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    private void showTotalKmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity());
        LayoutInflater inflater = activity().getLayoutInflater();
        View view = inflater.inflate(R.layout.total_km_dialog, null);

        Button btnSubmit = view.findViewById(R.id.submit_btn);
        Button btnCancel = view.findViewById(R.id.btnCancel);


        EditText txtTotalKm = view.findViewById(R.id.txtKM);


        builder.setView(view);
        KmDialog = builder.create();
        KmDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        btnSubmit.setOnClickListener(view12 -> {
            if (!txtTotalKm.getText().toString().isEmpty()) {
                KmDialog.dismiss();
                statusUpdateCallOutstation(STATUS, txtTotalKm.getText().toString());
            } else
                Toast.makeText(activity(), "Enter your total kilometre you are travelled", Toast.LENGTH_SHORT).show();

        });

        btnCancel.setOnClickListener(view12 -> KmDialog.dismiss());
        KmDialog.show();
    }

    @Override
    public void onSuccess(Object object) {
        getActivity().getSupportFragmentManager().beginTransaction().remove(StatusFlowFragment.this).commit();
        mActivity.getTripDetails(false);

        Intent intent = new Intent(INTENT_FILTER);
        thisContext.sendBroadcast(intent);
    }


    @Override
    public void onError(Throwable e)
    {
        hideLoading();
    }


    void statusUpdateCall(String status) {
        if (DATUM != null) {
            Request_ datum = DATUM;
            HashMap<String, Object> map = new HashMap<>();
            map.put("status", status);
            map.put("_method", "PATCH");
            if (status.equalsIgnoreCase("DROPPED")) {
                if (DATUM.getSLatitude() > 0) {
                    Geocoder geocoder = new Geocoder(mActivity, Locale.getDefault());
                    try {
                        List<Address> addressesSS = geocoder.getFromLocation(DATUM.getSLatitude(), DATUM.getSLongitude(), 1);
                        if (addressesSS != null && addressesSS.size() > 0) {
                            String stateName = addressesSS.get(0).getAdminArea();
                            map.put("sState", stateName);

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        List<Address> addressesDD = geocoder.getFromLocation(SharedHelper.getDoubleKey(mActivity, "lastLat"), SharedHelper.getDoubleKey(mActivity, "lastLong"), 1);
                        if (addressesDD != null && addressesDD.size() > 0) {
                            String dState = addressesDD.get(0).getAdminArea();
                            map.put("dState", dState);

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            //    SharedHelper.putKey(mActivity, "tripDistance",57000);

                if (SharedHelper.getDoubleKey(mActivity, "tripDistance") > 0) {
                    map.put("ride_distance",SharedHelper.getDoubleKey(mActivity, "tripDistance")/1000);
                    //AppUtils.writeToFile(" Total Distance in KM :"+map.get("ride_distance"),mActivity);

                   // Toast.makeText(mActivity, "Distance:"+map.get("ride_distance"), Toast.LENGTH_SHORT).show();

                    SharedHelper.putKey(mActivity, "TotalD",""+map.get("ride_distance"));

                    SharedHelper.putKey(mActivity, "tripStatus", "");
                    SharedHelper.putKey(mActivity, "tripDistance", 0.0f);

                }else{
                    map.put("ride_distance",0.0);

                    SharedHelper.putKey(mActivity, "tripDistance", 0.0f);
                    //Toast.makeText(mActivity, "Distance Failed:", Toast.LENGTH_SHORT).show();

                }
                map.put("latitude", SharedHelper.getKey(activity(), "current_latitude"));
                map.put("longitude", SharedHelper.getKey(activity(), "current_longitude"));
                map.put("address", getAddress(new LatLng(Double.parseDouble(SharedHelper.getKey(activity(), "current_latitude")), Double.parseDouble(SharedHelper.getKey(activity(), "current_longitude")))));
            }
            presenter.statusUpdate(map, datum.getId());
            TRIPDISTANCE = 0.0;
            mActivity.SERVICE_STATUS = "no";


        }

    }

    void statusUpdateCallOutstation(String status, String km) {
        if (DATUM != null) {
            Request_ datum = DATUM;
            HashMap<String, Object> map = new HashMap<>();
            map.put("status", status);
            map.put("_method", "PATCH");
            map.put("driver_end_distance", km);
            if (status.equalsIgnoreCase("DROPPED")) {

                map.put("latitude", SharedHelper.getKey(activity(), "current_latitude"));
                map.put("longitude", SharedHelper.getKey(activity(), "current_longitude"));
                map.put("address", getAddress(new LatLng(Double.parseDouble(SharedHelper.getKey(activity(), "current_latitude")), Double.parseDouble(SharedHelper.getKey(activity(), "current_longitude")))));
            }
            presenter.statusUpdate(map, datum.getId());
        }

    }


    void cancelRequestPopup() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(thisContext);
        alertDialogBuilder
                .setMessage("Are you sure want to Cancel this request?")
                .setCancelable(false)
                .setPositiveButton("YES", (dialog, id) -> {
                    CancelDialogFragment cancelDialogFragment = new CancelDialogFragment();
                    cancelDialogFragment.show(getChildFragmentManager(), cancelDialogFragment.getTag());
                })
                .setNegativeButton("NO", (dialog, id) -> dialog.cancel());
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    private void showOTP() {
        AlertDialog.Builder builder = new AlertDialog.Builder(thisContext);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.otp_dialog, null);

        Button submitBtn = view.findViewById(R.id.submit_btn);
        final PinView pinView = view.findViewById(R.id.pinView);

        builder.setView(view);
        otpDialog = builder.create();
        otpDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        submitBtn.setOnClickListener(view1 -> {
            if (data.getOtp().equalsIgnoreCase(Objects.requireNonNull(pinView.getText()).toString())) {
                Toast.makeText(thisContext, "OTP Verified!", Toast.LENGTH_SHORT).show();
                OTP_STATUS = "verified";
                statusUpdateCall(STATUS);

                otpDialog.dismiss();
            } else {
                Toast.makeText(thisContext, "Wrong OTP!", Toast.LENGTH_SHORT).show();
            }
        });
        otpDialog.show();
    }

    public String getAddress(LatLng currentLocation) {
        String address = null;
        try {
            Geocoder geocoder = new Geocoder(activity(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(currentLocation.latitude, currentLocation.longitude, 1);
            if ((addresses != null) && !addresses.isEmpty()) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder();
                if (returnedAddress.getMaxAddressLineIndex() > 0) {
                    for (int j = 0; j < returnedAddress.getMaxAddressLineIndex(); j++) {
                        strReturnedAddress.append(returnedAddress.getAddressLine(j));
                    }
                } else {
                    strReturnedAddress.append(returnedAddress.getAddressLine(0));
                }
                address = strReturnedAddress.toString();
            }
        } catch (Exception e) {
            Log.e("MAP", "getAddress: " + e);
        }
        return address;
    }
    void getDeviceLocation() {
        try {
                Task<Location> locationResult = mActivity.mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(mActivity, task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        mLastKnownLocation = task.getResult();
                        getDistance(mLastKnownLocation.getLatitude(),mLastKnownLocation.getLatitude() ,
                               Double.valueOf( SharedHelper.getKey(mActivity, "current_latitude")),
                               Double.valueOf(SharedHelper.getKey(mActivity, "current_longitude")));

                    }
                });

        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

}
