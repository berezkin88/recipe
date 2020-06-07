package person.birch.recipe.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import person.birch.recipe.domain.Recipe;

/**
 * @author Aleksandr Beryozkin
 */
@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
