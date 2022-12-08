package kgm.NA;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;
import java.util.Random;

/* loaded from: classes.dex */
public class HuntingState extends State {
    boolean bearActive;
    float bearEndX;
    boolean bearLeft;
    float bearStartX;
    float bearX;
    float bearY;
    boolean bugActive;
    float bugEndX;
    boolean bugLeft;
    float bugStartX;
    float bugX;
    float bugY;
    boolean deerActive;
    float deerEndX;
    boolean deerLeft;
    float deerStartX;
    float deerX;
    float deerY;
    int fadeAlpha;
    boolean isFading;
    int kills;
    int maxMisses;
    int misses;
    States nextState;
    boolean rabbitActive;
    float rabbitEndX;
    boolean rabbitLeft;
    float rabbitStartX;
    float rabbitX;
    float rabbitY;
    float speed;
    boolean squirrelActive;
    float squirrelEndX;
    boolean squirrelLeft;
    float squirrelStartX;
    float squirrelX;
    float squirrelY;
    TextBox textBox;
    Paint textPaint;
    Random rand = new Random();
    String[] loseLines = {"You let the infected animals escape!"};

    public HuntingState(AssetManager am, Assets assets) {
        this.status = States.RUNNING;
        this.assets = assets;
        load(am, assets);
        this.isFading = false;
        this.fadeAlpha = 0;
        this.textPaint = new Paint();
        this.textPaint.setTextSize(Screen.relScreenWidth * 0.08f);
        this.textPaint.setColor(-16777216);
        this.kills = 0;
        this.misses = 0;
        this.speed = Screen.relScreenWidth * 0.002f;
        this.maxMisses = 10;
        this.bearStartX = (Screen.relScreenWidth * (-0.0907f)) + Screen.relXZero;
        this.bearEndX = (Screen.relScreenWidth * 0.0524f) + Screen.relXZero;
        this.bearY = (Screen.relScreenHeight * 0.3628f) + Screen.relYZero;
        this.squirrelStartX = (Screen.relScreenWidth * 0.4195f) + Screen.relXZero;
        this.squirrelEndX = (Screen.relScreenWidth * 0.3188f) + Screen.relXZero;
        this.squirrelY = (Screen.relScreenHeight * 0.634f) + Screen.relYZero;
        this.rabbitStartX = (Screen.relScreenWidth * 0.4404f) + Screen.relXZero;
        this.rabbitEndX = (Screen.relScreenWidth * 0.5104f) + Screen.relXZero;
        this.rabbitY = (Screen.relScreenHeight * 0.6207f) + Screen.relYZero;
        this.deerStartX = (Screen.relScreenWidth * 0.9297f) + Screen.relXZero;
        this.deerEndX = (Screen.relScreenWidth * 0.7797f) + Screen.relXZero;
        this.deerY = (Screen.relScreenHeight * 0.3473f) + Screen.relYZero;
    }

