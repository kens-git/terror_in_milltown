package kgm.NA;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;

/* loaded from: classes.dex */
public class MenuState extends State {
    int fadeAlpha;
    Paint fadePaint;
    float fadeVolume;
    long inputTime;
    long inputTimeout;
    boolean isFading;
    boolean isOptions;

    public MenuState(AssetManager am, Assets assets) {
        this.status = States.RUNNING;
        this.assets = assets;
        this.isOptions = false;
        this.isFading = false;
        this.fadeAlpha = MotionEventCompat.ACTION_MASK;
        this.fadePaint = new Paint();
        this.fadeVolume = 1.0f;
        load(am, assets);
        assets.menuMusic.play(true);
        this.inputTimeout = 50L;
    }

    @Override // kgm.NA.State
    public void update(MotionEvent touch, KeyEvent key) {
        if (key != null) {
            key.getKeyCode();
        }
        if (touch != null) {
            if (touch.getAction() == 1 || touch.getAction() == 0) {
                if (this.isOptions) {
                    if (touch.getY() > Screen.absScreenHeight * 0.158f && touch.getY() < Screen.absScreenHeight * 0.225f && touch.getX() > Screen.absScreenWidth * 0.268f && touch.getX() < Screen.absScreenWidth * 0.517f) {
                        if (touch.getAction() == 0) {
                            this.assets.touchDown.playSound(this.assets.sounds);
                        } else {
                            if (SaveFile.soundOn) {
                                SaveFile.soundOn = false;
                            } else {
                                SaveFile.soundOn = true;
                            }
                            this.assets.touchUp.playSound(this.assets.sounds);
                        }
                    }
                    if (touch.getY() > Screen.absScreenHeight * 0.308f && touch.getY() < Screen.absScreenHeight * 0.366f && touch.getX() > Screen.absScreenWidth * 0.268f && touch.getX() < Screen.absScreenWidth * 0.517f) {
                        if (touch.getAction() == 0) {
                            this.assets.touchDown.playSound(this.assets.sounds);
                        } else {
                            if (SaveFile.musicOn) {
                                SaveFile.musicOn = false;
                            } else {
                                SaveFile.musicOn = true;
                            }
                            this.assets.touchUp.playSound(this.assets.sounds);
                        }
                    }
                    if (touch.getY() > Screen.absScreenHeight * 0.491f && touch.getY() < Screen.absScreenHeight * 0.616f && touch.getX() > Screen.absScreenWidth * 0.258f && touch.getX() < Screen.absScreenWidth * 0.556f) {
                        if (touch.getAction() == 0) {
                            this.assets.touchDown.playSound(this.assets.sounds);
                        } else {
                            SaveFile.reset();
                            this.assets.touchUp.playSound(this.assets.sounds);
                        }
                    }
                    if (touch.getY() > Screen.absScreenHeight * 0.7f && touch.getY() < Screen.absScreenHeight * 0.841f && touch.getX() > Screen.absScreenWidth * 0.258f && touch.getX() < Screen.absScreenWidth * 0.556f) {
                        if (touch.getAction() == 0) {
                            this.assets.touchDown.playSound(this.assets.sounds);
                        } else {
                            this.isOptions = false;
                            this.assets.touchUp.playSound(this.assets.sounds);
                        }
                    }
                } else {
                    if (touch.getY() > Screen.absScreenHeight * 0.483f && touch.getY() < Screen.absScreenHeight * 0.63f && touch.getX() > Screen.absScreenWidth * 0.394f && touch.getX() < Screen.absScreenWidth * 0.81f) {
                        if (touch.getAction() == 0) {
                            this.assets.touchDown.playSound(this.assets.sounds);
                        } else {
                            this.isFading = true;
                            this.assets.touchUp.playSound(this.assets.sounds);
                        }
                    }
                    if (touch.getY() > Screen.absScreenHeight * 0.696f && touch.getY() < Screen.absScreenHeight * 0.833f && touch.getX() > Screen.absScreenWidth * 0.39f && touch.getX() < Screen.absScreenWidth * 0.81f) {
                        if (touch.getAction() == 0) {
                            this.assets.touchDown.playSound(this.assets.sounds);
                        } else {
                            this.isOptions = true;
                            this.assets.touchUp.playSound(this.assets.sounds);
                        }
                    }
                }
            }
            touch.setLocation(0.0f, 0.0f);
        }
        if (this.isFading) {
            this.fadeVolume -= 0.015f;
            this.assets.menuMusic.setVolume(this.fadeVolume);
            this.fadeAlpha -= 3;
            if (this.fadeAlpha < 0) {
                this.fadeAlpha = 0;
            }
            this.fadePaint.setAlpha(this.fadeAlpha);
            if (this.fadeAlpha <= 0) {
                if (SaveFile.isFirstRun) {
                    this.status = States.INTRO;
                    SaveFile.isFirstRun = false;
                } else {
                    this.status = States.MAP;
                }
            }
        }
        if (!SaveFile.musicOn) {
            this.assets.menuMusic.setVolume(0.0f);
        }
        if (SaveFile.musicOn && !this.isFading) {
            this.assets.menuMusic.setVolume(1.0f);
        }
    }

    @Override // kgm.NA.State
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.isOptions) {
            canvas.drawBitmap(this.assets.optionsBackground.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
            if (SaveFile.soundOn) {
                canvas.drawBitmap(this.assets.optionSelected.bitmap, Screen.absScreenWidth * 0.492f, Screen.absScreenHeight * 0.162f, (Paint) null);
            }
            if (SaveFile.musicOn) {
                canvas.drawBitmap(this.assets.optionSelected.bitmap, Screen.absScreenWidth * 0.492f, Screen.absScreenHeight * 0.31f, (Paint) null);
                return;
            }
            return;
        }
        canvas.drawBitmap(this.assets.menuBackground.bitmap, Screen.relXZero, Screen.relYZero, this.fadePaint);
    }

    @Override // kgm.NA.State
    public void load(AssetManager am, Assets assets) {
        assets.menuBackground.load(am, "menu-background.png", 400, 300);
        assets.optionsBackground.load(am, "options-background.png", 400, 300);
        assets.optionSelected.load(am, "option-on.png", 19, 19);
        assets.menuMusic.load(am, "menu-music.ogg");
        assets.touchDown.load(assets.sounds, am, "touch-down.ogg");
        assets.touchUp.load(assets.sounds, am, "touch-up.ogg");
    }

    @Override // kgm.NA.State
    public void delete() {
        this.assets.menuBackground.delete();
        this.assets.optionsBackground.delete();
        this.assets.optionSelected.delete();
        this.assets.menuMusic.delete();
        this.assets.touchDown.delete(this.assets.sounds);
    }

    @Override // kgm.NA.State
    public void pause() {
        this.assets.menuMusic.pause();
    }

    @Override // kgm.NA.State
    public void resume() {
        this.assets.menuMusic.resume();
    }

    @Override // kgm.NA.State
    public States getStatus() {
        return this.status;
    }
}
