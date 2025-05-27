package dev.ilya_anna.announcement_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import dev.ilya_anna.announcement_service.dto.AnnouncementDto;
import dev.ilya_anna.announcement_service.dto.AnnouncementUpdateDto;
import dev.ilya_anna.announcement_service.exceptions.AnnouncementNotFoundException;
import dev.ilya_anna.announcement_service.services.AnnouncementService;

@RestController
@RequestMapping("/api/v1/announcements")
public class AnnouncementController {

  @Autowired
  private AnnouncementService announcementService;
  
  @GetMapping("/{id}")
  public ResponseEntity<AnnouncementDto> getAnnouncementById(@PathVariable String id) {
    try {
      return ResponseEntity.ok(announcementService.getAnnouncementById(id));
    } catch (AnnouncementNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping
  public ResponseEntity<AnnouncementDto> createAnnouncement(AnnouncementDto announcementDto) {
    return null;
  }

  @PutMapping("/{id}")
  public ResponseEntity<AnnouncementDto> updateAnnouncement(
      @PathVariable String id,
      @RequestBody AnnouncementUpdateDto announcementDto) {
    return null;
  }

  @PutMapping("/{id}/preview")
  public ResponseEntity<AnnouncementDto> updatePreviewImage(
      @PathVariable String id, 
      @RequestParam("preview_image_file") MultipartFile previewImage) {
      
      return null;
  }

  @PostMapping("/{id}/images")
    public ResponseEntity<AnnouncementDto> uploadImage(
      @PathVariable String id, 
      @RequestParam("image_file") MultipartFile image) {
      
      return null;
  }

  @DeleteMapping("/{id}/images/{imageId}")
    public ResponseEntity<AnnouncementDto> deleteImage(
      @PathVariable String id, 
      @PathVariable String imageId) {
      
      return null;
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteAnnouncement(@PathVariable String id) {
    return null;
  }

  @GetMapping("/count-by-user/{userId}")
  public ResponseEntity<Integer> countAnnouncementsByUser(@PathVariable String userId) {
    return null;
  }
}
