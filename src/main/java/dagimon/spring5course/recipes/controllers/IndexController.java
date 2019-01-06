package dagimon.spring5course.recipes.controllers;

import dagimon.spring5course.recipes.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class IndexController {

    RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {
        log.info("Redirecting to page index.html");
        model.addAttribute("recipes", recipeService.getRecipes().collectList().block());
        model.addAttribute("recipesSize", recipeService.getRecipes().collectList().block().size());
        return "index";
    }

}
