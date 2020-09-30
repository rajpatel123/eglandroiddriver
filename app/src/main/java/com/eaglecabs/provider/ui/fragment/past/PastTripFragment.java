package com.eaglecabs.provider.ui.fragment.past;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.eaglecabs.provider.R;
import com.eaglecabs.provider.base.BaseFragment;
import com.eaglecabs.provider.data.network.model.HistoryList;
import com.eaglecabs.provider.ui.activity.past_detail.PastTripDetailActivity;
import com.eaglecabs.provider.ui.adapter.PastTripAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PastTripFragment extends BaseFragment implements PastTripIView, PastTripAdapter.ClickListener{

    @BindView(R.id.past_trip_rv)
    RecyclerView pastTripRv;
    @BindView(R.id.error_layout)
    LinearLayout errorLayout;
    Unbinder unbinder;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    List<HistoryList> list = new ArrayList<>();

    PastTripPresenter<PastTripFragment> presenter = new PastTripPresenter<>();
    Context context;

    public PastTripFragment() {
        // Required empty public constructor
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_past_trip;
    }

    @Override
    public View initView(View view) {

        unbinder = ButterKnife.bind(this, view);
        this.context = getContext();
        presenter.attachView(this);

        pastTripRv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        pastTripRv.setItemAnimator(new DefaultItemAnimator());
        pastTripRv.setHasFixedSize(true);

        progressBar.setVisibility(View.VISIBLE);
        presenter.getHistory();
        return view;
    }

    @Override
    public void onSuccess(List<HistoryList> historyList) {
        progressBar.setVisibility(View.GONE);
        list.clear();
        list.addAll(historyList);
        loadAdapter();
    }

    @Override
    public void onError(Throwable e) {
        progressBar.setVisibility(View.GONE);
    }

    private void loadAdapter() {
        if (list.size() > 0) {
            PastTripAdapter adapter = new PastTripAdapter(list, context);
            adapter.setClickListener(this);
            pastTripRv.setAdapter(adapter);
            pastTripRv.setVisibility(View.VISIBLE);
            errorLayout.setVisibility(View.GONE);
        } else {
            pastTripRv.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void redirectClick(HistoryList historyList) {
        Intent intent = new Intent(context, PastTripDetailActivity.class);
        intent.putExtra("request_id", historyList.getId());
        startActivity(intent);
    }
}
