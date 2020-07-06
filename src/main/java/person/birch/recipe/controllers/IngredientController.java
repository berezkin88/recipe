package person.birch.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import person.birch.recipe.commands.IngredientCommand;
import person.birch.recipe.commands.RecipeCommand;
import person.birch.recipe.commands.UnitOfMeasureCommand;
import person.birch.recipe.services.IngredientService;
import person.birch.recipe.services.RecipeService;
import person.birch.recipe.services.UnitOfMeasureService;

/**
 * @author Aleksandr Beryozkin
 */
@Controller
@Slf4j
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @RequestMapping("/recipe/{recipeId}/ingredients")
    public String listIngredients(@PathVariable Long recipeId, Model model) {
        log.debug("Getting ingredients list for recipe id: {}", recipeId);

        model.addAttribute("recipe", recipeService.findCommandById(recipeId));
        return "recipe/ingredient/list";
    }

    @RequestMapping("recipe/{recipeId}/ingredient/{id}/show")
    public String showRecipeIngredient(@PathVariable String recipeId,
                                       @PathVariable String id, Model model){
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));
        return "recipe/ingredient/show";
    }

    @RequestMapping("recipe/{recipeId}/ingredient/{id}/update")
    public String updateRecipeIngredient(@PathVariable Long recipeId,
                                         @PathVariable Long id, Model model){
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId,id));

        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
        return "recipe/ingredient/ingredientform";
    }

    @PostMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand command){
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        log.debug("saved recipe id:" + savedCommand.getRecipeId());
        log.debug("saved ingredient id:" + savedCommand.getId());

        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
    }

    @RequestMapping("recipe/{recipeId}/ingredient/new")
    public String newRecipe(@PathVariable Long recipeId, Model model) {
        RecipeCommand recipeCommand = recipeService.findCommandById(recipeId);
        // todo: raise exception if null

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(recipeId);
        model.addAttribute("ingredient", ingredientCommand);

        ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());

        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());

        return "recipe/ingredient/ingredientform";
    }

    @RequestMapping("recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteRecipe(@PathVariable Long recipeId, @PathVariable Long ingredientId) {
        log.debug("Deleting ingredient with id {} from recipe with id {}", ingredientId, recipeId);
        ingredientService.deleteIngredientFromRecipeById(recipeId, ingredientId);

        return String.format("redirect:/recipe/%d/ingredients", recipeId);
    }
}
