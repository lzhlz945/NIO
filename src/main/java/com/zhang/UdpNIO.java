package com.zhang;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author: create by zhl
 * @version: v1.0
 * @description: com.zhang
 * @date:2020/11/12
 */
public class UdpNIO {
    /**
     * 非阻塞UDP Nio send 和receive
     */

    @Test
    public void test01() throws IOException {
        DatagramChannel dc=DatagramChannel.open();
        dc.configureBlocking(false);
        FileChannel fileChannel = FileChannel.open(Paths.get("3.jpg"), StandardOpenOption.READ);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
           /* String str="hello";
            byteBuffer.put(str.getBytes());*/
        while (fileChannel.read(byteBuffer)!=-1){
                byteBuffer.flip();
            dc.send(byteBuffer,new InetSocketAddress("127.0.0.1",9090));
                byteBuffer.clear();
            }
          byteBuffer.flip();

        fileChannel.close();
        dc.close();

    }
    @Test
    public void test02() throws IOException {
        DatagramChannel dc=DatagramChannel.open();

        dc.configureBlocking(false);

        dc.bind(new InetSocketAddress(9090));
        Selector selector = Selector.open();
        dc.register(selector, SelectionKey.OP_READ);
        while (selector.select()>0){
            Iterator<SelectionKey> sk =selector.selectedKeys().iterator();
            while (sk.hasNext()){
                SelectionKey it = sk.next();
                if(it.isReadable()){

                 DatagramChannel channel= (DatagramChannel) it.channel();

                    ByteBuffer buffer = ByteBuffer.allocate(1024*10240);

                    FileChannel fileChannel = FileChannel.open(Paths.get("1234.jpg"), StandardOpenOption.WRITE,StandardOpenOption.CREATE);


                        buffer.flip();
                        fileChannel.write(buffer);
                        buffer.clear();

                    channel.receive(buffer);

                    buffer.flip();
                    System.out.println(new String(buffer.array(),0,buffer.limit()));

                    buffer.clear();

                }
            }
            sk.remove();

        }


    }
    @Test
    public void test03(){
        Scanner input = new Scanner(System.in);
        System.out.println("请输入一个字符串(中间能加空格或符号)");
        String a = input.nextLine();
        System.out.println("请输入一个字符串(中间不能加空格或符号)");
        String b = input.next();
        System.out.println("请输入一个整数");
        int c;
        c = input.nextInt();
        System.out.println("请输入一个double类型的小数");
        double d = input.nextDouble();
        System.out.println("请输入一个float类型的小数");
        float f = input.nextFloat();
        System.out.println("按顺序输出abcdf的值：");
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);
        System.out.println(f);
    }
}
