package viewer;

import connector.ConnectionMaker;
import connector.MySqlConnectionMaker;
import controller.UserController;
import model.UserDTO;
import util.ScannerUtil;

import java.util.Scanner;

public class UserViewer {
    private final Scanner SCANNER = new Scanner(System.in);
    private UserDTO logIn;
    private ConnectionMaker connectionMaker;

    public UserViewer() {
        connectionMaker = new MySqlConnectionMaker();
    }

    public void showIndex() {
        String message = "1. 로그인 2. 회원 가입 3. 종료";
        while (true) {
            int userChoice = ScannerUtil.nextInt(SCANNER, message);
            if (userChoice == 1) {
                auth();
                if (logIn != null) {
                    showMenu();
                }

            } else if (userChoice == 2) {
                register();
            } else if (userChoice == 3) {
                System.out.println("사용해주셔서 감사합니다.");
                break;
            }
        }
    }

    private void auth() {
        String message = "아이디를 입력해주세요.";
        String username = ScannerUtil.nextLine(SCANNER, message);

        message = "비밀번호를 입력해주세요.";
        String password = ScannerUtil.nextLine(SCANNER, message);

        UserController userController = new UserController(connectionMaker);
        UserDTO temp = userController.auth(username, password);

        if (temp == null) {
            System.out.println("잘못된 정보입니다.");
        } else {
            logIn = temp;
        }

    }

    private void register() {
        String message = "사용하실 아이디를 입력해주세요.";
        String username = ScannerUtil.nextLine(SCANNER, message);

        message = "사용하실 비밀번호를 입력해주세요.";
        String password = ScannerUtil.nextLine(SCANNER, message);

        message = "사용하실 닉네임을 입력해주세요.";
        String nickname = ScannerUtil.nextLine(SCANNER, message);

        UserDTO attempt = new UserDTO();
        attempt.setUsername(username);
        attempt.setPassword(password);
        attempt.setNickname(nickname);

        UserController userController = new UserController(connectionMaker);
        if (!userController.register(attempt)) {
            System.out.println("잘못 입력하셨습니다.");
        } else {
            System.out.println("정상적으로 회원가입이 되었습니다.");
        }
    }

    private void showMenu() {
        String message = "1. 게시판으로 2. 회원 정보 확인 3. 로그아웃";
        while (logIn != null) {
            int userChoice = ScannerUtil.nextInt(SCANNER, message);
            if (userChoice == 1) {
                BoardViewer boardViewer = new BoardViewer(connectionMaker, SCANNER, logIn);
                boardViewer.showMenu();

            } else if (userChoice == 2) {

            } else if (userChoice == 3) {
                System.out.println("정상적으로 로그아웃 되셨습니다.");
                logIn = null;
            }
        }
    }
}









