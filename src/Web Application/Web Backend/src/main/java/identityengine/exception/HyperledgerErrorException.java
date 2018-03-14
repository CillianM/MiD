package identityengine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class HyperledgerErrorException extends RuntimeException {
    public HyperledgerErrorException() {
        super();
    }

    public HyperledgerErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public HyperledgerErrorException(String message) {
        super(message);
    }

    public HyperledgerErrorException(Throwable cause) {
        super(cause);
    }
}
