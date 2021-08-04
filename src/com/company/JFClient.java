package com.company;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class JFClient {

    private int portNo = 0;
    private String hostName = null;
    private Socket serverSocket;


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

    public int sendRequestType(String request){
        try {
            OutputStreamWriter toSend = new OutputStreamWriter(this.serverSocket.getOutputStream());
            toSend.write("JFile " + request + " ");
            toSend.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 1;
    }


    public int sendMessage(String arg){
        try {
            this.serverSocket = new Socket(this.hostName, this.portNo);
            OutputStreamWriter tosend = new OutputStreamWriter(serverSocket.getOutputStream());
            tosend.write(arg, 0, arg.length());
            tosend.flush();

        } catch (UnknownHostException e) {
            e.printStackTrace();
            return -1;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        return 1;

    }
}
