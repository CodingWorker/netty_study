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
    public void bind(int port)throws {
        //配置服务端的NIO线程组
        //NioEventLoopGroup是一组线程组，专门用于网络事件的处理
        //实际上它们就是Reactor线程组
        //这里创建两个的原因是：一个用于服务端接收客户端的连接，一个用于
        // 进行SocketChannel的网路读写
        EventLoopGroup bossGroup=new NioEventLoopGroup();
        EventLoopGroup workerGroup=new NioEventLoopGroup();
        try{
            //ServerBootstrap是netty用于启动NIO服务端的辅助启动类，目的是降低
            // 服务端的开发难度
            ServerBootstrap boots=new ServerBootstrap();

            boots.group(bossGroup,workerGroup)//加入两个线程组
                    .channel(NioServerSocketChannelException.class)//对应JDK中的ServerSocketChannel类
                    .option(ChannelOption.SO_BACKLOG,1024)//配置TCP参数
                    .childHandler(new ChildChannelHandler());//绑定IO事件处理类，；例如处理日志，对消息进行编解码等

            //绑定端口
            // 同步等待成功
            //ChannelFuture的功能类似java.lang.concurrent.Future类，主要用于异步操作的通知回调
            ChannelFuture future=boots.bind(port).sync();

            //等待服务端监听端口关闭
            //然后才会退出main
            future.channel().closeFuture().sync();
        }finally {
            //优雅的退出，释放线程资源
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
                //使用默认值
            }
        }

        new TimeServer().bind(port);
    }
}
