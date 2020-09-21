package com.eaglecabs.provider.ui.bottomsheetdialog.cancel;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eaglecabs.provider.R;
import com.eaglecabs.provider.base.BaseBottomSheetDialogFragment;
import com.eaglecabs.provider.common.SharedHelper;

import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;

import static com.eaglecabs.provider.common.fcm.MyFirebaseMessagingService.INTENT_FILTER;

public class CancelDialogFragment extends BaseBottomSheetDialogFragment implements CancelDialogIView {


    @BindView(R.id.txtComments)
    EditText comments;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    Unbinder unbinder;

    CancelDialogPresenter<CancelDialogFragment> presenter = new CancelDialogPresenter<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_cancel;
    }

    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        presenter.attachView(this);

    }


    @OnClick(R.id.btnSubmit)
    public void onViewClicked() {
        int cancelReasonLength = comments.getText().length();

        if (comments.getText().toString().isEmpty()) {

            Toast.makeText(getContext(), "Please enter cancel reason", Toast.LENGTH_SHORT).show();
        } else if (cancelReasonLength > 15) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", SharedHelper.getKey(Objects.requireNonNull(getContext()), "cancel_id"));
            map.put("cancel_reason", comments.getText().toString());
            showLoading();
            presenter.cancelRequest(map);

        } else {
            Toast.makeText(getContext(), "Please enter min 15 character reason  for canceling. ", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onSuccessCancel(Object object) {

        dismissAllowingStateLoss();
        hideLoading();

        Intent intent = new Intent(INTENT_FILTER);
        activity().sendBroadcast(intent);


    }

    @Override
    public void onError(Throwable e) {
        hideLoading();

        //Log.d("Cancel", "cancel");
        Toast.makeText(activity(), "Cannot cancel request at this stage!", Toast.LENGTH_SHORT).show();

    }


}
