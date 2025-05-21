package dev.ilya_anna.announcement_service.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import dev.ilya_anna.announcement_service.entities.SignOutMark;
import dev.ilya_anna.announcement_service.repositories.SignOutMarkRepository;
import dev.ilya_anna.announcement_service.exceptions.SignOutMarkValidationException;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class DefaultSignOutService implements SignOutService{

    @Autowired
    private SignOutMarkRepository signOutMarkRepository;

    @Value("${app.jwt.access.duration}")
    private Long accessDuration;

    /**
     * Validates sign out mark for given user id and time. If mark exists and it's sign out time is after given time
     * then IllegalArgumentException is thrown with message "authentication expired"
     *
     * @param userId user id
     * @param time   time to compare with sign out time
     * @throws IllegalArgumentException if authentication expired
     */
    @Override
    public void validateSignOutMark(String userId, LocalDateTime time) throws SignOutMarkValidationException {
        Optional<SignOutMark> signOutMarkOptional = signOutMarkRepository.findById(userId);
        if (signOutMarkOptional.isPresent()) {

            SignOutMark signOutMark = signOutMarkOptional.get();
            LocalDateTime signOutTime = signOutMark.getSignOutTime();
            if (signOutTime.isAfter(time)) {
                throw new SignOutMarkValidationException("authentication expired");
            }
        }
    }
}
