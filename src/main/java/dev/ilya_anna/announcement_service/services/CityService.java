package dev.ilya_anna.announcement_service.services;

import java.util.List;

import dev.ilya_anna.announcement_service.dto.CityDto;

public interface CityService {
  List<CityDto> getCities();
}
