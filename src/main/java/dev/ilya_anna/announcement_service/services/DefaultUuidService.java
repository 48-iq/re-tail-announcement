package dev.ilya_anna.announcement_service.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DefaultUuidService implements UuidService {

  @Value("${app.uuid.seed}")
  private String uuidSeed;

  @Override
  public String generate() {
    return uuidSeed + UUID.randomUUID().toString();
  }
  
}
