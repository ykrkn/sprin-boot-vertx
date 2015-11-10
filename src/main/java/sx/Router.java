package sx;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.MessageConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import sx.payload.BatchState;
import sx.payload.DSMatrix;
import sx.payload.Query;
import sx.verticle.impl.QueryProducerVerticle;
import sx.verticle.impl.TransformerVerticle;
import sx.verticle.rpc.Request;
import sx.verticle.rpc.RequestCodec;
import sx.verticle.IVerticle;
import sx.verticle.rpc.Response;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by sx on 07.11.15.
 */
@Component
public final class Router extends io.vertx.core.AbstractVerticle {

    private static final Logger logger = LoggerFactory.getLogger(Router.class);

    private static final Map<String, Long> heartbeatMap = new ConcurrentHashMap<>();

    public static final Map<UUID, Object> transientResultMap = new ConcurrentHashMap<>();

    @Autowired
    private Vertx vertx;

    @PostConstruct
    private void init() {
        vertx.deployVerticle(this);
    }

    @Override
    public void start() throws Exception {
        // handle deploymentID as String
        vertx.eventBus().<String>consumer(IVerticle.HEARTBEAT_CHANNEL).handler((m)->{
            heartbeatMap.put(m.body(), System.currentTimeMillis());
        });

        sendRequest(IVerticle.qname(IVerticle.QUERY_PRODUCER_CHANNEL),
                new Request(QueryProducerVerticle.GET_QUERY_METHOD, "STRING", 1, true), (m)->{
                    if(m.succeeded()) {
                        Assert.notNull(m.result());
                        sendRequest(IVerticle.EXTRACTOR_CHANNEL, new Request("extract", m.result()));
                    } else {
                        handleError(m.cause());
                    }
                }
        );

        vertx.eventBus().<Response>consumer(IVerticle.SOURCE_DATA_CHANNEL).handler((m)-> {
            Assert.notNull(m.body());
            Assert.isInstanceOf(UUID.class, m.body().getPayload(), "Expects UUID reference");
            // some logic for routing....
            sendRequest(IVerticle.TRANSFORMER_CHANNEL,
                    new Request(TransformerVerticle.TRANSFORM_METHOD, m.body().getPayload())
            );
        });

        vertx.eventBus().<Response>consumer(IVerticle.BI_DATA_CHANNEL).handler((m)->{

        });
    }

    private void handleError(Throwable cause) {
        logger.error(cause.getMessage(), cause);
    }

    public void sendRequest(String channel, Request request){
        vertx.eventBus().send(channel, request, RequestCodec.createDeliveryOptions());
    }

    public void sendRequest(String channel, Request request, Handler<AsyncResult<Message<Response>>> handler){
        vertx.eventBus().send(channel, request, RequestCodec.createDeliveryOptions(), handler);
    }

    public static boolean isVerticleAlive(String ID){
        if(!heartbeatMap.containsKey(ID)) return false;
        return (System.currentTimeMillis() - heartbeatMap.get(ID)) < (3 * IVerticle.HEARTBEAT_DELAY);
    }
}
