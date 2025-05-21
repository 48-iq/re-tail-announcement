package dev.ilya_anna.announcement_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnnouncementDto {
  private String id;
  private String title;
  private String description;
  private String address;
  private Double price;
  private String createdAt;
  private String creatorId;
  private String previewImageId;
  private Boolean isActive;
  private Integer count;
  private String subcategoryId;
  private String categoryId;
  private String cityName;
}
