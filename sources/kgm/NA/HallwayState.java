package kgm.NA;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;

/* loaded from: classes.dex */
public class HallwayState extends State {
    int fadeAlpha;
    boolean isFading;
    States nextState;

    public HallwayState(AssetManager am, Assets assets) {
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
            if (touch.getY() > (Screen.relScreenHeight * 0.25f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.65f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.312f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.662f) + Screen.relXZero) {
                if (touch.getAction() == 0) {
                    this.assets.touchDown.playSound(this.assets.sounds);
                }
                if (touch.getAction() == 1) {
                    this.nextState = States.OFFICE;
                    this.isFading = true;
                    this.assets.touchUp.playSound(this.assets.sounds);
                }
            }
            if (touch.getY() > (Screen.relScreenHeight * 0.233f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.983f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.737f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.965f) + Screen.relXZero) {
                if (touch.getAction() == 0) {
                    this.assets.touchDown.playSound(this.assets.sounds);
                }
                if (touch.getAction() == 1) {
                    this.nextState = States.LOBBY;
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
        canvas.drawBitmap(this.assets.hallway.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        canvas.drawARGB(this.fadeAlpha, 0, 0, 0);
    }

    @Override // kgm.NA.State
    public void load(AssetManager am, Assets assets) {
        assets.hallway.load(am, "hallway.png", 400, 300);
        assets.touchDown.load(assets.sounds, am, "touch-down.ogg");
        assets.touchUp.load(assets.sounds, am, "touch-up.ogg");
    }

    @Override // kgm.NA.State
    public void delete() {
        this.assets.hallway.delete();
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
