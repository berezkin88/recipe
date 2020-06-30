package person.birch.recipe.services;

import person.birch.recipe.commands.UnitOfMeasureCommand;

import java.util.Set;

/**
 * @author Aleksandr Beryozkin
 */

public interface UnitOfMeasureService {

    Set<UnitOfMeasureCommand> listAllUoms();
}
