package com.eaglecabs.provider.ui.activity.profile;


import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.eaglecabs.provider.BuildConfig;
import com.eaglecabs.provider.MvpApplication;
import com.eaglecabs.provider.R;
import com.eaglecabs.provider.base.BaseActivity;
import com.eaglecabs.provider.common.SharedHelper;
import com.eaglecabs.provider.common.Utilities;
import com.eaglecabs.provider.data.network.model.Fleet;
import com.eaglecabs.provider.data.network.model.Service;
import com.eaglecabs.provider.data.network.model.User;
import com.eaglecabs.provider.ui.activity.change_password.ChangePasswordActivtiy;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.eaglecabs.provider.common.Constants.MULTIPLE_PERMISSION;
import static com.eaglecabs.provider.common.Constants.RC_MULTIPLE_PERMISSION_CODE;

public class ProfileActivity extends BaseActivity implements ProfileIView, EasyPermissions.PermissionCallbacks {

    @BindView(R.id.imgProfile)
    CircleImageView imgProfile;
    @BindView(R.id.txtFirstName)
    EditText txtFirstName;
    @BindView(R.id.txtLastName)
    EditText txtLastName;
    @BindView(R.id.txtPhoneNumber)
    EditText txtPhoneNumber;
    @BindView(R.id.txtEmail)
    EditText txtEmail;
    @BindView(R.id.txtService)
    EditText txtService;
    @BindView(R.id.btnSave)
    Button btnSave;
    @BindView(R.id.lblChangePassword)
    TextView lblChangePassword;
    @BindView(R.id.emergency_mobile1)
    EditText emergencyMobile1;
    @BindView(R.id.emergency_mobile2)
    EditText emergencyMobile2;
    @BindView(R.id.fleet_layout)
    LinearLayout fleetLayout;
    @BindView(R.id.fleet)
    EditText fleet;

    @BindView(R.id.dailyRidesCHK)
    CheckBox dailyRidesCHK;
    @BindView(R.id.rentalsRidesCHK)
    CheckBox rentalsRidesCHK;
    @BindView(R.id.outStationRidesCHK)
    CheckBox outStationRidesCHK;

