package kgm.NA;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;
import java.util.Random;

/* loaded from: classes.dex */
public class ClubState extends State {
    int bartenderHeadDir;
    long bartenderTimer;
    long bartenderUpdateTime;
    int bouncerHeadDir;
    long bouncerTimer;
    long bouncerUpdateTime;
    int fadeAlpha;
    boolean isFading;
    States nextState;
    Random rand;
    TextBox textBox;
    String[] thugLines = {"\"Yo, bro? Why are you staring? Why don't we", "step outside?\""};
    String[] bouncerLines = {"\"VIPs only...\""};
    String[] bouncerLines2 = {"\"Get yourself cleaned up, there's a dress code!\""};
    String[] bartenderLines = {"\"Get a VIP pass for only $1,000!\""};
    String[] bartenderLines2 = {"\"The VIP room is where the city's most", "distinguished residents go to talk business.\""};
    String[] bartenderLines3 = {"\"Enjoy the VIP room!\""};

    public ClubState(AssetManager am, Assets assets) {
        this.status = States.RUNNING;
        this.assets = assets;
        load(am, assets);
        this.isFading = false;
        this.fadeAlpha = 0;
        this.rand = new Random();
        this.bartenderTimer = this.rand.nextInt(3000) + 500;
        this.bartenderUpdateTime = System.currentTimeMillis();
        this.bartenderHeadDir = this.rand.nextInt(3) + 1;
        this.bouncerTimer = this.rand.nextInt(3000) + 500;
        this.bouncerUpdateTime = System.currentTimeMillis();
        this.bouncerHeadDir = this.rand.nextInt(1);
        assets.clubMusic.setVolume(0.5f);
        assets.clubMusic.playRand(true);
    }

