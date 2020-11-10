package com.zhang.ioAndNio;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

/**
 * @author: create by zhl
 * @version: v1.0
 * @description: com.zhang.ioAndNio
 * @date:2020/11/10
 */
public class Deffer {
    /**
     * IO与NIO的区别
     * IO:是面向流的；是单向的，输入、输出流
     *    是阻塞式IO，没有选择器
     * NIO：面向通道，缓冲区（铁路类似），数据传输利用缓冲区（火车类似）；是双向的
     *      非阻塞IO,有选择器。
     */

    /**
     * 一、缓冲区（buffer）
     * 缓冲区类型：
     * ByteBuffer、CharBuffer.....  是一些数据存放不同类型的数据
     * 通过allocate()获取缓冲区 分配的意思
     *
     * 二、存取数据的方法
     * get()、put()
     *
     * 三、四个核心属性
     *     private int mark = -1;   使用reset()后记录最后一次position的值，position==mark;
     *     private int position = 0; 存放数据最后指向的数组位置
     *     private int limit;     表示缓冲区中可以操作数据的大小；limit后数据不能读写
     *     private int capacity（容量）; 缓冲区最大数据容量；一旦声明不能改变
     *
     * 方法：
     * 1、alcohol():分配一个指定类型的缓冲区：--Buffer.alcohol();
     * 2、flip();转换成读的状态； 翻转
     * 3、rewind()读完后再次转换成开始读的状态，position==0；倒带
     * 4、clear()清空position、limit的数据，单buffer中的数据不会清除
     * 5、reset()重置position==0;
     * 6、remaining()缓冲区还剩于的可操作数据  剩余的
      */
 @Test
 public void test01(){

     ByteBuffer buffer=ByteBuffer.allocate(1024);

     System.out.println("------------------------------");
     System.out.println(buffer.position()); //0
     System.out.println(buffer.limit());    //1024
     System.out.println(buffer.capacity()); //1024

     String str="abcdefg";
     byte[] bytes = str.getBytes();
     buffer.put(bytes);

     System.out.println(buffer.position()); //7
     System.out.println(buffer.limit());    //1024
     System.out.println(buffer.capacity()); //1024

     //存入数据后转换成读取模式

     buffer.flip();
     //limit==position、position==0
     System.out.println(buffer.position()); //0
     System.out.println(buffer.limit());    //7
     System.out.println(buffer.capacity()); //1024

     byte[] dst=new byte[buffer.limit()];
     ByteBuffer byteBuffer = buffer.get(dst);
     System.out.println(byteBuffer.toString());
     System.out.println(new String(dst,0,dst.length));
     System.out.println(buffer.position()); //7
     System.out.println(buffer.limit());    //7
     System.out.println(buffer.capacity()); //1024

     //再次切换到最开始的读,可以重新开始读
     buffer.rewind();

     System.out.println(buffer.position()); //0
     System.out.println(buffer.limit());    //7
     System.out.println(buffer.capacity()); //1024


     //clear()清空 ，只是清空了position、limit和capacity 缓冲区的数据时不会清除的

     buffer.clear();
     System.out.println(buffer.position()); //0
     System.out.println(buffer.limit());    //1024
     System.out.println(buffer.capacity()); //1024

 }
 @Test
    public void test02(){
     ByteBuffer buffer=ByteBuffer.allocate(10);
     String str="abcde";

     buffer.put(str.getBytes());
     buffer.flip();
     byte[] bytes=new byte[2];
     buffer.get(bytes,0,2);
     System.out.println(new String(bytes,0,2));
     System.out.println(buffer.position());
     buffer.mark();
     System.out.println(buffer.remaining());
     buffer.get(bytes,0,2);
     System.out.println(new String(bytes,0,2));
     System.out.println(buffer.position());

     buffer.reset();
     System.out.println(buffer.position());

 }

    /**
     * 直接缓冲区：allocateDirect();
     * 减去了copy：不易控制，资源消耗大
     */
    @Test
    public void test03(){
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        System.out.println(buffer.isDirect());

    }

}
