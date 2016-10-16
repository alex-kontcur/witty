package com.witty.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * ServerExceptionHandler - spring infrastructure component for handling exceptions
 *
 * @author Alexander Kontsur (bona)
 * @since 28.05.2016
 */
@ControllerAdvice
public class ServerExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ExceptionHandler({DuplicatedMissionIdException.class, SeedRangeNotFoundException.class})
    public ResponseEntity<Object> handleValidationAndViolation(Exception ex, WebRequest request) {
        return buildResponse(ex, HttpStatus.CONFLICT);
    }

    private ResponseEntity buildResponse(Exception ex, HttpStatus status) {
        logger.error("Error", ex);

        StackTraceElement stackTraceElement = ex.getStackTrace()[0];
        String source = "Source: " + stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName();
        ErrorMessage errorMessage = new ErrorMessage(source, ex.getMessage());
        return new ResponseEntity(errorMessage, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        logger.error("Error", ex);

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
        List<String> errors = new ArrayList<>(fieldErrors.size() + globalErrors.size());
        String error;
        for (FieldError fieldError : fieldErrors) {
            error = "Field [" + fieldError.getField() + "] Issue: " + fieldError.getDefaultMessage();
            errors.add(error);
        }
        for (ObjectError objectError : globalErrors) {
            error = "Object [" + objectError.getObjectName() + "] Issue: " + objectError.getDefaultMessage();
            errors.add(error);
        }

        ErrorMessage errorMessage = new ErrorMessage(errors);
        return new ResponseEntity(errorMessage, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers,
                                                                     HttpStatus status, WebRequest request) {
        logger.error("Error", ex);

        String unsupported = "Unsupported content type: " + ex.getContentType();
        String supported = "Supported content types: " + MediaType.toString(ex.getSupportedMediaTypes());
        ErrorMessage errorMessage = new ErrorMessage(unsupported, supported);
        return new ResponseEntity(errorMessage, headers, status);
    }

}
