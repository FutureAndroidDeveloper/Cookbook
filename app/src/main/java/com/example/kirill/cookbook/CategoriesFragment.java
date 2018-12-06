package com.example.kirill.cookbook;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.Toast;


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
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

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

}
