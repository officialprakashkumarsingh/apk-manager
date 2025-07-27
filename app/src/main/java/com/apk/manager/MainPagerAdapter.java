package com.apk.manager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MainPagerAdapter extends FragmentStateAdapter {
    private static final int NUM_PAGES = 3;
    private AppManager appManager;

    public MainPagerAdapter(@NonNull FragmentActivity fragmentActivity, AppManager appManager) {
        super(fragmentActivity);
        this.appManager = appManager;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return AppListFragment.newInstance(AppListFragment.TYPE_INSTALLED, appManager);
            case 1:
                return AppListFragment.newInstance(AppListFragment.TYPE_SYSTEM, appManager);
            case 2:
                return ApkRepositoryFragment.newInstance(appManager);
            default:
                return AppListFragment.newInstance(AppListFragment.TYPE_INSTALLED, appManager);
        }
    }

    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
}