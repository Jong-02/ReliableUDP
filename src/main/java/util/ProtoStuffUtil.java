package util;

import entity.ClientInfo;
import entity.ClientSentP;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/**
 * @ClassName util.ProtoStuffUtil
 * @Description TODO
 *  @description protostuff序列化
 *  * 并将schema对象缓存起来
 * @Version 1.0
 */
@SuppressWarnings("unused")
public class ProtoStuffUtil {

    public static void main(String[] args) {
        ClientInfo clientInfo = new ClientInfo("127",12);
        ClientSentP clientSentP = new ClientSentP("abc",clientInfo);
        byte[] test = serialize(clientSentP);
        System.out.println(test);

        ClientSentP c = deserialize(test, ClientSentP.class);
        System.out.println(c.toString());
    }


    /**
     * 避免每次序列化都要重新申请Buffer空间
     */
    private final static LinkedBuffer BUFFER = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
    /**
     * 缓存schema对象的map
     */
    private final static Map<Class<?>, Schema<?>> CACHED_SCHEMA = new ConcurrentHashMap<Class<?>, Schema<?>>();

    /**
     * 根据获取相应类型的schema方法
     *
     * @param clazz 获得视图的类
     * @return 返回
     */
    @SuppressWarnings("unchecked")
    public static  <T> Schema<T> getSchema(Class<T> clazz) {
        // 先尝试从缓存schema map中获取相应类型的schema
        Schema<T> schema = (Schema<T>) CACHED_SCHEMA.get(clazz);
        // 如果没有获取到对应的schema，则创建一个该类型的schema
        // 同时将其添加到schema map中
        if (schema == null) {
            schema = RuntimeSchema.getSchema(clazz);
            if (schema != null) {
                CACHED_SCHEMA.put(clazz, schema);
            }
        }
        // 返回schema对象
        return schema;
    }

    /**
     * 序列化方法，将对象序列化为字节数组（对象 ---> 字节数组）
     *
     * @param obj 序列化对象
     * @return 字节码
     */
    @SuppressWarnings("unchecked")
    public static <T> byte[] serialize(T obj) {
        // 获取泛型对象的类型
        Class<T> clazz = (Class<T>) obj.getClass();
        // 创建泛型对象的schema对象
        Schema<T> schema = getSchema(clazz);
        try {
            // 序列化
            // 返回序列化对象
            return ProtostuffIOUtil.toByteArray(obj, schema, BUFFER);
        } finally {
            BUFFER.clear();
        }
    }

    /**
     * 反序列化方法，将字节数组反序列化为对象（字节数组 ---> 对象）
     */
    public static <T> T deserialize(byte[] data, Class<T> clazz) {
        // 创建泛型对象的schema对象
        Schema<T> schema = getSchema(clazz);
        // 根据schema实例化对象
        T message = schema.newMessage();
        // 将字节数组中的数据反序列化到message对象
        ProtostuffIOUtil.mergeFrom(data, message, schema);
        // 返回反序列化对象
        return message;
    }

    public static <T> T deserialize(byte[] data, int length, Class<T> clazz) {
        byte[] a = new byte[length];
        System.arraycopy(data, 0, a, 0, length);
        return deserialize(a, clazz);
    }

}
