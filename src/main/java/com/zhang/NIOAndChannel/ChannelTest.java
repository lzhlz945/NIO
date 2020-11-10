package com.zhang.NIOAndChannel;

/**
 * @author: create by zhl
 * @version: v1.0
 * @description: com.zhang.NIOAndChannel
 * @date:2020/11/10
 */
public class ChannelTest {
    /**
     * nio  传输是以通道传输的，通道不能直接传输数据，就通过缓冲实现在通道中传输数据
     * 通道的api：java.nio.channels.channel
     * FileChannel
     * SocketChannel
     * ServerChannel
     * DatagramChannel
     *
     *获取通道的方法:
     * 1、 getChannel()
     * 本地
     * FileInputStream/OutputStream
     * 网络
     *      Socket
     *      ServerSocket
     *      DatagramSocket udp
     *2、在JDK 1.7 nio2 中为通道提供了静态方法open();
     *
     *3、在JDK 1.7 nio2 中的Files 工具类的newByteChannel();
     */
}
