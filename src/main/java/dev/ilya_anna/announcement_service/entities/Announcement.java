package dev.ilya_anna.announcement_service.entities;

import java.time.ZonedDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "announcements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Announcement {
  @Id
  private String id;
  private String title;
  private String description;
  private String address;
  private Double price;
  private ZonedDateTime createdAt;
  @ManyToOne
  @JoinColumn(name = "creator_id")
  private User creator;
  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "preview_image_id")
  private Image previewImage;
  private Boolean isActive;
  private Integer count;
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "subcategory_id")
  private Subcategory subcategory;
  @ManyToOne
  @JoinColumn(name = "city_name")
  private City city;
}
