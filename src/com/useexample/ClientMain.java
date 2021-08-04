package com.useexample;

import com.company.JFClient;

public class ClientMain {

    public static void main(String[] args) {
        System.out.println("ok");

        JFClient ok = new JFClient();
        ok.setHostName("localhost");
        ok.setPort(2343);

        ok.sendMessage("ok so you are going to be fine");
        ok.sendRequestType("ok");

    }
}
