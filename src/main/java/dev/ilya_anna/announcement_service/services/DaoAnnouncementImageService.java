package dev.ilya_anna.announcement_service.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dev.ilya_anna.announcement_service.dto.AnnouncementDto;
import dev.ilya_anna.announcement_service.entities.Announcement;
import dev.ilya_anna.announcement_service.entities.Image;
import dev.ilya_anna.announcement_service.exceptions.AnnouncementNotFoundException;
import dev.ilya_anna.announcement_service.repositories.AnnouncementRepository;
import jakarta.transaction.Transactional;

@Service
public class DaoAnnouncementImageService implements AnnouncementImageService {

  @Autowired
  private ImageService imageService;

  @Autowired
  private AnnouncementRepository announcementRepository;

  @Override
  @Transactional
  public AnnouncementDto uploadImage(String announcementId, MultipartFile imageFile) {

    Announcement announcement = announcementRepository.findById(announcementId)
      .orElseThrow(() -> new AnnouncementNotFoundException("Announcement with id: " + announcementId + " not found"));
    
    Image image = imageService.uploadImage(imageFile);

    announcement.getImages().add(image);
    announcement = announcementRepository.save(announcement);
    return AnnouncementDto.builder()
      .id(announcement.getId())
      .title(announcement.getTitle())
      .description(announcement.getDescription())
      .address(announcement.getAddress())
      .price(announcement.getPrice())
      .creatorId(announcement.getCreator().getId())
      .previewImageId(announcement.getPreviewImage().getId())
      .cityId(announcement.getCity().getId())
      .cityName(announcement.getCity().getName())
      .categoryId(announcement.getSubcategory().getCategory().getId())
      .categoryName(announcement.getSubcategory().getCategory().getName())
      .subcategoryId(announcement.getSubcategory().getId())
      .subcategoryName(announcement.getSubcategory().getName())
      .imageIds(announcement.getImages().stream().map(i -> i.getId()).toList())
      .isActive(announcement.getIsActive())
      .build();

  }

  @Override
  @Transactional
  public AnnouncementDto deleteImage(String announcementId, String imageId) {

    Announcement announcement = announcementRepository.findById(announcementId)
      .orElseThrow(() -> new AnnouncementNotFoundException("Announcement with id: " + announcementId + " not found"));
    imageService.deleteImage(imageId);
    announcement.getImages().removeIf(i -> i.getId().equals(imageId));
    announcement = announcementRepository.save(announcement);
    return AnnouncementDto.builder()
      .id(announcement.getId())
      .title(announcement.getTitle())
      .description(announcement.getDescription())
      .address(announcement.getAddress())
      .price(announcement.getPrice())
      .creatorId(announcement.getCreator().getId())
      .previewImageId(announcement.getPreviewImage().getId())
      .cityId(announcement.getCity().getId())
      .cityName(announcement.getCity().getName())
      .categoryId(announcement.getSubcategory().getCategory().getId())
      .categoryName(announcement.getSubcategory().getCategory().getName())
      .subcategoryId(announcement.getSubcategory().getId())
      .subcategoryName(announcement.getSubcategory().getName())
      .imageIds(announcement.getImages().stream().map(image -> image.getId()).toList())
      .isActive(announcement.getIsActive())
      .build();
  }

  @Override
  @Transactional
  public AnnouncementDto updatePreviewImage(String announcementId, MultipartFile previewImageFile) {

    Announcement announcement = announcementRepository.findById(announcementId)
      .orElseThrow(() -> new AnnouncementNotFoundException("Announcement with id: " + announcementId + " not found"));
    imageService.deleteImage(announcement.getPreviewImage().getId());
    Image previewImage = imageService.uploadImage(previewImageFile);
    announcement.setPreviewImage(previewImage);
    announcement = announcementRepository.save(announcement);
    return AnnouncementDto.builder()
      .id(announcement.getId())
      .title(announcement.getTitle())
      .description(announcement.getDescription())
      .address(announcement.getAddress())
      .price(announcement.getPrice())
      .creatorId(announcement.getCreator().getId())
      .previewImageId(announcement.getPreviewImage().getId())
      .cityId(announcement.getCity().getId())
      .cityName(announcement.getCity().getName())
      .categoryId(announcement.getSubcategory().getCategory().getId())
      .categoryName(announcement.getSubcategory().getCategory().getName())
      .subcategoryId(announcement.getSubcategory().getId())
      .subcategoryName(announcement.getSubcategory().getName())
      .imageIds(announcement.getImages().stream().map(image -> image.getId()).toList())
      .isActive(announcement.getIsActive())
      .build();
  }
  
}
