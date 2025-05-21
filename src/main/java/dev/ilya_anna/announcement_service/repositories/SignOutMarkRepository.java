package dev.ilya_anna.announcement_service.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dev.ilya_anna.announcement_service.entities.SignOutMark;

@Repository
public interface SignOutMarkRepository extends CrudRepository<SignOutMark, String> {
}

