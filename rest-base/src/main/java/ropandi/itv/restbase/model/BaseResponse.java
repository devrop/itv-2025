package ropandi.itv.restbase.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ropandi.itv.restbase.enumeration.IHttpMessages;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 5912852901129060936L;
    private Boolean success;
    private T data;
    private String message;
    private IHttpMessages httpMessages;
    private List<String> errors;

    public static BaseResponse<String> build(
            Boolean success, String message, IHttpMessages httpMessages) {
        BaseResponse<String> baseResponse = new BaseResponse<>();
        baseResponse.setSuccess(success);
        baseResponse.setMessage(message);
        baseResponse.setHttpMessages(httpMessages);
        return baseResponse;
    }

    public static <T> BaseResponse<T> buildWithData(
            Boolean success, T data, IHttpMessages httpMessages) {
        BaseResponse<T> baseResponse = new BaseResponse<>();
        baseResponse.setSuccess(success);
        baseResponse.setHttpMessages(httpMessages);
        baseResponse.setMessage(httpMessages.getMessage());
        baseResponse.setData(data);
        return baseResponse;
    }

    public static BaseResponse<String> ok(IHttpMessages httpMessages) {
        BaseResponse<String> baseResponse = new BaseResponse<>();
        baseResponse.setSuccess(true);
        baseResponse.setMessage(httpMessages.getMessage());
        baseResponse.setHttpMessages(httpMessages);
        return baseResponse;
    }
    public static BaseResponse<String> ok(String message, IHttpMessages httpMessages) {
        BaseResponse<String> baseResponse = new BaseResponse<>();
        baseResponse.setSuccess(true);
        baseResponse.setMessage(message);
        baseResponse.setHttpMessages(httpMessages);
        return baseResponse;
    }

    public static BaseResponse<String> error(
            String message, IHttpMessages httpMessages, List<String> errors) {
        BaseResponse<String> baseResponse = new BaseResponse<>();
        baseResponse.setSuccess(false);
        baseResponse.setMessage(Objects.isNull(message)? httpMessages.getMessage() : message);
        baseResponse.setHttpMessages(httpMessages);
        baseResponse.setErrors(errors);
        return baseResponse;
    }
}