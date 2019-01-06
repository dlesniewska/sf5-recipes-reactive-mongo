package dagimon.spring5course.recipes.controllers;

import dagimon.spring5course.recipes.commands.RecipeCommand;
import dagimon.spring5course.recipes.domain.Recipe;
import dagimon.spring5course.recipes.services.ImageService;
import dagimon.spring5course.recipes.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import reactor.core.publisher.Mono;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ImageControllerTest {

    ImageController imageController;
    MockMvc mockMvc;
    @Mock
    RecipeService recipeService;
    @Mock
    ImageService imageService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        imageController = new ImageController(recipeService, imageService);
        mockMvc = MockMvcBuilders.standaloneSetup(imageController).setControllerAdvice(new ControllerExceptionHandler()).build();
    }

    @Test
    public void showUploadImageFormshowUploadImageForm() throws Exception {
        //given
        RecipeCommand mockedRecipe = new RecipeCommand();
        mockedRecipe.setId("1");
        when(recipeService.findCommandById(anyString())).thenReturn(Mono.just(mockedRecipe));

        mockMvc.perform(get("/recipe/1/image"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipe/imageUploadForm"));
        verify(recipeService).findCommandById(anyString());
    }

    //rewrited as a controller advice global handling number format exception
    //no String -> long conversion for mongo since id is of type String anyway
//    @Test
//    public void testShowUploadImageFormNumberFormatException() throws Exception {
//        mockMvc.perform(get("/recipe/dupa/image"))
//                .andExpect(status().isBadRequest())
//                .andExpect(view().name("400error"))
//                .andExpect(model().attributeExists("exception"));
//    }

    @Test
    public void handleImageUpload() throws Exception {

        MockMultipartFile multipartFile = new MockMultipartFile("imagefile",
                        "testing.txt",
                        "text/plain",
                        "Spring Framework Guru".getBytes());
        when(imageService.saveImageFile(anyString(), any())).thenReturn(Mono.empty());
        mockMvc.perform(multipart("/recipe/1/image").file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/recipe/1/show"));

        verify(imageService, times(1)).saveImageFile(anyString(), any());
    }

    @Test
    public void renderImageFromDB() throws Exception {
        //given
        RecipeCommand command = new RecipeCommand();
        command.setId("1");
        String s = "fake image text";
        Byte[] bytesBoxed = new Byte[s.getBytes().length];
        int i = 0;
        for (byte primByte : s.getBytes()){
            bytesBoxed[i++] = primByte;
        }
        command.setImage(bytesBoxed);
        when(recipeService.findCommandById(anyString())).thenReturn(Mono.just(command));

        //when
        MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeImage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        byte[] reponseBytes = response.getContentAsByteArray();
        assertEquals(s.getBytes().length, reponseBytes.length);
    }
}