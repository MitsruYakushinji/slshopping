package com.example.slshopping.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.slshopping.entity.Category;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    // カテゴリー検索画面
    @GetMapping
    public String listCategories(@RequestParam(required = false) String keyword, Model model){
        List<Category> listCategories = categoryService.listAll(keyword);
        model.addAttribute("listCategories", listCategories);
        model.addAttribute("keyword", keyword);
        return "categories/categories";
    }

    // カテゴリー新規登録画面表示
    @GetMapping("/new")
    public String newCategoryForm(Model model){
        Category category = new Category();
        model.addAttribute("category", category);
        return "categories/category_form";
    }

    // カテゴリー新規登録処理
    @PostMapping("/save")
    public String newCategory(@Valid @ModelAttribute Category category, BindingResult result, Model model, RedirectAttributes ra){
        // 入力値のチェック
        if(result.hasErrors()){
            model.addAttribute("errors", result.getAllErrors());
            return "categories/category_form";
        }
        
        // 重複チェック
        if(!categoryService.checkUnique(category)){
            model.addAttribute("error_message", "重複しています");
            return "categories/category_form";
        }

        // カテゴリー情報の保存
        categoryService.save(category);
        // 登録成功のメッセージを格納
        ra.addFlashAttribute("success_message", "登録に成功しました。");
        return "redirect:/categories";
    }

    // カテゴリー編集画面表示
    @GetMapping("/edit/{id}")
    public String editCategoryForm(@PathVariable(name = "id") Long id, Model model, RedirectAttributes ra){
        try{
        Category category = categoryService.get(id);
        model.addAttribute("category", category);
        return "categories/category_form";
        } catch (NotFoundException e) {
            ra.addFlashAttribute("error_message", "対象のデータが見つかりませんでした。");
            return "redirect:/categories";
        }
    }

    // カテゴリー更新処理
    @PostMapping("/edit/{id}")
    public String editCategory(@Valid @ModelAttribute Category category, BindingResult result, Model model, RedirectAttributes ra){
        // 入力値チェック
        if(result.hasErrors()){
            model.addAttribute("errors", result.getAllErrors());
            return "categories/category_edit";
        }

        // 重複チェック
        if(!categoryService.checkUnique(category)){
            model.addAttribute("error_message", "重複しています");
            return "categories/category_edit";
        }

        // カテゴリー情報の更新
        categoryService.save(category);
        // 更新成功後のメッセージを格納
        ra.addFlashAttribute("success_message", "更新に成功しました");
        return "redirect:/categories";
    }

}
