package tcpip;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import tcpip.dto.ProductSaveDto;

public class ProductClient {


  private static final ProductServer productServer = new ProductServer();

  private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

  private static final ObjectMapper objectMapper = new ObjectMapper();
  private static int sequence = 0;

  public static void main(String[] args) throws IOException {

    while (true){
      int menuNum = Integer.parseInt(br.readLine());
      switch (menuNum){
        case 1:
          System.out.println("[상품 생성]");
          System.out.print("상품 이름: ");
          String productName = br.readLine();
          System.out.print("상품 가격: ");
          int price = Integer.parseInt(br.readLine());
          System.out.print("상품 재고: ");
          int stockQuantity = Integer.parseInt(br.readLine());
          ProductSaveDto productSaveDto = new ProductSaveDto(menuNum , ++sequence , productName , price , stockQuantity);
          String saveJson = objectMapper.writeValueAsString(productSaveDto);
          productServer.save(saveJson);

          //소켓 : 클라이언트 및 서버 단말 , 전
        case 2:


        case 3:

        case 4:

      }
    }




  }



}
