package ropandi.itv.restbase.service;

import ropandi.itv.restbase.enumeration.BaseHttpMessages;
import ropandi.itv.restbase.enumeration.IHttpMessages;
import ropandi.itv.restbase.model.BaseResponse;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseService {

    protected BaseResponse responseOk() {
        return BaseResponse.ok(BaseHttpMessages.OK);
    }

    protected <T> BaseResponse<T> responseOk(T data) {
        return BaseResponse.buildWithData(true, data, BaseHttpMessages.OK);
    }

    protected BaseResponse responseErrorFailedSaveData() {
        return BaseResponse.error(null, BaseHttpMessages.FAILED_SAVED_DATA, null);
    }

    protected BaseResponse responseErrorFailedBadRequest() {
        return BaseResponse.error(null, BaseHttpMessages.BAD_REQUEST, null);
    }

    protected BaseResponse responseErrorFailedBadRequest(String message, String... args) {
        List<String> errors = new ArrayList<>();
        for (String error : args) {
            errors.add(error);
        }
        return BaseResponse.error(message, BaseHttpMessages.BAD_REQUEST, errors);
    }

    protected <T> BaseResponse<T> responseData(boolean success, T data, IHttpMessages httpMessages) {
        return BaseResponse.buildWithData(success, data, httpMessages);
    }

    protected BaseResponse responseData(boolean success, IHttpMessages httpMessages) {
        return success ? BaseResponse.ok(httpMessages) : BaseResponse.error(null, httpMessages, null);
    }
    protected BaseResponse responseData(boolean success,String message, IHttpMessages httpMessages) {
        return success ? BaseResponse.ok(httpMessages) : BaseResponse.error(message, httpMessages, null);
    }

}
