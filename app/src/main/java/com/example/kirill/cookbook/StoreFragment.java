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
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class StoreFragment extends Fragment {
    private Cursor cursor;
    private SQLiteDatabase database;

    private ListView ingredientsShopList;
    private TextView emptyList;
    private Button deleteButton;


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
        ShopDatabaseHelper openHelper = new ShopDatabaseHelper(inflater.getContext());
        try {
            database = openHelper.getWritableDatabase();
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

        deleteButton = (Button) view.findViewById(R.id.delete_shop);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAlert();
            }
        });

        // hide hint if list not empty
        emptyList = (TextView) view.findViewById(R.id.empty_list_hint);

        if (cursor != null && ingredientsShopList.getCount() != 0) {
            emptyList.setVisibility(View.GONE);
        } else {
            // hide button if list is empty
            deleteButton.setVisibility(View.GONE);
        }

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
                                deleteSelectedIngredients();
                                updateCursorAndListView();
                                Toast.makeText(getContext(), getString(R.string.delete_selected), Toast.LENGTH_SHORT).show();
                                break;
                            }
                            case 1: {
                                database.delete("SHOP", null, null);
                                updateCursorAndListView();
                                emptyList.setVisibility(View.VISIBLE);
                                deleteButton.setVisibility(View.GONE);
                                Toast.makeText(getContext(), getString(R.string.clear_shopping_list), Toast.LENGTH_SHORT).show();
                                break;
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

        if (cursor != null) {
            cursor.close();
        } else if (database != null) {
            database.close();
        }
    }

    private void updateCursorAndListView() {
        Cursor newCursor = database.query("SHOP", new String[]{"_id", "NAME"},
                null, null, null, null, null);

        CursorAdapter adapter = (CursorAdapter) ingredientsShopList.getAdapter();
        adapter.changeCursor(newCursor);
        adapter.notifyDataSetChanged();
        cursor = newCursor;
    }

    private void deleteSelectedIngredients() {
        SparseBooleanArray chosen = ingredientsShopList.getCheckedItemPositions();
        int listSize = ingredientsShopList.getCount();

        for (int i = 0; i < listSize; i++) {
            if (chosen.get(i)) {
                String ingredientName = ((Cursor)ingredientsShopList.getItemAtPosition(i)).getString(cursor.getColumnIndex("NAME"));
                database.delete("SHOP", "NAME = ?", new String[]{ingredientName});
            }
        }

        // show hint and hide button if was selected last item
        if (listSize == 1) {
            emptyList.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.GONE);
        }
    }
}
