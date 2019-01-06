package dagimon.spring5course.recipes.services;

import dagimon.spring5course.recipes.commands.RecipeCommand;
import dagimon.spring5course.recipes.domain.Recipe;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

public interface RecipeService {
    Flux<Recipe> getRecipes();

    Mono<Recipe> findById(String recipeId);

    Mono<RecipeCommand> saveCommand(RecipeCommand command);

    Mono<RecipeCommand> findCommandById(String id);

    Mono<Void> deleteById(String id);
}
