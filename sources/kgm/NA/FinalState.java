package kgm.NA;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;
import java.util.Random;

/* loaded from: classes.dex */
public class FinalState extends State {
    private static /* synthetic */ int[] $SWITCH_TABLE$kgm$NA$FinalState$SubState;
    long attackTimer;
    boolean creatureActive;
    int creatureAlpha;
    float creatureDestX;
    int creatureHealth;
    boolean creatureLeft;
    Paint creaturePaint;
    float creatureX;
    float creatureY;
    boolean cymbalPlayed;
    int damageFrames;
    long delayTimer;
    int fadeAlpha;
    boolean finalMusicStarted;
    DSprite fireballSprite;
    boolean firstFrame;
    int frameCount;
    long inputTimer;
    boolean isFading;
    States nextState;
    boolean rockActive;
    int rockCount;
    Paint rockPaint;
    float rockSize;
    long rockTimer;
    float rockX;
    float rockY;
    long soundTimer;
    long spikeTimer;
    long startTimer;
    SubState subState;
    int tempAlpha;
    DSprite thrownRockSprite;
    Random rand = new Random();
    boolean[] spikeActive = new boolean[3];
    boolean[] spikeHit = new boolean[3];
    float[] spikeX = new float[3];
    float[] spikeY = new float[3];

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public enum SubState {
        INTRO,
        IDLE,
        ROCK,
        FIREBALL,
        DEATH,
        HIT;

        /* renamed from: values  reason: to resolve conflict with enum method */
        public static SubState[] valuesCustom() {
            SubState[] valuesCustom = values();
            int length = valuesCustom.length;
            SubState[] subStateArr = new SubState[length];
            System.arraycopy(valuesCustom, 0, subStateArr, 0, length);
            return subStateArr;
        }
    }

    static /* synthetic */ int[] $SWITCH_TABLE$kgm$NA$FinalState$SubState() {
        int[] iArr = $SWITCH_TABLE$kgm$NA$FinalState$SubState;
        if (iArr == null) {
            iArr = new int[SubState.valuesCustom().length];
            try {
                iArr[SubState.DEATH.ordinal()] = 5;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[SubState.FIREBALL.ordinal()] = 4;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[SubState.HIT.ordinal()] = 6;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[SubState.IDLE.ordinal()] = 2;
            } catch (NoSuchFieldError e4) {
            }
            try {
                iArr[SubState.INTRO.ordinal()] = 1;
            } catch (NoSuchFieldError e5) {
            }
            try {
                iArr[SubState.ROCK.ordinal()] = 3;
            } catch (NoSuchFieldError e6) {
            }
            $SWITCH_TABLE$kgm$NA$FinalState$SubState = iArr;
        }
        return iArr;
    }

    public FinalState(AssetManager am, Assets assets) {
        this.status = States.RUNNING;
        this.assets = assets;
        load(am, assets);
        this.isFading = false;
        this.subState = SubState.INTRO;
        this.fadeAlpha = MotionEventCompat.ACTION_MASK;
        this.creatureX = Screen.relScreenWidth + Screen.relXZero;
        this.creatureY = (Screen.relScreenHeight * 0.5f) + Screen.relYZero;
        this.creatureHealth = 60;
        assets.finalIntroMusic.play(false);
        this.startTimer = System.currentTimeMillis() + 16000;
        this.delayTimer = System.currentTimeMillis() + 8000;
        this.creaturePaint = new Paint();
        this.fireballSprite = new DSprite(assets.fireball, 0.0f, 0.0f);
        this.thrownRockSprite = new DSprite(assets.thrownRock, 0.0f, 0.0f);
        this.rockPaint = new Paint();
        this.rockPaint.setTextSize(Screen.relScreenWidth * 0.075f);
        this.spikeTimer = System.currentTimeMillis() + this.rand.nextInt(15000);
    }

