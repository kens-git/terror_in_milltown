package kgm.NA;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;
import java.util.Random;

/* loaded from: classes.dex */
public class PawnState extends State {
    int fadeAlpha;
    long headTimer;
    long headUpdateTime;
    boolean isFading;
    States nextState;
    TextBox textBox;
    Random rand = new Random();
    String[] guyLines1 = {"\"That guy is always lurking outside.\""};
    String[] guyLines2 = {"\"Only some of this stuff is stolen.\""};
    String[] guyLines3 = {"\"I'm looking for game consoles in case you", "have any or know where to get some, if you", "catch my drift.\""};
    int headDir = 1;

    public PawnState(AssetManager am, Assets assets) {
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
                if (touch.getY() > (Screen.relScreenHeight * 0.166f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.75f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.025f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.25f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        this.nextState = States.DOWNTOWN;
                        this.isFading = true;
                        this.assets.touchUp.playSound(this.assets.sounds);
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.0666f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.266f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.3375f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.49f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1 && SaveFile.cash >= 200 && !SaveFile.hasBat) {
                        SaveFile.hasBat = true;
                        this.assets.acquire.playSound(this.assets.sounds);
                        SaveFile.cash -= 200;
                        this.assets.purchase.playSound(this.assets.sounds);
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.0666f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.266f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.8f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.95f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1 && SaveFile.cash >= 250 && !SaveFile.hasRacket) {
                        SaveFile.cash -= 250;
                        SaveFile.hasRacket = true;
                        this.assets.acquire.playSound(this.assets.sounds);
                        this.assets.purchase.playSound(this.assets.sounds);
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.2833f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.5833f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.58f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.7375f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        int random = this.rand.nextInt(3) + 1;
                        switch (random) {
                            case 1:
                                this.textBox = new TextBox("guy", this.guyLines1, 1);
                                break;
                            case 2:
                                this.textBox = new TextBox("guy", this.guyLines2, 1);
                                break;
                            case 3:
                                this.textBox = new TextBox("guy", this.guyLines3, 3);
                                break;
                        }
                        this.textBox.start();
                        this.assets.textBox.playSound(this.assets.sounds);
                        this.headDir = 2;
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
        canvas.drawBitmap(this.assets.pawnShop.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        if (this.headDir == 1) {
            canvas.drawBitmap(this.assets.pawnGuyHeadLeft.bitmap, (Screen.relScreenWidth * 0.6243f) + Screen.relXZero, (Screen.relScreenHeight * 0.2935f) + Screen.relYZero, (Paint) null);
        } else {
            canvas.drawBitmap(this.assets.pawnGuyHeadCenter.bitmap, (Screen.relScreenWidth * 0.6212f) + Screen.relXZero, (Screen.relScreenHeight * 0.2935f) + Screen.relYZero, (Paint) null);
        }
        if (!SaveFile.hasBat) {
            canvas.drawBitmap(this.assets.pawnBat.bitmap, (Screen.relScreenWidth * 0.3311f) + Screen.relXZero, (Screen.relScreenHeight * 0.0757f) + Screen.relYZero, (Paint) null);
        }
        if (!SaveFile.hasRacket) {
            canvas.drawBitmap(this.assets.pawnRacket.bitmap, (Screen.relScreenWidth * 0.821f) + Screen.relXZero, (Screen.relScreenHeight * 0.069f) + Screen.relYZero, (Paint) null);
        }
        if (this.textBox != null) {
            this.textBox.draw(canvas);
        }
        canvas.drawARGB(this.fadeAlpha, 0, 0, 0);
    }

    @Override // kgm.NA.State
    public void load(AssetManager am, Assets assets) {
        assets.pawnShop.load(am, "pawn-shop.png", 400, 300);
        assets.pawnGuyHeadCenter.load(am, "pawn-guy-head-center.png", 22, 27);
        assets.pawnGuyHeadLeft.load(am, "pawn-guy-head-left.png", 20, 27);
        assets.pawnBat.load(am, "pawn-bat.png", 58, 59);
        assets.pawnRacket.load(am, "pawn-racket.png", 43, 52);
        assets.touchDown.load(assets.sounds, am, "touch-down.ogg");
        assets.touchUp.load(assets.sounds, am, "touch-up.ogg");
        assets.textBox.load(assets.sounds, am, "textbox.ogg");
        assets.purchase.load(assets.sounds, am, "purchase.ogg");
        assets.acquire.load(assets.sounds, am, "acquire.ogg");
    }

    @Override // kgm.NA.State
    public void delete() {
        this.assets.pawnShop.delete();
        this.assets.pawnGuyHeadCenter.delete();
        this.assets.pawnGuyHeadLeft.delete();
        this.assets.pawnBat.delete();
        this.assets.pawnRacket.delete();
        this.assets.touchDown.delete(this.assets.sounds);
        this.assets.touchUp.delete(this.assets.sounds);
        this.assets.textBox.delete(this.assets.sounds);
        this.assets.purchase.delete(this.assets.sounds);
        this.assets.acquire.delete(this.assets.sounds);
    }

    @Override // kgm.NA.State
    public void pause() {
    }

    @Override // kgm.NA.State
    public void resume() {
    }
}
