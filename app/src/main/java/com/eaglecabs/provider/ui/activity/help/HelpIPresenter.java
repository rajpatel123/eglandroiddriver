package com.eaglecabs.provider.ui.activity.help;


import com.eaglecabs.provider.base.MvpPresenter;

public interface HelpIPresenter<V extends HelpIView> extends MvpPresenter<V> {
    void getHelp();
}
