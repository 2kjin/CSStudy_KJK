package TCP;

import service.Controller;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Controller controller = new Controller();

        while (true){
//            controller.showMenu();
            int num = sc.nextInt();
//            controller.choiceMenu(num);
        }
    }
}