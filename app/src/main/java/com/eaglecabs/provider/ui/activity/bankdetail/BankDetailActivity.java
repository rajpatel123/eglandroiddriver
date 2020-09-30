package com.eaglecabs.provider.ui.activity.bankdetail;


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
import android.widget.ImageView;
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
import com.eaglecabs.provider.ui.activity.profile.ProfileIView;
import com.eaglecabs.provider.ui.activity.profile.ProfilePresenter;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class BankDetailActivity extends BaseActivity implements BankIView, EasyPermissions.PermissionCallbacks {

    @BindView(R.id.imgProfile)
    ImageView imgProfile;
    @BindView(R.id.txtAccountNumber)
    EditText txtAccountNumber;

    @BindView(R.id.txtACHolderName)
    EditText txtACHolderName;

    @BindView(R.id.txtbankName)
    EditText txtbankName;

    @BindView(R.id.checkHint)
    TextView checkHint;

    @BindView(R.id.txtIFSC)
    EditText txtIFSC;


    @BindView(R.id.btnSave)
    Button btnSave;
    File imgFile = null;
    BankPresenter<BankDetailActivity> presenter = new BankPresenter<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_bank;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        presenter.attachView(this);
      presenter.getBank();
        setTitle(R.string.bank);


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
                startActivity(new Intent(BankDetailActivity.this, ChangePasswordActivtiy.class));
                break;
            case R.id.imgProfile:
                MultiplePermissionTask();
                break;
        }
    }


    void profileUpdate() {
  String acNumber =  txtAccountNumber.getText().toString();
  String uname =  txtACHolderName.getText().toString();
  String bname =  txtbankName.getText().toString();
  String ifscCode =  txtIFSC.getText().toString();
       if (imgFile!=null){
           if (!TextUtils.isEmpty(acNumber) && acNumber.length()>=9 && acNumber.length()<=18 ){
               if (!TextUtils.isEmpty(uname)){
                   if (!TextUtils.isEmpty(bname)){
                       if (!TextUtils.isEmpty(ifscCode) && validateIfsc(ifscCode)) {
                           HashMap<String, RequestBody> map = new HashMap<>();
                           map.put("b_account", toRequestBody(acNumber));
                           map.put("u_name", toRequestBody(uname));
                           map.put("b_name", toRequestBody(bname));
                           map.put("b_ifsc_code", toRequestBody(ifscCode));


                           MultipartBody.Part filePart = null;
                           if (imgFile != null)
                               filePart = MultipartBody.Part.createFormData("cheque_url", imgFile.getName(), RequestBody.create(MediaType.parse("image*//*"), imgFile));

                           showLoading();
                           presenter.bankDetailUpdate(map, filePart);
                       }else{
                           Toast.makeText(this, "Please enter valid ifsc code", Toast.LENGTH_SHORT).show();

                       }

                   }else{
                       Toast.makeText(this, "Please enter bank name", Toast.LENGTH_SHORT).show();

                   }
               }else{
                   Toast.makeText(this, "Please enter account holder name", Toast.LENGTH_SHORT).show();
               }
           }else{
               Toast.makeText(this, "Please enter valid account number", Toast.LENGTH_SHORT).show();
           }
       }else{
           Toast.makeText(this, "Please upload clear image of cancelled cheque", Toast.LENGTH_SHORT).show();

       }



    }

    private boolean validateIfsc(String ifscCode) {
            // Regex to check valid IFSC Code.
            String regex
                    = "^[A-Z]{4}0[A-Z0-9]{6}$";

            // Compile the ReGex
            Pattern p
                    = Pattern.compile(regex);

            // If the string is empty
            // return false
            if (ifscCode == null) {
                return false;
            }

            // Pattern class contains matcher()
            // method to find matching between
            // the given string and
            // the regular expression.
            Matcher m = p.matcher(ifscCode);

            // Return if the string
            // matched the ReGex
            return m.matches();
        }


    @Override
    public void onSuccess(User user) {
        hideLoading();

        if (user!=null && user.getB_account()<1){
            return;
        }else{
            txtAccountNumber.setEnabled(false);
            txtACHolderName.setEnabled(false);
            txtbankName.setEnabled(false);
            txtIFSC.setEnabled(false);
            imgProfile.setEnabled(false);
        }
        Utilities.printV("TOKEN===>", SharedHelper.getKey(MvpApplication.getInstance(), "access_token", ""));

        txtAccountNumber.setText(""+user.getB_account());
        txtACHolderName.setText(user.getBuser_name());
        txtbankName.setText(user.getB_name());
        txtIFSC.setText(user.getB_ifsc_code());
        checkHint.setVisibility(View.GONE);

        //tx.setText(user.getBName());


//        SharedHelper.putKey(this, "user_avatar", BuildConfig.BASE_IMAGE_URL + user.getAvatar());
//        SharedHelper.putKey(this, "user_name", user.getFirstName() + " " + user.getLastName());
        Glide.with(activity()).load(BuildConfig.BASE_IMAGE_URL + user.getCheque_url()).apply(RequestOptions.placeholderOf(R.drawable.user).dontAnimate().error(R.drawable.chk)).into(imgProfile);
        //Toast.makeText(activity(), "Profile updated successfully", Toast.LENGTH_SHORT).show();


    }


    @Override
    public void onSuccessBank(BankDetails user) {
        hideLoading();
        Toasty.success(this, "Profile Updated!", Toast.LENGTH_SHORT, true).show();
      //  SharedHelper.putKey(this, "user_avatar", BuildConfig.BASE_IMAGE_URL + user.getAvatar());
       // SharedHelper.putKey(this, "user_name", user.getFirstName() + " " + user.getLastName());
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

        EasyImage.handleActivityResult(requestCode, resultCode, data, BankDetailActivity.this, new DefaultCallback() {
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
