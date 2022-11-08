import java.util.Scanner;

public class UnitInfo extends CommonInfoAbstract {
    static Type type;
    static int hp;
    static boolean magicYN;
    static int atk;
    static int defence;
    static String size;
    static int atkCycle;
    static float speed;
    static float range;

    public String marine(String name) {

        type = Type.BIO;
        hp = 50;
        magicYN = false;
        atk = 5;
        defence = 0;
        size = "small";
        atkCycle = 2;
        speed = 1.825f;
        range = 5;

        return String.format("%s's unit type : %s", name, type);
    }

    @Override
    public String showUnitInfo(Type getType, int getHp, boolean getMagicYN, int getAtk) {
        Scanner scanner = new Scanner(System.in);
        String unitName = scanner.next();
        return String.format("Name : %s\ntype : %s\nhp : %s\nMagic Or not : %s\natk : %s\n",unitName, getType, getHp, getMagicYN, getAtk);
    }
}
