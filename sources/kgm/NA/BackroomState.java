package kgm.NA;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;
import java.util.Random;

/* loaded from: classes.dex */
public class BackroomState extends State {
    int fadeAlpha;
    long fatGuyHeadTimer;
    long fatGuyHeadUpdateTime;
    boolean isFading;
    long kenHeadTimer;
    long kenHeadUpdateTime;
    States nextState;
    long oldGuyHeadTimer;
    long oldGuyHeadUpdateTime;
    TextBox textBox;
    Random rand = new Random();
    String[] oldGuyLines1 = {"\"We run this joint.\""};
    String[] oldGuyLines2 = {"\"Why don't you try a few games while", "you're here?\""};
    String[] oldGuyLines3 = {"\"Thanks for the delivery, kid. Now, get", "this to your friend outside.\""};
    String[] kenLines1 = {"\"I'm a cool guy!\""};
    String[] kenLines2 = {"\"I have a royal flush. Don't tell the", "other guys.\""};
    String[] kenLines3 = {"\"I once killed a man with my bare hands.\""};
    String[] fatGuyLines1 = {"\"We're the tough guys, don't mess with", "us!\""};
    String[] fatGuyLines2 = {"\"Don't be sticking your nose in other", "people's business, kid.\""};
    int oldGuyHeadDir = this.rand.nextInt(2) + 1;
    int kenHeadDir = this.rand.nextInt(3) + 1;
    int fatGuyHeadDir = this.rand.nextInt(2) + 1;

    public BackroomState(AssetManager am, Assets assets) {
        this.status = States.RUNNING;
        this.assets = assets;
        load(am, assets);
        this.isFading = false;
        this.fadeAlpha = 0;
        this.oldGuyHeadUpdateTime = System.currentTimeMillis();
        this.oldGuyHeadTimer = this.rand.nextInt(3000) + 500;
        this.kenHeadUpdateTime = System.currentTimeMillis();
        this.kenHeadTimer = this.rand.nextInt(3000) + 500;
        this.fatGuyHeadUpdateTime = System.currentTimeMillis();
        this.fatGuyHeadTimer = this.rand.nextInt(3000) + 500;
        assets.backroomMusic.playRand(true);
    }

