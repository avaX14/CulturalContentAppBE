package com.kts.cultural_content.controller;
import com.kts.cultural_content.service.imageupload.ImagesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/images")
public class ImagesController {

    @Autowired
    private ImagesService imagesService;
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/offers/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity changeOfferImage(@RequestParam("file") MultipartFile file, @PathVariable("id") Long id){
        imagesService.uploadOfferImage(file, id);
        return ResponseEntity.ok().build();
    }
}
