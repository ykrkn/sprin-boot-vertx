package sx.verticle.rpc;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;

import java.io.*;

/**
 * Created by sx on 09.11.15.
 */
public abstract class NativeByteCodec<T> implements MessageCodec<T, T> {

    @Override
    public void encodeToWire(Buffer buffer, T o) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutput out = new ObjectOutputStream(bos)) {
            out.writeObject(o);
            buffer.appendBytes( bos.toByteArray() );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public T decodeFromWire(int pos, Buffer buffer) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(buffer.getBytes());
             ObjectInput in = new ObjectInputStream(bis)) {
            return (T) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public T transform(T o) {
        return o;
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }

}
