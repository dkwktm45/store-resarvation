package com.task.domain.entity;

import com.task.domain.type.ResType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long reservationId;

  @ManyToOne
  @JoinColumn(name = "userId")
  private User user;

  @ManyToOne
  @JoinColumn(name = "storeId")
  private Store store;

  private LocalDateTime reservationTime;

  @Enumerated(value = EnumType.STRING)
  private ResType status;

  private String reservationCode;
}