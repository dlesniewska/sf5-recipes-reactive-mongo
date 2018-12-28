package dagimon.spring5course.recipes.services;

import dagimon.spring5course.recipes.commands.UnitOfMeasureCommand;
import dagimon.spring5course.recipes.converters.UnitOfMeasureToUnitOfMeasureCommand;
import dagimon.spring5course.recipes.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository uomRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand converter;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository uomRepository, UnitOfMeasureToUnitOfMeasureCommand converter) {
        this.uomRepository = uomRepository;
        this.converter = converter;
    }

    @Override
    public Set<UnitOfMeasureCommand> listAllCommands() {
        return StreamSupport.stream(uomRepository.findAll().spliterator(), false)
                .map(uom -> converter.convert(uom))
                .collect(Collectors.toSet());
    }
}
