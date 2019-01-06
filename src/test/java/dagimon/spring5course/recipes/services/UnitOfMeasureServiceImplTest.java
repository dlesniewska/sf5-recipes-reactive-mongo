package dagimon.spring5course.recipes.services;

import dagimon.spring5course.recipes.commands.UnitOfMeasureCommand;
import dagimon.spring5course.recipes.converters.UnitOfMeasureToUnitOfMeasureCommand;
import dagimon.spring5course.recipes.domain.UnitOfMeasure;
import dagimon.spring5course.recipes.repositories.UnitOfMeasureRepository;
import dagimon.spring5course.recipes.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UnitOfMeasureServiceImplTest {

    UnitOfMeasureService uomService;
    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommandConverter = new UnitOfMeasureToUnitOfMeasureCommand();

    @Mock
    UnitOfMeasureReactiveRepository uomRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        uomService = new UnitOfMeasureServiceImpl(uomRepository, unitOfMeasureToUnitOfMeasureCommandConverter);
    }

    @Test
    public void listAllCommands() {
        //given
        Set<UnitOfMeasure> unitOfMeasures = new HashSet<>();
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setId("1");
        unitOfMeasures.add(uom1);
        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setId("2");
        unitOfMeasures.add(uom2);

        //when
        when(uomRepository.findAll()).thenReturn(Flux.just(uom1, uom2));

        //then
        List<UnitOfMeasureCommand> foundCommands = uomService.listAllCommands().collectList().block();
        assertEquals(2, foundCommands.size());
        verify(uomRepository).findAll();
    }
}