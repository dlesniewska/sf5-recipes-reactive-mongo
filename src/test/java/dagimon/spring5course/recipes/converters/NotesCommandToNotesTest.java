package dagimon.spring5course.recipes.converters;

import dagimon.spring5course.recipes.commands.NotesCommand;
import dagimon.spring5course.recipes.domain.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NotesCommandToNotesTest {

    private final static String OBJECT_ID = "1";
    private final static String OBJECT_DESC = "DESC";

    private NotesCommandToNotes converter;

    @Before
    public void setUp() throws Exception {
        converter = new NotesCommandToNotes();
    }

    @Test
    public void convertNull() {
        assertNull(converter.convert(null));
    }

    @Test
    public void convertEmpty() {
        assertNotNull(converter.convert(new NotesCommand()));
    }

    @Test
    public void convert() {
        //given
        NotesCommand objectToConvert = new NotesCommand();
        objectToConvert.setId(OBJECT_ID);
        objectToConvert.setRecipeNotes(OBJECT_DESC);

        //when
        Notes objectConverted = converter.convert(objectToConvert);

        //then
        assertNotNull(objectConverted);
        assertEquals(OBJECT_ID, objectConverted.getId());
        assertEquals(OBJECT_DESC, objectConverted.getRecipeNotes());
    }
}