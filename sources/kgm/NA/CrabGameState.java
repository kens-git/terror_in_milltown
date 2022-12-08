package kgm.NA;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;
import java.util.Random;
import java.util.Vector;

/* loaded from: classes.dex */
public class CrabGameState extends State {
    long crabTime;
    float crabWidth;
    Vector<Crab> crabs;
    int crabsLeft;
    int crabsToKill;
    float damageCount;
    int fadeAlpha;
    boolean isFading;
    States nextState;
    TextBox textBox;
    boolean textBoxShown;
    Paint textPaint;
    Random rand = new Random();
    int damageLevel = 0;
    String[] winLines = {"\"Thanks! Here, you can have this old key I", "found in the sand.\""};
    String[] winLines2 = {"\"Thanks!\""};
    String[] loseLines = {"\"It's ruined!\""};

    public CrabGameState(AssetManager am, Assets assets) {
        this.status = States.RUNNING;
        this.assets = assets;
        load(am, assets);
        this.isFading = false;
        this.fadeAlpha = 0;
        this.crabWidth = assets.crab1.getWidth();
        this.crabs = new Vector<>();
        if (Screen.relScreenWidth > 750) {
            this.crabsLeft = 200;
            this.crabsToKill = 200;
        } else {
            this.crabsLeft = 100;
            this.crabsToKill = 100;
        }
        this.textPaint = new Paint();
        this.textPaint.setTextSize(Screen.relScreenWidth * 0.1f);
        this.crabTime = System.currentTimeMillis();
    }

