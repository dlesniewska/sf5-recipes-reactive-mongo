package dagimon.spring5course.recipes.converters;

import dagimon.spring5course.recipes.commands.CategoryCommand;
import dagimon.spring5course.recipes.domain.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryCommandToCategoryTest {

    private final static Long OBJECT_ID = 1L;
    private final static String OBJECT_DESC = "DESC";

    private CategoryCommandToCategory converter;

    @Before
    public void setUp() throws Exception {
        converter = new CategoryCommandToCategory();
    }

    @Test
    public void convertNull() {
        assertNull(converter.convert(null));
    }

    @Test
    public void convertEmpty() {
        assertNotNull(converter.convert(new CategoryCommand()));
    }

    @Test
    public void convert() {
        //given
        CategoryCommand objectToConvert = new CategoryCommand();
        objectToConvert.setId(OBJECT_ID);
        objectToConvert.setDescription(OBJECT_DESC);

        //when
        Category objectConverted = converter.convert(objectToConvert);

        //then
        assertNotNull(objectConverted);
        assertEquals(OBJECT_ID, objectConverted.getId());
        assertEquals(OBJECT_DESC, objectConverted.getDescription());
    }
}