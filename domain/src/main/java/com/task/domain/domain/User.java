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
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userID;
  private String username;
  private String password;
  private String userType;

  @OneToMany(mappedBy = "user")
  private List<Reservation> reservations;

  @OneToMany(mappedBy = "user")
  private List<Review> reviews;

  // getters and setters
}
