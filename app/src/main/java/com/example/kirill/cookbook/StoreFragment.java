package com.example.kirill.cookbook;


import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class StoreFragment extends Fragment {
    private Cursor cursor;
    private SQLiteDatabase database;


    public StoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_store, container, false);

        ListView ingredientsShopList = (ListView) view.findViewById(R.id.checkable_ingredients_shop);

        // Create cursor
        SQLiteOpenHelper openHelper = new ShopDatabaseHelper(inflater.getContext());
        try {
            database = openHelper.getReadableDatabase();
            cursor = database.query("SHOP",                  // get all ingredients from DB
                    new String[]{"_id", "NAME"},
                    null, null, null, null, null);

            SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(inflater.getContext(),
                    R.layout.ingredient_list_item,
                    cursor,
                    new String[]{"NAME"},
                    new int[] {R.id.text_line},0);
            ingredientsShopList.setAdapter(cursorAdapter);
        } catch (SQLException e) {
            Toast.makeText(inflater.getContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        database.close();

    }
}
