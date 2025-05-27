package dev.ilya_anna.announcement_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AnnouncementCardDto {
  private String id;
  private String title;
  private String creatorId;
  private Boolean isActive;
  private String previewImageId;
  private String subcategoryName;
  private String categoryName;
  private String cityName;
  private String categoryId;
  private String subcategoryId;
  private String cityId;
}
