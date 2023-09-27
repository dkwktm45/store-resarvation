package com.task.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

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
  private Long storeId;
  @Column(unique = true)
  private String storeName;
  private String storeLocation;
  private String storeDescription;
  private Integer totalSeats;
  @ColumnDefault("0")
  private Integer availableSeats;

  @OneToMany(mappedBy = "store")
  private List<Reservation> reservations;

  @OneToMany(mappedBy = "store")
  private List<Review> reviews;

  @ManyToOne(fetch = FetchType.LAZY)
  @JsonBackReference
  @JoinColumn(name = "partnerId")
  private Partner partner;


  public void increaseUser() {
    this.availableSeats++;
  }

}