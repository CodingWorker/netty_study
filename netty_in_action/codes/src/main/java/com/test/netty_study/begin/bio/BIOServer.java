package com.test.netty_study.begin.bio;

import org.omg.PortableInterceptor.ServerRequestInfo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by IntelliJ IDEA
 * Project: Netty-In-Action-Demo
 * User: DaiYan
 * Date: 2017/10/25
 */
public class BIOServer {
    static int serverPort=1010;
    public static void startServer()throws Exception{
        ServerSocket serverSocket=new ServerSocket(serverPort);
        System.out.println("server start!!!");
        Socket clientSocket;
        String req="";
        String resp="ok";
        String line=null;
        while(true){
            clientSocket=serverSocket.accept();
            BufferedReader bfr=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter printWriter=new PrintWriter(clientSocket.getOutputStream());
            while((line=bfr.readLine())!=null){
                if(line.equalsIgnoreCase("done")) break;
                req+=line;
            }

            printWriter.write(resp);
            bfr.close();
            printWriter.close();
            clientSocket.close();
        }
    }

    public static void main(String[] args) throws Exception{
        startServer();
    }
}
