package kgm.NA;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;
import java.util.Random;

/* loaded from: classes.dex */
public class FightState extends State {
    private static /* synthetic */ int[] $SWITCH_TABLE$kgm$NA$FightState$SubState;
    boolean backgroundLeft;
    float backgroundX;
    long blockTimer;
    boolean blocked;
    int cpuDamage;
    int damageAlpha;
    boolean dodgeFirst;
    boolean dodgeStart;
    Sprite face;
    int fadeAlpha;
    SubState fightState;
    float fistStartScale;
    long hackTimer;
    boolean headDown;
    float headX;
    float headY;
    float headYBottom;
    float headYTop;
    long idleStart;
    boolean isFading;
    DSprite leftFist;
    States nextState;
    float offset;
    boolean punchDodged;
    boolean punchGen;
    boolean punchIsLeft;
    long punchTimer;
    boolean punchTimerSet;
    int quitFadeAlpha;
    Random rand;
    DSprite rightFist;
    TextBox textBox;
    boolean textBoxStarted;
    long timer;
    boolean userPunchLanded;
    long userPunchTimer;
    long winTimer;
    String[] introLines = {"Tap the left and right sides of the screen to", "dodge punches. Tap the guy's face after", "dodging to throw a punch."};
    String[] winLines = {"You win! The guy dropped $"};

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public enum SubState {
        IDLE,
        PUNCH_THROWN,
        PUNCH_LANDED,
        PUNCH_MISSED,
        INTRO,
        WIN;

        /* renamed from: values  reason: to resolve conflict with enum method */
        public static SubState[] valuesCustom() {
            SubState[] valuesCustom = values();
            int length = valuesCustom.length;
            SubState[] subStateArr = new SubState[length];
            System.arraycopy(valuesCustom, 0, subStateArr, 0, length);
            return subStateArr;
        }
    }

