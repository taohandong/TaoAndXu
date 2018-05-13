package com.share.mvpdemo.model.book;

import android.support.annotation.NonNull;


import com.share.mvpdemo.api.DoubanApi;
import com.share.mvpdemo.bean.BookListBean;
import com.share.mvpdemo.contract.book.BookCustomContract;
import com.share.mvpsdk.base.BaseModel;
import com.share.mvpsdk.helper.RetrofitCreateHelper;
import com.share.mvpsdk.helper.RxHelper;

import io.reactivex.Observable;


/**
 * Created by Horrarndoo on 2017/10/21.
 * <p>
 */

public class BookCustomModel extends BaseModel implements BookCustomContract.IBookCustomModel {
    @NonNull
    public static BookCustomModel newInstance() {
        return new BookCustomModel();
    }

    @Override
    public Observable<BookListBean> getBookListWithTag(String tag, int start, int count) {
        return RetrofitCreateHelper.createApi(DoubanApi.class, DoubanApi.HOST).getBookListWithTag
                (tag, start, count).compose(RxHelper.<BookListBean>rxSchedulerHelper());
    }
}