    @Override // kgm.NA.State
    public void update(MotionEvent touch, KeyEvent key) {
        if (touch != null && touch.getX() > Screen.relScreenWidth + Screen.relXZero) {
            touch.setLocation(-10.0f, -10.0f);
        }
        switch ($SWITCH_TABLE$kgm$NA$FinalState$SubState()[this.subState.ordinal()]) {
            case 1:
                this.frameCount++;
                if (System.currentTimeMillis() > this.delayTimer) {
                    this.fadeAlpha--;
                }
                if (this.fadeAlpha < 0) {
                    this.fadeAlpha = 0;
                }
                if (System.currentTimeMillis() > this.startTimer) {
                    this.creatureActive = true;
                }
                if (this.creatureActive) {
                    this.creatureX -= Screen.relScreenWidth * 0.003f;
                }
                if (this.creatureX < (Screen.relScreenWidth * 0.82f) + Screen.relXZero && !this.cymbalPlayed) {
                    this.cymbalPlayed = true;
                    this.assets.cymbal.playSound(this.assets.sounds);
                }
                if (this.creatureX < Screen.relXZero + (Screen.relScreenWidth / 2)) {
                    this.subState = SubState.IDLE;
                    this.assets.finalIntroMusic.stop();
                    this.assets.finalMusic.play(true);
                    this.assets.finalMusic.setVolume(0.25f);
                    this.assets.cymbal.stop(this.assets.sounds);
                    this.fadeAlpha = 0;
                    generatePos();
                    this.attackTimer = System.currentTimeMillis() + this.rand.nextInt(20000);
                    this.rockTimer = System.currentTimeMillis() + this.rand.nextInt(10000);
                    this.soundTimer = System.currentTimeMillis() + this.rand.nextInt(15000);
                    break;
                }
                break;
            case 2:
                updateCreature();
                if (this.rockTimer < System.currentTimeMillis() && !this.rockActive) {
                    this.rockActive = true;
                    this.rockTimer = System.currentTimeMillis();
                    while (true) {
                        this.rockX = this.rand.nextInt(Screen.relScreenWidth) + Screen.relXZero;
                        if (this.rockX >= Screen.relXZero && this.rockX <= (Screen.relScreenWidth + Screen.relXZero) - this.assets.rock.getWidth()) {
                            while (true) {
                                this.rockY = this.rand.nextInt(Screen.relScreenHeight) + Screen.relYZero;
                                if (this.rockY < Screen.relYZero + (Screen.relScreenHeight * 0.75d) || this.rockY > (Screen.relScreenHeight + Screen.relYZero) - this.assets.rock.getHeight()) {
                                }
                            }
                        }
                    }
                }
                if (this.rockActive && touch != null && touch.getAction() == 0 && touch.getX() > this.rockX && touch.getX() < this.rockX + this.assets.rock.getWidth() && touch.getY() > this.rockY && touch.getY() < this.rockY + this.assets.rock.getHeight()) {
                    this.rockCount++;
                    this.rockActive = false;
                    this.rockTimer = System.currentTimeMillis() + this.rand.nextInt(15000);
                }
                if (touch != null && this.rockCount > 0 && this.subState == SubState.IDLE && touch.getAction() == 0 && touch.getX() > Screen.relXZero && touch.getY() > Screen.relYZero) {
                    float y = (Screen.relScreenHeight * 0.68f) + Screen.relYZero;
                    if (touch.getY() < y) {
                        this.rockCount--;
                        this.subState = SubState.ROCK;
                        this.assets.rockThrow.playSound(this.assets.sounds);
                        this.thrownRockSprite.x = touch.getX() - (this.assets.thrownRock.getWidth() / 2);
                        this.thrownRockSprite.y = Screen.relYZero - this.assets.thrownRock.getHeight();
                        this.thrownRockSprite.scale(0.8f, 0.8f);
                    }
                }
                if (this.spikeTimer < System.currentTimeMillis() && this.subState == SubState.IDLE) {
                    int i = 0;
                    while (true) {
                        if (i < 3) {
                            if (this.spikeActive[i]) {
                                i++;
                            } else {
                                this.spikeActive[i] = true;
                                while (true) {
                                    this.spikeX[i] = this.rand.nextInt(Screen.relScreenWidth) + Screen.relXZero;
                                    if (this.spikeX[i] >= Screen.relXZero && this.spikeX[i] <= (Screen.relScreenWidth + Screen.relXZero) - (this.assets.spike.getWidth() * 3)) {
                                        this.spikeY[i] = (Screen.relScreenHeight * 0.2f) + Screen.relYZero;
                                        this.spikeTimer = System.currentTimeMillis() + this.rand.nextInt(15000);
                                    }
                                }
                            }
                        }
                    }
                }
                if (this.soundTimer < System.currentTimeMillis()) {
                    this.assets.creature.playSound(this.assets.sounds, 0.5f);
                    this.soundTimer = System.currentTimeMillis() + this.rand.nextInt(15000);
                }
                if (this.attackTimer < System.currentTimeMillis() && this.subState == SubState.IDLE) {
                    this.subState = SubState.FIREBALL;
                    this.assets.fireballSound.playSound(this.assets.sounds);
                    this.fireballSprite.scale(0.2f, 0.2f);
                    this.fireballSprite.x = this.creatureX;
                    this.fireballSprite.y = this.creatureY;
                    this.fireballSprite.setAlpha(MotionEventCompat.ACTION_MASK);
                }
                if (this.damageFrames != 0) {
                    this.damageFrames++;
                    if (this.damageFrames % 2 == 0) {
                        this.creatureAlpha = MotionEventCompat.ACTION_MASK;
                        this.creaturePaint.setAlpha(this.creatureAlpha);
                    } else {
                        this.creatureAlpha = 0;
                        this.creaturePaint.setAlpha(this.creatureAlpha);
                    }
                    if (this.damageFrames >= 40) {
                        this.damageFrames = 0;
                        this.creatureAlpha = MotionEventCompat.ACTION_MASK;
                        this.creaturePaint.setAlpha(this.creatureAlpha);
                        break;
                    }
                }
                break;
            case 3:
                updateCreature();
                for (int i2 = 0; i2 < 3; i2++) {
                    if (this.spikeHit[i2]) {
                        float[] fArr = this.spikeY;
                        fArr[i2] = fArr[i2] + (Screen.relScreenHeight * 0.015f);
                        if (this.spikeY[i2] > (Screen.relScreenHeight * 0.36f) + Screen.relYZero && this.spikeY[i2] < (Screen.relScreenHeight * 0.6f) + Screen.relYZero && this.spikeX[i2] > this.creatureX && this.spikeX[i2] - this.assets.spike.getWidth() < this.creatureX) {
                            this.creatureHealth -= 10;
                            this.assets.creatureHit.playSound(this.assets.sounds);
                            this.assets.creature.playSound(this.assets.sounds, 0.5f);
                            this.damageFrames = 1;
                            if (this.creatureHealth > 0) {
                                this.subState = SubState.IDLE;
                                this.spikeHit[0] = false;
                                this.spikeHit[1] = false;
                                this.spikeHit[2] = false;
                            } else {
                                this.subState = SubState.DEATH;
                                this.assets.finalMusic.stop();
                                this.creatureY = (Screen.relScreenHeight * 0.75f) + Screen.relYZero;
                                this.creatureX -= this.assets.creatureDead.getHeight() / 2;
                            }
                            for (int k = 0; k < 3; k++) {
                                if (this.spikeActive[k] && this.spikeHit[k]) {
                                    this.spikeHit[k] = false;
                                    this.spikeActive[k] = false;
                                }
                            }
                        }
                        if (this.spikeY[i2] > Screen.relScreenHeight * 0.7f) {
                            this.subState = SubState.IDLE;
                            for (int k2 = 0; k2 < 3; k2++) {
                                if (this.spikeActive[k2] && this.spikeHit[k2]) {
                                    this.spikeHit[k2] = false;
                                    this.spikeActive[k2] = false;
                                }
                            }
                        }
                    }
                }
                if (!this.spikeHit[0] && !this.spikeHit[1] && !this.spikeHit[2] && this.subState != SubState.IDLE) {
                    DSprite dSprite = this.thrownRockSprite;
                    DSprite dSprite2 = this.thrownRockSprite;
                    float f = dSprite2.scaleX - 0.015f;
                    dSprite2.scaleX = f;
                    DSprite dSprite3 = this.thrownRockSprite;
                    float f2 = dSprite3.scaleY - 0.015f;
                    dSprite3.scaleY = f2;
                    dSprite.scale(f, f2);
                    this.thrownRockSprite.y += Screen.relScreenHeight * 0.0175f;
                    this.thrownRockSprite.x -= this.thrownRockSprite.scaleX * 0.02f;
                    if (this.thrownRockSprite.y > (Screen.relScreenHeight * 0.45f) + Screen.relYZero && this.thrownRockSprite.x > this.creatureX && this.thrownRockSprite.x < this.creatureX + this.assets.creatureFrame1.getWidth()) {
                        if (this.creatureLeft) {
                            if (this.creatureX - (this.assets.creatureFrame1.getWidth() * 1.5f) > Screen.relXZero) {
                                this.creatureX -= this.assets.creatureFrame1.getWidth() * 1.5f;
                            } else {
                                this.creatureX += this.assets.creatureFrame1.getWidth() * 1.5f;
                            }
                        } else if (this.creatureX + (this.assets.creatureFrame1.getWidth() * 1.5f) < Screen.relScreenWidth + Screen.relXZero) {
                            this.creatureX += this.assets.creatureFrame1.getWidth() * 1.5f;
                        } else {
                            this.creatureX -= this.assets.creatureFrame1.getWidth() * 1.5f;
                        }
                    }
                    if (this.thrownRockSprite.scaleX < 0.26f) {
                        for (int j = 0; j < 3; j++) {
                            if (this.thrownRockSprite.x > this.spikeX[j] - this.assets.spike.getWidth() && this.thrownRockSprite.x < this.spikeX[j] + this.assets.spike.getWidth()) {
                                this.spikeHit[j] = true;
                                this.assets.spikeHit.playSound(this.assets.sounds, 0.2f);
                                this.thrownRockSprite.scaleX = 0.0f;
                                this.thrownRockSprite.scaleY = 0.0f;
                            }
                        }
                    }
                    if (this.thrownRockSprite.y > Screen.relScreenHeight * 0.75f) {
                        this.subState = SubState.IDLE;
                        this.thrownRockSprite.scaleX = 1.0f;
                        this.thrownRockSprite.scaleY = 1.0f;
                        break;
                    }
                }
                break;
            case 4:
                DSprite dSprite4 = this.fireballSprite;
                DSprite dSprite5 = this.fireballSprite;
                float f3 = dSprite5.scaleX + 0.01f;
                dSprite5.scaleX = f3;
                DSprite dSprite6 = this.fireballSprite;
                float f4 = dSprite6.scaleY + 0.01f;
                dSprite6.scaleY = f4;
                dSprite4.scale(f3, f4);
                this.fireballSprite.x -= Screen.relScreenHeight * 0.005f;
                this.fireballSprite.y -= Screen.relScreenHeight * 0.005f;
                this.fireballSprite.rotate(10.0f, false);
                if (this.fireballSprite.scaleX >= 1.0f) {
                    this.fadeAlpha += 25;
                    this.tempAlpha = MotionEventCompat.ACTION_MASK;
                    this.subState = SubState.HIT;
                    this.attackTimer = System.currentTimeMillis() + this.rand.nextInt(20000);
                    if (this.fadeAlpha >= 255) {
                        this.fadeAlpha = MotionEventCompat.ACTION_MASK;
                        this.isFading = true;
                        this.nextState = States.CAVE;
                    }
                }
                if (touch != null && touch.getAction() == 0) {
                    this.fireballSprite.alpha.setAlpha(this.fireballSprite.alpha.getAlpha() - 25);
                    if (this.fireballSprite.alpha.getAlpha() <= 25) {
                        this.subState = SubState.IDLE;
                        this.tempAlpha = MotionEventCompat.ACTION_MASK;
                        this.fireballSprite.setAlpha(MotionEventCompat.ACTION_MASK);
                        this.attackTimer = System.currentTimeMillis() + this.rand.nextInt(20000);
                        this.inputTimer = System.currentTimeMillis() + 250;
                    }
                }
                updateCreature();
                break;
            case 5:
                if (!this.assets.finalOutroMusic.mediaPlayer.isPlaying()) {
                    if (this.finalMusicStarted) {
                        this.isFading = true;
                        this.nextState = States.OUTRO;
                        break;
                    } else {
                        this.finalMusicStarted = true;
                        this.assets.finalOutroMusic.play(false);
                        this.assets.finalOutroMusic.setVolume(0.4f);
                        break;
                    }
                }
                break;
            case 6:
                updateCreature();
                this.tempAlpha -= 4;
                if (this.tempAlpha <= this.fadeAlpha) {
                    this.tempAlpha = this.fadeAlpha;
                    this.inputTimer = System.currentTimeMillis() + 250;
                    this.subState = SubState.IDLE;
                    break;
                }
                break;
        }
        if (touch != null) {
            touch.setLocation(-10.0f, -10.0f);
        }
        if (this.frameCount >= 10) {
            this.frameCount = 0;
            if (this.firstFrame) {
                this.firstFrame = false;
            } else {
                this.firstFrame = true;
            }
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
        canvas.drawBitmap(this.assets.finalBackground.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        if (this.firstFrame && this.creatureHealth > 0) {
            canvas.drawBitmap(this.assets.creatureFrame1.bitmap, this.creatureX, this.creatureY, this.creaturePaint);
        }
        if (!this.firstFrame && this.creatureHealth > 0) {
            canvas.drawBitmap(this.assets.creatureFrame2.bitmap, this.creatureX, this.creatureY, this.creaturePaint);
        }
        if (this.creatureHealth <= 0) {
            canvas.drawBitmap(this.assets.creatureDead.bitmap, this.creatureX, this.creatureY, (Paint) null);
        }
        if (this.rockActive) {
            canvas.drawBitmap(this.assets.rock.bitmap, this.rockX, this.rockY, (Paint) null);
        }
        for (int i = 0; i < 3; i++) {
            if (this.spikeActive[i]) {
                canvas.drawBitmap(this.assets.spike.bitmap, this.spikeX[i], this.spikeY[i], (Paint) null);
            }
        }
        canvas.drawBitmap(this.assets.finalForeground.bitmap, Screen.relXZero, Screen.relYZero, (Paint) null);
        if (this.subState != SubState.INTRO) {
            canvas.drawText(Integer.toString(this.rockCount), (Screen.relScreenWidth * 0.035f) + Screen.relXZero, (Screen.relScreenHeight * 0.12f) + Screen.relYZero, this.rockPaint);
        }
        if (this.subState == SubState.ROCK) {
            this.thrownRockSprite.draw(canvas);
        }
        if (this.subState == SubState.FIREBALL) {
            this.fireballSprite.draw(canvas);
        }
        if (this.subState == SubState.HIT) {
            canvas.drawARGB(this.tempAlpha, 0, 0, 0);
        }
        canvas.drawARGB(this.fadeAlpha, 0, 0, 0);
    }

    @Override // kgm.NA.State
    public void load(AssetManager am, Assets assets) {
        assets.creatureFrame1.load(am, "creature-1.png", 46, 104);
        assets.creatureFrame2.load(am, "creature-2.png", 46, 104);
        assets.creatureDead.load(am, "creature-dead.png", 103, 46);
        assets.fireball.load(am, "fireball.png", 334, 307);
        assets.rock.load(am, "rock.png", 28, 31);
        assets.thrownRock.load(am, "thrown-rock.png", 77, 83);
        assets.finalBackground.load(am, "final-background.png", 400, 300);
        assets.finalForeground.load(am, "final-foreground.png", 400, 92);
        assets.spike.load(am, "spike.png", 16, 43);
        assets.finalIntroMusic.load(am, "final-intro-music.ogg");
        assets.finalOutroMusic.load(am, "final-outro-music.ogg");
        assets.finalMusic.load(am, "final-music.ogg");
        assets.creature.load(assets.sounds, am, "creature.ogg");
        assets.creatureHit.load(assets.sounds, am, "cpu-punch.ogg");
        assets.fireballSound.load(assets.sounds, am, "fireball.ogg");
        assets.rockThrow.load(assets.sounds, am, "swing.ogg");
        assets.spikeHit.load(assets.sounds, am, "spike-hit.ogg");
        assets.cymbal.load(assets.sounds, am, "cymbal.ogg");
    }

    @Override // kgm.NA.State
    public void delete() {
        this.assets.creatureFrame1.delete();
        this.assets.creatureFrame2.delete();
        this.assets.creatureDead.delete();
        this.assets.fireball.delete();
        this.assets.rock.delete();
        this.assets.thrownRock.delete();
        this.assets.finalBackground.delete();
        this.assets.finalForeground.delete();
        this.assets.spike.delete();
        this.assets.finalIntroMusic.delete();
        this.assets.finalOutroMusic.delete();
        this.assets.finalMusic.delete();
        this.assets.creature.delete(this.assets.sounds);
        this.assets.fireballSound.delete(this.assets.sounds);
        this.assets.rockThrow.delete(this.assets.sounds);
        this.assets.spikeHit.delete(this.assets.sounds);
        this.assets.cymbal.delete(this.assets.sounds);
        this.assets.creatureHit.delete(this.assets.sounds);
    }

    @Override // kgm.NA.State
    public void pause() {
        if (this.assets.finalIntroMusic.mediaPlayer.isPlaying()) {
            this.assets.finalIntroMusic.pause();
        }
        if (this.assets.finalMusic.mediaPlayer.isPlaying()) {
            this.assets.finalMusic.pause();
        }
        if (this.assets.finalOutroMusic.mediaPlayer.isPlaying()) {
            this.assets.finalOutroMusic.pause();
        }
    }

    @Override // kgm.NA.State
    public void resume() {
        if (this.assets.finalIntroMusic.isPaused) {
            this.assets.finalIntroMusic.resume();
        }
        if (this.assets.finalMusic.isPaused) {
            this.assets.finalMusic.resume();
        }
        if (this.assets.finalOutroMusic.isPaused) {
            this.assets.finalOutroMusic.resume();
        }
    }

    public void generatePos() {
        this.creatureLeft = this.rand.nextBoolean();
        if (this.creatureLeft) {
            this.creatureDestX -= (Screen.relScreenWidth * this.rand.nextFloat()) + Screen.relXZero;
        } else {
            this.creatureDestX += (Screen.relScreenWidth * this.rand.nextFloat()) + Screen.relXZero;
        }
        if (this.creatureDestX < Screen.relXZero) {
            this.creatureLeft = false;
            this.creatureDestX += (Screen.relScreenWidth * 0.25f) + Screen.relXZero;
        }
        if (this.creatureDestX > (Screen.relScreenWidth + Screen.relXZero) - this.assets.creatureFrame1.getWidth()) {
            this.creatureLeft = true;
            this.creatureDestX -= (Screen.relScreenWidth * 0.25f) + Screen.relXZero;
        }
    }

    public void updateCreature() {
        this.frameCount++;
        if (this.creatureLeft) {
            this.creatureX -= Screen.relScreenWidth * 0.0045f;
            if (this.creatureX <= this.creatureDestX) {
                generatePos();
            }
        }
        if (!this.creatureLeft) {
            this.creatureX += Screen.relScreenWidth * 0.0045f;
            if (this.creatureX >= this.creatureDestX) {
                generatePos();
            }
        }
    }

    public void generateRock() {
    }

    public void creatureDamage() {
    }
}
