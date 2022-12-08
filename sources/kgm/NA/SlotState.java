package kgm.NA;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;
import java.util.Random;

/* loaded from: classes.dex */
public class SlotState extends State {
    private static /* synthetic */ int[] $SWITCH_TABLE$kgm$NA$SlotState$Icons;
    private static /* synthetic */ int[] $SWITCH_TABLE$kgm$NA$SlotState$SubState;
    float absIcon1X;
    float absIcon2X;
    float absIcon3X;
    float absIconY;
    Icons bottom1;
    Icons bottom2;
    Icons bottom3;
    boolean column1Done;
    boolean column2Done;
    int fadeAlpha;
    boolean gameStarted;
    float icon1Y;
    float icon2Y;
    float icon3Y;
    boolean isFading;
    Icons last1;
    Icons last2;
    Icons last3;
    int numIcons;
    int prize;
    boolean prizeGenerated;
    Random rand;
    int scrollCount;
    SubState slotState;
    boolean sound1;
    boolean sound2;
    Paint textPaint;
    Icons top1;
    Icons top2;
    Icons top3;
    int winNum;
    Paint winPaint;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public enum Icons {
        MELON,
        BAR,
        CHERRY,
        SEVEN,
        RED,
        BLUE,
        YELLOW,
        PINK;

        /* renamed from: values  reason: to resolve conflict with enum method */
        public static Icons[] valuesCustom() {
            Icons[] valuesCustom = values();
            int length = valuesCustom.length;
            Icons[] iconsArr = new Icons[length];
            System.arraycopy(valuesCustom, 0, iconsArr, 0, length);
            return iconsArr;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public enum SubState {
        SPINNING,
        WIN,
        IDLE,
        QUIT;

        /* renamed from: values  reason: to resolve conflict with enum method */
        public static SubState[] valuesCustom() {
            SubState[] valuesCustom = values();
            int length = valuesCustom.length;
            SubState[] subStateArr = new SubState[length];
            System.arraycopy(valuesCustom, 0, subStateArr, 0, length);
            return subStateArr;
        }
    }

    static /* synthetic */ int[] $SWITCH_TABLE$kgm$NA$SlotState$Icons() {
        int[] iArr = $SWITCH_TABLE$kgm$NA$SlotState$Icons;
        if (iArr == null) {
            iArr = new int[Icons.valuesCustom().length];
            try {
                iArr[Icons.BAR.ordinal()] = 2;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[Icons.BLUE.ordinal()] = 6;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[Icons.CHERRY.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[Icons.MELON.ordinal()] = 1;
            } catch (NoSuchFieldError e4) {
            }
            try {
                iArr[Icons.PINK.ordinal()] = 8;
            } catch (NoSuchFieldError e5) {
            }
            try {
                iArr[Icons.RED.ordinal()] = 5;
            } catch (NoSuchFieldError e6) {
            }
            try {
                iArr[Icons.SEVEN.ordinal()] = 4;
            } catch (NoSuchFieldError e7) {
            }
            try {
                iArr[Icons.YELLOW.ordinal()] = 7;
            } catch (NoSuchFieldError e8) {
            }
            $SWITCH_TABLE$kgm$NA$SlotState$Icons = iArr;
        }
        return iArr;
    }

    static /* synthetic */ int[] $SWITCH_TABLE$kgm$NA$SlotState$SubState() {
        int[] iArr = $SWITCH_TABLE$kgm$NA$SlotState$SubState;
        if (iArr == null) {
            iArr = new int[SubState.valuesCustom().length];
            try {
                iArr[SubState.IDLE.ordinal()] = 3;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[SubState.QUIT.ordinal()] = 4;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[SubState.SPINNING.ordinal()] = 1;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[SubState.WIN.ordinal()] = 2;
            } catch (NoSuchFieldError e4) {
            }
            $SWITCH_TABLE$kgm$NA$SlotState$SubState = iArr;
        }
        return iArr;
    }

    public SlotState(AssetManager am, Assets assets) {
        this.status = States.RUNNING;
        this.assets = assets;
        load(am, assets);
        this.textPaint = new Paint();
        this.textPaint.setTextSize(Screen.relScreenWidth * 0.08f);
        this.textPaint.setColor(-16777216);
        this.winPaint = new Paint();
        this.winPaint.setTextSize(Screen.relScreenWidth * 0.2f);
        this.winPaint.setFakeBoldText(true);
        this.slotState = SubState.IDLE;
        this.prizeGenerated = false;
        this.absIcon1X = (Screen.relScreenWidth * 0.031f) + Screen.relXZero;
        this.absIcon2X = (Screen.relScreenWidth * 0.371f) + Screen.relXZero;
        this.absIcon3X = (Screen.relScreenWidth * 0.74f) + Screen.relXZero;
        this.absIconY = (Screen.relScreenHeight * 0.378f) + Screen.relYZero;
        this.icon1Y = this.absIconY;
        this.icon2Y = this.absIconY;
        this.icon3Y = this.absIconY;
        this.rand = new Random();
        this.winNum = 0;
        this.prize = 0;
        this.last1 = Icons.PINK;
        this.last2 = Icons.PINK;
        this.last3 = Icons.PINK;
        this.bottom1 = Icons.PINK;
        this.bottom2 = Icons.PINK;
        this.bottom3 = Icons.PINK;
        this.top1 = Icons.PINK;
        this.top2 = Icons.PINK;
        this.top3 = Icons.PINK;
        this.scrollCount = 0;
        this.column1Done = false;
        this.column2Done = false;
        this.isFading = false;
        this.fadeAlpha = 0;
        assets.casinoMusic.playRand(true);
        assets.casinoMusic.setVolume(0.75f);
    }

    @Override // kgm.NA.State
    public void update(MotionEvent touch, KeyEvent key) {
        switch ($SWITCH_TABLE$kgm$NA$SlotState$SubState()[this.slotState.ordinal()]) {
            case 1:
                if (!this.prizeGenerated) {
                    this.bottom1 = this.last1;
                    this.bottom2 = this.last2;
                    this.bottom3 = this.last3;
                    this.top1 = generateIcon();
                    this.top2 = generateIcon();
                    this.top3 = generateIcon();
                    generatePrize();
                    this.prizeGenerated = true;
                    this.scrollCount = 0;
                    this.assets.slotSpin.playSound(this.assets.sounds, 0.5f);
                }
                if (!this.column1Done) {
                    this.icon1Y += Screen.relScreenHeight * 0.06f;
                }
                if (!this.column2Done) {
                    this.icon2Y += Screen.relScreenHeight * 0.06f;
                }
                this.icon3Y += Screen.relScreenHeight * 0.06f;
                if (this.icon3Y >= (Screen.relScreenHeight * 0.67f) + Screen.relYZero) {
                    this.icon1Y = this.absIconY;
                    this.icon2Y = this.absIconY;
                    this.icon3Y = this.absIconY;
                    this.bottom1 = this.top1;
                    this.bottom2 = this.top2;
                    this.bottom3 = this.top3;
                    this.top1 = generateIcon();
                    this.top2 = generateIcon();
                    this.top3 = generateIcon();
                    if (this.scrollCount >= 20) {
                        this.icon1Y = this.absIconY;
                        this.bottom1 = this.last1;
                        this.column1Done = true;
                        if (!this.sound1) {
                            this.assets.slotSpin.stop(this.assets.sounds);
                            this.assets.slotStop.playSound(this.assets.sounds);
                            this.sound1 = true;
                        }
                    }
                    if (this.scrollCount >= 25) {
                        this.icon2Y = this.absIconY;
                        this.bottom2 = this.last2;
                        this.column2Done = true;
                        if (!this.sound2) {
                            this.assets.slotStop.playSound(this.assets.sounds);
                            this.sound2 = true;
                        }
                    }
                    if (this.scrollCount == 30) {
                        this.assets.slotStop.playSound(this.assets.sounds);
                        this.icon3Y = this.absIconY;
                        this.bottom3 = this.last3;
                        this.slotState = SubState.IDLE;
                        this.prizeGenerated = false;
                        this.column1Done = false;
                        this.column2Done = false;
                        this.sound1 = false;
                        this.sound2 = false;
                        SaveFile.cash += this.prize;
                        if (this.prize > 0) {
                            this.assets.purchase.playSound(this.assets.sounds);
                        }
                        this.prize = 0;
                    }
                    this.scrollCount++;
                    return;
                }
                return;
            case 2:
            default:
                return;
            case 3:
                if (touch != null) {
                    if (touch.getAction() == 0 && touch.getX() > (Screen.relScreenWidth * 0.669f) + Screen.relXZero && touch.getX() < Screen.relScreenWidth + Screen.relXZero && touch.getY() > (Screen.relScreenHeight * 0.783f) + Screen.relYZero && touch.getY() < Screen.relScreenWidth + Screen.relYZero) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                        int i = SaveFile.cash;
                    }
                    if (touch.getAction() == 1 && touch.getX() > (Screen.relScreenWidth * 0.669f) + Screen.relXZero && touch.getX() < Screen.relScreenWidth + Screen.relXZero && touch.getY() > (Screen.relScreenHeight * 0.783f) + Screen.relYZero && touch.getY() < Screen.relScreenWidth + Screen.relYZero) {
                        this.assets.touchUp.playSound(this.assets.sounds);
                        if (SaveFile.cash >= 5) {
                            this.slotState = SubState.SPINNING;
                            SaveFile.cash -= 5;
                        }
                    }
                    if (touch.getY() > (Screen.relScreenHeight * 0.783f) + Screen.relYZero && touch.getY() < Screen.absScreenHeight + Screen.relYZero && touch.getX() > Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.3f) + Screen.relXZero) {
                        if (touch.getAction() == 0) {
                            this.assets.touchDown.playSound(this.assets.sounds);
                        }
                        if (touch.getAction() == 1) {
                            this.slotState = SubState.QUIT;
                            this.isFading = true;
                            this.assets.touchUp.playSound(this.assets.sounds);
                        }
                    }
                    touch.setLocation(-10.0f, -10.0f);
                    return;
                }
                return;
            case 4:
                this.fadeAlpha += 16;
                if (this.fadeAlpha > 255) {
                    this.fadeAlpha = MotionEventCompat.ACTION_MASK;
                }
                if (this.fadeAlpha == 255) {
                    this.status = States.CASINO;
                    return;
                }
                return;
        }
    }

    @Override // kgm.NA.State
    public void draw(Canvas canvas) {
        if (this.slotState == SubState.IDLE) {
            canvas.drawBitmap(getIconBitmap(this.last1), this.absIcon1X, this.absIconY, (Paint) null);
            canvas.drawBitmap(getIconBitmap(this.last2), this.absIcon2X, this.absIconY, (Paint) null);
            canvas.drawBitmap(getIconBitmap(this.last3), this.absIcon3X, this.absIconY, (Paint) null);
        }
        if (this.slotState == SubState.SPINNING) {
            canvas.drawBitmap(getIconBitmap(this.bottom1), this.absIcon1X, this.icon1Y, (Paint) null);
            canvas.drawBitmap(getIconBitmap(this.top1), this.absIcon1X, this.icon1Y - getIconBitmap(this.bottom1).getHeight(), (Paint) null);
            canvas.drawBitmap(getIconBitmap(this.bottom2), this.absIcon2X, this.icon2Y, (Paint) null);
            canvas.drawBitmap(getIconBitmap(this.top2), this.absIcon2X, this.icon2Y - getIconBitmap(this.bottom2).getHeight(), (Paint) null);
            canvas.drawBitmap(getIconBitmap(this.bottom3), this.absIcon3X, this.icon3Y, (Paint) null);
            canvas.drawBitmap(getIconBitmap(this.top3), this.absIcon3X, this.icon3Y - getIconBitmap(this.bottom3).getHeight(), (Paint) null);
        }
        if (this.slotState == SubState.WIN) {
        }
        canvas.drawBitmap(this.assets.slotMachine.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        canvas.drawText("Cr: $" + SaveFile.cash, (Screen.relScreenWidth * 0.3f) + Screen.relXZero, (Screen.relScreenHeight * 0.93f) + Screen.relYZero, this.textPaint);
        canvas.drawARGB(this.fadeAlpha, 0, 0, 0);
    }

    @Override // kgm.NA.State
    public void load(AssetManager am, Assets assets) {
        assets.slotMachine.load(am, "slot-machine.png", 400, 300);
        assets.slotPinkDiamond.load(am, "slot-diamond-pink.png", 90, 104);
        assets.slotRedDiamond.load(am, "slot-diamond-red.png", 90, 104);
        assets.slotYellowDiamond.load(am, "slot-diamond-yellow.png", 90, 104);
        assets.slotBlueDiamond.load(am, "slot-diamond-blue.png", 90, 104);
        assets.slotBar.load(am, "slot-bar.png", 90, 104);
        assets.slotCherry.load(am, "slot-cherry.png", 90, 104);
        assets.slotMelon.load(am, "slot-melon.png", 90, 104);
        assets.slotSeven.load(am, "slot-seven.png", 90, 104);
        assets.slotSpin.load(assets.sounds, am, "slot-spin.ogg");
        assets.slotStop.load(assets.sounds, am, "slot-stop.ogg");
        assets.purchase.load(assets.sounds, am, "purchase.ogg");
        assets.touchDown.load(assets.sounds, am, "touch-down.ogg");
        assets.touchUp.load(assets.sounds, am, "touch-up.ogg");
        assets.casinoMusic.load(am, "casino-music.ogg");
    }

    @Override // kgm.NA.State
    public void delete() {
        this.assets.slotMachine.delete();
        this.assets.slotPinkDiamond.delete();
        this.assets.slotRedDiamond.delete();
        this.assets.slotYellowDiamond.delete();
        this.assets.slotBlueDiamond.delete();
        this.assets.slotBar.delete();
        this.assets.slotCherry.delete();
        this.assets.slotMelon.delete();
        this.assets.slotSeven.delete();
        this.assets.slotSpin.delete(this.assets.sounds);
        this.assets.slotStop.delete(this.assets.sounds);
        this.assets.purchase.delete(this.assets.sounds);
        this.assets.touchDown.delete(this.assets.sounds);
        this.assets.touchUp.delete(this.assets.sounds);
        this.assets.casinoMusic.delete();
    }

    @Override // kgm.NA.State
    public void pause() {
        this.assets.casinoMusic.pause();
    }

    @Override // kgm.NA.State
    public void resume() {
        this.assets.casinoMusic.resume();
    }

    public Icons generateIcon() {
        Icons temp = Icons.MELON;
        int num = this.rand.nextInt(8) + 1;
        switch (num) {
            case 1:
                Icons temp2 = Icons.MELON;
                return temp2;
            case 2:
                Icons temp3 = Icons.BAR;
                return temp3;
            case 3:
                Icons temp4 = Icons.CHERRY;
                return temp4;
            case 4:
                Icons temp5 = Icons.SEVEN;
                return temp5;
            case 5:
                Icons temp6 = Icons.RED;
                return temp6;
            case 6:
                Icons temp7 = Icons.BLUE;
                return temp7;
            case MotionEventCompat.ACTION_HOVER_MOVE /* 7 */:
                Icons temp8 = Icons.YELLOW;
                return temp8;
            case 8:
                Icons temp9 = Icons.PINK;
                return temp9;
            default:
                return temp;
        }
    }

    public void generatePrize() {
        this.winNum = this.rand.nextInt(100) + 1;
        if (this.winNum < 16) {
            this.winNum = this.rand.nextInt(2) + 1;
            if (this.winNum == 1) {
                this.winNum = this.rand.nextInt(3) + 1;
                if (this.winNum != 3) {
                    this.prize = 10;
                    this.last1 = Icons.CHERRY;
                    this.last2 = Icons.CHERRY;
                    while (this.last3 == Icons.CHERRY) {
                        this.last3 = generateIcon();
                    }
                } else {
                    this.prize = 5;
                    this.last1 = Icons.CHERRY;
                    while (this.last2 == Icons.CHERRY) {
                        this.last2 = generateIcon();
                    }
                    while (this.last3 == Icons.CHERRY) {
                        this.last3 = generateIcon();
                    }
                }
            }
            this.winNum = this.rand.nextInt(3) + 1;
            if (this.winNum == 1) {
                this.prize = 15;
                this.last1 = Icons.MELON;
                this.last2 = Icons.MELON;
                this.last3 = Icons.MELON;
            }
            this.winNum = this.rand.nextInt(5) + 1;
            if (this.winNum == 1) {
                this.prize = 25;
                this.last1 = Icons.CHERRY;
                this.last2 = Icons.CHERRY;
                this.last3 = Icons.CHERRY;
            }
            this.winNum = this.rand.nextInt(10) + 1;
            if (this.winNum == 1) {
                this.prize = 50;
                this.last1 = Icons.BAR;
                this.last2 = Icons.BAR;
                this.last3 = Icons.BAR;
            }
            this.winNum = this.rand.nextInt(15) + 1;
            if (this.winNum == 1) {
                this.prize = 75;
                this.last1 = Icons.SEVEN;
                this.last2 = Icons.SEVEN;
                this.last3 = Icons.SEVEN;
            }
            this.winNum = this.rand.nextInt(25) + 1;
            if (this.winNum == 1) {
                this.prize = 125;
                this.last1 = Icons.RED;
                this.last2 = Icons.RED;
                this.last3 = Icons.RED;
            }
            this.winNum = this.rand.nextInt(50) + 1;
            if (this.winNum == 1) {
                this.prize = 250;
                this.last1 = Icons.BLUE;
                this.last2 = Icons.BLUE;
                this.last3 = Icons.BLUE;
            }
            this.winNum = this.rand.nextInt(100) + 1;
            if (this.winNum == 1) {
                this.prize = 500;
                this.last1 = Icons.YELLOW;
                this.last2 = Icons.YELLOW;
                this.last3 = Icons.YELLOW;
            }
            this.winNum = this.rand.nextInt(1000) + 1;
            if (this.winNum == 1) {
                this.prize = 5000;
                this.last1 = Icons.PINK;
                this.last2 = Icons.PINK;
                this.last3 = Icons.PINK;
            }
            if (this.prize == 0) {
                this.prize = 5;
                this.last1 = Icons.CHERRY;
                this.last2 = generateIcon();
                this.last3 = generateIcon();
                while (this.last2 == Icons.CHERRY) {
                    this.last2 = generateIcon();
                }
                while (this.last3 == Icons.CHERRY) {
                    this.last3 = generateIcon();
                }
                return;
            }
            return;
        }
        this.prize = 0;
        this.last1 = generateIcon();
        this.last2 = generateIcon();
        this.last3 = generateIcon();
        while (this.last1 == Icons.CHERRY) {
            this.last1 = generateIcon();
        }
        while (this.last2 == Icons.CHERRY) {
            this.last2 = generateIcon();
        }
        while (this.last3 == Icons.CHERRY) {
            this.last3 = generateIcon();
        }
        while (this.last1 == this.last3 && this.last2 == this.last3) {
            this.last3 = generateIcon();
            while (this.last3 == Icons.CHERRY) {
                this.last3 = generateIcon();
            }
        }
    }

    public Bitmap getIconBitmap(Icons icon) {
        switch ($SWITCH_TABLE$kgm$NA$SlotState$Icons()[icon.ordinal()]) {
            case 1:
                return this.assets.slotMelon.bitmap;
            case 2:
                return this.assets.slotBar.bitmap;
            case 3:
                return this.assets.slotCherry.bitmap;
            case 4:
                return this.assets.slotSeven.bitmap;
            case 5:
                return this.assets.slotRedDiamond.bitmap;
            case 6:
                return this.assets.slotBlueDiamond.bitmap;
            case MotionEventCompat.ACTION_HOVER_MOVE /* 7 */:
                return this.assets.slotYellowDiamond.bitmap;
            case 8:
                return this.assets.slotPinkDiamond.bitmap;
            default:
                return this.assets.slotBar.bitmap;
        }
    }
}
