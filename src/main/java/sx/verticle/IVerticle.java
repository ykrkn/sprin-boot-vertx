package sx.verticle;

/**
 * Created by sx on 07.11.15.
 */
public interface IVerticle {
    String HEARTBEAT_CHANNEL = "HEARTBEAT";

    /**
     * Send request and gets generated query or null if not exists
     */
    String QUERY_PRODUCER_CHANNEL = "QUERY_PRODUCER";

    /**
     * Send Query as request - no wait result
     */
    String EXTRACTOR_CHANNEL = "EXTRACTOR";

    /**
     * Receive DSMatrix extracted as-is
     */
    String SOURCE_DATA_CHANNEL = "SOURCE_DATA_CHANNEL";

    /**
     * Send DSMatrix to transformer and underlying business logic - no wait result
     */
    String TRANSFORMER_CHANNEL = "TRANSFORMER_CHANNEL";

    /**
     * Receive transformed MLMatrix
     */
    String BI_DATA_CHANNEL = "BI_DATA_CHANNEL";

    /**
     * Send MLMatrix into dataset loader and no wait result
     */
    String LOADER_CHANNEL = "LOADER";

    long HEARTBEAT_DELAY = 1000;

    long HEARTBEAT_JITTER = 200;

    char CHANNEL_SEPARATOR = ':';

    String channelName();

    static String qname(String... name){
        if(name.length == 1) return name[0];
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<name.length; ++i){
            if(i!=0) sb.append(CHANNEL_SEPARATOR);
            sb.append(name[i]);
        }
        return sb.toString();
    }

}
