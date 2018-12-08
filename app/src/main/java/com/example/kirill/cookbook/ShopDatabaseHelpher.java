package com.example.kirill.cookbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ShopDatabaseHelpher extends SQLiteOpenHelper {
    public static final String DB_NAME = "shop";
    public static final int DB_VERSION = 1;

    public ShopDatabaseHelpher(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }

    private static void insertShop(SQLiteDatabase database, String name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", name);
        database.insert("SHOP", null, contentValues);
    }

    private void updateMyDatabase(SQLiteDatabase database, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            database.execSQL("CREATE TABLE SHOP ( "
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT);");
        }
    }
}
