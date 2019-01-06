package dagimon.spring5course.recipes.services;

import dagimon.spring5course.recipes.commands.RecipeCommand;
import dagimon.spring5course.recipes.converters.RecipeCommandToRecipe;
import dagimon.spring5course.recipes.converters.RecipeToRecipeCommand;
import dagimon.spring5course.recipes.domain.Recipe;
import dagimon.spring5course.recipes.exceptions.NotFoundException;
import dagimon.spring5course.recipes.repositories.RecipeRepository;
import dagimon.spring5course.recipes.repositories.reactive.RecipeReactiveRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    //service being tested
    RecipeServiceImpl recipeService;

    @Mock
    RecipeReactiveRepository recipeRepository;
    @Mock
    RecipeCommandToRecipe recipeCommandToRecipeConverter;
    @Mock
    RecipeToRecipeCommand recipeToRecipeCommandConverter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this); //initialize all @Mock annotations
        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipeConverter, recipeToRecipeCommandConverter);
    }

    @Test
    public void getRecipesTest() {
        //mocking behaviour
        Recipe recipe = new Recipe();
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(recipe);
        when(recipeRepository.findAll()).thenReturn(Flux.fromIterable(recipes));

        //checking goals
        assertEquals(1, recipeService.getRecipes().collectList().block().size());
        verify(recipeRepository, times(1)).findAll(); //check if findAll was called exactly 1 time
        verify(recipeRepository, never()).findById(anyString());
    }

    @Test
    public void getRecipeByIdTest() {
        String mockedRecipeId = "1";

        //mocking behaviour
        Recipe mockedRecipe = new Recipe();
        mockedRecipe.setId(mockedRecipeId);
        when(recipeRepository.findById(anyString())).thenReturn(Mono.just(mockedRecipe));

        //checking goals
        Recipe returnedRecipe = recipeService.findById(mockedRecipeId).block();
        assertEquals(mockedRecipeId, returnedRecipe.getId());
        verify(recipeRepository).findById(anyString());
        verify(recipeRepository, never()).findAll();
    }

    //not possible anymore
//    @Test(expected = NotFoundException.class)
//    public void getRecipeByIdTestNotFound() throws Exception {
//        when(recipeRepository.findById(anyString())).thenReturn(Mono.empty());
//        recipeService.findById("1");
//        //should go kaboom
//    }
    @Test
    public void getRecipeCommandByIdTest() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId("1");

        when(recipeRepository.findById(anyString())).thenReturn(Mono.just(recipe));

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("1");

        when(recipeToRecipeCommandConverter.convert(any())).thenReturn(recipeCommand);

        RecipeCommand commandById = recipeService.findCommandById("1").block();

        assertNotNull("Null recipe returned", commandById);
        verify(recipeRepository, times(1)).findById(anyString());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void testDeleteById() {
        when(recipeRepository.deleteById(anyString())).thenReturn(Mono.empty());

        recipeService.deleteById("2");
        verify(recipeRepository).deleteById(anyString());
    }
}