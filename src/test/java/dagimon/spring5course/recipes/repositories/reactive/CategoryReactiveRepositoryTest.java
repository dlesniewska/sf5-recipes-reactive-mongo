package dagimon.spring5course.recipes.repositories.reactive;

import dagimon.spring5course.recipes.domain.Category;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataMongoTest
public class CategoryReactiveRepositoryTest {

    public static final String CATEGORY = "category";
    @Autowired
    CategoryReactiveRepository repo;

    @Before
    public void setUp() throws Exception {
        repo.deleteAll().block();
    }

    @Test
    public void testSaveAndCount() {
        Category category = new Category();
        category.setDescription(CATEGORY);
        repo.save(category).block();

        assertEquals(Long.valueOf(1L), repo.count().block());
    }

    @Test
    public void testFindByDescription() {
        Category category = new Category();
        category.setDescription(CATEGORY);
        repo.save(category).block();

        Category categoryFound = repo.findByDescription(CATEGORY).block();
        assertNotNull(categoryFound.getId());
        assertEquals(CATEGORY, categoryFound.getDescription());
    }
}