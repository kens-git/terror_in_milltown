package kgm.NA;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;

/* loaded from: classes.dex */
public class WelcomeState extends State {
    int fadeAlpha;
    boolean isFading;
    String[] lines = {"Welcome to Milltown! The city is in distress as", "multiple residents have seemingly vanished", "into thin air. Explore the city, talk to the...", "residents and uncover the mystery of the", "disappearences before more people go", "missing."};
    States nextState;
    TextBox textBox;

    public WelcomeState(AssetManager am, Assets assets) {
        this.status = States.RUNNING;
        this.assets = assets;
        load(am, assets);
        this.isFading = false;
        this.fadeAlpha = 0;
        this.textBox = new TextBox("intro", this.lines, 6);
        this.textBox.start();
    }

    @Override // kgm.NA.State
    public void update(MotionEvent touch, KeyEvent key) {
        if (key != null) {
            key.getKeyCode();
        }
        if (touch != null) {
            if (touch.getX() > Screen.relXZero && touch.getAction() == 1 && this.textBox != null && !this.textBox.isFinished && this.textBox.isPaused) {
                this.textBox.resume();
                this.assets.touchUp.playSound(this.assets.sounds);
            }
            touch.setLocation(-10.0f, -10.0f);
        }
        if (this.textBox != null && this.textBox.isFinished) {
            this.textBox = null;
            this.isFading = true;
            this.nextState = States.MAP;
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
        canvas.drawBitmap(this.assets.welcomeScreen.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        if (this.textBox != null) {
            this.textBox.draw(canvas);
        }
        canvas.drawARGB(this.fadeAlpha, 0, 0, 0);
    }

    @Override // kgm.NA.State
    public void load(AssetManager am, Assets assets) {
        assets.welcomeScreen.load(am, "welcome-screen.png", 400, 300);
        assets.textBox.load(assets.sounds, am, "textbox.ogg");
        assets.touchUp.load(assets.sounds, am, "touch-up.ogg");
    }

    @Override // kgm.NA.State
    public void delete() {
        this.assets.welcomeScreen.delete();
        this.assets.textBox.delete(this.assets.sounds);
        this.assets.touchUp.delete(this.assets.sounds);
    }

    @Override // kgm.NA.State
    public void pause() {
    }

    @Override // kgm.NA.State
    public void resume() {
    }
}
