package com.i5306.java.netty.chat.Serializer;

public interface Serializer {

    byte JSON_SERIALIZER = 1;

    Serializer DEFAULT = new JSONSerializer();

    /**
     * 序列化算法
     * @return
     */
    byte getSerializerAlgorithm();

    /**
     * 对象序列化成二进制
     * @param object
     * @return
     */
    byte[] serialize(Object object);

    /**
     * 二进制序列化成对象
     * @param clazz
     * @param bytes
     * @param <T>
     * @return
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
