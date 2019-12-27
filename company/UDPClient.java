package com.company;

import java.io.*;
import java.net.*;

public class UDPClient {
    public static void main(String[] args) throws Exception {
        try {
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            byte[] sendData = new byte[1024];
            System.out.println("input port: ");
            String sentence = inFromUser.readLine();
            int port = Integer.parseInt(sentence);
            InetAddress ipAddress;
            DatagramSocket clientSocket = new DatagramSocket();
            while (true) {
                try {
                    System.out.println("input ip: ");
                    String ip = inFromUser.readLine();
                    ipAddress = InetAddress.getByName(ip);
                    break;
                }
                catch (Exception ex) {
                    System.out.println("Try another ip");
                }
            }
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, port);
            clientSocket.send(sendPacket);

            InThread in = new InThread(clientSocket, ipAddress, port, clientSocket);
            OutThread out = new OutThread(ipAddress, port, clientSocket);
            in.start();
            out.start();
            out.join();
        }

        catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}