    @Override // kgm.NA.State
    public void update(MotionEvent touch, KeyEvent key) {
        if (touch != null) {
            if (this.textBox == null && touch.getAction() == 0) {
                int i = 0;
                while (true) {
                    if (i >= this.crabs.size()) {
                        break;
                    }
                    if (touch.getX() > this.crabs.elementAt(i).x * 0.95f) {
                        if (touch.getX() < this.crabs.elementAt(i).frame1.getWidth() + (1.05f * this.crabs.elementAt(i).x) && touch.getY() > this.crabs.elementAt(i).y * 0.95f) {
                            if (touch.getY() < this.crabs.elementAt(i).frame1.getHeight() + (1.05f * this.crabs.elementAt(i).y) && !this.crabs.elementAt(i).isDeleted()) {
                                this.crabs.elementAt(i).delete();
                                this.crabsToKill--;
                                this.assets.crabKill.playSound(this.assets.sounds, 0.25f);
                                break;
                            }
                        }
                    }
                    i++;
                }
            }
            if (touch.getX() > Screen.relXZero && touch.getAction() == 1 && this.textBox != null && !this.textBox.isFinished && this.textBox.isPaused) {
                this.textBox.resume();
            }
            touch.setLocation(-10.0f, -10.0f);
        }
        if (this.damageLevel < 5 && System.currentTimeMillis() - this.crabTime > 300 && this.crabsLeft > 0) {
            if (Screen.relScreenWidth > 750) {
                int speed = 0;
                if (this.crabsLeft >= 175) {
                    speed = 4;
                }
                if (this.crabsLeft < 175 && this.crabsLeft >= 125) {
                    speed = 3;
                }
                if (this.crabsLeft < 125 && this.crabsLeft >= 75) {
                    speed = 2;
                }
                if (this.crabsLeft < 75) {
                    speed = 1;
                }
                this.crabs.add(new Crab(speed, this.assets.crab1.getWidth(), this.assets.crab1.bitmap, this.assets.crab2.bitmap));
                this.crabTime = System.currentTimeMillis();
                this.crabsLeft--;
            } else {
                int speed2 = 0;
                if (this.crabsLeft >= 85) {
                    speed2 = 4;
                }
                if (this.crabsLeft < 85 && this.crabsLeft >= 65) {
                    speed2 = 3;
                }
                if (this.crabsLeft < 65 && this.crabsLeft >= 30) {
                    speed2 = 2;
                }
                if (this.crabsLeft < 30) {
                    speed2 = 1;
                }
                this.crabs.add(new Crab(speed2, this.assets.crab1.getWidth(), this.assets.crab1.bitmap, this.assets.crab2.bitmap));
                this.crabTime = System.currentTimeMillis();
                this.crabsLeft--;
            }
        }
        if (this.crabsToKill == 0 && this.textBox == null) {
            if (!SaveFile.hasLighthouseKey && !this.textBoxShown) {
                this.textBox = new TextBox("win", this.winLines, 2);
                SaveFile.hasLighthouseKey = true;
                this.assets.acquire.playSound(this.assets.sounds);
                this.textBox.start();
                this.textBoxShown = true;
            }
            if (SaveFile.hasLighthouseKey && !this.textBoxShown) {
                this.textBox = new TextBox("win", this.winLines2, 1);
                this.textBox.start();
                this.textBoxShown = true;
            }
        }
        if (this.damageLevel == 5 && this.textBox == null && !this.textBoxShown) {
            this.textBox = new TextBox("lose", this.loseLines, 1);
            this.textBox.start();
            this.textBoxShown = true;
        }
        if (this.textBox != null && this.textBox.isFinished) {
            this.textBox = null;
            this.isFading = true;
            this.nextState = States.BEACH;
        }
        if (this.crabs.size() > 0) {
            for (int i2 = 0; i2 < this.crabs.size(); i2++) {
                if (!this.crabs.elementAt(i2).isDeleted()) {
                    this.crabs.elementAt(i2).update(this.assets);
                    if (this.crabs.elementAt(i2).damage()) {
                        int num = this.rand.nextInt(5) + 1;
                        if (num == 1) {
                            this.assets.castleDamage.playSound(this.assets.sounds);
                        }
                        if (Screen.relScreenWidth > 750) {
                            this.damageCount = this.crabs.elementAt(i2).damageAmount() + this.damageCount;
                        } else {
                            this.damageCount += 0.18f;
                        }
                        if (this.damageCount > 500.0f) {
                            this.damageLevel++;
                            this.damageCount = 0.0f;
                        }
                    }
                }
            }
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
        canvas.drawARGB(MotionEventCompat.ACTION_MASK, 203, 177, 68);
        canvas.drawBitmap(this.assets.starfish.bitmap, (Screen.relScreenWidth * 0.7f) + Screen.relXZero, (Screen.relScreenHeight * 0.1f) + Screen.relYZero, (Paint) null);
        if (this.damageLevel == 1 || this.damageLevel == 0) {
            canvas.drawBitmap(this.assets.castle1.bitmap, (Screen.relScreenWidth * 0.2788f) + Screen.relXZero, (Screen.relScreenHeight * 0.3074f) + Screen.relYZero, (Paint) null);
        }
        if (this.damageLevel == 2) {
            canvas.drawBitmap(this.assets.castle2.bitmap, (Screen.relScreenWidth * 0.2788f) + Screen.relXZero, (Screen.relScreenHeight * 0.4224f) + Screen.relYZero, (Paint) null);
        }
        if (this.damageLevel == 3) {
            canvas.drawBitmap(this.assets.castle3.bitmap, (Screen.relScreenWidth * 0.2788f) + Screen.relXZero, (Screen.relScreenHeight * 0.4924f) + Screen.relYZero, (Paint) null);
        }
        if (this.damageLevel == 4) {
            canvas.drawBitmap(this.assets.castle4.bitmap, (Screen.relScreenWidth * 0.2788f) + Screen.relXZero, (Screen.relScreenHeight * 0.5874f) + Screen.relYZero, (Paint) null);
        }
        if (this.damageLevel >= 5) {
            canvas.drawBitmap(this.assets.castle5.bitmap, (Screen.relScreenWidth * 0.2788f) + Screen.relXZero, (Screen.relScreenHeight * 0.5874f) + Screen.relYZero, (Paint) null);
        }
        if (this.crabs.size() > 0) {
            for (int i = 0; i < this.crabs.size(); i++) {
                if (!this.crabs.elementAt(i).isDeleted()) {
                    this.crabs.elementAt(i).draw(canvas);
                }
            }
        }
        if (this.textBox != null) {
            this.textBox.draw(canvas);
        }
        canvas.drawText(Integer.toString(this.crabsToKill), (Screen.relScreenWidth * 0.05f) + Screen.relXZero, (Screen.absScreenHeight * 0.15f) + Screen.relYZero, this.textPaint);
        canvas.drawARGB(this.fadeAlpha, 0, 0, 0);
    }

    @Override // kgm.NA.State
    public void load(AssetManager am, Assets assets) {
        assets.crab1.load(am, "crab-1.png", 70, 25);
        assets.crab2.load(am, "crab-2.png", 69, 26);
        assets.starfish.load(am, "starfish.png", 89, 37);
        assets.castle1.load(am, "castle-1.png", 184, 143);
        assets.castle2.load(am, "castle-2.png", 178, 109);
        assets.castle3.load(am, "castle-3.png", 178, 88);
        assets.castle4.load(am, "castle-4.png", 178, 58);
        assets.castle5.load(am, "castle-5.png", 178, 58);
        assets.crabClick.load(assets.sounds, am, "crab-click.ogg");
        assets.crabKill.load(assets.sounds, am, "crab-kill.ogg");
        assets.castleDamage.load(assets.sounds, am, "castle-damage.ogg");
        assets.acquire.load(assets.sounds, am, "acquire.ogg");
    }

    @Override // kgm.NA.State
    public void delete() {
        this.assets.crab1.delete();
        this.assets.crab2.delete();
        this.assets.starfish.delete();
        this.assets.castle1.delete();
        this.assets.castle2.delete();
        this.assets.castle3.delete();
        this.assets.castle4.delete();
        this.assets.castle5.delete();
        this.assets.crabClick.delete(this.assets.sounds);
        this.assets.crabKill.delete(this.assets.sounds);
        this.assets.castleDamage.delete(this.assets.sounds);
        this.assets.acquire.delete(this.assets.sounds);
    }

    @Override // kgm.NA.State
    public void pause() {
    }

    @Override // kgm.NA.State
    public void resume() {
    }
}
