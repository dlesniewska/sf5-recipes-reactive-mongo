package dagimon.spring5course.recipes.services;

import dagimon.spring5course.recipes.domain.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();
}
