package dagimon.spring5course.recipes.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(exclude = {"recipeNotes"})
//@Document
public class Notes {
    @Id
    private String id;
    private String recipeNotes;

    public Notes() {
    }

    public Notes(String recipeNotes) {
        this.recipeNotes = recipeNotes;
    }
}
