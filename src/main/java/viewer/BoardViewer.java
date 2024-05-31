//package viewer;
//
//import connector.ConnectionMaker;
//import model.UserDTO;
//import util.ScannerUtil;
//
//import java.sql.Connection;
//import java.util.Scanner;
//
//public class BoardViewer {
//    private Connection connection;
//    private final Scanner SCANNER;
//    private ConnectionMaker connectionMaker;
//    private UserDTO logIn;
//
//    // ConnectionMaker를 통해 BoardController 객체를 생성하고
//    // 이를 통해 데이터베이스와 연결
//    public BoardViewer(ConnectionMaker connectionMaker, Scanner scanner, UserDTO logIn) {
//        this.connectionMaker = connectionMaker;
//        SCANNER = scanner;
//        this.logIn = logIn;
//    }
//
//    public void showMenu() {
//        String message = "1. 글 작성하기 2. 글 목록보기 3. 뒤로가기";
//        int userChoice = ScannerUtil.nextInt(SCANNER, message);
//        while (true) {
//            if (userChoice == 1) {
//                insert();
//            } else if (userChoice == 2) {
//
//            } else if (userChoice == 3) {
//                System.out.println("뒤로 갑니다");
//                break;
//            }
//        }
//    }
//
//    private void insert() {
//
//
//    }
//}







package viewer;

import connector.ConnectionMaker;
import controller.BoardController;
import model.BoardDTO;
import model.UserDTO;
import util.ScannerUtil;

import java.util.List;
import java.util.Scanner;

public class BoardViewer {
    private final Scanner SCANNER;
    private final BoardController boardController;
    private final UserDTO logIn;

    public BoardViewer(ConnectionMaker connectionMaker, Scanner scanner, UserDTO logIn) {
        this.SCANNER = scanner;
        this.logIn = logIn;
        this.boardController = new BoardController(connectionMaker);
    }

    public void showMenu() {
        String message = "1. 글 작성하기 2. 글 목록보기 3. 뒤로가기";
        while (true) {
            int userChoice = ScannerUtil.nextInt(SCANNER, message);
            if (userChoice == 1) {
                insert();
            } else if (userChoice == 2) {
                printList();
            } else if (userChoice == 3) {
                System.out.println("뒤로 갑니다");
                break;
            }
        }
    }

    private void insert() {
        BoardDTO boardDTO = new BoardDTO();
        String message;
        message = "제목: ";
        boardDTO.setTitle(ScannerUtil.nextLine(SCANNER, message));

        message = "내용: ";
        boardDTO.setContent(ScannerUtil.nextLine(SCANNER, message));

        boardDTO.setWriterId(logIn.getId());

        boardController.insert(boardDTO);
        System.out.println("게시글이 성공적으로 등록되었습니다.");
    }

    private void printList() {
        List<BoardDTO> list = boardController.selectAll();

        if (list.isEmpty()) {
            System.out.println("게시글이 없습니다.");
        } else {
            for (BoardDTO b : list) {
                System.out.printf("%d. %s - %s\n", b.getId(), b.getTitle(), b.getNickname());
            }
            String message = "상세보기할 글의 번호나 뒤로가시려면 0을 입력해주세요.";
            int userChoice = ScannerUtil.nextInt(SCANNER, message);

            if (userChoice != 0) {
                printOne(userChoice);
            }
        }
    }

    private void printOne(int id) {
        BoardDTO boardDTO = boardController.selectOne(id);

        if (boardDTO == null) {
            System.out.println("해당 번호의 게시글이 존재하지 않습니다.");
        } else {
            System.out.println("제목: " + boardDTO.getTitle());
            System.out.println("작성자: " + boardDTO.getNickname());
            System.out.println("작성일: " + boardDTO.getEntryDate());
            System.out.println("수정일: " + boardDTO.getModifyDate());
            System.out.println("내용: " + boardDTO.getContent());

            String message = "1. 수정 2. 삭제 3. 목록으로 돌아가기";
            int userChoice = ScannerUtil.nextInt(SCANNER, message);

            if (userChoice == 1) {
                update(id);
            } else if (userChoice == 2) {
                delete(id);
            }
        }
    }

    private void update(int id) {
        BoardDTO boardDTO = boardController.selectOne(id);

        String message;
        message ="새 제목: ";
        boardDTO.setTitle(ScannerUtil.nextLine(SCANNER, message));

        message = "새 내용: ";
        boardDTO.setContent(ScannerUtil.nextLine(SCANNER, message));

        boardController.update(boardDTO);
        System.out.println("게시글이 성공적으로 수정되었습니다.");
    }

    private void delete(int id) {
        String message = "정말로 삭제하시겠습니까? Y/N";
        String yesNo = ScannerUtil.nextLine(SCANNER, message);

        if (yesNo.equalsIgnoreCase("Y")) {
            boardController.delete(id);
            System.out.println("게시글이 성공적으로 삭제되었습니다.");
        } else {
            printOne(id);
        }
    }
}
