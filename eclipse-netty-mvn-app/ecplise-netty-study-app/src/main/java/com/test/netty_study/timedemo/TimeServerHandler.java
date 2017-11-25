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
 * �̳���ChannelHandlerAdapter�����ڶ������¼����ж�д����
 */
public class TimeServerHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg)throws Exception{
        //����ת��
        //ByteBuf������java.nio.ByteBuffer���ḻ�˹���
        ByteBuf buf=(ByteBuf)msg;

        //��ȡ�������ֽ�����ĸ���
        byte[] req=new byte[buf.readableBytes()];
        buf.readBytes(req);//����������������µ�����

        String body=new String(req, Charset.forName("UTF-8"));
        System.out.println("the time server receive order : "+body);
        String currentTime="QUERY TIME ORDER".equals(body)?new Date(System.currentTimeMillis()).toString():"BAD ORDER";
        ByteBuf resp= Unpooled.copiedBuffer(currentTime.getBytes());

        //�첽������Ϣ���ͻ���
        ctx.write(resp);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx)throws  Exception{
        //����Ϣ���Ͷ����е���Ϣд�뵽SocketChannel�з��͸��Է�
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
        //�쳣ʱ�ͷ���Դ
        ctx.close();
    }
}
