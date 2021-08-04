package com.company;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class JFileServer {

    private int portNo = 0;
    private String hostName = null;
    private ServerSocket JFSocket;
    private Socket JFclient;


    public static void hello(){
        System.out.println("hello");
    }

    public int setPort(int portNo){
        this.portNo = portNo;
        return 1;
    }

    public int getPort(){
        return this.portNo;
    }

    public int setHostName(String hostName){
        this.hostName = hostName;
        return 1;
    }

    public String getHostName(){
        if(this.hostName ==null){
            return "Host Name not Set";
        }
        return this.hostName;
    }

    public void processMessage(){
        byte buffer[] = new byte[1000];
        int bytesread = 0;
        int totalbytesread = 0;
        StringBuilder message = new StringBuilder();
        try {
            while ((bytesread = this.JFclient.getInputStream().read(buffer, totalbytesread, buffer.length - totalbytesread)) != -1) {
                totalbytesread += bytesread;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //slice the buffer to avoid priting null chars
        System.out.println(new String(Arrays.copyOfRange(buffer, 0, totalbytesread), StandardCharsets.UTF_8));

    }



    public void server() {
        while(true) {
            try {
                this.JFSocket = new ServerSocket(this.portNo);
                this.JFSocket.setReuseAddress(true);
                this.JFclient = JFSocket.accept();
                this.processMessage();
                JFSocket.close();
                JFclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
