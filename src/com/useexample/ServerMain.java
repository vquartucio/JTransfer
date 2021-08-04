package com.useexample;


import com.company.JFileServer;

public class ServerMain {

    public static void main(String[] args) {
	// write your code here

        JFileServer.hello();

        JFileServer ok = new JFileServer();
        ok.setPort(2343);
        System.out.println(ok.getPort());

        ok.server();




    }
}
