package ie.mid.identityengine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ServerErrorException extends RuntimeException {
    public ServerErrorException() {
        super();
    }

    public ServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerErrorException(String message) {
        super(message);
    }

    public ServerErrorException(Throwable cause) {
        super(cause);
    }
}
