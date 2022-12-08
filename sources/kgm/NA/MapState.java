package kgm.NA;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;

/* loaded from: classes.dex */
public class MapState extends State {
    private static /* synthetic */ int[] $SWITCH_TABLE$kgm$NA$States;
    DSprite beachText;
    DSprite downtownText;
    int fadeAlpha;
    DSprite forestText;
    boolean isFading;
    States nextState;
    boolean scaleDown;
    float scalePct;
    DSprite suburbsText;
    TouchedButton touchedButton;

    /* loaded from: classes.dex */
    enum TouchedButton {
        DOWNTOWN,
        SUBURBS,
        FOREST,
        BEACH;

        /* renamed from: values  reason: to resolve conflict with enum method */
        public static TouchedButton[] valuesCustom() {
            TouchedButton[] valuesCustom = values();
            int length = valuesCustom.length;
            TouchedButton[] touchedButtonArr = new TouchedButton[length];
            System.arraycopy(valuesCustom, 0, touchedButtonArr, 0, length);
            return touchedButtonArr;
        }
    }

    static /* synthetic */ int[] $SWITCH_TABLE$kgm$NA$States() {
        int[] iArr = $SWITCH_TABLE$kgm$NA$States;
        if (iArr == null) {
            iArr = new int[States.valuesCustom().length];
            try {
                iArr[States.BACKROOM.ordinal()] = 16;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[States.BEACH.ordinal()] = 21;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[States.BUG_GAME.ordinal()] = 38;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[States.CABIN.ordinal()] = 11;
            } catch (NoSuchFieldError e4) {
            }
            try {
                iArr[States.CASINO.ordinal()] = 14;
            } catch (NoSuchFieldError e5) {
            }
            try {
                iArr[States.CAVE.ordinal()] = 35;
            } catch (NoSuchFieldError e6) {
            }
            try {
                iArr[States.CLUB.ordinal()] = 6;
            } catch (NoSuchFieldError e7) {
            }
            try {
                iArr[States.COMPUTER.ordinal()] = 24;
            } catch (NoSuchFieldError e8) {
            }
            try {
                iArr[States.CRAB_GAME.ordinal()] = 39;
            } catch (NoSuchFieldError e9) {
            }
            try {
                iArr[States.DOWNTOWN.ordinal()] = 5;
            } catch (NoSuchFieldError e10) {
            }
            try {
                iArr[States.FIGHT.ordinal()] = 7;
            } catch (NoSuchFieldError e11) {
            }
            try {
                iArr[States.FINAL.ordinal()] = 41;
            } catch (NoSuchFieldError e12) {
            }
            try {
                iArr[States.FOREST.ordinal()] = 10;
            } catch (NoSuchFieldError e13) {
            }
            try {
                iArr[States.HALLWAY.ordinal()] = 17;
            } catch (NoSuchFieldError e14) {
            }
            try {
                iArr[States.HOUSE_ONE.ordinal()] = 27;
            } catch (NoSuchFieldError e15) {
            }
            try {
                iArr[States.HOUSE_THREE.ordinal()] = 29;
            } catch (NoSuchFieldError e16) {
            }
            try {
                iArr[States.HOUSE_TWO.ordinal()] = 28;
            } catch (NoSuchFieldError e17) {
            }
            try {
                iArr[States.HUNTING.ordinal()] = 36;
            } catch (NoSuchFieldError e18) {
            }
            try {
                iArr[States.INTRO.ordinal()] = 4;
            } catch (NoSuchFieldError e19) {
            }
            try {
                iArr[States.INVENTORY.ordinal()] = 37;
            } catch (NoSuchFieldError e20) {
            }
            try {
                iArr[States.LIGHTHOUSE.ordinal()] = 33;
            } catch (NoSuchFieldError e21) {
            }
            try {
                iArr[States.LOBBY.ordinal()] = 15;
            } catch (NoSuchFieldError e22) {
            }
            try {
                iArr[States.MALL.ordinal()] = 30;
            } catch (NoSuchFieldError e23) {
            }
            try {
                iArr[States.MAP.ordinal()] = 3;
            } catch (NoSuchFieldError e24) {
            }
            try {
                iArr[States.MAZE.ordinal()] = 34;
            } catch (NoSuchFieldError e25) {
            }
            try {
                iArr[States.MENU.ordinal()] = 2;
            } catch (NoSuchFieldError e26) {
            }
            try {
                iArr[States.OFFICE.ordinal()] = 18;
            } catch (NoSuchFieldError e27) {
            }
            try {
                iArr[States.OUTRO.ordinal()] = 40;
            } catch (NoSuchFieldError e28) {
            }
            try {
                iArr[States.PARK.ordinal()] = 26;
            } catch (NoSuchFieldError e29) {
            }
            try {
                iArr[States.PAWN.ordinal()] = 9;
            } catch (NoSuchFieldError e30) {
            }
            try {
                iArr[States.POLICE.ordinal()] = 12;
            } catch (NoSuchFieldError e31) {
            }
            try {
                iArr[States.POLICE_OFFICE.ordinal()] = 13;
            } catch (NoSuchFieldError e32) {
            }
            try {
                iArr[States.ROULETTE.ordinal()] = 23;
            } catch (NoSuchFieldError e33) {
            }
            try {
                iArr[States.RUNNING.ordinal()] = 1;
            } catch (NoSuchFieldError e34) {
            }
            try {
                iArr[States.SLOTS.ordinal()] = 8;
            } catch (NoSuchFieldError e35) {
            }
            try {
                iArr[States.STORE.ordinal()] = 31;
            } catch (NoSuchFieldError e36) {
            }
            try {
                iArr[States.SUBURBS.ordinal()] = 20;
            } catch (NoSuchFieldError e37) {
            }
            try {
                iArr[States.THEATER.ordinal()] = 32;
            } catch (NoSuchFieldError e38) {
            }
            try {
                iArr[States.TREE_FORT.ordinal()] = 25;
            } catch (NoSuchFieldError e39) {
            }
            try {
                iArr[States.VIP.ordinal()] = 19;
            } catch (NoSuchFieldError e40) {
            }
            try {
                iArr[States.WELCOME.ordinal()] = 22;
            } catch (NoSuchFieldError e41) {
            }
            $SWITCH_TABLE$kgm$NA$States = iArr;
        }
        return iArr;
    }

