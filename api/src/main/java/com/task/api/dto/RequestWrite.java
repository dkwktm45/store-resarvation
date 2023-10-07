package com.task.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestWrite {
  @NotNull(message = "빈 값은 허용하지 않습니다.")
  Long reservationId;
  @Min(0) @Max(5)
  Double rating;
  @NotBlank(message = "빈 값은 허용하지 않습니다.")
  String message;
  @NotBlank(message = "빈 값은 허용하지 않습니다.")
  String nickName;
  @NotBlank(message = "빈 값은 허용하지 않습니다.")
  String storeName;
}
