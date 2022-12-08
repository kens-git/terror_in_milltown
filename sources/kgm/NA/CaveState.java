package kgm.NA;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;
import java.util.Random;

/* loaded from: classes.dex */
public class CaveState extends State {
    int fadeAlpha;
    boolean isFading;
    States nextState;
    TextBox textBox;
    Random rand = new Random();
    String[] lines1 = {"\"This is a restricted area.\""};
    String[] lines2 = {"You used the lighthouse light to light up", "the cave."};
    String[] lines3 = {"It's too dark to go in there!"};

    public CaveState(AssetManager am, Assets assets) {
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
                if (touch.getY() > (Screen.relScreenHeight * 0.8f) + Screen.relYZero && touch.getY() < Screen.relScreenHeight + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.3443f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.515f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        this.nextState = States.MAP;
                        this.isFading = true;
                        this.assets.touchUp.playSound(this.assets.sounds);
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.8f) + Screen.relYZero && touch.getY() < Screen.relScreenHeight + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.515f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.7125f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        this.nextState = States.INVENTORY;
                        this.isFading = true;
                        this.assets.touchUp.playSound(this.assets.sounds);
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.15f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.6837f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.26f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.785f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        if (SaveFile.agentsLeft) {
                            if (!SaveFile.hasLight) {
                                this.textBox = new TextBox("toodark", this.lines3, 1);
                                this.textBox.start();
                                this.assets.textBox.playSound(this.assets.sounds);
                            }
                            if (SaveFile.caveIsLit) {
                                this.isFading = true;
                                this.nextState = States.FINAL;
                                this.assets.touchUp.playSound(this.assets.sounds);
                            }
                            if (SaveFile.hasLight && !SaveFile.caveIsLit) {
                                SaveFile.caveIsLit = true;
                                this.textBox = new TextBox("fuck", this.lines2, 2);
                                this.textBox.start();
                                this.assets.textBox.playSound(this.assets.sounds);
                            }
                        } else {
                            this.textBox = new TextBox("noentry", this.lines1, 1);
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
                Engine.lastState = States.CAVE;
            }
        }
    }

    @Override // kgm.NA.State
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawBitmap(this.assets.caveBackground.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        if (!SaveFile.caveIsLit) {
            canvas.drawBitmap(this.assets.caveCover.bitmap, (Screen.relScreenWidth * 0.094f) + Screen.relXZero, (Screen.relScreenHeight * 0.0429f) + Screen.relYZero, (Paint) null);
        }
        if (!SaveFile.agentsLeft) {
            canvas.drawBitmap(this.assets.agents.bitmap, (Screen.relScreenWidth * 0.41f) + Screen.relXZero, (Screen.relScreenHeight * 0.3246f) + Screen.relYZero, (Paint) null);
        }
        canvas.drawBitmap(this.assets.caveForeground.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        canvas.drawBitmap(this.assets.iconMap.bitmap, (Screen.relScreenWidth * 0.3854f) + Screen.relXZero, (Screen.relScreenHeight * 0.8168f) + Screen.relYZero, (Paint) null);
        canvas.drawBitmap(this.assets.iconInventory.bitmap, (Screen.relScreenWidth * 0.5529f) + Screen.relXZero, (Screen.relScreenHeight * 0.8293f) + Screen.relYZero, (Paint) null);
        if (this.textBox != null) {
            this.textBox.draw(canvas);
        }
        canvas.drawARGB(this.fadeAlpha, 0, 0, 0);
    }

    @Override // kgm.NA.State
    public void load(AssetManager am, Assets assets) {
        assets.caveBackground.load(am, "cave-background.png", 400, 300);
        assets.caveForeground.load(am, "cave-foreground.png", 400, 300);
        assets.caveCover.load(am, "cave-cover.png", 326, 190);
        assets.agents.load(am, "agents.png", 86, 123);
        assets.iconMap.load(am, "icon-map.png", 47, 47);
        assets.iconInventory.load(am, "icon-inventory.png", 60, 45);
        assets.touchDown.load(assets.sounds, am, "touch-down.ogg");
        assets.touchUp.load(assets.sounds, am, "touch-up.ogg");
        assets.textBox.load(assets.sounds, am, "textbox.ogg");
        assets.forestBackground.load(am, "forest-background.ogg");
    }

    @Override // kgm.NA.State
    public void delete() {
        this.assets.caveBackground.delete();
        this.assets.caveForeground.delete();
        this.assets.caveCover.delete();
        this.assets.agents.delete();
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
