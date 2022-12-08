package kgm.NA;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;

/* loaded from: classes.dex */
public class SuburbsState extends State {
    int fadeAlpha;
    boolean isFading;
    States nextState;
    TextBox textBox;
    String[] doorLines = {"No one answered the door."};
    String[] lockedLines = {"It's locked."};

    public SuburbsState(AssetManager am, Assets assets) {
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
                if (touch.getX() > (Screen.relScreenWidth * 0.295f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.4375f) + Screen.relXZero && touch.getY() > (Screen.relScreenHeight * 0.7666f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.9666f) + Screen.relYZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        this.isFading = true;
                        this.nextState = States.MAP;
                        this.assets.touchUp.playSound(this.assets.sounds);
                    }
                }
                if (touch.getX() > (Screen.relScreenWidth * 0.5125f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.6875f) + Screen.relXZero && touch.getY() > (Screen.relScreenHeight * 0.7666f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.9666f) + Screen.relYZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        this.isFading = true;
                        this.nextState = States.INVENTORY;
                        this.assets.touchUp.playSound(this.assets.sounds);
                    }
                }
                if (touch.getX() > (Screen.relScreenWidth * 0.025f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.12f) + Screen.relXZero && touch.getY() > (Screen.relScreenHeight * 0.566f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.8666f) + Screen.relYZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        this.isFading = true;
                        this.nextState = States.HOUSE_ONE;
                        this.assets.touchUp.playSound(this.assets.sounds);
                    }
                }
                if (touch.getX() > (Screen.relScreenWidth * 0.1625f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.2475f) + Screen.relXZero && touch.getY() > (Screen.relScreenHeight * 0.2933f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.5666f) + Screen.relYZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        if (SaveFile.talkedToKid) {
                            this.isFading = true;
                            this.nextState = States.HOUSE_TWO;
                            this.assets.touchUp.playSound(this.assets.sounds);
                        } else {
                            this.textBox = new TextBox("noentry", this.doorLines, 1);
                            this.textBox.start();
                            this.assets.textBox.playSound(this.assets.sounds);
                        }
                    }
                }
                if (touch.getX() > (Screen.relScreenWidth * 0.3575f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.655f) + Screen.relXZero && touch.getY() > (Screen.relScreenHeight * 0.0666f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.29f) + Screen.relYZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        this.isFading = true;
                        this.nextState = States.MALL;
                        this.assets.touchUp.playSound(this.assets.sounds);
                    }
                }
                if (touch.getX() > (Screen.relScreenWidth * 0.8875f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.975f) + Screen.relXZero && touch.getY() > (Screen.relScreenHeight * 0.5833f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.8666f) + Screen.relYZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        if (!SaveFile.lightkeeperLeft) {
                            this.isFading = true;
                            this.nextState = States.HOUSE_THREE;
                            this.assets.touchUp.playSound(this.assets.sounds);
                        } else {
                            this.textBox = new TextBox("house3", this.doorLines, 1);
                            this.textBox.start();
                            this.assets.textBox.playSound(this.assets.sounds);
                        }
                    }
                }
                if (touch.getX() > (Screen.relScreenWidth * 0.6925f) + Screen.relXZero && touch.getX() < Screen.relScreenWidth + Screen.relXZero && touch.getY() > (Screen.relScreenHeight * 0.3266f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.56f) + Screen.relYZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        this.isFading = true;
                        this.nextState = States.PARK;
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
        if (this.isFading) {
            this.fadeAlpha += 16;
            if (this.fadeAlpha > 255) {
                this.fadeAlpha = MotionEventCompat.ACTION_MASK;
                this.status = this.nextState;
                Engine.lastState = States.SUBURBS;
            }
        }
    }

    @Override // kgm.NA.State
    public void draw(Canvas canvas) {
        canvas.drawBitmap(this.assets.suburbs.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        canvas.drawBitmap(this.assets.iconMap.bitmap, (Screen.relScreenWidth * 0.3064f) + Screen.relXZero, (Screen.relScreenHeight * 0.7884f) + Screen.relYZero, (Paint) null);
        canvas.drawBitmap(this.assets.iconInventory.bitmap, (Screen.relScreenWidth * 0.5259f) + Screen.relXZero, (Screen.relScreenHeight * 0.7891f) + Screen.relYZero, (Paint) null);
        if (this.textBox != null) {
            this.textBox.draw(canvas);
        }
        canvas.drawARGB(this.fadeAlpha, 0, 0, 0);
    }

    @Override // kgm.NA.State
    public void load(AssetManager am, Assets assets) {
        assets.suburbs.load(am, "suburbs.png", 400, 300);
        assets.iconMap.load(am, "icon-map.png", 47, 47);
        assets.iconInventory.load(am, "icon-inventory.png", 60, 45);
        assets.touchDown.load(assets.sounds, am, "touch-down.ogg");
        assets.touchUp.load(assets.sounds, am, "touch-up.ogg");
        assets.textBox.load(assets.sounds, am, "textbox.ogg");
    }

    @Override // kgm.NA.State
    public void delete() {
        this.assets.suburbs.delete();
        this.assets.iconMap.delete();
        this.assets.iconInventory.delete();
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
