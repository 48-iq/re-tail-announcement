package dev.ilya_anna.announcement_service.controllers;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/announcement-images")
public class ImageController {
  @GetMapping("/{id}")
  public ResponseEntity<Resource> getImageById(String id) {
    return null;
  }
}
