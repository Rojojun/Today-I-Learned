public class Weapon {
    int atk;
    String weaponName;
    WeaponType weaponType;
    int atkCycle;
    float range;
    void registWeapon(String name, int num) {
        weaponName = name;
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

    }
}
