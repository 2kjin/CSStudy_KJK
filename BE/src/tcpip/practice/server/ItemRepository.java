package tcpip.practice.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ItemRepository {

  private static final Map<Long, Item> store = new HashMap<>();
  private static Long sequence = 0L;
  public void save(Item item){
    item.setId(++sequence);
    store.put(item.getId(), item);
  }

  public Optional<Item> findById(Long id){
    return Optional.ofNullable(store.get(id));
  }

  public List<Item> findAll(){
    return new ArrayList<>(store.values());
  }

  public void update(Item updateItem){
    Item findItem = findById(updateItem.getId()).orElseThrow();
    findItem.setName(updateItem.getName());
    findItem.setPrice(updateItem.getPrice());
    findItem.setStockQuantity(updateItem.getStockQuantity());
  }

  public void delete(Item item){
    store.remove(item.getId() , item);
  }

}
