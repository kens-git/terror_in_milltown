package kgm.NA;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;
import java.util.Random;

/* loaded from: classes.dex */
public class HouseThreeState extends State {
    int fadeAlpha;
    boolean isFading;
    States nextState;
    TextBox textBox;
    Random rand = new Random();
    String[] lines1 = {"\"I hope they find those missing people.\""};
    String[] lines2 = {"\"I find knitting to be rather relaxing.\""};
    String[] lines3 = {"\"My husband and I got into an argument and", "now he spends all of his time down at that", "lighthouse. Will you give this to him for me?\""};
    String[] artLines = {"It's some sort of weird abstract art. Kind of", "an odd choice for a senior."};

    public HouseThreeState(AssetManager am, Assets assets) {
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
                if (touch.getY() > (Screen.relScreenHeight * 0.1833f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.8f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.1075f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.375f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        this.nextState = States.SUBURBS;
                        this.isFading = true;
                        this.assets.touchUp.playSound(this.assets.sounds);
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.45f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.9f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.63f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.8575f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        if (!SaveFile.hasLetter) {
                            this.textBox = new TextBox("lady", this.lines3, 3);
                            SaveFile.hasLetter = true;
                            this.assets.acquire.playSound(this.assets.sounds);
                        } else {
                            int num = this.rand.nextInt(2) + 1;
                            if (num == 1) {
                                this.textBox = new TextBox("lady", this.lines1, 1);
                            } else {
                                this.textBox = new TextBox("lady", this.lines2, 1);
                            }
                        }
                        this.textBox.start();
                        this.assets.textBox.playSound(this.assets.sounds);
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.0766f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.3566f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.52d) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.92f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        this.textBox = new TextBox("art", this.artLines, 2);
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
        canvas.drawBitmap(this.assets.houseThree.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        if (this.textBox != null) {
            this.textBox.draw(canvas);
        }
        canvas.drawARGB(this.fadeAlpha, 0, 0, 0);
    }

    @Override // kgm.NA.State
    public void load(AssetManager am, Assets assets) {
        assets.houseThree.load(am, "house-three.png", 400, 300);
        assets.touchDown.load(assets.sounds, am, "touch-down.ogg");
        assets.touchUp.load(assets.sounds, am, "touch-up.ogg");
        assets.textBox.load(assets.sounds, am, "textbox.ogg");
        assets.acquire.load(assets.sounds, am, "acquire.ogg");
    }

    @Override // kgm.NA.State
    public void delete() {
        this.assets.houseThree.delete();
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
