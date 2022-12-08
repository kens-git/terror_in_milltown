package kgm.NA;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.SoundPool;
import android.util.Log;
import java.io.IOException;

/* loaded from: classes.dex */
public class Sound {
    private static final String filepath = "sound/";
    public boolean isLoaded = false;
    public int id = -1;

    public void load(SoundPool soundPool, AssetManager assetManager, String filename) {
        try {
            AssetFileDescriptor assetFD = assetManager.openFd(filepath + filename);
            this.id = soundPool.load(assetFD, 1);
            this.isLoaded = true;
        } catch (IOException e) {
            Log.d("Sound", "Couldn't load file " + filename);
        }
    }

    public void delete(SoundPool soundPool) {
        soundPool.unload(this.id);
        this.isLoaded = false;
    }

    public void playSound(SoundPool soundPool) {
        if (SaveFile.soundOn) {
            soundPool.play(this.id, 1.0f, 1.0f, 0, 0, 1.0f);
        }
    }

    public void playSound(SoundPool soundPool, int playNumTimes, float playBackRate) {
        if (SaveFile.soundOn) {
            soundPool.play(this.id, 1.0f, 1.0f, 0, playNumTimes - 1, playBackRate);
        }
    }

    public void playSound(SoundPool soundPool, float volume) {
        if (SaveFile.soundOn) {
            soundPool.play(this.id, volume, volume, 0, 0, 1.0f);
        }
    }

    public void stop(SoundPool soundPool) {
        soundPool.stop(this.id);
    }
}
