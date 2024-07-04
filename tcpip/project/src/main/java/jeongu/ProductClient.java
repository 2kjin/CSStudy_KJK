package jeongu;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductClient {
  private static final String SERVER_IP = "127.0.0.1";
  private static final int SERVER_PORT = 8080;
  static ObjectMapper mapper = new ObjectMapper();
  private static final Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) {
    new ProductClient().start();
  }

  public void start(){
    Socket socket = null;

    try {
      // 서버 연결
      socket = new Socket(SERVER_IP, SERVER_PORT);


      System.out.println("서버에 연결되었습니다.");

      while (true) {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String responseJson = in.readLine();

        Response response = mapper.readValue(responseJson, Response.class);

        String status = response.getStatus();
        if (status.equals("fail")) {
          System.out.println("예기치 못한 오류가 발생했습니다.");
          break;
        }

        ArrayList<Product> arrayList = response.getData();

        productList(arrayList);
        showMenu();

        Product product;

        int menu = scanner.nextInt();

        if (menu == 1){
          product = createMenu();
        } else if (menu == 2) {
          product = updateMenu();
        } else if (menu == 3) {
          product = deleteMenu();
        } else if (menu == 4) {
          break;
        } else {
          System.out.println("잘못된 선택입니다.");
          continue;
        }

        PrintWriter out = new PrintWriter(socket.getOutputStream());

        Request request = new Request(menu, product);
        String requestJson = mapper.writeValueAsString(request);

        out.println(requestJson);
        out.flush();

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
