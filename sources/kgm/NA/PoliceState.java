package kgm.NA;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;
import java.util.Random;

/* loaded from: classes.dex */
public class PoliceState extends State {
    int fadeAlpha;
    int headDir;
    long headTimer;
    long headUpdateTime;
    boolean isFading;
    States nextState;
    TextBox textBox;
    Random rand = new Random();
    String[] lines1 = {"\"The chief is in his office.\""};
    String[] lines2 = {"\"If you see these suspects make sure to call", "the police.\""};
    String[] lines3 = {"\"We're doing the best we can to track down", "those missing people.\""};

    public PoliceState(AssetManager am, Assets assets) {
        this.status = States.RUNNING;
        this.assets = assets;
        load(am, assets);
        this.isFading = false;
        this.fadeAlpha = 0;
        this.headTimer = this.rand.nextInt(3000) + 500;
        this.headUpdateTime = System.currentTimeMillis();
        this.headDir = this.rand.nextInt(3) + 1;
    }

    @Override // kgm.NA.State
    public void update(MotionEvent touch, KeyEvent key) {
        if (key != null) {
            key.getKeyCode();
        }
        if (touch != null) {
            if (this.textBox == null && !this.isFading) {
                if (touch.getY() > (Screen.relScreenHeight * 0.166f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.8f) + Screen.relYZero && touch.getX() > Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.225f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        this.nextState = States.DOWNTOWN;
                        this.isFading = true;
                        this.assets.touchUp.playSound(this.assets.sounds);
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.116f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.72f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.805f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.902f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        this.nextState = States.POLICE_OFFICE;
                        this.isFading = true;
                        this.assets.touchUp.playSound(this.assets.sounds);
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.3f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.6f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.45f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.6375f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        int random = this.rand.nextInt(3) + 1;
                        switch (random) {
                            case 1:
                                this.textBox = new TextBox("null", this.lines1, 1);
                                break;
                            case 2:
                                this.textBox = new TextBox("null", this.lines2, 2);
                                break;
                            case 3:
                                this.textBox = new TextBox("null", this.lines3, 2);
                                break;
                        }
                        this.headDir = 1;
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
            this.headDir = this.rand.nextInt(3) + 1;
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
        canvas.drawBitmap(this.assets.policeStation.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        if (this.headDir == 1) {
            canvas.drawBitmap(this.assets.secretaryHeadCenter.bitmap, (Screen.relScreenWidth * 0.4964f) + Screen.relXZero, (Screen.relScreenHeight * 0.3032f) + Screen.relYZero, (Paint) null);
        }
        if (this.headDir == 2) {
            canvas.drawBitmap(this.assets.secretaryHeadLeft.bitmap, (Screen.relScreenWidth * 0.4994f) + Screen.relXZero, (Screen.relScreenHeight * 0.3065f) + Screen.relYZero, (Paint) null);
        }
        if (this.headDir == 3) {
            canvas.drawBitmap(this.assets.secretaryHeadRight.bitmap, (Screen.relScreenWidth * 0.4998f) + Screen.relXZero, (Screen.relScreenHeight * 0.3065f) + Screen.relYZero, (Paint) null);
        }
        if (this.textBox != null) {
            this.textBox.draw(canvas);
        }
        canvas.drawARGB(this.fadeAlpha, 0, 0, 0);
    }

    @Override // kgm.NA.State
    public void load(AssetManager am, Assets assets) {
        assets.policeStation.load(am, "police-station.png", 400, 300);
        assets.secretaryHeadCenter.load(am, "secretary-head-center.png", 33, 46);
        assets.secretaryHeadLeft.load(am, "secretary-head-left.png", 31, 43);
        assets.secretaryHeadRight.load(am, "secretary-head-right.png", 31, 43);
        assets.touchDown.load(assets.sounds, am, "touch-down.ogg");
        assets.touchUp.load(assets.sounds, am, "touch-up.ogg");
        assets.textBox.load(assets.sounds, am, "textbox.ogg");
    }

    @Override // kgm.NA.State
    public void delete() {
        this.assets.policeStation.delete();
        this.assets.secretaryHeadCenter.delete();
        this.assets.secretaryHeadLeft.delete();
        this.assets.secretaryHeadRight.delete();
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
