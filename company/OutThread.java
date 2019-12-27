package com.company;

import java.net.*;
import java.io.*;

import static java.lang.Integer.parseInt;

public class OutThread extends  Thread {

    private DatagramSocket socket;
    private int port;
    private InetAddress ip;

    public OutThread(InetAddress ipAddress, int port, DatagramSocket socket){

        this.socket = socket;
        this.port = port;
        this.ip = ipAddress;

    }

    @Override
    public void run(){
        try{
            String name = "Client";
            String sentence;
            while (true) {


                BufferedReader sendMessage = new BufferedReader(new InputStreamReader(System.in));
                System.out.println(name+": ");
                byte[] sendData = new byte[1024];
                sentence = sendMessage.readLine();

                if (sentence.equals("@name")) {
                    System.out.println("input your name: ");
                    name = sendMessage.readLine();
                    DatagramPacket sendPacket = new DatagramPacket("@name".getBytes(),"@name".getBytes().length, ip, port);
                    socket.send(sendPacket);
                    sendPacket = new DatagramPacket(name.getBytes(),name.getBytes().length, ip, port);
                    socket.send(sendPacket);
                    continue;
                }
                if (sentence.equals("@game")) {
                    System.out.println("Make a wish number between: ");

                    while (true) {
                        try {
                            int start = parseInt(sendMessage.readLine());
                            int finish = parseInt(sendMessage.readLine());
                            String sendStart = "" + start;
                            String sendFinish = "" + finish;
                            DatagramPacket sendPacket = new DatagramPacket("@game".getBytes(), "@game".getBytes().length, ip, port);
                            socket.send(sendPacket);
                            sendPacket = new DatagramPacket(sendStart.getBytes(), sendStart.getBytes().length, ip, port);
                            socket.send(sendPacket);
                            sendPacket = new DatagramPacket(sendFinish.getBytes(), sendFinish.getBytes().length, ip, port);
                            socket.send(sendPacket);
                            break;
                        } catch (NumberFormatException ex) {
                            System.out.println("Введите число");
                        }
                    }
                    continue;
                }
                if (sentence.equals("maybe")) {

                    DatagramPacket sendPacket = new DatagramPacket(sentence.getBytes(),sentence.getBytes().length, ip, port);
                    socket.send(sendPacket);
                    continue;
                }
                if (sentence.equals("@quit")) {
                    String end = "Bye";
                    sendData = end.getBytes();
                    DatagramPacket res = new DatagramPacket(sendData, sendData.length, ip, port);
                    socket.send(res);
                    System.exit(0);
                    socket.close();
                }

                sendData = sentence.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ip, port);
                socket.send(sendPacket);
            }
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}