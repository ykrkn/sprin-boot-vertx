package sx.verticle.rpc;

import java.util.UUID;

/**
 * Created by sx on 09.11.15.
 */
public final class Response {

    private final UUID uuid;

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    private Object payload;

    public Response(UUID requestUUID){
        uuid = requestUUID;
    }

    public Response(UUID requestUUID, Object result){
        this(requestUUID);
        payload = result;
    }

    public UUID uuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return "Response{" + uuid +
                ", payload=" + payload +
                '}';
    }
}
