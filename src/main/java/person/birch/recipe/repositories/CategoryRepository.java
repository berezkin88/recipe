package person.birch.recipe.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import person.birch.recipe.domain.Category;

/**
 * @author Aleksandr Beryozkin
 */
@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
}
