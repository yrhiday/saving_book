package com.yusufrh.saving_book.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.yusufrh.saving_book.response.BaseResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
 @ExceptionHandler(DuplicateEntryException.class)
    public ResponseEntity<BaseResponse> handleDuplicateEntryException(DuplicateEntryException ex) {
        BaseResponse<Object> baseResponse = new BaseResponse<>(1,ex.getMessage(), null);
        return new ResponseEntity<>(baseResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DataNotFoundException.class) 
    public ResponseEntity<BaseResponse> handleDataNotFoundException(DataNotFoundException ex) { 
        BaseResponse<Object> baseResponse = new BaseResponse<>(1,ex.getMessage(), null);
        return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class) 
    public ResponseEntity<BaseResponse> handleIllegalArgumentException(IllegalArgumentException ex) { 
        BaseResponse<Object> baseResponse = new BaseResponse<>(1,ex.getMessage(), null);
        return new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);
    } 
    
    @ExceptionHandler(MethodArgumentNotValidException.class) 
    public ResponseEntity<BaseResponse> handleValidationExceptions(MethodArgumentNotValidException ex) { 
        BaseResponse<Object> baseResponse = new BaseResponse<>(1,"Validation error", null);
        return new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);
    } 

    @ExceptionHandler(MissingServletRequestParameterException.class) 
    public ResponseEntity<BaseResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) { 
        BaseResponse<Object> baseResponse = new BaseResponse<>(1,"Required request parameter '" + ex.getParameterName() + "' is missing.", null);
        return new ResponseEntity<>(baseResponse, HttpStatus.NOT_ACCEPTABLE);
    }
    
    @ExceptionHandler(MethodArgumentTypeMismatchException.class) 
    public ResponseEntity<BaseResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        BaseResponse<Object> baseResponse = new BaseResponse<>(1,"Argument mistmatch.", null);
        return new ResponseEntity<>(baseResponse, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(ConstraintViolationException.class) 
    public ResponseEntity<BaseResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        BaseResponse<Object> baseResponse = new BaseResponse<>(1,"Invalid request parameter type.", null);
        return new ResponseEntity<>(baseResponse, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(ResourceNotFoundException.class) 
    public ResponseEntity<BaseResponse> handleResourceNotFoundException(ResourceNotFoundException ex) { 
        BaseResponse<Object> baseResponse = new BaseResponse<>(1, ex.getMessage(), null); 
        return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND); 
    } 
    
    @ExceptionHandler(NoHandlerFoundException.class) 
    public ResponseEntity<BaseResponse> handleNoHandlerFoundException(NoHandlerFoundException ex) { 
        BaseResponse<Object> baseResponse = new BaseResponse<>(1, "Resource not found", null); 
        return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND); 
    }

    @ExceptionHandler(Exception.class) 
    public ResponseEntity<BaseResponse> handleGeneralException(Exception ex) { 
        BaseResponse<Object> baseResponse = new BaseResponse<>(1,"An unexpected error occurred. Please try again later.", null);
        return new ResponseEntity<>(baseResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
