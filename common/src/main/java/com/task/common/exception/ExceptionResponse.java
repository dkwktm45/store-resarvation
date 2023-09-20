package com.task.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;


@Getter
@ToString
@AllArgsConstructor
public class ExceptionResponse{
  private String message;
  private HttpStatus httpStatus;

}
