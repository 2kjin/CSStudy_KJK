package tcpip.practice.server;

import java.util.List;

public class ItemService {

  private final ItemRepository itemRepository;
  public ItemService(){
    itemRepository = new ItemRepository();
  }

  public void printMenu(){
    List<Item> findItems = itemRepository.findAll();
    System.out.println("-".repeat(50));
    System.out.println("no\t\tname\t\t\t\t\t\t\t\tprice\t\t\t\tstock");
    System.out.println("-".repeat(50));
    for (Item findItem : findItems) {
      System.out.println(findItem);
    }
    System.out.println("-".repeat(50));
    System.out.println("메뉴: 1. Create | 2. Update | 3.Delete | 4.Exit");
    System.out.print("선택 : ");
  }

  public void saveItem(Item item){
    itemRepository.save(item);
  }


  //update를 하는데 만약 id가 없으면 예외
  public void updateItem(Item item){
    itemRepository.update(item);

  }

  public Item findItem(Long id){
    return itemRepository.findById(id).orElseThrow(()->new IllegalStateException("잘못된 상품 번호입니다. 다시 한 번 확인해주세요"));
  }

  public void deleteItem(Item item){
    itemRepository.delete(item);
  }





}
