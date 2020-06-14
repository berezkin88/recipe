package person.birch.recipe.domain;

import lombok.*;

import javax.persistence.*;

/**
 * @author Aleksandr Beryozkin
 */
@Data
@Entity
public class UnitOfMeasure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uom;
}
