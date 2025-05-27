package dev.ilya_anna.announcement_service.services;

import dev.ilya_anna.announcement_service.events.UserCreateEvent;
import dev.ilya_anna.announcement_service.events.UserUpdateEvent;

public interface UserService {
  void create(UserCreateEvent uce);
  void update(UserUpdateEvent upe);
}