    @Override // kgm.NA.State
    public void update(MotionEvent touch, KeyEvent key) {
        if (touch != null) {
            if (touch.getX() > Screen.relXZero && touch.getAction() == 1 && this.textBox != null && !this.textBox.isFinished && this.textBox.isPaused) {
                this.textBox.resume();
            }
            if (!this.bearActive && !this.squirrelActive && !this.rabbitActive && !this.deerActive && this.kills < 40 && this.misses < this.maxMisses) {
                int num = this.rand.nextInt(4) + 1;
                switch (num) {
                    case 1:
                        this.bearActive = true;
                        this.bearX = this.bearStartX;
                        this.bearLeft = false;
                        this.assets.bearSound.playSound(this.assets.sounds);
                        break;
                    case 2:
                        this.squirrelActive = true;
                        this.squirrelX = this.squirrelStartX;
                        this.squirrelLeft = true;
                        this.assets.squirrelSound.playSound(this.assets.sounds);
                        break;
                    case 3:
                        this.rabbitActive = true;
                        this.rabbitX = this.rabbitStartX;
                        this.rabbitLeft = false;
                        this.assets.rabbitSound.playSound(this.assets.sounds);
                        break;
                    case 4:
                        this.deerActive = true;
                        this.deerX = this.deerStartX;
                        this.deerLeft = true;
                        this.assets.deerSound.playSound(this.assets.sounds);
                        break;
                    default:
                        this.bearActive = true;
                        this.bearX = this.bearStartX;
                        this.bearLeft = false;
                        break;
                }
            }
            if (this.bearActive) {
                if (touch.getX() > this.bearX && touch.getX() < this.bearX + this.assets.bear.bitmap.getWidth() && touch.getY() > this.bearY && touch.getY() < this.bearY + this.assets.bear.bitmap.getHeight()) {
                    touch.getAction();
                    if (touch.getAction() == 1) {
                        this.bearActive = false;
                        this.kills++;
                        this.assets.bearSound.stop(this.assets.sounds);
                        if (this.kills % 10 == 0) {
                            this.speed += Screen.relScreenWidth * 0.001f;
                        }
                    }
                }
                if (this.bearLeft) {
                    this.bearX -= this.speed;
                    if (this.bearX < this.bearStartX) {
                        this.bearActive = false;
                        this.misses++;
                    }
                } else {
                    this.bearX += this.speed;
                    if (this.bearX >= this.bearEndX) {
                        this.bearLeft = true;
                    }
                }
            }
            if (this.squirrelActive) {
                if (touch.getX() > this.squirrelX && touch.getX() < this.squirrelX + this.assets.squirrel.bitmap.getWidth() && touch.getY() > this.squirrelY && touch.getY() < this.squirrelY + this.assets.squirrel.bitmap.getHeight()) {
                    touch.getAction();
                    if (touch.getAction() == 1) {
                        this.squirrelActive = false;
                        this.kills++;
                        this.assets.squirrelSound.stop(this.assets.sounds);
                        if (this.kills % 10 == 0) {
                            this.speed += Screen.relScreenWidth * 0.001f;
                        }
                    }
                }
                if (this.squirrelLeft) {
                    this.squirrelX -= this.speed;
                    if (this.squirrelX < this.squirrelEndX) {
                        this.squirrelLeft = false;
                    }
                } else {
                    this.squirrelX += this.speed;
                    if (this.squirrelX >= this.squirrelStartX) {
                        this.squirrelActive = false;
                        this.misses++;
                    }
                }
            }
            if (this.rabbitActive) {
                if (touch.getX() > this.rabbitX && touch.getX() < this.rabbitX + this.assets.rabbit.bitmap.getWidth() && touch.getY() > this.rabbitY && touch.getY() < this.rabbitY + this.assets.rabbit.bitmap.getHeight()) {
                    touch.getAction();
                    if (touch.getAction() == 1) {
                        this.rabbitActive = false;
                        this.kills++;
                        this.assets.rabbitSound.stop(this.assets.sounds);
                        if (this.kills % 10 == 0) {
                            this.speed += Screen.relScreenWidth * 0.001f;
                        }
                    }
                }
                if (this.rabbitLeft) {
                    this.rabbitX -= this.speed;
                    if (this.rabbitX < this.rabbitStartX) {
                        this.rabbitActive = false;
                        this.misses++;
                    }
                } else {
                    this.rabbitX += this.speed;
                    if (this.rabbitX >= this.rabbitEndX) {
                        this.rabbitLeft = true;
                    }
                }
            }
            if (this.deerActive) {
                if (touch.getX() > this.deerX && touch.getX() < this.deerX + this.assets.deer.bitmap.getWidth() && touch.getY() > this.deerY && touch.getY() < this.deerY + this.assets.deer.bitmap.getHeight()) {
                    touch.getAction();
                    if (touch.getAction() == 1) {
                        this.deerActive = false;
                        this.kills++;
                        this.assets.deerSound.stop(this.assets.sounds);
                        if (this.kills % 10 == 0) {
                            this.speed += Screen.relScreenWidth * 0.001f;
                        }
                    }
                }
                if (this.deerLeft) {
                    this.deerX -= this.speed;
                    if (this.deerX < this.deerEndX) {
                        this.deerLeft = false;
                    }
                } else {
                    this.deerX += this.speed;
                    if (this.deerX >= this.deerStartX) {
                        this.deerActive = false;
                        this.misses++;
                    }
                }
            }
            if (touch.getAction() == 0) {
                this.assets.shotgunLoad.playSound(this.assets.sounds);
            }
            if (touch.getAction() == 1) {
                this.assets.shotgunFire.playSound(this.assets.sounds, 0.5f);
            }
            touch.setLocation(-10.0f, -10.0f);
            touch.setAction(2);
            if (this.kills >= 40 && !this.isFading) {
                this.isFading = true;
                this.nextState = States.CABIN;
                SaveFile.beatHuntingGame = true;
            }
            if (this.misses >= this.maxMisses && this.textBox == null && !this.isFading) {
                this.textBox = new TextBox("lose", this.loseLines, 1);
                this.textBox.start();
            }
            if (this.textBox != null && this.textBox.isFinished) {
                this.textBox = null;
                this.isFading = true;
                this.nextState = States.CABIN;
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
    }

    @Override // kgm.NA.State
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawBitmap(this.assets.huntingBackground.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        if (this.bearActive) {
            canvas.drawBitmap(this.assets.bear.bitmap, this.bearX, this.bearY, (Paint) null);
        }
        if (this.squirrelActive) {
            canvas.drawBitmap(this.assets.squirrel.bitmap, this.squirrelX, this.squirrelY, (Paint) null);
        }
        if (this.rabbitActive) {
            canvas.drawBitmap(this.assets.rabbit.bitmap, this.rabbitX, this.rabbitY, (Paint) null);
        }
        if (this.deerActive) {
            canvas.drawBitmap(this.assets.deer.bitmap, this.deerX, this.deerY, (Paint) null);
        }
        if (this.bugActive) {
            canvas.drawBitmap(this.assets.huntingBug.bitmap, this.bugX, this.bugY, (Paint) null);
        }
        canvas.drawBitmap(this.assets.huntingForeground.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        canvas.drawText(Integer.toString(40 - this.kills), Screen.relXZero + (Screen.relScreenWidth * 0.1f), Screen.relYZero + (Screen.relScreenHeight * 0.1f), this.textPaint);
        if (this.textBox != null) {
            this.textBox.draw(canvas);
        }
        canvas.drawARGB(this.fadeAlpha, 0, 0, 0);
    }

    @Override // kgm.NA.State
    public void load(AssetManager am, Assets assets) {
        assets.huntingBackground.load(am, "hunting-background.png", 400, 300);
        assets.huntingForeground.load(am, "hunting-foreground.png", 400, 300);
        assets.bear.load(am, "bear.png", 72, 131);
        assets.squirrel.load(am, "squirrel.png", 37, 52);
        assets.rabbit.load(am, "rabbit.png", 33, 80);
        assets.deer.load(am, "deer.png", 66, 98);
        assets.shotgunLoad.load(assets.sounds, am, "shotgun-load.ogg");
        assets.shotgunFire.load(assets.sounds, am, "shotgun-fire.ogg");
        assets.bearSound.load(assets.sounds, am, "bear.ogg");
        assets.squirrelSound.load(assets.sounds, am, "squirrel.ogg");
        assets.rabbitSound.load(assets.sounds, am, "rabbit.ogg");
        assets.deerSound.load(assets.sounds, am, "deer.ogg");
    }

    @Override // kgm.NA.State
    public void delete() {
        this.assets.huntingBackground.delete();
        this.assets.huntingForeground.delete();
        this.assets.bear.delete();
        this.assets.squirrel.delete();
        this.assets.rabbit.delete();
        this.assets.deer.delete();
        this.assets.shotgunLoad.delete(this.assets.sounds);
        this.assets.shotgunFire.delete(this.assets.sounds);
        this.assets.bearSound.delete(this.assets.sounds);
        this.assets.squirrelSound.delete(this.assets.sounds);
        this.assets.rabbitSound.delete(this.assets.sounds);
        this.assets.deerSound.delete(this.assets.sounds);
    }

    @Override // kgm.NA.State
    public void pause() {
    }

    @Override // kgm.NA.State
    public void resume() {
    }
}
