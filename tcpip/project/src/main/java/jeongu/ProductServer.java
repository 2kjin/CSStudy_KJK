package jeongu;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import lombok.Data;

public class ProductServer {
  static int productNumber = 0;
  static ArrayList<Product> productList = new ArrayList();

  public static void main(String[] args) {
    ServerSocket serverSocket = null;
    Socket socket = null;
    ObjectMapper mapper = new ObjectMapper();
    String status = "success";

    try {
      serverSocket = new ServerSocket(8080);
      socket = serverSocket.accept();
      while (true) {
        System.out.println("요청을 기다리는 중...");

        // 클라이언트에게 리스트 보내기
        PrintWriter pw = new PrintWriter(socket.getOutputStream());

        Response response = new Response(status, productList);
        String responseJson = mapper.writeValueAsString(response);

        pw.println(responseJson);
        pw.flush();

        // 클라이언트 요청 확인
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String requestJson = br.readLine();

        Request request = mapper.readValue(requestJson, Request.class);

        int selectedMenu = request.getMenu();
        Product receivedProduct = request.getData();

        if (selectedMenu == 1){
          saveProduct(receivedProduct);
        } else if (selectedMenu == 2) {
          updateProduct(receivedProduct);
        } else if (selectedMenu == 3) {
          deleteProduct(receivedProduct);
        } else if (selectedMenu == 4) {
          System.out.println("클라이언트가 연결을 끊었습니다.");
          continue;
        } else {
          continue;
        }

        System.out.println(productList);

      }


    } catch (Exception e) {
      e.printStackTrace();
    }
  }


//  class SocketClient {
//
//  }
  private static void saveProduct(Product product) {
    productNumber++;
    product.setNo(productNumber);
    productList.add(product);
  }

  private static void updateProduct(Product product) {
    for (Product target : productList) {
      if (target.getNo() == product.getNo()) {
        target.setName(product.getName());
        target.setPrice(product.getPrice());
        target.setStock(product.getStock());
      }
    }
  }

  private static void deleteProduct(Product product) {
    for (Product target : productList) {
      if (target.getNo() == product.getNo()) {
        productList.remove(target);
        break;
      }
    }
  }


}


