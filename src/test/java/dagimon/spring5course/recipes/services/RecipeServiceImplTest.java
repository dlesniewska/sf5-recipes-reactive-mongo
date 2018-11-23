package dagimon.spring5course.recipes.services;

import dagimon.spring5course.recipes.domain.Recipe;
import dagimon.spring5course.recipes.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    //service being tested
    RecipeServiceImpl recipeService;

    @Mock //dependency inside service
    RecipeRepository recipeRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this); //initialize all @Mock annotations
        recipeService = new RecipeServiceImpl(recipeRepository);
    }

    @Test
    public void getRecipes() {
        //mocking behaviour
        Recipe recipe = new Recipe();
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(recipe);
        when(recipeRepository.findAll()).thenReturn(recipes);

        //checking goals
        assertEquals(1, recipeService.getRecipes().size());
        verify(recipeRepository, times(1)).findAll(); //check if findAll was called exactly 1 time
    }
}