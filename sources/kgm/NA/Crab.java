package kgm.NA;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import java.util.Random;

/* loaded from: classes.dex */
public class Crab {
    private boolean damage;
    private boolean deleted;
    Bitmap frame1;
    Bitmap frame2;
    public int speed;
    public float x;
    public float y;
    Random rand = new Random();
    int frameCount = 0;
    int frame = 1;
    boolean goingLeft = this.rand.nextBoolean();

    public Crab(int speed, float crabWidth, Bitmap bitmap1, Bitmap bitmap2) {
        this.speed = speed;
        if (this.goingLeft) {
            this.x = Screen.relScreenWidth + Screen.relXZero;
        } else {
            this.x = Screen.relXZero - crabWidth;
        }
        this.y = 0.0f;
        while (true) {
            if (this.y < 0.3f || this.y > 0.6856f) {
                this.y = this.rand.nextFloat();
            } else {
                this.y = (Screen.relScreenHeight * this.y) + Screen.relYZero;
                this.frame1 = bitmap1;
                this.frame2 = bitmap2;
                return;
            }
        }
    }

    public void update(Assets assets) {
        if (this.goingLeft) {
            if (this.speed == 1) {
                this.x -= Screen.relScreenWidth * 0.006f;
            }
            if (this.speed == 2) {
                this.x -= Screen.relScreenWidth * 0.004f;
            }
            if (this.speed == 3) {
                this.x -= Screen.relScreenWidth * 0.003f;
            }
            if (this.speed == 4) {
                this.x -= Screen.relScreenWidth * 0.002f;
            }
            if (this.x < (Screen.relScreenWidth * 0.7329f) + Screen.relXZero) {
                this.x = (Screen.relScreenWidth * 0.7329f) + Screen.relXZero;
                this.damage = true;
            }
        } else {
            if (this.speed == 1) {
                this.x += Screen.relScreenWidth * 0.006f;
            }
            if (this.speed == 2) {
                this.x += Screen.relScreenWidth * 0.004f;
            }
            if (this.speed == 3) {
                this.x += Screen.relScreenWidth * 0.003f;
            }
            if (this.speed == 4) {
                this.x += Screen.relScreenWidth * 0.002f;
            }
            if (this.x > (Screen.relScreenWidth * 0.1186f) + Screen.relXZero) {
                this.x = (Screen.relScreenWidth * 0.1186f) + Screen.relXZero;
                this.damage = true;
            }
        }
        this.frameCount++;
        if (this.frameCount >= this.speed * 6) {
            int num = this.rand.nextInt(5) + 1;
            if (num == 1) {
                assets.crabClick.playSound(assets.sounds);
            }
            this.frameCount = 0;
            if (this.frame == 1) {
                this.frame = 2;
            } else {
                this.frame = 1;
            }
        }
    }

    public void draw(Canvas canvas) {
        if (this.frame == 1) {
            canvas.drawBitmap(this.frame1, this.x, this.y, (Paint) null);
        } else {
            canvas.drawBitmap(this.frame2, this.x, this.y, (Paint) null);
        }
    }

    public void delete() {
        this.deleted = true;
    }

    public boolean isDeleted() {
        return this.deleted;
    }

    public boolean damage() {
        return this.damage;
    }

    public int damageAmount() {
        if (this.speed == 4) {
            return 1;
        }
        if (this.speed == 3) {
            return 2;
        }
        if (this.speed == 2) {
            return 3;
        }
        return this.speed == 1 ? 4 : 4;
    }
}
