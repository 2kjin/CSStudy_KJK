package kyungmin.tcpip;

import static java.util.concurrent.TimeUnit.SECONDS;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import kyungmin.tcpip.dto.ProductClientDto;
import kyungmin.tcpip.dto.ProductServerDto;
import kyungmin.tcpip.project.exception.NoProductException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
  private final ObjectMapper objectMapper;

  private final ProductRepository productRepository;
  //LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(100);


  //ThreadPoolExecutor executorService = new ThreadPoolExecutor(10, 10, 10, SECONDS, queue);



  /**
   * 저장
   */
  public void saveProduct(String saveJson) throws JsonProcessingException {
    try {
      ProductClientDto productClientDto = objectMapper.readValue(saveJson, ProductClientDto.class); //DTO는 메뉴 넘버 포함 , 엔티티는 미포함
      Product saveProduct = new Product(productClientDto.getNo(), productClientDto.getName(), productClientDto.getPrice(), productClientDto.getStock());
      productRepository.save(saveProduct);
      returnResultToClient("success");
    }catch (Exception e){
      returnResultToClient("fail");
    }

  }
  /**
   * 단 건 조회
   */
  public Product findOne(int no) {
      return productRepository.findByNo(no).orElseThrow(()->new NoProductException("요청한 no 번호는 찾을 수 없습니다"));
  }


  /**
   * 수정
   */
  public void updateProduct(String updateJson) throws JsonProcessingException {
    try {
      Product findProduct = getProduct(updateJson);
      productRepository.update(findProduct);
      returnResultToClient("success");
    }catch (Exception e){
      returnResultToClient("fail");
    }
  }


  /**
   * 삭제
   */
  public void deleteProduct(String deleteJson) throws JsonProcessingException {
    try {
      Product findProduct = getProduct(deleteJson);
      productRepository.delete(findProduct);
      returnResultToClient("success");
    }catch (Exception e){
      returnResultToClient("fail");
    }
  }

  private Product getProduct(String deleteJson) throws JsonProcessingException {
    ProductClientDto deleteDto = objectMapper.readValue(deleteJson, ProductClientDto.class);
    return findOne(deleteDto.getNo());
  }

  public void returnResultToClient(String success) throws JsonProcessingException {
    ProductServerDto productServerDto = new ProductServerDto(success, (Product[]) findProducts().toArray());
    String successSaveJson = objectMapper.writeValueAsString(productServerDto);
    ProductClient.findProductsAndPrintMenu(successSaveJson);

  }
  /**
   * 전체 조회
   */
  public List<Product> findProducts() {
    return productRepository.findAll();
  }

  public void printMenu() {
    ProductServerDto productServerDto = objectMapper.readValue(json, ProductServerDto.class);
    String status = productServerDto.getStatus();
    Product[] products = productServerDto.getProducts();

    if (status.equals("fail")){
      System.out.println("=".repeat(20)+"Warning!!!!"+"=".repeat(20));
      System.out.println("방금 수행하신 명령에 오류가 있었기 때문에 기존 데이터를 종료합니다");
      System.out.println("=".repeat(50));
      System.out.println();
      return;
    }

    System.out.println("-".repeat(50));
    System.out.println("no\t\tname\t\t\t\t\t\t\t\tprice\t\t\t\tstock");
    System.out.println("-".repeat(50));

    for (Product product : products) {
      System.out.println(product);
    }
    System.out.println("-".repeat(50));
    System.out.println("메뉴: 1. Create | 2. Update | 3.Delete | 4.Exit");
    System.out.print("선택 : ");

  }
}
