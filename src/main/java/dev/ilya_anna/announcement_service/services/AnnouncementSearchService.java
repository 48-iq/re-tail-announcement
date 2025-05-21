package dev.ilya_anna.announcement_service.services;

import dev.ilya_anna.announcement_service.dto.AnnouncementCardsPageDto;
import dev.ilya_anna.announcement_service.entities.Announcement;
import dev.ilya_anna.announcement_service.models.AnnouncementQuery;

public interface AnnouncementSearchService {
  /**
   * search announcements in storage
   * @param announcementQuery
   * @return page of announcements
   * @see Announcement
   * @see AnnouncementCardsPageDto
   */
  AnnouncementCardsPageDto searchCards(AnnouncementQuery announcementQuery);
}
