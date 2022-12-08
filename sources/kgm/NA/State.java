package kgm.NA;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;

/* loaded from: classes.dex */
public abstract class State {
    public Assets assets;
    public States status;

    public abstract void delete();

    public abstract void load(AssetManager assetManager, Assets assets);

    public abstract void pause();

    public abstract void resume();

    public abstract void update(MotionEvent motionEvent, KeyEvent keyEvent);

    public void draw(Canvas canvas) {
        canvas.drawRGB(0, 0, 0);
    }

    public States getStatus() {
        return this.status;
    }
}
