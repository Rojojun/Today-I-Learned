public interface UnitCommonInfo{
    default void showUnitInfo() {

    }

    String showUnitInfo(Type getType, int getHp, boolean getMagicYN, int getAtk);
}
