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

            insertFood(database, "Snacks", "ЩУР", "Влад Щур; Артем Каралюк; Вадим Аникович",
                    "1. Помыть;2. Нарезать;3. Отварить", R.drawable.test);
            insertFood(database, "Snacks", "Pasta", "Сергей Холдеев; Артем Каралюк; Артем Епамченко",
                    "1. Купить;2. Высушить;3. Продать", R.drawable.pasta);
            insertFood(database, "Snacks", "Pizza", "Влад Пушко; Дмитрий Костеж; Дмитрий Чириков",
                    "1. Украсть;2. Отмыть;3. Скурить", R.drawable.pizza);
            insertFood(database, "Snacks", "Soup", "Самый; Ненавидимый; Мц",
                    "1. Вылечу;2. Вас;3. От", R.drawable.zamay);


            insertFood(database, "Salads", "Жак", "В; легких; снова",
                    "1. дым;2. я;3. не могу", R.drawable.a);
            insertFood(database, "Salads", "Энтони", "вдохнуть; воздух; но мне",
                    "1. плевать;2. я;3. бездыханным", R.drawable.b);
            insertFood(database, "Salads", "Окси", "останусь; если; так",
                    "1. надо;2. бездыханным;3. останусь", R.drawable.c);
            insertFood(database, "Salads", "Рики", "не; говори; что",
                    "1. я;2. не;3. могу", R.drawable.d);


            insertFood(database, "Main", "баста", "я; долго; искал",
                    "1. свой;2. путь;3. стиль", R.drawable.e);
            insertFood(database, "Main", "каста", "потратил; тут; столько",
                    "1. сил;2. чтобы;3. мой", R.drawable.f);
            insertFood(database, "Main", "гнойный", "мир; меня; простил",
                    "1. я;2. свеж;3. и", R.drawable.g);
            insertFood(database, "Main", "мармиладный", "молод; пока; я",
                    "1. продал;2. дух;3. с молотка", R.drawable.h);


            insertFood(database, "Desserts", "ЩУР", "Где же; начало; пути",
                    "1. найти;2. я;3. понимаю", R.drawable.w);
            insertFood(database, "Desserts", "Pasta", "улицы; тупо; нигеру",
                    "1. вырвали;2. сердце;3. Около", R.drawable.x);
            insertFood(database, "Desserts", "Pizza", "часа; вдыхаю; дым",
                    "1. в;2. эту;3. бездну", R.drawable.y);
            insertFood(database, "Desserts", "Soup", "бесталанный; soundboy; DJ",
                    "1. Будто;2. рукав;3. не пришей", R.drawable.z);
        }

        if (oldVersion < 2) {
            database.execSQL("ALTER TABLE FOOD ADD COLUMN FAVORITE NUMERIC");
        }
    }
}
