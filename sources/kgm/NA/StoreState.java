package kgm.NA;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;
import java.util.Random;

/* loaded from: classes.dex */
public class StoreState extends State {
    int fadeAlpha;
    long headTimer;
    long headUpdateTime;
    boolean isFading;
    States nextState;
    TextBox textBox;
    Random rand = new Random();
    int headDir = this.rand.nextInt(2) + 1;
    String[] girlLines = {"\"We have a sale on this week.\""};
    String[] suitLines = {"The price tag says it's $500."};

    public StoreState(AssetManager am, Assets assets) {
        this.status = States.RUNNING;
        this.assets = assets;
        load(am, assets);
        this.isFading = false;
        this.fadeAlpha = 0;
        this.headUpdateTime = System.currentTimeMillis();
        this.headTimer = this.rand.nextInt(3000) + 500;
        assets.mallMusic.playRand(true);
        assets.mallMusic.setVolume(0.5f);
    }

    @Override // kgm.NA.State
    public void update(MotionEvent touch, KeyEvent key) {
        if (key != null) {
            key.getKeyCode();
        }
        if (touch != null && !this.isFading) {
            if (this.textBox == null) {
                if (touch.getY() > (Screen.relScreenHeight * 0.0833f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.6833f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.22f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.8325f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        this.nextState = States.MALL;
                        this.isFading = true;
                        this.assets.touchUp.playSound(this.assets.sounds);
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.5333f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.8666f) + Screen.relYZero && touch.getX() > Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.1975f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1 && !SaveFile.hasFancyClothes) {
                        if (SaveFile.cash >= 500) {
                            SaveFile.cash -= 500;
                            SaveFile.hasFancyClothes = true;
                            this.assets.acquire.playSound(this.assets.sounds);
                            this.assets.purchase.playSound(this.assets.sounds);
                        } else {
                            this.textBox = new TextBox("suit", this.suitLines, 1);
                            this.textBox.start();
                            this.assets.textBox.playSound(this.assets.sounds);
                        }
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.5f) + Screen.relYZero && touch.getY() < Screen.relScreenHeight + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.84f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.965f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        this.headDir = 1;
                        this.textBox = new TextBox("girl", this.girlLines, 1);
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
        canvas.drawBitmap(this.assets.store.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        if (this.headDir == 1) {
            canvas.drawBitmap(this.assets.storeGirlHeadCenter.bitmap, (Screen.relScreenWidth * 0.8878f) + Screen.relXZero, (Screen.relScreenHeight * 0.5078f) + Screen.relYZero, (Paint) null);
        } else {
            canvas.drawBitmap(this.assets.storeGirlHeadSide.bitmap, (Screen.relScreenWidth * 0.8851f) + Screen.relXZero, (Screen.relScreenHeight * 0.5177f) + Screen.relYZero, (Paint) null);
        }
        if (!SaveFile.hasFancyClothes) {
            canvas.drawBitmap(this.assets.storeSuit.bitmap, (Screen.relScreenWidth * 0.0137f) + Screen.relXZero, (Screen.relScreenHeight * 0.5171f) + Screen.relYZero, (Paint) null);
        }
        if (this.textBox != null) {
            this.textBox.draw(canvas);
        }
        canvas.drawARGB(this.fadeAlpha, 0, 0, 0);
    }

    @Override // kgm.NA.State
    public void load(AssetManager am, Assets assets) {
        assets.store.load(am, "store.png", 400, 300);
        assets.storeSuit.load(am, "store-suit.png", 70, 99);
        assets.storeGirlHeadCenter.load(am, "store-girl-head-center.png", 15, 23);
        assets.storeGirlHeadSide.load(am, "store-girl-head-side.png", 22, 18);
        assets.touchDown.load(assets.sounds, am, "touch-down.ogg");
        assets.touchUp.load(assets.sounds, am, "touch-up.ogg");
        assets.textBox.load(assets.sounds, am, "textbox.ogg");
        assets.mallMusic.load(am, "mall-music.ogg");
        assets.purchase.load(assets.sounds, am, "purchase.ogg");
        assets.acquire.load(assets.sounds, am, "acquire.ogg");
    }

    @Override // kgm.NA.State
    public void delete() {
        this.assets.store.delete();
        this.assets.storeSuit.delete();
        this.assets.storeGirlHeadCenter.delete();
        this.assets.storeGirlHeadSide.delete();
        this.assets.touchDown.delete(this.assets.sounds);
        this.assets.touchUp.delete(this.assets.sounds);
        this.assets.textBox.delete(this.assets.sounds);
        this.assets.mallMusic.delete();
        this.assets.purchase.delete(this.assets.sounds);
        this.assets.acquire.delete(this.assets.sounds);
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
