package com.test.netty_study.timedemo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * Created by IntelliJ IDEA
 * Project: netty-mvn-app
 * User: DaiYan
 * Date: 2017/10/31
 */

/**
 * 继承自ChannelHandlerAdapter，用于对网络事件进行读写操作
 */
public class TimeServerHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg)throws Exception{
        //类型转换
        //ByteBuf类似于java.nio.ByteBuffer，丰富了功能
        ByteBuf buf=(ByteBuf)msg;

        //获取缓冲区字节数组的个数
        byte[] req=new byte[buf.readableBytes()];
        buf.readBytes(req);//将缓冲区数组读入新的数组

        String body=new String(req, Charset.forName("UTF-8"));
        System.out.println("the time server receive order : "+body);
        String currentTime="QUERY TIME ORDER".equals(body)?new Date(System.currentTimeMillis()).toString():"BAD ORDER";
        ByteBuf resp= Unpooled.copiedBuffer(currentTime.getBytes());

        //异步发送消息给客户端
        ctx.write(resp);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx)throws  Exception{
        //将消息发送队列中的消息写入到SocketChannel中发送给对方
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
        //异常时释放资源
        ctx.close();
    }
}
