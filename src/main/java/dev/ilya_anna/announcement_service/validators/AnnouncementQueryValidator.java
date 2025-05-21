package dev.ilya_anna.announcement_service.validators;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import dev.ilya_anna.announcement_service.models.AnnouncementQuery;

@Component
public class AnnouncementQueryValidator implements Validator {

  @Override
  public boolean supports(@NonNull Class<?> clazz) {
    return clazz.equals(AnnouncementQuery.class);
  }

  @Override
  public void validate(Object target, Errors errors) {
    AnnouncementQuery announcementQuery = (AnnouncementQuery) target;
    String subcategoryName = announcementQuery.getSubcategoryName();
    String categoryName = announcementQuery.getCategoryName();
    Integer page = announcementQuery.getPage();
    Integer size = announcementQuery.getSize();

    if (size == null && page != null || page == null && size != null) {
      errors.rejectValue("page", "Page and size must be specified together");
    }

    if (size != null && size < 1) {
      errors.rejectValue("size", "Size must be greater than 0");
    }
    
    if (page != null && page < 0) {
      errors.rejectValue("page", "Page must be greater than 0");
    }

    if (categoryName != null && subcategoryName != null) {
      errors.rejectValue("categoryName", "Category and subcategory must not be specified together");
    } 
  }
  
}
