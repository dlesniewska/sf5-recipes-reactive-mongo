package dagimon.spring5course.recipes.services;

import dagimon.spring5course.recipes.commands.IngredientCommand;
import dagimon.spring5course.recipes.domain.Ingredient;

public interface IngredientService {
    IngredientCommand findCommandByRecipeIdAndId(Long recipeId, Long id);
    IngredientCommand saveIngredientCommand(IngredientCommand command);
    void deleteById(Long recipeId, Long id);
}
