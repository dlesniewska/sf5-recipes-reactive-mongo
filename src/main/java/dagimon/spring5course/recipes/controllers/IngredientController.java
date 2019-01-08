package dagimon.spring5course.recipes.controllers;

import dagimon.spring5course.recipes.commands.IngredientCommand;
import dagimon.spring5course.recipes.commands.UnitOfMeasureCommand;
import dagimon.spring5course.recipes.services.IngredientService;
import dagimon.spring5course.recipes.services.RecipeService;
import dagimon.spring5course.recipes.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService uomService;
    private WebDataBinder webDataBinder;

    @ModelAttribute("uomList")
    public Flux<UnitOfMeasureCommand> populateUomList() {
        return  uomService.listAllCommands();
    }

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService uomService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.uomService = uomService;
    }

    @InitBinder("ingredient")
    public void initBinder(WebDataBinder webDataBinder) {
        this.webDataBinder = webDataBinder;
    }

    /**
     * List all ingredients
     *
     * @param recipeId
     * @param model
     * @return
     */
    @GetMapping("/recipe/{recipeId}/ingredients")
    public String listIngredients(@PathVariable String recipeId, Model model) {
        log.debug("Getting ingredient list for recipe id: {}", recipeId);
        model.addAttribute("recipe", recipeService.findCommandById(recipeId));
        return "recipe/ingredient/list";
    }

    /**
     * Show ingredient by id
     *
     * @param recipeId
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/recipe/{recipeId}/ingredient/{id}/show")
    public String showIngredient(@PathVariable String recipeId, @PathVariable String id, Model model) {
        model.addAttribute("ingredient", ingredientService.findCommandByRecipeIdAndId(recipeId, id));
        return "recipe/ingredient/show";
    }

    @GetMapping("recipe/{recipeId}/ingredient/new")
    public String newRecipeIngredient(@PathVariable String recipeId, Model model){

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setUom(new UnitOfMeasureCommand());

        model.addAttribute("ingredient", ingredientCommand);
        return "recipe/ingredient/ingredientForm";
    }

    /**
     * Display ingredient edit form
     *
     * @param recipeId
     * @param id
     * @param model
     * @return
     */
    @GetMapping("recipe/{recipeId}/ingredient/{id}/update")
    public String updateRecipeIngredient(@PathVariable String recipeId,
                                         @PathVariable String id, Model model) {
        model.addAttribute("ingredient", ingredientService.findCommandByRecipeIdAndId(recipeId, id).block());
        return "recipe/ingredient/ingredientForm";
    }


    /**
     * Save ingredient edit form (new or edited)
     * @param command
     * @return
     */
    @PostMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute("ingredient") IngredientCommand command, @PathVariable String recipeId,  Model model) {
        webDataBinder.validate();
        BindingResult bindingResult = webDataBinder.getBindingResult();

        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });
            return "recipe/ingredient/ingredientForm";
        }

        command.setRecipeId(recipeId);
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command).block();
        log.debug("saved ingredient id:" + savedCommand.getId());

        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
    }

    /**
     * Delete ingredient from recipe
     */
    @GetMapping("recipe/{recipeId}/ingredient/{id}/delete")
    public String deleteRecipeIngredient(@PathVariable String recipeId,
                                         @PathVariable String id){
        log.debug("Delete ingredient with id {} from recipe with id {}", id, recipeId);
        ingredientService.deleteById(recipeId, id).block();
        return "redirect:/recipe/" + recipeId + "/ingredients";
    }

}
