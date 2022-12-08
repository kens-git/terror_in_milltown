package kgm.NA;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;

/* loaded from: classes.dex */
public class InventoryState extends State {
    Paint cashPaint;
    int fadeAlpha;
    boolean isFading;
    States lastState;
    Paint rectPaint;
    TextBox textBox;
    String[] moneyLines = {"It's a stack of money. Better keep it safe."};
    String[] drugLines = {"It's a suspicious-looking package."};
    String[] brassKnucklesLines = {"It's a pair of brass knuckles. These are", "probably illegal."};
    String[] suitLines = {"It's a rather dapper-looking suit."};
    String[] letterLines = {"It's a sealed envelope containing a letter."};
    String[] lunchboxLines = {"It's a lunchbox containing a particularly", "dry sandwich."};
    String[] elevatorLines = {"It's an access card."};
    String[] vipLines = {"It's a pass for a VIP room. You feel fancy."};
    String[] lighthouseLines = {"It's a rusty old key."};
    String[] batLines = {"It's a baseball bat. It would make a good", "weapon."};
    String[] racketLines = {"It's a tennis racket. Why did you buy it?"};
    String[] lightLines = {"It's a very large lightbulb."};
    String[] emailLines = {"It's a print-out of a rather incriminating", "e-mail."};

    public InventoryState(AssetManager am, Assets assets, States lastState) {
        this.status = States.RUNNING;
        this.assets = assets;
        this.lastState = lastState;
        load(am, assets);
        this.isFading = false;
        this.fadeAlpha = 0;
        this.rectPaint = new Paint();
        this.rectPaint.setColor(Color.rgb(85, 34, 0));
        this.cashPaint = new Paint();
        this.cashPaint.setColor(-1);
        this.cashPaint.setTextSize(Screen.relScreenHeight * 0.05f);
    }

