package person.birch.recipe.services;

import person.birch.recipe.commands.IngredientCommand;

/**
 * @author Aleksandr Beryozkin
 */

public interface IngredientService {

    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
}
