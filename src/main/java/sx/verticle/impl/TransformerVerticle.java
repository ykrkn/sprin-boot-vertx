package sx.verticle.impl;

import sx.verticle.ImporterVerticle;
import sx.verticle.RemoteCall;

import java.util.UUID;

/**
 * Created by sx on 10.11.15.
 */
public class TransformerVerticle extends ImporterVerticle {

    public static final String TRANSFORM_METHOD = "transform";

    protected final String sourceUrl;

    public TransformerVerticle(String sourceUrl) {
        super();
        this.sourceUrl = sourceUrl;
    }

    @Override
    public String channelName() {
        return TRANSFORMER_CHANNEL;// + CHANNEL_SEPARATOR + sourceUrl;
    }

    @RemoteCall
    public Object transform(UUID mtxRef){
        return null;
    }
}