package kgm.NA;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;
import java.util.Random;

/* loaded from: classes.dex */
public class LobbyState extends State {
    int fadeAlpha;
    long guardHeadTimer;
    long guardHeadUpdateTime;
    boolean isFading;
    States nextState;
    TextBox textBox;
    Random rand = new Random();
    String[] lines1 = {"I'm in charge around here."};
    String[] lines2 = {"\"Leave me alone; I'm working, and it's", "almost my lunch time.\""};
    String[] lines3 = {"\"Take this and leave me alone.\""};
    String[] noAccessLines = {"You need a keycard to access the elevator."};
    int guardHeadDir = this.rand.nextInt(3) + 1;

    public LobbyState(AssetManager am, Assets assets) {
        this.status = States.RUNNING;
        this.assets = assets;
        load(am, assets);
        this.isFading = false;
        this.fadeAlpha = 0;
        this.guardHeadUpdateTime = System.currentTimeMillis();
        this.guardHeadTimer = this.rand.nextInt(3000) + 500;
    }

    @Override // kgm.NA.State
    public void update(MotionEvent touch, KeyEvent key) {
        if (key != null) {
            key.getKeyCode();
        }
        if (touch != null && !this.isFading) {
            if (this.textBox == null) {
                if (touch.getY() > (Screen.relScreenHeight * 0.233f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.616f) + Screen.relYZero && touch.getX() > Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.205f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        this.nextState = States.DOWNTOWN;
                        this.isFading = true;
                        this.assets.touchUp.playSound(this.assets.sounds);
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.183f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.616f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.69f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.987f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        if (SaveFile.hasElevatorKey) {
                            this.nextState = States.HALLWAY;
                            this.isFading = true;
                            this.assets.touchUp.playSound(this.assets.sounds);
                        } else {
                            this.textBox = new TextBox("rejected", this.noAccessLines, 1);
                            this.textBox.start();
                            this.assets.textBox.playSound(this.assets.sounds);
                        }
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.4166f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.6333f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.3875f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.525f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.touchDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        if (SaveFile.hasElevatorKey) {
                            this.textBox = new TextBox("guard", this.lines1, 1);
                            SaveFile.hasLunchbox = false;
                        }
                        if (SaveFile.hasLunchbox && !SaveFile.hasElevatorKey) {
                            SaveFile.hasLunchbox = false;
                            SaveFile.hasElevatorKey = true;
                            this.assets.acquire.playSound(this.assets.sounds);
                            this.textBox = new TextBox("guard", this.lines3, 1);
                        }
                        if (!SaveFile.hasElevatorKey && !SaveFile.hasLunchbox) {
                            this.textBox = new TextBox("guard", this.lines2, 2);
                        }
                        this.guardHeadDir = 2;
                        this.textBox.start();
                        this.assets.textBox.playSound(this.assets.sounds);
                    }
                }
            }
            if (touch.getX() > Screen.relXZero && touch.getAction() == 1 && this.textBox != null && !this.textBox.isFinished && this.textBox.isPaused) {
                this.textBox.resume();
                this.assets.touchUp.playSound(this.assets.sounds);
            }
            touch.setLocation(-10.0f, -10.0f);
        }
        if (System.currentTimeMillis() - this.guardHeadUpdateTime > this.guardHeadTimer) {
            this.guardHeadDir = this.rand.nextInt(3) + 1;
            this.guardHeadUpdateTime = System.currentTimeMillis();
            this.guardHeadTimer = this.rand.nextInt(3000) + 500;
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
        canvas.drawBitmap(this.assets.lobby.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        switch (this.guardHeadDir) {
            case 1:
                canvas.drawBitmap(this.assets.lobbyGuardHeadLeft.bitmap, (Screen.relScreenWidth * 0.433f) + Screen.relXZero, (Screen.relScreenHeight * 0.4323f) + Screen.relYZero, (Paint) null);
                break;
            case 2:
                canvas.drawBitmap(this.assets.lobbyGuardHeadCenter.bitmap, (Screen.relScreenWidth * 0.4357f) + Screen.relXZero, (Screen.relScreenHeight * 0.4332f) + Screen.relYZero, (Paint) null);
                break;
            case 3:
                canvas.drawBitmap(this.assets.lobbyGuardHeadRight.bitmap, (Screen.relScreenWidth * 0.4354f) + Screen.relXZero, (Screen.relScreenHeight * 0.4323f) + Screen.relYZero, (Paint) null);
                break;
        }
        if (this.textBox != null) {
            this.textBox.draw(canvas);
        }
        canvas.drawARGB(this.fadeAlpha, 0, 0, 0);
    }

    @Override // kgm.NA.State
    public void load(AssetManager am, Assets assets) {
        assets.lobby.load(am, "lobby.png", 400, 300);
        assets.lobbyGuardHeadCenter.load(am, "lobby-guard-head-center.png", 17, 23);
        assets.lobbyGuardHeadLeft.load(am, "lobby-guard-head-left.png", 17, 22);
        assets.lobbyGuardHeadRight.load(am, "lobby-guard-head-right.png", 17, 22);
        assets.touchDown.load(assets.sounds, am, "touch-down.ogg");
        assets.touchUp.load(assets.sounds, am, "touch-up.ogg");
        assets.textBox.load(assets.sounds, am, "textbox.ogg");
        assets.acquire.load(assets.sounds, am, "acquire.ogg");
    }

    @Override // kgm.NA.State
    public void delete() {
        this.assets.lobby.delete();
        this.assets.lobbyGuardHeadCenter.delete();
        this.assets.lobbyGuardHeadLeft.delete();
        this.assets.lobbyGuardHeadRight.delete();
        this.assets.touchDown.delete(this.assets.sounds);
        this.assets.touchUp.delete(this.assets.sounds);
        this.assets.textBox.delete(this.assets.sounds);
        this.assets.acquire.delete(this.assets.sounds);
    }

    @Override // kgm.NA.State
    public void pause() {
    }

    @Override // kgm.NA.State
    public void resume() {
    }
}
