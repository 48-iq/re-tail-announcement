package dev.ilya_anna.announcement_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.ilya_anna.announcement_service.entities.User;
import dev.ilya_anna.announcement_service.events.UserCreateEvent;
import dev.ilya_anna.announcement_service.events.UserUpdateEvent;
import dev.ilya_anna.announcement_service.exceptions.UserAlreadyExistsException;
import dev.ilya_anna.announcement_service.exceptions.UserNotFoundException;
import dev.ilya_anna.announcement_service.repositories.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class DaoUserService implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public void create(UserCreateEvent uce) {
        if (userRepository.existsById(uce.getId()))
            throw new UserAlreadyExistsException();
        User user = User.builder()
            .id(uce.getId())
            .nickname(uce.getNickname())
            .build();
        
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void update(UserUpdateEvent upe) {
        User user = userRepository.findById(upe.getId())
            .orElseThrow(() -> new UserNotFoundException("user with id: " + upe.getId() + " not found"));
        if (!user.getNickname().equals(upe.getNickname())) {
            user.setNickname(upe.getNickname());
            userRepository.save(user);
        }
    }
    
}
