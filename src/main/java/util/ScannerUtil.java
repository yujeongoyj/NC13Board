package util;

import java.util.Scanner;

// 스캐너 클래스를 사용할 때
// 좀더 간편하게 사용할 수있는 메소드들을 모아둔
// 클래스
public class ScannerUtil {
    // 출력에 사용할 메시지를 받아서
    // 예쁘게 출력해줄 메소드
    private static void printMessage(String message) {
        System.out.println(message);
        System.out.print("> ");
    }

    // 사용자로부터 입력에 사용할 스캐너와 입력에 필요한 메시지를 받아서
    // 정수 입력을 처리하여 리턴해주는 메소드
    public static int nextInt(Scanner scanner, String message) {
        String temp = nextLine(scanner, message);
        while (!temp.matches("^-?\\d+$")) {
            System.out.println("숫자가 아닙니다.\n 다시 입력해주세요.");
            temp = nextLine(scanner, message);
        }

        // String을 int로 바꿀 때에는?
        // Integer.parseInt(String) 을 사용하면 된다.

        return Integer.parseInt(temp);
    }

    // 사용자가 스캐너, 메시지, 최소값, 최대값을 보내면
    // 해당 범위의 정수를 리턴해주는 메소드
    public static int nextInt(Scanner scanner, String message, int min, int max) {
        int temp = nextInt(scanner, message);
        while (!(temp >= min && temp <= max)) {
            System.out.println("범위를 벗어난 값입니다.");
            temp = nextInt(scanner, message);
        }

        return temp;
    }

    // Scanner 버그를 해결한 nextLine()
    public static String nextLine(Scanner scanner, String message) {
        printMessage(message);
        String temp = scanner.nextLine();
        if (temp.isEmpty()) {
            temp = scanner.nextLine();
        }

        return temp;
    }
}
