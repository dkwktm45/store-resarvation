package com.task.domain.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long reservationID;

  @ManyToOne
  @JoinColumn(name = "userID")
  private User user;

  @ManyToOne
  @JoinColumn(name = "storeID")
  private Store store;

  private Date reservationTime;
  private String status;
  private Integer reservedSeats;

  @OneToOne(mappedBy = "reservation")
  private Visit visit;

  // getters and setters
}