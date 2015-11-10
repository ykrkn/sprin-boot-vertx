package sx.verticle;

import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.MessageConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.Router;
import sx.verticle.rpc.RemoteCallException;
import sx.verticle.rpc.Request;
import sx.verticle.rpc.Response;
import sx.verticle.rpc.ResponseCodec;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by sx on 07.11.15.
 */
public abstract class ImporterVerticle
        extends io.vertx.core.AbstractVerticle implements IVerticle, Handler<Message<Request>> {

    protected static final Logger logger = LoggerFactory.getLogger(ImporterVerticle.class);

    protected MessageConsumer<Request> consumer;

    public ImporterVerticle(){

    }

    @Override
    public void start() throws Exception {
        consumer = vertx.eventBus().consumer(channelName());
        consumer.handler(this);
        vertx.periodicStream(generateJitterDelay()).handler((it) -> {
            vertx.eventBus().<String>send(IVerticle.HEARTBEAT_CHANNEL, deploymentID());
        });
    }

    @Override
    public void handle(Message<Request> event) {
        Request req = event.body();
        try{
            Object result = callAnnotatedMethod(req);
            Response res = new Response(req.uuid(), result);
            event.reply(res, ResponseCodec.createDeliveryOptions());
        }catch (RemoteCallException e){
            logger.error(e.getMessage(), e);
            event.fail(0, e.getMessage());
        }
    }

    private static long generateJitterDelay(){
        return (long) (HEARTBEAT_DELAY-(Math.random()*HEARTBEAT_JITTER));
    }


    protected <T> T callAnnotatedMethod(Request req) throws RemoteCallException {
        T result = null;
        try {
            String methodName = req.getMethod();
            Class<?> cls = this.getClass();
            Method found = null;

            for (Method method : cls.getDeclaredMethods()) {
                if (method.isAnnotationPresent(RemoteCall.class)) {
                    if (methodName.equals(method.getName())) {
                        found = method;
                        break;
                    }
                }
            }
            if (found != null) {
                found.setAccessible(true);
                result = (T) found.invoke(this, req.getArgs());
            }else{
                throw new RemoteCallException("Remote call - Method not found " + getClass().getName() + ":" + req.getMethod() + "()");
            }
        } catch (InvocationTargetException e) {
            throw new RemoteCallException(e.getTargetException());
        } catch (IllegalAccessException e) {
            throw new RemoteCallException(e);
        }
        return result;
    }
}
