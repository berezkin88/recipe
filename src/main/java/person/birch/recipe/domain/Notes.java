package person.birch.recipe.domain;

import lombok.*;

import javax.persistence.*;

/**
 * @author Aleksandr Beryozkin
 */
@Getter
@Setter
@Entity
@EqualsAndHashCode(exclude = "recipes")
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Recipe recipe;

    @Lob
    private String recipeNotes;
}
