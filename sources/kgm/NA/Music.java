package kgm.NA;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.util.Log;
import java.io.IOException;
import java.util.Random;

/* loaded from: classes.dex */
public class Music {
    public static final String filepath = "music/";
    public boolean isLoaded = false;
    public boolean isPaused;
    MediaPlayer mediaPlayer;

    public void load(AssetManager am, String filename) {
        this.mediaPlayer = new MediaPlayer();
        try {
            AssetFileDescriptor assetFD = am.openFd(filepath + filename);
            this.mediaPlayer.setDataSource(assetFD.getFileDescriptor(), assetFD.getStartOffset(), assetFD.getLength());
            assetFD.close();
            this.mediaPlayer.prepare();
            this.isLoaded = true;
        } catch (IOException e) {
            Log.d("Music", "failed to load file " + filename);
            this.mediaPlayer = null;
        } catch (IllegalStateException e2) {
            load(am, filename);
        }
    }

    public void play(boolean loop) {
        if (this.mediaPlayer != null) {
            if (!this.mediaPlayer.isPlaying()) {
                this.mediaPlayer.start();
            }
            if (loop) {
                this.mediaPlayer.setLooping(loop);
            }
        }
        if (!SaveFile.musicOn) {
            this.mediaPlayer.setVolume(0.0f, 0.0f);
        }
    }

    public void playRand(boolean loop) {
        if (this.mediaPlayer != null) {
            if (!this.mediaPlayer.isPlaying()) {
                Random rand = new Random();
                int pos = rand.nextInt(this.mediaPlayer.getDuration()) + 1;
                this.mediaPlayer.seekTo(pos);
                this.mediaPlayer.start();
            }
            if (loop) {
                this.mediaPlayer.setLooping(loop);
            }
        }
        if (!SaveFile.musicOn) {
            this.mediaPlayer.setVolume(0.0f, 0.0f);
        }
    }

    public void setLooping(boolean loop) {
        if (this.mediaPlayer != null) {
            if (loop) {
                this.mediaPlayer.setLooping(true);
            } else {
                this.mediaPlayer.setLooping(false);
            }
        }
    }

    public void pause() {
        if (this.isLoaded && this.mediaPlayer.isPlaying()) {
            this.mediaPlayer.pause();
            this.isPaused = true;
        }
    }

    public void stop() {
        if (this.mediaPlayer.isPlaying()) {
            this.mediaPlayer.stop();
        }
    }

    public void restart(boolean loop) {
        stop();
        play(loop);
    }

    public void resume() {
        if (this.mediaPlayer != null && !this.mediaPlayer.isPlaying()) {
            this.mediaPlayer.start();
        }
    }

    public void delete() {
        if (this.mediaPlayer != null) {
            this.mediaPlayer.release();
        }
        this.isLoaded = false;
        this.mediaPlayer = null;
    }

    public void setVolume(float volume) {
        if (this.mediaPlayer != null) {
            if (SaveFile.musicOn) {
                this.mediaPlayer.setVolume(volume, volume);
            } else {
                this.mediaPlayer.setVolume(0.0f, 0.0f);
            }
        }
    }

    public void setVolume(float left, float right) {
        if (this.mediaPlayer != null) {
            this.mediaPlayer.setVolume(left, right);
        }
    }
}
