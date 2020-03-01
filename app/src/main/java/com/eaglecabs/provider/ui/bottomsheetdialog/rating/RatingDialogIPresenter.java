package com.eaglecabs.provider.ui.bottomsheetdialog.rating;

import com.eaglecabs.provider.base.MvpPresenter;

import java.util.HashMap;

public interface RatingDialogIPresenter<V extends RatingDialogIView> extends MvpPresenter<V> {
    void rate(HashMap<String, Object> obj, Integer id);
}
