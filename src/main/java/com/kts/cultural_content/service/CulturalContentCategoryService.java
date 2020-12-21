package com.kts.cultural_content.service;
import com.kts.cultural_content.model.CulturalContentCategory;
import com.kts.cultural_content.repository.CulturalContentCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CulturalContentCategoryService implements ServiceInterface<CulturalContentCategory> {

    @Autowired
    private CulturalContentCategoryRepository culturalContentCategoryRepository;

    @Override
    public List<CulturalContentCategory> findAll() {
        return culturalContentCategoryRepository.findAll();
    }

    public Page<CulturalContentCategory> findAll(Pageable pageable) {
        return culturalContentCategoryRepository.findAll(pageable);
    }

    @Override
    public CulturalContentCategory findOne(Long id) {
        return culturalContentCategoryRepository.findById(id).orElse(null);
    }

    @Override
    public CulturalContentCategory create(CulturalContentCategory entity) throws Exception {
        if(culturalContentCategoryRepository.findByName(entity.getName()) != null){
            throw new Exception("Cultural content category with given name already exists");
        }
        return culturalContentCategoryRepository.save(entity);
    }

    @Override
    public CulturalContentCategory update(CulturalContentCategory entity, Long id) throws Exception {
        CulturalContentCategory existingCulturalContentCategory =  culturalContentCategoryRepository.findById(id).orElse(null);
        if(existingCulturalContentCategory == null){
            throw new Exception("Cultural content category with given id doesn't exist");
        }
        existingCulturalContentCategory.setName(entity.getName());
        if(culturalContentCategoryRepository.findByNameAndIdNot(existingCulturalContentCategory.getName(), id) != null){
            throw new Exception("Cultural content category with given name already exists");
        }
        return culturalContentCategoryRepository.save(existingCulturalContentCategory);
    }

    /*
    * Kada brišemo kategoriju kulturne ponude (institucija, manifestacija...),
    * obrisaće se i svi tipovi te kategorije (muzeji, festivali...).
    * */
    @Override
    public void delete(Long id) throws Exception {
        CulturalContentCategory existingCulturalContentCategory = culturalContentCategoryRepository.findById(id).orElse(null);
        if(existingCulturalContentCategory == null){
            throw new Exception("Cultural content category with given id doesn't exist");
        }
        culturalContentCategoryRepository.delete(existingCulturalContentCategory);
    }
}
