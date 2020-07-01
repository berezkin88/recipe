package person.birch.recipe.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Aleksandr Beryozkin
 */
@Getter
@Setter
@Entity
@EqualsAndHashCode(exclude = "recipes")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    @ManyToMany(mappedBy = "categories", fetch = FetchType.EAGER)
    private Set<Recipe> recipes;
}
