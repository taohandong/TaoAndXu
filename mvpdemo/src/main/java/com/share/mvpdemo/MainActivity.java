package com.share.mvpdemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.share.mvpdemo.activity.VideoActivity;
import com.share.mvpdemo.fragment.book.BookRootFragment;
import com.share.mvpsdk.base.activity.BaseCompatActivity;
import com.share.mvpsdk.rxbus.RxBus;
import com.share.mvpsdk.utils.PermissionUtils;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends BaseCompatActivity implements PermissionUtils.PermissionGrant{

    @BindView(R.id.nv_menu)
    NavigationView nvMenu;
    @BindView(R.id.dl_root)
    DrawerLayout dlRoot;
    @BindView(R.id.bviv_bar)
    BottomNavigationView bottomNavigationView;

    TextView tvPlay;

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURTH = 3;
    public static final int FIFTH = 4;

    private SupportFragment[] mFragments = new SupportFragment[1];
    @Override
    protected void initView(Bundle savedInstanceState) {
        PermissionUtils.requestMultiPermissions(this,this);
        try {
            Log.d("66666","initView"+(" | | |").split("\\|").length);
        }catch (Exception e){
            Log.d("66666","e="+e.toString());
        }
        tvPlay=findViewById(R.id.tvPlay);
        bottomNavigationView=findViewById(R.id.bviv_bar);
        tvPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoActivity.getInstance(MainActivity.this);
            }
        });
        if (savedInstanceState == null) {
//            mFragments[FIRST] = HomeRootFragment.newInstance();
            mFragments[FIRST] = BookRootFragment.newInstance();
//            mFragments[SECOND] = GankIoRootFragment.newInstance();
//            mFragments[THIRD] = MovieRootFragment.newInstance();
//            mFragments[FOURTH] = BookRootFragment.newInstance();
//            mFragments[FIFTH] = PersonalRootFragment.newInstance();

            loadMultipleRootFragment(R.id.fl_container, FIRST,
                    mFragments[FIRST]);
//                    mFragments[SECOND],
//                    mFragments[THIRD],
//                    mFragments[FOURTH],
//                    mFragments[FIFTH]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用,也可以通过getSupportFragmentManager.getFragments()
            // 自行进行判断查找(效率更高些),用下面的方法查找更方便些
//            mFragments[FIRST] = findFragment(HomeRootFragment.class);
            mFragments[FIRST] = findFragment(BookRootFragment.class);
//            mFragments[SECOND] = findFragment(GankIoRootFragment.class);
//            mFragments[THIRD] = findFragment(MovieRootFragment.class);
//            mFragments[FOURTH] = findFragment(BookRootFragment.class);
//            mFragments[FIFTH] = findFragment(PersonalRootFragment.class);
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_item_home:

                        break;
                    case R.id.menu_item_gank_io:

                        break;
                    case R.id.menu_item_movie:

                        break;
                    case R.id.menu_item_book:

                        break;
                    case R.id.menu_item_personal:

                        break;
                }
                return true;
            }
        });




    }

    @Override
    protected void initData() {
        super.initData();
        RxBus.get().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unRegister(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onPermissionGranted(int requestCode) {

    }
}
