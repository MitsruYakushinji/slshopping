package com.example.slshopping.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.slshopping.entity.Category;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> listAll() {
        return categoryRepository.findAll();
    }

    public List<Category> listAll(String keyword) {
        if(keyword != null && !keyword.isEmpty()){
            return categoryRepository.search(keyword);
        }else{
            return categoryRepository.findAll();
        }
    }
}
