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
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.Toast;

import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesFragment extends Fragment {
    public String test;
    private final Random random = new Random();

    public CategoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        // Random choice of food from BD by CATEGORY
        FoodDatabaseHelper foodDatabaseHelper = new FoodDatabaseHelper(inflater.getContext());
        try {
            SQLiteDatabase database = foodDatabaseHelper.getReadableDatabase();
            Cursor cursor = database.query("FOOD",
                    new String[]{"_id", "CATEGORY", "NAME", "INGREDIENTS", "RECIPE", "IMAGE_RESOURCE_ID"},
                    "CATEGORY = ?",
                    new String[]{"Snacks"},
                    null,
                    null,
                   null,
                    null);

//            int firstId = getFirstIdCursor(cursor);
//            int cursorSize = getCursorSize(cursor);
//            int lastId = (firstId + cursorSize) - 1;
//            cursor.close();

            // Скорее всего нумерация срок в курсоре начинается с 1
            int firstId = getFirstIdCursor(cursor); //1     5
            int cursorSize = getCursorSize(cursor); //3     5
            int lastId = (firstId + cursorSize);    //4     10
            cursor.close();

            // Random first food
            int randomBetweenId = random.nextInt(lastId - firstId) + firstId;  // (4 - 1) = 3 --> [0; 2]
                                                                                            // 0+1; 1+1; 2+1; --> 1;2;3
                                                                                            // (10 - 5) = 5 --> [0; 4]
                                                                                            // 0+5; 1+5; 2+5; 3+5; 4+5; --> 5;6;7;8;9

            // Add second random with checking, that first random and second random are not equals!
            // Use while()

            cursor = database.query("FOOD",
                    new String[]{"_id", "CATEGORY", "NAME", "INGREDIENTS", "RECIPE", "IMAGE_RESOURCE_ID"},
                    "_id = ?",
                    new String[]{Integer.toString(randomBetweenId)},
                    null,
                    null,
                    null,
                    null);

            if (cursor.moveToFirst()) {
                // g
                test = cursor.getString(cursor.getColumnIndex("NAME"));
            }

            // get from cursor second random food here

            cursor.close();
        } catch (SQLException e) {
            Toast.makeText(inflater.getContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
        }


        // Probable better will be add support class for storing info about food
        // Or add information about food in arrays
        int[] images = new int[Food.foods.length];
        String[] names = new String[Food.foods.length];

        for (int i = 0; i < Food.foods.length; i++) {
            names[i] = Food.foods[i].getName();
            images[i] = Food.foods[i].getImageResourceId();
        }

        GridView gridViewSnack = (GridView) view.findViewById(R.id.gridViewSnack);
        gridViewSnack.setAdapter(new CardGridAdapter(this.getContext(), names, images, R.layout.card_captioned_image));

        GridView gridViewSalads = (GridView) view.findViewById(R.id.gridViewSalads);
        gridViewSalads.setAdapter(new CardGridAdapter(this.getContext(), names, images, R.layout.card_captioned_image));

        GridView gridViewDishes = (GridView) view.findViewById(R.id.gridViewDishes);
        gridViewDishes.setAdapter(new CardGridAdapter(this.getContext(), names, images, R.layout.card_captioned_image));

        GridView gridViewDesserts = (GridView) view.findViewById(R.id.gridViewDesserts);
        gridViewDesserts.setAdapter(new CardGridAdapter(this.getContext(), names, images, R.layout.card_captioned_image));

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

    private int getCursorSize(Cursor cursor) {
        int size = 0;

        if (cursor.moveToFirst()) {
            size++;
        }

        while (cursor.moveToNext()) {
            size++;
        }

        cursor.moveToFirst();
        return size;
    }

    private int getFirstIdCursor(Cursor cursor) {
        int id = 0;

        if (cursor.moveToFirst()) {
            id = cursor.getInt(0);
        }

        return id;
    }
}
