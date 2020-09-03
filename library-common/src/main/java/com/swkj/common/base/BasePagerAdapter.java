package com.swkj.common.base;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;

import java.util.ArrayList;
import java.util.List;

/**
 * author pisa
 * date  2019/9/26
 * version 1.0
 * effect : 适用于界面数量少的场景
 */
public class BasePagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragments = new ArrayList<>();

    /**
     * Constructor for {@link FragmentPagerAdapter}.
     * <p>
     * If {@link #BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT} is passed in, then only the current
     * Fragment is in the {@link Lifecycle.State#RESUMED} state. All other fragments are capped at
     * {@link Lifecycle.State#STARTED}. If {@link #BEHAVIOR_SET_USER_VISIBLE_HINT} is passed, all
     * fragments are in the {@link Lifecycle.State#RESUMED} state and there will be callbacks to
     * {@link Fragment#setUserVisibleHint(boolean)}.
     *
     * @param fm       fragment manager that will interact with this adapter
     * @param behavior determines if only current fragments are in a resumed state
     */
    public BasePagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public BasePagerAdapter(@NonNull FragmentManager fm) {
        this(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return fragments.size();
    }

    /**
     * 设置数据源
     *
     * @param fragments
     */
    public void setFragments(List<Fragment> fragments) {
        if (fragments != null) {
            this.fragments.clear();
            this.fragments.addAll(fragments);
            notifyDataSetChanged();
        }
    }

    /**
     * This method may be called by the ViewPager to obtain a title string
     * to describe the specified page. This method may return null
     * indicating no title for this page. The default implementation returns
     * null.
     *
     * @param position The position of the title requested
     * @return A title for the requested page
     */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (fragments.isEmpty()) {
            return "";
        }
        return super.getPageTitle(position);
    }
}
