package com.kts.cultural_content.helper;

import com.kts.cultural_content.dto.CategoryTypeDTO;
import com.kts.cultural_content.model.CategoryType;

public class CategoryTypeMapper implements MapperInterface<CategoryType, CategoryTypeDTO> {
    @Override
    public CategoryType toEntity(CategoryTypeDTO dto) {
        return new CategoryType(dto.getName());
    }

    @Override
    public CategoryTypeDTO toDto(CategoryType entity) {
        return new CategoryTypeDTO(entity.getId(), entity.getName());
    }
}
