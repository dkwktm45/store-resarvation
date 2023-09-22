package com.task.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long reviewID;

  @ManyToOne
  @JoinColumn(name = "userId")
  private User user;

  @ManyToOne
  @JoinColumn(name = "storeId")
  private Store store;

  private Double rating;
  private String comment;
}