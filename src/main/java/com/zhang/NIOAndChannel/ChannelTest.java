package com.zhang.NIOAndChannel;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

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
    @Test
    public void test01() throws Exception{
        FileInputStream fis=new FileInputStream("E:\\NIO\\src\\1.jpg");
        FileOutputStream fos=new FileOutputStream("E:\\NIO\\src\\2.jpg");
        //获取channel
        FileChannel fisChannel = fis.getChannel();
        FileChannel fosChannel = fos.getChannel();

        //创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while (fisChannel.read(buffer) != -1){
            //把nio转换成读
            buffer.flip();
            fosChannel.write(buffer);
            buffer.clear();
        }
        fosChannel.close();
        fisChannel.close();
        fos.close();
        fis.close();



    }
}
