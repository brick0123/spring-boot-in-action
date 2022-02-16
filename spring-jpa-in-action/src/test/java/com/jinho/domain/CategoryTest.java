package com.jinho.domain;

import javax.persistence.EntityManager;
import lombok.Data;
import lombok.ToString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CategoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    EntityManager em;

    @BeforeEach
    void setUp() {
        final Category category1 = new Category(100L);
        final Category category2 = new Category(101L);
        final Category category3 = new Category(103L);

        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);

        category1.setParentCategory(category2);
        category2.setParentCategory(category3);

        em.flush();
        em.clear();
    }

    @Test
    void 재귀_컨버트() {
        final Category category = categoryRepository.findByCode(100L).get();

        final CategoryDto categoryDto = new CategoryDto(category);
    }

    @Data
    @ToString
    public static class CategoryDto {

        private Long code;
        private CategoryDto parent;

        public CategoryDto(Category category) {
            this.code = category.getCategoryCode();
            this.parent = category.getParentCategory() != null ? new CategoryDto(category.getParentCategory()) : null;
        }
    }
}
