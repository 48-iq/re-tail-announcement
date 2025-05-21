package dev.ilya_anna.announcement_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import dev.ilya_anna.announcement_service.dto.AnnouncementCardDto;
import dev.ilya_anna.announcement_service.dto.AnnouncementCardsPageDto;
import dev.ilya_anna.announcement_service.entities.Announcement;
import dev.ilya_anna.announcement_service.models.AnnouncementQuery;
import dev.ilya_anna.announcement_service.repositories.AnnouncementRepository;
import dev.ilya_anna.announcement_service.validators.AnnouncementQueryValidator;
import jakarta.transaction.Transactional;

@Service
public class DaoAnnouncementSearchService implements AnnouncementSearchService {
  @Autowired
  private AnnouncementQueryValidator announcementQueryValidator;

  @Autowired
  private AnnouncementRepository announcementRepository;

  @Transactional
  @Override
  public AnnouncementCardsPageDto searchCards(AnnouncementQuery announcementQuery) {
    BindingResult errors = new BeanPropertyBindingResult(announcementQuery, "announcementQuery");
    announcementQueryValidator.validate(announcementQuery, errors);
    if (errors.hasErrors()) {
      throw new IllegalArgumentException(errors.toString());
    }
    Page<Announcement> announcementsPage = announcementRepository.search(
        announcementQuery.getQuery(), 
        announcementQuery.getCityId(), 
        announcementQuery.getSubcategoryName(), 
        announcementQuery.getCategoryName(),
        PageRequest.of(announcementQuery.getPage(), announcementQuery.getSize()));
    return AnnouncementCardsPageDto.builder()
        .totalElements(announcementsPage.getTotalElements())
        .totalPages(announcementsPage.getTotalPages())
        .page(announcementsPage.getNumber())
        .size(announcementsPage.getSize())
        .announcements(announcementsPage.stream().map(
            announcement -> AnnouncementCardDto.builder()
                .id(announcement.getId())
                .title(announcement.getTitle())
                .cityName(announcement.getCity().getName())
                .cityId(announcement.getCity().getId())
                .subcategoryName(announcement.getSubcategory().getName())
                .categoryName(announcement.getSubcategory().getCategory().getName())
                .build()
        ).toList())
        .build();
  }
}
