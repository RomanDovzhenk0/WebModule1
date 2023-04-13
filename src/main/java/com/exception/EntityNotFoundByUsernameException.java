package com.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundByUsernameException extends EntityNotFoundException {
   public EntityNotFoundByUsernameException(String message) {
      super(message);
   }
}
