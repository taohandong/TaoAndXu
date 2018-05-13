package com.share.mvpdemo.presenter;

import android.support.annotation.NonNull;

import com.share.mvpdemo.contract.HomeMainContract;
import com.share.mvpdemo.model.HomeMainModel;


/**
 * Created by Horrarndoo on 2017/9/11.
 * <p>
 */

public class HomeMainPresenter extends HomeMainContract.HomeMainPresenter {

    @NonNull
    public static HomeMainPresenter newInstance() {
        return new HomeMainPresenter();
    }

    @Override
    public void getTabList() {
        if (mIView == null || mIModel == null)
            return;

        mIView.showTabList(mIModel.getTabs());
    }

    @Override
    public HomeMainContract.IHomeMainModel getModel() {
        return HomeMainModel.newInstance();
    }

    @Override
    public void onStart() {

    }
}
