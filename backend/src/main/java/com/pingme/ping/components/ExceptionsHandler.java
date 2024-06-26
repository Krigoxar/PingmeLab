package com.pingme.ping.components;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * The ExceptionsHandler class in Java handles various exceptions by providing custom error
 * responses with corresponding HTTP status codes.
 */
@RestControllerAdvice
public class ExceptionsHandler {
  private static final Logger log = LoggerFactory.getLogger(ExceptionsHandler.class);

  /** The ErrorHandling function.F */
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(RuntimeException.class)
  public ErrorResponse handleInternalServerError(RuntimeException ex) {
    String str = String.format("ERROR, 500 CODE %s", ex.getLocalizedMessage());
    log.error(str);
    return new ErrorResponse("500 ERROR, INTERNAL SERVER ERROR");
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({
    HttpClientErrorException.class,
    HttpMessageNotReadableException.class,
    MethodArgumentNotValidException.class,
    MissingServletRequestParameterException.class,
    ConstraintViolationException.class
  })
  public ErrorResponse handleBadRequestException(Exception ex) {
    log.error("ERROR, 400 CODE");
    return new ErrorResponse("400 ERROR, BAD REQUEST");
  }

  @ResponseStatus(HttpStatus.CONFLICT)
  @ExceptionHandler(HttpClientErrorException.Conflict.class)
  public ErrorResponse handleConflictException(Exception ex) {
    log.error("ERROR, 409 CODE");
    return new ErrorResponse("409 ERROR, CONFLICT");
  }

  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ErrorResponse handleMethodNotAllowed(Exception ex) {
    log.error("ERROR, 405 CODE");
    return new ErrorResponse("405 ERROR, METHOD NOT ALLOWED");
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NoHandlerFoundException.class)
  public ErrorResponse handlerFoundException(Exception ex) {
    log.error("ERROR, 404 CODE");
    return new ErrorResponse("404 ERROR, NOT FOUND");
  }
}
