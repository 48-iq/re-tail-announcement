package dev.ilya_anna.announcement_service.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import dev.ilya_anna.announcement_service.entities.Image;

public interface ImageService {
  Resource getImageById(String id);
  /**
   * save image to storage
   * @param file 
   * @return generated id of image
   */
  Image uploadImage(MultipartFile file);

  /**
   * delete image from storage
   * @param id
   */
  void deleteImage(String id);

}
