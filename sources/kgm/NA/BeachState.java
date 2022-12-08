package kgm.NA;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;
import java.util.Random;

/* loaded from: classes.dex */
public class BeachState extends State {
    int fadeAlpha;
    int fishermanHeadDir;
    long fishermanHeadTimer;
    long fishermanHeadUpdateTime;
    boolean isFading;
    int kidHeadDir;
    long kidHeadTimer;
    long kidHeadUpdateTime;
    States nextState;
    TextBox textBox;
    Random rand = new Random();
    String[] fishermanLines = {"\"Great day for fishing!\""};
    String[] kidLines1 = {"\"These stupid crabs keep ruining my sand", "castle!\""};
    String[] kidLines2 = {"\"They came back!\""};
    String[] lockedLines = {"It's locked."};

    public BeachState(AssetManager am, Assets assets) {
        this.kidHeadDir = 1;
        this.status = States.RUNNING;
        this.assets = assets;
        load(am, assets);
        this.isFading = false;
        this.fadeAlpha = 0;
        this.kidHeadUpdateTime = System.currentTimeMillis();
        this.kidHeadTimer = this.rand.nextInt(3000) + 500;
        this.kidHeadDir = this.rand.nextInt(2) + 1;
        this.fishermanHeadUpdateTime = System.currentTimeMillis();
        this.fishermanHeadTimer = this.rand.nextInt(3000) + 500;
        this.fishermanHeadDir = this.rand.nextInt(2) + 1;
        assets.beachBackground.playRand(true);
    }

