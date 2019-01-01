package dagimon.spring5course.recipes.services;

import dagimon.spring5course.recipes.domain.Recipe;
import dagimon.spring5course.recipes.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImageFile(String recipeId, MultipartFile imageFile) {
        log.debug("Received a file");
        Recipe recipe = recipeRepository.findById(recipeId).get();
        try {
            Byte[] bytesArray = new Byte[imageFile.getBytes().length];

            int i = 0;

            for (byte b : imageFile.getBytes()) {
                bytesArray[i++] = b;
            }
            recipe.setImage(bytesArray);
            recipeRepository.save(recipe);

        } catch (IOException e) {
            log.error("Error setting the image for the recipe with id {}", recipeId);
            e.printStackTrace();
        }
    }
}
