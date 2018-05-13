package com.share.mvpdemo.fragment.book.child;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.share.mvpdemo.R;
import com.share.mvpdemo.constant.TabFragmentIndex;
import com.share.mvpdemo.contract.book.BookMainContract;
import com.share.mvpdemo.presenter.bok.BookMainPresenter;
import com.share.mvpsdk.adapter.FragmentAdapter;
import com.share.mvpsdk.base.BasePresenter;
import com.share.mvpsdk.base.fragment.BaseMVPCompatFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 */
public class BookFragment extends BaseMVPCompatFragment<BookMainContract.BookMainPresenter,
        BookMainContract.IBookMainModel> implements BookMainContract.IBookMainView {


    @BindView(R.id.tl_tabs)
    TabLayout tlTabs;
    @BindView(R.id.vp_fragment)
    ViewPager vpFragment;

    private List<Fragment> fragments;

    public static BookFragment newInstance() {
        Bundle args = new Bundle();
        BookFragment fragment = new BookFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return BookMainPresenter.newInstance();
    }

    @Override
    public void showTabList(String[] tabs) {
        Logger.w(Arrays.toString(tabs));
        Log.d("66666",getActivity().getComponentName().getPackageName()+"showTabList");
        //实际上3个子布局是一样的，都只有一个recycleview，但是为了后续升级拓展，子fragment都是使用单独的布局文件
        for (int i = 0; i < tabs.length; i++) {
            tlTabs.addTab(tlTabs.newTab().setText(tabs[i]));
            switch (i) {
                case TabFragmentIndex.TAB_BOOK_LITERATURE_INDEX:
                    fragments.add(BookCustomFragment.newInstance("文学"));
                    break;
                case TabFragmentIndex.TAB_BOOK_CULTURE_INDEX:
                    fragments.add(BookCustomFragment.newInstance("文化"));
                    break;
                case TabFragmentIndex.TAB_BOOK_LIFE_INDEX:
                    fragments.add(BookCustomFragment.newInstance("生活"));
                    break;
                default:
                    fragments.add(BookCustomFragment.newInstance("文学"));
                    break;
            }
        }
        vpFragment.setAdapter(new FragmentAdapter(getChildFragmentManager(), fragments));
        //要设置到viewpager.setAdapter后才起作用
        vpFragment.setCurrentItem(TabFragmentIndex.TAB_BOOK_LITERATURE_INDEX);
        tlTabs.setupWithViewPager(vpFragment);
        tlTabs.setVerticalScrollbarPosition(TabFragmentIndex.TAB_BOOK_LITERATURE_INDEX);
        //tlTabs.setupWithViewPager方法内部会remove所有的tabs，这里重新设置一遍tabs的text，否则tabs的text不显示
        for (int i = 0; i < tabs.length; i++) {
            tlTabs.getTabAt(i).setText(tabs[i]);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_book;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
//        ButterKnife.bind(this, view);
        tlTabs=view.findViewById(R.id.tl_tabs);
        vpFragment=view.findViewById(R.id.vp_fragment);
        mPresenter.getTabList();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragments = new ArrayList<>();
    }
}
