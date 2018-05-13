package com.share.mvpdemo.fragment.book;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.share.mvpdemo.R;
import com.share.mvpdemo.fragment.book.child.BookFragment;
import com.share.mvpsdk.base.fragment.BaseCompatFragment;

/**
 * create an instance of this fragment.
 */
public class BookRootFragment extends BaseCompatFragment {
    public static BookRootFragment newInstance() {
        Bundle args = new Bundle();
        BookRootFragment fragment = new BookRootFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_book_root;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        if (findChildFragment(BookFragment.class) == null) {
            loadRootFragment(R.id.fl_container, BookFragment.newInstance());
        }
    }
}
