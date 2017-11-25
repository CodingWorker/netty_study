package com.test.netty_study.timedemo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.Socket;

/**
 * Created by IntelliJ IDEA
 * Project: netty-mvn-app
 * User: DaiYan
 * Date: 2017/10/31
 */
public class TimeClient {
    public void connect(int port,String host)throws Exception{
        //���ÿͻ���nio�߳���
        //��Ҫ����io��д
        EventLoopGroup group=new NioEventLoopGroup();
        try{
            //�ͻ��˸���������
            Bootstrap btsp=new Bootstrap();

            //���ø���������
            //NioSocketChannel��Ӧjdk��SocketChannel
            //handler�ǵ�����NioSocketChannel�ɹ�֮���ڽ��г�ʼ��ʱ��������ChannelHandler
            //���õ�ChannelPipline�У����ڴ�������IO�¼�
            btsp.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch){
                            ch.pipeline().addLast(new TimeClientHandler());
                        }
                    });

            //�����첽���Ӳ���
            //����connect���������첽����
            //Ȼ��syncͬ���ȴ����ӳɹ�
            ChannelFuture future=btsp.connect(host,port).sync();

            //�ȴ��ͻ�����·�ر�
            future.channel().closeFuture().sync();
        }finally{
            //�����˳����ͷ�NIO�߳�����Դ
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception{
        int port=8080;
        if(args!=null&&args.length>0){
            try{
                port=Integer.valueOf(args[0]);
            }catch(NumberFormatException ex){
                //ʹ��Ĭ�϶˿�
            }
        }

        new TimeClient().connect(port,"localhost");
    }
}
