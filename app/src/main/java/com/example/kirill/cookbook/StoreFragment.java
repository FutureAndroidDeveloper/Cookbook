package com.example.kirill.cookbook;


import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class StoreFragment extends Fragment {
    private Cursor cursor;
    private SQLiteDatabase database;
    private ListView ingredientsShopList;


    public StoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_store, container, false);

        ingredientsShopList = (ListView) view.findViewById(R.id.checkable_ingredients_shop);

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

        Button deleteButton = (Button) view.findViewById(R.id.delete_shop);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAlert();
            }
        });

        return view;
    }

    public void showDialogAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getResources().getString(R.string.shop_dialog_title))
                .setItems(R.array.dialog_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0: {
                                Toast.makeText(getContext(), "DELETED SELECTED", Toast.LENGTH_SHORT).show();
                            }
                            case 1: {
                                database.delete("SHOP", null, null);
                                deleteIngredientsShoppingList();
                                Toast.makeText(getContext(), "DELETED ALL", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        database.close();
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        Cursor newCursor = database.query("SHOP", new String[]{"_id", "NAME"},
//                null, null, null, null, null);
//        CursorAdapter adapter = (CursorAdapter) ingredientsShopList.getAdapter();
//        adapter.changeCursor(newCursor);
//        cursor = newCursor;
//    }

    private void deleteIngredientsShoppingList() {
        Cursor newCursor = database.query("SHOP", new String[]{"_id", "NAME"},
                null, null, null, null, null);
        CursorAdapter adapter = (CursorAdapter) ingredientsShopList.getAdapter();
        adapter.changeCursor(newCursor);
        cursor = newCursor;
    }
}
