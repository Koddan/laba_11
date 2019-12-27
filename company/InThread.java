package com.company;

import java.io.IOException;
import java.net.*;


public class InThread extends Thread{

    private DatagramSocket socket;
    private DatagramSocket socket1;
    private int port;
    private InetAddress ip;

    public InThread(DatagramSocket socket, InetAddress ipAddress, int port, DatagramSocket socket1) {
        this.socket = socket;
        this.socket1 = socket1;
        this.port = port;
        this.ip = ipAddress;
    }

    @Override
    public void run() {
        String name = "AnotherClient";
        int number = 0;
        try {

            while (true) {

                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);

                int length = receivePacket.getLength();
                String tmp = new String(receiveData);
                String sentence = tmp.substring(0, length);
                if (sentence.equals("@name")) {
                    byte[] receiveDataName = new byte[1024];
                    DatagramPacket receivePacketName = new DatagramPacket(receiveDataName, receiveDataName.length);
                    socket.receive(receivePacketName);

                    int nameLength = receivePacketName.getLength();
                    String changeNameSentence = new String(receiveDataName);
                    name = changeNameSentence.substring(0, nameLength);
                }
                if (sentence.equals("@game")) {
                    byte[] receiveDataName = new byte[1024];
                    DatagramPacket receivePacketName = new DatagramPacket(receiveDataName, receiveDataName.length);
                    int start;
                    int finish;
                    while (true) {
                        try {
                            socket.receive(receivePacketName);
                            int length1 = receivePacketName.getLength();
                            tmp = new String(receiveDataName);
                            String sentence1 = tmp.substring(0, length1);
                            System.out.println(sentence1);
                            start = Integer.parseInt(sentence1);

                            socket.receive(receivePacketName);
                            length1 = receivePacketName.getLength();
                            tmp = new String(receiveDataName);
                            String sentence2 = tmp.substring(0, length1);
                            System.out.println(sentence2);
                            finish = Integer.parseInt(sentence2);
                            break;
                        } catch (NumberFormatException ex) {
                            System.out.println("Введите число");
                        }
                    }
                    number = Math.toIntExact(Math.round(start + (finish - start) * Math.random()));

                }
                if (sentence.equals("maybe")) {
                    while (true) {
                        byte[] receiveDataName = new byte[1024];
                        DatagramPacket receivePacketName = new DatagramPacket(receiveDataName, receiveDataName.length);
                        socket.receive(receivePacketName);
                        int length1 = receivePacketName.getLength();
                        tmp = new String(receiveDataName);
                        String sentence1 = tmp.substring(0, length1);

                        try {

                            if (sentence1.equals("game over")) {
                                String answer = "you leaved the game";
                                DatagramPacket sendPacket = new DatagramPacket(answer.getBytes(), answer.getBytes().length, ip, port);
                                socket1.send(sendPacket);
                                break;
                            }

                            if (Integer.parseInt(sentence1) > number) {
                                String answer = "Need less";
                                DatagramPacket sendPacket = new DatagramPacket(answer.getBytes(), answer.getBytes().length, ip, port);
                                socket1.send(sendPacket);
                            }
                            if (Integer.parseInt(sentence1) < number) {
                                String answer = "Need more";
                                DatagramPacket sendPacket = new DatagramPacket(answer.getBytes(), answer.getBytes().length, ip, port);
                                socket1.send(sendPacket);
                            }
                            if (Integer.parseInt(sentence1) == number) {
                                String answer = "You won";
                                DatagramPacket sendPacket = new DatagramPacket(answer.getBytes(), answer.getBytes().length, ip, port);
                                socket1.send(sendPacket);
                                break;
                            }

                        }
                        catch (NumberFormatException ex) {
                            String answer = "введите число";
                            DatagramPacket sendPacket = new DatagramPacket(answer.getBytes(), answer.getBytes().length, ip, port);
                            socket1.send(sendPacket);
                        }
                    }
                    continue;

                }


                else {
                    System.out.print(name + ": ");
                    for (int i = 0; i < length; i++) {
                        System.out.print(sentence.charAt(i));
                    }
                    System.out.println();
                }

            }
        }
        catch(IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

}
