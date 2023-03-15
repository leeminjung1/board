package com.leeminjung1.infrastructure.repository;

import com.leeminjung1.domain.model.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);

    List<Category> findAllByOrderByParentIdAscPriorityAsc();

    @Modifying
    @Query("delete from Category c where c.id in ?1")
    void deleteCategoriesWithIds(List<Long> ids);

    @Modifying
    @Query("update Category c set c.name = :name, c.priority = :priority, c.depth = :depth where c.id = :id")
    void update(Integer priority, Integer depth, String name, Long id);
}