    Set<Integer> serviceType = new HashSet<>();
    File imgFile = null;
    ProfilePresenter<ProfileActivity> presenter = new ProfilePresenter<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_profile;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        presenter.attachView(this);
        presenter.getProfile();
        setTitle(R.string.profile);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @OnClick({R.id.btnSave, R.id.lblChangePassword, R.id.imgProfile})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSave:
                profileUpdate();
                break;
            case R.id.lblChangePassword:
                startActivity(new Intent(ProfileActivity.this, ChangePasswordActivtiy.class));
                break;
            case R.id.imgProfile:
                MultiplePermissionTask();
                break;
        }
    }


    void profileUpdate() {
        String serviceTypes = null;
        StringBuilder builder = new StringBuilder();

        if (serviceType.size() > 0) {
            Iterator<Integer> it = serviceType.iterator();
            while (it.hasNext()) {
                builder.append(it.next() + ",");
            }


            serviceTypes = builder.substring(0, builder.length() - 1);


        } else {
            Toasty.success(this, "Please select at least 1 service type!", Toast.LENGTH_SHORT, true).show();
            return;
        }


        Map<String, RequestBody> map = new HashMap<>();
        map.put("first_name", toRequestBody(txtFirstName.getText().toString()));
        map.put("last_name", toRequestBody(txtLastName.getText().toString()));
        map.put("email", toRequestBody(txtEmail.getText().toString()));
        map.put("mobile", toRequestBody(txtPhoneNumber.getText().toString()));
        map.put("emergency_contact1", toRequestBody(emergencyMobile1.getText().toString()));
        map.put("emergency_contact2", toRequestBody(emergencyMobile2.getText().toString()));
        map.put("service_type", toRequestBody(serviceTypes));

        MultipartBody.Part filePart = null;
        if (imgFile != null)
            filePart = MultipartBody.Part.createFormData("avatar", imgFile.getName(), RequestBody.create(MediaType.parse("image*//*"), imgFile));

        showLoading();
        presenter.profileUpdate(map, filePart);
    }


    @Override
    public void onSuccess(User user) {
        hideLoading();

        Utilities.printV("User===>", user.getFirstName() + user.getLastName());

        Utilities.printV("TOKEN===>", SharedHelper.getKey(MvpApplication.getInstance(), "access_token", ""));

        txtFirstName.setText(user.getFirstName());
        txtLastName.setText(user.getLastName());
        txtPhoneNumber.setText(user.getMobile());
        txtEmail.setText(user.getEmail());
        emergencyMobile1.setText(user.getEmergencyContact1());
        emergencyMobile2.setText(user.getEmergencyContact2());
        txtService.setText("");

        if (!TextUtils.isEmpty(user.getService_type()) && user.getService_type().length() > 0) {
            String types[] = user.getService_type().split(",");
            dailyRidesCHK.setChecked(false);
            rentalsRidesCHK.setChecked(false);
            outStationRidesCHK.setChecked(false);

            if (types.length > 0) {
                for (String type : types) {
                    if (type.equalsIgnoreCase( "1")) {
                        dailyRidesCHK.setChecked(true);
                        serviceType.add(1);
                    } else if (type.equalsIgnoreCase( "2")) {
                        rentalsRidesCHK.setChecked(true);
                        serviceType.add(2);

                    } else if (type.equalsIgnoreCase( "3")) {
                        outStationRidesCHK.setChecked(true);
                        serviceType.add(3);

                    }
                }
            }
        }


        Fleet fleet = user.getFleet();
        fleetLayout.setVisibility(fleet == null ? View.GONE : View.VISIBLE);
        if (fleet != null) {
            this.fleet.setText(fleet.getCompany());
            SharedHelper.putKey(activity(), "fleet_id", String.valueOf(fleet.getId()));
            if (user.getProviderVehicle() != null) {
                SharedHelper.putKey(activity(), "fleet_vehicle_id", String.valueOf(user.getProviderVehicle().getFleetVehicleId()));
            }
        }

        for (Service service : user.getService()) {
            txtService.append(service.getServiceType().getName() + ", ");
        }
        SharedHelper.putKey(this, "user_avatar", BuildConfig.BASE_IMAGE_URL + user.getAvatar());
        SharedHelper.putKey(this, "user_name", user.getFirstName() + " " + user.getLastName());
        Glide.with(activity()).load(BuildConfig.BASE_IMAGE_URL + user.getAvatar()).apply(RequestOptions.placeholderOf(R.drawable.user).dontAnimate().error(R.drawable.user)).into(imgProfile);
        //Toast.makeText(activity(), "Profile updated successfully", Toast.LENGTH_SHORT).show();

        dailyRidesCHK.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    serviceType.add(1);
                } else {
                    serviceType.remove(1);
                }
            }
        });

        rentalsRidesCHK.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    serviceType.add(2);
                } else {
                    serviceType.remove(2);
                }
            }
        });


        outStationRidesCHK.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    serviceType.add(3);
                } else {
                    serviceType.remove(3);
                }
            }
        });

    }


    @Override
    public void onSuccessUser(User user) {
        hideLoading();
        Toasty.success(this, "Profile Updated!", Toast.LENGTH_SHORT, true).show();
        SharedHelper.putKey(this, "user_avatar", BuildConfig.BASE_IMAGE_URL + user.getAvatar());
        SharedHelper.putKey(this, "user_name", user.getFirstName() + " " + user.getLastName());
        finish();
    }

    @Override
    public void onError(Throwable e) {
        hideLoading();
        finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, ProfileActivity.this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                e.printStackTrace();
            }

            @Override
            public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {
                try {
                    imgFile = new Compressor(activity()).compressToFile(imageFiles.get(0));
                    Glide.with(activity()).load(Uri.fromFile(imgFile))
                            .apply(RequestOptions.placeholderOf(R.drawable.user)
                                    .override(100, 100)
                                    .dontAnimate().error(R.drawable.user)).into(imgProfile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {

            }
        });
    }

    private boolean hasMultiplePermission() {
        return EasyPermissions.hasPermissions(this, MULTIPLE_PERMISSION);
    }


    @AfterPermissionGranted(RC_MULTIPLE_PERMISSION_CODE)
    void MultiplePermissionTask() {
        if (hasMultiplePermission()) {
            pickImage();
        } else {
            EasyPermissions.requestPermissions(
                    this, "Please Accept All the Permission!",
                    RC_MULTIPLE_PERMISSION_CODE,
                    MULTIPLE_PERMISSION);
        }

    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


}
