package kgm.NA;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;

/* loaded from: classes.dex */
public class DowntownState extends State {
    int fadeAlpha;
    boolean isFading;
    States nextState;
    TextBox textBox;
    String[] dealerLines1 = {"\"Hey, man. Deliver this package to the", "guys in the casino for me.\""};
    String[] dealerLines2 = {"\"Make sure you get it there!\""};
    String[] dealerLines3 = {"\"Thanks, man. Here, take this, it will", "help you in a fight.\""};
    String[] thugLines = {"\"Yo, bro, I slipped. You won't be so lucky this", "time.\""};

    public DowntownState(AssetManager am, Assets assets) {
        this.status = States.RUNNING;
        this.assets = assets;
        load(am, assets);
        this.isFading = false;
        this.fadeAlpha = 0;
        assets.downtownBackground.playRand(true);
    }

    @Override // kgm.NA.State
    public void update(MotionEvent touch, KeyEvent key) {
        if (key != null) {
            key.getKeyCode();
        }
        if (touch != null) {
            if (this.textBox == null && !this.isFading) {
                if (touch.getY() > (Screen.relScreenHeight * 0.8f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.9733f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.45f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.6125f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        this.assets.touchUp.playSound(this.assets.sounds);
                        this.nextState = States.INVENTORY;
                        this.isFading = true;
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.8f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.9833f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.26d) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.4f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        this.assets.touchUp.playSound(this.assets.sounds);
                        this.nextState = States.MAP;
                        this.isFading = true;
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.306f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.9f) + Screen.relYZero && touch.getX() > Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.17f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        this.assets.touchUp.playSound(this.assets.sounds);
                        this.nextState = States.POLICE;
                        this.isFading = true;
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.358f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.558f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.225f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.33f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        this.assets.touchUp.playSound(this.assets.sounds);
                        this.nextState = States.CLUB;
                        this.isFading = true;
                    }
                }
                if (touch.getY() > Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.283f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.325f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.572f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        this.assets.touchUp.playSound(this.assets.sounds);
                        this.nextState = States.LOBBY;
                        this.isFading = true;
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.266f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.7f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.5875f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.695f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        this.assets.touchUp.playSound(this.assets.sounds);
                        this.nextState = States.CASINO;
                        this.isFading = true;
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.536f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.9f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.697f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.787f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        this.assets.touchUp.playSound(this.assets.sounds);
                        this.nextState = States.PAWN;
                        this.isFading = true;
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.6666f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.916f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.8f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 1.0f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        if (!SaveFile.wonBarFight && !SaveFile.hasBrassKnuckles) {
                            if (SaveFile.hasDrugs) {
                                this.textBox = new TextBox("dealer", this.dealerLines2, 1);
                                this.textBox.start();
                                this.assets.textBox.playSound(this.assets.sounds);
                            }
                            if (!SaveFile.hasDrugs && !SaveFile.hasMoney) {
                                this.textBox = new TextBox("dealer", this.dealerLines1, 2);
                                this.textBox.start();
                                SaveFile.hasDrugs = true;
                                this.assets.acquire.playSound(this.assets.sounds);
                                this.assets.textBox.playSound(this.assets.sounds);
                            }
                            if (SaveFile.hasMoney) {
                                this.textBox = new TextBox("dealer", this.dealerLines3, 2);
                                this.textBox.start();
                                SaveFile.hasBrassKnuckles = true;
                                this.assets.acquire.playSound(this.assets.sounds);
                                SaveFile.hasMoney = false;
                                this.assets.textBox.playSound(this.assets.sounds);
                            }
                        }
                        if (SaveFile.wonBarFight) {
                            this.textBox = new TextBox("thug", this.thugLines, 2);
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
            }
            if (this.fadeAlpha == 255) {
                this.status = this.nextState;
                Engine.lastState = States.DOWNTOWN;
            }
        }
    }

    @Override // kgm.NA.State
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawBitmap(this.assets.downtown.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        if (!SaveFile.wonBarFight) {
            canvas.drawBitmap(this.assets.dealer.bitmap, (Screen.relScreenWidth * 0.91f) + Screen.relXZero, (Screen.relScreenHeight * 0.81f) + Screen.relYZero, (Paint) null);
        }
        if (SaveFile.wonBarFight) {
            canvas.drawBitmap(this.assets.downtownThug.bitmap, (Screen.relScreenWidth * 0.91f) + Screen.relXZero, (Screen.relScreenHeight * 0.79f) + Screen.relYZero, (Paint) null);
        }
        canvas.drawBitmap(this.assets.iconMap.bitmap, (Screen.relScreenWidth * 0.2695f) + Screen.relXZero, (Screen.relScreenHeight * 0.8147f) + Screen.relYZero, (Paint) null);
        canvas.drawBitmap(this.assets.iconInventory.bitmap, (Screen.relScreenWidth * 0.4592f) + Screen.relXZero, (Screen.relScreenHeight * 0.8122f) + Screen.relYZero, (Paint) null);
        if (this.textBox != null) {
            this.textBox.draw(canvas);
        }
        canvas.drawARGB(this.fadeAlpha, 0, 0, 0);
    }

    @Override // kgm.NA.State
    public void load(AssetManager am, Assets assets) {
        assets.downtown.load(am, "downtown.png", 400, 300);
        assets.dealer.load(am, "dealer.png", 14, 32);
        assets.downtownThug.load(am, "downtown-thug.png", 14, 35);
        assets.iconMap.load(am, "icon-map.png", 47, 47);
        assets.iconInventory.load(am, "icon-inventory.png", 60, 45);
        assets.touchDown.load(assets.sounds, am, "touch-down.ogg");
        assets.touchUp.load(assets.sounds, am, "touch-up.ogg");
        assets.textBox.load(assets.sounds, am, "textbox.ogg");
        assets.downtownBackground.load(am, "downtown-background.ogg");
        assets.acquire.load(assets.sounds, am, "acquire.ogg");
    }

    @Override // kgm.NA.State
    public void delete() {
        this.assets.downtown.delete();
        this.assets.dealer.delete();
        this.assets.downtownThug.delete();
        this.assets.iconMap.delete();
        this.assets.iconInventory.delete();
        this.assets.touchDown.delete(this.assets.sounds);
        this.assets.touchUp.delete(this.assets.sounds);
        this.assets.textBox.delete(this.assets.sounds);
        this.assets.downtownBackground.delete();
        this.assets.acquire.delete(this.assets.sounds);
    }

    @Override // kgm.NA.State
    public void pause() {
        this.assets.downtownBackground.pause();
    }

    @Override // kgm.NA.State
    public void resume() {
        this.assets.downtownBackground.resume();
    }
}
