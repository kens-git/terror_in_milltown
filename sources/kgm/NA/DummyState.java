package kgm.NA;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;
import java.util.Random;

/* loaded from: classes.dex */
public class DummyState extends State {
    int fadeAlpha;
    long headTimer;
    long headUpdateTime;
    boolean isFading;
    States nextState;
    TextBox textBox;
    Random rand = new Random();
    String[] guyLines1 = {"\"That guy is always lurking outside.\""};
    String[] guyLines2 = {"\"Only some of this stuff is stolen.\""};
    String[] guyLines3 = {"\"I'm looking for game consoles in case you", "have any or know where to get some, if you", "catch my drift.\""};
    int headDir = 1;

    public DummyState(AssetManager am, Assets assets) {
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
        if (touch != null) {
            if (touch.getY() > (Screen.relScreenHeight * 0.2833f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.5833f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.58f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.7375f) + Screen.relXZero) {
                touch.getAction();
                if (touch.getAction() == 1) {
                    int random = this.rand.nextInt(3) + 1;
                    switch (random) {
                        case 1:
                            this.textBox = new TextBox("guy", this.guyLines1, 1);
                            break;
                        case 2:
                            this.textBox = new TextBox("guy", this.guyLines2, 1);
                            break;
                        case 3:
                            this.textBox = new TextBox("guy", this.guyLines3, 3);
                            break;
                    }
                    this.textBox.start();
                }
            }
            if (touch.getX() > Screen.relXZero && this.textBox != null && !this.textBox.isFinished && this.textBox.isPaused) {
                this.textBox.resume();
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
        canvas.drawBitmap(this.assets.pawnShop.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        if (this.headDir == 1) {
            canvas.drawBitmap(this.assets.pawnGuyHeadLeft.bitmap, (Screen.relScreenWidth * 0.6243f) + Screen.relXZero, (Screen.relScreenHeight * 0.2935f) + Screen.relYZero, (Paint) null);
        } else {
            canvas.drawBitmap(this.assets.pawnGuyHeadCenter.bitmap, (Screen.relScreenWidth * 0.6212f) + Screen.relXZero, (Screen.relScreenHeight * 0.2935f) + Screen.relYZero, (Paint) null);
        }
        if (!SaveFile.hasBat) {
            canvas.drawBitmap(this.assets.pawnBat.bitmap, (Screen.relScreenWidth * 0.3311f) + Screen.relXZero, (Screen.relScreenHeight * 0.0757f) + Screen.relYZero, (Paint) null);
        }
        if (!SaveFile.hasRacket) {
            canvas.drawBitmap(this.assets.pawnRacket.bitmap, (Screen.relScreenWidth * 0.821f) + Screen.relXZero, (Screen.relScreenHeight * 0.069f) + Screen.relYZero, (Paint) null);
        }
        if (this.textBox != null) {
            this.textBox.draw(canvas);
        }
        canvas.drawARGB(this.fadeAlpha, 0, 0, 0);
    }

    @Override // kgm.NA.State
    public void load(AssetManager am, Assets assets) {
    }

    @Override // kgm.NA.State
    public void delete() {
    }

    @Override // kgm.NA.State
    public void pause() {
    }

    @Override // kgm.NA.State
    public void resume() {
    }
}
