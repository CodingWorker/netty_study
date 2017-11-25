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
        //配置客户端nio线程组
        //主要用于io读写
        EventLoopGroup group=new NioEventLoopGroup();
        try{
            //客户端辅助启动类
            Bootstrap btsp=new Bootstrap();

            //配置辅助启动类
            //NioSocketChannel对应jdk的SocketChannel
            //handler是当创建NioSocketChannel成功之后，在进行初始化时，将它的ChannelHandler
            //设置到ChannelPipline中，用于处理网络IO事件
            btsp.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch){
                            ch.pipeline().addLast(new TimeClientHandler());
                        }
                    });

            //发起异步连接操作
            //调用connect方法发起异步连接
            //然后sync同步等待连接成功
            ChannelFuture future=btsp.connect(host,port).sync();

            //等待客户端链路关闭
            future.channel().closeFuture().sync();
        }finally{
            //优雅退出，释放NIO线程组资源
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception{
        int port=8080;
        if(args!=null&&args.length>0){
            try{
                port=Integer.valueOf(args[0]);
            }catch(NumberFormatException ex){
                //使用默认端口
            }
        }

        new TimeClient().connect(port,"localhost");
    }
}
