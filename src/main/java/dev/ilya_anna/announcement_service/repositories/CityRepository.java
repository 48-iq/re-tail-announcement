package dev.ilya_anna.announcement_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.ilya_anna.announcement_service.entities.City;

@Repository
public interface CityRepository extends JpaRepository<City, String> {
}
