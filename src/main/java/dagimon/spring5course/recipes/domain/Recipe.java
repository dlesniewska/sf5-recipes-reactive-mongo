package dagimon.spring5course.recipes.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"ingredients", "category"})
@Document
public class Recipe {

    @Id
    private String id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    //@DBRef
    private Set<Ingredient> ingredients = new HashSet<>();
    @DBRef
    private Set<Category> category = new HashSet<>();
    private Difficulty difficulty;
    private Byte[] image;
    //@DBRef
    private Notes notes = new Notes();

    public void setNotes(Notes notes) {
        if (notes != null) {
            this.notes = notes;
        }
    }

    public Recipe addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
        return this;
    }

    public Recipe addCategory(Category category) {
        this.category.add(category);
        category.getRecipes().add(this);
        return this;
    }
}
