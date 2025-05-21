package dev.ilya_anna.announcement_service.services;

import dev.ilya_anna.announcement_service.dto.AnnouncementCreateDto;
import dev.ilya_anna.announcement_service.dto.AnnouncementDto;
import dev.ilya_anna.announcement_service.exceptions.AnnouncementAlreadyExistsException;
import jakarta.validation.Valid;
import dev.ilya_anna.announcement_service.entities.Announcement;

public interface AnnouncementService {

  /**
   * save announcement to storage
   * @param announcementCreateDto
   * @return created announcement
   * @see Announcement
   * @throws AnnouncementAlreadyExistsException
   */
  AnnouncementDto create(@Valid AnnouncementCreateDto announcementCreateDto) throws AnnouncementAlreadyExistsException;

  /**
   * delete announcement from storage
   * @param id
   * @see Announcement
   */
  void delete(String id);

  /**
   * update announcement in storage
   * @param id
   * @param announcementDto
   * @return updated announcement
   * @see Announcement
   */
  AnnouncementDto update(String id, @Valid AnnouncementDto announcementDto);

  /**
   * count announcements by user in storage
   * @param userId
   * @return number of announcements
   */
  Integer countAnnouncementsByUser(String userId);
}
