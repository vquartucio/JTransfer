package com.useexample;

import com.company.JFClient;

import java.io.IOException;

public class ClientMain {

    public static void main(String[] args) {
        System.out.println("ok");

        JFClient ok = new JFClient();
        ok.setHostName("localhost");
        ok.setPort(2345);
        ok.setDirectory("ExampleClientDirectory");
        ok.getFile("boat.png");
        ok.receiveFile("boat.png");


    }
}
