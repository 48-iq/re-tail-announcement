package dev.ilya_anna.announcement_service.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.ilya_anna.announcement_service.dto.AnnouncementCardDto;

@RestController
@RequestMapping("/api/v1/announcement-cards")
public class AnnouncementCardController {
  @GetMapping("/find")
  public ResponseEntity<AnnouncementCardDto> findAnnouncementCards(
      @RequestParam(name = "city_name", required = false) String cityName,
      @RequestParam(name = "subcategory_id", required = false) String subcategoryId,
      @RequestParam(name = "category_id", required = false) String categoryId,
      @RequestParam(name = "only_active", required = false) Boolean onlyActive,
      @RequestParam(name = "query", required = false) String query,
      @RequestParam(name = "page", required = false) Integer page,
      @RequestParam(name = "size", required = false) Integer size) {
    return null;
  }
}
