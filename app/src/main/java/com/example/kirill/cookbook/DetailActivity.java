package com.example.kirill.cookbook;

import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_FOOD_ID = "foodId";
    private Toolbar toolbar;
    private ViewPager pager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        collapsingToolbarLayout.setTitleEnabled(false);

        // Set toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("МОЙ РЕЦЕПТ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set adapter to pager
        SectionsRecipePagerAdapter pagerAdapter = new SectionsRecipePagerAdapter(getSupportFragmentManager());
        pager = (ViewPager) findViewById(R.id.pager_recipe);
        pager.setAdapter(pagerAdapter);

        // Connect tabLayout with viewPager
        tabLayout = (TabLayout) findViewById(R.id.tabs_recipe);
        tabLayout.setupWithViewPager(pager);
    }

    public class SectionsRecipePagerAdapter extends FragmentPagerAdapter {
        public static final int SECTIONS_COUNT = 3;


        public SectionsRecipePagerAdapter(FragmentManager fm) {
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

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getText(R.string.ingredients_tab);
                case 1:
                    return getResources().getText(R.string.recipe_tab);
                case 2:
                    return getResources().getText(R.string.rating_tab);
            }
            return null;
        }
    }
}
