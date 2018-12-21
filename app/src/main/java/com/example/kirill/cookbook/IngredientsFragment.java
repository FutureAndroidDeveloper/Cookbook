package com.example.kirill.cookbook;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
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
    public static final String BUNDLE_INGREDIENTS = "ingredients";

    private SQLiteOpenHelper shopDatabaseHelper;
    private SQLiteDatabase database;
    LayoutInflater layoutInflater;

    public ArrayList<String> selectedItems;
    private String[] ingredients;


    public IngredientsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // get arguments from activity

        Bundle args = getArguments();

        if (args == null) {
            Toast.makeText(getContext(), "arguments is null", Toast.LENGTH_SHORT).show();
        } else {
            ingredients = args.getStringArray(BUNDLE_INGREDIENTS);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        selectedItems = new ArrayList<>();
        layoutInflater = inflater;

        shopDatabaseHelper = new ShopDatabaseHelper(inflater.getContext());

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ingredients, container, false);

        final ListView ingredientsList = (ListView) view.findViewById(R.id.checkable_list);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(inflater.getContext(),
                R.layout.ingredient_list_item, R.id.text_line, ingredients);
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

        Button selectAll = (Button) view.findViewById(R.id.select_all);
        selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (String ingredient : ingredients) {
                    if (!selectedItems.contains(ingredient)) {
                        selectedItems.add(ingredient);
                        ingredientsList.setItemChecked(getSelectedPosition(ingredient), true);
                    }
                }

                adapter.notifyDataSetChanged();
                Toast.makeText(getContext(), getResources().getString(R.string.add_all_ingredients_toast_msg), Toast.LENGTH_SHORT).show();
            }
        });

        Button removeAll = (Button) view.findViewById(R.id.remove_all);
        removeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (String ingredient : ingredients) {
                    if (selectedItems.contains(ingredient)) {
                        deleteIngredientFromDatabase(ingredient);
                        selectedItems.remove(ingredient);
                        ingredientsList.setItemChecked(getSelectedPosition(ingredient), false);
                    }
                }

                adapter.notifyDataSetChanged();
                Toast.makeText(getContext(), getResources().getString(R.string.remove_all_ingredients_toast_msg), Toast.LENGTH_SHORT).show();
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

                for (String item : ingredients) {
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

        for (int index = 0; index < ingredients.length; index++) {
            if (ingredients[index].equals(ingredient)) {
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