    @Override // kgm.NA.State
    public void update(MotionEvent touch, KeyEvent key) {
        if (key != null) {
            key.getKeyCode();
        }
        if (touch != null && !this.isFading) {
            if (this.textBox == null) {
                if (touch.getY() > Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.33f) + Screen.relYZero && touch.getX() > Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.25f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        this.assets.touchUp.playSound(this.assets.sounds);
                        this.isFading = true;
                    }
                }
                if (touch.getY() > Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.33f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.25f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.5f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        if (SaveFile.hasDrugs) {
                            this.textBox = new TextBox("drugs", this.drugLines, 1);
                            this.textBox.start();
                            this.assets.textBox.playSound(this.assets.sounds);
                        }
                        if (SaveFile.hasMoney) {
                            this.textBox = new TextBox("money", this.moneyLines, 1);
                            this.textBox.start();
                            this.assets.textBox.playSound(this.assets.sounds);
                        }
                    }
                }
                if (touch.getY() > Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.33f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.5f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.75f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1 && SaveFile.hasBrassKnuckles) {
                        this.textBox = new TextBox("brass", this.brassKnucklesLines, 2);
                        this.textBox.start();
                        this.assets.textBox.playSound(this.assets.sounds);
                    }
                }
                if (touch.getY() > Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.33f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.75f) + Screen.relXZero && touch.getX() < Screen.relScreenWidth + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1 && SaveFile.hasFancyClothes) {
                        this.textBox = new TextBox("suit", this.suitLines, 1);
                        this.textBox.start();
                        this.assets.textBox.playSound(this.assets.sounds);
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.33d) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.66f) + Screen.relYZero && touch.getX() > Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.25f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1 && SaveFile.hasLetter) {
                        this.textBox = new TextBox("letter", this.letterLines, 1);
                        this.textBox.start();
                        this.assets.textBox.playSound(this.assets.sounds);
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.33f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.66f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.25f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.5f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        if (SaveFile.hasLunchbox) {
                            this.textBox = new TextBox("lunchbox", this.lunchboxLines, 2);
                            this.textBox.start();
                            this.assets.textBox.playSound(this.assets.sounds);
                        }
                        if (SaveFile.hasElevatorKey) {
                            this.textBox = new TextBox("elevator", this.elevatorLines, 1);
                            this.textBox.start();
                            this.assets.textBox.playSound(this.assets.sounds);
                        }
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.33f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.66f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.5f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.75f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1 && SaveFile.hasVIPPass) {
                        this.textBox = new TextBox("vip", this.vipLines, 1);
                        this.textBox.start();
                        this.assets.textBox.playSound(this.assets.sounds);
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.33f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.66f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.77f) + Screen.relXZero && touch.getX() < Screen.relScreenWidth + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1 && SaveFile.hasLighthouseKey) {
                        this.textBox = new TextBox("lighthousekey", this.lighthouseLines, 1);
                        this.textBox.start();
                        this.assets.textBox.playSound(this.assets.sounds);
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.66f) + Screen.relYZero && touch.getY() < Screen.relScreenHeight + Screen.relYZero && touch.getX() > Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.25f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1 && SaveFile.hasBat) {
                        this.textBox = new TextBox("bat", this.batLines, 2);
                        this.textBox.start();
                        this.assets.textBox.playSound(this.assets.sounds);
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.66f) + Screen.relYZero && touch.getY() < Screen.relScreenHeight + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.25f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.5f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1 && SaveFile.hasRacket) {
                        this.textBox = new TextBox("racket", this.racketLines, 1);
                        this.textBox.start();
                        this.assets.textBox.playSound(this.assets.sounds);
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.66f) + Screen.relYZero && touch.getY() < Screen.relScreenHeight + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.5f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.75f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1 && SaveFile.hasLight) {
                        this.textBox = new TextBox("light", this.lightLines, 1);
                        this.textBox.start();
                        this.assets.textBox.playSound(this.assets.sounds);
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.66f) + Screen.relYZero && touch.getY() < Screen.relScreenHeight + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.75f) + Screen.relXZero && touch.getX() < Screen.relScreenWidth + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1 && SaveFile.hasEmail) {
                        this.textBox = new TextBox("email", this.emailLines, 2);
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
                this.status = this.lastState;
            }
        }
    }

    @Override // kgm.NA.State
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawBitmap(this.assets.inventory.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        if (!SaveFile.hasDrugs) {
            drawSquare(canvas, 0.25f, Screen.relYZero, 0.5f, 0.33f);
            if (SaveFile.hasMoney) {
                canvas.drawBitmap(this.assets.iconMoney.bitmap, (Screen.relScreenWidth * 0.2724f) + Screen.relXZero, (Screen.relScreenHeight * 0.1124f) + Screen.relYZero, (Paint) null);
            }
        }
        if (!SaveFile.hasBrassKnuckles) {
            drawSquare(canvas, 0.5f, 0.0f, 0.75f, 0.33f);
        }
        if (!SaveFile.hasFancyClothes) {
            drawSquare(canvas, 0.75f, 0.0f, 1.0f, 0.33f);
        }
        if (!SaveFile.hasLetter) {
            drawSquare(canvas, 0.0f, 0.33f, 0.25f, 0.66f);
        }
        if (!SaveFile.hasElevatorKey) {
            drawSquare(canvas, 0.25f, 0.33f, 0.5f, 0.66f);
            if (SaveFile.hasLunchbox) {
                canvas.drawBitmap(this.assets.iconLunchbox.bitmap, (Screen.relScreenWidth * 0.2691f) + Screen.relXZero, (Screen.relScreenHeight * 0.4195f) + Screen.relYZero, (Paint) null);
            }
        }
        if (!SaveFile.hasVIPPass) {
            drawSquare(canvas, 0.5f, 0.33f, 0.75f, 0.66f);
        }
        if (!SaveFile.hasLighthouseKey) {
            drawSquare(canvas, 0.75f, 0.33f, 1.0f, 0.66f);
        }
        if (!SaveFile.hasBat) {
            drawSquare(canvas, 0.0f, 0.66f, 0.25f, 1.0f);
        }
        if (!SaveFile.hasRacket) {
            drawSquare(canvas, 0.25f, 0.66f, 0.5f, 1.0f);
        }
        if (!SaveFile.hasLight) {
            drawSquare(canvas, 0.5f, 0.66f, 0.75f, 1.0f);
        }
        if (!SaveFile.hasEmail) {
            drawSquare(canvas, 0.75f, 0.66f, 1.0f, 1.0f);
        }
        canvas.drawText("Cash: $" + Integer.toString(SaveFile.cash), (Screen.relScreenWidth * 0.02f) + Screen.relXZero, (Screen.relScreenHeight * 0.07f) + Screen.relYZero, this.cashPaint);
        if (this.textBox != null) {
            this.textBox.draw(canvas);
        }
        canvas.drawARGB(this.fadeAlpha, 0, 0, 0);
    }

    @Override // kgm.NA.State
    public void load(AssetManager am, Assets assets) {
        assets.inventory.load(am, "inventory.png", 400, 300);
        assets.iconMoney.load(am, "icon-money.png", 80, 36);
        assets.iconLunchbox.load(am, "icon-lunchbox.png", 87, 46);
        assets.touchDown.load(assets.sounds, am, "touch-down.ogg");
        assets.touchUp.load(assets.sounds, am, "touch-up.ogg");
        assets.textBox.load(assets.sounds, am, "textbox.ogg");
    }

    @Override // kgm.NA.State
    public void delete() {
        this.assets.inventory.delete();
        this.assets.iconMoney.delete();
        this.assets.iconLunchbox.delete();
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

    public void drawSquare(Canvas canvas, float leftPct, float topPct, float rightPct, float bottomPct) {
        canvas.drawRect(Screen.relXZero + (Screen.relScreenWidth * leftPct), Screen.relYZero + (Screen.relScreenHeight * topPct), Screen.relXZero + (Screen.relScreenWidth * rightPct), Screen.relYZero + (Screen.relScreenHeight * bottomPct), this.rectPaint);
    }
}
