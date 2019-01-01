package dagimon.spring5course.recipes.services;

import dagimon.spring5course.recipes.commands.IngredientCommand;
import dagimon.spring5course.recipes.domain.Ingredient;

public interface IngredientService {
    IngredientCommand findCommandByRecipeIdAndId(String recipeId, String id);
    IngredientCommand saveIngredientCommand(IngredientCommand command);
    void deleteById(String recipeId, String id);
}
