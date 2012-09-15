/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dart.game.data;

import dart.game.data.Achievement.AchievementCondition;
import dart.game.main.MainProfile;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 *
 * @author Maviosso
 */
public class AchievementData {

    public static MainProfile profile;
    private static final AchievementCondition DUKUN_CONDITION = new AchievementCondition() {

        public boolean isSatisfyCondition() {
            return profile.getKuntilanakKill() >= 50;
        }
    };
    private static final AchievementCondition EARTH_CONDITION = new AchievementCondition() {

        public boolean isSatisfyCondition() {
            return profile.getMaxFinalAttackCount() >= 5;
        }
    };
    private static final AchievementCondition HEDON_CONDITION = new AchievementCondition() {

        public boolean isSatisfyCondition() {

            return ItemDatabase.ALAT_MASAK.isBought() && ItemDatabase.TALI.isBought() && ItemDatabase.TONGKAT.isBought();
        }
    };
    private static final AchievementCondition JURAGAN_CONDITION = new AchievementCondition() {

        public boolean isSatisfyCondition() {

            return profile.getMoney() >= 10000;
        }
    };
    private static final AchievementCondition PENEGAK_CONDITION = new AchievementCondition() {

        public boolean isSatisfyCondition() {
            int[] bestCombos = profile.getBestCombo();
            boolean satisfy = false;
            for (int i = 0; i < bestCombos.length; i++) {
                if (bestCombos[i] >= 100) {
                    satisfy = true;
                }
            }
            return satisfy;
        }
    };
    private static final AchievementCondition PENGGALANG_CONDITION = new AchievementCondition() {

        public boolean isSatisfyCondition() {
            int[] bestCombos = profile.getBestCombo();
            boolean satisfy = false;
            for (int i = 0; i < bestCombos.length; i++) {
                if (bestCombos[i] >= 50) {
                    satisfy = true;
                }
            }
            return satisfy;
        }
    };
    private static final AchievementCondition SIAGA_CONDITION = new AchievementCondition() {

        public boolean isSatisfyCondition() {
            int[] bestCombos = profile.getBestCombo();
            boolean satisfy = false;
            for (int i = 0; i < bestCombos.length; i++) {
                if (bestCombos[i] >= 10) {
                    satisfy = true;
                }
            }
            return satisfy;
        }
    };
    private static final AchievementCondition RAJA_CONDITION = new AchievementCondition() {

        public boolean isSatisfyCondition() {
            int[] savedAnimals = profile.getSavedAnimals();
            int total = 0;
            for (int i = 0; i < savedAnimals.length; i++) {
                total += savedAnimals[i];
            }
            return total >= 15;
        }
    };
    private static final AchievementCondition PENYAYANG_CONDITION = new AchievementCondition() {

        public boolean isSatisfyCondition() {
            return !profile.isFinalAttackLastLevel();
        }
    };
    public static Achievement[] BADGES = {
        new Achievement("Dukun Cilik", "Kalahkan 50 Kuntilanak", "/Dukun.png", false, DUKUN_CONDITION),
        new Achievement("Earthshaker", "Lakukan 5 final attack dalam 1 level", "/Earthshaker.png", false, EARTH_CONDITION),
        new Achievement("Hedonis", "Beli semua barang di toko", "/Hedonis.png", false, HEDON_CONDITION),
        new Achievement("Juragan", "Kumpulkan 10000 rupiah", "/Juragan.png", false, JURAGAN_CONDITION),
        new Achievement("Pawang Cicak", "Selesaikan semua level", "/PawangCicak1.png", false, null),
        new Achievement("Penegak", "Lakukan 100 kombo", "/Penegak.png", false, PENEGAK_CONDITION),
        new Achievement("Penggalang", "Lakukan 50 kombo", "/Penggalang.png", false, PENGGALANG_CONDITION),
        new Achievement("Siaga", "Lakukan 10 kombo", "/Siaga.png", false, SIAGA_CONDITION),
        new Achievement("Raja Hutan", "Selamatkan 15 hewan yang dilindungi", "/Raja-Hutan.png", false, RAJA_CONDITION),
        new Achievement("Penyayang Alam", "Selesaikan level 5 tanpa final attack", "/Penyayang-Alam.png", false, PENYAYANG_CONDITION)
    };
    public static final int DUKUN = 0,
            EARTH = 1,
            HEDON = 2,
            JURAGAN = 3,
            PAWANG = 4,
            PENEGAK = 5,
            PENGGALANG = 6,
            SIAGA = 7,
            RAJA = 8,
            ALAM = 9;

    public static int[] getNewUnlockedAchievement() {
        return null;
    }

    public static void writeData(DataOutputStream stream) throws IOException {
        for (int i = 0; i < BADGES.length; i++) {
            stream.writeBoolean(BADGES[i].isUnlocked());
        }
    }

    public static void readData(DataInputStream stream) {
        for (int i = 0; i < BADGES.length; i++) {
            try {
                BADGES[i].setUnlocked(stream.readBoolean());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void reset() {
        for(int i = 0; i < BADGES.length; i++){
            BADGES[i].setUnlocked(false);
        }
    }
}
