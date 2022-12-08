package kgm.NA;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.MotionEventCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;
import java.util.Random;

/* loaded from: classes.dex */
public class RouletteState extends State {
    private static /* synthetic */ int[] $SWITCH_TABLE$kgm$NA$RouletteState$BetType;
    private static /* synthetic */ int[] $SWITCH_TABLE$kgm$NA$RouletteState$SubState;
    int betAmount;
    BetType betType;
    boolean chipActivated;
    int count;
    int fadeAlpha;
    int frameCount;
    int framesToCount;
    boolean isFading;
    States nextState;
    int num;
    boolean numGenerated;
    Paint numPaint;
    Random rand = new Random();
    SubState subState;
    Paint textPaint;
    float touchX;
    float touchY;
    int winAmount;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public enum BetType {
        ZERO,
        RED,
        BLACK,
        TWELVE,
        TWENTY_FOUR,
        THIRTY_SIX;

        /* renamed from: values  reason: to resolve conflict with enum method */
        public static BetType[] valuesCustom() {
            BetType[] valuesCustom = values();
            int length = valuesCustom.length;
            BetType[] betTypeArr = new BetType[length];
            System.arraycopy(valuesCustom, 0, betTypeArr, 0, length);
            return betTypeArr;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public enum SubState {
        IDLE,
        SPIN;

        /* renamed from: values  reason: to resolve conflict with enum method */
        public static SubState[] valuesCustom() {
            SubState[] valuesCustom = values();
            int length = valuesCustom.length;
            SubState[] subStateArr = new SubState[length];
            System.arraycopy(valuesCustom, 0, subStateArr, 0, length);
            return subStateArr;
        }
    }

    static /* synthetic */ int[] $SWITCH_TABLE$kgm$NA$RouletteState$BetType() {
        int[] iArr = $SWITCH_TABLE$kgm$NA$RouletteState$BetType;
        if (iArr == null) {
            iArr = new int[BetType.valuesCustom().length];
            try {
                iArr[BetType.BLACK.ordinal()] = 3;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[BetType.RED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[BetType.THIRTY_SIX.ordinal()] = 6;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[BetType.TWELVE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                iArr[BetType.TWENTY_FOUR.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                iArr[BetType.ZERO.ordinal()] = 1;
            } catch (NoSuchFieldError e6) {
            }
            $SWITCH_TABLE$kgm$NA$RouletteState$BetType = iArr;
        }
        return iArr;
    }

    static /* synthetic */ int[] $SWITCH_TABLE$kgm$NA$RouletteState$SubState() {
        int[] iArr = $SWITCH_TABLE$kgm$NA$RouletteState$SubState;
        if (iArr == null) {
            iArr = new int[SubState.valuesCustom().length];
            try {
                iArr[SubState.IDLE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[SubState.SPIN.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            $SWITCH_TABLE$kgm$NA$RouletteState$SubState = iArr;
        }
        return iArr;
    }

    public RouletteState(AssetManager am, Assets assets) {
        this.status = States.RUNNING;
        this.assets = assets;
        load(am, assets);
        this.isFading = false;
        this.fadeAlpha = 0;
        this.subState = SubState.IDLE;
        this.numPaint = new Paint();
        this.numPaint.setTextSize(Screen.relScreenWidth * 0.1f);
        this.textPaint = new Paint();
        this.textPaint.setColor(Color.rgb(204, 204, 204));
        this.textPaint.setTextSize(Screen.relScreenWidth * 0.1f);
        this.num = 0;
        this.betAmount = 0;
        this.count = 0;
        this.chipActivated = false;
        this.frameCount = 0;
        this.framesToCount = 2;
        this.winAmount = 0;
        assets.casinoMusic.playRand(true);
        assets.casinoMusic.setVolume(0.75f);
    }

    @Override // kgm.NA.State
    public void update(MotionEvent touch, KeyEvent key) {
        if (touch != null) {
            switch ($SWITCH_TABLE$kgm$NA$RouletteState$SubState()[this.subState.ordinal()]) {
                case 1:
                    if (!this.chipActivated) {
                        if (touch.getAction() == 0) {
                            if (touch.getY() > (Screen.relScreenHeight * 0.7333f) + Screen.relYZero && touch.getY() < Screen.relScreenHeight + Screen.relYZero && touch.getX() > Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.1975f) + Screen.relXZero) {
                                this.chipActivated = true;
                                this.betAmount = 5;
                                this.assets.touchDown.playSound(this.assets.sounds);
                            }
                            if (touch.getY() > (Screen.relScreenHeight * 0.7333f) + Screen.relYZero && touch.getY() < Screen.relScreenHeight + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.1975f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.3975f) + Screen.relXZero) {
                                this.chipActivated = true;
                                this.betAmount = 10;
                                this.assets.touchDown.playSound(this.assets.sounds);
                            }
                            if (touch.getY() > (Screen.relScreenHeight * 0.7333f) + Screen.relYZero && touch.getY() < Screen.relScreenHeight + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.3975f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.6025f) + Screen.relXZero) {
                                this.chipActivated = true;
                                this.betAmount = 25;
                                this.assets.touchDown.playSound(this.assets.sounds);
                            }
                            if (touch.getY() > (Screen.relScreenHeight * 0.7333f) + Screen.relYZero && touch.getY() < Screen.relScreenHeight + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.6025f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.8025f) + Screen.relXZero) {
                                this.chipActivated = true;
                                this.betAmount = 50;
                                this.assets.touchDown.playSound(this.assets.sounds);
                            }
                            if (touch.getY() > (Screen.relScreenHeight * 0.7333f) + Screen.relYZero && touch.getY() < Screen.relScreenHeight + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.8025f) + Screen.relXZero && touch.getX() < Screen.relScreenWidth + Screen.relXZero) {
                                this.chipActivated = true;
                                this.betAmount = 100;
                                this.assets.touchDown.playSound(this.assets.sounds);
                            }
                        }
                    } else {
                        this.touchX = touch.getX();
                        this.touchY = touch.getY();
                        if (touch.getAction() == 1) {
                            this.chipActivated = false;
                            this.touchX = -1000.0f;
                            this.touchY = -1000.0f;
                            if (SaveFile.cash >= this.betAmount) {
                                if (touch.getY() > (Screen.relScreenHeight * 0.2333f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.4833f) + Screen.relYZero && touch.getX() > Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.325f) + Screen.relXZero) {
                                    this.betType = BetType.ZERO;
                                    this.subState = SubState.SPIN;
                                    this.assets.touchUp.playSound(this.assets.sounds);
                                }
                                if (touch.getY() > (Screen.relScreenHeight * 0.2333f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.4833f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.325f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.6625f) + Screen.relXZero) {
                                    this.betType = BetType.RED;
                                    this.subState = SubState.SPIN;
                                    this.assets.touchUp.playSound(this.assets.sounds);
                                }
                                if (touch.getY() > (Screen.relScreenHeight * 0.2333f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.4833f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.6625f) + Screen.relXZero && touch.getX() < Screen.relScreenWidth + Screen.relXZero) {
                                    this.betType = BetType.BLACK;
                                    this.subState = SubState.SPIN;
                                    this.assets.touchUp.playSound(this.assets.sounds);
                                }
                                if (touch.getY() > (Screen.relScreenHeight * 0.4833f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.7333f) + Screen.relYZero && touch.getX() > Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.325f) + Screen.relXZero) {
                                    this.betType = BetType.TWELVE;
                                    this.subState = SubState.SPIN;
                                    this.assets.touchUp.playSound(this.assets.sounds);
                                }
                                if (touch.getY() > (Screen.relScreenHeight * 0.4833f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.7333f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.325f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.6625f) + Screen.relXZero) {
                                    this.betType = BetType.TWENTY_FOUR;
                                    this.subState = SubState.SPIN;
                                    this.assets.touchUp.playSound(this.assets.sounds);
                                }
                                if (touch.getY() > (Screen.relScreenHeight * 0.4833f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.7333f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.6625d) + Screen.relXZero && touch.getX() < Screen.relScreenWidth + Screen.relXZero) {
                                    this.betType = BetType.THIRTY_SIX;
                                    this.subState = SubState.SPIN;
                                    this.assets.touchUp.playSound(this.assets.sounds);
                                }
                            }
                        }
                    }
                    if (touch.getY() > Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.2333f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.6625f) + Screen.relXZero && touch.getX() < Screen.relScreenWidth + Screen.relXZero) {
                        if (touch.getAction() == 0) {
                            this.assets.touchDown.playSound(this.assets.sounds);
                        }
                        if (touch.getAction() == 1) {
                            this.isFading = true;
                            this.nextState = States.CASINO;
                            this.assets.touchUp.playSound(this.assets.sounds);
                            break;
                        }
                    }
                    break;
                case 2:
                    if (!this.numGenerated) {
                        this.num = this.rand.nextInt(37);
                        this.numGenerated = true;
                    }
                    this.frameCount++;
                    if (this.frameCount > this.framesToCount) {
                        this.frameCount = 0;
                        if (this.count > 35) {
                            this.framesToCount += 2;
                        }
                        this.num = this.rand.nextInt(37);
                        this.count++;
                        this.assets.rouletteSpin.playSound(this.assets.sounds, 0.5f);
                        if (this.count > 42) {
                            SaveFile.cash -= this.betAmount;
                            this.assets.rouletteStop.playSound(this.assets.sounds);
                            if (checkWin(this.num, this.betType)) {
                                SaveFile.cash += calculateWin(this.betType, this.betAmount);
                                this.assets.purchase.playSound(this.assets.sounds);
                            }
                            this.count = 0;
                            this.framesToCount = 2;
                            this.frameCount = 0;
                            this.betAmount = 0;
                            this.subState = SubState.IDLE;
                            this.touchX = -10000.0f;
                            this.touchY = -10000.0f;
                            break;
                        }
                    }
                    break;
            }
            touch.setLocation(-10.0f, -10.0f);
        }
        if (this.isFading) {
            this.fadeAlpha += 16;
            if (this.fadeAlpha > 255) {
                this.fadeAlpha = MotionEventCompat.ACTION_MASK;
            }
            if (this.fadeAlpha == 255) {
                this.status = this.nextState;
            }
        }
    }

