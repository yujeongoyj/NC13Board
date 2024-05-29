package viewer;

import connector.ConnectionMaker;
import model.UserDTO;
import util.ScannerUtil;

import java.sql.Connection;
import java.util.Scanner;

public class BoardViewer {
    private Connection connection;
    private final Scanner SCANNER;
    private ConnectionMaker connectionMaker;
    private UserDTO logIn;

    public BoardViewer(ConnectionMaker connectionMaker, Scanner scanner, UserDTO logIn) {
        this.connectionMaker = connectionMaker;
        SCANNER = scanner;
        this.logIn = logIn;
    }

    public void showMenu() {
        String message = "1. 글 작성하기 2. 글 목록보기 3. 뒤로가기";
        int userChoice = ScannerUtil.nextInt(SCANNER, message);
        while (true) {
            if (userChoice == 1) {

            } else if (userChoice == 2) {

            } else if (userChoice == 3) {
                System.out.println("뒤로 갑니다");
                break;
            }
        }

    }
}
