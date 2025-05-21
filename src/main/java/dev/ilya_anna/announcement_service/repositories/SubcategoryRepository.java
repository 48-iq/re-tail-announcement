package dev.ilya_anna.announcement_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.ilya_anna.announcement_service.entities.Subcategory;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, String> {
}
