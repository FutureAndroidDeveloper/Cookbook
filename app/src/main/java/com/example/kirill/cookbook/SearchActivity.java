package com.example.kirill.cookbook;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;

import java.util.ArrayList;


public class SearchActivity extends AppCompatActivity {
    public static final int RECYCLER_COLUMN_COUNT = 2;

    private CaptionedImagesAdapter adapter;
    private SQLiteDatabase database;
    private Cursor cursor;

    private ArrayList<Integer> foodIds;
    private ArrayList<String> captions;
    private ArrayList<Integer> imagesIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        foodIds = new ArrayList<>();
        captions = new ArrayList<>();
        imagesIds = new ArrayList<>();

        new FillCardTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint(getString(R.string.search_hint));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase();

                ArrayList<String> newCaptions = new ArrayList<>();
                ArrayList<Integer> newImageIds = new ArrayList<>();
                ArrayList<Integer> newFoodIds = new ArrayList<>();

                for (String caption: captions) {
                    if (caption.toLowerCase().contains(newText)) {
                        newCaptions.add(caption);
                        newImageIds.add(imagesIds.get(captions.indexOf(caption)));
                        newFoodIds.add(foodIds.get(captions.indexOf(caption)));
                    }
                }

                adapter.setSearchOperation(newCaptions, newImageIds, newFoodIds);

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (cursor != null) {
            cursor.close();
        } else if (database != null) {
            database.close();
        }
    }

    // class for async task with database
    private class FillCardTask extends AsyncTask<Void, Void, Boolean> {
        private RecyclerView foodRecycler;

        @Override
        protected void onPreExecute() {
            foodRecycler = (RecyclerView) findViewById(R.id.food_recycler);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            FoodDatabaseHelper databaseHelper = new FoodDatabaseHelper(SearchActivity.this);
            try {
                database = databaseHelper.getReadableDatabase();
                cursor = database.query("FOOD",
                        new String[]{"_id", "NAME", "IMAGE_RESOURCE_ID"},
                        null, null, null,
                        null, null, null);

                while (cursor.moveToNext()) {
                    foodIds.add(cursor.getInt(cursor.getColumnIndex("_id")));
                    captions.add(cursor.getString(cursor.getColumnIndex("NAME")));
                    imagesIds.add(cursor.getInt(cursor.getColumnIndex("IMAGE_RESOURCE_ID")));
                }

                adapter = new CaptionedImagesAdapter(captions, imagesIds, foodIds);
                foodRecycler.setAdapter(adapter);

                // set layoutManager
                GridLayoutManager layoutManager = new GridLayoutManager(SearchActivity.this,
                        RECYCLER_COLUMN_COUNT, GridLayoutManager.VERTICAL, false);
                foodRecycler.setLayoutManager(layoutManager);

                // set listener in cardView
                adapter.setListener(new CaptionedImagesAdapter.Listener() {
                    @Override
                    public void onClick(int id) {
                        Intent intent = new Intent(SearchActivity.this, DetailActivity.class);
                        intent.putExtra(DetailActivity.EXTRA_FOOD_ID, id);
                        startActivity(intent);
                    }
                });

                cursor.close();
                database.close();
                return true;
            } catch (SQLException e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (!success) {
                Toast.makeText(SearchActivity.this, getString(R.string.db_error), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
