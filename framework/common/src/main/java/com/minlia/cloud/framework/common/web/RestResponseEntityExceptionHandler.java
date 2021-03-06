/**
 * Copyright (C) 2004-2015 http://oss.minlia.com/license/framework/2015
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
//package com.minlia.cloud.framework.common.web;
//
//import com.minlia.cloud.framework.common.persistence.exception.MyEntityNotFoundException;
//import com.minlia.cloud.framework.common.web.exception.ApiError;
//import com.minlia.cloud.framework.common.web.exception.MyConflictException;
//import com.minlia.cloud.framework.common.web.exception.MyResourceNotFoundException;
//import com.minlia.cloud.framework.common.web.exception.ValidationErrorDTO;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.dao.DataAccessException;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.dao.InvalidDataAccessApiUsageException;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.converter.HttpMessageNotReadableException;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//import javax.persistence.EntityNotFoundException;
//import javax.validation.ConstraintViolationException;
//import java.util.List;
//
//@ControllerAdvice
//public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
//    private Logger log = LoggerFactory.getLogger(getClass());
//
//    public RestResponseEntityExceptionHandler() {
//        super();
//    }
//
//    // API
//
//    // 400
//
//    @Override
//    protected final ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
//        log.info("Bad Request: {}", ex.getMessage());
//        log.debug("Bad Request: ", ex);
//
//        final ApiError apiError = message(HttpStatus.BAD_REQUEST, ex);
//        return handleExceptionInternal(ex, apiError, headers, HttpStatus.BAD_REQUEST, request);
//    }
//
//    @Override
//    protected final ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
//        log.info("Bad Request: {}", ex.getMessage());
//        log.debug("Bad Request: ", ex);
//
//        final BindingResult result = ex.getBindingResult();
//        final List<FieldError> fieldErrors = result.getFieldErrors();
//        final ValidationErrorDTO dto = processFieldErrors(fieldErrors);
//
//        return handleExceptionInternal(ex, dto, headers, HttpStatus.BAD_REQUEST, request);
//    }
//
//    @ExceptionHandler(value = { ConstraintViolationException.class, DataIntegrityViolationException.class, IllegalArgumentException.class })
//    public final ResponseEntity<Object> handleBadRequest(final RuntimeException ex, final WebRequest request) {
//        log.info("Bad Request: {}", ex.getLocalizedMessage());
//        log.debug("Bad Request: ", ex);
//
//        final ApiError apiError = message(HttpStatus.BAD_REQUEST, ex);
//        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
//    }
//
//    // 404
//
//    @ExceptionHandler({ EntityNotFoundException.class, MyEntityNotFoundException.class, MyResourceNotFoundException.class })
//    protected ResponseEntity<Object> handleNotFound(final MyEntityNotFoundException ex, final WebRequest request) {
//        log.warn("Not Found: {}", ex.getMessage());
//
//        final ApiError apiError = message(HttpStatus.NOT_FOUND, ex);
//        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
//    }
//
//    // 409
//
//    @ExceptionHandler({ InvalidDataAccessApiUsageException.class, DataAccessException.class, MyConflictException.class })
//    protected ResponseEntity<Object> handleConflict(final RuntimeException ex, final WebRequest request) {
//        log.warn("Conflict: {}", ex.getMessage());
//
//        final ApiError apiError = message(HttpStatus.CONFLICT, ex);
//        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.CONFLICT, request);
//    }
//
//    // 500
//
//    @ExceptionHandler({ NullPointerException.class, IllegalArgumentException.class, IllegalStateException.class })
//    public ResponseEntity<Object> handleInternal(final RuntimeException ex, final WebRequest request) {
//        logger.error("500 Status Code", ex);
//
//        final ApiError apiError = message(HttpStatus.INTERNAL_SERVER_ERROR, ex);
//
//        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
//    }
//
//    // UTIL
//
//    private ValidationErrorDTO processFieldErrors(final List<FieldError> fieldErrors) {
//        final ValidationErrorDTO dto = new ValidationErrorDTO();
//
//        for (final FieldError fieldError : fieldErrors) {
//            final String localizedErrorMessage = fieldError.getDefaultMessage();
//            dto.addFieldError(fieldError.getField(), localizedErrorMessage);
//        }
//
//        return dto;
//    }
//
//    private ApiError message(final HttpStatus httpStatus, final Exception ex) {
//        final String message = ex.getMessage() == null ? ex.getClass().getSimpleName() : ex.getMessage();
//        final String devMessage = ex.getClass().getSimpleName();
//        // devMessage = ExceptionUtils.getStackTrace(ex);
//
//        return new ApiError(httpStatus.value(), message, devMessage);
//    }
//
//}
