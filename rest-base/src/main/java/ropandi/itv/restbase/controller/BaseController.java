package ropandi.itv.restbase.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import ropandi.itv.restbase.enumeration.BaseHttpMessages;
import ropandi.itv.restbase.enumeration.IHttpMessages;
import ropandi.itv.restbase.exception.AppException;
import ropandi.itv.restbase.model.ApiResponse;
import ropandi.itv.restbase.model.BaseResponse;

import java.nio.file.AccessDeniedException;
import java.security.SignatureException;
import java.util.Objects;

@Slf4j
public abstract class BaseController {
    protected ResponseEntity<?> responseApi(BaseResponse baseResponse) {
        IHttpMessages httpMessages = baseResponse.getHttpMessages();
        if (Objects.isNull(httpMessages)) {
            log.error("httpMessages is null");
            throw new AppException(BaseHttpMessages.SERVICE_UNAVAILABLE);
        }
        return buildResponseApi(
                ApiResponse.build(
                        baseResponse.getSuccess(),
                        baseResponse.getData(),
                        Objects.isNull(baseResponse.getMessage())?
                        baseResponse.getHttpMessages().getMessage() :
                        baseResponse.getMessage(),
                        httpMessages.getCode(),
                        baseResponse.getErrors()),
                httpMessages.getHttpStatusCode());
    }

    protected ResponseEntity<?> responseApi(boolean success,  IHttpMessages httpMessages) {
        ApiResponse apiResponse =
                success
                        ? ApiResponse.ofOk(httpMessages.getMessage())
                        : ApiResponse.ofError(
                        httpMessages.getMessage(),
                        httpMessages.getCode(),
                        null);
        return buildResponseApi(apiResponse, httpMessages.getHttpStatusCode());
    }

    public ResponseEntity<?> buildResponseApi(ApiResponse data, int httpStatusCode) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(data, headers, getHttpStatus(httpStatusCode));
    }

    private HttpStatus getHttpStatus(int httpStatusCode) {
        HttpStatus httpStatus = HttpStatus.valueOf(httpStatusCode);
        if (Objects.isNull(httpStatus)) {
            throw new IllegalArgumentException("No matching constant for [" + httpStatusCode + "]");
        }
        return httpStatus;
    }

    @ExceptionHandler({Exception.class, RuntimeException.class})
    @ResponseBody
    public ResponseEntity<?> handleException(Exception ex) {
        try {
            if (ex instanceof AppException) {
                AppException appException = (AppException) ex;

                IHttpMessages httpMessages = appException.getHttpMessages();
                if (Objects.isNull(httpMessages)) {
                    log.info("Http Message is null");
                    throw new Exception("Http Message is null");
                }
                return buildResponseApi(
                        ApiResponse.ofError(
                                httpMessages.getMessage(),
                                httpMessages.getCode(),
                                appException.getErrors()),
                        httpMessages.getHttpStatusCode());
            } else if (ex instanceof HttpMessageNotReadableException || ex instanceof HttpMediaTypeNotSupportedException) {
                log.error("Exception Happens. Cause :  ", ex);
                return responseApi(false, BaseHttpMessages.BAD_REQUEST);
            }  else if (ex instanceof SignatureException || ex instanceof AccessDeniedException) {
                log.error("Exception Happens. Cause :  ", ex);
                return responseApi(false, BaseHttpMessages.UNAUTHORIZED);
            } else {
                log.error("Exception Happens. Cause :  ", ex);
                return responseApi(false, BaseHttpMessages.SERVICE_UNAVAILABLE);
            }
        } catch (Exception e) {
            log.error("Exception Happens. Cause :  ", e);
            return responseApi(false, BaseHttpMessages.SERVICE_UNAVAILABLE);
        }
    }
}
