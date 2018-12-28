package dagimon.spring5course.recipes.converters;

import dagimon.spring5course.recipes.commands.NotesCommand;
import dagimon.spring5course.recipes.domain.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NotesToNotesCommandTest {

    private final static Long OBJECT_ID = 1L;
    private final static String OBJECT_DESC = "DESC";

    private NotesToNotesCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new NotesToNotesCommand();
    }

    @Test
    public void convertNull() {
        assertNull(converter.convert(null));
    }

    @Test
    public void convertEmpty() {
        assertNotNull(converter.convert(new Notes()));
    }

    @Test
    public void convert() {
        //given
        Notes objectToConvert = new Notes();
        objectToConvert.setId(OBJECT_ID);
        objectToConvert.setRecipeNotes(OBJECT_DESC);

        //when
        NotesCommand objectConverted = converter.convert(objectToConvert);

        //then
        assertNotNull(objectConverted);
        assertEquals(OBJECT_ID, objectConverted.getId());
        assertEquals(OBJECT_DESC, objectConverted.getRecipeNotes());
    }
}