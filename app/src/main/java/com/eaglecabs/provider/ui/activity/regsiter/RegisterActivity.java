package com.eaglecabs.provider.ui.activity.regsiter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.eaglecabs.provider.BuildConfig;
import com.eaglecabs.provider.R;
import com.eaglecabs.provider.base.BaseActivity;
import com.eaglecabs.provider.common.CommonValidation;
import com.eaglecabs.provider.common.SharedHelper;
import com.eaglecabs.provider.data.network.model.MyOTP;
import com.eaglecabs.provider.data.network.model.User;
import com.eaglecabs.provider.ui.activity.main.MainActivity;
import com.eaglecabs.provider.ui.activity.otp.OTPActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.HttpException;

import static com.eaglecabs.provider.common.Constants.APP_REQUEST_CODE;

public class RegisterActivity extends BaseActivity implements RegisterIView {


    private static final String TAG = "RegisterActivity";
    private static final int PICK_OTP_VERIFY = 222;

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtEmail)
    EditText txtEmail;
    @BindView(R.id.txtFirstName)
    EditText txtFirstName;
    @BindView(R.id.txtLastName)
    EditText txtLastName;
    @BindView(R.id.txtPhoneNumber)
    EditText txtPhoneNumber;
    @BindView(R.id.chkTerms)
    CheckBox chkTerms;
    @BindView(R.id.lblTerms)
    TextView lblTerms;
    @BindView(R.id.mobile_layout)
    LinearLayout mobile_layout;
    @BindView(R.id.registration_layout)
    LinearLayout registration_layout;
    @BindView(R.id.contact_1)
    EditText contact1;
    @BindView(R.id.contact_2)
    EditText contact2;

    @BindView(R.id.dial_code)
    Spinner dialCode;

    RegisterPresenter<RegisterActivity> presenter= new RegisterPresenter<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        presenter.attachView(this);
        registration_layout.setVisibility(View.GONE);
    }


    private boolean validation() {


//        if (txtEmail.getText().toString().isEmpty()) {
//            Toasty.error(this, getString(R.string.invalid_email), Toast.LENGTH_SHORT, true).show();
//            return false;
//        }
        if (CommonValidation.Validation(txtPhoneNumber.getText().toString().trim())) {
            Toasty.error(this, getString(R.string.invalid_mobile), Toast.LENGTH_SHORT, true).show();
            return false;
        } else if (CommonValidation.isValidPhone(txtPhoneNumber.getText().toString().trim())) {
            Toasty.error(this, getString(R.string.validmobile), Toast.LENGTH_SHORT, true).show();
            return false;
        } else if (CommonValidation.Validation(txtFirstName.getText().toString().trim())) {
            Toasty.error(this, getString(R.string.invalid_first_name), Toast.LENGTH_SHORT, true).show();
            return false;
        } else if (CommonValidation.Validation(txtLastName.getText().toString().trim())) {
            Toasty.error(this, getString(R.string.invalid_last_name), Toast.LENGTH_SHORT, true).show();
            return false;
        }/* else if (txtPassword.getText().toString().length() < 6) {
            Toasty.error(this, getString(R.string.invalid_password_length), Toast.LENGTH_SHORT, true).show();
            return false;
        } else if (txtConfirmPassword.getText().toString().isEmpty()) {
            Toasty.error(this, getString(R.string.invalid_confirm_password), Toast.LENGTH_SHORT, true).show();
            return false;
        } else if (!txtPassword.getText().toString().equals(txtConfirmPassword.getText().toString())) {
            Toasty.error(this, getString(R.string.password_should_be_same), Toast.LENGTH_SHORT, true).show();
            return false;
        } else if (!chkTerms.isChecked()) {
            Toasty.error(this, "Please Accept Terms and Conditions", Toast.LENGTH_SHORT, true).show();
            return false;

        }*/ else {
            return true;
        }


    }


    void register() {

        //All the String parameters, you have to put like
        Map<String, RequestBody> map = new HashMap<>();

        map.put("first_name", toRequestBody(txtFirstName.getText().toString()));
        map.put("last_name", toRequestBody(txtLastName.getText().toString()));
        //map.put("email", toRequestBody(txtEmail.getText().toString()));
        map.put("mobile", toRequestBody(txtPhoneNumber.getText().toString()));
        map.put("password", toRequestBody("123456"));
        map.put("password_confirmation", toRequestBody("123456"));
        map.put("emergency_contact1", toRequestBody(contact1.getText().toString()));
        map.put("emergency_contact2", toRequestBody(contact2.getText().toString()));
        map.put("device_token", toRequestBody(SharedHelper.getKeyFCM(this, "device_token")));
        map.put("device_id", toRequestBody(SharedHelper.getKeyFCM(this, "device_id")));
        map.put("device_type", toRequestBody(BuildConfig.DEVICE_TYPE));
        map.put("country_code", toRequestBody(String.valueOf(dialCode.getSelectedItem())));
        List<MultipartBody.Part> parts = new ArrayList<>();
        presenter.register(map, parts);
    }


    @OnClick({R.id.next, R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.next:
                if (mobile_layout.getVisibility() == View.VISIBLE) {
                    if (CommonValidation.Validation(txtPhoneNumber.getText().toString().trim())) {
                        Toasty.error(this, getString(R.string.invalid_mobile), Toast.LENGTH_SHORT, true).show();
                        return;
                    } else if (CommonValidation.isValidPhone(txtPhoneNumber.getText().toString().trim())) {
                        Toasty.error(this, getString(R.string.validmobile), Toast.LENGTH_SHORT, true).show();
                        return;
                    }
                    presenter.verifyMobileAlreadyExits(txtPhoneNumber.getText().toString());
                } else {
                    if (CommonValidation.Validation(txtPhoneNumber.getText().toString().trim())) {
                        Toasty.error(this, getString(R.string.invalid_mobile), Toast.LENGTH_SHORT, true).show();
                        return;
                    } else if (CommonValidation.isValidPhone(txtPhoneNumber.getText().toString().trim())) {
                        Toasty.error(this, getString(R.string.validmobile), Toast.LENGTH_SHORT, true).show();
                        return;
                    } else if (CommonValidation.Validation(txtFirstName.getText().toString().trim())) {
                        Toasty.error(this, getString(R.string.invalid_first_name), Toast.LENGTH_SHORT, true).show();
                        return;
                    } else if (CommonValidation.Validation(txtLastName.getText().toString().trim())) {
                        Toasty.error(this, getString(R.string.invalid_last_name), Toast.LENGTH_SHORT, true).show();
                        return;
                    } else if (contact1.getText().toString().isEmpty()) {
                        contact1.setError("Required");
                        return;
                    }
                    register();
                }


                break;
            case R.id.back:
                onBackPressed();
                break;

        }
    }


    @Override
    public void onSuccess(User user) {

        SharedHelper.putKey(this, "user_id", String.valueOf(user.getId()));
        SharedHelper.putKey(this, "access_token", user.getAccessToken());
        SharedHelper.putKey(this, "loggged_in", "true");


        Toasty.success(this, "Registered Successfully!", Toast.LENGTH_SHORT, true).show();
        finishAffinity();
        startActivity(new Intent(this, MainActivity.class));

    }

    @Override
    public void onSuccess(MyOTP otp) {

        Intent intent = new Intent(activity(), OTPActivity.class);
        intent.putExtra("mobile", txtPhoneNumber.getText().toString());
        intent.putExtra("country_code", String.valueOf(dialCode.getSelectedItem()));
        intent.putExtra("otp", String.valueOf(otp.getOtp()));
        startActivityForResult(intent, PICK_OTP_VERIFY);

    }

    @Override
    public void onError(Throwable e) {

        hideLoading();

        HttpException error = (HttpException) e;


        try {

            String errorBody = error.response().errorBody().string();
            JSONObject jObjError = new JSONObject(errorBody);


            if (jObjError.has("email"))
                Toast.makeText(getApplicationContext(), jObjError.optString("email"), Toast.LENGTH_LONG).show();
            if (jObjError.has("error"))
                Toast.makeText(getApplicationContext(), jObjError.optString("error"), Toast.LENGTH_LONG).show();
            if (jObjError.has("message"))
                Toast.makeText(getApplicationContext(), jObjError.optString("error"), Toast.LENGTH_LONG).show();
            if (jObjError.has("mobile"))
                Toast.makeText(activity(), jObjError.optString("mobile"), Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        } catch (Exception e1) {
            Toast.makeText(getApplicationContext(), e1.getMessage(), Toast.LENGTH_LONG).show();
        }


    }


    public void fbPhoneLogin() {

        final Intent intent = new Intent(this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE,
                        AccountKitActivity.ResponseType.TOKEN);

        configurationBuilder.setReadPhoneStateEnabled(true);
        configurationBuilder.setReceiveSMS(true);

        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build());
        startActivityForResult(intent, APP_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == APP_REQUEST_CODE && data != null) {
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                @Override
                public void onSuccess(Account account) {
                    Log.d("AccountKit", "onSuccess: Account Kit" + AccountKit.getCurrentAccessToken().getToken());
                    if (AccountKit.getCurrentAccessToken().getToken() != null) {
                        //SharedHelper.putKey(RegisterActivity.this, "account_kit_token", AccountKit.getCurrentAccessToken().getToken());
                        PhoneNumber phoneNumber = account.getPhoneNumber();
                        SharedHelper.putKey(RegisterActivity.this, "dial_code", phoneNumber.getCountryCode());
                        SharedHelper.putKey(RegisterActivity.this, "mobile", phoneNumber.getPhoneNumber());

                        txtPhoneNumber.setText(SharedHelper.getKey(RegisterActivity.this, "mobile"));

                        register();
                    }
                }

                @Override
                public void onError(AccountKitError accountKitError) {
                    Log.e("AccountKit", "onError: Account Kit" + accountKitError);
                }
            });

        } else if (requestCode == PICK_OTP_VERIFY && resultCode == Activity.RESULT_OK) {
            registration_layout.setVisibility(View.VISIBLE);
            mobile_layout.setVisibility(View.GONE);

            Toast.makeText(this, "Thanks your Mobile is successfully verified, Please enter your First Name and Last Name to create your account", Toast.LENGTH_SHORT).show();
        }
    }


}
