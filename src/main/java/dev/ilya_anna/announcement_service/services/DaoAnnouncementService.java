package dev.ilya_anna.announcement_service.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import dev.ilya_anna.announcement_service.dto.AnnouncementCreateDto;
import dev.ilya_anna.announcement_service.dto.AnnouncementDto;
import dev.ilya_anna.announcement_service.entities.Announcement;
import dev.ilya_anna.announcement_service.entities.Image;
import dev.ilya_anna.announcement_service.exceptions.AnnouncementAlreadyExistsException;
import dev.ilya_anna.announcement_service.exceptions.UserNotFoundException;
import dev.ilya_anna.announcement_service.repositories.AnnouncementRepository;
import dev.ilya_anna.announcement_service.repositories.UserRepository;
import dev.ilya_anna.announcement_service.security.JwtUserDetails;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
@Validated
public class DaoAnnouncementService implements AnnouncementService {

  @Autowired
  private AnnouncementRepository announcementRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UuidService uuidService;

  @Autowired
  private ImageService imageService;

  @Transactional
  @Override
  public AnnouncementDto create(@Valid AnnouncementCreateDto announcementCreateDto)
      throws AnnouncementAlreadyExistsException {

    String creatorId = ((JwtUserDetails) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal()).getUserId();

    if (!userRepository.existsById(creatorId))
        throw new UserNotFoundException("User with id " + creatorId + " not found"); 

    if (announcementRepository.existsByCreatorIdAndTitle(creatorId, 
        announcementCreateDto.getTitle())) 
      throw new AnnouncementAlreadyExistsException("Announcement already exists");

      
    Announcement announcement = Announcement.builder()
      .id(uuidService.generate())
      .title(announcementCreateDto.getTitle())
      .description(announcementCreateDto.getDescription())
      .address(announcementCreateDto.getAddress())
      .price(announcementCreateDto.getPrice())
      .creator(userRepository.getReferenceById(creatorId))
      .previewImage(null)
      .isActive(announcementCreateDto.getIsActive())
      .city(null)
      .build();
    
    announcement = announcementRepository.save(announcement);
      
    Image previewImage = imageService.uploadImage(announcementCreateDto.getPreviewImageFile());
    announcement.setPreviewImage(previewImage);
    announcementRepository.save(announcement);

    AnnouncementDto announcementDto = AnnouncementDto.builder()
      .id(announcement.getId())
      .title(announcement.getTitle())
      .description(announcement.getDescription())
      .address(announcement.getAddress())
      .price(announcement.getPrice())
      .creatorId(announcement.getCreator().getId())
      .previewImageId(announcement.getPreviewImage().getId())
      .isActive(announcement.getIsActive())
      .build();

    return announcementDto;
    
  }

  @Override
  public void delete(String id) {
    Optional<Announcement> announcementOptional = announcementRepository.findById(id);
    if (announcementOptional.isPresent()) {
      imageService.deleteImage(announcementOptional.get().getPreviewImage().getId());
      announcementRepository.deleteById(announcementOptional.get().getId());
    }
  }

  @Override
  public AnnouncementDto update(String id, @Valid AnnouncementDto announcementDto) {
    
  }

  @Override
  public Integer countAnnouncementsByUser(String userId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'countAnnouncementsByUser'");
  }
  
}
