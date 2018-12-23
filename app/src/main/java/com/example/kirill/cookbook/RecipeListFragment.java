package com.example.kirill.cookbook;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeListFragment extends ListFragment {
    public static final String BUNDLE_RECIPES = "recipes";
    private String[] recipes;


    public RecipeListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();

        if (args == null) {
            Toast.makeText(getContext(), "arguments is null", Toast.LENGTH_SHORT).show();
        } else {
            recipes = args.getStringArray(BUNDLE_RECIPES);
        }
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        getListView().setEnabled(false);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(inflater.getContext(),
//                android.R.layout.simple_list_item_1, recipes);
//
//        setListAdapter(adapter);
//
//        return super.onCreateView(inflater, container, savedInstanceState);
//    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // добавить свой макет для рецепта, сделать его больше по высоте строки списка

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, recipes);

        setListAdapter(adapter);
        getListView().setEnabled(false);
    }
}
