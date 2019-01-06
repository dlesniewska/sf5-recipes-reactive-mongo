package dagimon.spring5course.recipes.services;

import dagimon.spring5course.recipes.commands.UnitOfMeasureCommand;
import reactor.core.publisher.Flux;

import java.util.Set;

public interface UnitOfMeasureService {
    Flux<UnitOfMeasureCommand> listAllCommands();
}
