package dev.ilya_anna.announcement_service.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import dev.ilya_anna.announcement_service.dto.AnnouncementCreateDto;
import dev.ilya_anna.announcement_service.dto.AnnouncementDto;
import dev.ilya_anna.announcement_service.dto.AnnouncementUpdateDto;
import dev.ilya_anna.announcement_service.entities.Announcement;
import dev.ilya_anna.announcement_service.entities.City;
import dev.ilya_anna.announcement_service.entities.Image;
import dev.ilya_anna.announcement_service.exceptions.AnnouncementAlreadyExistsException;
import dev.ilya_anna.announcement_service.exceptions.AnnouncementNotFoundException;
import dev.ilya_anna.announcement_service.exceptions.UserNotFoundException;
import dev.ilya_anna.announcement_service.repositories.AnnouncementRepository;
import dev.ilya_anna.announcement_service.repositories.CityRepository;
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

  @Autowired
  private CityRepository cityRepository;

  @Transactional
  @Override
  public AnnouncementDto createAnnouncement(@Valid AnnouncementCreateDto announcementCreateDto)
      throws AnnouncementAlreadyExistsException {

    String creatorId = ((JwtUserDetails) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal()).getUserId();

    if (!userRepository.existsById(creatorId))
        throw new UserNotFoundException("User with id " + creatorId + " not found"); 

    if (announcementRepository.existsByCreatorIdAndTitle(creatorId, 
        announcementCreateDto.getTitle())) 
      throw new AnnouncementAlreadyExistsException("Announcement already exists");
      
    if (!cityRepository.existsById(announcementCreateDto.getCityId()))
        throw new IllegalArgumentException("City with id: " + announcementCreateDto.getCityId() + " not exists");
    
    City city = cityRepository.getReferenceById(announcementCreateDto.getCityId());

    Announcement announcement = Announcement.builder()
      .id(uuidService.generate())
      .title(announcementCreateDto.getTitle())
      .description(announcementCreateDto.getDescription())
      .address(announcementCreateDto.getAddress())
      .price(announcementCreateDto.getPrice())
      .creator(userRepository.getReferenceById(creatorId))
      .previewImage(null)
      .isActive(announcementCreateDto.getIsActive())
      .city(city)
      .build();
    
    announcement = announcementRepository.save(announcement);
      
    Image previewImage = imageService.uploadImage(announcementCreateDto.getPreviewImageFile());
    announcement.setPreviewImage(previewImage);

    List<Image> images = new ArrayList<>();
    for (MultipartFile imageFile: announcementCreateDto.getImageFiles()) {
      Image image = imageService.uploadImage(imageFile);
      images.add(image);
    }

    announcement.setImages(images);

    announcementRepository.save(announcement);

    AnnouncementDto announcementDto = AnnouncementDto.builder()
      .id(announcement.getId())
      .title(announcement.getTitle())
      .description(announcement.getDescription())
      .address(announcement.getAddress())
      .price(announcement.getPrice())
      .creatorId(announcement.getCreator().getId())
      .previewImageId(announcement.getPreviewImage().getId())
      .cityId(announcement.getCity().getId())
      .cityName(announcement.getCity().getName())
      .categoryId(announcement.getSubcategory().getCategory().getId())
      .categoryName(announcement.getSubcategory().getCategory().getName())
      .subcategoryId(announcement.getSubcategory().getId())
      .subcategoryName(announcement.getSubcategory().getName())
      .imageIds(announcement.getImages().stream().map(image -> image.getId()).toList())
      .isActive(announcement.getIsActive())
      .build();
    return announcementDto;    
  }

  @Override
  public void deleteAnnouncementById(String id) {
    Optional<Announcement> announcementOptional = announcementRepository.findById(id);
    if (announcementOptional.isPresent()) {
      imageService.deleteImage(announcementOptional.get().getPreviewImage().getId());
      announcementRepository.deleteById(announcementOptional.get().getId());
    }
  }

  @Override
  public AnnouncementDto updateAnnouncementById(String id, @Valid AnnouncementUpdateDto announcementUpdateDto) {
    
    Announcement announcement = announcementRepository.findById(id).orElseThrow(
      () -> new AnnouncementNotFoundException("Announcement with id " + id + " not found"));

    if (!cityRepository.existsById(announcementUpdateDto.getCityId()))
      throw new IllegalArgumentException("City with id: " + announcementUpdateDto.getCityId() + " not exists");

    announcement.setDescription(announcementUpdateDto.getDescription());
    announcement.setAddress(announcementUpdateDto.getAddress());
    announcement.setPrice(announcementUpdateDto.getPrice());
    announcement.setIsActive(announcementUpdateDto.getIsActive());
    announcement.setCity(cityRepository.getReferenceById(announcementUpdateDto.getCityId()));
    announcement = announcementRepository.save(announcement);
    
  return AnnouncementDto.builder()
      .id(announcement.getId())
      .title(announcement.getTitle())
      .description(announcement.getDescription())
      .address(announcement.getAddress())
      .price(announcement.getPrice())
      .creatorId(announcement.getCreator().getId())
      .previewImageId(announcement.getPreviewImage().getId())
      .cityId(announcement.getCity().getId())
      .cityName(announcement.getCity().getName())
      .categoryId(announcement.getSubcategory().getCategory().getId())
      .categoryName(announcement.getSubcategory().getCategory().getName())
      .subcategoryId(announcement.getSubcategory().getId())
      .subcategoryName(announcement.getSubcategory().getName())
      .imageIds(announcement.getImages().stream().map(image -> image.getId()).toList())
      .isActive(announcement.getIsActive())
      .build();
  }

  @Override
  public Integer countAnnouncementsByUser(String userId) {
    return announcementRepository.countByCreatorId(userId);
  }

  @Override
  public AnnouncementDto getAnnouncementById(String id) {
    return announcementRepository.findById(id)
      .map(announcement -> AnnouncementDto.builder()
        .id(announcement.getId())
        .title(announcement.getTitle())
        .description(announcement.getDescription())
        .address(announcement.getAddress())
        .price(announcement.getPrice())
        .creatorId(announcement.getCreator().getId())
        .previewImageId(announcement.getPreviewImage().getId())
        .cityId(announcement.getCity().getId())
        .cityName(announcement.getCity().getName())
        .categoryId(announcement.getSubcategory().getCategory().getId())
        .categoryName(announcement.getSubcategory().getCategory().getName())
        .subcategoryId(announcement.getSubcategory().getId())
        .subcategoryName(announcement.getSubcategory().getName())
        .imageIds(announcement.getImages().stream().map(image -> image.getId()).toList())
        .isActive(announcement.getIsActive())
        .build())
      .orElseThrow(() -> new AnnouncementNotFoundException("Announcement with id: " + id + " not found"));
  }
  
}
