package com.zhang.charset;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * @author: create by zhl
 * @version: v1.0
 * @description: com.zhang.charset
 * @date:2020/11/11
 */
public class NioCharset {

    @Test
    public void test01() throws CharacterCodingException {

        Charset charset = Charset.forName("GBK");
        //编码
        CharsetEncoder encoder = charset.newEncoder();
        //解码
        CharsetDecoder decoder = charset.newDecoder();

        CharBuffer buffer = CharBuffer.allocate(100);
        buffer.put("如果超人会飞");
        //转换成读模式
        buffer.flip();
        //进行编码，将char变为byte
        ByteBuffer encode = encoder.encode(buffer);

        for (int i = 0; i < 12; i++) {

        System.out.println(encode.get());

        }
       encode.flip();
        CharBuffer decode = decoder.decode(encode);

        System.out.println(decode.toString());

    }


}
