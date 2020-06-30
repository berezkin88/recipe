package person.birch.recipe.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import person.birch.recipe.commands.IngredientCommand;
import person.birch.recipe.converters.IngredientToIngredientCommand;
import person.birch.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import person.birch.recipe.domain.Ingredient;
import person.birch.recipe.domain.Recipe;
import person.birch.recipe.repositories.RecipeRepository;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class IngredientServiceImplTest {

    private final IngredientToIngredientCommand ingredientToIngredientCommand
            = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());

    @Mock
    private RecipeRepository recipeRepository;

    IngredientService ingredientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        ingredientService = new IngredientServiceImpl(ingredientToIngredientCommand, recipeRepository);
    }

    @Test
    void findByRecipeIdAndIngredientId() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(2L);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);

        recipe.setIngredients(Set.of(ingredient1, ingredient2, ingredient3));

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        IngredientCommand actual = ingredientService.findByRecipeIdAndIngredientId(1L, 3L);

        verify(recipeRepository, times(1)).findById(anyLong());

        assertEquals(3L, actual.getId());
        assertEquals(1L, actual.getRecipeId());
    }
}