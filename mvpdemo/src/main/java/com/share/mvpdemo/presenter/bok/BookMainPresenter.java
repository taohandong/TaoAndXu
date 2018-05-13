package com.share.mvpdemo.presenter.bok;

import android.support.annotation.NonNull;

import com.share.mvpdemo.contract.book.BookMainContract;
import com.share.mvpdemo.model.book.BookMainModel;

/**
 * Created by Horrarndoo on 2017/10/21.
 * <p>
 */

public class BookMainPresenter extends BookMainContract.BookMainPresenter {
    @NonNull
    public static BookMainPresenter newInstance() {
        return new BookMainPresenter();
    }

    @Override
    public void getTabList() {
        if (mIView == null || mIModel == null)
            return;

        mIView.showTabList(mIModel.getTabs());
    }

    @Override
    public BookMainContract.IBookMainModel getModel() {
        return BookMainModel.newInstance();
    }

    @Override
    public void onStart() {
    }
}
