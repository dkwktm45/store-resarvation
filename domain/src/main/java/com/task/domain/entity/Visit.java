package com.task.domain.entity;

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
public class Visit {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long visitID;

  @OneToOne
  @JoinColumn(name = "reservationID")
  private Reservation reservation;

  private Date checkInTime;
  private Date checkOutTime;

  // getters and setters
}
