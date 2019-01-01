package dagimon.spring5course.recipes.services;

import dagimon.spring5course.recipes.commands.RecipeCommand;
import dagimon.spring5course.recipes.converters.RecipeCommandToRecipe;
import dagimon.spring5course.recipes.converters.RecipeToRecipeCommand;
import dagimon.spring5course.recipes.domain.Recipe;
import dagimon.spring5course.recipes.exceptions.NotFoundException;
import dagimon.spring5course.recipes.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipeConverter;
    private final RecipeToRecipeCommand recipeToRecipeCommandConverter;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipeConverter, RecipeToRecipeCommand recipeToRecipeCommandConverter) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipeConverter = recipeCommandToRecipeConverter;
        this.recipeToRecipeCommandConverter = recipeToRecipeCommandConverter;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("getRecipes service call");
        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
        return recipes;
    }

    @Override
    public Recipe findById(String recipeId) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
        if(!optionalRecipe.isPresent()) {
            throw new NotFoundException("Recipe not found, oh my God. Invalid id was: " + recipeId);
        }
        return optionalRecipe.get();
    }

    @Override
    @Transactional
    public RecipeCommand saveCommand(RecipeCommand command) {
        Recipe recipe = recipeRepository.save(recipeCommandToRecipeConverter.convert(command));
        log.info("Saved recipe with id {}", recipe.getId());
        return recipeToRecipeCommandConverter.convert(recipe);
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(String id) {
        return recipeToRecipeCommandConverter.convert(findById(id));
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        recipeRepository.deleteById(id);
    }
}
