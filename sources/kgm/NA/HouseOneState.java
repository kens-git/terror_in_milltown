package kgm.NA;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;
import java.util.Random;

/* loaded from: classes.dex */
public class HouseOneState extends State {
    int fadeAlpha;
    long headTimer;
    long headUpdateTime;
    boolean isFading;
    States nextState;
    TextBox textBox;
    Random rand = new Random();
    String[] lines1 = {"\"Take this - it should help.\""};
    String[] lines2 = {"\"Please help find our son.\""};
    int headDir = this.rand.nextInt(2) + 1;

    public HouseOneState(AssetManager am, Assets assets) {
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
                if (touch.getY() > (Screen.relScreenHeight * 0.1666f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.7666f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.67f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.95f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        this.nextState = States.SUBURBS;
                        this.isFading = true;
                        this.assets.touchUp.playSound(this.assets.sounds);
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.4166f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.9f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.25f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.5f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        if (SaveFile.cash < 250) {
                            SaveFile.cash += 250;
                            this.assets.purchase.playSound(this.assets.sounds);
                            this.textBox = new TextBox("lines", this.lines1, 1);
                            this.textBox.start();
                        } else {
                            this.textBox = new TextBox("lines", this.lines2, 1);
                            this.textBox.start();
                        }
                        this.assets.textBox.playSound(this.assets.sounds);
                        this.headDir = 1;
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
            this.textBox = null;
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
        canvas.drawBitmap(this.assets.houseOne.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        if (this.headDir == 1) {
            canvas.drawBitmap(this.assets.familyGuyHeadCenter.bitmap, (Screen.relScreenWidth * 0.3858f) + Screen.relXZero, (Screen.relScreenHeight * 0.4344f) + Screen.relYZero, (Paint) null);
        } else {
            canvas.drawBitmap(this.assets.familyGuyHeadSide.bitmap, (Screen.relScreenWidth * 0.3755f) + Screen.relXZero, (Screen.relScreenHeight * 0.4295f) + Screen.relYZero, (Paint) null);
        }
        if (this.textBox != null) {
            this.textBox.draw(canvas);
        }
        canvas.drawARGB(this.fadeAlpha, 0, 0, 0);
    }

    @Override // kgm.NA.State
    public void load(AssetManager am, Assets assets) {
        assets.houseOne.load(am, "house-one.png", 400, 300);
        assets.familyGuyHeadCenter.load(am, "family-guy-head-center.png", 18, 18);
        assets.familyGuyHeadSide.load(am, "family-guy-head-side.png", 19, 19);
        assets.touchDown.load(assets.sounds, am, "touch-down.ogg");
        assets.touchUp.load(assets.sounds, am, "touch-up.ogg");
        assets.textBox.load(assets.sounds, am, "textbox.ogg");
        assets.purchase.load(assets.sounds, am, "purchase.ogg");
    }

    @Override // kgm.NA.State
    public void delete() {
        this.assets.houseOne.delete();
        this.assets.familyGuyHeadCenter.delete();
        this.assets.familyGuyHeadSide.delete();
        this.assets.touchDown.delete(this.assets.sounds);
        this.assets.touchUp.delete(this.assets.sounds);
        this.assets.textBox.delete(this.assets.sounds);
        this.assets.purchase.delete(this.assets.sounds);
    }

    @Override // kgm.NA.State
    public void pause() {
    }

    @Override // kgm.NA.State
    public void resume() {
    }
}
