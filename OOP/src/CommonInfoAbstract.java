abstract class CommonInfoAbstract implements UnitCommonInfo {
    static Type type;
    static int hp;
    static boolean magicYN;
    static int atk;
    static int defence;
    static String size;

    @Override
    public String showUnitInfo(Type getType, int getHp, boolean getMagicYN, int getAtk) {
        type = getType;
        hp = getHp;
        magicYN = getMagicYN;
        atk = getAtk;
        if (getMagicYN) {
            int mp;
        }
        return null;
    };

}
