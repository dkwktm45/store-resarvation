package com.task.domain.entity;

import com.task.domain.type.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String userName;

  @Column(nullable = false)
  private Boolean valid;

  private LocalDateTime verifyExpiredAt;

  private String verificationCode;

  @Column(nullable = false)
  private String password;

  @Enumerated(value = EnumType.STRING)
  private UserType userType;

  @OneToMany(mappedBy = "user")
  private List<Reservation> reservations;

  @OneToMany(mappedBy = "user")
  private List<Review> reviews;

  public void createValid(String code, LocalDateTime date) {
    this.valid = false;
    this.verificationCode = code;
    this.verifyExpiredAt = date.plusDays(1);
  }

  public void changeValidUser() {
    this.valid = true;
  }
}
