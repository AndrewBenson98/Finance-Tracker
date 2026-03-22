package com.benson.category_service.service;

import com.benson.category_service.exceptions.CategoryAlreadyExistsException;
import com.benson.category_service.exceptions.CategoryNotFoundException;
import com.benson.category_service.model.dto.request.CreateCategoryDTO;
import com.benson.category_service.model.dto.response.CategoryDTO;

import java.util.List;

public interface CategoryService {

    CategoryDTO createCategory(CreateCategoryDTO createCategoryDTO) throws CategoryAlreadyExistsException;

    List<CategoryDTO> getCategoriesByUserId(Long userId);

    void deleteCategoryById(Long id) throws CategoryNotFoundException;


}
