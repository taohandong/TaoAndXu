package com.share.mvpdemo.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.share.mvpdemo.R;
import com.share.mvpdemo.fragment.home.HomeFragment;
import com.share.mvpsdk.base.fragment.BaseCompatFragment;

import me.yokeyword.fragmentation.SupportFragment;

/**
 */
public class HomeRootFragment extends BaseCompatFragment {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_root;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
if (findChildFragment(HomeFragment.class)==null){
    loadRootFragment(R.id.fl_container,HomeFragment.newInstance());
}
    }

    public static HomeRootFragment newInstance() {
        Bundle args = new Bundle();
        HomeRootFragment fragment = new HomeRootFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
