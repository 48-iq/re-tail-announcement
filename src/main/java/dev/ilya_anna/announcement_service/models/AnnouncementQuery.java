package dev.ilya_anna.announcement_service.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
/**
 * Class AnnouncementQuery
 * use for aggregation of query filters
 */
public class AnnouncementQuery {
  private String query;
  private Integer page;
  private Integer size;
  private String cityId;
  private String subcategoryName;
  private String categoryName;
}
