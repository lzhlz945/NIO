package com.zhang.非阻塞式io;

import com.sun.org.apache.bcel.internal.generic.Select;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * @author: create by zhl
 * @version: v1.0
 * @description: com.zhang.非阻塞式io
 * @date:2020/11/11
 */
public class UnBlockIo {
    @Test
    public void client() throws IOException {

        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9092));

        socketChannel.configureBlocking(false);
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        buffer.put(new Date().toString().getBytes());
        buffer.flip();
        socketChannel.write(buffer);
        socketChannel.close();
    }

    @Test
    public void server() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //转换成飞阻塞式io
        serverSocketChannel.configureBlocking(false);
        //绑定客服端
        serverSocketChannel.bind(new InetSocketAddress(9092));
        //创建选择器
        Selector selector = Selector.open();
        //把通道注册到选择器上  SelectionKey.OP_ACCEPT 选择键
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (selector.select()>0){
            Iterator<SelectionKey> s = selector.selectedKeys().iterator();
            while (s.hasNext()){
                SelectionKey next = s.next();
                if(next.isAcceptable()){
                    SocketChannel accept = serverSocketChannel.accept();
                    //切换成非阻塞式Io
                    accept.configureBlocking(false);
                    //注册到选择器上
                    accept.register(selector,SelectionKey.OP_READ);

                }else if(next.isReadable()){
                    SocketChannel channel = (SocketChannel) next.channel();

                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int len=0 ;
                    while ((len=channel.read(buffer) )> 0){
                        buffer.flip();
                        System.out.println(new String(buffer.array(),0,len));
                        buffer.clear();
                    }

                }
                s.remove();

            }


        }


    }

    @Test
    public void client01() throws IOException {

        //1、创建socket通道 并转换成非阻塞式io
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9191));
        socketChannel.configureBlocking(false);
        //2、创建 缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        buffer.put("发送的测试消息01".getBytes());
        buffer.put("发送的测试消息02".getBytes());
        buffer.put("----------------".getBytes());
        buffer.put(new Date().toString().getBytes());
        //3、转换成读模式
        buffer.flip();
        socketChannel.write(buffer);
        //4、关闭
        socketChannel.close();

    }


    @Test
    public void server01() throws IOException {

        //1、创建ServerSocket 通道 并转换成非阻塞io
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        //2、绑定客服端
        serverSocketChannel.bind(new InetSocketAddress(9191));

        //3、创建选择器
        Selector selector = Selector.open();

        //4、把ServerSocketChannel注册到选择器上  SelectionKey.OP_ACCEPT选择键
        serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
        while (selector.select() > 0){

            //5、遍历selectKeys
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()){
                SelectionKey key = it.next();

                //6、判断key
                if(key.isAcceptable()){
                    //7、变为非阻塞 并转注册到select上
                    SocketChannel accept = serverSocketChannel.accept();
                    accept.configureBlocking(false);
                    accept.register(selector,SelectionKey.OP_READ);

                }else if(key.isReadable()){
                    //8、读取数据
                    SocketChannel socketChannel= (SocketChannel) key.channel();

                    ByteBuffer buffer = ByteBuffer.allocate(1024);

                    int len=0;
                    while ((len=socketChannel.read(buffer)) >0){
                        buffer.flip();
                        System.out.println(new String(buffer.array(),0,len));
                        buffer.clear();
                    }


                }


            }
            //迭代器删除选择器
            it.remove();


        }
    }







}
