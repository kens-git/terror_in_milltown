package kgm.NA;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;
import java.util.Random;

/* loaded from: classes.dex */
public class BugGameState extends State {
    float bugX;
    float bugY;
    boolean bulletFired;
    float bulletX;
    float bulletY;
    int dragonHealth;
    boolean dragonLeft;
    float dragonX;
    int fadeAlpha;
    float initialX;
    float initialY;
    boolean isFading;
    States nextState;
    int shotsLeft;
    TextBox textBox;
    long textStart;
    boolean winTextStarted;
    Random rand = new Random();
    String[] winLines = {"Kid: \"Thanks! Here, you can have this. My", "mom made it for me.\""};
    String[] loseLines = {"Kid: \"You ran out of ammo!\""};

    public BugGameState(AssetManager am, Assets assets) {
        this.status = States.RUNNING;
        this.assets = assets;
        load(am, assets);
        this.isFading = false;
        this.fadeAlpha = 0;
        this.shotsLeft = 10;
        this.dragonHealth = 7;
        this.bulletFired = false;
        this.initialX = (Screen.relScreenWidth * 0.1895f) + Screen.relXZero;
        this.initialY = (Screen.relScreenHeight * 0.7561f) + Screen.relYZero;
        this.dragonLeft = false;
        this.dragonX = Screen.relXZero;
        this.bugX = (Screen.relScreenWidth * 0.1696f) + Screen.relXZero;
        this.bugY = (Screen.relScreenHeight * 0.1627f) + Screen.relYZero;
        this.winTextStarted = false;
        assets.bugMusic.play(true);
        assets.bugMusic.setVolume(0.5f);
    }

