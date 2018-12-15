package com.example.kirill.cookbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FoodDatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "FOOD";
    public static final int DB_VERSION = 1;

    public FoodDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateFoodDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateFoodDatabase(db, oldVersion, newVersion);
    }

    private static void insertFood(SQLiteDatabase database, String category, String name,
                                   String ingredients, String recipe,
                                   int resource_id) {
        ContentValues foodValues = new ContentValues();
        foodValues.put("CATEGORY", category);
        foodValues.put("NAME", name);
        foodValues.put("INGREDIENTS", ingredients);
        foodValues.put("RECIPE", recipe);
        foodValues.put("IMAGE_RESOURCE_ID", resource_id);

        database.insert("FOOD", null, foodValues);
    }

    private void updateFoodDatabase(SQLiteDatabase database, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            database.execSQL("CREATE TABLE FOOD ("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "CATEGORY TEXT,"
                    + "NAME TEXT,"
                    + "INGREDIENTS TEXT,"
                    + "RECIPE TEXT,"
                    + "IMAGE_RESOURCE_ID INTEGER);");
            
            insertFood(database, "Snacks", "ЩУР", "1. Влад Щур;2. Артем Каралюк;3. Вадим Аникович",
                    "1. Помыть;2. Нарезать;3. Отварить", R.drawable.test);
            insertFood(database, "Snacks", "Pasta", "1. Сергей Холдеев;2. Артем Каралюк;3. Артем Епамченко",
                    "1. Купить;2. Высушить;3. Продать", R.drawable.pasta);
            insertFood(database, "Snacks", "Pizza", "1. Влад Пушко;2. Дмитрий Костеж;3. Дмитрий Чириков",
                    "1. Украсть;2. Отмыть;3. Скурить", R.drawable.pizza);
        }
    }
}