    @Override // kgm.NA.State
    public void update(MotionEvent touch, KeyEvent key) {
        if (key != null) {
            key.getKeyCode();
        }
        if (touch != null) {
            if (this.textBox == null) {
                if (touch.getY() > Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.3166f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.855f) + Screen.relXZero && touch.getX() < Screen.relScreenWidth + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        if (SaveFile.hasLighthouseKey) {
                            this.isFading = true;
                            this.nextState = States.LIGHTHOUSE;
                            this.assets.touchUp.playSound(this.assets.sounds);
                        } else {
                            this.textBox = new TextBox("locked", this.lockedLines, 1);
                            this.textBox.start();
                            this.assets.textBox.playSound(this.assets.sounds);
                        }
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.8f) + Screen.relYZero && touch.getY() < Screen.relScreenHeight + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.0875f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.25f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        this.nextState = States.MAP;
                        this.isFading = true;
                        this.assets.touchUp.playSound(this.assets.sounds);
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.8f) + Screen.relYZero && touch.getY() < Screen.relScreenHeight + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.8125f) + Screen.relXZero && touch.getX() < Screen.relScreenWidth + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        this.nextState = States.INVENTORY;
                        this.isFading = true;
                        this.assets.touchUp.playSound(this.assets.sounds);
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.6166f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.8666f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.39f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.695f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        if (!SaveFile.hasLighthouseKey) {
                            this.textBox = new TextBox("kid", this.kidLines1, 1);
                        } else {
                            this.textBox = new TextBox("kid", this.kidLines2, 1);
                        }
                        this.textBox.start();
                        this.assets.textBox.playSound(this.assets.sounds);
                        this.kidHeadDir = 1;
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.1866f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.4333f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.1875f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.315f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        this.textBox = new TextBox("fisherman", this.fishermanLines, 1);
                        this.textBox.start();
                        this.assets.textBox.playSound(this.assets.sounds);
                        this.fishermanHeadDir = 1;
                    }
                }
            }
            if (touch.getX() > Screen.relXZero && touch.getAction() == 1 && this.textBox != null && !this.textBox.isFinished && this.textBox.isPaused) {
                this.textBox.resume();
                this.assets.touchUp.playSound(this.assets.sounds);
            }
            touch.setLocation(-10.0f, -10.0f);
        }
        if (this.textBox != null && this.textBox.isFinished) {
            if (this.textBox.id == "kid") {
                this.isFading = true;
                this.nextState = States.CRAB_GAME;
            }
            this.textBox = null;
        }
        if (System.currentTimeMillis() - this.kidHeadUpdateTime > this.kidHeadTimer) {
            this.kidHeadDir = this.rand.nextInt(2) + 1;
            this.kidHeadUpdateTime = System.currentTimeMillis();
            this.kidHeadTimer = this.rand.nextInt(3000) + 500;
        }
        if (System.currentTimeMillis() - this.fishermanHeadUpdateTime > this.fishermanHeadTimer) {
            this.fishermanHeadDir = this.rand.nextInt(2) + 1;
            this.fishermanHeadUpdateTime = System.currentTimeMillis();
            this.fishermanHeadTimer = this.rand.nextInt(3000) + 500;
        }
        if (this.isFading) {
            this.fadeAlpha += 16;
            if (this.fadeAlpha > 255) {
                this.fadeAlpha = MotionEventCompat.ACTION_MASK;
            }
            if (this.fadeAlpha == 255) {
                this.status = this.nextState;
                Engine.lastState = States.BEACH;
            }
        }
    }

    @Override // kgm.NA.State
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawBitmap(this.assets.beach.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        canvas.drawBitmap(this.assets.iconMap.bitmap, (Screen.relScreenWidth * 0.1109f) + Screen.relXZero, (Screen.relScreenHeight * 0.8257f) + Screen.relYZero, (Paint) null);
        canvas.drawBitmap(this.assets.iconInventory.bitmap, (Screen.relScreenWidth * 0.8308f) + Screen.relXZero, (Screen.relScreenHeight * 0.8238f) + Screen.relYZero, (Paint) null);
        if (this.kidHeadDir == 1) {
            canvas.drawBitmap(this.assets.beachKidHeadCenter.bitmap, (Screen.relScreenWidth * 0.4811f) + Screen.relXZero, (Screen.relScreenHeight * 0.6386f) + Screen.relYZero, (Paint) null);
        } else {
            canvas.drawBitmap(this.assets.beachKidHeadTurned.bitmap, (Screen.relScreenWidth * 0.4883f) + Screen.relXZero, (Screen.relScreenHeight * 0.6397f) + Screen.relYZero, (Paint) null);
        }
        if (this.fishermanHeadDir == 1) {
            canvas.drawBitmap(this.assets.fishermanHeadCenter.bitmap, (Screen.relScreenWidth * 0.2093f) + Screen.relXZero, (Screen.relScreenHeight * 0.246f) + Screen.relYZero, (Paint) null);
        } else {
            canvas.drawBitmap(this.assets.fishermanHeadSide.bitmap, (Screen.relScreenWidth * 0.2145f) + Screen.relXZero, (Screen.relScreenHeight * 0.2445f) + Screen.relYZero, (Paint) null);
        }
        if (this.textBox != null) {
            this.textBox.draw(canvas);
        }
        canvas.drawARGB(this.fadeAlpha, 0, 0, 0);
    }

    @Override // kgm.NA.State
    public void load(AssetManager am, Assets assets) {
        assets.beach.load(am, "beach.png", 400, 300);
        assets.iconMap.load(am, "icon-map.png", 47, 47);
        assets.iconInventory.load(am, "icon-inventory.png", 60, 45);
        assets.fishermanHeadCenter.load(am, "fisherman-head-center.png", 12, 9);
        assets.fishermanHeadSide.load(am, "fisherman-head-side.png", 12, 10);
        assets.beachKidHeadCenter.load(am, "beach-kid-head-center.png", 11, 13);
        assets.beachKidHeadTurned.load(am, "beach-kid-head-turned.png", 11, 12);
        assets.touchDown.load(assets.sounds, am, "touch-down.ogg");
        assets.touchUp.load(assets.sounds, am, "touch-up.ogg");
        assets.textBox.load(assets.sounds, am, "textbox.ogg");
        assets.beachBackground.load(am, "beach-background.ogg");
    }

    @Override // kgm.NA.State
    public void delete() {
        this.assets.beach.delete();
        this.assets.iconMap.delete();
        this.assets.iconInventory.delete();
        this.assets.fishermanHeadCenter.delete();
        this.assets.fishermanHeadSide.delete();
        this.assets.beachKidHeadCenter.delete();
        this.assets.beachKidHeadTurned.delete();
        this.assets.touchDown.delete(this.assets.sounds);
        this.assets.touchUp.delete(this.assets.sounds);
        this.assets.textBox.delete(this.assets.sounds);
        this.assets.beachBackground.delete();
    }

    @Override // kgm.NA.State
    public void pause() {
        this.assets.beachBackground.pause();
    }

    @Override // kgm.NA.State
    public void resume() {
        this.assets.beachBackground.pause();
    }
}
