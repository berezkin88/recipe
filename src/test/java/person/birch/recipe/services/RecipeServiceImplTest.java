package person.birch.recipe.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import person.birch.recipe.domain.Recipe;
import person.birch.recipe.repositories.RecipeRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {

    RecipeService recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        recipeService = new RecipeServiceImpl(recipeRepository);
    }

    @Test
    void findAll() {

        Recipe recipe = new Recipe();
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe);

        when(recipeRepository.findAll()).thenReturn(recipes);

        List<Recipe> actual = recipeService.findAll();

        assertEquals(1, actual.size());
        verify(recipeRepository, times(1)).findAll();
    }
}