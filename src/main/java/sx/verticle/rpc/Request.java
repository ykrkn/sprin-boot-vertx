package sx.verticle.rpc;

import java.util.UUID;

/**
 * Created by sx on 09.11.15.
 */
public final class Request {

    private final UUID uuid;
    private String method;
    private Object[] args;

    public Request(String method, Object ... args){
        this.uuid = UUID.randomUUID();
        this.method = method;
        this.args = args;
    }

    public String getMethod(){
        return  method;
    }

    @Override
    public String toString() {
        StringBuilder _args = new StringBuilder();
        for(int i=0; i<args.length; ++i){
            if(i!=0) _args.append(','); _args.append(args[i]);
        }
        return "Request{" + uuid +
                ", method='" + method + '\'' +
                ", args=[" + _args + "]" +
                '}';
    }

    public Object[] getArgs() {
        return args;
    }

    public UUID uuid() {
        return uuid;
    }
}
