package kgm.NA;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;

/* loaded from: classes.dex */
public class ComputerState extends State {
    int currentEmail;
    Paint emailHeaderTextPaint;
    Paint emailTextPaint;
    int fadeAlpha;
    int inputNum1;
    int inputNum2;
    int inputNum3;
    int inputNum4;
    boolean isFading;
    States nextState;
    SubState substate;
    String temp;
    Paint textPaint;

    /* loaded from: classes.dex */
    enum SubState {
        LOGIN,
        EMAIL;

        /* renamed from: values  reason: to resolve conflict with enum method */
        public static SubState[] valuesCustom() {
            SubState[] valuesCustom = values();
            int length = valuesCustom.length;
            SubState[] subStateArr = new SubState[length];
            System.arraycopy(valuesCustom, 0, subStateArr, 0, length);
            return subStateArr;
        }
    }

    public ComputerState(AssetManager am, Assets assets) {
        this.isFading = true;
        this.status = States.RUNNING;
        this.assets = assets;
        load(am, assets);
        this.isFading = false;
        this.fadeAlpha = 0;
        this.substate = SubState.LOGIN;
        this.inputNum1 = -10;
        this.inputNum2 = -10;
        this.inputNum3 = -10;
        this.inputNum4 = -10;
        this.temp = "";
        this.textPaint = new Paint();
        this.textPaint.setTextSize(Screen.relScreenWidth * 0.06f);
        this.textPaint.setColor(-16777216);
        this.emailHeaderTextPaint = new Paint();
        this.emailHeaderTextPaint.setTextSize(Screen.relScreenWidth * 0.06f);
        this.emailHeaderTextPaint.setColor(-16777216);
        this.emailTextPaint = new Paint();
        this.emailTextPaint.setTextSize(Screen.relScreenWidth * 0.028f);
        this.emailTextPaint.setColor(-16777216);
        this.currentEmail = 1;
    }

