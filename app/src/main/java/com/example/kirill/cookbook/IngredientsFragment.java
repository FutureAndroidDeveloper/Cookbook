package com.example.kirill.cookbook;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class IngredientsFragment extends Fragment {
    private SQLiteDatabase database;
    LayoutInflater layoutInflater;
    public ArrayList<String> selectedItems;
    private SQLiteOpenHelper shopDatabaseHelper;


    final String[] catNames = new String[]{"Рыжик", "Барсик", "Мурзик",
            "Мурка", "Васька", "Томасина", "Кристина", "Пушок", "Дымка",
            "Кузя", "Китти", "Масяня", "Симба", "Паша", "Артём"};

    public IngredientsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        selectedItems = new ArrayList<>();
        layoutInflater = inflater;

        shopDatabaseHelper = new ShopDatabaseHelper(inflater.getContext());

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ingredients, container, false);

        ListView ingredientsList = (ListView) view.findViewById(R.id.checkable_list);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(inflater.getContext(),
                R.layout.ingredient_list_item, R.id.text_line, catNames);
        ingredientsList.setAdapter(adapter);

        // Load selected ingredients from database
        getSelectedIngredientsFromDatabase();
        for (String databaseIngredient : selectedItems) {
            ingredientsList.setItemChecked(getSelectedPosition(databaseIngredient), true);
        }

        ingredientsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = ((TextView) view).getText().toString();

                if (selectedItems.contains(selectedItem)) {     // uncheck item
                    selectedItems.remove(selectedItem);
                    deleteIngredientFromDatabase(selectedItem);
                } else {
                    selectedItems.add(selectedItem);
                }
            }
        });

        Button showButton = (Button) view.findViewById(R.id.show_list);
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ; // добавить все в бд
            }
        });

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();

        // Add selected ingredients to DB
        try {
            database = shopDatabaseHelper.getWritableDatabase();
            Cursor cursor = database.query("SHOP",
                    new String[]{"_id", "NAME"},
                    null, null, null, null, null);

            ContentValues contentValues = new ContentValues();

            if (!cursor.moveToFirst()) {        // if cursor is empty, set new values
                for (String item : selectedItems) {
                    contentValues.put("NAME", item);
                    database.insert("SHOP", null, contentValues);
                }
            } else {                            // if cursor has duplicates, delete them and insert again
                for (String item : selectedItems) {
                    database.delete("SHOP", "NAME = ?", new String[]{item});
                    contentValues.put("NAME", item);
                    database.insert("SHOP", null, contentValues);
                }
            }

            cursor.close();
        } catch (SQLException e) {
            Toast.makeText(layoutInflater.getContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
        }

        database.close();
    }

    private void getSelectedIngredientsFromDatabase() {
        try {
            database = shopDatabaseHelper.getReadableDatabase();
            Cursor cursor = database.query("SHOP",
                    new String[]{"_id", "NAME"},
                    null, null, null, null, null);

            while (cursor.moveToNext()) {
                String databaseIngredient = cursor.getString(cursor.getColumnIndex("NAME"));

                for (String item : catNames) {
                    if (item.equals(databaseIngredient)) {
                        selectedItems.add(databaseIngredient);
                        break;
                    }
                }
            }
            cursor.close();
        } catch (SQLException e) {
            Toast.makeText(layoutInflater.getContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
        }
        database.close();
    }

    private int getSelectedPosition(String ingredient) {
        int position = 0;

        for (int index = 0; index < catNames.length; index++) {
            if (catNames[index].equals(ingredient)) {
                position = index;
                break;
            }
        }
        return position;
    }

    private void deleteIngredientFromDatabase(String ingredient) {
        try {
            database = shopDatabaseHelper.getWritableDatabase();
            database.delete("SHOP", "NAME = ?", new String[]{ingredient});
        } catch (SQLException e) {
            Toast.makeText(layoutInflater.getContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
        }

        database.close();
    }
}
