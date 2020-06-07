package person.birch.recipe.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import person.birch.recipe.domain.UnitOfMeasure;

/**
 * @author Aleksandr Beryozkin
 */
@Repository
public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {
}
