package jeongu;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import lombok.Data;

public class ProductClient {

  static Scanner scanner = new Scanner(System.in);
  static final private String serverIp = "127.0.0.1";
  static Socket socket = null;
  static ObjectMapper mapper = new ObjectMapper();

  public static void main(String[] args) {

    BufferedReader br = null;
    PrintWriter pw = null;
    boolean quit = false;

    try {
      // 서버 연결
      socket = new Socket(serverIp, 8080);

      System.out.println("서버에 연결되었습니다.");

      while (!quit) {

        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String responseJson = br.readLine();

        Response response = mapper.readValue(responseJson, Response.class);

        String status = response.getStatus();
        if (status.equals("fail")) {
          System.out.println("잘못된 데이터를 받아왔습니다.");
        }
        ArrayList<Product> arrayList = response.getData();

        productList(arrayList);
        showMenu();

        Product product = new Product();

        int menu = scanner.nextInt();

        if (menu == 1){
          product = createMenu();
        } else if (menu == 2) {
          product = updateMenu();
        } else if (menu == 3) {
          product = deleteMenu();
        } else if (menu == 4) {
          quit = true;
        } else {
          System.out.println("잘못된 선택입니다.");
          continue;
        }

        pw = new PrintWriter(socket.getOutputStream());

        Request request = new Request(menu, product);
        String requestJson = mapper.writeValueAsString(request);

        pw.println(requestJson);
        pw.flush();

      }

    } catch (IOException e) {
      System.out.println(e);
    } finally {
      try {
        // 소켓 닫기
        if (socket != null) {
          socket.close();
        }
      } catch (IOException e) {
        System.out.println(e);
      }
    }

  }

//  @Data
//  private static class Request{
//    private int menu;
//    private Product data;
//
//    public Request(int menu, Product data) {
//      this.menu = menu;
//      this.data = data;
//    }
//  }


  private static Product createMenu() {
    Product product = new Product();
    System.out.println("[상품 생성]");
    product.setNo(0);
    System.out.print("상품 이름:");
    product.setName(scanner.next());
    System.out.print("상품 가격:");
    product.setPrice(scanner.nextInt());
    System.out.print("상품 재고:");
    product.setStock(scanner.nextInt());
    return product;
  }

  private static Product updateMenu() {
    Product product = new Product();
    System.out.println("[상품 수정]");
    System.out.print("상품 번호:");
    product.setNo(scanner.nextInt());
    System.out.print("이름 변경:");
    product.setName(scanner.next());
    System.out.print("가격 변경:");
    product.setPrice(scanner.nextInt());
    System.out.print("재고 변경:");
    product.setStock(scanner.nextInt());
    return product;
  }

  private static Product deleteMenu() {
    Product product = new Product();
    System.out.println("[상품 수정]");
    System.out.print("상품 번호:");
    product.setNo(scanner.nextInt());
    return product;
  }


  private static void productList(ArrayList<Product> products) {
    System.out.println("------------------------------------------");
    System.out.println("no\t\tname\t\t\t\t\t\t\tprice\t\t\tstock");
    System.out.println("------------------------------------------");
    for (Product product : products) {
      System.out.println(
          product.getNo() + "\t\t" + product.getName() + "\t\t\t\t\t\t\t" + product.getPrice()
              + "\t\t\t" + product.getStock());
    }
    System.out.println("------------------------------------------");
  }

  private static void showMenu() {
    System.out.println("메뉴: 1. Creat | 2. Update | 3. Delete | 4. Exit");
    System.out.print("선택: ");
  }


}
