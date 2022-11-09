public class Weapon {
    int atk;
    String weaponName;
    WeaponType weaponType;
    int atkCycle;
    float range;
    void registWeapon(String name, int num) {
        int input = num;
        switch (input) {
            case 0 :
                this.weaponType = WeaponType.NORMAL;
                input = 0;
                break;
            case 1 :
                this.weaponType = WeaponType.BOMB;
                input = 0;
                break;
            case 2 :
                this.weaponType = WeaponType.VIBRATING;
                input = 0;
                break;
            default:
                System.out.println("0 : 일반형\n1: 폭발형\n2 : 진동형");
        }
        input = 0;

        if (atkCycle > 36) {
            throw new IllegalArgumentException("에러 : 올바르지 않은 유효값입니다.");
        }
        if (range > 12) {
            throw new IllegalArgumentException("에러 : 올바르지 않은 유효값입니다.");
        }
        // 공격력은 int의 최대 값으로s
        if (atk > Integer.MAX_VALUE - 1) {
            throw new IllegalArgumentException("에러 : 올바르지 않은 유효값입니다.");
        }
        System.out.println("atk : " + atk);
        System.out.println("cycle : " + atkCycle);
        System.out.println("weapon name : " + name);
        System.out.println("type : " + weaponType);
        System.out.println("range : " + range);
    }
}
