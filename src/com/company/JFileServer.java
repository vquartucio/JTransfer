package com.company;

import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class JFileServer {

    private int portNo = 0;
    private String hostName = null;
    private ServerSocket JFSocket;
    private Socket JFclient;
    private String serverDirectory;


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

    public int setDirectory(String directory){
        this.serverDirectory = directory;
        return 1;
    }

    public String getDirectory(){
        return this.serverDirectory;
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

    public int listenForRequest() {
        while (true) {
            byte buffer[] = new byte[500];
            byte endOfRequest[] = {'/', 'r', '/', 't', '/', 't', '/', 'r'};
            int bytesread = 0;
            int totalbytesread = 0;
            boolean found = false;
            StringBuilder message = new StringBuilder();
            System.out.println("listening for request");
            while (!found) {
                try {
                    System.out.println("waiting");
                    while ((bytesread = this.JFclient.getInputStream().read(buffer, totalbytesread, buffer.length - totalbytesread)) != -1) {
                        totalbytesread += bytesread;
                        int j = 0;
                        for (int i = 0; i < totalbytesread; i++) {
                            if (buffer[i] == endOfRequest[j]) {
                                j++;
                            } else {
                                j = 0;
                            }
                            if (j == endOfRequest.length - 1) {
                                System.out.println("contains");
                                found = true;
                                return 1;
                            }
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                //slice the buffer to avoid priting null chars
                System.out.println(new String(Arrays.copyOfRange(buffer, 0, totalbytesread), StandardCharsets.UTF_8));
            }
        }
    }



    public long sendData(byte dataToSend[], long bytesToSend){
        try {

            DataOutputStream toSend = new DataOutputStream(this.JFclient.getOutputStream());
            toSend.write(dataToSend,0,(int)bytesToSend);
            toSend.flush();
            //OutputStreamWriter toSend = new OutputStreamWriter(this.JFclient.getOutputStream());
            //toSend.write("JTransfer");
            //toSend.flush();

        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        System.out.println("sent bytes");
        return bytesToSend;
    }

    public int sendFile(String fileName){
        File fileToSend = new File(this.serverDirectory+"/"+fileName);
        System.out.println(this.serverDirectory+"/"+fileName);
        byte fileBuffer[] = new byte[4096];
        long fileSize = fileToSend.length();
        System.out.println(fileSize);
        long bytes = 0;
        long totalBytesRead = 0;
        try {
            FileInputStream fileInput = new FileInputStream(fileToSend);
            while(totalBytesRead<fileSize){
                long bytesToRead = Long.min(4096,fileSize-totalBytesRead);
                System.out.println(bytesToRead);
                bytes = fileInput.readNBytes(fileBuffer,0, (int)bytesToRead);
                long byteSent = sendData(fileBuffer, bytes);
                if(byteSent!= bytes){
                    return -600;
                }
                totalBytesRead+=byteSent;
            }
        } catch (FileNotFoundException e) {
            return -400;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 1;
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
    private int sendRequestType(String request, String fileName){
        try {
            OutputStreamWriter toSend = new OutputStreamWriter(this.JFclient.getOutputStream());
            toSend.write("JTransfer hahaha " + "/r/t/t/r");
            toSend.flush();
            toSend.close();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        System.out.println("request sent");
        return 1;
    }

    public void server() {
            try {
                this.JFSocket = new ServerSocket(this.portNo);
                this.JFSocket.setReuseAddress(true);
                this.JFclient = JFSocket.accept();
                System.out.println("Accepted and Connected");
                this.listenForRequest();
                System.out.println("reiceved request");
                this.sendFile("boat.png");
                System.out.println("server sent");
                while(true){

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

    }
}
