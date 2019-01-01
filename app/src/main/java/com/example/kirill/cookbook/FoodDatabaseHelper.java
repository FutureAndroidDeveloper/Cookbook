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

            insertFood(database, "Salads", "Italian spring bean salad",
                    "1 stick of celery , (60g); " +
                            "½ a carrot , (60g); " +
                            "120 g radicchio; " +
                            "10 g fresh basil; " +
                            "1½ x 400 g tin of beans , such as borlotti or kidney; " +
                            "1 tablespoon chopped onion; " +
                            "130 ml extra virgin olive oil; " +
                            "80 ml white wine vinegar; " +
                            "75 g Parmesan rinds , (about 3); " +
                            "100 g stale bread",

                    "1. Trim the celery, peel and trim the carrot, then cut, along with" +
                            " the radicchio, into 5mm cubes. Pick and finely chop the basil leaves.; " +
                            "2. Drain and rinse the beans and place in a bowl (save the remainder" +
                            " for another dish) with the radicchio, celery, carrot, onion and" +
                            " basil. Stir in the olive oil and vinegar, then season and leave " +
                            "to rest for 2 hours at room temperature.; " +
                            "3. Meanwhile, place the Parmesan rinds in a pot with 1.5 litres " +
                            "of cold water and cook slowly over a low–medium for 1 hour." +
                            " Remove from the heat and leave the rinds in the water to cool.; " +
                            "4. Preheat the oven to 200ºC/400ºF/gas 6.; " +
                            "5. Cut the bread into 1cm cubes, scatter over a baking tray and" +
                            " toast in the oven until golden, about 20 minutes, tossing halfway through.; " +
                            "6. Divide the bean mixture between plates, then add a few " +
                            "tablespoons of the cooled parmesan broth. Garnish with croutons" +
                            " and the Parmesan rinds, finely sliced with a speed-peeler, if you like.",

                    R.drawable.italian_spring_bean_salad);

            insertFood(database, "Salads", "Beetroot, carrot & orange salad",
                    "750 g carrots; " +
                            "500 g raw beetroot; " +
                            "olive oil; " +
                            "2 oranges; " +
                            "1 tablespoon sesame seeds; " +
                            "½ a bunch of fresh coriander; " +
                            "extra virgin olive oil",

                    "1. Preheat the oven to 200°C/400ºF/gas 6; " +
                            "2. Trim, peel and halve the carrots, then scrub the beets clean," +
                            " and chop into wedges.; " +
                            "3. Parboil the carrots in a large pan of boiling salted water for" +
                            " 5 minutes, then transfer them to a colander using a slotted spoon.; " +
                            "4. Carefully lower in the beets and parboil for 5 minutes," +
                            " then drain (parboiling separately will stop the carrots from" +
                            " turning purple).; " +
                            "5. Transfer the carrots and beets to a large roasting tin, " +
                            "drizzle with olive oil and season with sea salt and black pepper." +
                            " Roast for 30 to 40 minutes, or until sticky and shiny, jiggling" +
                            " the tray occasionally for even cooking.; " +
                            "6. Meanwhile, finely grate the orange zest. Trim off" +
                            " the skin and pith, then cut the orange into segments.; " +
                            "7. Toast the sesame seeds in a dry pan on a low heat for a couple" +
                            " of minutes or until golden, tossing regularly. Pick and roughly" +
                            " chop the coriander leaves.; " +
                            "8. When the time's up, let the roasted veg cool a little, " +
                            "then toss with the orange zest and segments, a good lug of extra" +
                            " virgin olive oil and a little extra seasoning, if needed.; " +
                            "9. Arrange over a large platter, scatter over the toasted sesame" +
                            " seeds and coriander leaves, then tuck in.",

                    R.drawable.beetroot_orange_salad);





            insertFood(database, "Main", "Mark Hamill's roast sirloin & Yorkshire puddings",
                    "4 large free-range eggs; " +
                            "150 g plain flour; " +
                            "175 ml whole milk; " +
                            "50 g beef dripping; " +
                            "2 kg beef bones , with bone marrow; " +
                            "2 large leeks; " +
                            "2 red onions; " +
                            "2 heaped tablespoons plain flour; " +
                            "100 ml red wine; " +
                            "100 ml port; " +
                            "2 kg whole dry-aged sirloin of beef; " +
                            "olive oil; " +
                            "40 g black peppercorns; " +
                            "3 sprigs of fresh rosemary",

                    "1. Ideally, make your Yorkie batter the night before. Whisk the eggs," +
                            " flour, milk, 25ml of water and a pinch of sea salt to a smooth" +
                            " batter, then pop into the fridge overnight, removing when you" +
                            " preheat the oven for the meat (or, as a minimum, make the day you " +
                            "need it but leave to rest at room temperature for at least 30 minutes).; " +
                            "2. Preheat the oven to 180ºC/350ºF/gas 4.; " +
                            "3. For the gravy, place the bones in a large roasting tray. Trim, " +
                            "wash and roughly chop the leeks, quarter the unpeeled onions, then" +
                            " add to the tray and roast for 45 minutes, or until golden brown.; " +
                            "4. Remove the bones and veg to a large pot, keeping the tray of" +
                            " juices to one side. Top up the pot with 2.5 litres of water," +
                            " bring to the boil, then reduce to a simmer for at least 2 hours" +
                            " 30 minutes, or until the liquid has reduced by half.; " +
                            "5. Place the tray over a medium heat on the hob, then add the " +
                            "flour and stir well to pick up any sticky bits from the bottom." +
                            " Pour in the wine and port, leave to bubble away for 1 minute," +
                            " then gradually whisk in a few ladles of the stock, before" +
                            " tipping it all back into the pot.; " +
                            "6. Simmer gently for a further 2 hours, or until you reach " +
                            "your desired consistency.; " +
                            "7. Lift out the bones and strain the gravy, skimming off any" +
                            " fat from the surface, then adjust the seasoning, if needed." +
                            " Keep aside to reheat at the last minute.; " +
                            "8. When you’re ready, remove the beef from the fridge and leave to" +
                            " come up to room temperature. Turn the oven up to full whack" +
                            " (240ºC/475ºF/gas 9).; " +
                            "9. Score the beef fat in a criss-cross fashion, " +
                            "then rub with 1 tablespoon of oil.; " +
                            "10. In a blender, blitz the peppercorns, 1 tablespoon of salt" +
                            "and the rosemary leaves to a fine dust, then sprinkle and pat" +
                            " all over the beef.; " +
                            "11. Place a large roasting tray on a medium-high heat, " +
                            "carefully sear the beef on all sides, then transfer to the oven.; " +
                            "12. Immediately reduce the temperature to 180ºC/350ºF/gas 4 and" +
                            " roast for 50 minutes – this will give you medium-rare" +
                            " (cook for a little longer, if you prefer) – then remove to a board." +
                            " Cover and rest for 30 minutes.; " +
                            "13. Turn the oven up to 220ºC/425ºF/gas 7.; " +
                            "14. Divide the dripping between a 6-well deep Yorkshire pudding" +
                            " tray (8.3g per well, if you want to be super-scientific about " +
                            "it!), then place on the middle shelf of the oven for 5 minutes," +
                            " or until the fat is smoking hot.; " +
                            "15. Quickly but carefully pour the batter into the wells – each" +
                            " should be between half and three-quarters full. Immediately" +
                            " return to the oven and bake for 25 minutes, or until they have" +
                            " quadrupled in volume, are deep golden all over and sound hollow" +
                            " when tapped.; " +
                            "16. Carve and serve up the beef, adding a Yorkshire pudding to each" +
                            " plate, then drizzle with gravy (reheat, if needed). Delicious" +
                            " served with pinches of lemon-dressed watercress, horseradish and" +
                            " crispy roast potatoes.",

                    R.drawable.mark_hamill_roast_sirloin);

            insertFood(database, "Main", "Crispy skin lemon sole",
                    "½ x 280 g jar of artichoke hearts in oil; " +
                            "2 mixed-colour courgettes; " +
                            "1 bunch of fresh mint , (30g); " +
                            "2 x 200 g sides of flat white fish , such as lemon sole, skin on," +
                            " scaled, from sustainable sources; " +
                            "1-2 fresh mixed-colour chillies",

                    "1. Preheat the grill to high. Scoop out the artichokes, halve" +
                            " lengthways and place in a large non-stick ovenproof frying pan on" +
                            " a medium heat with 1 tablespoon of oil from their jar. Quarter the" +
                            " courgettes lengthways, cut out the core, slice them at an angle the" +
                            " same size as the artichokes and add to the pan. Cook for 10 minutes," +
                            " stirring regularly. Finely slice the top leafy half of the mint," +
                            " tossing half into the pan with a splash of water.; " +
                            "2. Rub the sole with a little olive oil, sea salt and black pepper," +
                            " then lay skin side up on the veg. Place the pan directly under the" +
                            " grill for 7 to 10 minutes, or until the skin is wonderfully crisp" +
                            " – keep an eye on it! Meanwhile, finely slice the chillies, mix as" +
                            " much as you dare with the remaining mint, 2 tablespoons of red wine" +
                            " vinegar and 1 tablespoon of extra virgin olive oil, then taste and" +
                            " season to perfection. Plate up the veg and sole, pulling back half" +
                            " the crispy skin to expose the fish, then drizzle over the chilli" +
                            " mint dressing.",

                    R.drawable.crispy_skin_lemon_sole);

            insertFood(database, "Main", "Sausage linguine",
                    "150 g dried linguine; " +
                            "200 g broccoletti , or sprouting broccoli; " +
                            "olive oil; " +
                            "1 large higher-welfare sausage , (125g); " +
                            "1 clove of garlic; " +
                            "2 anchovy fillets in oil , from sustainable sources; " +
                            "1 pinch of dried red chilli flakes; " +
                            "100 ml Frascati white wine; " +
                            "20 g pecorino , or Parmesan cheese; " +
                            "extra virgin olive oil",

                    "1. Cook the pasta in a pan of boiling salted water according to the" +
                            " packet instructions, then drain, reserving a mugful of starchy" +
                            " cooking water.; " +
                            "2. Meanwhile, trim the broccoletti (halving any thick stalks" +
                            " lengthways to make them more delicate to eat). Place a large frying " +
                            "pan on a medium heat with 1 tablespoon of olive oil. Squeeze the" +
                            " sausagemeat out of the skin into the pan, breaking it up with a" +
                            " wooden spoon. Once lightly golden, peel, roughly chop and add the" +
                            " garlic, followed by the anchovies, chilli flakes, broccoletti and" +
                            " wine. Leave to bubble away while the pasta cooks.; " +
                            "3. Toss the drained pasta into the sausage pan, then finely grate" +
                            " over the pecorino and drizzle with extra virgin olive oil. Toss" +
                            " again, loosening with a little reserved cooking water, if needed," +
                            " to create a light, creamy sauce. Taste and season to perfection," +
                            " then serve right away.",

                    R.drawable.linguine);

            insertFood(database, "Main", "Chicken tikka skewers",
                    "2 lemons; " +
                            "olive oil; " +
                            "2 teaspoons Patak's tikka masala spice paste; " +
                            "2 tablespoons natural yoghurt , plus extra to serve; " +
                            "½ a small ripe pineapple; " +
                            "3 fresh red chillies; " +
                            "2 skinless free-range chicken breasts; " +
                            "1-2 little gem lettuces; " +
                            "½ a bunch of fresh coriander (15g) , optional",

                    "1. For the marinade, finely grate the zest of 1 lemon and reserve" +
                            " for garnish, then halve and squeeze the juice into a small bowl. " +
                            "Add 1 teaspoon of oil, the paste and yoghurt, then mix well.; " +
                            "2. Peel the pineapple, removing any little gnarly bits, then " +
                            "cut into quarters. Remove and discard the core, then cut into " +
                            "1cm thick slices and add to the bowl with the marinade.; " +
                            "3. Trim, halve and deseed the chillies, then add to the marinade." +
                            " Slice the chicken into 2cm chunks and add to the mix.; " +
                            "4. Toss together to coat, then place in the fridge to marinate for" +
                            " at least 2 hours, preferably overnight.; " +
                            "5. When you’re ready to cook, remove the chicken and pineapple" +
                            " mixture from the fridge, and pick out and tear the chilli into" +
                            " smaller pieces. Starting with the chicken, thread the ingredients" +
                            " onto skewers, alternating between the ingredients, as you go. Pour" +
                            " any remaining marinade over the the top and drizzle with a little oil.; " +
                            "6. Put a dry, non-stick pan on a medium heat, add the skewers and" +
                            " cook for 7 to 10 minutes, or until the chicken is cooked through," +
                            " turning no more than three times and seasoning on the first turn" +
                            " with a little sea salt.; " +
                            "7. Trim the lettuce, then click off leaves to make little cups." +
                            " Shave the chicken, pineapple and chilli off the skewers with a" +
                            " sharp knife, then scatter over the reserved lemon zest and pick" +
                            " over the coriander leaves (if using).; " +
                            "8. Slice the remaining lemon into wedges for squeezing over and serve" +
                            " with the lettuce cups and a dollop of yoghurt, if you like.",

                    R.drawable.chicken_tikka_skewers);





            insertFood(database, "Desserts", "Chocolate clementine torte",
                    "350 g quality dark chocolate , (70%); " +
                            "250 g unsalted butter; " +
                            "5 large free-range eggs; " +
                            "250 g white caster sugar; " +
                            "200 ml fresh clementine juice , (roughly 15 clementines)",

                    "1. Preheat the oven to 120°C/250°F/gas ¾. Snap the chocolate and melt" +
                            " in a heatproof bowl with 225g of the butter and a pinch of sea salt" +
                            " over a pan of gently simmering water until smooth and glossy," +
                            " stirring occasionally. Whisk the eggs and sugar in a freestanding" +
                            " mixer on a high speed until light, fluffy and tripled in size.; " +
                            "2. Grease a 25cm round cake tin all over with the remaining butter " +
                            "and line the base with greaseproof paper. Remove the chocolate from " +
                            "the heat, leave to stand for a couple of minutes, then pour it into " +
                            "the egg mixture. Whisk on a medium speed to combine, then pour into " +
                            "the lined tin. Give a few taps on the work surface to bring the air " +
                            "bubbles to the surface, then place the tin into a shallow roasting " +
                            "tray. Place the tray in the oven, then carefully pour enough boiling " +
                            "kettle water into the tray to come halfway up the side of the cake " +
                            "tin. Gently slide the shelf back in and bake for 35 to 40 minutes, " +
                            "or until set but with a slight wobble. ; " +
                            "3. Squeeze the clementine juice through a sieve into a small pan and " +
                            "simmer over a low heat for 20 minutes, or until syrupy enough to " +
                            "coat the back of a spoon. Carefully pour it into a bowl and leave to cool.; " +
                            "Remove the torte from the oven and leave to cool in the water-filled " +
                            "tin for 1 hour. Gently loosen the edges with a butter knife, then " +
                            "carefully turn it out onto a serving board and peel off the paper. " +
                            "Slice and serve with a drizzle of clementine syrup.",

                    R.drawable.flourless_chocolate_torte);

            insertFood(database, "Desserts", "Cherry chocolate mousse",
                    "200 g quality dark chocolate , (70%); " +
                            "1 x 400 g tin of black pitted cherries in syrup; " +
                            "200 ml double cream; " +
                            "4 large free-range eggs; " +
                            "2 tablespoons golden caster sugar",

                    "1. Melt the chocolate in a heatproof bowl over a pan of gently " +
                            "simmering water, then remove to cool for 10 minutes. Meanwhile, " +
                            "simmer the cherries and their syrup in a non-stick frying pan on " +
                            "a medium heat until thick, then remove.; " +
                            "2. Whip the cream to very soft peaks. Separate the eggs, add the " +
                            "yolks to the cream with the sugar, and whisk to combine. Add a " +
                            "pinch of sea salt to the whites and, with a clean whisk, beat until " +
                            "super-stiff. Fold the cooled chocolate into the cream, then very " +
                            "gently fold that through the egg whites with a spatula.; " +
                            "3. Divvy up the mousse between six glasses or bowls, interspersing " +
                            "the cherries and syrup throughout, and finishing with a few nice " +
                            "cherries on top.",

                    R.drawable.cherry_chocolate_mousse);

            insertFood(database, "Desserts", "Peach & almond Alaska",
                    "80 g flaked almonds; " +
                            "1 x 410 g tin of peach halves in juice; " +
                            "4 large scoops of vanilla ice cream; " +
                            "2 large free-range eggs; " +
                            "100 g golden caster sugar",

                    "1. Preheat the grill to high. Toast the almonds on a tray as it heats" +
                            " up, keeping a close eye on them and removing as soon as lightly" +
                            " golden. Slice up the peaches and divide between four ovenproof" +
                            " bowls, along with their juice. Sit a nice round scoop of ice cream" +
                            " on top of each, and place in the freezer.; " +
                            "2. Separate the eggs. Put the whites into the bowl of a" +
                            " free-standing mixer (save the yolks for another recipe)," +
                            " add a pinch of sea salt and whisk until the mixture forms stiff " +
                            "peaks (you could use an electric hand whisk). With the mixer still" +
                            " running, gradually add the sugar until combined. Spoon into a" +
                            " piping bag (I like a star-shaped nozzle) or a large sandwich bag" +
                            " that you can snip the corner off.; " +
                            "3. Remove the bowls from the freezer and scatter over the toasted" +
                            " almonds. Pipe the meringue over the ice cream as delicately or " +
                            "roughly as you like. Now – I work two at a time to retain maximum " +
                            "control – pop the bowls under the grill for just 2 minutes, or until" +
                            " golden. Remove carefully and serve right away.",

                    R.drawable.peach_almond_alaska);

            insertFood(database, "Desserts", "Brigadeiros",
                    "400 g condensed milk; " +
                            "4 tablespoons cocoa powder; " +
                            "¼ teaspoon sea salt; " +
                            "3 tablespoons butter; " +
                            "1 vanilla pod; " +
                            "good-quality chocolate sprinkles , for coating",

                    "1. Pour the condensed milk into a heavy-based saucepan and sift in " +
                            "the cocoa powder and ¼ of a teaspoon of sea salt.; " +
                            "2. Place over a low heat and cook for 10 to 15 minutes, stirring, " +
                            "until the mixture is thick, shiny and leaves the sides of the pan " +
                            "when stirred. Do not leave the pan unattended, as the condensed milk " +
                            "can burn easily.; " +
                            "3. Once the chocolate mixture has thickened, remove from the heat" +
                            " and stir in the butter.; " +
                            "4. Split the vanilla pod in half lengthways and scrape out the " +
                            "seeds with the back of a small knife. Add the seeds to the pan " +
                            "and stir well. (The scraped-out pod can be used to make vanilla " +
                            "sugar – just put it in your sugar jar and leave it to infuse the sugar).; " +
                            "5. Let the mixture cool completely, then cover with clingfilm and " +
                            "chill for about 30 minutes, until set.; " +
                            "6. Put the chocolate sprinkles in a small bowl. If you have one, " +
                            "use a mini ice-cream scoop to make a 20g ball and drop it into " +
                            "the sprinkles (alternatively, use a teaspoon).; " +
                            "7. Coat it liberally with the sprinkles, roll it with your hands to" +
                            " make a neat ball and place it in a mini cupcake or petit-four " +
                            "case. Continue with the rest of the mixture.; " +
                            "8. Store in an airtight container in the fridge, but take them out" +
                            " at least half an hour before serving, as they’re best at room temperature.",

                    R.drawable.brigadeiros);
        }

        if (oldVersion < 2) {
            database.execSQL("ALTER TABLE FOOD ADD COLUMN FAVORITE NUMERIC");
        }
    }
}
