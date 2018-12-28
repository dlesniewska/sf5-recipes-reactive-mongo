package dagimon.spring5course.recipes.bootstrap;

import dagimon.spring5course.recipes.domain.*;
import dagimon.spring5course.recipes.repositories.CategoryRepository;
import dagimon.spring5course.recipes.repositories.RecipeRepository;
import dagimon.spring5course.recipes.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Stream;

@Slf4j
@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository uomRepository;

    public RecipeBootstrap(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository uomRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.uomRepository = uomRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepository.saveAll(prepareRecipes());
    }

    public List<Recipe> prepareRecipes() {
        log.info("Initializing sample data");
        List<Recipe> recipes = new ArrayList<>();

        //units map
        Map<String, UnitOfMeasure> units = new HashMap<>();
        Stream.of("Each", "Tablespoon", "Teaspoon", "Dash", "Pint", "Cup").forEach(unit -> {
            units.put(unit, getUnitOfMeasure(unit).get());
        });

        //categories map
        Map<String, Category> categories = new HashMap<>();
        Stream.of("American", "Mexican").forEach(category -> {
            categories.put(category, getCategory(category).get());
        });

        //Guacamole recipe
        Recipe guacamoleRecipe = new Recipe();
        guacamoleRecipe.setDescription("Perfect Guacamole");
        guacamoleRecipe.addCategory(categories.get("Mexican"));
        guacamoleRecipe.addCategory(categories.get("American"));
        guacamoleRecipe.setPrepTime(10);
        guacamoleRecipe.setCookTime(0);
        guacamoleRecipe.setDifficulty(Difficulty.EASY);
        guacamoleRecipe.setServings(4);
        guacamoleRecipe.setSource("SimplyRecipes");
        guacamoleRecipe.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guacamoleRecipe.addIngredient(new Ingredient("ripe avocados", new BigDecimal(2), units.get("Each")));
        guacamoleRecipe.addIngredient(new Ingredient("Kosher salt", new BigDecimal(.5), units.get("Teaspoon")));
        guacamoleRecipe.addIngredient(new Ingredient("fresh lime juice or lemon juice", new BigDecimal(1), units.get("Tablespoon")));
        guacamoleRecipe.addIngredient(new Ingredient("minced red onion or thinly sliced green onion", new BigDecimal(.25), units.get("Cup")));
        guacamoleRecipe.addIngredient(new Ingredient("serrano chiles, stems and seeds removed, minced", new BigDecimal(2), units.get("Each")));
        guacamoleRecipe.addIngredient(new Ingredient("cilantro (leaves and tender stems), finely chopped", new BigDecimal(2), units.get("Tablespoon")));
        guacamoleRecipe.addIngredient(new Ingredient("freshly grated black pepper", new BigDecimal(1), units.get("Dash")));
        guacamoleRecipe.addIngredient(new Ingredient("ripe tomato, seeds and pulp removed, chopped", new BigDecimal(.5), units.get("Each")));
        Notes guacamoleNotes = new Notes("Guacamole, a dip made from avocados, is originally from Mexico. The name is derived from two Aztec Nahuatl words—ahuacatl (avocado) and molli (sauce).\n" +

                "MAKING GUACAMOLE IS EASY\n" +
                "All you really need to make guacamole is ripe avocados and salt. After that, a little lime or lemon juice—a splash of acidity—will help to balance the richness of the avocado. Then if you want, add chopped cilantro, chiles, onion, and/or tomato.\n" +

                "Once you have basic guacamole down, feel free to experiment with variations including strawberries, peaches, pineapple, mangoes, even watermelon. You can get creative with homemade guacamole!\n" +

                "GUACAMOLE TIP: USE RIPE AVOCADOS\n" +
                "The trick to making perfect guacamole is using ripe avocados that are just the right amount of ripeness. Not ripe enough and the avocado will be hard and tasteless. Too ripe and the taste will be off.\n" +

                "Check for ripeness by gently pressing the outside of the avocado. If there is no give, the avocado is not ripe yet and will not taste good. If there is a little give, the avocado is ripe. If there is a lot of give, the avocado may be past ripe and not good. In this case, taste test first before using.");
        guacamoleRecipe.setNotes(guacamoleNotes);
        guacamoleRecipe.setDirections("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.\n" +


                "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)\n" +


                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +

                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +

                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +

                "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n" +

                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.");
        recipes.add(guacamoleRecipe);

        //Tacos recipe
        Recipe tacoRecipe = new Recipe();
        tacoRecipe.setDescription("Spicy Grilled Chicken Tacos Recipe");
        tacoRecipe.addCategory(categories.get("Mexican"));
        tacoRecipe.addCategory(categories.get("American"));
        tacoRecipe.setPrepTime(20);
        tacoRecipe.setCookTime(15);
        tacoRecipe.setDifficulty(Difficulty.MODERATE);
        tacoRecipe.setServings(6);
        tacoRecipe.setSource("SimplyRecipes");
        tacoRecipe.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        tacoRecipe.addIngredient(new Ingredient("ancho chili powder", new BigDecimal(2), units.get("Tablespoon")));
        tacoRecipe.addIngredient(new Ingredient("dried oregano", new BigDecimal(1), units.get("Teaspoon")));
        tacoRecipe.addIngredient(new Ingredient("dried cumin", new BigDecimal(1), units.get("Teaspoon")));
        tacoRecipe.addIngredient(new Ingredient("sugar", new BigDecimal(.5), units.get("Teaspoon")));
        tacoRecipe.addIngredient(new Ingredient("salt", new BigDecimal(2), units.get("Tablespoon")));
        tacoRecipe.addIngredient(new Ingredient("clove garlic, finely chopped", new BigDecimal(1), units.get("Each")));
        tacoRecipe.addIngredient(new Ingredient("finely grated orange zest", new BigDecimal(1), units.get("Tablespoon")));
        tacoRecipe.addIngredient(new Ingredient("fresh-squeezed orange juice", new BigDecimal(3), units.get("Tablespoon")));
        tacoRecipe.addIngredient(new Ingredient("olive oil", new BigDecimal(2), units.get("Tablespoon")));
        tacoRecipe.addIngredient(new Ingredient("skinless, boneless chicken thighs (1 1/4 pounds)", new BigDecimal(6), units.get("Each")));
        tacoRecipe.addIngredient(new Ingredient("small corn tortillas", new BigDecimal(8), units.get("Each")));
        tacoRecipe.addIngredient(new Ingredient("packed baby arugula (3 ounces)", new BigDecimal(3), units.get("Cup")));
        tacoRecipe.addIngredient(new Ingredient("medium ripe avocados, sliced", new BigDecimal(2), units.get("Each")));
        tacoRecipe.addIngredient(new Ingredient("radishes, thinly sliced", new BigDecimal(4), units.get("Each")));
        tacoRecipe.addIngredient(new Ingredient("cherry tomatoes, halved", new BigDecimal(.5), units.get("Pint")));
        tacoRecipe.addIngredient(new Ingredient("red onion, thinly sliced", new BigDecimal(.25), units.get("Each")));
        tacoRecipe.addIngredient(new Ingredient("roughly chopped cilantropowder", new BigDecimal(1), units.get("Each")));
        tacoRecipe.addIngredient(new Ingredient("sour cream", new BigDecimal(.5), units.get("Cup")));
        tacoRecipe.addIngredient(new Ingredient("cup milk", new BigDecimal(.25), units.get("Cup")));
        tacoRecipe.addIngredient(new Ingredient("lime, cut into wedges", new BigDecimal(1), units.get("Each")));
        Notes tacoNotes = new Notes("The ancho chiles I use in the marinade are named for their wide shape. They are large, have a deep reddish brown color when dried, and are mild in flavor with just a hint of heat. You can find ancho chile powder at any markets that sell Mexican ingredients, or online.\n" +

                "I like to put all the toppings in little bowls on a big platter at the center of the table: avocados, radishes, tomatoes, red onions, wedges of lime, and a sour cream sauce. I add arugula, as well – this green isn’t traditional for tacos, but we always seem to have some in the fridge and I think it adds a nice green crunch to the tacos.\n" +

                "Everyone can grab a warm tortilla from the pile and make their own tacos just they way they like them.\n" +

                "You could also easily double or even triple this recipe for a larger party. A taco and a cold beer on a warm day? Now that’s living!");
        tacoRecipe.setNotes(tacoNotes);
        tacoRecipe.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +

                "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +

                "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +

                "Spicy Grilled Chicken Tacos\n" +

                "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +

                "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +

                "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +

                "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.");
        recipes.add(tacoRecipe);
        return recipes;
    }

    private Optional<UnitOfMeasure> getUnitOfMeasure(String uomDescription) {
        Optional<UnitOfMeasure> optional = uomRepository.findByDescription(uomDescription);
        if (!optional.isPresent()) {
            throw new RuntimeException(uomDescription + " unit is not present");
        }
        return optional;
    }

    private Optional<Category> getCategory(String category) {
        Optional<Category> optional = categoryRepository.findByDescription(category);
        if (!optional.isPresent()) {
            throw new RuntimeException(category + " category is not present");
        }
        return optional;
    }

}
