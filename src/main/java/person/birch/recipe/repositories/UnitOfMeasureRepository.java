package person.birch.recipe.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import person.birch.recipe.domain.UnitOfMeasure;

import java.util.Optional;

/**
 * @author Aleksandr Beryozkin
 */
@Repository
public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {

    Optional<UnitOfMeasure> findByUom(String uom);
}
