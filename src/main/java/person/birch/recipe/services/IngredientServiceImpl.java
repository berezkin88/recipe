package person.birch.recipe.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import person.birch.recipe.commands.IngredientCommand;
import person.birch.recipe.converters.IngredientToIngredientCommand;
import person.birch.recipe.domain.Recipe;
import person.birch.recipe.repositories.RecipeRepository;

import java.util.Optional;

/**
 * @author Aleksandr Beryozkin
 */
@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final RecipeRepository recipeRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, RecipeRepository recipeRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (recipeOptional.isEmpty()) {
            // todo: implement error handling
            log.error("recipe id not found. Id: {}", recipeId);
        }

        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredientToIngredientCommand::convert)
                .findFirst();

        if (ingredientCommandOptional.isEmpty()) {
            // todo: implement error handling
            log.error("Ingredient id not found: {}", ingredientId);
        }

        return ingredientCommandOptional.orElse(null);
    }
}
