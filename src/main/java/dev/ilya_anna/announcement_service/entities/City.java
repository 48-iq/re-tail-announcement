package dev.ilya_anna.announcement_service.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cities")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class City {
  @Id
  private String id;
  private String name;
}
