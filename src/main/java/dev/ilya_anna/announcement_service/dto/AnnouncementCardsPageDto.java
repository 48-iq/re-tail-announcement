package dev.ilya_anna.announcement_service.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnnouncementCardsPageDto {
  private Integer page;
  private Integer size;
  private Integer totalPages;
  private Long totalElements;
  private List<AnnouncementCardDto> announcements;
}
