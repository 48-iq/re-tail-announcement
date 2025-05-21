package dev.ilya_anna.announcement_service.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.ilya_anna.announcement_service.dto.AnnouncementDto;

@RestController
@RequestMapping("/api/v1/announcements")
public class AnnouncementController {
  
  @GetMapping("/{id}")
  public ResponseEntity<AnnouncementDto> getAnnouncementById(@PathVariable String id) {
    return null;
  }

  @PostMapping
  public ResponseEntity<AnnouncementDto> createAnnouncement(AnnouncementDto announcementDto) {
    return null;
  }

  @PutMapping("/{id}")
  public ResponseEntity<AnnouncementDto> updateAnnouncement(
      @PathVariable String id,
      @RequestBody AnnouncementDto announcementDto) {
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
