package kgm.NA;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;
import java.util.Random;

/* loaded from: classes.dex */
public class VIPState extends State {
    int fadeAlpha;
    boolean isFading;
    TextBox textBox;
    Random rand = new Random();
    String[] ceoLines1 = {"\"What could I do? I would have been killed", "if we admitted to knowing about the", "creature.\""};
    String[] ceoLines2 = {"\"My password is " + SaveFile.computerPassword + ", it will tell you", "everything you need to know.\""};
    String[] ceoLines3 = {"\"Those mobsters pressured the company", "so that we wouldn't report on the police", "chief's corruption.\""};

    public VIPState(AssetManager am, Assets assets) {
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
                if (touch.getY() > (Screen.relScreenHeight * 0.05f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.916f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.05f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.225f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        this.isFading = true;
                        this.assets.touchUp.playSound(this.assets.sounds);
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.33f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.8f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.4625f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.725f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        int random = this.rand.nextInt(4) + 1;
                        switch (random) {
                            case 1:
                                this.textBox = new TextBox("ceo", this.ceoLines2, 2);
                                break;
                            case 2:
                                this.textBox = new TextBox("ceo", this.ceoLines2, 2);
                                break;
                            case 3:
                                this.textBox = new TextBox("ceo", this.ceoLines3, 3);
                                break;
                            case 4:
                                this.textBox = new TextBox("ceo", this.ceoLines2, 2);
                                break;
                            default:
                                this.textBox = new TextBox("ceo", this.ceoLines2, 2);
                                break;
                        }
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
                this.status = States.CLUB;
            }
        }
    }

    @Override // kgm.NA.State
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawBitmap(this.assets.vipRoom.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        if (this.textBox != null) {
            this.textBox.draw(canvas);
        }
        canvas.drawARGB(this.fadeAlpha, 0, 0, 0);
    }

    @Override // kgm.NA.State
    public void load(AssetManager am, Assets assets) {
        assets.vipRoom.load(am, "vip-room.png", 400, 300);
        assets.touchDown.load(assets.sounds, am, "touch-down.ogg");
        assets.touchUp.load(assets.sounds, am, "touch-up.ogg");
        assets.textBox.load(assets.sounds, am, "textbox.ogg");
    }

    @Override // kgm.NA.State
    public void delete() {
        this.assets.vipRoom.delete();
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
