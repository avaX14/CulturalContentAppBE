package com.kts.cultural_content.controller;

import com.kts.cultural_content.dto.CategoryTypeDTO;
import com.kts.cultural_content.dto.CulturalContentCategoryDTO;
import com.kts.cultural_content.helper.CategoryTypeMapper;
import com.kts.cultural_content.helper.CulturalContentCategoryMapper;
import com.kts.cultural_content.model.CategoryType;
import com.kts.cultural_content.model.CulturalContentCategory;
import com.kts.cultural_content.service.CategoryTypeService;
import com.kts.cultural_content.service.CulturalContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value =  "/api/cultural-content-category", produces = MediaType.APPLICATION_JSON_VALUE)
public class CulturalContentCategoryController {

    @Autowired
    private CulturalContentCategoryService culturalContentCategoryService;

    private CulturalContentCategoryMapper culturalContentCategoryMapper;

    private CategoryTypeMapper categoryTypeMapper;

    @Autowired
    private CategoryTypeService categoryTypeService;

    @RequestMapping(method = RequestMethod.GET)
    // @GetMapping
    public ResponseEntity<List<CulturalContentCategoryDTO>> getAllCulturalContentCategories() {
        List<CulturalContentCategory> culturalContentCategories = culturalContentCategoryService.findAll();

        return new ResponseEntity<>(toCulturalContentCategoryDTOList(culturalContentCategories), HttpStatus.OK);
    }

    @RequestMapping(value= "/by-page",method = RequestMethod.GET)
    public ResponseEntity<Page<CulturalContentCategoryDTO>> getAllCulturalContentCategories(Pageable pageable) {
        Page<CulturalContentCategory> page = culturalContentCategoryService.findAll(pageable);
        List<CulturalContentCategoryDTO> culturalContentCategoryDTOS = toCulturalContentCategoryDTOList(page.toList());
        Page<CulturalContentCategoryDTO> pageCulturalContentCategoryDTOS = new PageImpl<>(culturalContentCategoryDTOS,page.getPageable(),page.getTotalElements());

        return new ResponseEntity<>(pageCulturalContentCategoryDTOS, HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<CulturalContentCategoryDTO> getCulturalContentCategory(@PathVariable Long id){
        CulturalContentCategory culturalContentCategory = culturalContentCategoryService.findOne(id);
        if(culturalContentCategory == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(culturalContentCategoryMapper.toDto(culturalContentCategory), HttpStatus.OK);
    }

    @RequestMapping(method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CulturalContentCategoryDTO> createCulturalContentCategory(@RequestBody CulturalContentCategoryDTO culturalContentCategoryDTO){
        CulturalContentCategory culturalContentCategory;
        try {
            culturalContentCategory = culturalContentCategoryService.create(culturalContentCategoryMapper.toEntity(culturalContentCategoryDTO));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(culturalContentCategoryMapper.toDto(culturalContentCategory), HttpStatus.CREATED);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CulturalContentCategoryDTO> updateCulturalContentCategory(
            @RequestBody CulturalContentCategoryDTO culturalContentCategoryDTO, @PathVariable Long id){
        CulturalContentCategory culturalContentCategory;
        try {
            culturalContentCategory = culturalContentCategoryService.update(culturalContentCategoryMapper.toEntity(culturalContentCategoryDTO), id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(culturalContentCategoryMapper.toDto(culturalContentCategory), HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> deleteCulturalContentCategory(@PathVariable Long id){
        try {
            culturalContentCategoryService.delete(id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{categoryId}/category-types", method = RequestMethod.GET)
    public ResponseEntity<List<CategoryTypeDTO>> getAllCategoryTypes(@PathVariable Long categoryId) {
        List<CategoryType> categoryTypes = categoryTypeService.findAll(categoryId);
        return new ResponseEntity<>(toCategoryTypeDTOList(categoryTypes), HttpStatus.OK);
    }

    @RequestMapping(value="/{categoryId}/category-types/{id}", method=RequestMethod.GET)
    public ResponseEntity<CategoryTypeDTO> getCategoryType(@PathVariable("categoryId") Long categoryId,
                                                        @PathVariable("id") Long id){
        CategoryType categoryType = categoryTypeService.findOne(categoryId, id);
        if(categoryType == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(categoryTypeMapper.toDto(categoryType), HttpStatus.OK);
    }

    @RequestMapping(value="/{categoryId}/category-types", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryTypeDTO> createCategoryType(@RequestBody CategoryTypeDTO categoryTypeDTO,
                                                              @PathVariable Long categoryId){
        CategoryType categoryType;
        try {
            categoryType = categoryTypeService.create(categoryTypeMapper.toEntity(categoryTypeDTO), categoryId);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(categoryTypeMapper.toDto(categoryType), HttpStatus.CREATED);
    }

    @RequestMapping(value="/{categoryId}/category-types/{id}", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryTypeDTO> updateCulturalContentCategory(
            @RequestBody CategoryTypeDTO categoryTypeDTO,@PathVariable Long categoryId, @PathVariable Long id){
        CategoryType categoryType;
        try {
            categoryType = categoryTypeService.update(categoryTypeMapper.toEntity(categoryTypeDTO), id, categoryId);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(categoryTypeMapper.toDto(categoryType), HttpStatus.OK);
    }

    @RequestMapping(value="/{categoryId}/category-types/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> deleteCategoryType(@PathVariable Long categoryId, @PathVariable Long id){
        try {
            categoryTypeService.delete(id, categoryId);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public CulturalContentCategoryController() {

        culturalContentCategoryMapper = new CulturalContentCategoryMapper();
        categoryTypeMapper = new CategoryTypeMapper();
    }

    private List<CulturalContentCategoryDTO> toCulturalContentCategoryDTOList(List<CulturalContentCategory> culturalContentCategories){
        List<CulturalContentCategoryDTO> culturalContentCategoryDTOS = new ArrayList<>();
        for (CulturalContentCategory culturalContentCategory: culturalContentCategories) {
            culturalContentCategoryDTOS.add(culturalContentCategoryMapper.toDto(culturalContentCategory));
        }
        return culturalContentCategoryDTOS;
    }

    private List<CategoryTypeDTO> toCategoryTypeDTOList(List<CategoryType> categoryTypes) {
        List<CategoryTypeDTO> categoryTypeDTOS = new ArrayList<>();
        for (CategoryType categoryType: categoryTypes) {
            categoryTypeDTOS.add(categoryTypeMapper.toDto(categoryType));
        }
        return categoryTypeDTOS;
    }
}
