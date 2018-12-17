package com.example.kirill.cookbook;

import android.database.Cursor;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_FOOD_ID = "foodId";
    public static final int DEFAULT_EXTRA_VALUE = 1;
    private String title;

    private SQLiteDatabase database;
    private Cursor cursor;
    private FloatingActionButton floatingActionButton;
    private boolean like = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        int foodId = getIntent().getIntExtra(EXTRA_FOOD_ID, DEFAULT_EXTRA_VALUE);

        FoodDatabaseHelper databaseHelper = new FoodDatabaseHelper(this);
        try {
            database = databaseHelper.getReadableDatabase();
            cursor = database.query("FOOD",
                    new String[]{"_id", "NAME", "INGREDIENTS"},
                    "_id = ?",
                    new String[]{Integer.toString(foodId)},
                    null, null, null, null);

            // get activity title
            if (cursor.moveToFirst()) {
                title = cursor.getString(cursor.getColumnIndex("NAME"));
            }
        } catch (SQLException e) {
            Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT).show();
        }

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        collapsingToolbarLayout.setTitleEnabled(false);

        // Set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(title);

        // Set adapter to pager
        SectionsRecipePagerAdapter pagerAdapter = new SectionsRecipePagerAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.pager_recipe);
        pager.setAdapter(pagerAdapter);

        // Connect tabLayout with viewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs_recipe);
        tabLayout.setupWithViewPager(pager);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab_like);
    }

    public class SectionsRecipePagerAdapter extends FragmentPagerAdapter {
        public static final int SECTIONS_COUNT = 3;


        public SectionsRecipePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: {
                    Bundle bundle = new Bundle();
                    bundle.putStringArray(IngredientsFragment.BUNDLE_INGREDIENTS, getSplitIngredients(cursor));

                    Fragment ingredientsFragment = new IngredientsFragment();
                    ingredientsFragment.setArguments(bundle);

                    return ingredientsFragment;
                }
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

    public void onClickLike(View view) {
        if (like) {
            floatingActionButton.setColorFilter(Color.argb(255, 255, 255, 255));
        } else {
            floatingActionButton.setColorFilter(Color.argb(255,255,0,0));
        }
        like = !like;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        
        if (cursor != null) {
            cursor.close();
            database.close();
        }
    }

    protected String[] getSplitIngredients(Cursor ingredientsCursor) {
        String[] ingredients = {};

        if (ingredientsCursor.moveToFirst()) {
            ingredients = ingredientsCursor.getString(ingredientsCursor.getColumnIndex("INGREDIENTS")).split("; ");
        }

        return ingredients;
    }
}
