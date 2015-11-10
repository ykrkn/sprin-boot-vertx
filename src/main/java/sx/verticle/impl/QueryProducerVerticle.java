package sx.verticle.impl;

import sx.verticle.ImporterVerticle;
import sx.verticle.RemoteCall;

import java.util.ArrayList;

/**
 * Created by sx on 07.11.15.
 */
public class QueryProducerVerticle extends ImporterVerticle {

    public static final String GET_QUERY_METHOD = "getQuery";

    protected final String sourceUrl;

    public QueryProducerVerticle(String sourceUrl) {
        super();
        this.sourceUrl = sourceUrl;
    }

    @Override
    public String channelName() {
        return QUERY_PRODUCER_CHANNEL;// + CHANNEL_SEPARATOR + sourceUrl;
    }

    @RemoteCall
    public Object getQuery(String a1, int a2, boolean a3){
        System.out.println(a1);
        System.out.println(a2);
        System.out.println(a3);
        //if(userIsAGoat()) throw new RuntimeException("ZZZ");
        return new ArrayList<>();
    }

    private boolean userIsAGoat() {
        return true;
    }
}
