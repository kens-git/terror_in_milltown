package kgm.NA;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;
import java.util.Random;

/* loaded from: classes.dex */
public class HouseTwoState extends State {
    int fadeAlpha;
    long headTimer;
    long headUpdateTime;
    boolean isFading;
    States nextState;
    TextBox textBox;
    Random rand = new Random();
    String[] lines1 = {"\"I can't beat this level!\""};
    String[] lines2 = {"\"I miss my friend.\""};
    int headDir = this.rand.nextInt(2) + 1;

    public HouseTwoState(AssetManager am, Assets assets) {
        this.status = States.RUNNING;
        this.assets = assets;
        load(am, assets);
        this.isFading = false;
        this.fadeAlpha = 0;
        this.headUpdateTime = System.currentTimeMillis();
        this.headTimer = this.rand.nextInt(3000) + 500;
    }

    @Override // kgm.NA.State
    public void update(MotionEvent touch, KeyEvent key) {
        if (key != null) {
            key.getKeyCode();
        }
        if (touch != null && !this.isFading) {
            if (this.textBox == null) {
                if (touch.getY() > (Screen.relScreenHeight * 0.0666f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.5333f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.695f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.905f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        this.nextState = States.SUBURBS;
                        this.isFading = true;
                        this.assets.touchUp.playSound(this.assets.sounds);
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.5666f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.8833f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.4625f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.6175f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        if (!SaveFile.beatBugGame) {
                            this.textBox = new TextBox("game", this.lines1, 1);
                            this.textBox.start();
                        } else {
                            this.textBox = new TextBox("kid", this.lines2, 1);
                            this.textBox.start();
                        }
                        this.headDir = 1;
                        this.assets.textBox.playSound(this.assets.sounds);
                    }
                }
            }
            if (touch.getX() > Screen.relXZero && touch.getAction() == 1 && this.textBox != null && !this.textBox.isFinished && this.textBox.isPaused) {
                this.textBox.resume();
                this.assets.touchUp.playSound(this.assets.sounds);
            }
            touch.setLocation(-10.0f, -10.0f);
        }
        if (this.textBox != null) {
            if (this.textBox.id == "game" && this.textBox.isFinished) {
                this.isFading = true;
                this.nextState = States.BUG_GAME;
            }
            if (this.textBox.isFinished) {
                this.textBox = null;
            }
        }
        if (System.currentTimeMillis() - this.headUpdateTime > this.headTimer) {
            this.headDir = this.rand.nextInt(2) + 1;
            this.headUpdateTime = System.currentTimeMillis();
            this.headTimer = this.rand.nextInt(3000) + 500;
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
        canvas.drawBitmap(this.assets.houseTwo.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        if (this.headDir == 1) {
            canvas.drawBitmap(this.assets.kidHeadCenter.bitmap, (Screen.relScreenWidth * 0.5382f) + Screen.relXZero, (Screen.relScreenHeight * 0.5834f) + Screen.relYZero, (Paint) null);
        } else {
            canvas.drawBitmap(this.assets.kidHeadSide.bitmap, (Screen.relScreenWidth * 0.5303f) + Screen.relXZero, (Screen.relScreenHeight * 0.5785f) + Screen.relYZero, (Paint) null);
        }
        if (this.textBox != null) {
            this.textBox.draw(canvas);
        }
        canvas.drawARGB(this.fadeAlpha, 0, 0, 0);
    }

    @Override // kgm.NA.State
    public void load(AssetManager am, Assets assets) {
        assets.houseTwo.load(am, "house-two.png", 400, 300);
        assets.kidHeadCenter.load(am, "kid-head-center.png", 21, 29);
        assets.kidHeadSide.load(am, "kid-head-side.png", 29, 27);
        assets.touchDown.load(assets.sounds, am, "touch-down.ogg");
        assets.touchUp.load(assets.sounds, am, "touch-up.ogg");
        assets.textBox.load(assets.sounds, am, "textbox.ogg");
    }

    @Override // kgm.NA.State
    public void delete() {
        this.assets.houseTwo.delete();
        this.assets.kidHeadCenter.delete();
        this.assets.kidHeadSide.delete();
        this.assets.touchDown.delete(this.assets.sounds);
        this.assets.touchUp.delete(this.assets.sounds);
        this.assets.textBox.delete(this.assets.sounds);
    }

    @Override // kgm.NA.State
    public void pause() {
    }

    @Override // kgm.NA.State
    public void resume() {
    }
}
