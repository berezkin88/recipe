package person.birch.recipe.services;

import org.springframework.stereotype.Service;
import person.birch.recipe.domain.Recipe;
import person.birch.recipe.repositories.RecipeRepository;

import java.util.List;

/**
 * @author Aleksandr Beryozkin
 */
@Service
public class RecipeServiceImpl implements RecipeService{

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public List<Recipe> findAll() {
        return (List<Recipe>) recipeRepository.findAll();
    }
}
