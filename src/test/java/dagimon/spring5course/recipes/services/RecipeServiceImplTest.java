package dagimon.spring5course.recipes.services;

import dagimon.spring5course.recipes.converters.RecipeCommandToRecipe;
import dagimon.spring5course.recipes.converters.RecipeToRecipeCommand;
import dagimon.spring5course.recipes.domain.Recipe;
import dagimon.spring5course.recipes.exceptions.NotFoundException;
import dagimon.spring5course.recipes.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    //service being tested
    RecipeServiceImpl recipeService;

    @Mock //dependency inside service
    RecipeRepository recipeRepository;
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
        when(recipeRepository.findAll()).thenReturn(recipes);

        //checking goals
        assertEquals(1, recipeService.getRecipes().size());
        verify(recipeRepository, times(1)).findAll(); //check if findAll was called exactly 1 time
        verify(recipeRepository, never()).findById(anyLong());
    }

    @Test
    public void getRecipeByIdTest() {
        Long mockedRecipeId = 1L;

        //mocking behaviour
        Recipe mockedRecipe = new Recipe();
        mockedRecipe.setId(mockedRecipeId);
        Optional<Recipe> mockedRecipeOptional = Optional.of(mockedRecipe);
        when(recipeRepository.findById(anyLong())).thenReturn(mockedRecipeOptional);

        //checking goals
        Recipe returnedRecipe = recipeService.findById(mockedRecipeId);
        assertEquals(mockedRecipeId, returnedRecipe.getId());
        verify(recipeRepository).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }

    @Test(expected = NotFoundException.class)
    public void getRecipeByIdTestNotFound() throws Exception {
        Optional<Recipe> recipeOptional = Optional.empty();
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        recipeService.findById(1L);
        //should go kaboom
    }

    @Test
    public void testDeleteById() {
        recipeService.deleteById(2L);
        verify(recipeRepository).deleteById(anyLong());
    }
}