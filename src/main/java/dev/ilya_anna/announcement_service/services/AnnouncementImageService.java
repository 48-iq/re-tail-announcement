package dev.ilya_anna.announcement_service.services;

import org.springframework.web.multipart.MultipartFile;

import dev.ilya_anna.announcement_service.dto.AnnouncementDto;

public interface AnnouncementImageService {
  AnnouncementDto uploadImage(String announcementId, MultipartFile imageFile);
  AnnouncementDto deleteImage(String announcementId, String imageId);
  AnnouncementDto updatePreviewImage(String announcementId, MultipartFile previewImageFile);
} 
