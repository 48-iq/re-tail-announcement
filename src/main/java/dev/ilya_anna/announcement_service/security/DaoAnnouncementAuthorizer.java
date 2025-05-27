package dev.ilya_anna.announcement_service.security;

import java.util.Map;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import dev.ilya_anna.announcement_service.entities.Announcement;
import dev.ilya_anna.announcement_service.exceptions.AnnouncementNotFoundException;
import dev.ilya_anna.announcement_service.repositories.AnnouncementRepository;

@Component
public class DaoAnnouncementAuthorizer implements AuthorizationManager<RequestAuthorizationContext> {

  @Autowired
  private AnnouncementRepository announcementRepository;

  @Override
  public AuthorizationDecision check(Supplier<Authentication> authenticationSupplier, 
      RequestAuthorizationContext requestAuthorizationContext) {
    Authentication authentication = authenticationSupplier.get();
    JwtUserDetails jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();
    Map<String, String> variables = requestAuthorizationContext.getVariables();
    String announcementId = variables.get("id");
    String userId = jwtUserDetails.getUserId();
    Announcement announcement = announcementRepository.findById(announcementId)
        .orElseThrow(() -> new AnnouncementNotFoundException("announcement with id: " + announcementId + " not found"));
    return new AuthorizationDecision(announcement.getCreator().getId().equals(userId));
    
  }
  
}
