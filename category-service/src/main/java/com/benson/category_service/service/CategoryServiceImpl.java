package com.benson.category_service.service;

import com.benson.category_service.exceptions.CategoryAlreadyExistsException;
import com.benson.category_service.exceptions.CategoryNotFoundException;
import com.benson.category_service.model.Category;
import com.benson.category_service.model.dto.request.CreateCategoryDTO;
import com.benson.category_service.model.dto.response.CategoryDTO;
import com.benson.category_service.repository.CategoryRepository;
import com.benson.category_service.utils.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {


    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(@Autowired CategoryRepository categoryRepository,@Autowired CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryDTO createCategory(CreateCategoryDTO createCategoryDTO) throws CategoryAlreadyExistsException {

        //check that the category does not exist
        if(!categoryRepository.findByName(createCategoryDTO.name()).isEmpty()){
            throw new CategoryAlreadyExistsException("Category with name " + createCategoryDTO.name() + " already exists");
        }

        //map to entity
        Category category = categoryMapper.toEntity(createCategoryDTO);

        Category savedCategory = categoryRepository.save(category);

        return categoryMapper.toDto(savedCategory);
    }


    @Override
    public List<CategoryDTO> getCategoriesByUserId(Long userId) {

        //get all categories by user id
        List<Category> categories = categoryRepository.findByUserId(userId);

        return categoryMapper.toDto(categories);
    }

    @Override
    public void deleteCategoryById(Long id) throws CategoryNotFoundException {

            Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category with id " + id + " not found"));

            categoryRepository.delete(category);

    }
}
