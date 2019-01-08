package dagimon.spring5course.recipes.services;

import dagimon.spring5course.recipes.commands.IngredientCommand;
import dagimon.spring5course.recipes.converters.IngredientCommandToIngredient;
import dagimon.spring5course.recipes.converters.IngredientToIngredientCommand;
import dagimon.spring5course.recipes.domain.Ingredient;
import dagimon.spring5course.recipes.domain.Recipe;
import dagimon.spring5course.recipes.repositories.RecipeRepository;
import dagimon.spring5course.recipes.repositories.reactive.RecipeReactiveRepository;
import dagimon.spring5course.recipes.repositories.reactive.UnitOfMeasureReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@Component
public class IngredientServiceImpl implements IngredientService {

    private final RecipeReactiveRepository recipeRepository;
    private final UnitOfMeasureReactiveRepository uomRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;


    public IngredientServiceImpl(RecipeReactiveRepository recipeRepository, UnitOfMeasureReactiveRepository uomRepository, IngredientToIngredientCommand ingredientToIngredientCommand, IngredientCommandToIngredient ingredientCommandToIngredient) {
        this.recipeRepository = recipeRepository;
        this.uomRepository = uomRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
    }

    @Override
    public Mono<IngredientCommand> findCommandByRecipeIdAndId(String recipeId, String id) {
        return recipeRepository
                .findById(recipeId)
                .flatMapIterable(Recipe::getIngredients)
                .filter(ingredient -> ingredient.getId().equalsIgnoreCase(id))
                .single()
                .map(ingredient -> {
                    IngredientCommand command = ingredientToIngredientCommand.convert(ingredient);
                    command.setRecipeId(recipeId);
                    return command;
                });
    }

    @Override
    public Mono<IngredientCommand> saveIngredientCommand(IngredientCommand command) {
        Recipe recipe = recipeRepository.findById(command.getRecipeId()).block();

        if (recipe == null) {

            //todo toss error if not found!
            log.error("Recipe not found for id: " + command.getRecipeId());
            return Mono.just(new IngredientCommand());
        } else {

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst();

            if (ingredientOptional.isPresent()) {
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(command.getDescription());
                ingredientFound.setAmount(command.getAmount());
                ingredientFound.setUom(uomRepository
                        .findById(command.getUom().getId()).block());

                if (ingredientFound.getUom() == null) {
                    new RuntimeException("UOM NOT FOUND");
                }
            } else {
                //add new Ingredient
                Ingredient ingredient = ingredientCommandToIngredient.convert(command);
                recipe.addIngredient(ingredient);
            }

            Recipe savedRecipe = recipeRepository.save(recipe).block();

            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
                    .findFirst();

            //check by description
            if (!savedIngredientOptional.isPresent()) {
                //not totally safe... But best guess
                savedIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(recipeIngredients -> recipeIngredients.getDescription().equals(command.getDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients.getUom().getId().equals(command.getUom().getId()))
                        .findFirst();
            }

            //todo check for fail

            //enhance with id value
            IngredientCommand ingredientCommandSaved = ingredientToIngredientCommand.convert(savedIngredientOptional.get());
            ingredientCommandSaved.setRecipeId(recipe.getId());

            return Mono.just(ingredientCommandSaved);
        }

    }

    @Override
    public Mono<Void> deleteById(String recipeId, String id) {
        Recipe recipe = recipeRepository.findById(recipeId).block();
        if(recipe != null) {
            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                    .filter(ingredient -> id.equals(ingredient.getId()))
                    .findFirst();
            if(ingredientOptional.isPresent()) {
                recipe.getIngredients().remove(ingredientOptional.get());
                recipeRepository.save(recipe).block();
            }

        } else {
            log.debug("Recipe with id {} not found", recipeId);
        }
        return Mono.empty();
    }
}
