package kgm.NA;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;

/* loaded from: classes.dex */
public class MallState extends State {
    int fadeAlpha;
    boolean isFading;
    States nextState;

    public MallState(AssetManager am, Assets assets) {
        this.status = States.RUNNING;
        this.assets = assets;
        load(am, assets);
        this.isFading = false;
        this.fadeAlpha = 0;
        assets.mallMusic.playRand(true);
        assets.mallMusic.setVolume(0.5f);
    }

    @Override // kgm.NA.State
    public void update(MotionEvent touch, KeyEvent key) {
        if (key != null) {
            key.getKeyCode();
        }
        if (touch != null && !this.isFading) {
            if (touch.getY() > (Screen.relScreenHeight * 0.2f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.3375f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.77f) + Screen.relXZero && touch.getX() < Screen.relScreenWidth + Screen.relXZero) {
                if (touch.getAction() == 0) {
                    this.assets.touchDown.playSound(this.assets.sounds);
                }
                if (touch.getAction() == 1) {
                    this.nextState = States.SUBURBS;
                    this.isFading = true;
                    this.assets.touchUp.playSound(this.assets.sounds);
                }
            }
            if (touch.getY() > (Screen.relScreenHeight * 0.1266f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.71f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.2575f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.615f) + Screen.relXZero) {
                if (touch.getAction() == 0) {
                    this.assets.touchDown.playSound(this.assets.sounds);
                }
                if (touch.getAction() == 1) {
                    this.nextState = States.STORE;
                    this.isFading = true;
                    this.assets.touchUp.playSound(this.assets.sounds);
                }
            }
            if (touch.getY() > (Screen.relScreenHeight * 0.28f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.7066f) + Screen.relYZero && touch.getX() > Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.2175f) + Screen.relXZero) {
                if (touch.getAction() == 0) {
                    this.assets.touchDown.playSound(this.assets.sounds);
                }
                if (touch.getAction() == 1) {
                    this.nextState = States.THEATER;
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
        canvas.drawBitmap(this.assets.mall.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        canvas.drawARGB(this.fadeAlpha, 0, 0, 0);
    }

    @Override // kgm.NA.State
    public void load(AssetManager am, Assets assets) {
        assets.mall.load(am, "mall.png", 400, 300);
        assets.touchDown.load(assets.sounds, am, "touch-down.ogg");
        assets.touchUp.load(assets.sounds, am, "touch-up.ogg");
        assets.mallMusic.load(am, "mall-music.ogg");
    }

    @Override // kgm.NA.State
    public void delete() {
        this.assets.mall.delete();
        this.assets.touchDown.delete(this.assets.sounds);
        this.assets.touchUp.delete(this.assets.sounds);
        this.assets.mallMusic.delete();
    }

    @Override // kgm.NA.State
    public void pause() {
        this.assets.mallMusic.pause();
    }

    @Override // kgm.NA.State
    public void resume() {
        this.assets.mallMusic.resume();
    }
}
