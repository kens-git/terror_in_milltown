package kgm.NA;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

/* loaded from: classes.dex */
public class DSprite extends Sprite {
    Paint alpha;
    float centerX;
    float centerY;
    Matrix matRotation;
    Matrix matScale;
    Matrix matTotal;
    Matrix matTranslation;
    float rotation;
    float scaleX;
    float scaleY;

    public DSprite(Image image, float pctX, float pctY) {
        super(image, pctX, pctY);
        this.centerX = getCenterX();
        this.centerY = getCenterY();
        this.scaleX = 1.0f;
        this.scaleY = 1.0f;
        this.rotation = 0.0f;
        this.matRotation = new Matrix();
        this.matTranslation = new Matrix();
        this.matScale = new Matrix();
        this.matTotal = new Matrix();
        this.alpha = new Paint();
    }

    @Override // kgm.NA.Sprite
    public void draw(Canvas canvas) {
        this.matScale = new Matrix();
        this.matScale.setScale(this.scaleX, this.scaleY);
        this.matRotation = new Matrix();
        this.matRotation.setRotate(this.rotation, this.centerX, this.centerY);
        this.matTranslation = new Matrix();
        this.matTranslation.setTranslate(this.x, this.y);
        this.matTotal = new Matrix();
        this.matTotal.postConcat(this.matRotation);
        this.matTotal.postConcat(this.matScale);
        this.matTotal.postConcat(this.matTranslation);
        canvas.drawBitmap(this.image.bitmap, this.matTotal, this.alpha);
    }

    public void rotate(float degrees, boolean reset) {
        if (reset) {
            this.rotation = 0.0f;
        }
        this.rotation += degrees;
    }

    public void scale(float scaleX, float scaleY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    public float getCenterX() {
        this.centerX = this.image.bitmap.getWidth() / 2;
        return this.centerX;
    }

    public float getCenterY() {
        this.centerY = this.image.bitmap.getHeight() / 2;
        return this.centerY;
    }

    public void setAlpha(int alpha) {
        this.alpha.setAlpha(alpha);
    }
}
