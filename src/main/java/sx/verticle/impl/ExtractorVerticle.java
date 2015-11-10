package sx.verticle.impl;

import io.vertx.core.eventbus.Message;
import sx.IConnector;
import sx.payload.Query;
import sx.verticle.ImporterVerticle;
import sx.verticle.rpc.Request;

/**
 * Created by sx on 07.11.15.
 */
public class ExtractorVerticle extends ImporterVerticle {

    protected final IConnector connector;

    public ExtractorVerticle(IConnector connector) {
        super();
        this.connector = connector;
    }

    @Override
    public String channelName() {
        return EXTRACTOR_CHANNEL;// + CHANNEL_SEPARATOR + connector.getUrl();
    }

}
