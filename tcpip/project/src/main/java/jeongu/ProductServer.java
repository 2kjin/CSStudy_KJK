package jeongu;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductServer {
  static ArrayList productList = new ArrayList();

  public static void main(String[] args) {
    ServerSocket serverSocket = null;
    Socket socket = null;

    try {
      serverSocket = new ServerSocket(8080);
      while (true) {
        System.out.println("연결을 기다리는 중...");

        socket = serverSocket.accept();

        Sender sender = new Sender(socket);
        Receiver receiver = new Receiver(socket);

        sender.start();
        receiver.start();
      }


    } catch (Exception e) {
      e.printStackTrace();
    }
  }

//  class SocketClient {
//
//  }
}

class Sender extends Thread {
  Socket socket;
  DataOutputStream out;
  String name;
  Sender(Socket socket) {
    this.socket = socket;
    try {
      out = new DataOutputStream(socket.getOutputStream());
      name = "[" + socket.getInetAddress() + ":" + socket.getPort()+"]";
    } catch (Exception e) {}
  }

  public void run() {
    Scanner scanner = new Scanner(System.in);
    while (out != null) {
      try {
        out.writeUTF(name+scanner.nextLine());
      } catch (IOException e) {}
    }
  }
}

class Receiver extends Thread {
  Socket socket;
  DataInputStream in;

  Receiver(Socket socket) {
    this.socket = socket;
    try {
      in = new DataInputStream(socket.getInputStream());
    } catch (IOException e) {}
  }

  public void run() {
    while (in != null) {
      try {
        System.out.println(in.readUTF());
      } catch (IOException e) {}
    }
  }
}
