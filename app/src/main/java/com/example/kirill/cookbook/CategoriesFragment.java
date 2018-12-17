package com.example.kirill.cookbook;


import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import static com.example.kirill.cookbook.FoodGridHelper.*;

import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesFragment extends Fragment {
    public final String[] DatabaseCategories= {"Snacks", "Salads", "Main", "Desserts"};

    public static final int gridColumnCount = 2;
    public static final int gridRowCount = 4;
    public int randomId;
    private final Random random = new Random();

    private SQLiteDatabase database;
    private Cursor cursor;

    public CategoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        if (!wasCreated) {

            // Random choice of food from BD by CATEGORY
            FoodDatabaseHelper foodDatabaseHelper = new FoodDatabaseHelper(inflater.getContext());
            try {
                database = foodDatabaseHelper.getReadableDatabase();

                for (int rowGrid = 0; rowGrid < gridRowCount; rowGrid++) {
                    for (int columnGrid = 0; columnGrid < gridColumnCount; columnGrid++) {
                        cursor = getRandomCategoryRecordFromDb(database, DatabaseCategories[rowGrid]);

                        // get all needed information about food
                        if (cursor.moveToFirst()) {
                            categoriesIds[rowGrid][columnGrid] = cursor.getInt(cursor.getColumnIndex("_id"));
                            images[rowGrid][columnGrid] = cursor.getInt(cursor.getColumnIndex("IMAGE_RESOURCE_ID"));
                            names[rowGrid][columnGrid] = cursor.getString(cursor.getColumnIndex("NAME"));
                        }
                    }
                }

                wasCreated = true;

            } catch (SQLException e) {
                Toast.makeText(inflater.getContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
            }
        }


        GridView gridViewSnack = (GridView) view.findViewById(R.id.gridViewSnack);
        gridViewSnack.setAdapter(new CardGridAdapter(this.getContext(), names[0], images[0], R.layout.card_captioned_image));

        GridView gridViewSalads = (GridView) view.findViewById(R.id.gridViewSalads);
        gridViewSalads.setAdapter(new CardGridAdapter(this.getContext(), names[1], images[1], R.layout.card_captioned_image));

        GridView gridViewDishes = (GridView) view.findViewById(R.id.gridViewDishes);
        gridViewDishes.setAdapter(new CardGridAdapter(this.getContext(), names[2], images[2], R.layout.card_captioned_image));

        GridView gridViewDesserts = (GridView) view.findViewById(R.id.gridViewDesserts);
        gridViewDesserts.setAdapter(new CardGridAdapter(this.getContext(), names[3], images[3], R.layout.card_captioned_image));


        gridViewSnack.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_FOOD_ID, (int) id);
                 startActivity(intent);
            }
        });

        return view;
    }

    private int getFirstIdCursor(Cursor cursor) {
        int id = 0;

        if (cursor.moveToFirst()) {
            id = cursor.getInt(0);
        }

        return id;
    }

    private Cursor getRandomCategoryRecordFromDb(SQLiteDatabase database, String category) {
        Cursor cursor = database.query("FOOD",
                new String[]{"_id", "CATEGORY", "NAME", "INGREDIENTS", "RECIPE", "IMAGE_RESOURCE_ID"},
                "CATEGORY = ?",
                new String[]{category},
                null,
                null,
                null,
                null);

        int firstId = getFirstIdCursor(cursor);
        int cursorSize = cursor.getCount();
        int lastId = (firstId + cursorSize);

        // Random record food
        int randomBetweenId = random.nextInt(lastId - firstId) + firstId;

        while (randomBetweenId == randomId) {
            randomBetweenId = random.nextInt(lastId - firstId) + firstId;
        }
        randomId = randomBetweenId;

        cursor = database.query("FOOD",
                new String[]{"_id", "CATEGORY", "NAME", "INGREDIENTS", "RECIPE", "IMAGE_RESOURCE_ID"},
                "_id = ?",
                new String[]{Integer.toString(randomBetweenId)},
                null,
                null,
                null,
                null);

        return  cursor;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (!(cursor == null)) {
            cursor.close();
            database.close();
        }
    }
}
