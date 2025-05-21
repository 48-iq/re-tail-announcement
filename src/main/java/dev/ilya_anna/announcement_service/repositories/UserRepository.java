package dev.ilya_anna.announcement_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.ilya_anna.announcement_service.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
