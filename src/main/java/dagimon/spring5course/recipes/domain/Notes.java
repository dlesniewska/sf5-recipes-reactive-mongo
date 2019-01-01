package dagimon.spring5course.recipes.domain;

import lombok.*;

import javax.persistence.*;

@Data
@EqualsAndHashCode(exclude = {"recipe", "recipeNotes"})
public class Notes {

    private String id;
    private Recipe recipe;
    private String recipeNotes;

    public Notes() {
    }

    public Notes(String recipeNotes) {
        this.recipeNotes = recipeNotes;
    }
}
