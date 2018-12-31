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
        // get arguments from activity

        Bundle args = getArguments();

        if (args == null) {
            Toast.makeText(getContext(), "arguments is null", Toast.LENGTH_SHORT).show();
        } else {
            recipes = args.getStringArray(BUNDLE_RECIPES);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recipe_list_fragment, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // set my layout as row in list

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                R.layout.list_fragment_item, recipes);

        setListAdapter(adapter);
//        getListView().setEnabled(true);
    }
}
