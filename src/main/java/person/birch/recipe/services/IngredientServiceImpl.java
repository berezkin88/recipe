package person.birch.recipe.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import person.birch.recipe.commands.IngredientCommand;
import person.birch.recipe.converters.IngredientCommandToIngredient;
import person.birch.recipe.converters.IngredientToIngredientCommand;
import person.birch.recipe.domain.Ingredient;
import person.birch.recipe.domain.Recipe;
import person.birch.recipe.repositories.RecipeRepository;
import person.birch.recipe.repositories.UnitOfMeasureRepository;

import java.util.Optional;

/**
 * @author Aleksandr Beryozkin
 */
@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository, IngredientCommandToIngredient ingredientCommandToIngredient) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (recipeOptional.isEmpty()) {
            // todo: implement error handling
            log.error("recipe id not found. Id: {}", recipeId);
        }

        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredientToIngredientCommand::convert)
                .findFirst();

        if (ingredientCommandOptional.isEmpty()) {
            // todo: implement error handling
            log.error("Ingredient id not found: {}", ingredientId);
        }

        return ingredientCommandOptional.orElse(null);
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());

        if (recipeOptional.isEmpty()) {
            //todo: throw an error if not found
            log.error("Recipe not found for id: {}", command.getRecipeId());
            return new IngredientCommand();
        } else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst();

            if (ingredientOptional.isPresent()) {
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(command.getDescription());
                ingredientFound.setAmount(command.getAmount());
                ingredientFound.setUom(unitOfMeasureRepository
                        .findById(command.getUnitOfMeasure().getId())
                        .orElseThrow(() -> new RuntimeException("UOM NOT FOUND")));
            } else {
                Ingredient ingredient = ingredientCommandToIngredient.convert(command);
                ingredient.setRecipe(recipe);
                recipe.getIngredients().add(ingredient);
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst();

            if (savedIngredientOptional.isEmpty()) {
                savedIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(ingredient -> ingredient.getDescription().equals(command.getDescription()))
                        .filter(ingredient -> ingredient.getAmount().equals(command.getAmount()))
                        .filter(ingredient -> ingredient.getUom().getId().equals(command.getUnitOfMeasure().getId()))
                        .findFirst();
            }

            return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
        }
    }

    @Override
    public void deleteIngredientFromRecipeById(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (recipeOptional.isEmpty()) {
            log.error("Recipe not found for id: {}", recipeId);
        }

        recipeOptional.ifPresent(recipe -> {
            recipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientId))
                    .findFirst()
                    .ifPresent(ingredient -> {
                        recipe.getIngredients().remove(ingredient);
                        ingredient.setRecipe(null);
                    });

            recipeRepository.save(recipeOptional.get());
        });

    }
}
