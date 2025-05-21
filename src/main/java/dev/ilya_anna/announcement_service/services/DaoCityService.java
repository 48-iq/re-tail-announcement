package dev.ilya_anna.announcement_service.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.ilya_anna.announcement_service.dto.CityDto;
import dev.ilya_anna.announcement_service.entities.City;
import dev.ilya_anna.announcement_service.repositories.CityRepository;

@Service
public class DaoCityService implements CityService {

  @Autowired
  private CityRepository cityRepository;

  @Override
  public List<CityDto> getCities() {
    return cityRepository.findAll()
        .stream()
        .map(city -> CityDto.builder()
            .id(city.getId())
            .name(city.getName())
            .build()
          )
        .toList();
  }
  
}
