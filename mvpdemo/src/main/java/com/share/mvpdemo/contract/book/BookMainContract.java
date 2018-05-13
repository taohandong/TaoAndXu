package com.share.mvpdemo.contract.book;


import com.share.mvpsdk.base.BasePresenter;
import com.share.mvpsdk.base.IBaseFragment;
import com.share.mvpsdk.base.IBaseModel;

/**
 * Created by Horrarndoo on 2017/10/21.
 * <p>
 */

public interface BookMainContract {
    //book主页接口
    abstract class BookMainPresenter extends BasePresenter<IBookMainModel, IBookMainView> {
        public abstract void getTabList();
    }

    interface IBookMainModel extends IBaseModel {
        String[] getTabs();
    }

    interface IBookMainView extends IBaseFragment {
        void showTabList(String[] tabs);
    }
}
