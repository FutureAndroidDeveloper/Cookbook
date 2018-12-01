package com.example.kirill.cookbook;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    public static final int SECTIONS_COUNT = 3;


    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new CategoriesFragment();
            case 1:
                return new FavoriteFragment();
            case 2:
                return new StoreFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return SECTIONS_COUNT;
    }
}
