package kgm.NA;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;

/* loaded from: classes.dex */
public class CasinoState extends State {
    int fadeAlpha;
    boolean isFading;
    States nextState;

    public CasinoState(AssetManager am, Assets assets) {
        this.status = States.RUNNING;
        this.assets = assets;
        load(am, assets);
        this.isFading = false;
        this.fadeAlpha = 0;
        assets.casinoMusic.playRand(true);
        assets.casinoMusic.setVolume(0.75f);
    }

    @Override // kgm.NA.State
    public void update(MotionEvent touch, KeyEvent key) {
        if (key != null) {
            key.getKeyCode();
        }
        if (touch != null && !this.isFading) {
            if (touch.getY() > (Screen.relScreenHeight * 0.283f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.566f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.05f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.177f) + Screen.relXZero) {
                if (touch.getAction() == 0) {
                    this.assets.touchDown.playSound(this.assets.sounds);
                }
                if (touch.getAction() == 1) {
                    this.nextState = States.BACKROOM;
                    this.isFading = true;
                    this.assets.touchUp.playSound(this.assets.sounds);
                }
            }
            if (touch.getY() > (Screen.relScreenHeight * 0.333f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.616f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.262f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.737f) + Screen.relXZero) {
                if (touch.getAction() == 0) {
                    this.assets.touchDown.playSound(this.assets.sounds);
                }
                if (touch.getAction() == 1) {
                    this.nextState = States.SLOTS;
                    this.isFading = true;
                    this.assets.touchUp.playSound(this.assets.sounds);
                }
            }
            if (touch.getY() > (Screen.relScreenHeight * 0.65f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.9166f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.075f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.9125f) + Screen.relXZero) {
                if (touch.getAction() == 0) {
                    this.assets.touchDown.playSound(this.assets.sounds);
                }
                if (touch.getAction() == 1) {
                    this.nextState = States.ROULETTE;
                    this.isFading = true;
                    this.assets.touchUp.playSound(this.assets.sounds);
                }
            }
            if (touch.getY() > (Screen.relScreenHeight * 0.133f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.566f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.835f) + Screen.relXZero && touch.getX() < Screen.relScreenWidth + Screen.relXZero) {
                if (touch.getAction() == 0) {
                    this.assets.touchDown.playSound(this.assets.sounds);
                }
                if (touch.getAction() == 1) {
                    this.nextState = States.DOWNTOWN;
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
        canvas.drawBitmap(this.assets.casino.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        canvas.drawARGB(this.fadeAlpha, 0, 0, 0);
    }

    @Override // kgm.NA.State
    public void load(AssetManager am, Assets assets) {
        assets.casino.load(am, "casino.png", 400, 300);
        assets.casinoMusic.load(am, "casino-music.ogg");
        assets.touchDown.load(assets.sounds, am, "touch-down.ogg");
        assets.touchUp.load(assets.sounds, am, "touch-up.ogg");
    }

    @Override // kgm.NA.State
    public void delete() {
        this.assets.casino.delete();
        this.assets.casinoMusic.delete();
        this.assets.touchDown.delete(this.assets.sounds);
        this.assets.touchUp.delete(this.assets.sounds);
    }

    @Override // kgm.NA.State
    public void pause() {
        this.assets.casinoMusic.pause();
    }

    @Override // kgm.NA.State
    public void resume() {
        this.assets.casinoMusic.resume();
    }
}
