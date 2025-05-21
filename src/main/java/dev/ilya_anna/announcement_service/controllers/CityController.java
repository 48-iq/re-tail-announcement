package dev.ilya_anna.announcement_service.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.ilya_anna.announcement_service.dto.CityDto;
import dev.ilya_anna.announcement_service.services.CityService;

@RestController
@RequestMapping("/api/v1/cities")
public class CityController {
  @Autowired
  private CityService cityService;
  @GetMapping
  public ResponseEntity<List<CityDto>> getCities() {
    return ResponseEntity.ok(cityService.getCities());
  }
}
