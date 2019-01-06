package dagimon.spring5course.recipes.repositories.reactive;

import dagimon.spring5course.recipes.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureReactiveRepositoryTest {

    public static final String UOM = "uom";
    @Autowired
    UnitOfMeasureReactiveRepository repo;

    @Before
    public void setUp() throws Exception {
        repo.deleteAll().block();
    }

    @Test
    public void testSaveAndCount() {
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setDescription(UOM);
        repo.save(uom).block();

        assertEquals(Long.valueOf(1L), repo.count().block());
    }

    @Test
    public void testFindByDescription() {
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setDescription(UOM);
        repo.save(uom).block();

        UnitOfMeasure uomFound = repo.findByDescription(UOM).block();
        assertNotNull(uomFound.getId());
        assertEquals(UOM, uomFound.getDescription());
    }
}