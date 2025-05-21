package dev.ilya_anna.announcement_service.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import dev.ilya_anna.announcement_service.entities.Announcement;
@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, String> {
  @Query("SELECT a FROM Announcement a WHERE (:cityId is null a.city.id = :cityId) " + 
  "AND (:subcategoryName is null a.subcategory.name = :subcategoryName) " + 
  "AND (:categoryName is null a.subcategory.category.name = :categoryName) " + 
  "AND a.title LIKE %:query%")
  Page<Announcement> search(String query, String cityId, 
      String subcategoryName, 
      String categoryName, 
      PageRequest pageRequest);

  boolean existsByCreatorIdAndTitle(String creatorId, String title);
} 
