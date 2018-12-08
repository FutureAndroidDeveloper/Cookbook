package com.example.kirill.cookbook;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class IngredientsFragment extends Fragment {
    private LayoutInflater layoutInflater;
    public static ArrayList<String> selectedItems;
    final String[] catNames = new String[]{"Рыжик", "Барсик", "Мурзик",
            "Мурка", "Васька", "Томасина", "Кристина", "Пушок", "Дымка",
            "Кузя", "Китти", "Масяня", "Симба"};

    public IngredientsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        selectedItems = new ArrayList<>();
        layoutInflater = inflater;

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ingredients, container, false);

        ListView ingredientsList = (ListView) view.findViewById(R.id.checkable_list);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(inflater.getContext(),
                R.layout.ingredient_list_item, R.id.text_line, catNames);

        ingredientsList.setAdapter(adapter);

        ingredientsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = ((TextView) view).getText().toString();

                if (selectedItem.contains(selectedItem)) {
                    selectedItems.remove(selectedItem);     // uncheck item
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

}
