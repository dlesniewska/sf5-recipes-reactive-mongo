package dagimon.spring5course.recipes.repositories;

import dagimon.spring5course.recipes.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, String> {
}
