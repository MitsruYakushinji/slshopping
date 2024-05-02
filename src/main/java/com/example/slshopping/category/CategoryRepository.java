package com.example.slshopping.category;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.slshopping.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

    public Category findByName(String name);

    @Query("SELECT c FROM Category c WHERE c.name LIKE %?1%")
    public List<Category> search(String keyword);
}
