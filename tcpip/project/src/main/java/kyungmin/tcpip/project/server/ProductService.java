package kyungmin.tcpip.project.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import kyungmin.tcpip.project.client.ProductClient;
import kyungmin.tcpip.project.domain.Product;
import kyungmin.tcpip.project.dto.ProductClientDto;
import kyungmin.tcpip.project.dto.ProductServerDto;
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
  
}
