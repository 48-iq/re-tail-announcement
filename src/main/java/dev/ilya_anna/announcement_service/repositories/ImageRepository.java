package dev.ilya_anna.announcement_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.ilya_anna.announcement_service.entities.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {
}
