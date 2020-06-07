package person.birch.recipe.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import person.birch.recipe.domain.*;
import person.birch.recipe.repositories.CategoryRepository;
import person.birch.recipe.repositories.RecipeRepository;
import person.birch.recipe.repositories.UnitOfMeasureRepository;

import java.math.BigDecimal;
import java.util.Set;

/**
 * @author Aleksandr Beryozkin
 */
@Component
public class DataLoader implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final RecipeRepository recipeRepository;

    public DataLoader(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository, RecipeRepository recipeRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loader();
    }

    private void loader() {
        recipeRepository.deleteAll();

        System.out.println("Database cleared");

        Category mexican = categoryRepository.findByDescription("Mexican").get();

        UnitOfMeasure tablespoon = unitOfMeasureRepository.findByUom("Tablespoon").get();
        UnitOfMeasure teaspoon = unitOfMeasureRepository.findByUom("Teaspoon").get();
        UnitOfMeasure clove = unitOfMeasureRepository.findByUom("Clove").get();
        UnitOfMeasure unit = unitOfMeasureRepository.findByUom("Unit").get();
        UnitOfMeasure dash = unitOfMeasureRepository.findByUom("Dash").get();

        Ingredient ancho = new Ingredient();
        ancho.setAmount(new BigDecimal(2));
        ancho.setDescription("ancho chili powder");
        ancho.setUom(tablespoon);

        Ingredient garlic = new Ingredient();
        garlic.setAmount(new BigDecimal(1));
        garlic.setDescription("garlic, finely chopped");
        garlic.setUom(clove);

        Ingredient oregano = new Ingredient();
        oregano.setAmount(new BigDecimal(1));
        oregano.setDescription("oregano");
        oregano.setUom(teaspoon);

        Ingredient chickenThigh = new Ingredient();
        chickenThigh.setAmount(new BigDecimal("1.250"));
        chickenThigh.setDescription("skinless, boneless chicken thighs");
        chickenThigh.setUom(teaspoon);

        Recipe recipeChicken = new Recipe();
        recipeChicken.setCategories(Set.of(mexican));
        recipeChicken.setIngredients(Set.of(ancho, oregano, garlic, chickenThigh));
        recipeChicken.setCookTime(15);
        recipeChicken.setDescription("Spicy grilled chicken tacos! Quick marinade, then grill. " +
                "Ready in about 30 minutes. Great for a quick weeknight dinner, backyard cookouts, " +
                "and tailgate parties.");
        recipeChicken.setDifficulty(Difficulty.EASY);
        recipeChicken.setDirections("Dinner, Grill");
        recipeChicken.setNotes(new Notes());
        recipeChicken.setPrepTime(20);
        recipeChicken.setServings(6);
        recipeChicken.setSource("Simply Recipes");
        recipeChicken.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");

        ancho.setRecipe(recipeChicken);
        oregano.setRecipe(recipeChicken);
        garlic.setRecipe(recipeChicken);
        chickenThigh.setRecipe(recipeChicken);

        Recipe savedRecipe1 = recipeRepository.save(recipeChicken);

        System.out.println("Recipe with id " + savedRecipe1.getId() + " saved");

        Ingredient avocado = new Ingredient();
        avocado.setAmount(new BigDecimal(2));
        avocado.setDescription("avocados");
        avocado.setUom(unit);

        Ingredient salt = new Ingredient();
        salt.setAmount(new BigDecimal("0.25"));
        salt.setDescription("salt");
        salt.setUom(teaspoon);

        Ingredient limeJuice = new Ingredient();
        limeJuice.setAmount(new BigDecimal(1));
        limeJuice.setDescription("fresh lime juice or lemon juice");
        limeJuice.setUom(tablespoon);

        Ingredient blackPepper = new Ingredient();
        blackPepper.setAmount(new BigDecimal(1));
        blackPepper.setDescription("freshly grated black pepper");
        blackPepper.setUom(dash);

        Recipe recipeGuacamole = new Recipe();
        recipeGuacamole.setCategories(Set.of(mexican));
        recipeGuacamole.setIngredients(Set.of(avocado, salt, limeJuice, blackPepper));
        recipeGuacamole.setCookTime(10);
        recipeGuacamole.setDescription("The best guacamole keeps it simple: just ripe avocados, salt, " +
                "a squeeze of lime, onions, chiles, cilantro, and some chopped tomato. " +
                "Serve it as a dip at your next party or spoon it on top of tacos for an easy dinner upgrade.");
        recipeGuacamole.setDifficulty(Difficulty.MODERATE);
        recipeGuacamole.setDirections("Video, Dip");
        recipeGuacamole.setNotes(new Notes());
        recipeGuacamole.setPrepTime(10);
        recipeGuacamole.setServings(4);
        recipeGuacamole.setSource("Simply Recipes");
        recipeGuacamole.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");

        avocado.setRecipe(recipeGuacamole);
        salt.setRecipe(recipeGuacamole);
        limeJuice.setRecipe(recipeGuacamole);
        blackPepper.setRecipe(recipeGuacamole);

        Recipe savedRecipe2 = recipeRepository.save(recipeGuacamole);

        System.out.println("Recipe with id " + savedRecipe2.getId() + " saved");

        System.out.println("Recipes saved");
    }
}
