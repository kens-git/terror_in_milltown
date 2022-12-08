package kgm.NA;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;
import java.util.Random;

/* loaded from: classes.dex */
public class MazeState extends State {
    int count;
    int currentFrame;
    int fadeAlpha;
    boolean isFading;
    States nextState;
    Random rand = new Random();
    TextBox textBox;

    public MazeState(AssetManager am, Assets assets) {
        this.status = States.RUNNING;
        this.assets = assets;
        load(am, assets);
        this.isFading = false;
        this.fadeAlpha = 0;
        this.currentFrame = this.rand.nextInt(3) + 1;
        this.count = 0;
        assets.forestBackground.playRand(true);
    }

    @Override // kgm.NA.State
    public void update(MotionEvent touch, KeyEvent key) {
        if (key != null) {
            key.getKeyCode();
        }
        if (touch != null && !this.isFading) {
            if (touch.getX() > Screen.relXZero) {
                if (touch.getAction() == 0) {
                    this.assets.touchDown.playSound(this.assets.sounds);
                }
                if (touch.getAction() == 1) {
                    if (this.rand.nextInt(10) + 1 <= 2) {
                        this.nextState = States.CAVE;
                        this.isFading = true;
                    } else {
                        this.nextState = States.MAZE;
                        this.isFading = true;
                    }
                    this.assets.touchUp.playSound(this.assets.sounds);
                }
            }
            if (touch.getX() > Screen.relXZero && this.textBox != null && !this.textBox.isFinished && this.textBox.isPaused) {
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
            }
        }
    }

    @Override // kgm.NA.State
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.assets.maze1.isLoaded) {
            canvas.drawBitmap(this.assets.maze1.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        }
        if (this.assets.maze2.isLoaded) {
            canvas.drawBitmap(this.assets.maze2.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        }
        if (this.assets.maze3.isLoaded) {
            canvas.drawBitmap(this.assets.maze3.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        }
        if (this.textBox != null) {
            this.textBox.draw(canvas);
        }
        canvas.drawARGB(this.fadeAlpha, 0, 0, 0);
    }

    @Override // kgm.NA.State
    public void load(AssetManager am, Assets assets) {
        int num = this.rand.nextInt(3) + 1;
        if (num == 1) {
            assets.maze1.load(am, "maze1.png", 400, 300);
        }
        if (num == 2) {
            assets.maze2.load(am, "maze2.png", 400, 300);
        }
        if (num == 3) {
            assets.maze3.load(am, "maze3.png", 400, 300);
        }
        assets.touchDown.load(assets.sounds, am, "touch-down.ogg");
        assets.touchUp.load(assets.sounds, am, "touch-up.ogg");
        assets.forestBackground.load(am, "forest-background.ogg");
    }

    @Override // kgm.NA.State
    public void delete() {
        if (this.assets.maze1.isLoaded) {
            this.assets.maze1.delete();
        }
        if (this.assets.maze2.isLoaded) {
            this.assets.maze2.delete();
        }
        if (this.assets.maze3.isLoaded) {
            this.assets.maze3.delete();
        }
        this.assets.touchDown.delete(this.assets.sounds);
        this.assets.touchUp.delete(this.assets.sounds);
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
