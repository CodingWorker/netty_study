package com.test.netty_study.begin.bio;

import java.io.*;
import java.net.Socket;

/**
 * Created by IntelliJ IDEA
 * Project: Netty-In-Action-Demo
 * User: DaiYan
 * Date: 2017/10/25
 */
public class BIOClient {
    private static void start()throws Exception{
        Socket socket=new Socket("localhost",BIOServer.serverPort);
        System.out.println("client start!!!");
        PrintWriter printWriter=new PrintWriter(socket.getOutputStream());
        printWriter.write("hello server");
        printWriter.flush();
    }

    public static void main(String[] args) throws Exception{
        start();
    }
}
