package kgm.NA;

import android.content.SharedPreferences;
import java.util.Random;

/* loaded from: classes.dex */
public class SaveFile {
    public static boolean agentsLeft;
    public static boolean beatBugGame;
    public static boolean beatHuntingGame;
    public static boolean bribedGuard;
    public static int cash;
    public static boolean caveIsLit;
    public static int computerPassword;
    public static boolean hasBat;
    public static boolean hasBrassKnuckles;
    public static boolean hasDrugs;
    public static boolean hasElevatorKey;
    public static boolean hasEmail;
    public static boolean hasFancyClothes;
    public static boolean hasLetter;
    public static boolean hasLight;
    public static boolean hasLighthouseKey;
    public static boolean hasLunchbox;
    public static boolean hasMoney;
    public static boolean hasRacket;
    public static boolean hasVIPPass;
    public static boolean isFirstRun;
    public static boolean lightkeeperLeft;
    public static boolean musicOn;
    public static Random rand = new Random();
    public static boolean soundOn;
    public static boolean talkedToKid;
    public static boolean wonBarFight;

    public static void read(SharedPreferences data) {
        soundOn = data.getBoolean("soundOn", true);
        musicOn = data.getBoolean("musicOn", true);
        isFirstRun = data.getBoolean("isFirstRun", true);
        cash = data.getInt("cash", 100);
        hasMoney = data.getBoolean("hasMoney", false);
        hasDrugs = data.getBoolean("hasDrugs", false);
        hasBrassKnuckles = data.getBoolean("hasBrassKnuckles", false);
        hasFancyClothes = data.getBoolean("hasFancyClothes", false);
        hasVIPPass = data.getBoolean("hasVIPPass", false);
        wonBarFight = data.getBoolean("wonBarFight", false);
        talkedToKid = data.getBoolean("talkedToKid", false);
        hasLunchbox = data.getBoolean("hasLunchbox", false);
        bribedGuard = data.getBoolean("bribedGuard", false);
        hasEmail = data.getBoolean("hasEmail", false);
        hasLetter = data.getBoolean("hasLetter", false);
        hasLighthouseKey = data.getBoolean("hasLighthouseKey", false);
        lightkeeperLeft = data.getBoolean("lightkeeperLeft", false);
        hasLight = data.getBoolean("hasLight", false);
        agentsLeft = data.getBoolean("agentsLeft", false);
        computerPassword = data.getInt("computerPassword", rand.nextInt(8999) + 1001);
        hasBat = data.getBoolean("hasBat", false);
        hasRacket = data.getBoolean("hasRacket", false);
        hasElevatorKey = data.getBoolean("hasElevatorKey", false);
        beatHuntingGame = data.getBoolean("beatHuntingGame", false);
        caveIsLit = data.getBoolean("caveIsLit", false);
        beatBugGame = data.getBoolean("beatBugGame", false);
    }

    public static void write(SharedPreferences.Editor editor) {
        editor.putBoolean("soundOn", soundOn);
        editor.putBoolean("musicOn", musicOn);
        editor.putBoolean("isFirstRun", isFirstRun);
        editor.putInt("cash", cash);
        editor.putBoolean("hasMoney", hasMoney);
        editor.putBoolean("hasDrugs", hasDrugs);
        editor.putBoolean("hasBrassKnuckles", hasBrassKnuckles);
        editor.putBoolean("hasFancyClothes", hasFancyClothes);
        editor.putBoolean("hasVIPPass", hasVIPPass);
        editor.putBoolean("wonBarFight", wonBarFight);
        editor.putBoolean("talkedToKid", talkedToKid);
        editor.putBoolean("hasLunchbox", hasLunchbox);
        editor.putBoolean("bribedGuard", bribedGuard);
        editor.putBoolean("hasEmail", hasEmail);
        editor.putBoolean("hasLetter", hasLetter);
        editor.putBoolean("hasLighthouseKey", hasLighthouseKey);
        editor.putBoolean("lightkeeperLeft", lightkeeperLeft);
        editor.putBoolean("hasLight", hasLight);
        editor.putBoolean("agentsLeft", agentsLeft);
        editor.putInt("computerPassword", computerPassword);
        editor.putBoolean("hasBat", hasBat);
        editor.putBoolean("hasRacket", hasRacket);
        editor.putBoolean("hasElevatorKey", hasElevatorKey);
        editor.putBoolean("beatHuntingGame", beatHuntingGame);
        editor.putBoolean("caveIsLit", caveIsLit);
        editor.putBoolean("beatBugGame", beatBugGame);
    }

    public static void reset() {
        soundOn = true;
        musicOn = true;
        isFirstRun = true;
        cash = 100;
        hasMoney = false;
        hasDrugs = false;
        hasBrassKnuckles = false;
        hasFancyClothes = false;
        hasVIPPass = false;
        wonBarFight = false;
        talkedToKid = false;
        hasLunchbox = false;
        bribedGuard = false;
        hasEmail = false;
        hasLetter = false;
        hasLighthouseKey = false;
        lightkeeperLeft = false;
        hasLight = false;
        agentsLeft = false;
        computerPassword = rand.nextInt(8999) + 1001;
        hasBat = false;
        hasRacket = false;
        hasElevatorKey = false;
        beatHuntingGame = false;
        caveIsLit = false;
        beatBugGame = false;
    }
}