    @Override // kgm.NA.State
    public void update(MotionEvent touch, KeyEvent key) {
        if (key != null) {
            key.getKeyCode();
        }
        if (touch != null && !this.isFading) {
            if (this.textBox == null) {
                if (touch.getY() > (Screen.relScreenHeight * 0.45f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.9333f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.275f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.4875f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        if (SaveFile.hasDrugs && !SaveFile.hasBrassKnuckles) {
                            SaveFile.hasDrugs = false;
                            SaveFile.hasMoney = true;
                            this.assets.acquire.playSound(this.assets.sounds);
                            this.textBox = new TextBox("oldguy", this.oldGuyLines3, 2);
                        } else {
                            int random = this.rand.nextInt(2) + 1;
                            if (random == 1) {
                                this.textBox = new TextBox("oldguy", this.oldGuyLines1, 1);
                            } else {
                                this.textBox = new TextBox("oldguy", this.oldGuyLines2, 2);
                            }
                        }
                        this.oldGuyHeadDir = 1;
                        this.textBox.start();
                        this.assets.textBox.playSound(this.assets.sounds);
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.45f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.9f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.5f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.6625f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        int random2 = this.rand.nextInt(3) + 1;
                        switch (random2) {
                            case 1:
                                this.textBox = new TextBox("guy", this.kenLines1, 1);
                                break;
                            case 2:
                                this.textBox = new TextBox("guy", this.kenLines2, 2);
                                break;
                            case 3:
                                this.textBox = new TextBox("guy", this.kenLines3, 1);
                                break;
                        }
                        this.kenHeadDir = 2;
                        this.textBox.start();
                        this.assets.textBox.playSound(this.assets.sounds);
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.4833f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.9333f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.675f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.875f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        int random3 = this.rand.nextInt(2) + 1;
                        switch (random3) {
                            case 1:
                                this.textBox = new TextBox("guy", this.fatGuyLines1, 2);
                                break;
                            case 2:
                                this.textBox = new TextBox("guy", this.fatGuyLines2, 2);
                                break;
                        }
                        this.fatGuyHeadDir = 1;
                        this.textBox.start();
                        this.assets.textBox.playSound(this.assets.sounds);
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.133f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.55f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.027f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.202f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        this.nextState = States.CASINO;
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
        if (System.currentTimeMillis() - this.oldGuyHeadUpdateTime > this.oldGuyHeadTimer) {
            this.oldGuyHeadDir = this.rand.nextInt(2) + 1;
            this.oldGuyHeadUpdateTime = System.currentTimeMillis();
            this.oldGuyHeadTimer = this.rand.nextInt(3000) + 500;
        }
        if (System.currentTimeMillis() - this.kenHeadUpdateTime > this.kenHeadTimer) {
            this.kenHeadDir = this.rand.nextInt(3) + 1;
            this.kenHeadUpdateTime = System.currentTimeMillis();
            this.kenHeadTimer = this.rand.nextInt(3000) + 500;
        }
        if (System.currentTimeMillis() - this.fatGuyHeadUpdateTime > this.fatGuyHeadTimer) {
            this.fatGuyHeadDir = this.rand.nextInt(2) + 1;
            this.fatGuyHeadUpdateTime = System.currentTimeMillis();
            this.fatGuyHeadTimer = this.rand.nextInt(3000) + 500;
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
        canvas.drawBitmap(this.assets.backroom.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        if (this.oldGuyHeadDir == 1) {
            canvas.drawBitmap(this.assets.backroomOldGuyHeadCenter.bitmap, (Screen.relScreenWidth * 0.3095f) + Screen.relXZero, (Screen.relScreenHeight * 0.4507f) + Screen.relYZero, (Paint) null);
        } else {
            canvas.drawBitmap(this.assets.backroomOldGuyHeadSide.bitmap, (Screen.relScreenWidth * 0.3142f) + Screen.relXZero, (Screen.relScreenHeight * 0.4445f) + Screen.relYZero, (Paint) null);
        }
        switch (this.kenHeadDir) {
            case 1:
                canvas.drawBitmap(this.assets.backroomKenHeadLeft.bitmap, (Screen.relScreenWidth * 0.5485f) + Screen.relXZero, (Screen.relScreenHeight * 0.4573f) + Screen.relYZero, (Paint) null);
                break;
            case 2:
                canvas.drawBitmap(this.assets.backroomKenHeadCenter.bitmap, (Screen.relScreenWidth * 0.5527f) + Screen.relXZero, (Screen.relScreenHeight * 0.4562f) + Screen.relYZero, (Paint) null);
                break;
            case 3:
                canvas.drawBitmap(this.assets.backroomKenHeadRight.bitmap, (Screen.relScreenWidth * 0.5524f) + Screen.relXZero, (Screen.relScreenHeight * 0.4573f) + Screen.relYZero, (Paint) null);
                break;
        }
        if (this.fatGuyHeadDir == 1) {
            canvas.drawBitmap(this.assets.backroomFatGuyHeadCenter.bitmap, (Screen.relScreenWidth * 0.7886f) + Screen.relXZero, (Screen.relScreenHeight * 0.4878f) + Screen.relYZero, (Paint) null);
        } else {
            canvas.drawBitmap(this.assets.backroomFatGuyHeadSide.bitmap, (Screen.relScreenWidth * 0.7914f) + Screen.relXZero, (Screen.relScreenHeight * 0.4849f) + Screen.relYZero, (Paint) null);
        }
        if (this.textBox != null) {
            this.textBox.draw(canvas);
        }
        canvas.drawARGB(this.fadeAlpha, 0, 0, 0);
    }

    @Override // kgm.NA.State
    public void load(AssetManager am, Assets assets) {
        assets.backroom.load(am, "backroom.png", 400, 300);
        assets.backroomOldGuyHeadCenter.load(am, "backroom-old-guy-head-center.png", 24, 28);
        assets.backroomOldGuyHeadSide.load(am, "backroom-old-guy-head-side.png", 24, 30);
        assets.backroomKenHeadCenter.load(am, "backroom-ken-head-center.png", 19, 26);
        assets.backroomKenHeadLeft.load(am, "backroom-ken-head-left.png", 21, 25);
        assets.backroomKenHeadRight.load(am, "backroom-ken-head-right.png", 21, 25);
        assets.backroomFatGuyHeadCenter.load(am, "backroom-fat-guy-head-center.png", 28, 29);
        assets.backroomFatGuyHeadSide.load(am, "backroom-fat-guy-head-side.png", 27, 29);
        assets.touchDown.load(assets.sounds, am, "touch-down.ogg");
        assets.touchUp.load(assets.sounds, am, "touch-up.ogg");
        assets.textBox.load(assets.sounds, am, "textbox.ogg");
        assets.backroomMusic.load(am, "backroom-music.ogg");
        assets.acquire.load(assets.sounds, am, "acquire.ogg");
    }

    @Override // kgm.NA.State
    public void delete() {
        this.assets.backroom.delete();
        this.assets.backroomOldGuyHeadCenter.delete();
        this.assets.backroomOldGuyHeadSide.delete();
        this.assets.backroomKenHeadCenter.delete();
        this.assets.backroomKenHeadLeft.delete();
        this.assets.backroomKenHeadRight.delete();
        this.assets.backroomFatGuyHeadCenter.delete();
        this.assets.backroomFatGuyHeadSide.delete();
        this.assets.touchDown.delete(this.assets.sounds);
        this.assets.touchUp.delete(this.assets.sounds);
        this.assets.textBox.delete(this.assets.sounds);
        this.assets.backroomMusic.delete();
        this.assets.acquire.delete(this.assets.sounds);
    }

    @Override // kgm.NA.State
    public void pause() {
        this.assets.backroomMusic.pause();
    }

    @Override // kgm.NA.State
    public void resume() {
        this.assets.backroomMusic.resume();
    }
}
