package jeongu;

//import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

public class ProductClient {

  static Scanner scanner = new Scanner(System.in);
  static final private String serverIp = "127.0.0.1";

  public static void main(String[] args) {

//    ObjectMapper mapper = null;
    BufferedReader br = null;
    PrintWriter pw = null;
    Socket socket = null;

    boolean quit = false;
    try {
      // 서버 연결
      socket = new Socket(serverIp, 8080);

      System.out.println("서버에 연결되었습니다.");
      System.out.println("[클라이언트의 채팅창]");

      while (!quit) {
        productList();
        showMenu();

        Product product = new Product();

        int menu = scanner.nextInt();
        switch (menu) {
          case 1 -> product = createMenu();
          case 2 -> product = updateMenu();
          case 3 -> product = deleteMenu();
          case 4 -> quit = true;
          default -> System.out.println("잘못된 선택입니다.");
        }

//        mapper = new ObjectMapper();
//        br = new BufferedReader(new InputStreamReader(System.in));
//        pw = new PrintWriter(socket.getOutputStream());

        Request request = new Request(menu, product);
//        String requestJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request);
//        System.out.println(requestJson);

      }

    } catch (IOException e) {
      System.out.println(e);
    } finally {
      try {
        // 소켓 닫기
        if (socket != null) {
          socket.close();
        }
        if (br != null) {
          br.close();
        }
        if (pw != null) {
          pw.close();
        }
      } catch (IOException e) {
        System.out.println(e);
      }
    }


  }

  private static class Request{
    private int menu;
    private Product product;

    public Request(int menu, Product data) {
      this.menu = menu;
      this.product = data;
    }

    @Override
    public String toString() {
      return "{" +
          "menu:" + menu +
          ", data:" + product.toString() +
          '}';
    }
  }

  private static class Response{
    private String status;
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


  private static void productList() {
    System.out.println("------------------------------------------");
    System.out.println("no\t\tname\t\t\t\t\t\t\tprice\t\t\tstock");
    System.out.println("------------------------------------------");
    System.out.println("------------------------------------------");
  }

  private static void showMenu() {
    System.out.println("메뉴: 1. Creat | 2. Update | 3. Delete | 4. Exit");
    System.out.print("선택: ");
  }


}
