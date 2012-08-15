/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dart.game.data;

/**
 *
 * @author Maviosso
 */
public class ItemDatabase {

    public static Item ALAT_MASAK = new Weapon(15, 20, 40, "", "Alat masak", "Enaaak", 0, true, true),
            TONGKAT = new Weapon(25, 25, 50, "", "Tongkat", "Bukan sembarang tongkat", 100, false, false),
            TALI = new Weapon(20, 100, 60, "", "Tali", "Tali sakti mandraguna", 400, false, false),
            RING = new Accesory(100, "", "Ring", "Bukan sembarang cincin", 100, false, false),
            HASDUK = new Accesory(100, "", "Hasduk", "Bukan sembarang hasduk", 250, false, false),
            BADGE = new Accesory(100, "", "Badge", "Bukan sembarang badge", 5000, false, false);

    public static void reset() {
        ALAT_MASAK = new Weapon(15, 20, 40, "", "Alat masak", "Enaaak", 0, true, true);
        TONGKAT = new Weapon(25, 25, 50, "", "Tongkat", "Bukan sembarang tongkat", 100, false, false);
        TALI = new Weapon(20, 100, 60, "", "Tali", "Tali sakti mandraguna", 400, false, false);
        RING = new Accesory(100, "", "Ring", "Bukan sembarang cincin", 100, false, false);
        HASDUK = new Accesory(100, "", "Hasduk", "Bukan sembarang hasduk", 250, false, false);
        BADGE = new Accesory(100, "", "Badge", "Bukan sembarang badge", 5000, false, false);
    }

    public static void unequipAllWeapons() {
        ALAT_MASAK.setEquipped(false);
        TONGKAT.setEquipped(false);
        TALI.setEquipped(false);
    }
}
