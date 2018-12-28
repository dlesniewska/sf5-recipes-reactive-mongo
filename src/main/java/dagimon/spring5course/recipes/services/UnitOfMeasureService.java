package dagimon.spring5course.recipes.services;

import dagimon.spring5course.recipes.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {
    Set<UnitOfMeasureCommand> listAllCommands();
}
