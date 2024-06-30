package service;

import domain.Product;

import java.util.Scanner;

public class Controller {

    Scanner sc = new Scanner(System.in);
    Product p = new Product();

    String[][] ProductList = new String[100][3];
    int productNumber = 0;

    public void getProductList(){
        System.out.println("---------------------------------------------------------");
        System.out.println("no       name                     price         stock    ");
        System.out.println("---------------------------------------------------------");
        for (int i = 0; i < productNumber; i++) {
            for (int j = 0; j < ProductList[i].length; j++) {
                System.out.print(ProductList[i][j] + "       ");
            }
            System.out.println();
        }
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
        System.out.println("[ 상품 생성 ]");
        for(int i = 0; i < ProductList.length; i++){
            if (productNumber == i){
                System.out.print("상품 이름 : ");
                ProductList[i][0] = sc.next();
                System.out.print("상품 가격 : ");
                ProductList[i][1] = sc.next();
                System.out.print("상품 재고 : ");
                ProductList[i][2] = sc.next();
            }
        }
        productNumber++;
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