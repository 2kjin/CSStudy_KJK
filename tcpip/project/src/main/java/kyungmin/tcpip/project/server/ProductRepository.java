package kyungmin.tcpip.project.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import kyungmin.tcpip.project.domain.Product;
import kyungmin.tcpip.project.exception.NoProductException;
import kyungmin.tcpip.project.server.Item;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {

  private static final List<Product> store = new ArrayList<>();

  //C
  public void save(Product product){
    store.add(product);
  }

  //R
  public Optional<Product> findByNo(int no){
    return Optional.ofNullable(store.get(no));
  }

  public List<Product> findAll(){
    return store;
  }

  //U
  public void update(Product updateProduct){
    Product findProduct = findByNo(updateProduct.getNo()).orElseThrow(()->new NoProductException("요청한 no 번호는 찾을 수 없습니다"));
    findProduct.setName(updateProduct.getName());
    findProduct.setPrice(updateProduct.getPrice());
    findProduct.setStock(updateProduct.getStock());
  }

  //D
  public void delete(Product deleteProduct){
    Product findProduct = findByNo(deleteProduct.getNo()).orElseThrow(() -> new NoProductException("요청한 no 번호는 찾을 수 없습니다"));
    store.remove(findProduct);
  }

}
