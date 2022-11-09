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

        Weapon weapon = new Weapon();

        System.out.println("ATK?");
        weapon.atk = scanner.nextInt();
        System.out.println("ATK Cycle?");
        weapon.atkCycle = scanner.nextInt();
        System.out.println("ATK range");
        weapon.range = scanner.nextInt();
        System.out.println("ATK type?");
        int input = scanner.nextInt();
        System.out.println("Weapon Name");
        String wName = scanner.next();
        System.out.println("result");
        weapon.registWeapon(wName, input);
    }
}