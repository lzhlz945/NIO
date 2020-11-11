package com.zhang.net通信;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author: create by zhl
 * @version: v1.0
 * @description: com.zhang.net通信
 * @date:2020/11/11
 */
public class InetTest {
    /**
     * FileChannel 只能阻塞式Io
     *
     * 网络读写步骤：
     * 客服端：
     * 1、创建socket通道 在open 中 new InetSocketAddress("127.0.0.1",9090)
     * 2、创建读本地文件通道
     * 3、创建ByteBuffer
     * 4、读写操作 flip clear
     * 5、关闭
     *
     * 服务端：
     * 1、创建serverSocket 通道 OPEN中不许参数
     * 2、创建文件写通道
     * 3、绑定客服端 bind(port: xxxx);
     * 4、accept 接受数据
     * 5、读写操作
     * 6、关闭；
     */
    @Test
    public void client01() throws IOException {

        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1",9090));

        FileChannel fileChannel = FileChannel.open(Paths.get("E:\\NIO\\src\\1.jpg"), StandardOpenOption.READ);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (fileChannel.read(buffer) != -1){
            buffer.flip();

            socketChannel.write(buffer);
            buffer.clear();
        }

        fileChannel.close();
        socketChannel.close();

    }
    @Test
    public void server01() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        FileChannel fileChannel = FileChannel.open(Paths.get("E:\\NIO\\src\\22.jpg"), StandardOpenOption.WRITE,StandardOpenOption.CREATE);
        //绑定客服端
        serverSocketChannel.bind(new InetSocketAddress(9090));

        //接收数据
        SocketChannel accept = serverSocketChannel.accept();

        //创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while (accept.read(buffer) != -1){
            buffer.flip();
            fileChannel.write(buffer);
            buffer.flip();
        }

        accept.close();
        fileChannel.close();
        serverSocketChannel.close();

    }

    @Test
    public void client02() throws IOException {
        //创建socket通道
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8081));

        //创建读取本地文件的通道
        FileChannel fileChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);

        //创建缓存
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (fileChannel.read(buffer) != -1){
            //转换成读
            buffer.flip();
            socketChannel.write(buffer);
            //刷新 position的值
            buffer.clear();
        }
        //关闭
        fileChannel.close();
        socketChannel.close();

    }

    @Test
    public void server02() throws IOException {
        //创建serverSocket 通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //创建文件写通道
        FileChannel fileChannel = FileChannel.open(Paths.get("3.jpg"), StandardOpenOption.WRITE,StandardOpenOption.CREATE);
        //绑定客服端
        serverSocketChannel.bind(new InetSocketAddress(8081));
        //接受客服端的消息
        SocketChannel accept = serverSocketChannel.accept();
        //创建缓存区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //读写操作
        while (accept.read(buffer) != -1){
            buffer.flip();
            fileChannel.write(buffer);
            buffer.clear();

        }

        fileChannel.close();
        accept.close();
        serverSocketChannel.close();
    }
}
