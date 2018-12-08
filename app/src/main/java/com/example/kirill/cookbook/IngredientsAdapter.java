package com.example.kirill.cookbook;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class IngredientsAdapter extends BaseAdapter {
    private String[] ingredients;
    private int layout;
    private LayoutInflater inflater;

    public IngredientsAdapter(Context context, String[] ingredients, int layout) {
        this.inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.ingredients = ingredients;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return ingredients.length;
    }

    @Override
    public Object getItem(int position) {
        return ingredients[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
        }

        final TextView listText = (TextView) convertView.findViewById(R.id.ingredient_text);
        final CheckBox listCheck = (CheckBox) convertView.findViewById(R.id.ingredient_check);
        listText.setText(ingredients[position]);
        listCheck.setChecked(false);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Revers the checkbox
                listCheck.setChecked(!listCheck.isChecked());

                if (listCheck.isChecked()) {

                    IngredientsFragment.BdNames.add(listText.getText().toString());
                } else {
                    Toast.makeText(inflater.getContext(), "DELETE", Toast.LENGTH_SHORT).show();
                }

                //Toast.makeText(inflater.getContext(), "selected item: " + listText.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }
}