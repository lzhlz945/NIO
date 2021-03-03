package com.zhang.fenAndhe.FAH;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author: create by zhl
 * @version: v1.0
 * @description: com.zhang.fenAndhe.FAH
 * @date:2020/11/11
 */
public class TestFandH {

    @Test
    public void test01() throws IOException {
        RandomAccessFile randomAccessFile=new RandomAccessFile("E:\\11.txt","rw");

        FileChannel channel = randomAccessFile.getChannel();
        ByteBuffer buffer1=ByteBuffer.allocate(100);
        ByteBuffer buffer2=ByteBuffer.allocate(1024);
        ByteBuffer[] buffers={buffer1,buffer2};
        channel.read(buffers);

        for (ByteBuffer buffer : buffers) {
            buffer.flip();
            System.out.println(new String(buffer.array(),0,buffer.limit()));
            System.out.println("______________________**************_______________________");
        }
//        System.out.println(new String(buffers[0].array(),0,buffers[0].limit()));
//        System.out.println("______________________**************_______________________");
//        System.out.println(new String(buffers[1].array(),0,buffers[1].limit()));

        RandomAccessFile randomAccessFile1=new RandomAccessFile("E:\\112.txt","rw");
        FileChannel channel1 = randomAccessFile1.getChannel();

        channel1.write(buffers);


    }


}
