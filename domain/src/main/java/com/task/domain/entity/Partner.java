package com.task.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Partner {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long partnerId;
  private String partnerName;
  @Column(unique = true)
  private String partnerEmail;

  @JsonManagedReference
  @OneToMany(mappedBy = "partner" , cascade = ALL)
  private List<Store> stores;
}
