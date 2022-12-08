package kgm.NA;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;

/* loaded from: classes.dex */
public class IntroState extends State {
    int currentImage;
    int fadeAlpha;
    boolean fadeIn;
    boolean fadeOut;
    int frameCount;
    float volume;

    public IntroState(AssetManager am, Assets assets) {
        this.status = States.RUNNING;
        this.assets = assets;
        load(am, assets);
        assets.introMusic.play(true);
        this.currentImage = 1;
        this.fadeAlpha = MotionEventCompat.ACTION_MASK;
        this.fadeIn = true;
        this.fadeOut = false;
        this.volume = 1.0f;
    }

    @Override // kgm.NA.State
    public void update(MotionEvent touch, KeyEvent key) {
        if (key != null) {
            key.getKeyCode();
        }
        if (touch != null) {
            touch.getAction();
        }
        if (this.fadeIn) {
            this.fadeAlpha -= 9;
            if (this.fadeAlpha <= 0) {
                this.fadeIn = false;
                this.fadeOut = false;
                this.fadeAlpha = 0;
            }
        }
        if (this.fadeOut) {
            this.fadeAlpha += 9;
            if (this.currentImage == 4) {
                this.volume -= 0.05f;
                this.assets.introMusic.setVolume(this.volume);
            }
            if (this.fadeAlpha >= 255) {
                this.fadeIn = true;
                this.fadeOut = false;
                this.fadeAlpha = MotionEventCompat.ACTION_MASK;
                this.currentImage++;
            }
        }
        if (!this.fadeIn && !this.fadeOut) {
            this.frameCount++;
            if (this.frameCount >= 200) {
                this.frameCount = 0;
                this.fadeOut = true;
            }
        }
        if (this.currentImage > 4) {
            SaveFile.isFirstRun = false;
            this.status = States.WELCOME;
        }
    }

    @Override // kgm.NA.State
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.currentImage == 1) {
            canvas.drawBitmap(this.assets.newspaper1.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        }
        if (this.currentImage == 2) {
            canvas.drawBitmap(this.assets.newspaper2.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        }
        if (this.currentImage == 3) {
            canvas.drawBitmap(this.assets.newspaper3.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        }
        if (this.currentImage == 4) {
            canvas.drawBitmap(this.assets.newspaper4.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        }
        canvas.drawARGB(this.fadeAlpha, 0, 0, 0);
    }

    @Override // kgm.NA.State
    public void load(AssetManager am, Assets assets) {
        assets.newspaper1.load(am, "newspaper1.png", 400, 300);
        assets.newspaper2.load(am, "newspaper2.png", 400, 300);
        assets.newspaper3.load(am, "newspaper3.png", 400, 300);
        assets.newspaper4.load(am, "newspaper4.png", 400, 300);
        assets.introMusic.load(am, "menu-music.ogg");
    }

    @Override // kgm.NA.State
    public void delete() {
        this.assets.newspaper1.delete();
        this.assets.newspaper2.delete();
        this.assets.newspaper3.delete();
        this.assets.newspaper4.delete();
        this.assets.introMusic.delete();
    }

    @Override // kgm.NA.State
    public void pause() {
        this.assets.introMusic.pause();
    }

    @Override // kgm.NA.State
    public void resume() {
        this.assets.introMusic.resume();
    }
}
