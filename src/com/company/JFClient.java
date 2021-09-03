package com.company;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class JFClient {

    private int portNo = 0;
    private String hostName = null;
    private Socket serverSocket;
    private String clientDirectory;


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

    private int startSocket(){
        try {
            this.serverSocket = new Socket(this.hostName, this.portNo);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public int setDirectory(String directory){
        this.clientDirectory = directory;
        return 1;
    }

    private int sendRequestType(String request, String fileName){
        try {
            OutputStreamWriter toSend = new OutputStreamWriter(this.serverSocket.getOutputStream());
            toSend.write("JTransfer " + request + " " + fileName + " " +  "/r/t/t/r");
            toSend.flush();
            //toSend.close();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        System.out.println("request sent");
        return 1;
    }

    public int getFile(String fileName){
        startSocket();
        sendRequestType("GET", fileName);
        return 1;
    }

    public int pushFile(String fileName){
        startSocket();
        sendRequestType("PUSH", fileName);
        return 1;
    }

    public int receiveFile(String filename){
        //startSocket();
        System.out.println("receiving");

        byte buffer[] = new byte[4096];
        int bytesread = 0;
        long totalBytesWritten = 0;
        try {
            File fileToSave = new File(this.clientDirectory+"/"+filename);
            FileOutputStream fileOutput = new FileOutputStream(fileToSave);
            while(totalBytesWritten<1781137) {
                System.out.println("waiting to bread ");
                bytesread = this.serverSocket.getInputStream().read(buffer, 0, 4096);
                    System.out.println("reading byts please");
                    fileOutput.write(buffer, 0, bytesread);
                    totalBytesWritten += bytesread;
                    System.out.println(totalBytesWritten);

            }

            fileOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("outside of receivng");
        return 1;
    }

}
