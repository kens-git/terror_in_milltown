package kgm.NA;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;

/* loaded from: classes.dex */
public class ForestState extends State {
    int fadeAlpha;
    boolean isFading;
    String[] lines = {"It's too scary to go in there!"};
    States nextState;
    TextBox textBox;

    public ForestState(AssetManager am, Assets assets) {
        this.status = States.RUNNING;
        this.assets = assets;
        load(am, assets);
        this.isFading = false;
        this.fadeAlpha = 0;
        assets.forestBackground.playRand(true);
    }

    @Override // kgm.NA.State
    public void update(MotionEvent touch, KeyEvent key) {
        if (key != null) {
            key.getKeyCode();
        }
        if (touch != null && !this.isFading) {
            if (this.textBox == null) {
                if (touch.getY() > (Screen.relScreenHeight * 0.8066f) + Screen.relYZero && touch.getY() < Screen.relScreenHeight + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.6536f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.81f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        this.isFading = true;
                        this.nextState = States.MAP;
                        this.assets.touchUp.playSound(this.assets.sounds);
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.8066f) + Screen.relYZero && touch.getY() < Screen.relScreenHeight + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.81f) + Screen.relXZero && touch.getX() < Screen.relScreenWidth + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        this.isFading = true;
                        this.nextState = States.INVENTORY;
                        this.assets.touchUp.playSound(this.assets.sounds);
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.1833f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.45f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.125f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.355f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        this.isFading = true;
                        this.nextState = States.CABIN;
                        this.assets.touchUp.playSound(this.assets.sounds);
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.2433f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.5166f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.5575f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.735f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        if (SaveFile.beatHuntingGame) {
                            this.isFading = true;
                            this.nextState = States.MAZE;
                            this.assets.touchUp.playSound(this.assets.sounds);
                        } else {
                            this.textBox = new TextBox("maze", this.lines, 1);
                            this.textBox.start();
                            this.assets.textBox.playSound(this.assets.sounds);
                        }
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.1533f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.4066f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.7625f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.9675d) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        this.isFading = true;
                        this.nextState = States.TREE_FORT;
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
                Engine.lastState = States.FOREST;
            }
        }
    }

    @Override // kgm.NA.State
    public void draw(Canvas canvas) {
        canvas.drawBitmap(this.assets.forest.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        canvas.drawBitmap(this.assets.iconMap.bitmap, (Screen.relScreenWidth * 0.673f) + Screen.relXZero, (Screen.relScreenHeight * 0.8288f) + Screen.relYZero, (Paint) null);
        canvas.drawBitmap(this.assets.iconInventory.bitmap, (Screen.relScreenWidth * 0.8279f) + Screen.relXZero, (Screen.relScreenHeight * 0.8308f) + Screen.relYZero, (Paint) null);
        if (this.textBox != null) {
            this.textBox.draw(canvas);
        }
        canvas.drawARGB(this.fadeAlpha, 0, 0, 0);
    }

    @Override // kgm.NA.State
    public void load(AssetManager am, Assets assets) {
        assets.forest.load(am, "forest.png", 400, 300);
        assets.iconMap.load(am, "icon-map.png", 47, 47);
        assets.iconInventory.load(am, "icon-inventory.png", 60, 47);
        assets.touchDown.load(assets.sounds, am, "touch-down.ogg");
        assets.touchUp.load(assets.sounds, am, "touch-up.ogg");
        assets.textBox.load(assets.sounds, am, "textbox.ogg");
        assets.forestBackground.load(am, "forest-background.ogg");
    }

    @Override // kgm.NA.State
    public void delete() {
        this.assets.forest.delete();
        this.assets.iconMap.delete();
        this.assets.iconInventory.delete();
        this.assets.touchDown.delete(this.assets.sounds);
        this.assets.touchUp.delete(this.assets.sounds);
        this.assets.textBox.delete(this.assets.sounds);
        this.assets.forestBackground.delete();
    }

    @Override // kgm.NA.State
    public void pause() {
        this.assets.forestBackground.pause();
    }

    @Override // kgm.NA.State
    public void resume() {
        this.assets.forestBackground.resume();
    }
}