    @Override // kgm.NA.State
    public void update(MotionEvent touch, KeyEvent key) {
        if (key != null) {
            key.getKeyCode();
        }
        if (touch != null) {
            if (this.substate == SubState.LOGIN) {
                if (touch.getY() > (Screen.relScreenHeight * 0.7066f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.8333f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.125f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.6187f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.keyDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        this.nextState = States.OFFICE;
                        this.isFading = true;
                        this.assets.keyUp.playSound(this.assets.sounds);
                    }
                }
                checkNumInput(touch);
                if (this.temp.length() == 4) {
                    if (this.temp.equals(String.valueOf(SaveFile.computerPassword))) {
                        this.substate = SubState.EMAIL;
                        this.assets.login.playSound(this.assets.sounds);
                    } else {
                        this.temp = "";
                    }
                }
            }
            if (this.substate == SubState.EMAIL) {
                if (touch.getY() > (Screen.relScreenHeight * 0.7333f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.8766f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.1f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.2718f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.clickDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        this.nextState = States.OFFICE;
                        this.isFading = true;
                        this.assets.clickUp.playSound(this.assets.sounds);
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.7333f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.8766f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.2718f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.485f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.clickDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        this.currentEmail--;
                        if (this.currentEmail <= 0) {
                            this.currentEmail = 5;
                        }
                        this.assets.clickUp.playSound(this.assets.sounds);
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.7333f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.8766f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.485f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.687f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.clickDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        this.currentEmail++;
                        if (this.currentEmail > 5) {
                            this.currentEmail = 1;
                        }
                        this.assets.clickUp.playSound(this.assets.sounds);
                    }
                }
                if (touch.getY() > (Screen.relScreenHeight * 0.7333f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.8766f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.687f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.8994f) + Screen.relXZero) {
                    if (touch.getAction() == 0) {
                        this.assets.clickDown.playSound(this.assets.sounds);
                    }
                    if (touch.getAction() == 1) {
                        if (this.currentEmail == 4) {
                            SaveFile.hasEmail = true;
                            this.assets.acquire.playSound(this.assets.sounds);
                        }
                        this.assets.print.playSound(this.assets.sounds);
                        this.assets.keyUp.playSound(this.assets.sounds);
                    }
                }
            }
            touch.setLocation(-10.0f, -10.0f);
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
        canvas.drawBitmap(this.assets.computerScreen.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        if (this.substate == SubState.LOGIN) {
            canvas.drawBitmap(this.assets.loginScreen.bitmap, (Screen.relScreenWidth * 0.0949f) + Screen.relXZero, (Screen.relScreenHeight * 0.1173f) + Screen.relYZero, (Paint) null);
            canvas.drawText(this.temp, (Screen.relScreenWidth * 0.13f) + Screen.relXZero, (Screen.relScreenHeight * 0.295f) + Screen.relYZero, this.textPaint);
        }
        if (this.substate == SubState.EMAIL) {
            drawEmail(canvas);
        }
        canvas.drawARGB(this.fadeAlpha, 0, 0, 0);
    }

    @Override // kgm.NA.State
    public void load(AssetManager am, Assets assets) {
        assets.computerScreen.load(am, "computer-screen.png", 400, 300);
        assets.loginScreen.load(am, "login-screen.png", 323, 228);
        assets.keyDown.load(assets.sounds, am, "key-down.ogg");
        assets.keyUp.load(assets.sounds, am, "key-up.ogg");
        assets.clickDown.load(assets.sounds, am, "click-down.ogg");
        assets.clickUp.load(assets.sounds, am, "click-up.ogg");
        assets.login.load(assets.sounds, am, "login.ogg");
        assets.print.load(assets.sounds, am, "print.ogg");
        assets.acquire.load(assets.sounds, am, "acquire.ogg");
    }

    @Override // kgm.NA.State
    public void delete() {
        this.assets.computerScreen.delete();
        this.assets.loginScreen.delete();
        this.assets.keyDown.delete(this.assets.sounds);
        this.assets.keyUp.delete(this.assets.sounds);
        this.assets.clickDown.delete(this.assets.sounds);
        this.assets.clickUp.delete(this.assets.sounds);
        this.assets.login.delete(this.assets.sounds);
        this.assets.print.delete(this.assets.sounds);
        this.assets.acquire.delete(this.assets.sounds);
    }

    @Override // kgm.NA.State
    public void pause() {
    }

    @Override // kgm.NA.State
    public void resume() {
    }

    public void drawEmail(Canvas canvas) {
        switch (this.currentEmail) {
            case 1:
                canvas.drawText("Re: Meeting with the board", (Screen.relScreenWidth * 0.11f) + Screen.relXZero, (Screen.relScreenHeight * 0.21f) + Screen.relYZero, this.emailHeaderTextPaint);
                canvas.drawText("   There is a meeting with the Board of Directors next", (Screen.relScreenWidth * 0.115f) + Screen.relXZero, (Screen.relScreenHeight * 0.266f) + Screen.relYZero, this.emailTextPaint);
                canvas.drawText("Wednesday to discuss the third quarter financial results. Also,", (Screen.relScreenWidth * 0.115f) + Screen.relXZero, (Screen.relScreenHeight * 0.306f) + Screen.relYZero, this.emailTextPaint);
                canvas.drawText("please bring the revised budgets for the fourth quarter.", (Screen.relScreenWidth * 0.115f) + Screen.relXZero, (Screen.relScreenHeight * 0.346f) + Screen.relYZero, this.emailTextPaint);
                canvas.drawText("", (Screen.relScreenWidth * 0.115f) + Screen.relXZero, (Screen.relScreenHeight * 0.386f) + Screen.relYZero, this.emailTextPaint);
                canvas.drawText("regards,", (Screen.relScreenWidth * 0.115f) + Screen.relXZero, (Screen.relScreenHeight * 0.426f) + Screen.relYZero, this.emailTextPaint);
                canvas.drawText("Rich E. Rockefeller", (Screen.relScreenWidth * 0.115f) + Screen.relXZero, (Screen.relScreenHeight * 0.506f) + Screen.relYZero, this.emailTextPaint);
                canvas.drawText("Chairman of the Board", (Screen.relScreenWidth * 0.115f) + Screen.relXZero, (Screen.relScreenHeight * 0.546f) + Screen.relYZero, this.emailTextPaint);
                return;
            case 2:
                canvas.drawText("Re: Lunch", (Screen.relScreenWidth * 0.11f) + Screen.relXZero, (Screen.relScreenHeight * 0.21f) + Screen.relYZero, this.emailHeaderTextPaint);
                canvas.drawText("   Hey, Sweetie. Just a reminder that we're meeting for lunch", (Screen.relScreenWidth * 0.115f) + Screen.relXZero, (Screen.relScreenHeight * 0.266f) + Screen.relYZero, this.emailTextPaint);
                canvas.drawText("on Thursday. I figure we'd try that new Italian place on", (Screen.relScreenWidth * 0.115f) + Screen.relXZero, (Screen.relScreenHeight * 0.306f) + Screen.relYZero, this.emailTextPaint);
                canvas.drawText("Beachview Drive.", (Screen.relScreenWidth * 0.115f) + Screen.relXZero, (Screen.relScreenHeight * 0.346f) + Screen.relYZero, this.emailTextPaint);
                canvas.drawText("", (Screen.relScreenWidth * 0.115f) + Screen.relXZero, (Screen.relScreenHeight * 0.386f) + Screen.relYZero, this.emailTextPaint);
                canvas.drawText("DON'T FORGET!", (Screen.relScreenWidth * 0.115f) + Screen.relXZero, (Screen.relScreenHeight * 0.426f) + Screen.relYZero, this.emailTextPaint);
                canvas.drawText("xoxo", (Screen.relScreenWidth * 0.115f) + Screen.relXZero, (Screen.relScreenHeight * 0.466f) + Screen.relYZero, this.emailTextPaint);
                return;
            case 3:
                canvas.drawText("Re: Q3 Financials", (Screen.relScreenWidth * 0.11f) + Screen.relXZero, (Screen.relScreenHeight * 0.21f) + Screen.relYZero, this.emailHeaderTextPaint);
                canvas.drawText("Hey, Ed. The third quarter results are in:", (Screen.relScreenWidth * 0.115f) + Screen.relXZero, (Screen.relScreenHeight * 0.266f) + Screen.relYZero, this.emailTextPaint);
                canvas.drawText("", (Screen.relScreenWidth * 0.115f) + Screen.relXZero, (Screen.relScreenHeight * 0.306f) + Screen.relYZero, this.emailTextPaint);
                canvas.drawText("Revenue:      75.4M", (Screen.relScreenWidth * 0.115f) + Screen.relXZero, (Screen.relScreenHeight * 0.346f) + Screen.relYZero, this.emailTextPaint);
                canvas.drawText("Op. Income: 34.9M", (Screen.relScreenWidth * 0.115f) + Screen.relXZero, (Screen.relScreenHeight * 0.386f) + Screen.relYZero, this.emailTextPaint);
                canvas.drawText("Pre-tax Inc.: 30.8M", (Screen.relScreenWidth * 0.115f) + Screen.relXZero, (Screen.relScreenHeight * 0.426f) + Screen.relYZero, this.emailTextPaint);
                canvas.drawText("Net Income: 27.6M", (Screen.relScreenWidth * 0.115f) + Screen.relXZero, (Screen.relScreenHeight * 0.466f) + Screen.relYZero, this.emailTextPaint);
                canvas.drawText("Diluted EPS:     0.27", (Screen.relScreenWidth * 0.115f) + Screen.relXZero, (Screen.relScreenHeight * 0.506f) + Screen.relYZero, this.emailTextPaint);
                canvas.drawText("", (Screen.relScreenWidth * 0.115f) + Screen.relXZero, (Screen.relScreenHeight * 0.546f) + Screen.relYZero, this.emailTextPaint);
                canvas.drawText("Bob Dupont", (Screen.relScreenWidth * 0.115f) + Screen.relXZero, (Screen.relScreenHeight * 0.586f) + Screen.relYZero, this.emailTextPaint);
                canvas.drawText("CFO", (Screen.relScreenWidth * 0.115f) + Screen.relXZero, (Screen.relScreenHeight * 0.626f) + Screen.relYZero, this.emailTextPaint);
                return;
            case 4:
                canvas.drawText("Re: N/A", (Screen.relScreenWidth * 0.11f) + Screen.relXZero, (Screen.relScreenHeight * 0.21f) + Screen.relYZero, this.emailHeaderTextPaint);
                canvas.drawText("   A little birdie told me that you have information regarding", (Screen.relScreenWidth * 0.115f) + Screen.relXZero, (Screen.relScreenHeight * 0.266f) + Screen.relYZero, this.emailTextPaint);
                canvas.drawText("the existence of the creature. It would be bad for the city of", (Screen.relScreenWidth * 0.115f) + Screen.relXZero, (Screen.relScreenHeight * 0.306f) + Screen.relYZero, this.emailTextPaint);
                canvas.drawText("Milltown and more importantly myself if word of this creature", (Screen.relScreenWidth * 0.115f) + Screen.relXZero, (Screen.relScreenHeight * 0.346f) + Screen.relYZero, this.emailTextPaint);
                canvas.drawText("were to be released to the the public.", (Screen.relScreenWidth * 0.115f) + Screen.relXZero, (Screen.relScreenHeight * 0.386f) + Screen.relYZero, this.emailTextPaint);
                canvas.drawText("   I have very powerful - very dangerous - friends that have a", (Screen.relScreenWidth * 0.115f) + Screen.relXZero, (Screen.relScreenHeight * 0.426f) + Screen.relYZero, this.emailTextPaint);
                canvas.drawText("vested interest in keeping me in power, so I suggest that you", (Screen.relScreenWidth * 0.115f) + Screen.relXZero, (Screen.relScreenHeight * 0.466f) + Screen.relYZero, this.emailTextPaint);
                canvas.drawText("keep anything that you know to yourself, just in case anything", (Screen.relScreenWidth * 0.115f) + Screen.relXZero, (Screen.relScreenHeight * 0.506f) + Screen.relYZero, this.emailTextPaint);
                canvas.drawText("bad should happen to you.", (Screen.relScreenWidth * 0.115f) + Screen.relXZero, (Screen.relScreenHeight * 0.546f) + Screen.relYZero, this.emailTextPaint);
                canvas.drawText("", (Screen.relScreenWidth * 0.115f) + Screen.relXZero, (Screen.relScreenHeight * 0.586f) + Screen.relYZero, this.emailTextPaint);
                canvas.drawText("chief", (Screen.relScreenWidth * 0.115f) + Screen.relXZero, (Screen.relScreenHeight * 0.626f) + Screen.relYZero, this.emailTextPaint);
                return;
            case 5:
                canvas.drawText("Re: Acquisition", (Screen.relScreenWidth * 0.11f) + Screen.relXZero, (Screen.relScreenHeight * 0.21f) + Screen.relYZero, this.emailHeaderTextPaint);
                canvas.drawText("   Hey, Ed. We've been stockpiling a lot of free cash and now", (Screen.relScreenWidth * 0.115f) + Screen.relXZero, (Screen.relScreenHeight * 0.266f) + Screen.relYZero, this.emailTextPaint);
                canvas.drawText("might be a good time to blow it on a foolish acquisition or", (Screen.relScreenWidth * 0.115f) + Screen.relXZero, (Screen.relScreenHeight * 0.306f) + Screen.relYZero, this.emailTextPaint);
                canvas.drawText("some joint venture with a neighboring town's media", (Screen.relScreenWidth * 0.115f) + Screen.relXZero, (Screen.relScreenHeight * 0.346f) + Screen.relYZero, this.emailTextPaint);
                canvas.drawText("company.", (Screen.relScreenWidth * 0.115f) + Screen.relXZero, (Screen.relScreenHeight * 0.386f) + Screen.relYZero, this.emailTextPaint);
                canvas.drawText("", (Screen.relScreenWidth * 0.115f) + Screen.relXZero, (Screen.relScreenHeight * 0.426f) + Screen.relYZero, this.emailTextPaint);
                canvas.drawText("let me know,", (Screen.relScreenWidth * 0.115f) + Screen.relXZero, (Screen.relScreenHeight * 0.466f) + Screen.relYZero, this.emailTextPaint);
                canvas.drawText("", (Screen.relScreenWidth * 0.115f) + Screen.relXZero, (Screen.relScreenHeight * 0.506f) + Screen.relYZero, this.emailTextPaint);
                canvas.drawText("Bod Dupont", (Screen.relScreenWidth * 0.115f) + Screen.relXZero, (Screen.relScreenHeight * 0.546f) + Screen.relYZero, this.emailTextPaint);
                canvas.drawText("CFO", (Screen.relScreenWidth * 0.115f) + Screen.relXZero, (Screen.relScreenHeight * 0.586f) + Screen.relYZero, this.emailTextPaint);
                return;
            default:
                return;
        }
    }

    public void checkNumInput(MotionEvent touch) {
        if (touch.getY() > (Screen.relScreenHeight * 0.7066f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.8333f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.6187f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.8625f) + Screen.relXZero) {
            if (touch.getAction() == 0) {
                this.assets.keyDown.playSound(this.assets.sounds);
            }
            if (touch.getAction() == 1) {
                this.temp = String.valueOf(this.temp) + "0";
                this.assets.keyUp.playSound(this.assets.sounds);
            }
        }
        if (touch.getY() > (Screen.relScreenHeight * 0.3333f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.4633f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.125f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.3725f) + Screen.relXZero) {
            if (touch.getAction() == 0) {
                this.assets.keyDown.playSound(this.assets.sounds);
            }
            if (touch.getAction() == 1) {
                this.temp = String.valueOf(this.temp) + "1";
                this.assets.keyUp.playSound(this.assets.sounds);
            }
        }
        if (touch.getY() > (Screen.relScreenHeight * 0.3333f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.4633f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.3725f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.6187f) + Screen.relXZero) {
            if (touch.getAction() == 0) {
                this.assets.keyDown.playSound(this.assets.sounds);
            }
            if (touch.getAction() == 1) {
                this.temp = String.valueOf(this.temp) + "2";
                this.assets.keyUp.playSound(this.assets.sounds);
            }
        }
        if (touch.getY() > (Screen.relScreenHeight * 0.3333f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.4633f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.6187f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.8625f) + Screen.relXZero) {
            if (touch.getAction() == 0) {
                this.assets.keyDown.playSound(this.assets.sounds);
            }
            if (touch.getAction() == 1) {
                this.temp = String.valueOf(this.temp) + "3";
                this.assets.keyUp.playSound(this.assets.sounds);
            }
        }
        if (touch.getY() > (Screen.relScreenHeight * 0.4633f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.5866f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.125f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.3725f) + Screen.relXZero) {
            if (touch.getAction() == 0) {
                this.assets.keyDown.playSound(this.assets.sounds);
            }
            if (touch.getAction() == 1) {
                this.temp = String.valueOf(this.temp) + "4";
                this.assets.keyUp.playSound(this.assets.sounds);
            }
        }
        if (touch.getY() > (Screen.relScreenHeight * 0.4633f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.5866f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.3725f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.6187f) + Screen.relXZero) {
            if (touch.getAction() == 0) {
                this.assets.keyDown.playSound(this.assets.sounds);
            }
            if (touch.getAction() == 1) {
                this.temp = String.valueOf(this.temp) + "5";
                this.assets.keyUp.playSound(this.assets.sounds);
            }
        }
        if (touch.getY() > (Screen.relScreenHeight * 0.4633f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.5866f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.6187f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.8625f) + Screen.relXZero) {
            if (touch.getAction() == 0) {
                this.assets.keyDown.playSound(this.assets.sounds);
            }
            if (touch.getAction() == 1) {
                this.temp = String.valueOf(this.temp) + "6";
                this.assets.keyUp.playSound(this.assets.sounds);
            }
        }
        if (touch.getY() > (Screen.relScreenHeight * 0.5866f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.7066f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.125f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.3725f) + Screen.relXZero) {
            if (touch.getAction() == 0) {
                this.assets.keyDown.playSound(this.assets.sounds);
            }
            if (touch.getAction() == 1) {
                this.temp = String.valueOf(this.temp) + "7";
                this.assets.keyUp.playSound(this.assets.sounds);
            }
        }
        if (touch.getY() > (Screen.relScreenHeight * 0.5866f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.7066f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.3725f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.6187f) + Screen.relXZero) {
            if (touch.getAction() == 0) {
                this.assets.keyDown.playSound(this.assets.sounds);
            }
            if (touch.getAction() == 1) {
                this.temp = String.valueOf(this.temp) + "8";
                this.assets.keyUp.playSound(this.assets.sounds);
            }
        }
        if (touch.getY() > (Screen.relScreenHeight * 0.5866f) + Screen.relYZero && touch.getY() < (Screen.relScreenHeight * 0.7066f) + Screen.relYZero && touch.getX() > (Screen.relScreenWidth * 0.6187f) + Screen.relXZero && touch.getX() < (Screen.relScreenWidth * 0.8625f) + Screen.relXZero) {
            if (touch.getAction() == 0) {
                this.assets.keyDown.playSound(this.assets.sounds);
            }
            if (touch.getAction() == 1) {
                this.temp = String.valueOf(this.temp) + "9";
                this.assets.keyUp.playSound(this.assets.sounds);
            }
        }
    }
}
