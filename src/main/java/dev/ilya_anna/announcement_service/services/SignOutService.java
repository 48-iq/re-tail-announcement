package dev.ilya_anna.announcement_service.services;


import java.time.LocalDateTime;
import dev.ilya_anna.announcement_service.exceptions.SignOutMarkValidationException;
public interface SignOutService {
    /**
     * Validates user query, if this user has already signed out then throws exception
     * @param userId
     * @param time
     * @throws SignOutMarkValidationException
     */
    void validateSignOutMark(String userId, LocalDateTime time) throws SignOutMarkValidationException;
}
