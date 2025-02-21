package it.gestione.eventi.exceptions;

import it.gestione.eventi.auth.JwtTokenMissingException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class ExceptionHandlerClass extends ResponseEntityExceptionHandler {
	@ExceptionHandler (value = EntityNotFoundException.class)
	protected ResponseEntity<String> entityNotFound (EntityNotFoundException ex) {
		Error error = new Error();
		error.setMessage("Entity not found");
		error.setDetails(ex.getMessage());
		error.setStatus("404");
		return new ResponseEntity<>(error.toString(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler (value = EntityExistsException.class)
	protected ResponseEntity<Error> alreadyExistsException (EntityExistsException ex) {
		Error error = new Error();
		error.setMessage("Entity already exists");
		error.setDetails(ex.getMessage());
		error.setStatus("409");
		return new ResponseEntity<>(error, HttpStatus.CONFLICT);
	}

	@ExceptionHandler (value = AccessDeniedException.class)
	protected ResponseEntity<Error> accessDenied (AccessDeniedException ex) {
		Error error = new Error();
		error.setMessage("No Authorized");
		error.setDetails(ex.getMessage());
		error.setStatus("401");
		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
	}


	@ExceptionHandler (ConstraintViolationException.class)
	public ResponseEntity<Map<String, String>> handleConstraintViolationException (ConstraintViolationException ex) {
		Map<String, String> errors = new HashMap<>();
		for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			String fieldName = violation.getPropertyPath().toString();
			if (fieldName.contains(".")) {
				fieldName = fieldName.substring(fieldName.lastIndexOf('.') + 1);
			}
			errors.put(fieldName, violation.getMessage());

		}
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler (value = JwtTokenMissingException.class)
	protected ResponseEntity<String> JwtTokenMissingException (JwtTokenMissingException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
	}

}