    public MapState(AssetManager am, Assets assets) {
        this.status = States.RUNNING;
        this.assets = assets;
        load(am, assets);
        this.nextState = null;
        this.isFading = false;
        this.fadeAlpha = 0;
        this.downtownText = new DSprite(assets.downtownText, 0.0833f, 0.066f);
        this.suburbsText = new DSprite(assets.suburbsText, 0.4675f, 0.093f);
        this.beachText = new DSprite(assets.beachText, 0.895f, 0.346f);
        this.forestText = new DSprite(assets.forestText, 0.255f, 0.75f);
        this.scalePct = 1.0f;
        this.scaleDown = true;
        this.touchedButton = null;
    }

    @Override // kgm.NA.State
    public void update(MotionEvent touch, KeyEvent key) {
        if (touch != null) {
            if (touch.getX() > Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.4138f) + Screen.relXZero && touch.getY() > Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.513f) + Screen.relYZero) {
                if (touch.getAction() == 0 || touch.getAction() == 2) {
                    this.touchedButton = TouchedButton.DOWNTOWN;
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                }
                if (touch.getAction() == 1) {
                    this.assets.touchUp.playSound(this.assets.sounds);
                    this.nextState = States.DOWNTOWN;
                    this.isFading = true;
                }
            }
            if (touch.getX() > (Screen.relScreenWidth * 0.4138f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.795f) + Screen.relXZero && touch.getY() > Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.513f) + Screen.relYZero) {
                if (touch.getAction() == 0 || touch.getAction() == 2) {
                    this.touchedButton = TouchedButton.SUBURBS;
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                }
                if (touch.getAction() == 1) {
                    this.assets.touchUp.playSound(this.assets.sounds);
                    this.nextState = States.SUBURBS;
                    this.isFading = true;
                }
            }
            if (touch.getX() > (Screen.relScreenWidth * 0.795f) + Screen.relXZero && touch.getX() < Screen.relScreenWidth + Screen.relXZero && touch.getY() > Screen.relYZero && touch.getY() < Screen.relScreenHeight + Screen.relYZero) {
                if (touch.getAction() == 0 || touch.getAction() == 2) {
                    this.touchedButton = TouchedButton.BEACH;
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                }
                if (touch.getAction() == 1) {
                    this.assets.touchUp.playSound(this.assets.sounds);
                    this.nextState = States.BEACH;
                    this.isFading = true;
                }
            }
            if (touch.getX() > Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.795f) + Screen.relXZero && touch.getY() > (Screen.relScreenHeight * 0.51f) + Screen.relYZero && touch.getY() < Screen.relScreenHeight + Screen.relYZero) {
                if (touch.getAction() == 0 || touch.getAction() == 2) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    this.touchedButton = TouchedButton.FOREST;
                }
                if (touch.getAction() == 1) {
                    this.assets.touchUp.playSound(this.assets.sounds);
                    this.nextState = States.FOREST;
                    this.isFading = true;
                }
            }
            touch.setLocation(-10.0f, -10.0f);
        }
        if (this.scaleDown) {
            this.scalePct -= 0.005f;
            if (this.scalePct < 0.9f) {
                this.scaleDown = false;
            }
        } else {
            this.scalePct += 0.005f;
            if (this.scalePct > 1.0f) {
                this.scaleDown = true;
                this.scalePct = 1.0f;
            }
        }
        if (this.isFading) {
            this.scalePct = 1.0f;
            this.downtownText.setAlpha(0);
            this.suburbsText.setAlpha(0);
            this.beachText.setAlpha(0);
            this.forestText.setAlpha(0);
            switch ($SWITCH_TABLE$kgm$NA$States()[this.nextState.ordinal()]) {
                case 5:
                    this.downtownText.setAlpha(MotionEventCompat.ACTION_MASK);
                    break;
                case 10:
                    this.forestText.setAlpha(MotionEventCompat.ACTION_MASK);
                    break;
                case 20:
                    this.suburbsText.setAlpha(MotionEventCompat.ACTION_MASK);
                    break;
                case 21:
                    this.beachText.setAlpha(MotionEventCompat.ACTION_MASK);
                    break;
            }
        }
        if (this.touchedButton == TouchedButton.DOWNTOWN) {
            this.downtownText.scale(0.9f, 0.9f);
        } else {
            this.downtownText.scale(this.scalePct, this.scalePct);
        }
        if (this.touchedButton == TouchedButton.SUBURBS) {
            this.suburbsText.scale(0.9f, 0.9f);
        } else {
            this.suburbsText.scale(this.scalePct, this.scalePct);
        }
        if (this.touchedButton == TouchedButton.BEACH) {
            this.beachText.scale(0.9f, 0.9f);
        } else {
            this.beachText.scale(this.scalePct, this.scalePct);
        }
        if (this.touchedButton == TouchedButton.FOREST) {
            this.forestText.scale(0.9f, 0.9f);
        } else {
            this.forestText.scale(this.scalePct, this.scalePct);
        }
        if (this.isFading) {
            this.fadeAlpha += 8;
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
        canvas.drawBitmap(this.assets.map.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        this.downtownText.draw(canvas);
        this.suburbsText.draw(canvas);
        this.beachText.draw(canvas);
        this.forestText.draw(canvas);
        canvas.drawARGB(this.fadeAlpha, 0, 0, 0);
    }

    @Override // kgm.NA.State
    public void load(AssetManager am, Assets assets) {
        assets.map.load(am, "map.png", 400, 300);
        assets.downtownText.load(am, "downtown-text.png", 107, 106);
        assets.suburbsText.load(am, "suburbs-text.png", 96, 97);
        assets.beachText.load(am, "beach-text.png", 17, 75);
        assets.forestText.load(am, "forest-text.png", 98, 19);
        assets.touchDown.load(assets.sounds, am, "touch-down.ogg");
        assets.touchUp.load(assets.sounds, am, "touch-up.ogg");
    }

    @Override // kgm.NA.State
    public void delete() {
        this.assets.map.delete();
        this.assets.downtownText.delete();
        this.assets.suburbsText.delete();
        this.assets.beachText.delete();
        this.assets.forestText.delete();
        this.assets.touchDown.delete(this.assets.sounds);
        this.assets.touchUp.delete(this.assets.sounds);
    }

    @Override // kgm.NA.State
    public void pause() {
    }

    @Override // kgm.NA.State
    public void resume() {
    }
}