    static /* synthetic */ int[] $SWITCH_TABLE$kgm$NA$FightState$SubState() {
        int[] iArr = $SWITCH_TABLE$kgm$NA$FightState$SubState;
        if (iArr == null) {
            iArr = new int[SubState.valuesCustom().length];
            try {
                iArr[SubState.IDLE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[SubState.INTRO.ordinal()] = 5;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[SubState.PUNCH_LANDED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[SubState.PUNCH_MISSED.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                iArr[SubState.PUNCH_THROWN.ordinal()] = 2;
            } catch (NoSuchFieldError e5) {
            }
            try {
                iArr[SubState.WIN.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            $SWITCH_TABLE$kgm$NA$FightState$SubState = iArr;
        }
        return iArr;
    }

    public FightState(AssetManager am, Assets assets) {
        this.status = States.RUNNING;
        this.assets = assets;
        load(am, assets);
        if (!SaveFile.wonBarFight) {
            this.fightState = SubState.INTRO;
        } else {
            this.fightState = SubState.IDLE;
        }
        this.winTimer = 0L;
        this.cpuDamage = 0;
        this.backgroundX = Screen.relXZero;
        this.offset = 0.0f;
        this.fadeAlpha = MotionEventCompat.ACTION_MASK;
        this.quitFadeAlpha = 0;
        this.userPunchTimer = 0L;
        this.punchTimer = 0L;
        this.dodgeFirst = true;
        this.dodgeStart = false;
        this.userPunchLanded = false;
        this.punchTimerSet = false;
        this.punchDodged = false;
        this.punchIsLeft = true;
        this.punchGen = false;
        this.headDown = true;
        this.rand = new Random();
        this.timer = this.rand.nextInt(3000);
        this.backgroundLeft = this.rand.nextBoolean();
        this.headX = (Screen.relScreenWidth * 0.342f) + Screen.relXZero;
        this.headYTop = ((Screen.relScreenHeight * 0.59f) - assets.fightFace1.getHeight()) + Screen.relYZero;
        this.headY = this.headYTop;
        this.headYBottom = ((Screen.relScreenHeight * 0.641f) - assets.fightFace1.getHeight()) + Screen.relYZero;
        this.fistStartScale = 0.25f;
        this.leftFist = new DSprite(assets.fightFistLeft, 0.25f, 0.55f);
        this.leftFist.scale(this.fistStartScale, this.fistStartScale);
        this.leftFist.setAbsPosition(this.leftFist.x, this.leftFist.y);
        this.rightFist = new DSprite(assets.fightFistRight, 0.5f, 0.55f);
        this.rightFist.setAbsPosition(this.rightFist.x, this.rightFist.y);
        this.rightFist.scale(this.fistStartScale, this.fistStartScale);
        this.blockTimer = 0L;
        this.blocked = false;
    }

    @Override // kgm.NA.State
    public void update(MotionEvent touch, KeyEvent key) {
        String[] strArr;
        String[] strArr2;
        switch ($SWITCH_TABLE$kgm$NA$FightState$SubState()[this.fightState.ordinal()]) {
            case 1:
                if (this.damageAlpha >= 255) {
                    this.status = States.DOWNTOWN;
                }
                if (touch != null && touch.getAction() == 0 && !this.blocked) {
                    this.blocked = true;
                    this.blockTimer = System.currentTimeMillis();
                    this.timer += 1000;
                }
                if (this.idleStart == 0) {
                    this.idleStart = System.currentTimeMillis();
                    this.timer = this.rand.nextInt(3000);
                } else if (System.currentTimeMillis() - this.idleStart > this.timer) {
                    this.fightState = SubState.PUNCH_THROWN;
                    this.assets.swing.playSound(this.assets.sounds);
                    this.idleStart = 0L;
                }
                if (!this.blocked) {
                    if (this.headDown) {
                        this.headY += Screen.relScreenHeight * 0.0025f;
                        if (this.headY > this.headYBottom) {
                            this.headDown = false;
                            this.headY = this.headYBottom;
                        }
                    } else {
                        this.headY -= Screen.relScreenHeight * 0.0025f;
                        if (this.headY < this.headYTop) {
                            this.headDown = true;
                            this.headY = this.headYTop;
                        }
                    }
                    if (this.backgroundLeft) {
                        this.backgroundX -= Screen.relScreenWidth * 0.003f;
                        if (this.backgroundX <= (this.assets.fightBackground.getWidth() * 0.75f) - this.assets.fightBackground.getWidth()) {
                            this.backgroundX = (this.assets.fightBackground.getWidth() * 0.75f) - this.assets.fightBackground.getWidth();
                            this.backgroundLeft = false;
                            return;
                        }
                        return;
                    }
                    this.backgroundX += Screen.relScreenWidth * 0.003f;
                    if (this.backgroundX >= Screen.relXZero) {
                        this.backgroundX = Screen.relXZero;
                        this.backgroundLeft = true;
                        return;
                    }
                    return;
                } else if (System.currentTimeMillis() - this.blockTimer > 1000) {
                    this.blocked = false;
                    return;
                } else {
                    this.headY = this.headYBottom + (Screen.relScreenHeight * 0.04f);
                    return;
                }
            case 2:
                if (!this.punchGen) {
                    this.punchDodged = false;
                    this.punchGen = true;
                    this.punchIsLeft = this.rand.nextBoolean();
                    this.headY = this.headYBottom;
                    this.leftFist.scale(0.25f, 0.25f);
                    this.rightFist.scale(0.25f, 0.25f);
                }
                if (this.punchIsLeft) {
                    this.leftFist.scale(this.leftFist.scaleX + 0.04f, this.leftFist.scaleY + 0.04f);
                    this.leftFist.x -= ((this.leftFist.image.getWidth() * this.leftFist.scaleX) - (this.leftFist.image.getWidth() * (this.leftFist.scaleX - 0.04f))) / 2.0f;
                    this.leftFist.y -= ((this.leftFist.image.getHeight() * this.leftFist.scaleY) - (this.leftFist.image.getHeight() * (this.leftFist.scaleY - 0.04f))) / 2.0f;
                } else {
                    this.rightFist.scale(this.rightFist.scaleX + 0.04f, this.rightFist.scaleY + 0.04f);
                    this.rightFist.x -= ((this.rightFist.image.getWidth() * this.rightFist.scaleX) - (this.rightFist.image.getWidth() * (this.rightFist.scaleX - 0.04f))) / 2.0f;
                    this.rightFist.y -= ((this.rightFist.image.getHeight() * this.rightFist.scaleY) - (this.rightFist.image.getHeight() * (this.rightFist.scaleX - 0.04f))) / 2.0f;
                }
                if (!this.punchDodged && touch != null && touch.getAction() == 0) {
                    if (this.punchIsLeft) {
                        if (touch.getX() > (Screen.relScreenWidth * 0.7f) + Screen.relXZero && touch.getX() < Screen.relScreenWidth + Screen.relXZero) {
                            this.punchDodged = true;
                        }
                    } else if (touch.getX() < (Screen.relScreenWidth * 0.3f) + Screen.relXZero && touch.getX() > Screen.relXZero) {
                        this.punchDodged = true;
                    }
                    touch.setLocation(-10.0f, -10.0f);
                }
                if (this.punchIsLeft) {
                    if (this.leftFist.scaleX >= 1.0f) {
                        if (this.punchDodged) {
                            this.fightState = SubState.PUNCH_MISSED;
                            this.assets.dodge.playSound(this.assets.sounds);
                            return;
                        }
                        this.punchGen = false;
                        this.punchDodged = false;
                        this.leftFist.scale(0.25f, 0.25f);
                        this.leftFist.x = (Screen.relScreenWidth * 0.25f) + Screen.relXZero;
                        this.leftFist.y = (Screen.relScreenHeight * 0.55f) + Screen.relYZero;
                        this.damageAlpha += 40;
                        if (this.damageAlpha >= 240) {
                            this.damageAlpha = MotionEventCompat.ACTION_MASK;
                        }
                        this.assets.cpuPunch.playSound(this.assets.sounds);
                        this.fightState = SubState.PUNCH_LANDED;
                        return;
                    }
                    return;
                } else if (this.rightFist.scaleX >= 1.0f) {
                    if (this.punchDodged) {
                        this.fightState = SubState.PUNCH_MISSED;
                        this.assets.dodge.playSound(this.assets.sounds);
                        return;
                    }
                    this.punchGen = false;
                    this.punchDodged = false;
                    this.rightFist.scale(0.25f, 0.25f);
                    this.rightFist.x = (Screen.relScreenWidth * 0.5f) + Screen.relXZero;
                    this.rightFist.y = (Screen.relScreenHeight * 0.55f) + Screen.relYZero;
                    this.damageAlpha += 40;
                    if (this.damageAlpha >= 240) {
                        this.damageAlpha = MotionEventCompat.ACTION_MASK;
                    }
                    this.assets.cpuPunch.playSound(this.assets.sounds);
                    this.fightState = SubState.PUNCH_LANDED;
                    return;
                } else {
                    return;
                }
            case 3:
                if (!this.punchTimerSet) {
                    this.punchTimer = System.currentTimeMillis();
                    this.punchTimerSet = true;
                }
                if (System.currentTimeMillis() - this.punchTimer < 1250) {
                    this.fadeAlpha -= 6;
                    if (this.fadeAlpha <= 0) {
                        this.fadeAlpha = MotionEventCompat.ACTION_MASK;
                        this.punchTimerSet = false;
                        this.fightState = SubState.IDLE;
                        return;
                    }
                    return;
                }
                this.punchTimerSet = false;
                this.fadeAlpha = MotionEventCompat.ACTION_MASK;
                this.fightState = SubState.IDLE;
                return;
            case 4:
                if (touch != null && !this.userPunchLanded) {
                    if ((touch.getAction() == 2 || touch.getAction() == 0) && touch.getX() > this.headX && touch.getX() < this.headX + this.assets.fightFace1.getWidth() && touch.getY() > this.headY && touch.getY() < this.headY + this.assets.fightFace1.getHeight()) {
                        this.userPunchLanded = true;
                        if (SaveFile.hasBrassKnuckles) {
                            this.cpuDamage += 3;
                            this.assets.userPunch.playSound(this.assets.sounds);
                        } else {
                            this.cpuDamage++;
                            this.assets.cpuPunch.playSound(this.assets.sounds);
                        }
                        if (this.cpuDamage >= 18) {
                            this.fightState = SubState.WIN;
                        }
                    }
                }
                if (!this.userPunchLanded) {
                    if (this.punchIsLeft) {
                        this.leftFist.scale(this.leftFist.scaleX - 0.04f, this.leftFist.scaleY - 0.04f);
                        this.leftFist.x -= ((this.leftFist.image.getWidth() * this.leftFist.scaleX) - (this.leftFist.image.getWidth() * (this.leftFist.scaleX + 0.04f))) / 2.0f;
                        this.leftFist.y -= ((this.leftFist.image.getHeight() * this.leftFist.scaleY) - (this.leftFist.image.getHeight() * (this.leftFist.scaleY + 0.04f))) / 2.0f;
                        if (this.leftFist.scaleX < 0.25f) {
                            this.fightState = SubState.IDLE;
                            this.dodgeStart = false;
                            this.punchGen = false;
                            this.punchDodged = false;
                            this.leftFist.scaleX = 0.25f;
                            this.leftFist.scaleY = 0.25f;
                            this.leftFist.x = (Screen.relScreenWidth * 0.25f) + Screen.relXZero;
                            this.leftFist.y = (Screen.relScreenHeight * 0.55f) + Screen.relYZero;
                            return;
                        }
                        return;
                    }
                    this.rightFist.scale(this.rightFist.scaleX - 0.04f, this.rightFist.scaleY - 0.04f);
                    this.rightFist.x -= ((this.rightFist.image.getWidth() * this.rightFist.scaleX) - (this.rightFist.image.getWidth() * (this.rightFist.scaleY + 0.04f))) / 2.0f;
                    this.rightFist.y -= ((this.rightFist.image.getHeight() * this.rightFist.scaleY) - (this.rightFist.image.getHeight() * (this.rightFist.scaleY + 0.04f))) / 2.0f;
                    if (this.rightFist.scaleX < 0.25f) {
                        this.fightState = SubState.IDLE;
                        this.dodgeStart = false;
                        this.punchGen = false;
                        this.punchDodged = false;
                        this.rightFist.scaleX = 0.25f;
                        this.rightFist.scaleY = 0.25f;
                        this.rightFist.x = (Screen.relScreenWidth * 0.5f) + Screen.relXZero;
                        this.rightFist.y = (Screen.relScreenHeight * 0.55f) + Screen.relYZero;
                        return;
                    }
                    return;
                }
                if (this.userPunchTimer == 0) {
                    this.userPunchTimer = System.currentTimeMillis();
                }
                if (System.currentTimeMillis() - this.userPunchTimer >= 1000) {
                    this.fightState = SubState.IDLE;
                    this.userPunchTimer = 0L;
                    this.punchDodged = false;
                    this.punchGen = false;
                    this.userPunchLanded = false;
                    this.leftFist.setPosition(0.25f, 0.55f);
                    this.rightFist.setPosition(0.5f, 0.55f);
                    return;
                }
                return;
            case 5:
                if (this.textBox == null) {
                    this.textBox = new TextBox("intro", this.introLines, 3);
                    this.textBox.start();
                }
                if (touch != null && touch.getX() > Screen.relXZero && touch.getAction() == 1 && this.textBox != null) {
                    if (!this.textBox.isFinished) {
                        if (this.textBox.isPaused) {
                            this.textBox.resume();
                            return;
                        }
                        return;
                    }
                    this.fightState = SubState.IDLE;
                    this.textBox = null;
                    return;
                }
                return;
            case 6:
                if (this.textBox == null && !this.textBoxStarted) {
                    SaveFile.wonBarFight = true;
                    SaveFile.hasBrassKnuckles = true;
                    int prize = this.rand.nextInt(100) + 1;
                    SaveFile.cash += prize;
                    this.winLines[0] = String.valueOf(strArr[0]) + Integer.toString(prize);
                    this.winLines[0] = String.valueOf(strArr2[0]) + ".";
                    this.textBox = new TextBox("win", this.winLines, 1);
                    this.textBox.start();
                    this.textBoxStarted = true;
                    this.winTimer = System.currentTimeMillis();
                    if (touch != null) {
                        touch.setLocation(-10.0f, -10.0f);
                    }
                }
                if (this.textBoxStarted && touch != null && this.textBox != null) {
                    if (this.textBox.isFinished && System.currentTimeMillis() - this.winTimer > 1) {
                        this.textBox = null;
                        this.isFading = true;
                        this.nextState = States.DOWNTOWN;
                    } else if (this.textBox.isPaused) {
                        this.textBox.resume();
                    }
                }
                if (this.isFading) {
                    this.quitFadeAlpha += 16;
                    if (this.quitFadeAlpha >= 255) {
                        this.quitFadeAlpha = MotionEventCompat.ACTION_MASK;
                        this.status = this.nextState;
                        return;
                    }
                    return;
                }
                return;
            default:
                return;
        }
    }

    @Override // kgm.NA.State
    public void draw(Canvas canvas) {
        if (!this.textBoxStarted) {
            if (this.punchDodged) {
                if (!this.punchIsLeft && !this.userPunchLanded) {
                    canvas.drawBitmap(this.assets.fightBackground.bitmap, this.backgroundX - (Screen.relScreenWidth * 0.1f), Screen.relYZero, (Paint) null);
                    canvas.drawBitmap(this.assets.fightTorso.bitmap, ((Screen.relScreenWidth * 0.211f) + Screen.relXZero) - (Screen.relScreenWidth * 0.1f), ((Screen.relScreenHeight * 1.034f) - this.assets.fightTorso.getHeight()) + Screen.relYZero, (Paint) null);
                    canvas.drawBitmap(this.assets.fightFace1.bitmap, this.headX - (Screen.relScreenWidth * 0.1f), this.headY, (Paint) null);
                    canvas.drawBitmap(this.assets.fightArmLeft.bitmap, ((Screen.relScreenWidth * 0.21f) + Screen.relXZero) - (Screen.relScreenWidth * 0.1f), ((Screen.relScreenHeight * 0.925f) - this.assets.fightArmLeft.getHeight()) + Screen.relYZero, (Paint) null);
                    this.rightFist.draw(canvas);
                }
                if (this.punchIsLeft && !this.userPunchLanded) {
                    canvas.drawBitmap(this.assets.fightBackground.bitmap, this.backgroundX + (Screen.relScreenWidth * 0.1f), Screen.relYZero, (Paint) null);
                    canvas.drawBitmap(this.assets.fightTorso.bitmap, (Screen.relScreenWidth * 0.211f) + Screen.relXZero + (Screen.relScreenWidth * 0.1f), ((Screen.relScreenHeight * 1.034f) - this.assets.fightTorso.getHeight()) + Screen.relYZero, (Paint) null);
                    canvas.drawBitmap(this.assets.fightFace1.bitmap, this.headX + (Screen.relScreenWidth * 0.1f), this.headY, (Paint) null);
                    canvas.drawBitmap(this.assets.fightArmRight.bitmap, (Screen.relScreenWidth * 0.552f) + Screen.relXZero + (Screen.relScreenWidth * 0.1f), ((Screen.relScreenHeight * 0.92f) - this.assets.fightArmRight.getHeight()) + Screen.relYZero, (Paint) null);
                    this.leftFist.draw(canvas);
                }
                if (this.userPunchLanded) {
                    canvas.drawBitmap(this.assets.fightBackground.bitmap, this.backgroundX, Screen.relYZero, (Paint) null);
                    canvas.drawBitmap(this.assets.fightTorso.bitmap, (Screen.relScreenWidth * 0.211f) + Screen.relXZero, ((Screen.relScreenHeight * 1.034f) - this.assets.fightTorso.getHeight()) + Screen.relYZero, (Paint) null);
                    canvas.drawBitmap(this.assets.fightFacePunched.bitmap, this.headX, this.headY, (Paint) null);
                    canvas.drawBitmap(this.assets.fightArmRight.bitmap, (Screen.relScreenWidth * 0.552f) + Screen.relXZero, ((Screen.relScreenHeight * 0.92f) - this.assets.fightArmRight.getHeight()) + Screen.relYZero, (Paint) null);
                    canvas.drawBitmap(this.assets.fightArmLeft.bitmap, (Screen.relScreenWidth * 0.21f) + Screen.relXZero, ((Screen.relScreenHeight * 0.925f) - this.assets.fightArmLeft.getHeight()) + Screen.relYZero, (Paint) null);
                }
            } else {
                canvas.drawBitmap(this.assets.fightBackground.bitmap, this.backgroundX, Screen.relYZero, (Paint) null);
                canvas.drawBitmap(this.assets.fightTorso.bitmap, (Screen.relScreenWidth * 0.211f) + Screen.relXZero + this.offset, ((Screen.relScreenHeight * 1.034f) - this.assets.fightTorso.getHeight()) + Screen.relYZero, (Paint) null);
                canvas.drawBitmap(this.assets.fightFace1.bitmap, this.headX, this.headY, (Paint) null);
                if (!this.punchGen) {
                    if (!this.blocked) {
                        canvas.drawBitmap(this.assets.fightArmRight.bitmap, (Screen.relScreenWidth * 0.552f) + Screen.relXZero, ((Screen.relScreenHeight * 0.92f) - this.assets.fightArmRight.getHeight()) + Screen.relYZero, (Paint) null);
                        canvas.drawBitmap(this.assets.fightArmLeft.bitmap, (Screen.relScreenWidth * 0.21f) + Screen.relXZero, ((Screen.relScreenHeight * 0.925f) - this.assets.fightArmLeft.getHeight()) + Screen.relYZero, (Paint) null);
                    } else {
                        canvas.drawBitmap(this.assets.fightArmRight.bitmap, (Screen.relScreenWidth * 0.52f) + Screen.relXZero, ((Screen.relScreenHeight * 0.92f) - this.assets.fightArmRight.getHeight()) + Screen.relYZero, (Paint) null);
                        canvas.drawBitmap(this.assets.fightArmLeft.bitmap, (Screen.relScreenWidth * 0.21f) + Screen.relXZero, ((Screen.relScreenHeight * 0.925f) - this.assets.fightArmLeft.getHeight()) + Screen.relYZero, (Paint) null);
                    }
                }
                if (this.punchGen && this.punchIsLeft) {
                    canvas.drawBitmap(this.assets.fightArmRight.bitmap, (Screen.relScreenWidth * 0.552f) + Screen.relXZero + this.offset, ((Screen.relScreenHeight * 0.92f) - this.assets.fightArmRight.getHeight()) + Screen.relYZero, (Paint) null);
                }
                if (this.punchGen && this.punchIsLeft) {
                    this.leftFist.draw(canvas);
                }
                if (this.punchGen && !this.punchIsLeft) {
                    canvas.drawBitmap(this.assets.fightArmLeft.bitmap, (Screen.relScreenWidth * 0.21f) + Screen.relXZero + this.offset, ((Screen.relScreenHeight * 0.925f) - this.assets.fightArmLeft.getHeight()) + Screen.relYZero, (Paint) null);
                }
                if (this.punchGen && !this.punchIsLeft) {
                    this.rightFist.draw(canvas);
                }
            }
        } else {
            canvas.drawBitmap(this.assets.fightBackground.bitmap, this.backgroundX, Screen.relYZero, (Paint) null);
        }
        if (this.textBox != null) {
            this.textBox.draw(canvas);
        }
        if (this.fadeAlpha != 255) {
            canvas.drawARGB(this.fadeAlpha, 0, 0, 0);
        }
        canvas.drawARGB(this.damageAlpha, 0, 0, 0);
        canvas.drawARGB(this.quitFadeAlpha, 0, 0, 0);
    }

    @Override // kgm.NA.State
    public void load(AssetManager am, Assets assets) {
        assets.fightBackground.load(am, "fight-background.png", 600, 300);
        assets.fightFace1.load(am, "fight-face-1.png", 101, 110);
        assets.fightFacePunched.load(am, "fight-face-punched.png", 101, 110);
        assets.fightTorso.load(am, "fight-torso.png", 209, 165);
        assets.fightArmRight.load(am, "fight-arm-right.png", 91, 149);
        assets.fightArmLeft.load(am, "fight-arm-left.png", 91, 149);
        assets.fightFistRight.load(am, "fight-fist-right.png", 305, 237);
        assets.fightFistLeft.load(am, "fight-fist-left.png", 305, 237);
        assets.swing.load(assets.sounds, am, "swing.ogg");
        assets.dodge.load(assets.sounds, am, "swing.ogg");
        assets.cpuPunch.load(assets.sounds, am, "cpu-punch.ogg");
        assets.userPunch.load(assets.sounds, am, "user-punch.ogg");
    }

    @Override // kgm.NA.State
    public void delete() {
        this.assets.fightBackground.delete();
        this.assets.fightFace1.delete();
        this.assets.fightFacePunched.delete();
        this.assets.fightTorso.delete();
        this.assets.fightArmRight.delete();
        this.assets.fightArmLeft.delete();
        this.assets.fightFistRight.delete();
        this.assets.fightFistLeft.delete();
        this.assets.swing.delete(this.assets.sounds);
        this.assets.dodge.delete(this.assets.sounds);
        this.assets.cpuPunch.delete(this.assets.sounds);
        this.assets.userPunch.delete(this.assets.sounds);
    }

    @Override // kgm.NA.State
    public void pause() {
    }

    @Override // kgm.NA.State
    public void resume() {
    }
}
