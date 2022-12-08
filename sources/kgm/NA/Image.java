package kgm.NA;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public class Image {
    public static final String filepath = "image/";
    public Bitmap bitmap;
    public boolean isLoaded = false;

    public void load(AssetManager am, String filename, int baselineWidth, int baselineHeight) {
        float imgWidthPct;
        float imgHeightPct;
        if (Screen.relScreenWidth < Screen.relScreenHeight) {
            imgWidthPct = baselineWidth / 300.0f;
            imgHeightPct = baselineHeight / 400.0f;
        } else {
            imgWidthPct = baselineWidth / 400.0f;
            imgHeightPct = baselineHeight / 300.0f;
        }
        int scaleX = (int) ((Screen.relScreenWidth * imgWidthPct) + 0.5f);
        int scaleY = (int) ((Screen.relScreenHeight * imgHeightPct) + 0.5f);
        try {
            InputStream is = am.open(filepath + filename);
            this.bitmap = BitmapFactory.decodeStream(is);
            this.isLoaded = true;
            is.close();
            Bitmap temp = Bitmap.createScaledBitmap(this.bitmap, scaleX, scaleY, false);
            this.bitmap = temp;
        } catch (IOException e) {
            Log.d("Image", "failed to load" + filename);
            this.bitmap = null;
            this.isLoaded = false;
        }
    }

    public void delete() {
        if (this.bitmap != null) {
            this.bitmap.recycle();
        }
        this.isLoaded = false;
    }

    public int getWidth() {
        return this.bitmap.getWidth();
    }

    public int getHeight() {
        return this.bitmap.getHeight();
    }
}
