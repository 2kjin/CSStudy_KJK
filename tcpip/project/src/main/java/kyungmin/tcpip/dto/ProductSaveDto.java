package kyungmin.tcpip.dto;

public class ProductSaveDto {

  private int menuNum;
  private int no;
  private String name;
  private int price;
  private int stock;

  public ProductSaveDto(int menuNum, int no, String name, int price, int stock) {
    this.menuNum = menuNum;
    this.no = no;
    this.name = name;
    this.price = price;
    this.stock = stock;
  }

  public ProductSaveDto() {
  }

  public int getMenuNum() {
    return menuNum;
  }

  public void setMenuNum(int menuNum) {
    this.menuNum = menuNum;
  }

  public int getNo() {
    return no;
  }

  public void setNo(int no) {
    this.no = no;
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

  public int getStock() {
    return stock;
  }

  public void setStock(int stock) {
    this.stock = stock;
  }
}
