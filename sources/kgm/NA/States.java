package kgm.NA;

/* loaded from: classes.dex */
public enum States {
    RUNNING,
    MENU,
    MAP,
    INTRO,
    DOWNTOWN,
    CLUB,
    FIGHT,
    SLOTS,
    PAWN,
    FOREST,
    CABIN,
    POLICE,
    POLICE_OFFICE,
    CASINO,
    LOBBY,
    BACKROOM,
    HALLWAY,
    OFFICE,
    VIP,
    SUBURBS,
    BEACH,
    WELCOME,
    ROULETTE,
    COMPUTER,
    TREE_FORT,
    PARK,
    HOUSE_ONE,
    HOUSE_TWO,
    HOUSE_THREE,
    MALL,
    STORE,
    THEATER,
    LIGHTHOUSE,
    MAZE,
    CAVE,
    HUNTING,
    INVENTORY,
    BUG_GAME,
    CRAB_GAME,
    OUTRO,
    FINAL;

    /* renamed from: values  reason: to resolve conflict with enum method */
    public static States[] valuesCustom() {
        States[] valuesCustom = values();
        int length = valuesCustom.length;
        States[] statesArr = new States[length];
        System.arraycopy(valuesCustom, 0, statesArr, 0, length);
        return statesArr;
    }
}
