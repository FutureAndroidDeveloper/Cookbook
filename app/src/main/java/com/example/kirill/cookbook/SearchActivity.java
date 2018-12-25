package com.example.kirill.cookbook;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;

import java.util.ArrayList;

import javax.net.ssl.SSLException;

public class SearchActivity extends AppCompatActivity {
    public static final int RECYCLER_COLUMN_COUNT = 2;

    private Toolbar toolbar;
    private SearchView searchView;
    private RecyclerView foodRecycler;
    private CaptionedImagesAdapter adapter;

    private ArrayList<Integer> foodIds;
    private ArrayList<String> captions;
    private ArrayList<Integer> imagesIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        foodIds = new ArrayList<>();
        captions = new ArrayList<>();
        imagesIds = new ArrayList<>();

        foodRecycler = (RecyclerView) findViewById(R.id.food_recycler);

        FoodDatabaseHelper databaseHelper = new FoodDatabaseHelper(this);
        try {
            SQLiteDatabase database = databaseHelper.getReadableDatabase();
            Cursor cursor = database.query("FOOD",
                    new String[]{"_id", "NAME", "IMAGE_RESOURCE_ID"},
                    null, null, null,
                    null, null, null);

            while (cursor.moveToNext()) {
                foodIds.add(cursor.getInt(cursor.getColumnIndex("_id")));
                captions.add(cursor.getString(cursor.getColumnIndex("NAME")));
                imagesIds.add(cursor.getInt(cursor.getColumnIndex("IMAGE_RESOURCE_ID")));
            }

            adapter = new CaptionedImagesAdapter(captions, imagesIds);
            foodRecycler.setAdapter(adapter);

            // set layoutManager
            GridLayoutManager layoutManager = new GridLayoutManager(this,
                    RECYCLER_COLUMN_COUNT, GridLayoutManager.VERTICAL, false);
            foodRecycler.setLayoutManager(layoutManager);

            cursor.close();
            database.close();
        } catch (SQLException e) {
            Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

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

                for (String caption: captions) {
                    if (caption.toLowerCase().contains(newText)) {
                        newCaptions.add(caption);
                        newImageIds.add(imagesIds.get(captions.indexOf(caption)));
                    }
                }

                adapter.setSearchOperation(newCaptions, newImageIds);

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
