package project.first.spring.repositories;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.first.spring.entities.Beer;
import project.first.spring.entities.Category;

@SpringBootTest
class CategoryRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    Beer beer;

    @BeforeEach
    void setUp(){
        beer = beerRepository.findAll().get(0);
    }

    @Transactional
    @Test
    void testCategoryRepository(){
        Category savedCategory = categoryRepository.save(Category.builder()
                .description("test category")
                .build());

        beer.addCategory(savedCategory);
        Beer savedBeer = beerRepository.save(beer);
        System.out.println(beer.getBeerName());
    }

}