package com.example.kirill.cookbook;

public class FoodGridHelper {
    public static final int FIRST_GRID_INDEX = 0;
    public static final int SECOND_GRID_INDEX = 1;
    public static final int THIRD_GRID_INDEX = 2;
    public static final int FOURTH_GRID_INDEX = 3;

    public static boolean wasCreated = false;

    public static int[][] images = new int[CategoriesFragment.gridRowCount][CategoriesFragment.gridColumnCount];
    public static String[][] names = new String[CategoriesFragment.gridRowCount][CategoriesFragment.gridColumnCount];
    public static int[][] categoriesIds = new int[CategoriesFragment.gridRowCount][CategoriesFragment.gridColumnCount];
}
