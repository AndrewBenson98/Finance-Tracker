package com.benson.category_service.utils;

import com.benson.category_service.model.Category;
import com.benson.category_service.model.dto.request.CreateCategoryDTO;
import com.benson.category_service.model.dto.response.CategoryDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTO toDto(Category category);
    Category toEntity(CategoryDTO categoryDTO);
    Category toEntity(CreateCategoryDTO createCategoryDTO);
    List<CategoryDTO> toDto(List<Category> categoryList);

}
