package com.example.kirill.cookbook;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {
    public static final int FAVORITE_VALUE = 1;
    public static final int UN_FAVORITE_VALUE = 0;

    private SQLiteDatabase database;
    private Cursor favoriteCursor;

    private ArrayList<Integer> foodIds;
    private ArrayList<String> captions;
    private ArrayList<Integer> imagesIds;

    private RecyclerView favoriteFoodRecycler;


    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        favoriteFoodRecycler = (RecyclerView) view.findViewById(R.id.food_recycler);
        foodIds = new ArrayList<>();
        captions = new ArrayList<>();
        imagesIds = new ArrayList<>();

        FoodDatabaseHelper databaseHelper = new FoodDatabaseHelper(getContext());
        try {
            database = databaseHelper.getWritableDatabase();
            favoriteCursor = database.query("FOOD",
                    new String[]{"_id", "NAME", "IMAGE_RESOURCE_ID"},
                    "FAVORITE = ?",
                    new String[]{Integer.toString(FAVORITE_VALUE)},
                    null, null, null, null);

            // filling lists with information
            while (favoriteCursor.moveToNext()) {
                foodIds.add(favoriteCursor.getInt(favoriteCursor.getColumnIndex("_id")));
                captions.add(favoriteCursor.getString(favoriteCursor.getColumnIndex("NAME")));
                imagesIds.add(favoriteCursor.getInt(favoriteCursor.getColumnIndex("IMAGE_RESOURCE_ID")));
            }

            CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(captions, imagesIds, foodIds);
            favoriteFoodRecycler.setAdapter(adapter);

            // set layoutManager
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                    LinearLayoutManager.VERTICAL, false);
            favoriteFoodRecycler.setLayoutManager(layoutManager);

            favoriteFoodRecycler.setNestedScrollingEnabled(false);

            // set listener in cardView
            adapter.setListener(new CaptionedImagesAdapter.Listener() {
                @Override
                public void onClick(int id) {
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra(DetailActivity.EXTRA_FOOD_ID, id);
                    getActivity().startActivity(intent);
                }
            });

        } catch (SQLException e) {
            Toast.makeText(getContext(), getString(R.string.favorite_was_removed_toast),
                    Toast.LENGTH_SHORT).show();
        }

        Button deleteButton = (Button) view.findViewById(R.id.delete_favorite);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAlert();
            }
        });

        // hide hint if list not empty
        TextView emptyRecycler = (TextView) view.findViewById(R.id.empty_recycler_hint);

        if (favoriteCursor != null && !captions.isEmpty()) {
            emptyRecycler.setVisibility(View.GONE);
        } else {
            // hide button if list is empty
            deleteButton.setVisibility(View.GONE);
        }

        return view;
    }

    private void showDialogAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getResources().getString(R.string.favorite_dialog_title));
        builder.setMessage(getResources().getString(R.string.favorite_dialog_message));

        // add the buttons
        builder.setPositiveButton(getResources().getString(R.string.favorite_dialog_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteFavorite();
                updateFavoriteCursor();
                Toast.makeText(getContext(), "DELETED ALL", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.favorite_dialog_cancel), null);

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteFavorite() {
        ContentValues favoriteValue = new ContentValues();
        favoriteValue.put("FAVORITE", UN_FAVORITE_VALUE);

        if (database != null) {
            database.update("FOOD",
                    favoriteValue,
                    "FAVORITE = ?",
                    new String[]{Integer.toString(FAVORITE_VALUE)});
        }
    }

    private void updateFavoriteCursor() {
        Cursor newFavoriteCursor = database.query("FOOD",
                new String[]{"_id", "NAME", "IMAGE_RESOURCE_ID"},
                "FAVORITE = ?",
                new String[]{Integer.toString(FAVORITE_VALUE)},
                null, null, null, null);

        captions.clear();
        imagesIds.clear();

        CaptionedImagesAdapter adapter = (CaptionedImagesAdapter) favoriteFoodRecycler.getAdapter();
        adapter.notifyDataSetChanged();

        favoriteCursor = newFavoriteCursor;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (favoriteCursor != null) {
            favoriteCursor.close();
        } else if (database != null) {
            database.close();
        }
    }
}