    @Override // kgm.NA.State
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawBitmap(this.assets.roulette.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        if (this.num == 0) {
            this.numPaint.setColor(Color.rgb(20, 108, 20));
        } else if (this.num % 2 == 0) {
            this.numPaint.setColor(-16777216);
        } else {
            this.numPaint.setColor(SupportMenu.CATEGORY_MASK);
        }
        canvas.drawText(Integer.toString(SaveFile.cash), (Screen.relScreenWidth * 0.41f) + Screen.relXZero, (Screen.relScreenHeight * 0.18f) + Screen.relYZero, this.textPaint);
        canvas.drawText(Integer.toString(this.num), (Screen.relScreenWidth * 0.138f) + Screen.relXZero, (Screen.relScreenHeight * 0.16f) + Screen.relYZero, this.numPaint);
        if (this.subState == SubState.IDLE && this.chipActivated) {
            switch (this.betAmount) {
                case 5:
                    canvas.drawBitmap(this.assets.chipFive.bitmap, this.touchX - (this.assets.chipFive.bitmap.getWidth() / 2), this.touchY - (this.assets.chipFive.bitmap.getWidth() / 2), (Paint) null);
                    break;
                case 10:
                    canvas.drawBitmap(this.assets.chipTen.bitmap, this.touchX - (this.assets.chipTen.bitmap.getWidth() / 2), this.touchY - (this.assets.chipTen.bitmap.getWidth() / 2), (Paint) null);
                    break;
                case 25:
                    canvas.drawBitmap(this.assets.chipTwentyFive.bitmap, this.touchX - (this.assets.chipTwentyFive.bitmap.getWidth() / 2), this.touchY - (this.assets.chipTwentyFive.bitmap.getWidth() / 2), (Paint) null);
                    break;
                case 50:
                    canvas.drawBitmap(this.assets.chipFifty.bitmap, this.touchX - (this.assets.chipFifty.bitmap.getWidth() / 2), this.touchY - (this.assets.chipFifty.bitmap.getWidth() / 2), (Paint) null);
                    break;
                case 100:
                    canvas.drawBitmap(this.assets.chipHundred.bitmap, this.touchX - (this.assets.chipHundred.bitmap.getWidth() / 2), this.touchY - (this.assets.chipHundred.bitmap.getWidth() / 2), (Paint) null);
                    break;
            }
        }
        if (this.subState == SubState.SPIN) {
            switch ($SWITCH_TABLE$kgm$NA$RouletteState$BetType()[this.betType.ordinal()]) {
                case 1:
                    canvas.drawBitmap(getChip(this.betAmount), (Screen.relScreenWidth * 0.0899f) + Screen.relXZero, (Screen.relScreenHeight * 0.2446f) + Screen.relYZero, (Paint) null);
                    break;
                case 2:
                    canvas.drawBitmap(getChip(this.betAmount), (Screen.relScreenWidth * 0.4111f) + Screen.relXZero, (Screen.relScreenHeight * 0.2446f) + Screen.relYZero, (Paint) null);
                    break;
                case 3:
                    canvas.drawBitmap(getChip(this.betAmount), (Screen.relScreenWidth * 0.7381f) + Screen.relXZero, (Screen.relScreenHeight * 0.2446f) + Screen.relYZero, (Paint) null);
                    break;
                case 4:
                    canvas.drawBitmap(getChip(this.betAmount), (Screen.relScreenWidth * 0.0899f) + Screen.relXZero, (Screen.relScreenHeight * 0.4946f) + Screen.relYZero, (Paint) null);
                    break;
                case 5:
                    canvas.drawBitmap(getChip(this.betAmount), (Screen.relScreenWidth * 0.4111f) + Screen.relXZero, (Screen.relScreenHeight * 0.4946f) + Screen.relYZero, (Paint) null);
                    break;
                case 6:
                    canvas.drawBitmap(getChip(this.betAmount), (Screen.relScreenWidth * 0.7381f) + Screen.relXZero, (Screen.relScreenHeight * 0.4946f) + Screen.relYZero, (Paint) null);
                    break;
            }
        }
        canvas.drawARGB(this.fadeAlpha, 0, 0, 0);
    }

