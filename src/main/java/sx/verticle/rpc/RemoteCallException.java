package sx.verticle.rpc;

/**
 * Created by sx on 10.11.15.
 */
public class RemoteCallException extends Exception {

    public RemoteCallException(Throwable targetException) {
        super(targetException);
    }

    public RemoteCallException(String message) {
        super(message);
    }
}
