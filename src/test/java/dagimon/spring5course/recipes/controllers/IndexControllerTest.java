package dagimon.spring5course.recipes.controllers;

import dagimon.spring5course.recipes.domain.Recipe;
import dagimon.spring5course.recipes.repositories.RecipeRepository;
import dagimon.spring5course.recipes.services.RecipeService;
import dagimon.spring5course.recipes.services.RecipeServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import reactor.core.publisher.Flux;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class IndexControllerTest {

    IndexController indexController;
    @Mock
    Model model;
    @Mock
    RecipeService recipeService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        indexController = new IndexController(recipeService);
    }

    @Test
    public void getIndexPage() throws Exception {

        //given (startup data)
        Set<Recipe> recipes = new HashSet<>();
        Recipe recipe = new Recipe();
        recipe.setId("1");
        recipes.add(recipe);
        recipe = new Recipe();
        recipe.setId("2");
        recipes.add(recipe);
        when(recipeService.getRecipes()).thenReturn(Flux.fromIterable(recipes));
        ArgumentCaptor<List<Recipe>> argumentCaptor = ArgumentCaptor.forClass(List.class); //checking result type captor

        //when (actuall call)
        String redirectPage = indexController.getIndexPage(model);

        //then (checking our goals)
        assertEquals("index", redirectPage);

        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture()); //times checking & set argument
        List<Recipe> argumentCaptorSet = argumentCaptor.getValue();
        assertEquals(2, argumentCaptorSet.size());

        verify(recipeService, times(2)).getRecipes();
    }

    @Test
    public void testMockMvc() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
        when(recipeService.getRecipes()).thenReturn(Flux.empty());
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"));
    }
}