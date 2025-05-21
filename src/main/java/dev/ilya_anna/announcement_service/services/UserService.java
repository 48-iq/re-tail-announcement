package dev.ilya_anna.announcement_service.services;

import dev.ilya_anna.announcement_service.entities.User;

public interface UserService {
  void create(User user);
  void update(String id, User user);

}
