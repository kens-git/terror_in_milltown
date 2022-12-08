package kgm.NA;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;
import java.util.Random;

/* loaded from: classes.dex */
public class PoliceOfficeState extends State {
    int fadeAlpha;
    long headTimer;
    long headUpdateTime;
    boolean isFading;
    States nextState;
    TextBox textBox;
    Random rand = new Random();
    String[] lines1 = {"\"The people of Milltown have nothing to fear.\""};
    String[] lines2 = {"\"It's just a matter of time before we crack the", "case.\""};
    String[] lines3 = {"\"It's best to let the police handle this; we", "don't need anybody snooping around when", "we're in the middle of an investigation.\""};
    String[] lines4 = {"Yeah, we knew about it, we knew all along.", "It's not like it matters anymore, you won't", "make it back. I had my reasons. I'll tell my", "guys to leave the cave."};
    int headDir = 1;

    public PoliceOfficeState(AssetManager am, Assets assets) {
        this.status = States.RUNNING;
        this.assets = assets;
        load(am, assets);
        this.isFading = false;
        this.fadeAlpha = 0;
        this.headUpdateTime = System.currentTimeMillis();
        this.headTimer = this.rand.nextInt(3000) + 500;
    }

    @Override // kgm.NA.State
    public void update(MotionEvent touch, KeyEvent key) {
        if (key != null) {
            key.getKeyCode();
        }
        if (touch != null && !this.isFading) {
            if (this.textBox == null) {
                if (touch.getY() > (Screen.relScreenHeight * 0.4f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.6833f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.5625f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.725f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        if (!SaveFile.hasEmail) {
                            int random = this.rand.nextInt(3) + 1;
                            switch (random) {
                                case 1:
                                    this.textBox = new TextBox("chief", this.lines1, 1);
                                    break;
                                case 2:
                                    this.textBox = new TextBox("chief", this.lines2, 2);
                                    break;
                                case 3:
                                    this.textBox = new TextBox("chief", this.lines3, 3);
                                    break;
                            }
                            this.textBox.start();
                            this.assets.textBox.playSound(this.assets.sounds);
                            this.headDir = 1;
                        } else {
                            SaveFile.agentsLeft = true;
                            this.textBox = new TextBox("chief", this.lines4, 4);
                            this.assets.textBox.playSound(this.assets.sounds);
                        }
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.25f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.7833f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.075f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.2875f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        this.nextState = States.POLICE;
                        this.isFading = true;
                        this.assets.touchUp.playSound(this.assets.sounds);
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
        canvas.drawBitmap(this.assets.policeOffice.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        if (this.headDir == 1) {
            canvas.drawBitmap(this.assets.chiefHeadCenter.bitmap, (Screen.relScreenWidth * 0.6107f) + Screen.relXZero, (Screen.relScreenHeight * 0.4135f) + Screen.relYZero, (Paint) null);
        } else {
            canvas.drawBitmap(this.assets.chiefHeadLeft.bitmap, (Screen.relScreenWidth * 0.6124f) + Screen.relXZero, (Screen.relScreenHeight * 0.4195f) + Screen.relYZero, (Paint) null);
        }
        if (this.textBox != null) {
            this.textBox.draw(canvas);
        }
        canvas.drawARGB(this.fadeAlpha, 0, 0, 0);
    }

    @Override // kgm.NA.State
    public void load(AssetManager am, Assets assets) {
        assets.policeOffice.load(am, "police-office.png", 400, 300);
        assets.chiefHeadCenter.load(am, "chief-head-center.png", 28, 34);
        assets.chiefHeadLeft.load(am, "chief-head-left.png", 27, 34);
        assets.touchDown.load(assets.sounds, am, "touch-down.ogg");
        assets.touchUp.load(assets.sounds, am, "touch-up.ogg");
        assets.textBox.load(assets.sounds, am, "textbox.ogg");
    }

    @Override // kgm.NA.State
    public void delete() {
        this.assets.policeOffice.delete();
        this.assets.chiefHeadCenter.delete();
        this.assets.chiefHeadLeft.delete();
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
