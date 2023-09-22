package com.task.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreDto {
  @NotBlank(message = "상호명은 필수 입니다.")
  private String storeName;
  @NotBlank(message = "위치내용은 필수 입니다.")
  private String storeLocation;
  @NotBlank(message = "부가내용은 필수 입니다.")
  private String storeDescription;
  @Min(1)
  private Integer totalSeats;
  @Min(0)
  private Integer availableSeats;
  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Detail {
    private String storeName;
    private String storeLocation;
    private String storeDescription;
    private Integer totalSeats;
    private Integer availableSeats;
    private Integer reviewSize;
    private Integer reservationsSize;
  }
}
