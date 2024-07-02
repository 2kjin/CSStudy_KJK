package kyungmin.tcpip.project.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import kyungmin.tcpip.project.server.Item;
import kyungmin.tcpip.project.server.ItemService;


public class ItemController {
  private static final ItemService itemService = new ItemService();
  private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));



  public static void main(String[] args) throws IOException {

    while (true){
      itemService.printMenu();
      int menuNum = Integer.parseInt(br.readLine());
      switch (menuNum){
        case 1:
          requestSave();
          continue;

        case 2:
          requestUpdate();
          continue;

        case 3:
          requestDelete();
          continue;
        case 4:
          return;
      }
    }

  }

  private static void requestSave() throws IOException {
    System.out.println("[상품 생성]");
    System.out.print("상품 이름: ");
    String itemName = br.readLine();
    System.out.print("상품 가격: ");
    int price = Integer.parseInt(br.readLine());
    System.out.print("상품 재고: ");
    int stockQuantity = Integer.parseInt(br.readLine());
    Item item = new Item(itemName, price, stockQuantity);
    itemService.saveItem(item);
  }

  private static void requestDelete() throws IOException {
    System.out.print("상품 번호: ");
    long deleteId = Long.parseLong(br.readLine());
    try {
      Item findItem = itemService.findItem(deleteId);
      itemService.deleteItem(findItem);
    }catch (IllegalStateException e) {
      System.out.println(e.getMessage());
    }
  }

  private static void requestUpdate() throws IOException {
      System.out.println("[상품 수정]");
      System.out.print("상품 번호: ");
      long updateId = Long.parseLong(br.readLine());
      try{
        Item findItem = itemService.findItem(updateId);
        System.out.print("이름 변경: ");
        findItem.setName(br.readLine());
        System.out.print("가격 변경: ");
        findItem.setPrice(Integer.parseInt(br.readLine()));
        System.out.print("재고 변경: ");
        findItem.setStockQuantity(Integer.parseInt(br.readLine()));
        itemService.updateItem(findItem);

      }catch (IllegalStateException e){
        System.out.println(e.getMessage());
      }

    }
}







