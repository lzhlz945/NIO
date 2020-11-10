package com.zhang.NIOAndChannel;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

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

    /**
     *
     * @throws IOException
     * 第二种读取不通过channel
     * open() 参数：Paths.get() 文件位置
     * StandardOpenOption
     *
     * open().map() 参数：MapMode(), position,size;
     */
    @Test
    public void test02() throws IOException {
        FileChannel open = FileChannel.open(Paths.get("E:\\NIO\\src\\1.jpg"), StandardOpenOption.READ);
        FileChannel open1 = FileChannel.open(Paths.get("E:\\NIO\\src\\3.jpg"), StandardOpenOption.READ, StandardOpenOption.WRITE,StandardOpenOption.CREATE);//CREATE 文件不存在就创建 CREATE_NEW 文件不存在就创建，存在就报错
        MappedByteBuffer map = open.map(FileChannel.MapMode.READ_ONLY, 0, open.size());
        MappedByteBuffer map1 = open1.map(FileChannel.MapMode.READ_WRITE, 0, open.size());
        byte[] det=new byte[map.limit()];
        map.get(det);
        map1.put(det);
        open1.close();
        open.close();


    }
    @Test
    public void test03() throws IOException {
        FileChannel open = FileChannel.open(Paths.get("E:\\NIO\\src\\1.jpg"), StandardOpenOption.READ);
        FileChannel open2 = FileChannel.open(Paths.get("E:\\NIO\\src\\6.jpg"), StandardOpenOption.READ,StandardOpenOption.WRITE,StandardOpenOption.CREATE_NEW);

        MappedByteBuffer map2 = open.map(FileChannel.MapMode.READ_ONLY, 0, open.size());
        MappedByteBuffer map1 = open2.map(FileChannel.MapMode.READ_WRITE, 0, open.size());

        byte[] bytes=new byte[map2.limit()];
        map2.get(bytes);
        map1.put(bytes);
        open2.close();
        open.close();


    }
}
