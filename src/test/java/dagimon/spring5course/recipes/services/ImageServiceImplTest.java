package dagimon.spring5course.recipes.services;

import dagimon.spring5course.recipes.domain.Recipe;
import dagimon.spring5course.recipes.repositories.RecipeRepository;
import dagimon.spring5course.recipes.repositories.reactive.RecipeReactiveRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ImageServiceImplTest {

    ImageService imageService;
    @Mock
    RecipeReactiveRepository recipeRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        imageService = new ImageServiceImpl(recipeRepository);
    }

    @Test
    public void saveImageFile() throws IOException {
        //given
        Recipe mockedRecipe = new Recipe();
        mockedRecipe.setId("1");
        MultipartFile file = new MockMultipartFile("imagefile", "image.txt", "text/plain", "SOme sample text".getBytes());
        when(recipeRepository.findById(anyString())).thenReturn(Mono.just(mockedRecipe));
        when(recipeRepository.save(any(Recipe.class))).thenReturn(Mono.just(mockedRecipe));
        //when(recipeRepository.save(any(Recipe.class))).thenReturn(mockedRecipe); //default is null, don't need that line actually

        //when
        imageService.saveImageFile("1", file);

        //then
        ArgumentCaptor<Recipe> captorRecipe = ArgumentCaptor.forClass(Recipe.class);
        verify(recipeRepository).save(captorRecipe.capture()); //catch the actual argument object
        Recipe savedRecipe = captorRecipe.getValue();
        assertEquals(file.getBytes().length, savedRecipe.getImage().length);
    }

}