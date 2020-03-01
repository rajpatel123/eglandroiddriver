package com.eaglecabs.provider.ui.activity.invite;

import android.view.MenuItem;
import android.widget.Button;

import com.eaglecabs.provider.R;
import com.eaglecabs.provider.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InviteActivity extends BaseActivity implements InviteIView {

    @BindView(R.id.call)
    Button call;

    InvitePresenter<InviteActivity> presenter = new InvitePresenter<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_invite;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        presenter.attachView(this);
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


}
