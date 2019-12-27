package com.company;

import java.io.*;
import java.net.*;

public class UDPServer {

    public static void main(String[] args) throws Exception {

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        byte[] receiveData = new byte[1024];
        System.out.println("input port: ");
        String sentence = inFromUser.readLine();
        int inPort = Integer.parseInt(sentence);

        DatagramSocket serverSocket = new DatagramSocket(inPort);
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

        serverSocket.receive(receivePacket);
        InetAddress ipAddress = receivePacket.getAddress();
        int outPort = receivePacket.getPort();

        DatagramPacket sendPacket = new DatagramPacket("Connected".getBytes(),"Connected".getBytes().length, ipAddress, outPort);
        serverSocket.send(sendPacket);

        InThread in = new InThread(serverSocket, ipAddress,outPort,new DatagramSocket());
        OutThread out = new OutThread(ipAddress,outPort,new DatagramSocket());
        in.start();
        out.start();

    }

}
