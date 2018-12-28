package dagimon.spring5course.recipes.services;

import dagimon.spring5course.recipes.commands.IngredientCommand;
import dagimon.spring5course.recipes.converters.IngredientCommandToIngredient;
import dagimon.spring5course.recipes.converters.IngredientToIngredientCommand;
import dagimon.spring5course.recipes.converters.UnitOfMeasureCommandToUnitOfMeasure;
import dagimon.spring5course.recipes.converters.UnitOfMeasureToUnitOfMeasureCommand;
import dagimon.spring5course.recipes.domain.Ingredient;
import dagimon.spring5course.recipes.domain.Recipe;
import dagimon.spring5course.recipes.repositories.RecipeRepository;
import dagimon.spring5course.recipes.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IngredientServiceImplTest {

    //testing
    IngredientService ingredientService;
    UnitOfMeasureRepository uomRepository;
    IngredientCommandToIngredient ingredientCommandToIngredientConverter;
    IngredientToIngredientCommand ingredientToIngredientCommandConverter;
    //mocking
    @Mock
    RecipeRepository recipeRepository;

    //init converters once (not per test)
    public IngredientServiceImplTest() {
        ingredientToIngredientCommandConverter = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        ingredientCommandToIngredientConverter = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ingredientService = new IngredientServiceImpl(recipeRepository, uomRepository, ingredientToIngredientCommandConverter, ingredientCommandToIngredientConverter);
    }

    @Test
    public void findCommandByRecipeIdAndIdHappyPath() {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(2L);
        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);
        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);
        Optional<Recipe> optionalRecipe = Optional.of(recipe);

        //when
        when(recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);

        //then
        IngredientCommand ingredientCommand = ingredientService.findCommandByRecipeIdAndId(1L, 3L);
        assertNotNull(ingredientCommand);
        assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
        assertEquals(Long.valueOf(3L), ingredientCommand.getId());
        verify(recipeRepository).findById(anyLong());
    }

    @Test
    public void testSaveIngredientCommand() throws Exception {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId(3L);
        command.setRecipeId(2L);
        Optional<Recipe> recipeOptional = Optional.of(new Recipe());
        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(3L);
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        //when
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        //then
        assertEquals(Long.valueOf(3L), savedCommand.getId());
        verify(recipeRepository).findById(anyLong());
        verify(recipeRepository).save(any(Recipe.class));
    }

    @Test
    public void deleteById() {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Ingredient ingredient = new Ingredient();
        ingredient.setId(3L);
        ingredient.setRecipe(recipe);
        recipe.addIngredient(ingredient);
        Optional<Recipe> recipeOptional = Optional.of(recipe);
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        //when
        ingredientService.deleteById(1L, 3L);

        //then
        verify(recipeRepository).findById(anyLong());
        verify(recipeRepository).save(any(Recipe.class));
    }
}