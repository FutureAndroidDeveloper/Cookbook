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

            insertFood(database, "Snacks", "Basic latkes",
                    "2 large Maris Piper potatoes , (600g total); " +
                            "1 onion , optional; " +
                            "4 tablespoons plain flour; " +
                            "2 large free-range eggs; " +
                            "vegetable oil , for frying; " +
                            "½ a bunch of fresh woody herbs , such; " +
                            "as sage, rosemary, thyme (15g)",

                    "1. Scrub the potatoes clean, then coarsely grate (skin and all). " +
                            "Peel and finely slice the onion (if using).; " +
                            "2. Place in the middle of a clean tea towel and wring out the liquid, " +
                            "then transfer to a large bowl.; " +
                            "3. Add the flour and crack in the eggs, season with sea salt and black " +
                            "pepper, then mix well to combine.; " +
                            "4. Place a large frying pan on a medium heat with enough oil to cover" +
                            " the bottom of the pan.; " +
                            "5. Shape small handfuls of the potato mixture " +
                            "(roughly the same size as a golf balls) into latkes, flattening gently," +
                            " then carefully add to the pan – you may need to work in batches.; " +
                            "6. Fry for 1½ to 2 minutes on each side, or until golden and crisp," +
                            " then drain on kitchen paper.; " +
                            "7. Pick the herbs and scatter into the pan with the remaining oil." +
                            " Fry for a few seconds until crisp, then remove to the kitchen paper" +
                            " before sprinkling over the hot latkes.",

                    R.drawable.latkes);

            insertFood(database, "Snacks", "Spiced aubergine dip",
                    "2 large aubergines; " +
                            "2 onions; " +
                            "4 cloves of garlic; " +
                            "1-2 green chillies; " +
                            "4cm piece of ginger; " +
                            "4 ripe tomatoes; " +
                            "1 bunch of fresh coriander; " +
                            "1 tablespoon vegetable oil; " +
                            "2 tablespoons curry powder; " +
                            "oven-baked corn tortillas",

                    "1. Preheat the oven to 220ºC/gas 7.; " +
                            "2. Prick the aubergines all over with a fork, then place on a " +
                            "roasting tray. Roast for 50 minutes to 1 hour, or until the skin" +
                            " blackens and chars and the flesh can be easily pierced with a spoon." +
                            " Allow to cool to room temperature.; " +
                            "3. Cut the cooled aubergines in half, scoop the flesh into a food " +
                            "processor and pulse until smooth. Set aside.; " +
                            "4. Peel and finely chop the onion and garlic, trim and finely slice " +
                            "the chillies, and peel and finely grate the ginger (you should end " +
                            "up with roughly 2 teaspoons). Roughly chop 3 of the tomatoes and slice" +
                            " the remaining, then pick and finely chop the coriander leaves.; " +
                            "5. Heat the oil in a large frying pan. Add the onion and sauté for " +
                            "5 to 6 minutes, or until softened.; " +
                            "6. Add the garlic, ginger and chilli, then stir-fry for 1 to 2 " +
                            "minutes. Stir in the tomatoes and curry powder and cook for a further " +
                            "12 to 15 minutes, or until softened.; " +
                            "7. Next, add the reserved aubergine and cook, for 3 to 4 minutes," +
                            " stirring regularly. Stir in the coriander, then remove from the heat.; " +
                            "8. Garnish with the extra tomato slices, and serve with the oven-baked" +
                            " corn tortilla",

                    R.drawable.spiced_aubergine_dip);
            insertFood(database, "Snacks", "Chipotle-fried fish & clementine bites",
                    "400 g white fish fillets , such as pollock, haddock, cod, skin " +
                            "off, pin-boned, from sustainable sources; " +
                            "2 limes; " +
                            "1 teaspoon dried chipotle flakes , plus ; " +
                            "extra to serve; " +
                            "1 teaspoon ground coriander; " +
                            "150 g cornflour; " +
                            "150 ml milk; " +
                            "1 litre vegetable oil , for frying; " +
                            "2 clementines",

                    "1. Cut the fish into 2.5cm chunks, season with sea salt and black " +
                            "pepper, squeeze over the juice of 1 lime and set aside for 10 minutes.; " +
                            "2. Pick and finely chop most of the fresh coriander leaves for the" +
                            " dip (reserving some for a garnish) and stir through the yoghurt in a" +
                            " small bowl.; " +
                            "3. Peel and crush the garlic, and add to the yoghurt along with " +
                            "the zest of the clementine half. Season to taste and set aside.; " +
                            "4. Crush the chipotle flakes using a pestle and mortar. Transfer" +
                            " to a large, shallow dish with the ground coriander, cornflour and" +
                            " a pinch of salt, then mix well. Pour the milk into a separate bowl.; " +
                            "5. Pour the oil into a large, thick-bottomed saucepan to a depth of" +
                            " around 6cm (it should be no more than two-thirds full). Heat it to" +
                            " 170ºC/325ºF (if you don’t have a thermometer, dip the end of a wooden" +
                            " spoon in the oil – if it starts bubbling rapidly around the spoon," +
                            " it’s ready).; " +
                            "6. Slice the clementines as thinly as you can – about 1mm. This can" +
                            " be fiddly, so don’t worry about getting perfect rounds.; " +
                            "7. Pat the fish and clementine slices with kitchen paper to soak" +
                            " up any excess juice. Coat them in the cornflour, then dip briefly " +
                            "in the milk before coating again in flour, shaking off the excess.; " +
                            "8. Deep fry the fish and clementine in batches for 2 to 3 minutes," +
                            " or until golden.; " +
                            "9. Drain on kitchen paper and scatter with salt, chipotle and the" +
                            " reserved coriander. Serve immediately with the dip and the remaining" +
                            " lime cut into wedges.",

                    R.drawable.chipotle_fried_fish_clementine_bites);

            insertFood(database, "Snacks", "Pork & apple sausage rolls",
                    "1 leek; " +
                            "1 Royal Gala apple; " +
                            "4 sprigs of fresh thyme; " +
                            "500 g minced pork; " +
                            "½ teaspoon mustard seeds; " +
                            "500 g puff pastry; " +
                            "1 large free-range egg",

                    "1. Preheat the oven to 200°C/400°F/gas 6.; " +
                            "2. Wash, trim and dice the leek, core and dice the apple, then " +
                            "pick the thyme leaves.; " +
                            "3. Combine the pork, leek, apple, thyme leaves and mustard " +
                            "seeds in a bowl. Season and set aside.; " +
                            "4. Roll out the pastry to 1cm thick and 34cm x 30cm. Halve " +
                            "lengthways and place a strip of mince down the centre of each.; " +
                            "5. Brush the edges with beaten egg, roll up and seal. Brush with " +
                            "more egg, then cut each strip into 3 rolls.; " +
                            "6. Score the tops and bake for 20 minutes, or until golden and" +
                            " cooked through.",

                    R.drawable.pork_apple_sausage_rolls);




            insertFood(database, "Salads", "Roast chicken & crispy bread salad",
                    "6 free-range chicken thighs , skin on, bone out; " +
                            "olive oil; " +
                            "снова; " +
                            "1 lemon; " +
                            "4 cloves of garlic; " +
                            "a few sprigs of fresh thyme; " +
                            "150 g ciabatta; " +
                            "50 g pine nuts; " +
                            "extra virgin olive oil; " +
                            "1 tablespoon runny honey; " +
                            "25 g raisins; " +
                            "90 g rocket",

                    "1. Preheat the oven to 200ºC/400ºF/gas 6.; " +
                            "2. Put the chicken in a baking dish and rub with a little olive oil." +
                            " Quarter the lemon and arrange in the dish with the unpeeled garlic." +
                            " Tuck the thyme around the chicken, season well with sea salt and" +
                            " black pepper and roast for 30 minutes.; " +
                            "3. Remove from the oven, then pick out and reserve the lemon and" +
                            " garlic. Tear the ciabatta into bite-sized pieces and add, tossing" +
                            " to coat in the juices, then scatter over the pine nuts." +
                            "4. Return the dish to the oven, reduce the temperature to" +
                            " 180ºC/350ºF/gas 4 and cook for 10 minutes, stirring halfway through.; " +
                            "5. Meanwhile, in a small bowl mix 2 tablespoons of extra virgin " +
                            "olive oil with the honey, then squeeze in the soft, sweet" +
                            " garlic (discarding the skins) and the juice from the roasted" +
                            " lemon, mashing as you go, until combined. Season to taste.; " +
                            "6. Remove the dish from the oven, strip over the roasted thyme " +
                            "leaves, stir in the raisins and pour over the dressing." +
                            " Cut the chicken into thick slices, then fold through the" +
                            " rocket, and serve straight from the dish or plate up.",

                    R.drawable.roast_chicken);

            insertFood(database, "Salads", "Drunken broad bean & goat’s cheese salad",
                    "250 g podded broad beans; " +
                            "150 ml white wine; " +
                            "50 ml white wine vinegar; " +
                            "2 tablespoons caster sugar; " +
                            "2 spring onions; " +
                            "extra virgin olive oil; " +
                            "100 g sourdough bread; " +
                            "1 handful of pea shoots; " +
                            "½ a small bunch of fresh mint; " +
                            "100 g goat's cheese; " +
                            "1 lemon; " +
                            "2 test; " +
                            "3 test; ",

                    "1. Blanch the broad beans in boiling salted water for 2 minutes." +
                            " Drain and refresh in cold water. Squeeze most of the beans out" +
                            " of their skins, leaving the smaller ones in their skins for texture.; " +
                            "2. In a bowl, combine the wine, vinegar and sugar. Stir to dissolve" +
                            " the sugar, add the beans and leave to marinate for 1 hour.; " +
                            "3. Preheat the oven to 190ºC/375ºF/gas 5.; " +
                            "4. Trim and chop the spring onions and blitz in a food processor" +
                            " with 4 tablespoons of oil.;" +
                            "5. Tear the bread into chunks, scatter over a baking tray," +
                            " drizzle with the onion oil and scrunch with your hands. Bake " +
                            "for 10 minutes, until crunchy.",

                    R.drawable.drunken_broad_bean);

            insertFood(database, "Salads", "Окси", "останусь; если; так",
                    "1. надо; 2. бездыханным; 3. останусь", R.drawable.c);
            insertFood(database, "Salads", "Рики", "не; говори; что",
                    "1. я; 2. не; 3. могу", R.drawable.d);


            insertFood(database, "Main", "баста", "я; долго; искал",
                    "1. свой; 2. путь; 3. стиль", R.drawable.e);
            insertFood(database, "Main", "каста", "потратил; тут; столько",
                    "1. сил; 2. чтобы; 3. мой", R.drawable.f);
            insertFood(database, "Main", "гнойный", "мир; меня; простил",
                    "1. я; 2. свеж; 3. и", R.drawable.g);
            insertFood(database, "Main", "мармиладный", "молод; пока; я",
                    "1. продал; 2. дух; 3. с молотка", R.drawable.h);


            insertFood(database, "Desserts", "ЩУР2", "Где же; начало; пути",
                    "1. найти; 2. я; 3. понимаю", R.drawable.w);
            insertFood(database, "Desserts", "Pasta2", "улицы; тупо; нигеру",
                    "1. вырвали; 2. сердце; 3. Около", R.drawable.x);
            insertFood(database, "Desserts", "Pizza2", "часа; вдыхаю; дым",
                    "1. в; 2. эту; 3. бездну", R.drawable.y);
            insertFood(database, "Desserts", "Soup2", "бесталанный; soundboy; DJ",
                    "1. Будто; 2. рукав; 3. не пришей", R.drawable.z);
        }

        if (oldVersion < 2) {
            database.execSQL("ALTER TABLE FOOD ADD COLUMN FAVORITE NUMERIC");
        }
    }
}
