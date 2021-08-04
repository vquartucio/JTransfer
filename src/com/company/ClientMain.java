package com.company;

public class ClientMain {

    public static void main(String[] args) {
        System.out.println("ok");

        Client ok = new Client();
        ok.setHostName("localhost");
        ok.setPort(2343);
        ok.sendMessage("ok so you are going to be fine");

    }
}
