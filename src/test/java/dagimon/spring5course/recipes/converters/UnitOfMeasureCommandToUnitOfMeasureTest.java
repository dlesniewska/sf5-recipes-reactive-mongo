package dagimon.spring5course.recipes.converters;

import dagimon.spring5course.recipes.commands.UnitOfMeasureCommand;
import dagimon.spring5course.recipes.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitOfMeasureCommandToUnitOfMeasureTest {

    private final static Long OBJECT_ID = 1L;
    private final static String OBJECT_DESC = "DESC";

    private UnitOfMeasureCommandToUnitOfMeasure converter;

    @Before
    public void setUp() throws Exception {
        converter = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    public void convertNull() {
        assertNull(converter.convert(null));
    }

    @Test
    public void convertEmpty() {
        assertNotNull(converter.convert(new UnitOfMeasureCommand()));
    }

    @Test
    public void convert() {
        //given
        UnitOfMeasureCommand objectToConvert = new UnitOfMeasureCommand();
        objectToConvert.setId(OBJECT_ID);
        objectToConvert.setDescription(OBJECT_DESC);

        //when
        UnitOfMeasure objectConverted = converter.convert(objectToConvert);

        //then
        assertNotNull(objectConverted);
        assertEquals(OBJECT_ID, objectConverted.getId());
        assertEquals(OBJECT_DESC, objectConverted.getDescription());
    }
}