package kyungmin.tcpip;

import static java.util.concurrent.TimeUnit.SECONDS;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import kyungmin.tcpip.dto.ProductClientDto;
import kyungmin.tcpip.dto.ProductServerDto;
import kyungmin.tcpip.project.exception.NoProductException;

public class ProductServer {


  private final ObjectMapper objectMapper = new ObjectMapper();
  LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(100);
  ThreadPoolExecutor executorService = new ThreadPoolExecutor(10, 10, 10, SECONDS, queue);


  /**
   * 저장
   */
  public void save(String saveJson) throws JsonProcessingException {
    try {
      ProductClientDto productClientDto = objectMapper.readValue(saveJson,
          ProductClientDto.class); //DTO는 메뉴 넘버 포함 , 엔티티는 미포함
      //executorService.execute(new Task());
      Product saveProduct = new Product(productClientDto.getNo(), productClientDto.getName(),
          productClientDto.getPrice(), productClientDto.getStock());
      store.add(saveProduct);
      returnResultToClient("success"); //리포지토리에서 예외 던지면 여기서 success , fail로 예외처리하면 끝
    }catch (Exception e){
      returnResultToClient("fail");
    }

  }



  /**
   * 단 건 조회
   */
  public Product findByNo(int no) {
    return Optional.ofNullable(store.get(no))
        .orElseThrow(() -> new NoProductException("no 값을 다시 확인해주세요"));
  }


  /**
   * 단 건 수정
   */
  public void update(String updateJson) throws JsonProcessingException {
    try {
      ProductClientDto updateDto = objectMapper.readValue(updateJson, ProductClientDto.class);
      Product findProduct = findByNo(updateDto.getNo());
      findProduct.setName(updateDto.getName());
      findProduct.setPrice(updateDto.getPrice());
      findProduct.setStock(updateDto.getStock());
      returnResultToClient("success");
    }catch (Exception e){
      returnResultToClient("fail");
    }
  }


  /**
   * 삭제
   */
  public void delete(String deleteJson) throws JsonProcessingException {
    try {
      ProductClientDto deleteDto = objectMapper.readValue(deleteJson, ProductClientDto.class);
      Product findProduct = findByNo(deleteDto.getNo());
      store.remove(findProduct);
      returnResultToClient("success");
    }catch (Exception e){
      returnResultToClient("fail");

    }
  }

  private void returnResultToClient(String success) throws JsonProcessingException {
    ProductServerDto productServerDto = new ProductServerDto(success, (Product[]) findAll().toArray());
    String successSaveJson = objectMapper.writeValueAsString(productServerDto);
    ProductClient.findProductsAndPrintMenu(successSaveJson);
  }
  /**
   * 전체 조회
   */
  public List<Product> findAll() {
    return store;
  }


  /*private static class Task implements Runnable {

    @Override
    public void run() {
      try {
        //쓰레드 번호를 출력해 준다.
        System.out.println(Thread.currentThread().getName());
        SECONDS.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }*/

}
