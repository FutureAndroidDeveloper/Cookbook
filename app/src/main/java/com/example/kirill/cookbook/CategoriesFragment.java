package com.example.kirill.cookbook;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.GridView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesFragment extends Fragment {


    public CategoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_categories, container, false);

        int[] images = new int[Food.foods.length];
        String[] names = new String[Food.foods.length];
        for (int i = 0; i < Food.foods.length; i++) {
            names[i] = Food.foods[i].getName();
            images[i] = Food.foods[i].getImageResourceId();
        }


        GridView gridView = (GridView) view.findViewById(R.id.gridViewNew);
        gridView.setAdapter(new CardGridAdapter(this.getContext(), names, images, R.layout.card_captioned_image));

        return view;
    }

}
