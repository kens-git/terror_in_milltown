package kgm.NA;

import android.graphics.Canvas;
import android.graphics.Paint;

/* loaded from: classes.dex */
public class Sprite {
    Image image;
    public float x;
    public float y;

    /* JADX INFO: Access modifiers changed from: package-private */
    public Sprite(Image image, float pctX, float pctY) {
        this.image = image;
        setPosition(pctX, pctY);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(this.image.bitmap, this.x, this.y, (Paint) null);
    }

    public void setPosition(float pctX, float pctY) {
        if (pctX == 0.0f) {
            this.x = Screen.relXZero;
        } else {
            this.x = (Screen.relScreenWidth * pctX) + Screen.relXZero;
        }
        if (pctY == 0.0f) {
            this.y = Screen.relYZero;
        } else {
            this.y = (Screen.relScreenHeight * pctY) + Screen.relYZero;
        }
    }

    public void setPosition(float pctX, float pctY, boolean centered) {
        setPosition(pctX, pctY);
        this.x -= this.image.getWidth() / 2;
        this.y -= this.image.getHeight() / 2;
    }

    public void setAbsPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
