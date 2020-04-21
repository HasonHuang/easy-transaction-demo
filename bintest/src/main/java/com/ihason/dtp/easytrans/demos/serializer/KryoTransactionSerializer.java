
package com.ihason.dtp.easytrans.demos.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.ihason.dtp.easytrans.demos.TxLog;

/**
 * Created by changming.xie on 7/22/16.
 */
public class KryoTransactionSerializer<T> implements ObjectSerializer<T> {

    private static Kryo kryo = null;

    static {
        kryo = new Kryo();

        kryo.register(TxLog.class);

        // 设置上下文类加载器，解决使用Spring-boot-devtools时引起的不同类加载器问题
        kryo.setClassLoader(Thread.currentThread().getContextClassLoader());
    }


    @Override
    public byte[] serialize(T transaction) {
        Output output = new Output(256, -1);
        kryo.writeObject(output, transaction);
        return output.toBytes();
    }

    @Override
    public T deserialize(byte[] bytes) {
        Input input = new Input(bytes);
        TxLog transaction = kryo.readObject(input, TxLog.class);
        return (T) transaction;
    }
}
