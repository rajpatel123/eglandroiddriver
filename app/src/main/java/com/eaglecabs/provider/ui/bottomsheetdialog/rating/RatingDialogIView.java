package com.eaglecabs.provider.ui.bottomsheetdialog.rating;

import com.eaglecabs.provider.base.MvpView;
import com.eaglecabs.provider.data.network.model.Rating;

public interface RatingDialogIView extends MvpView {
    void onSuccess(Rating rating);
}
