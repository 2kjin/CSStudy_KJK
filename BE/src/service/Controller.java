package service;

import domain.Product;

import java.util.Scanner;

public class Controller {

    Scanner sc = new Scanner(System.in);
    Product p = new Product();

    //    p.set(sc.nextLine());
    String[][] ProductList = new String[100][3];
    int productNumber = 0;

    public void getProductList(){
        System.out.println("---------------------------------------------------------");
        System.out.println("no       name                     price         stock    ");
        System.out.println("---------------------------------------------------------");

        System.out.println("---------------------------------------------------------");
        System.out.print("메뉴 : 1. Create | 2. Update | 3. Delete | 4. Exit");
        System.out.print("선택 : ");
    }

}