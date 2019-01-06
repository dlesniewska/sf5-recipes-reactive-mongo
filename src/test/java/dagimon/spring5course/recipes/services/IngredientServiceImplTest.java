package dagimon.spring5course.recipes.services;

import dagimon.spring5course.recipes.commands.IngredientCommand;
import dagimon.spring5course.recipes.commands.UnitOfMeasureCommand;
import dagimon.spring5course.recipes.converters.IngredientCommandToIngredient;
import dagimon.spring5course.recipes.converters.IngredientToIngredientCommand;
import dagimon.spring5course.recipes.converters.UnitOfMeasureCommandToUnitOfMeasure;
import dagimon.spring5course.recipes.converters.UnitOfMeasureToUnitOfMeasureCommand;
import dagimon.spring5course.recipes.domain.Ingredient;
import dagimon.spring5course.recipes.domain.Recipe;
import dagimon.spring5course.recipes.repositories.RecipeRepository;
import dagimon.spring5course.recipes.repositories.UnitOfMeasureRepository;
import dagimon.spring5course.recipes.repositories.reactive.RecipeReactiveRepository;
import dagimon.spring5course.recipes.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IngredientServiceImplTest {

    //testing
    IngredientService ingredientService;
    IngredientCommandToIngredient ingredientCommandToIngredientConverter;
    IngredientToIngredientCommand ingredientToIngredientCommandConverter;
    //mocking
    @Mock
    RecipeReactiveRepository recipeRepository;
    @Mock
    UnitOfMeasureReactiveRepository uomRepository;

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
        recipe.setId("1");
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId("1");
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId("2");
        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId("3");
        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);

        //when
        when(recipeRepository.findById(anyString())).thenReturn(Mono.just(recipe));

        //then
        IngredientCommand ingredientCommand = ingredientService.findCommandByRecipeIdAndId("1", "3").block();
        assertNotNull(ingredientCommand);
        assertEquals("1", ingredientCommand.getRecipeId());
        assertEquals("3", ingredientCommand.getId());
        verify(recipeRepository).findById(anyString());
    }

    @Test
    public void testSaveIngredientCommand() throws Exception {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId("3");
        command.setRecipeId("2");
        command.setUom(new UnitOfMeasureCommand());
        command.getUom().setId("1234");

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId("3");
        when(recipeRepository.findById(anyString())).thenReturn(Mono.just(new Recipe()));
        when(recipeRepository.save(any())).thenReturn(Mono.just(savedRecipe));

        //when
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command).block();

        //then
        assertEquals("3", savedCommand.getId());
        verify(recipeRepository).findById(anyString());
        verify(recipeRepository).save(any(Recipe.class));
    }

    @Test
    public void deleteById() {
        //given
        Recipe recipe = new Recipe();
        recipe.setId("1");
        Ingredient ingredient = new Ingredient();
        ingredient.setId("3");
        recipe.addIngredient(ingredient);
        when(recipeRepository.findById(anyString())).thenReturn(Mono.just(recipe));
        when(recipeRepository.save(any())).thenReturn(Mono.just(recipe));

        //when
        ingredientService.deleteById("1", "3");

        //then
        verify(recipeRepository).findById(anyString());
        verify(recipeRepository).save(any(Recipe.class));
    }
}