package person.birch.recipe.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import person.birch.recipe.domain.UnitOfMeasure;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UnitOfMeasureRepositoryTest {

    @Autowired
    UnitOfMeasureRepository repository;

    @BeforeEach
    void setUp() {

    }

    @Test
    void findByUom() {

        Optional<UnitOfMeasure> actual = repository.findByUom("Teaspoon");

        assertEquals("Teaspoon", actual.get().getUom());
    }

    @Test
    void findByUomCup() {

        Optional<UnitOfMeasure> actual = repository.findByUom("Cup");

        assertEquals("Cup", actual.get().getUom());
    }
}