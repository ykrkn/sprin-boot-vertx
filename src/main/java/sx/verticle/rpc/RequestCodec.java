package sx.verticle.rpc;

import io.vertx.core.eventbus.DeliveryOptions;

/**
 * Created by sx on 10.11.15.
 */
public class RequestCodec extends NativeByteCodec<Request> {

    public static final String NAME = RequestCodec.class.getName();

    @Override
    public String name() {
        return NAME;
    }

    public static DeliveryOptions createDeliveryOptions(){
        return new DeliveryOptions().setCodecName(NAME).setSendTimeout(60000);
    }
}
