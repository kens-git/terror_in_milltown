package kgm.NA;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;
import java.util.Random;

/* loaded from: classes.dex */
public class TreeFortState extends State {
    int fadeAlpha;
    boolean isFading;
    States nextState;
    TextBox textBox;
    Random rand = new Random();
    String[] lines1 = {"\"I saw something weird in the forest the", "other day.\""};
    String[] lines2 = {"\"I saw some guys in suits in the forest. I", "wonder what they were doing?\""};

    public TreeFortState(AssetManager am, Assets assets) {
        this.status = States.RUNNING;
        this.assets = assets;
        load(am, assets);
        this.isFading = false;
        this.fadeAlpha = 0;
    }

    @Override // kgm.NA.State
    public void update(MotionEvent touch, KeyEvent key) {
        if (key != null) {
            key.getKeyCode();
        }
        if (touch != null && !this.isFading) {
            if (this.textBox == null) {
                if (touch.getY() > (Screen.relScreenHeight * 0.0733f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.7666f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.0225f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.1875f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        this.nextState = States.FOREST;
                        this.isFading = true;
                        this.assets.touchUp.playSound(this.assets.sounds);
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.4333f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.8f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.42f) + Screen.relXZero && touch.getX() < Screen.relScreenWidth + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        int num = this.rand.nextInt(2) + 1;
                        if (num == 1) {
                            this.textBox = new TextBox("kid", this.lines1, 2);
                        } else {
                            this.textBox = new TextBox("kid", this.lines2, 2);
                        }
                        this.textBox.start();
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
        if (this.textBox != null && this.textBox.isFinished) {
            this.textBox = null;
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
        canvas.drawBitmap(this.assets.treeFort.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        if (this.textBox != null) {
            this.textBox.draw(canvas);
        }
        canvas.drawARGB(this.fadeAlpha, 0, 0, 0);
    }

    @Override // kgm.NA.State
    public void load(AssetManager am, Assets assets) {
        assets.treeFort.load(am, "tree-fort.png", 400, 300);
        assets.touchDown.load(assets.sounds, am, "touch-down.ogg");
        assets.touchUp.load(assets.sounds, am, "touch-up.ogg");
        assets.textBox.load(assets.sounds, am, "textbox.ogg");
    }

    @Override // kgm.NA.State
    public void delete() {
        this.assets.treeFort.delete();
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
