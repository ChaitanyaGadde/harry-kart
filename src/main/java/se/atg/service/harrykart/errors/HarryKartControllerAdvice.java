package se.atg.service.harrykart.errors;

import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HarryKartControllerAdvice {

  @ExceptionHandler(value = HarryKartException.class)
  public ResponseEntity<?> handleException(HarryKartException harryKartException) {
    return new ResponseEntity<>(new ErrorResponse(harryKartException.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @Value
  public static class ErrorResponse {

    String error;
  }
}
