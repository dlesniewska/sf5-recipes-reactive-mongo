package dagimon.spring5course.recipes.converters;

import dagimon.spring5course.recipes.commands.UnitOfMeasureCommand;
import dagimon.spring5course.recipes.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitOfMeasureToUnitOfMeasureCommandTest {

    private final static Long OBJECT_ID = 1L;
    private final static String OBJECT_DESC = "DESC";

    private UnitOfMeasureToUnitOfMeasureCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    public void convertNull() {
        assertNull(converter.convert(null));
    }

    @Test
    public void convertEmpty() {
        assertNotNull(converter.convert(new UnitOfMeasure()));
    }

    @Test
    public void convert() {
        //given
        UnitOfMeasure objectToConvert = new UnitOfMeasure();
        objectToConvert.setId(OBJECT_ID);
        objectToConvert.setDescription(OBJECT_DESC);

        //when
        UnitOfMeasureCommand objectConverted = converter.convert(objectToConvert);

        //then
        assertNotNull(objectConverted);
        assertEquals(OBJECT_ID, objectConverted.getId());
        assertEquals(OBJECT_DESC, objectConverted.getDescription());
    }
}