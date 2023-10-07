package com.task.api.dto;

import com.task.domain.type.ResType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class ReservationDto {

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Request{
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식이 올바르지 않습니다.")
    @NotBlank(message = "이메일은 필수로 입력 값입니다.")
    private String partnerEmail;
    @NotBlank(message = "빈 값은 허용하지 않습니다.")
    private String storeName;
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
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class ResponseEntity{
    private Long reservationId;
    private String email;
    private ResType status;
  }
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class ResponseReview{
    private Long reservationId;
    private String storeName;
  }

}
