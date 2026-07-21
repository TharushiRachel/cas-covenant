package lk.sampath.cas_covenant.exception;

import java.time.ZonedDateTime;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends Exception {

  private final String message;
  private final HttpStatus httpStatus;
  private final ZonedDateTime timestamp;

  public ApiException(String message, HttpStatus httpStatus, ZonedDateTime timestamp) {
    this.message = message;
    this.httpStatus = httpStatus;
    this.timestamp = timestamp;
  }
}
