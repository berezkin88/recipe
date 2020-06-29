package person.birch.recipe.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import person.birch.recipe.commands.RecipeCommand;
import person.birch.recipe.domain.Recipe;
import person.birch.recipe.services.RecipeService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Aleksandr Beryozkin
 */

public class RecipeControllerTest {

    @Mock
    private RecipeService recipeService;

    private RecipeController controller;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new RecipeController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetRecipe() throws Exception {

        Recipe recipe = new Recipe();
        recipe.setId(1L);


        when(recipeService.findById(anyLong())).thenReturn(recipe);

        mockMvc.perform(get("/recipe/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/show"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void testPostNewRecipeForm() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId(2L);

        when(recipeService.saveRecipeCommand(any(RecipeCommand.class))).thenReturn(command);

        mockMvc.perform(post("/recipe")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("id", "")
        .param("description", "some string"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/show"));
    }

    @Test
    void testGetUpdateView() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId(2L);

        when(recipeService.findCommandById(anyLong())).thenReturn(command);

        mockMvc.perform(get("/recipe/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void testDeleteAction() throws Exception {
        mockMvc.perform(get("/recipe/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(recipeService, times(1)).deleteById(anyLong());
    }
}
