package com.kts.cultural_content.service.imageupload;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImagesServiceImpl implements ImagesService {

	@Override
	public void uploadOfferImage(MultipartFile file, Long id) {
        try {
            String folder = "C:\\Users\\My\\OneDrive\\Dokumenti\\FAKS\\CulturalContentApp\\CulturalContentAppBE\\src\\main\\resources\\static\\";
            byte[] bytes = file.getBytes();
            Path path = Paths.get(folder + "offer-" + id + file.getOriginalFilename());
            Files.write(path, bytes);
        } catch (Exception e) {
            //TODO: handle exception
        }

		
	}
    
}
