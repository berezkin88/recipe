package person.birch.recipe.services;

import person.birch.recipe.domain.Recipe;

import java.util.List;

/**
 * @author Aleksandr Beryozkin
 */

public interface RecipeService {
    List<Recipe> findAll();
}
