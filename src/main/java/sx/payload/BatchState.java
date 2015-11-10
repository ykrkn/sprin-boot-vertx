package sx.payload;

import java.util.UUID;

/**
 * Created by sx on 08.11.15.
 */
public final class BatchState {

    public static int QUERY = 1;

    public static int PREPROCESS = 2;

    public static int MAPPING = 4;

    public static int MRESOLVER = 8;

    public static int LRESOLVER = 16;

    public static int PRESOLVER = 32;

    public static int LOADER = 64;

    private Object result;

    private final UUID batchID;

    private int currentState = 0;

    public BatchState() {
        batchID = UUID.randomUUID();
    }

    public boolean checkState(int state){
        return 1 == (state & currentState);
    }

    public int changeState(int state){
        currentState |= state;
        return currentState;
    }

    public UUID getBatchID() {
        return batchID;
    }

    public Object get(){
        return result;
    }

    public void set(Object result){
        this.result = result;
    }
}