    @Override // kgm.NA.State
    public void update(MotionEvent touch, KeyEvent key) {
        if (key != null) {
            key.getKeyCode();
        }
        if (touch != null && !this.isFading) {
            if (this.textBox == null) {
                if (touch.getX() > (Screen.relScreenWidth * 0.385f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.498f) + Screen.relXZero && touch.getY() > (Screen.relScreenHeight * 0.225f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.725f) + Screen.relYZero) {
                    if (!SaveFile.wonBarFight) {
                        if (touch.getAction() == 0) {
                            this.assets.touchDown.playSound(this.assets.sounds);
                        }
                        if (touch.getAction() == 1) {
                            this.textBox = new TextBox("thug", this.thugLines, 2);
                            this.assets.textBox.playSound(this.assets.sounds);
                        }
                    } else {
                        if (touch.getAction() == 0) {
                            this.assets.touchDown.playSound(this.assets.sounds);
                        }
                        if (touch.getAction() == 1) {
                            this.bartenderHeadDir = 2;
                            if (SaveFile.hasVIPPass) {
                                this.textBox = new TextBox("bartender2", this.bartenderLines2, 2);
                            } else if (SaveFile.cash >= 1000) {
                                SaveFile.cash -= 1000;
                                SaveFile.hasVIPPass = true;
                                this.assets.purchase.playSound(this.assets.sounds);
                                this.assets.acquire.playSound(this.assets.sounds);
                                this.textBox = new TextBox("bartender3", this.bartenderLines3, 1);
                            } else {
                                this.textBox = new TextBox("bartender", this.bartenderLines, 1);
                            }
                            this.assets.textBox.playSound(this.assets.sounds);
                        }
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.116f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.566f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.79f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.965f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        if (SaveFile.hasVIPPass && SaveFile.hasFancyClothes) {
                            this.nextState = States.VIP;
                            this.isFading = true;
                        } else if (!this.isFading) {
                            this.bouncerHeadDir = 0;
                            if (SaveFile.hasVIPPass && !SaveFile.hasFancyClothes) {
                                this.textBox = new TextBox("bouncerLines", this.bouncerLines2, 1);
                            } else {
                                this.textBox = new TextBox("bouncerLines", this.bouncerLines, 1);
                            }
                            this.textBox.start();
                            this.assets.textBox.playSound(this.assets.sounds);
                        }
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.116f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.566f) + Screen.relYZero && touch.getX() > Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.316f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        this.nextState = States.DOWNTOWN;
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
        if (System.currentTimeMillis() - this.bartenderUpdateTime > this.bartenderTimer) {
            this.bartenderHeadDir = this.rand.nextInt(3) + 1;
            this.bartenderUpdateTime = System.currentTimeMillis();
            this.bartenderTimer = this.rand.nextInt(3000) + 500;
        }
        if (System.currentTimeMillis() - this.bouncerUpdateTime > this.bouncerTimer) {
            this.bouncerHeadDir = this.rand.nextInt(1);
            this.bouncerUpdateTime = System.currentTimeMillis();
            this.bouncerTimer = this.rand.nextInt(3000) + 500;
        }
        if (this.textBox != null) {
            if (this.textBox.id == "thug" && this.textBox.isFinished) {
                this.isFading = true;
                this.nextState = States.FIGHT;
            }
            if (this.textBox.isFinished) {
                this.textBox = null;
            }
        }
        if (this.isFading) {
            this.fadeAlpha += 16;
            if (this.fadeAlpha > 255) {
                this.fadeAlpha = MotionEventCompat.ACTION_MASK;
                this.status = this.nextState;
            }
        }
    }

    @Override // kgm.NA.State
    public void draw(Canvas canvas) {
        canvas.drawBitmap(this.assets.club.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        canvas.drawBitmap(this.assets.bartenderTorso.bitmap, (Screen.relScreenWidth * 0.39f) + Screen.relXZero, (Screen.relScreenHeight * 0.3f) + Screen.relYZero, (Paint) null);
        switch (this.bartenderHeadDir) {
            case 1:
                canvas.drawBitmap(this.assets.bartenderHeadLeft.bitmap, (Screen.relScreenWidth * 0.405f) + Screen.relXZero, (Screen.relScreenHeight * 0.24f) + Screen.relYZero, (Paint) null);
                break;
            case 2:
                canvas.drawBitmap(this.assets.bartenderHeadCenter.bitmap, (Screen.relScreenWidth * 0.41f) + Screen.relXZero, (Screen.relScreenHeight * 0.24f) + Screen.relYZero, (Paint) null);
                break;
            case 3:
                canvas.drawBitmap(this.assets.bartenderHeadRight.bitmap, (Screen.relScreenWidth * 0.415f) + Screen.relXZero, (Screen.relScreenHeight * 0.24f) + Screen.relYZero, (Paint) null);
                break;
            default:
                this.bartenderHeadDir = 2;
                break;
        }
        if (!SaveFile.hasFancyClothes || !SaveFile.hasVIPPass) {
            canvas.drawBitmap(this.assets.bouncerBody.bitmap, (Screen.relScreenWidth * 0.8248f) + Screen.relXZero, (Screen.relScreenHeight * 0.24382f) + Screen.relYZero, (Paint) null);
            if (this.bouncerHeadDir == 0) {
                canvas.drawBitmap(this.assets.bouncerHeadCenter.bitmap, (Screen.relScreenWidth * 0.85267f) + Screen.relXZero, (Screen.relScreenHeight * 0.181653f) + Screen.relYZero, (Paint) null);
            }
            if (this.bouncerHeadDir == 1) {
                canvas.drawBitmap(this.assets.bouncerHeadLeft.bitmap, (Screen.relScreenWidth * 0.86255f) + Screen.relXZero, (Screen.relScreenHeight * 0.178953f) + Screen.relYZero, (Paint) null);
            } else {
                canvas.drawBitmap(this.assets.bouncerHeadCenter.bitmap, (Screen.relScreenWidth * 0.85267f) + Screen.relXZero, (Screen.relScreenHeight * 0.181653f) + Screen.relYZero, (Paint) null);
            }
        }
        if (!SaveFile.wonBarFight) {
            canvas.drawBitmap(this.assets.thug.bitmap, (Screen.relScreenWidth * 0.34f) + Screen.relXZero, (Screen.relScreenHeight * 0.2f) + Screen.relYZero, (Paint) null);
        }
        if (this.textBox != null) {
            this.textBox.draw(canvas);
        }
        canvas.drawARGB(this.fadeAlpha, 0, 0, 0);
    }

    @Override // kgm.NA.State
    public void load(AssetManager am, Assets assets) {
        assets.club.load(am, "club.png", 400, 300);
        assets.bartenderTorso.load(am, "bartender-torso.png", 44, 50);
        assets.bartenderHeadCenter.load(am, "bartender-head-center.png", 26, 31);
        assets.bartenderHeadLeft.load(am, "bartender-head-left.png", 26, 30);
        assets.bartenderHeadRight.load(am, "bartender-head-right.png", 26, 30);
        assets.thug.load(am, "thug.png", 62, 162);
        assets.bouncerHeadLeft.load(am, "bouncer-head-left.png", 18, 26);
        assets.bouncerHeadCenter.load(am, "bouncer-head-center.png", 23, 25);
        assets.bouncerBody.load(am, "bouncer-body.png", 49, 120);
        assets.touchDown.load(assets.sounds, am, "touch-down.ogg");
        assets.touchUp.load(assets.sounds, am, "touch-up.ogg");
        assets.textBox.load(assets.sounds, am, "textbox.ogg");
        assets.clubMusic.load(am, "club-music.ogg");
        assets.purchase.load(assets.sounds, am, "purchase.ogg");
        assets.acquire.load(assets.sounds, am, "acquire.ogg");
    }

    @Override // kgm.NA.State
    public void delete() {
        this.assets.club.delete();
        this.assets.bartenderTorso.delete();
        this.assets.bartenderHeadCenter.delete();
        this.assets.bartenderHeadLeft.delete();
        this.assets.bartenderHeadRight.delete();
        this.assets.thug.delete();
        this.assets.bouncerHeadLeft.delete();
        this.assets.bouncerHeadCenter.delete();
        this.assets.bouncerBody.delete();
        this.assets.touchDown.delete(this.assets.sounds);
        this.assets.touchUp.delete(this.assets.sounds);
        this.assets.textBox.delete(this.assets.sounds);
        this.assets.clubMusic.delete();
        this.assets.purchase.delete(this.assets.sounds);
        this.assets.acquire.delete(this.assets.sounds);
    }

    @Override // kgm.NA.State
    public void pause() {
        this.assets.clubMusic.pause();
    }

    @Override // kgm.NA.State
    public void resume() {
        this.assets.clubMusic.resume();
    }
}
