package com.kts.cultural_content.service.imageupload;

import org.springframework.web.multipart.MultipartFile;

public interface ImagesService {
    void uploadOfferImage(MultipartFile file, Long id);   
}
