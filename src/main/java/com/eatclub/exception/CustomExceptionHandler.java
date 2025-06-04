package com.eatclub.exception;


import com.eatclub.model.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<ErrorDetails> handleBadRequestException(Exception e) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMsg("BadRequestException : "+e.getMessage());
        errorDetails.setStatus(HttpStatus.BAD_REQUEST.toString());
        return new ResponseEntity(errorDetails , HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler({ServiceException.class})
    public ResponseEntity<ErrorDetails> handleServiceException(Exception e) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMsg("ServiceException : "+e.getMessage());
        errorDetails.setStatus(HttpStatus.SERVICE_UNAVAILABLE.toString());
        return new ResponseEntity(errorDetails , HttpStatus.SERVICE_UNAVAILABLE);

    }

    @ExceptionHandler({ApplicationException.class})
    public ResponseEntity<ErrorDetails> handleApplicationException(Exception e) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMsg("ApplicationException : "+e.getMessage());
        errorDetails.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        return new ResponseEntity(errorDetails , HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
