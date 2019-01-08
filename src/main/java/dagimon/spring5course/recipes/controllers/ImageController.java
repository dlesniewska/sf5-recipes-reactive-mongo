package dagimon.spring5course.recipes.controllers;

import dagimon.spring5course.recipes.services.ImageService;
import dagimon.spring5course.recipes.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ImageController {

    private final RecipeService recipeService;
    private final ImageService imageService;

    public ImageController(RecipeService recipeService, ImageService imageService) {
        this.recipeService = recipeService;
        this.imageService = imageService;
    }

    @GetMapping("recipe/{recipeId}/image")
    public String showUploadImageForm(@PathVariable String recipeId, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(recipeId).block());
        return "recipe/imageUploadForm";
    }

    @PostMapping("recipe/{recipeId}/image")
    public String handleImageUpload(@PathVariable String recipeId, @RequestParam("imagefile") MultipartFile file){
        imageService.saveImageFile(recipeId, file).block();
        return "redirect:/recipe/" + recipeId + "/show";
    }

    //TODO WEBFLUX
//    @GetMapping("recipe/{recipeId}/recipeImage")
//    public void renderImageFromDB(@PathVariable String recipeId, HttpServletResponse response) throws IOException {
//
//        RecipeCommand recipeCommand = recipeService.findCommandById(recipeId).block();
//
//        if (recipeCommand.getImage() != null) {
//
//            byte[] byteArray = new byte[recipeCommand.getImage().length];
//            int i = 0;
//            for (Byte wrappedByte : recipeCommand.getImage()){
//                byteArray[i++] = wrappedByte; //auto unboxing
//            }
//
//            response.setContentType("image/jpeg");
//            InputStream is = new ByteArrayInputStream(byteArray);
//            IOUtils.copy(is, response.getOutputStream());
//        }
//    }
}
