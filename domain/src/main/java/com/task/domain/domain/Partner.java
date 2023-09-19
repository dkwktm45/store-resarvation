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
public class Partner {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long partnerID;
  private String partnerName;
  private String partnerLocation;
  private String partnerDescription;

  @OneToMany(mappedBy = "partner")
  private List<Store> stores;

  // getters and setters
}
