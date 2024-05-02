package com.example.slshopping.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
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

    // IDに紐づくカテゴリー情報取得処理
    public Category get(Long id) throws NotFoundException {
        return categoryRepository.findById(id)
            .orElseThrow(() -> new NotFoundException());
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public boolean checkUnique(Category category) {
        boolean isCreatingNew = (category.getId() == null || category.getId() == 0);
        Category categoryByName = categoryRepository.findByName(category.getName());

        if(isCreatingNew){
            if(categoryByName != null){
                return false;
            }
        } else {
            if(categoryByName != null && categoryByName.getId() != category.getId()){
                return false;
            }
        }
        return true;
    }
}
