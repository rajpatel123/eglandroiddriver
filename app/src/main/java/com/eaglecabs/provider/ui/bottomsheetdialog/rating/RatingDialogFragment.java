package com.eaglecabs.provider.ui.bottomsheetdialog.rating;

import android.content.Intent;
import android.support.v7.widget.AppCompatRatingBar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.eaglecabs.provider.BuildConfig;
import com.eaglecabs.provider.R;
import com.eaglecabs.provider.base.BaseBottomSheetDialogFragment;
import com.eaglecabs.provider.data.network.model.Rating;
import com.eaglecabs.provider.data.network.model.Request_;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.eaglecabs.provider.base.BaseActivity.DATUM;
import static com.eaglecabs.provider.common.fcm.MyFirebaseMessagingService.INTENT_FILTER;

public class RatingDialogFragment extends BaseBottomSheetDialogFragment implements RatingDialogIView {

    @BindView(R.id.rate_with_txt)
    TextView rateWithTxt;
    @BindView(R.id.user_img)
    ImageView userImg;
    @BindView(R.id.user_rating)
    AppCompatRatingBar userRating;
    @BindView(R.id.comments)
    EditText comments;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    Unbinder unbinder;

    RatingDialogPresenter<RatingDialogFragment> presenter = new RatingDialogPresenter<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_rating;
    }

    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        setCancelable(false);
        presenter.attachView(this);
        init();
    }

    private void init() {
        Request_ data = DATUM;

        if (data != null) {
            if (data.getUser() != null) {
                rateWithTxt.setText("Rate your service with " + data.getUser().getFirstName() + " " + data.getUser().getLastName());
                userRating.setRating(Float.parseFloat(data.getUser().getRating()));
                if (data.getUser().getPicture() != null) {
                    Glide.with(activity()).load(BuildConfig.BASE_IMAGE_URL + data.getUser().getPicture()).apply(RequestOptions.placeholderOf(R.drawable.user).dontAnimate().error(R.drawable.user)).into(userImg);
                }
            }
        }

    }


    @OnClick(R.id.btnSubmit)
    public void onViewClicked() {
        if (DATUM != null) {
            Request_ datum = DATUM;
            HashMap<String, Object> map = new HashMap<>();
            map.put("rating", Math.round(userRating.getRating()));
            map.put("comment", comments.getText().toString());
            presenter.rate(map, datum.getId());
        }
    }

    @Override
    public void onSuccess(Rating rating) {
        Intent intent = new Intent(INTENT_FILTER);
        activity().sendBroadcast(intent);
        dismissAllowingStateLoss();
    }

}
