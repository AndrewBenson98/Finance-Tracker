package com.benson.category_service.controller;

import com.benson.category_service.exceptions.CategoryNotFoundException;
import com.benson.category_service.model.dto.request.CreateCategoryDTO;
import com.benson.category_service.model.dto.response.CategoryDTO;
import com.benson.category_service.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CategoryController {

    private final CategoryService categoryService;

    public  CategoryController(@Autowired CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/categories")
    public ResponseEntity<CategoryDTO> createCategory(CreateCategoryDTO createCategoryDTO) throws CategoryNotFoundException {
        return  ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(createCategoryDTO));
    }

    @GetMapping("/categories/user/{id}")
    public ResponseEntity<List<CategoryDTO>> getCategoriesByUserId(@PathVariable Long id) throws CategoryNotFoundException {

        return ResponseEntity.ok(categoryService.getCategoriesByUserId(id));
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable Long id) throws CategoryNotFoundException {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build() ;
    }

}
