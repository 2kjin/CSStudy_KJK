package tcpip.practice.server;

public class Item {

  private Long id;

  private String name;
  private int price;
  private int stockQuantity;

  @Override
  public String toString() {
    return getId() + "\t\t" + getName() + "\t\t\t\t\t\t\t\t" + getPrice() + "\t\t\t" + getStockQuantity();
  }

  public Item(String name, int price, int stockQuantity) {
    this.name = name;
    this.price = price;
    this.stockQuantity = stockQuantity;
  }

  public Item() {
  }

  public Item(Long id, String name, int price, int stockQuantity) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.stockQuantity = stockQuantity;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public int getStockQuantity() {
    return stockQuantity;
  }

  public void setStockQuantity(int stockQuantity) {
    this.stockQuantity = stockQuantity;
  }
}
