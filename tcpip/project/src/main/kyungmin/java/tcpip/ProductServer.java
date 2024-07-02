package tcpip;

import static java.util.concurrent.TimeUnit.SECONDS;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.springframework.scheduling.config.Task;
import tcpip.dto.ProductSaveDto;

public class ProductServer {

  private static final List<Product> store = new ArrayList<>();
  private final ObjectMapper objectMapper = new ObjectMapper();
  LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(100);
  ThreadPoolExecutor executorService = new ThreadPoolExecutor(10,10,10, SECONDS ,queue);


  public void save(String product) throws JsonProcessingException {

    ProductSaveDto productSaveDto = objectMapper.readValue(product, ProductSaveDto.class);
    executorService.execute(new Task());
    Product saveProduct = new Product(productSaveDto.getNo() , productSaveDto.getName() , productSaveDto.getPrice() , productSaveDto.getStock());

    store.add(saveProduct);

  }
  public Product findByNo(int no){
    return store.get(no);
  }


  public void update(Product updateProduct){
    Product findProduct = findByNo(updateProduct.getNo());
    findProduct.setName(updateProduct.getName());
    findProduct.setPrice(updateProduct.getPrice());
    findProduct.setStock(updateProduct.getStock());
  }

  public void delete(Product deleteProduct){
    Product findProduct = findByNo(deleteProduct.getNo());
    store.remove(findProduct);
  }

  static class SocketClient{
    private String status;
    private List<Product> productList;

  }

  private static class Task implements Runnable {
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
  }

}
