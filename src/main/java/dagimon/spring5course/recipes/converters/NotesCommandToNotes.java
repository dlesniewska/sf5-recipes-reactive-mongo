package dagimon.spring5course.recipes.converters;

import dagimon.spring5course.recipes.commands.IngredientCommand;
import dagimon.spring5course.recipes.commands.NotesCommand;
import dagimon.spring5course.recipes.domain.Ingredient;
import dagimon.spring5course.recipes.domain.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Notes> {

    @Synchronized
    @Nullable
    @Override
    public Notes convert(NotesCommand source) {
        if (source == null) {
            return null;
        }

        final Notes notes = new Notes();
        notes.setId(source.getId());
        notes.setRecipeNotes(source.getRecipeNotes());
        return notes;
    }
}
