import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UnitInfo unitInfo = new UnitInfo();
       // Scanner scanner = new Scanner(System.in);
        Scanner scanner = new Scanner(System.in);
        String name = scanner.next();

        System.out.println(name);

        System.out.println(unitInfo.marine(name));

        System.out.println("hi");

        System.out.println( unitInfo.showUnitInfo(Type.BIO, 50, false, 10));
    }
}