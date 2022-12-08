package kgm.NA;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;
import java.util.Random;

/* loaded from: classes.dex */
public class CabinState extends State {
    int fadeAlpha;
    long headTimer;
    long headUpdateTime;
    boolean isFading;
    States nextState;
    TextBox textBox;
    Random rand = new Random();
    int headDir = this.rand.nextInt(2) + 1;
    String[] lines1 = {"\"The animals have been acting kind of funny.\""};
    String[] lines2 = {"\"I think that's the last of them. Be safe out", "there.\""};

    public CabinState(AssetManager am, Assets assets) {
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
                if (touch.getY() > (Screen.relScreenHeight * 0.1666f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.75f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.0675f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.305f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        this.isFading = true;
                        this.nextState = States.FOREST;
                        this.assets.touchUp.playSound(this.assets.sounds);
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.1666f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.7666f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.625f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.805f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        if (!SaveFile.beatHuntingGame) {
                            this.textBox = new TextBox("game", this.lines1, 1);
                            this.textBox.start();
                        } else {
                            this.textBox = new TextBox("fuck", this.lines2, 2);
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
                this.nextState = States.HUNTING;
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
        canvas.drawBitmap(this.assets.cabin.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        if (this.headDir == 1) {
            canvas.drawBitmap(this.assets.lumberjackHeadSide.bitmap, (Screen.relScreenWidth * 0.6692f) + Screen.relXZero, (Screen.relScreenHeight * 0.1715f) + Screen.relYZero, (Paint) null);
        } else {
            canvas.drawBitmap(this.assets.lumberjackHeadBack.bitmap, (Screen.relScreenWidth * 0.675f) + Screen.relXZero, (Screen.relScreenHeight * 0.1751f) + Screen.relYZero, (Paint) null);
        }
        canvas.drawBitmap(this.assets.lumberjackBody.bitmap, (Screen.relScreenWidth * 0.6343f) + Screen.relXZero, (Screen.relScreenHeight * 0.2799f) + Screen.relYZero, (Paint) null);
        if (this.textBox != null) {
            this.textBox.draw(canvas);
        }
        canvas.drawARGB(this.fadeAlpha, 0, 0, 0);
    }

    @Override // kgm.NA.State
    public void load(AssetManager am, Assets assets) {
        assets.cabin.load(am, "cabin.png", 400, 300);
        assets.lumberjackBody.load(am, "lumberjack-body.png", 64, 144);
        assets.lumberjackHeadBack.load(am, "lumberjack-head-back.png", 30, 37);
        assets.lumberjackHeadSide.load(am, "lumberjack-head-side.png", 34, 37);
        assets.touchDown.load(assets.sounds, am, "touch-down.ogg");
        assets.touchUp.load(assets.sounds, am, "touch-up.ogg");
        assets.textBox.load(assets.sounds, am, "textbox.ogg");
    }

    @Override // kgm.NA.State
    public void delete() {
        this.assets.cabin.delete();
        this.assets.lumberjackBody.delete();
        this.assets.lumberjackHeadBack.delete();
        this.assets.lumberjackHeadSide.delete();
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
