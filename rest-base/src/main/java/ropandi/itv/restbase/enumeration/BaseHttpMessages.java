package ropandi.itv.restbase.enumeration;

public enum BaseHttpMessages implements IHttpMessages{
   OK("R200","Success", 200),
    BAD_REQUEST("ER500", "Bad Request!", 500),
    UNAUTHORIZED("ER401", "Unauthorized!", 401),
    SERVICE_UNAVAILABLE("ER503", "Service unavailable!", 503),
    FAILED_SAVED_DATA("ER503S","Failed save!", 503)
    ;


    private final String code;
    private final String message;
    private final int httpStatusCode;

    BaseHttpMessages(String code, String message, int httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }

    @Override
    public String getCode() {
        return "";
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public int getHttpStatusCode() {
        return 0;
    }
}
