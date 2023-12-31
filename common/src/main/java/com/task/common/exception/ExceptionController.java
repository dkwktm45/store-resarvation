package com.task.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
@Slf4j
public class ExceptionController {

  @ExceptionHandler({
      CustomException.class
  })
  public ResponseEntity<ExceptionResponse> CustomExceptionHandler(final CustomException c){
    log.warn("api exception : {}", c.getErrorCode());
    return ResponseEntity.badRequest().body(
        new ExceptionResponse(c.getMessage(),
            c.getErrorCode().getHttpStatus()));
  };
  @ExceptionHandler({MethodArgumentNotValidException.class})
  public ResponseEntity<ExceptionResponse> notValidException(MethodArgumentNotValidException e) {
    BindingResult bindingResult = e.getBindingResult();
    String detail = "";

    if (bindingResult.hasErrors()) {
      detail = bindingResult.getFieldError().getDefaultMessage();
      log.warn("잘못된 요청이 왔습니다. : {}",detail);

      return ResponseEntity.badRequest().body(
          new ExceptionResponse(detail ,
              ErrorCode.REQUEST_BAD.getHttpStatus()));
    }

    log.warn("잘못된 요청에 대해서 값을 못 찾고 있습니다.");
    return ResponseEntity.badRequest().body(
        new ExceptionResponse(detail ,
            ErrorCode.REQUEST_BAD.getHttpStatus()));
  }

  @ExceptionHandler({MissingRequestHeaderException.class})
  public ResponseEntity<ExceptionResponse> notValidException(MissingRequestHeaderException e) {
    String headerName = e.getHeaderName();
    log.warn("헤더값을 찾지 못하고 있습니다. : {}",headerName);

    return ResponseEntity.badRequest().body(
        new ExceptionResponse(headerName +"을 찾지 못하고 있습니다.",
            ErrorCode.REQUEST_BAD.getHttpStatus()));
  }
}
