package com.chh.yinbao.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chh.yinbao.config.ActivityURL;
import com.chh.yinbao.R;
import com.chh.yinbao.fragment.BaseFragment;
import com.chh.yinbao.fragment.DiscountInfoFragment;
import com.chh.yinbao.fragment.PaybackPlanFragment;
import com.chh.yinbao.fragment.ProfileFragment;
import com.chh.yinbao.util.MyToast;
import com.chh.yinbao.widget.HomeBottomTabView;
import com.chh.yinbao.utils.AppManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

@Route(path = ActivityURL.MainActivity)
public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Bind(R.id.tabLayout)
    protected HomeBottomTabView tabLayout;

    private List<BaseFragment> fragmentList;
    private int currentFragmentIndex = -1;
    private boolean isExit = false;
    private Bundle isRestoreInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        this.isRestoreInstance = savedInstanceState;
        init();
    }

    private void init() {
        fragmentList = new ArrayList<>();
        initTabLayout();
        initFragment();
        selectItem(0);
        tabLayout.setCheckedViewState(0);
    }

    private void initTabLayout() {
        tabLayout.setmTabItemClickListener(new HomeBottomTabView.OnTabItemClickListener() {
            @Override
            public void onTabItemClick(int index) {
                switch (index) {
                    case HomeBottomTabView.DISCOUNT_INFO_TAB_INDEX:
                        selectItem(index);
                        break;
                    case HomeBottomTabView.PAYBACK_PLAN_TAB_INDEX:
                        selectItem(index);
                        break;
                    case HomeBottomTabView.PROFILE_TAB_INDEX:
                        selectItem(index);
                        break;
                }
            }
        });
    }

    private void initFragment() {
        fragmentList.clear();
        fragmentList.add(new DiscountInfoFragment());
        fragmentList.add(new PaybackPlanFragment());
        fragmentList.add(new ProfileFragment());
    }

    private void selectItem(int position) {
        if (position > fragmentList.size()) {
            position = fragmentList.size() - 1;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (isRestoreInstance == null) {
            if (currentFragmentIndex != -1) {
                transaction.hide(fragmentList.get(currentFragmentIndex));
            }
            BaseFragment fragment = fragmentList.get(position);
            if (fragment.isAdded()) {
                transaction.show(fragment);
            } else {
                transaction.add(R.id.viewContainer, fragment);
            }
        } else {
            String tag = fragmentList.get(position).getMyTag();
            for (int i = 0; i < fragmentList.size(); i++) {
                Fragment f = getSupportFragmentManager().findFragmentByTag(fragmentList.get(i).getMyTag());
                if (f != null) {
                    transaction.hide(getSupportFragmentManager().findFragmentByTag(fragmentList.get(i).getMyTag()));
                }
            }
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
            if (fragment != null) {
                transaction.show(fragment);
            } else {
                transaction.add(R.id.viewContainer, fragmentList.get(position), tag);
            }
        }
        currentFragmentIndex = position;
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
        tabLayout.setCheckedViewState(position);
        fragmentList.get(position).requestData();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!isExit) {
                isExit = true;
                MyToast.show(this, R.string.exit_warning);
                startExitTask();
            } else {
                AppManager.getAppManager().AppExit(this);
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void startExitTask() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3 * 1000);
                    isExit = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onResume() {
        isExit = false;
        super.onResume();
    }
}
