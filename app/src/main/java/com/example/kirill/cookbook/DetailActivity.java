package com.example.kirill.cookbook;

import android.content.ContentValues;
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
import android.support.v4.app.ListFragment;
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
    public static final String DB_SEPARATOR = "; ";
    public static final int DEFAULT_EXTRA_VALUE = 1;
    private String title;

    private SQLiteDatabase database;
    private Cursor cursor;
    private FloatingActionButton favoriteFAB;
    private boolean isFavorite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        int foodId = getIntent().getIntExtra(EXTRA_FOOD_ID, DEFAULT_EXTRA_VALUE);
        favoriteFAB = (FloatingActionButton) findViewById(R.id.fab_like);

        FoodDatabaseHelper databaseHelper = new FoodDatabaseHelper(this);
        try {
            database = databaseHelper.getReadableDatabase();
            cursor = database.query("FOOD",
                    null,
                    "_id = ?",
                    new String[]{Integer.toString(foodId)},
                    null, null, null, null);

            if (cursor.moveToFirst()) {
                // get activity title
                title = cursor.getString(cursor.getColumnIndex("NAME"));

                // get favorite flag
                isFavorite = cursor.getInt(cursor.getColumnIndex("FAVORITE")) == 1;

                if (isFavorite) {
                    favoriteFAB.setColorFilter(Color.argb(255,255,0,0));
                }
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
                    bundle.putStringArray(IngredientsFragment.BUNDLE_INGREDIENTS,
                            getSplitArray(cursor, "INGREDIENTS"));

                    Fragment ingredientsFragment = new IngredientsFragment();
                    ingredientsFragment.setArguments(bundle);

                    return ingredientsFragment;
                }
                case 1: {
                    Bundle bundle = new Bundle();
                    bundle.putStringArray(RecipeListFragment.BUNDLE_RECIPES,
                            getSplitArray(cursor, "RECIPE"));

                    ListFragment recipesFragment = new RecipeListFragment();
                    recipesFragment.setArguments(bundle);

                    return recipesFragment;
                }
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
        int foodId = getIntent().getIntExtra(EXTRA_FOOD_ID, DEFAULT_EXTRA_VALUE);
        ContentValues foodValues = new ContentValues();

        isFavorite = !isFavorite;

        if (isFavorite) {
            // set red color of fab
            favoriteFAB.setColorFilter(Color.argb(255,255,0,0));
        } else {
            // set white color of fab
            favoriteFAB.setColorFilter(Color.argb(255, 255, 255, 255));
        }

        foodValues.put("FAVORITE", isFavorite);

        // update favorite food data
        if (database != null) {
            database.update("FOOD",
                    foodValues,
                    "_id = ?",
                    new String[]{Integer.toString(foodId)});
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        
        if (cursor != null) {
            cursor.close();
            database.close();
        }
    }

    protected String[] getSplitArray(Cursor ingredientsCursor, String columnName) {
        String[] splitArray = {};

        if (ingredientsCursor.moveToFirst()) {
            splitArray = ingredientsCursor.getString(ingredientsCursor.getColumnIndex(columnName))
                    .split(DB_SEPARATOR);
        }

        return splitArray;
    }
}
