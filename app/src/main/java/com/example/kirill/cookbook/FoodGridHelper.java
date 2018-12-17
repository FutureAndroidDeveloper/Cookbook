package com.example.kirill.cookbook;

public class FoodGridHelper {
    public static boolean wasCreated = false;

    public static int[][] images = new int[CategoriesFragment.gridRowCount][CategoriesFragment.gridColumnCount];
    public static String[][] names = new String[CategoriesFragment.gridRowCount][CategoriesFragment.gridColumnCount];
    public static int[][] categoriesIds = new int[CategoriesFragment.gridRowCount][CategoriesFragment.gridColumnCount];
}
