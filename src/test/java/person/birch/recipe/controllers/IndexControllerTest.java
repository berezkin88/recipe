package person.birch.recipe.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import person.birch.recipe.domain.Recipe;
import person.birch.recipe.services.RecipeService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class IndexControllerTest {

    private static final String INDEX = "index";

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    @Captor
    ArgumentCaptor<List<Recipe>> captor;

    IndexController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        controller = new IndexController(recipeService);
    }

    @Test
    void testMockMvc() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name(INDEX));
    }

    @Test
    void getIndexPage() {

        List<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe());
        recipes.add(new Recipe());

        when(recipeService.findAll()).thenReturn(recipes);

        String actual = controller.getIndexPage(model);

        verify(model, times(1)).addAttribute(anyString(), captor.capture());
        verify(recipeService, times(1)).findAll();

        assertEquals(INDEX, actual);
        assertEquals(2, captor.getValue().size());
    }
}