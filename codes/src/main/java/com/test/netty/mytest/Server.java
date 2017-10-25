package com.test.netty.mytest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by DaiYan on 2017/9/6.
 */
public class Server {
    public static void main(String[] args){
        //服务类
        serverboo serverBootstrap=new ServerBootstrap();

        ExecutorService boos= Executors.newCachedThreadPool();
        ExecutorService worker=Executors.newCachedThreadPool();

        //设置niosocker工厂
        serverBootstrap.
    }
}
