package dagimon.spring5course.recipes.services;

import dagimon.spring5course.recipes.commands.IngredientCommand;
import dagimon.spring5course.recipes.domain.Ingredient;
import reactor.core.publisher.Mono;

public interface IngredientService {
    Mono<IngredientCommand> findCommandByRecipeIdAndId(String recipeId, String id);
    Mono<IngredientCommand> saveIngredientCommand(IngredientCommand command);
    Mono<Void> deleteById(String recipeId, String id);
}
