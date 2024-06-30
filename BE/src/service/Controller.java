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

    public void getMenu(int num){
        switch (num){
            case 1:
                productCreate();
                break;
            case 2:
                productUpdate();
                break;
            case 3:
                productDelete();
                break;
            case 4:
                exit();
                break;
            default:
                System.out.println("없는 메뉴 입니다. 다시 입력해 주세요");
                break;
        }
    }

    public void productCreate() {
    }

    public void productUpdate() {
    }

    public void productDelete() {
    }

    public void exit(){
        System.out.println("프로그램 종료");
        System.exit(0);
    }

}