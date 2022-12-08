package kgm.NA;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;
import java.util.Random;

/* loaded from: classes.dex */
public class LighthouseState extends State {
    int fadeAlpha;
    boolean isFading;
    States nextState;
    TextBox textBox;
    Random rand = new Random();
    String[] lightLines = {"It's a light for the lighthouse. I wouldn't touch", "it if I were you."};
    String[] keeperLines1 = {"\"Get out of here!\""};
    String[] keeperLines3 = {"\"I wish to be left alone!\""};
    String[] keeperLines2 = {"*sniff* \"It's been so long. I miss my beloved.", "I'm going home!\""};

    public LighthouseState(AssetManager am, Assets assets) {
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
            if (this.textBox == null) {
                if (touch.getY() > (Screen.relScreenHeight * 0.5833f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.9f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.7f) + Screen.relXZero && touch.getX() < Screen.relScreenWidth + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        this.nextState = States.BEACH;
                        this.isFading = true;
                        this.assets.touchUp.playSound(this.assets.sounds);
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.2333f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.75f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.41f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.605f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1 && !SaveFile.lightkeeperLeft) {
                        if (SaveFile.hasLetter) {
                            this.textBox = new TextBox("keeper", this.keeperLines2, 2);
                            SaveFile.hasLetter = false;
                            SaveFile.lightkeeperLeft = true;
                        } else {
                            int num = this.rand.nextInt(2) + 1;
                            if (num == 1) {
                                this.textBox = new TextBox("keeper", this.keeperLines1, 1);
                            } else {
                                this.textBox = new TextBox("keeper", this.keeperLines3, 1);
                            }
                        }
                        this.textBox.start();
                        this.assets.textBox.playSound(this.assets.sounds);
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.5933f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.8f) + Screen.relYZero && touch.getX() > Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.14f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        if (SaveFile.lightkeeperLeft) {
                            SaveFile.hasLight = true;
                            this.assets.acquire.playSound(this.assets.sounds);
                        } else {
                            this.textBox = new TextBox("light", this.lightLines, 2);
                            this.textBox.start();
                            this.assets.textBox.playSound(this.assets.sounds);
                        }
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
        canvas.drawBitmap(this.assets.lighthouse.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        if (!SaveFile.lightkeeperLeft) {
            canvas.drawBitmap(this.assets.lighthouseKeeper.bitmap, (Screen.relScreenWidth * 0.4524f) + Screen.relXZero, (Screen.relScreenHeight * 0.2811f) + Screen.relYZero, (Paint) null);
        }
        if (!SaveFile.hasLight) {
            canvas.drawBitmap(this.assets.lighthouseBulb.bitmap, (Screen.relScreenWidth * 0.0307f) + Screen.relXZero, (Screen.relScreenHeight * 0.6333f) + Screen.relYZero, (Paint) null);
        }
        if (this.textBox != null) {
            this.textBox.draw(canvas);
        }
        canvas.drawARGB(this.fadeAlpha, 0, 0, 0);
    }

    @Override // kgm.NA.State
    public void load(AssetManager am, Assets assets) {
        assets.lighthouse.load(am, "lighthouse.png", 400, 300);
        assets.lighthouseBulb.load(am, "lighthouse-bulb.png", 35, 38);
        assets.lighthouseKeeper.load(am, "lighthouse-keeper.png", 47, 131);
        assets.touchDown.load(assets.sounds, am, "touch-down.ogg");
        assets.touchUp.load(assets.sounds, am, "touch-up.ogg");
        assets.textBox.load(assets.sounds, am, "textbox.ogg");
        assets.acquire.load(assets.sounds, am, "acquire.ogg");
    }

    @Override // kgm.NA.State
    public void delete() {
        this.assets.lighthouse.delete();
        this.assets.lighthouseBulb.delete();
        this.assets.lighthouseKeeper.delete();
        this.assets.touchDown.delete(this.assets.sounds);
        this.assets.touchUp.delete(this.assets.sounds);
        this.assets.textBox.delete(this.assets.sounds);
        this.assets.acquire.delete(this.assets.sounds);
    }

    @Override // kgm.NA.State
    public void pause() {
    }

    @Override // kgm.NA.State
    public void resume() {
    }
}
