package ropandi.itv.restbase.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ropandi.itv.restbase.enumeration.IHttpMessages;

import java.io.Serial;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class AppException extends RuntimeException {

    /** */
    @Serial
    private static final long serialVersionUID = -9096677093257630458L;

    private IHttpMessages httpMessages;
    private List<String> errors;

    public AppException(IHttpMessages httpMessages) {
        super();
        this.httpMessages = httpMessages;
    }


    public AppException(List<String> errors, IHttpMessages httpMessages) {
        super();
        this.httpMessages = httpMessages;
        this.errors = errors;
    }

}
