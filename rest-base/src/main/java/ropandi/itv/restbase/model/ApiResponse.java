package ropandi.itv.restbase.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> implements Serializable {
    @Serial private static final long serialVersionUID = 5912852901129060936L;
    public static final Integer OK_RESULT = 1;
    public static final Integer ERROR_RESULT = 0;
    private Boolean success;
    private T contents;
    private String message;
    private String code;
    private List<String> errors;

    public static <T> ApiResponse<T> build(
            boolean success, T data, String message, String code, List<String> errors) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(success);
        apiResponse.setContents(data);
        apiResponse.setMessage(message);
        apiResponse.setCode(code);
        apiResponse.setErrors(errors);
        return apiResponse;
    }
    public static ApiResponse<String> ofOk(String message) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setMessage(message);
        return apiResponse;
    }

    public static ApiResponse<String> ofError(
            String message, String code, List<String> errors) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(false);
        apiResponse.setMessage(message);
        apiResponse.setCode(code);
        apiResponse.setErrors(errors);
        return apiResponse;
    }
}
