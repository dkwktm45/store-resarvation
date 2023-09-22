package com.task.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReservationDto {

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Request{
    private Long userId;
    private String storeName;
    private Integer maxSeat;
    private Integer currentSeat;
  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Response{
    private Long reservationId;
    private String reservationCode;
    private String storeName;
    private String message;
  }
}
