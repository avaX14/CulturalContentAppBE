package com.kts.cultural_content.service;

import com.kts.cultural_content.model.CategoryType;
import com.kts.cultural_content.model.CulturalContentCategory;
import com.kts.cultural_content.repository.CategoryTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryTypeService {

    @Autowired
    private CategoryTypeRepository categoryTypeRepository;

    @Autowired
    private CulturalContentCategoryService culturalContentCategoryService;

    public Page<CategoryType> findAll(Pageable pageable) {

        return categoryTypeRepository.findAll(pageable);
    }

    public List<CategoryType> findAllByCategoryId(Long categoryId) {

        return categoryTypeRepository.findByCategoryId(categoryId);
    }

    public CategoryType findOne(Long categoryId, Long id) {

        return categoryTypeRepository.findByCategoryIdAndId(categoryId, id);
    }

    public CategoryType create(CategoryType entity, Long categoryId) throws Exception {
        if(categoryTypeRepository.findByName(entity.getName()) != null){
            throw new Exception("Category type with given name already exists.");
        }
        CulturalContentCategory culturalContentCategory = culturalContentCategoryService.findOne(categoryId);
        if(culturalContentCategory == null) {
            throw new Exception("Chosen category doesn't exist.");
        }
        entity.setCategory(culturalContentCategory);
        return categoryTypeRepository.save(entity);
    }

    public CategoryType update(CategoryType entity, Long id, Long categoryId) throws Exception {
        CategoryType existingCCategoryType =  categoryTypeRepository.findByCategoryIdAndId(categoryId, id);
        if(existingCCategoryType == null){
            throw new Exception("Category type with given id doesn't exist");
        }
        existingCCategoryType.setName(entity.getName());
        if(categoryTypeRepository.findByNameAndIdNot(existingCCategoryType.getName(), id) != null){
            throw new Exception("Category type with given name already exists");
        }
        return categoryTypeRepository.save(existingCCategoryType);
    }

    public void delete(Long id, Long categoryId) throws Exception {
        CategoryType existingCategoryType = categoryTypeRepository.findByCategoryIdAndId(categoryId, id);
        if(existingCategoryType == null){
            throw new Exception("Category type with given id doesn't exist");
        }
        categoryTypeRepository.delete(existingCategoryType);
    }
}
