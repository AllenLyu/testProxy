package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
	// write your code here
        try {
            ServerSocket socket = new ServerSocket(8082);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print("hello socket");
    }
}
