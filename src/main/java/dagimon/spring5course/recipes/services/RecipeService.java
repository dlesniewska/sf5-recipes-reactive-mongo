package dagimon.spring5course.recipes.services;

import dagimon.spring5course.recipes.commands.RecipeCommand;
import dagimon.spring5course.recipes.domain.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();

    Recipe findById(Long recipeId);

    RecipeCommand saveCommand(RecipeCommand command);

    RecipeCommand findCommandById(Long id);

    void deleteById(Long id);
}
