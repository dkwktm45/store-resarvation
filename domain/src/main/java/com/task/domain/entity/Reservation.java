package com.task.domain.entity;

import com.task.domain.type.ResType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

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

  public static Reservation createEntityAll(User user, Store store,
                                        ResType status) {
    return Reservation.builder()
        .user(user)
        .store(store)
        .status(status)
        .store(store)
        .user(user)
        .reservationCode(getRandomCode())
        .reservationTime(getLimitTime())
        .build();
  }
  private static LocalDateTime getLimitTime() {
    return LocalDateTime.now().plusHours(2);
  }

  private static String getRandomCode() {
    return UUID.randomUUID().toString().substring(0, 5);
  }
}