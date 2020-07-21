package person.birch.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import person.birch.recipe.commands.RecipeCommand;
import person.birch.recipe.services.RecipeService;

/**
 * @author Aleksandr Beryozkin
 */
@Slf4j
@Controller
public class RecipeController {

    private final RecipeService service;

    public RecipeController(RecipeService service) {
        this.service = service;
    }

    @GetMapping("/recipe/{id}/show")
    public String showById(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", service.findById(id));

        return "/recipe/show";
    }

    @GetMapping("recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeform";
    }

    @GetMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", service.findCommandById(id));
        return "recipe/recipeform";
    }

    @PostMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
        RecipeCommand savedCommand = service.saveRecipeCommand(command);

        return String.format("redirect:/recipe/%d/show", savedCommand.getId());
    }

    @GetMapping("/recipe/{id}/delete")
    public String deleteById(@PathVariable Long id) {
        log.debug("Deleting id: {}", id);

        service.deleteById(id);
        return "redirect:/";
    }
}
