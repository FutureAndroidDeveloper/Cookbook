package com.example.kirill.cookbook;

public class Food {
    private String name;
    private int imageResourceId;

    public static final Food[] foods = {
            new Food("Name_1", R.drawable.diavolo),
            new Food("Name_2", R.drawable.farfalle),
            new Food("Name_3", R.drawable.fiori)
    };

    Food(String name, int imageResourceId) {
        this.name = name;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}
