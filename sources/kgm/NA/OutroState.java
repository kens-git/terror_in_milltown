package kgm.NA;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;

/* loaded from: classes.dex */
public class OutroState extends State {
    int currentImage;
    int fadeAlpha;
    boolean fadeIn;
    boolean fadeOut;
    int frameCount;

    public OutroState(AssetManager am, Assets assets) {
        this.status = States.RUNNING;
        this.assets = assets;
        load(am, assets);
        assets.outroMusic.play(false);
        this.currentImage = 1;
        this.fadeAlpha = MotionEventCompat.ACTION_MASK;
        this.fadeIn = true;
        this.fadeOut = false;
    }

    @Override // kgm.NA.State
    public void update(MotionEvent touch, KeyEvent key) {
        if (this.currentImage < 4) {
            if (this.fadeIn) {
                this.fadeAlpha -= 7;
                if (this.fadeAlpha <= 0) {
                    this.fadeIn = false;
                    this.fadeOut = false;
                    this.fadeAlpha = 0;
                }
            }
            if (this.fadeOut) {
                this.fadeAlpha += 7;
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
        } else {
            this.fadeAlpha = 0;
        }
        if (!this.assets.outroMusic.mediaPlayer.isPlaying()) {
            boolean musicOn = SaveFile.musicOn;
            boolean soundOn = SaveFile.soundOn;
            SaveFile.reset();
            SaveFile.soundOn = soundOn;
            SaveFile.musicOn = musicOn;
            this.status = States.MENU;
        }
    }

    @Override // kgm.NA.State
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.currentImage == 1) {
            canvas.drawBitmap(this.assets.newspaper5.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        }
        if (this.currentImage == 2) {
            canvas.drawBitmap(this.assets.newspaper6.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        }
        if (this.currentImage == 3) {
            canvas.drawBitmap(this.assets.newspaper7.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        }
        if (this.currentImage > 3) {
            canvas.drawBitmap(this.assets.outro.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        }
        canvas.drawARGB(this.fadeAlpha, 0, 0, 0);
    }

    @Override // kgm.NA.State
    public void load(AssetManager am, Assets assets) {
        assets.newspaper5.load(am, "newspaper5.png", 400, 300);
        assets.newspaper6.load(am, "newspaper6.png", 400, 300);
        assets.newspaper7.load(am, "newspaper7.png", 400, 300);
        assets.outro.load(am, "outro.png", 400, 300);
        assets.outroMusic.load(am, "outro-music.ogg");
    }

    @Override // kgm.NA.State
    public void delete() {
        this.assets.newspaper5.delete();
        this.assets.newspaper6.delete();
        this.assets.newspaper7.delete();
        this.assets.outro.delete();
        this.assets.outroMusic.delete();
    }

    @Override // kgm.NA.State
    public void pause() {
        this.assets.outroMusic.pause();
    }

    @Override // kgm.NA.State
    public void resume() {
        this.assets.outroMusic.resume();
    }
}
