package sx.verticle.rpc;

import io.vertx.core.eventbus.DeliveryOptions;

/**
 * Created by sx on 10.11.15.
 */
public class ResponseCodec extends NativeByteCodec<Response> {

    public static final String NAME = ResponseCodec.class.getName();

    @Override
    public String name() {
        return NAME;
    }

    public static DeliveryOptions createDeliveryOptions(){
        return new DeliveryOptions().setCodecName(NAME);
    }
}
