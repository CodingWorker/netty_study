package com.test.netty_study.timedemo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by IntelliJ IDEA
 * Project: netty-mvn-app
 * User: DaiYan
 * Date: 2017/10/31
 */
public class TimeServer {
    public void bind(int port)throws Exception{
        //���÷���˵�NIO�߳���
        //NioEventLoopGroup��һ���߳��飬ר�����������¼��Ĵ���
        //ʵ�������Ǿ���Reactor�߳���
        //���ﴴ��������ԭ���ǣ�һ�����ڷ���˽��տͻ��˵����ӣ�һ������
        // ����SocketChannel����·��д
        EventLoopGroup bossGroup=new NioEventLoopGroup();
        EventLoopGroup workerGroup=new NioEventLoopGroup();
        try{
            //ServerBootstrap��netty��������NIO����˵ĸ��������࣬Ŀ���ǽ���
            // ����˵Ŀ����Ѷ�
            ServerBootstrap boots=new ServerBootstrap();

            boots.group(bossGroup,workerGroup)//���������߳���
                    .channel(NioServerSocketChannel.class)//��ӦJDK�е�ServerSocketChannel��
                    .option(ChannelOption.SO_BACKLOG,1024)//����TCP����
                    .childHandler(new ChildChannelHandler());//��IO�¼������࣬�����紦����־������Ϣ���б�����

            //�󶨶˿�
            // ͬ���ȴ��ɹ�
            //ChannelFuture�Ĺ�������java.lang.concurrent.Future�࣬��Ҫ�����첽������֪ͨ�ص�
            ChannelFuture future=boots.bind(port).sync();

            //�ȴ�����˼����˿ڹر�
            //Ȼ��Ż��˳�main
            future.channel().closeFuture().sync();
        }finally {
            //���ŵ��˳����ͷ��߳���Դ
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel>{
        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline().addLast(new TimeServerHandler());
        }
    }

    public static void main(String[] args) throws Exception{
        int port=8080;
        if(args!=null&&args.length>0){
            try{
                port=Integer.valueOf(args[0]);
            }catch(NumberFormatException ex){
                //ʹ��Ĭ��ֵ
            }
        }

        new TimeServer().bind(port);
    }
}
