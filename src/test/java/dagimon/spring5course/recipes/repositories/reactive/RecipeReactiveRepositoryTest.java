package dagimon.spring5course.recipes.repositories.reactive;

import dagimon.spring5course.recipes.domain.Recipe;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataMongoTest
public class RecipeReactiveRepositoryTest {

    @Autowired
    RecipeReactiveRepository repo;

    @Before
    public void setUp() throws Exception {
        repo.deleteAll().block();
    }

    @Test
    public void testSaveAndCount() {
        Recipe recipe = new Recipe();
        recipe.setDescription("recipe");
        repo.save(recipe).block();

        assertEquals(Long.valueOf(1L), repo.count().block());
    }
}