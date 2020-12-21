package com.kts.cultural_content.helper;

import com.kts.cultural_content.dto.CulturalContentCategoryDTO;
import com.kts.cultural_content.model.CulturalContentCategory;

public class CulturalContentCategoryMapper implements MapperInterface<CulturalContentCategory, CulturalContentCategoryDTO> {

    @Override
    public CulturalContentCategory toEntity(CulturalContentCategoryDTO dto) {
        return new CulturalContentCategory(dto.getName());
    }

    @Override
    public CulturalContentCategoryDTO toDto(CulturalContentCategory entity) {
        return new CulturalContentCategoryDTO(entity.getId(), entity.getName());
    }
}
