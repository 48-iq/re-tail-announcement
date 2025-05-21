package dev.ilya_anna.announcement_service.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AnnouncementUpdateDto {
  private String title;
  private String description;
  private String address;
  private Double price;
  private MultipartFile previewImageFile;
  private Boolean isActive;
  private Integer count;
  private String subcategoryId;
  private String categoryId;
  private String cityName;
}
