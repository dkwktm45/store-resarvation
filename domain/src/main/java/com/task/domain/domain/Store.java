package com.task.domain.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Store {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long storeID;
  private String storeName;
  private String storeLocation;
  private String storeDescription;
  private Integer totalSeats;
  private Integer availableSeats;
  private Integer reviewCount;

  @ManyToOne
  @JoinColumn(name = "partnerID")
  private Partner partner;

  @OneToMany(mappedBy = "store")
  private List<Reservation> reservations;

  @OneToMany(mappedBy = "store")
  private List<Review> reviews;

}