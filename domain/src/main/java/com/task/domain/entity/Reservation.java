package com.task.domain.entity;

import com.task.domain.type.ResType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.task.domain.type.ResType.WAITING;

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

  @ColumnDefault("0")
  private boolean reservationCheck;


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
    return LocalDateTime.now().plusMinutes(2);
  }

  private static String getRandomCode() {
    return UUID.randomUUID().toString().substring(0, 5);
  }

  public void changeWaitingSuccess() {
    this.status = WAITING;
  }

  public void changeKiosk() {
    this.reservationCheck = true;
  }
}