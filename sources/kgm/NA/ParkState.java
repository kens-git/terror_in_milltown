package kgm.NA;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;
import java.util.Random;

/* loaded from: classes.dex */
public class ParkState extends State {
    int fadeAlpha;
    boolean isFading;
    int kidHeadDir;
    long kidHeadTimer;
    long kidHeadUpdateTime;
    States nextState;
    TextBox textBox;
    Random rand = new Random();
    String[] lines1 = {"\"My little brother is at home playing video", "games. I'll let him know that you're stopping", "by."};
    String[] lines2 = {"\"I think my brother is friends with that", "missing kid.\""};

    public ParkState(AssetManager am, Assets assets) {
        this.kidHeadDir = 1;
        this.status = States.RUNNING;
        this.assets = assets;
        load(am, assets);
        this.isFading = false;
        this.fadeAlpha = 0;
        this.kidHeadUpdateTime = System.currentTimeMillis();
        this.kidHeadTimer = this.rand.nextInt(3000) + 500;
        this.kidHeadDir = this.rand.nextInt(2) + 1;
    }

    @Override // kgm.NA.State
    public void update(MotionEvent touch, KeyEvent key) {
        if (key != null) {
            key.getKeyCode();
        }
        if (touch != null && !this.isFading) {
            if (this.textBox == null) {
                if (touch.getY() > (Screen.relScreenHeight * 0.1766f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.4233f) + Screen.relYZero && touch.getX() > Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.3125f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        this.nextState = States.SUBURBS;
                        this.isFading = true;
                        this.assets.touchUp.playSound(this.assets.sounds);
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.3166f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.7166f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.74f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.8875f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        if (!SaveFile.talkedToKid) {
                            this.textBox = new TextBox("kid", this.lines1, 3);
                            SaveFile.talkedToKid = true;
                        } else {
                            this.textBox = new TextBox("kid", this.lines2, 2);
                        }
                        this.kidHeadDir = 1;
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
        if (System.currentTimeMillis() - this.kidHeadUpdateTime > this.kidHeadTimer) {
            this.kidHeadDir = this.rand.nextInt(2) + 1;
            this.kidHeadUpdateTime = System.currentTimeMillis();
            this.kidHeadTimer = this.rand.nextInt(3000) + 500;
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
        canvas.drawBitmap(this.assets.park.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        if (this.kidHeadDir == 1) {
            canvas.drawBitmap(this.assets.parkKidHeadCenter.bitmap, (Screen.relScreenWidth * 0.79f) + Screen.relXZero, (Screen.relScreenHeight * 0.3388f) + Screen.relYZero, (Paint) null);
        } else {
            canvas.drawBitmap(this.assets.parkKidHeadLeft.bitmap, (Screen.relScreenWidth * 0.7892f) + Screen.relXZero, (Screen.relScreenHeight * 0.3393f) + Screen.relYZero, (Paint) null);
        }
        if (this.textBox != null) {
            this.textBox.draw(canvas);
        }
        canvas.drawARGB(this.fadeAlpha, 0, 0, 0);
    }

    @Override // kgm.NA.State
    public void load(AssetManager am, Assets assets) {
        assets.park.load(am, "park.png", 400, 300);
        assets.parkKidHeadCenter.load(am, "park-kid-head-center.png", 17, 22);
        assets.parkKidHeadLeft.load(am, "park-kid-head-left.png", 17, 22);
        assets.touchDown.load(assets.sounds, am, "touch-down.ogg");
        assets.touchUp.load(assets.sounds, am, "touch-up.ogg");
        assets.textBox.load(assets.sounds, am, "textbox.ogg");
    }

    @Override // kgm.NA.State
    public void delete() {
        this.assets.park.delete();
        this.assets.parkKidHeadCenter.delete();
        this.assets.parkKidHeadLeft.delete();
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
