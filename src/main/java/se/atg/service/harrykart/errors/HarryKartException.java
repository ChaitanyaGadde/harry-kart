package se.atg.service.harrykart.errors;

import org.springframework.http.HttpStatus;

public class HarryKartException extends RuntimeException {

  private final HttpStatus status;
  private final String code;
  private final String reason;

  public HarryKartException(HttpStatus status, String code, String reason, String message, Throwable cause) {
    super(message, cause);
    this.status = status;
    this.code = code;
    this.reason = reason;
  }

  public HarryKartException(HttpStatus status, String code, String reason, String message) {
    super(message);
    this.status = status;
    this.code = code;
    this.reason = reason;
  }

  public HarryKartException(HttpStatus status, String reason, String message) {
    this(status, status.name(), reason, message);
  }

  public HarryKartException(HttpStatus status, String message) {
    this(status, status.name(), status.getReasonPhrase(), message);
  }

  public HarryKartException(HttpStatus status, String message, Throwable cause) {
    this(status, status.name(), status.getReasonPhrase(), message, cause);
  }

  public HttpStatus getStatus() {
    return this.status;
  }

  public String getCode() {
    return this.code;
  }

  public String getReason() {
    return this.reason;
  }
}
