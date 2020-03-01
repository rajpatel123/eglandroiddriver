package com.eaglecabs.provider.ui.activity.help;

import com.eaglecabs.provider.base.MvpView;
import com.eaglecabs.provider.data.network.model.Help;

public interface HelpIView extends MvpView {
    void onSuccess(Help object);
    void onError(Throwable e);
}
