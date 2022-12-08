package kgm.NA;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;

/* loaded from: classes.dex */
public class OfficeState extends State {
    int fadeAlpha;
    boolean isFading;
    States nextState;

    public OfficeState(AssetManager am, Assets assets) {
        this.isFading = true;
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
            if (touch.getY() > (Screen.relScreenHeight * 0.133f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.5f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.116f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.2675f) + Screen.relXZero) {
                if (touch.getAction() == 0) {
                    this.assets.touchDown.playSound(this.assets.sounds);
                }
                if (touch.getAction() == 1) {
                    this.nextState = States.HALLWAY;
                    this.isFading = true;
                    this.assets.touchUp.playSound(this.assets.sounds);
                }
            }
            if (touch.getY() > (Screen.relScreenHeight * 0.54f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.75f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.175f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.3625f) + Screen.relXZero) {
                if (touch.getAction() == 0) {
                    this.assets.touchDown.playSound(this.assets.sounds);
                }
                if (touch.getAction() == 1) {
                    this.nextState = States.COMPUTER;
                    this.isFading = true;
                    this.assets.touchUp.playSound(this.assets.sounds);
                }
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
        canvas.drawBitmap(this.assets.office.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        canvas.drawARGB(this.fadeAlpha, 0, 0, 0);
    }

    @Override // kgm.NA.State
    public void load(AssetManager am, Assets assets) {
        assets.office.load(am, "office.png", 400, 300);
        assets.touchDown.load(assets.sounds, am, "touch-down.ogg");
        assets.touchUp.load(assets.sounds, am, "touch-up.ogg");
    }

    @Override // kgm.NA.State
    public void delete() {
        this.assets.office.delete();
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
