package pl.petlovers.Pet2Vet.exceptions.unautorized_exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public abstract class UnauthorizedAttemptException extends RuntimeException {
  protected UnauthorizedAttemptException(String message) {
    super(message);
    log.error("---Error: " + message);
  }
}