    @Override // kgm.NA.State
    public void load(AssetManager am, Assets assets) {
        assets.roulette.load(am, "roulette.png", 400, 300);
        assets.chipFive.load(am, "chip-five.png", 68, 68);
        assets.chipTen.load(am, "chip-ten.png", 68, 68);
        assets.chipTwentyFive.load(am, "chip-twenty-five.png", 68, 68);
        assets.chipFifty.load(am, "chip-fifty.png", 68, 68);
        assets.chipHundred.load(am, "chip-hundred.png", 68, 68);
        assets.rouletteSpin.load(assets.sounds, am, "roulette-spin.ogg");
        assets.rouletteStop.load(assets.sounds, am, "roulette-stop.ogg");
        assets.purchase.load(assets.sounds, am, "purchase.ogg");
        assets.touchDown.load(assets.sounds, am, "touch-down.ogg");
        assets.touchUp.load(assets.sounds, am, "touch-up.ogg");
        assets.casinoMusic.load(am, "casino-music.ogg");
    }

    @Override // kgm.NA.State
    public void delete() {
        this.assets.roulette.delete();
        this.assets.chipFive.delete();
        this.assets.chipTen.delete();
        this.assets.chipTwentyFive.delete();
        this.assets.chipFifty.delete();
        this.assets.chipHundred.delete();
        this.assets.rouletteSpin.delete(this.assets.sounds);
        this.assets.rouletteStop.delete(this.assets.sounds);
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

    public Bitmap getChip(int bet) {
        switch (bet) {
            case 5:
                return this.assets.chipFive.bitmap;
            case 10:
                return this.assets.chipTen.bitmap;
            case 25:
                return this.assets.chipTwentyFive.bitmap;
            case 50:
                return this.assets.chipFifty.bitmap;
            case 100:
                return this.assets.chipHundred.bitmap;
            default:
                return this.assets.agents.bitmap;
        }
    }

    public int calculateWin(BetType type, int betAmount) {
        switch ($SWITCH_TABLE$kgm$NA$RouletteState$BetType()[type.ordinal()]) {
            case 1:
                return betAmount * 35;
            case 2:
                return betAmount * 2;
            case 3:
                return betAmount * 2;
            case 4:
                return betAmount * 3;
            case 5:
                return betAmount * 3;
            case 6:
                return betAmount * 3;
            default:
                return 0;
        }
    }

    public boolean checkWin(int num, BetType betType) {
        if (num == 0 && betType == BetType.ZERO) {
            return true;
        }
        switch ($SWITCH_TABLE$kgm$NA$RouletteState$BetType()[betType.ordinal()]) {
            case 1:
            default:
                return false;
            case 2:
                return num % 2 == 1;
            case 3:
                return num % 2 == 0;
            case 4:
                return num <= 12;
            case 5:
                return num >= 13 && num <= 24;
            case 6:
                return num >= 25 && num <= 36;
        }
    }
}