    @Override // kgm.NA.State
    public void update(MotionEvent touch, KeyEvent key) {
        if (key != null) {
            key.getKeyCode();
        }
        if (touch != null) {
            if (this.textBox == null && touch.getX() > Screen.relXZero) {
                if (touch.getAction() == 0 && !this.bulletFired && this.shotsLeft > 0 && this.dragonHealth > 0) {
                    this.bulletFired = true;
                    this.assets.cannon.playSound(this.assets.sounds);
                    this.bulletX = this.initialX;
                    this.bulletY = this.initialY;
                    this.shotsLeft--;
                }
                touch.getAction();
            }
            if (touch.getX() > Screen.relXZero && touch.getAction() == 1 && this.textBox != null && !this.textBox.isFinished && this.textBox.isPaused) {
                this.textBox.resume();
            }
            touch.setLocation(-10.0f, -10.0f);
        }
        if (this.bulletFired) {
            this.bulletX += Screen.relScreenWidth * 0.02f;
            this.bulletY -= Screen.relScreenWidth * 0.02f;
            if (this.bulletY < Screen.relYZero) {
                this.bulletFired = false;
            }
            if (this.bulletX > this.dragonX * 1.18f && this.bulletX < (this.dragonX * 0.84d) + this.assets.dragonLeft.getWidth() && this.bulletY < Screen.relYZero + this.assets.dragonLeft.getHeight()) {
                this.dragonHealth--;
                this.assets.hit.playSound(this.assets.sounds);
                this.bulletFired = false;
            }
        }
        if (this.dragonHealth <= 0) {
            if (this.dragonLeft) {
                this.dragonX -= Screen.relScreenWidth * 0.02f;
            } else {
                this.dragonX += Screen.relScreenWidth * 0.02f;
            }
            if (!this.winTextStarted) {
                this.assets.win.playSound(this.assets.sounds, 0.5f);
                this.assets.bugMusic.stop();
                this.winTextStarted = true;
                this.textStart = System.currentTimeMillis();
            }
        } else if (this.dragonLeft) {
            this.dragonX -= Screen.relScreenWidth * 0.02f;
            if (this.dragonX <= Screen.relXZero - this.assets.dragonLeft.getWidth()) {
                this.dragonLeft = false;
            }
        } else {
            this.dragonX += Screen.relScreenWidth * 0.02f;
            if (this.dragonX >= Screen.relScreenWidth + Screen.relXZero) {
                this.dragonLeft = true;
            }
        }
        if (this.shotsLeft == 0 && this.dragonHealth > 0 && !this.bulletFired && this.textBox == null) {
            this.textBox = new TextBox("lose", this.loseLines, 1);
            this.textBox.start();
        }
        if (this.winTextStarted && System.currentTimeMillis() - this.textStart > 1000 && this.textBox == null) {
            this.textBox = new TextBox("win", this.winLines, 2);
            this.textBox.start();
            this.assets.acquire.playSound(this.assets.sounds);
        }
        if (this.textBox != null && this.textBox.isFinished) {
            if (this.textBox.id == "win") {
                this.isFading = true;
                this.nextState = States.HOUSE_TWO;
                SaveFile.hasLunchbox = true;
                SaveFile.beatBugGame = true;
            } else {
                this.isFading = true;
                this.nextState = States.HOUSE_TWO;
            }
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
        canvas.drawBitmap(this.assets.bugBackground.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        if (this.bulletFired) {
            canvas.drawBitmap(this.assets.bullet.bitmap, this.bulletX, this.bulletY, (Paint) null);
        }
        for (int j = 0; j < this.dragonHealth; j++) {
            canvas.drawBitmap(this.assets.bugHeart.bitmap, (Screen.relScreenWidth * 0.63f) + (j * Screen.relScreenWidth * 0.07f), Screen.relScreenHeight * 0.02f, (Paint) null);
        }
        if (this.dragonLeft) {
            canvas.drawBitmap(this.assets.dragonLeft.bitmap, this.dragonX, Screen.relYZero, (Paint) null);
        } else {
            canvas.drawBitmap(this.assets.dragonRight.bitmap, this.dragonX, Screen.relYZero, (Paint) null);
        }
        for (int i = 0; i < this.shotsLeft; i++) {
            canvas.drawBitmap(this.assets.bullet.bitmap, (Screen.relScreenWidth * 0.5f) + (i * Screen.relScreenWidth * 0.04f), Screen.relScreenHeight * 0.92f, (Paint) null);
        }
        if (this.winTextStarted) {
            canvas.drawBitmap(this.assets.bugText.bitmap, (Screen.relScreenWidth * 0.1358f) + Screen.relXZero, (Screen.relScreenHeight * 0.3061f) + Screen.relYZero, (Paint) null);
        }
        if (this.textBox != null) {
            this.textBox.draw(canvas);
        }
        canvas.drawARGB(this.fadeAlpha, 0, 0, 0);
    }

    @Override // kgm.NA.State
    public void load(AssetManager am, Assets assets) {
        assets.bugBackground.load(am, "bug-background.png", 400, 300);
        assets.bullet.load(am, "bullet.png", 12, 12);
        assets.bugText.load(am, "bug-text.png", 297, 104);
        assets.dragonLeft.load(am, "dragon-left.png", 157, 72);
        assets.dragonRight.load(am, "dragon-right.png", 157, 72);
        assets.bugHeart.load(am, "bug-heart.png", 19, 20);
        assets.cannon.load(assets.sounds, am, "cannon.ogg");
        assets.hit.load(assets.sounds, am, "hit.ogg");
        assets.bugMusic.load(am, "bug-music.ogg");
        assets.win.load(assets.sounds, am, "win.ogg");
        assets.acquire.load(assets.sounds, am, "acquire.ogg");
    }

    @Override // kgm.NA.State
    public void delete() {
        this.assets.bugText.delete();
        this.assets.bullet.delete();
        this.assets.bugText.delete();
        this.assets.dragonLeft.delete();
        this.assets.dragonRight.delete();
        this.assets.bugHeart.delete();
        this.assets.cannon.delete(this.assets.sounds);
        this.assets.hit.delete(this.assets.sounds);
        this.assets.bugMusic.delete();
        this.assets.win.delete(this.assets.sounds);
        this.assets.acquire.delete(this.assets.sounds);
    }

    @Override // kgm.NA.State
    public void pause() {
        this.assets.bugMusic.pause();
    }

    @Override // kgm.NA.State
    public void resume() {
        this.assets.bugMusic.resume();
    }
}
