package kgm.NA;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.util.TimeUtils;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/* loaded from: classes.dex */
public class Engine extends Activity implements Runnable, View.OnTouchListener, View.OnKeyListener {
    private static /* synthetic */ int[] $SWITCH_TABLE$kgm$NA$States;
    static States lastState;
    public int absScreenH;
    public int absScreenW;
    AssetManager assetManager;
    Assets assets;
    State currentState;
    int frameRate;
    SurfaceView gameScreen;
    Thread gameThread;
    SurfaceHolder holder;
    KeyEvent key;
    Paint letterboxPaint;
    int letterboxSize;
    boolean orientIsPortrait;
    boolean paused;
    PowerManager powerManager;
    public int relScreenHeight;
    public int relScreenWidth;
    public volatile boolean running;
    SharedPreferences saveData;
    SharedPreferences.Editor saveEditor;
    public int screenDPI;
    DisplayMetrics screenInfo;
    Paint testTextPaint;
    MotionEvent touch;
    PowerManager.WakeLock wakeLock;

    static /* synthetic */ int[] $SWITCH_TABLE$kgm$NA$States() {
        int[] iArr = $SWITCH_TABLE$kgm$NA$States;
        if (iArr == null) {
            iArr = new int[States.valuesCustom().length];
            try {
                iArr[States.BACKROOM.ordinal()] = 16;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[States.BEACH.ordinal()] = 21;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[States.BUG_GAME.ordinal()] = 38;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[States.CABIN.ordinal()] = 11;
            } catch (NoSuchFieldError e4) {
            }
            try {
                iArr[States.CASINO.ordinal()] = 14;
            } catch (NoSuchFieldError e5) {
            }
            try {
                iArr[States.CAVE.ordinal()] = 35;
            } catch (NoSuchFieldError e6) {
            }
            try {
                iArr[States.CLUB.ordinal()] = 6;
            } catch (NoSuchFieldError e7) {
            }
            try {
                iArr[States.COMPUTER.ordinal()] = 24;
            } catch (NoSuchFieldError e8) {
            }
            try {
                iArr[States.CRAB_GAME.ordinal()] = 39;
            } catch (NoSuchFieldError e9) {
            }
            try {
                iArr[States.DOWNTOWN.ordinal()] = 5;
            } catch (NoSuchFieldError e10) {
            }
            try {
                iArr[States.FIGHT.ordinal()] = 7;
            } catch (NoSuchFieldError e11) {
            }
            try {
                iArr[States.FINAL.ordinal()] = 41;
            } catch (NoSuchFieldError e12) {
            }
            try {
                iArr[States.FOREST.ordinal()] = 10;
            } catch (NoSuchFieldError e13) {
            }
            try {
                iArr[States.HALLWAY.ordinal()] = 17;
            } catch (NoSuchFieldError e14) {
            }
            try {
                iArr[States.HOUSE_ONE.ordinal()] = 27;
            } catch (NoSuchFieldError e15) {
            }
            try {
                iArr[States.HOUSE_THREE.ordinal()] = 29;
            } catch (NoSuchFieldError e16) {
            }
            try {
                iArr[States.HOUSE_TWO.ordinal()] = 28;
            } catch (NoSuchFieldError e17) {
            }
            try {
                iArr[States.HUNTING.ordinal()] = 36;
            } catch (NoSuchFieldError e18) {
            }
            try {
                iArr[States.INTRO.ordinal()] = 4;
            } catch (NoSuchFieldError e19) {
            }
            try {
                iArr[States.INVENTORY.ordinal()] = 37;
            } catch (NoSuchFieldError e20) {
            }
            try {
                iArr[States.LIGHTHOUSE.ordinal()] = 33;
            } catch (NoSuchFieldError e21) {
            }
            try {
                iArr[States.LOBBY.ordinal()] = 15;
            } catch (NoSuchFieldError e22) {
            }
            try {
                iArr[States.MALL.ordinal()] = 30;
            } catch (NoSuchFieldError e23) {
            }
            try {
                iArr[States.MAP.ordinal()] = 3;
            } catch (NoSuchFieldError e24) {
            }
            try {
                iArr[States.MAZE.ordinal()] = 34;
            } catch (NoSuchFieldError e25) {
            }
            try {
                iArr[States.MENU.ordinal()] = 2;
            } catch (NoSuchFieldError e26) {
            }
            try {
                iArr[States.OFFICE.ordinal()] = 18;
            } catch (NoSuchFieldError e27) {
            }
            try {
                iArr[States.OUTRO.ordinal()] = 40;
            } catch (NoSuchFieldError e28) {
            }
            try {
                iArr[States.PARK.ordinal()] = 26;
            } catch (NoSuchFieldError e29) {
            }
            try {
                iArr[States.PAWN.ordinal()] = 9;
            } catch (NoSuchFieldError e30) {
            }
            try {
                iArr[States.POLICE.ordinal()] = 12;
            } catch (NoSuchFieldError e31) {
            }
            try {
                iArr[States.POLICE_OFFICE.ordinal()] = 13;
            } catch (NoSuchFieldError e32) {
            }
            try {
                iArr[States.ROULETTE.ordinal()] = 23;
            } catch (NoSuchFieldError e33) {
            }
            try {
                iArr[States.RUNNING.ordinal()] = 1;
            } catch (NoSuchFieldError e34) {
            }
            try {
                iArr[States.SLOTS.ordinal()] = 8;
            } catch (NoSuchFieldError e35) {
            }
            try {
                iArr[States.STORE.ordinal()] = 31;
            } catch (NoSuchFieldError e36) {
            }
            try {
                iArr[States.SUBURBS.ordinal()] = 20;
            } catch (NoSuchFieldError e37) {
            }
            try {
                iArr[States.THEATER.ordinal()] = 32;
            } catch (NoSuchFieldError e38) {
            }
            try {
                iArr[States.TREE_FORT.ordinal()] = 25;
            } catch (NoSuchFieldError e39) {
            }
            try {
                iArr[States.VIP.ordinal()] = 19;
            } catch (NoSuchFieldError e40) {
            }
            try {
                iArr[States.WELCOME.ordinal()] = 22;
            } catch (NoSuchFieldError e41) {
            }
            $SWITCH_TABLE$kgm$NA$States = iArr;
        }
        return iArr;
    }

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setRequestedOrientation(0);
        this.orientIsPortrait = false;
        this.powerManager = (PowerManager) getSystemService("power");
        this.wakeLock = this.powerManager.newWakeLock(6, "wakelock");
        this.wakeLock.acquire();
        super.onCreate(savedInstanceState);
        this.gameScreen = new SurfaceView(this);
        this.gameScreen.setOnTouchListener(this);
        this.gameScreen.setOnKeyListener(this);
        this.gameScreen.setFocusableInTouchMode(true);
        this.gameScreen.requestFocus();
        setContentView(this.gameScreen);
        this.holder = this.gameScreen.getHolder();
        this.screenInfo = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(this.screenInfo);
        if (this.orientIsPortrait) {
            this.relScreenHeight = (int) ((this.screenInfo.widthPixels * 1.3333f) + 0.5f);
            this.relScreenWidth = this.screenInfo.widthPixels;
            this.letterboxSize = (this.screenInfo.heightPixels - this.relScreenHeight) / 2;
        } else {
            this.relScreenWidth = (int) ((this.screenInfo.heightPixels * 1.3333f) + 0.5f);
            this.relScreenHeight = this.screenInfo.heightPixels;
            this.letterboxSize = (this.screenInfo.widthPixels - this.relScreenWidth) / 2;
        }
        this.absScreenH = this.screenInfo.heightPixels;
        this.absScreenW = this.screenInfo.widthPixels;
        Screen.absScreenWidth = this.screenInfo.widthPixels;
        Screen.absScreenHeight = this.screenInfo.heightPixels;
        Screen.relScreenWidth = this.relScreenWidth;
        Screen.relScreenHeight = this.relScreenHeight;
        Screen.relXZero = (this.screenInfo.widthPixels - this.relScreenWidth) / 2;
        Screen.relYZero = (this.screenInfo.heightPixels - this.relScreenHeight) / 2;
        this.letterboxPaint = new Paint();
        this.letterboxPaint.setColor(-16777216);
        this.testTextPaint = new Paint();
        this.testTextPaint.setColor(SupportMenu.CATEGORY_MASK);
        setVolumeControlStream(3);
        this.saveData = getSharedPreferences("saveData", 0);
        this.saveEditor = this.saveData.edit();
        SaveFile.read(this.saveData);
        this.assetManager = getAssets();
        this.assets = new Assets(this.assetManager);
        this.paused = false;
        this.touch = null;
        this.key = null;
        this.frameRate = 40;
        this.currentState = new MenuState(this.assetManager, this.assets);
        this.running = true;
        this.gameThread = new Thread(this);
        this.gameThread.start();
    }

    @Override // android.app.Activity
    public void onResume() {
        super.onResume();
        this.wakeLock.acquire();
        this.paused = false;
        this.currentState.resume();
    }

    @Override // android.app.Activity
    public void onPause() {
        super.onPause();
        if (this.wakeLock.isHeld()) {
            this.wakeLock.release();
        }
        SaveFile.write(this.saveEditor);
        this.saveEditor.commit();
        this.currentState.pause();
        this.paused = true;
    }

    public void onConfigurationChange(Configuration config) {
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View v, MotionEvent event) {
        this.touch = event;
        return true;
    }

    @Override // android.view.View.OnKeyListener
    public boolean onKey(View view, int keyCode, KeyEvent event) {
        if (keyCode == 3 || keyCode == 24 || keyCode == 25 || keyCode == 164) {
            return false;
        }
        if (keyCode != 4) {
            this.key = event;
            return true;
        }
        return true;
    }

    @Override // java.lang.Runnable
    public void run() {
        Paint textPaint = new Paint();
        textPaint.setColor(SupportMenu.CATEGORY_MASK);
        textPaint.setTextSize(20.0f);
        System.currentTimeMillis();
        long perFrameTime = 1000 / this.frameRate;
        boolean testTextOn = false;
        while (this.running) {
            if (!this.paused) {
                if (this.key != null && this.key.getKeyCode() == 82 && this.key.getAction() == 0) {
                    if (testTextOn) {
                        testTextOn = false;
                    } else {
                        testTextOn = true;
                    }
                }
                if (this.currentState.status != States.RUNNING) {
                    this.currentState.delete();
                    switch ($SWITCH_TABLE$kgm$NA$States()[this.currentState.status.ordinal()]) {
                        case 2:
                            this.currentState = new MenuState(this.assetManager, this.assets);
                            break;
                        case 3:
                            this.currentState = new MapState(this.assetManager, this.assets);
                            break;
                        case 4:
                            this.currentState = new IntroState(this.assetManager, this.assets);
                            break;
                        case 5:
                            this.currentState = new DowntownState(this.assetManager, this.assets);
                            break;
                        case 6:
                            this.currentState = new ClubState(this.assetManager, this.assets);
                            break;
                        case MotionEventCompat.ACTION_HOVER_MOVE /* 7 */:
                            this.currentState = new FightState(this.assetManager, this.assets);
                            break;
                        case 8:
                            this.currentState = new SlotState(this.assetManager, this.assets);
                            break;
                        case 9:
                            this.currentState = new PawnState(this.assetManager, this.assets);
                            break;
                        case 10:
                            this.currentState = new ForestState(this.assetManager, this.assets);
                            break;
                        case 11:
                            this.currentState = new CabinState(this.assetManager, this.assets);
                            break;
                        case 12:
                            this.currentState = new PoliceState(this.assetManager, this.assets);
                            break;
                        case 13:
                            this.currentState = new PoliceOfficeState(this.assetManager, this.assets);
                            break;
                        case 14:
                            this.currentState = new CasinoState(this.assetManager, this.assets);
                            break;
                        case ViewDragHelper.EDGE_ALL /* 15 */:
                            this.currentState = new LobbyState(this.assetManager, this.assets);
                            break;
                        case 16:
                            this.currentState = new BackroomState(this.assetManager, this.assets);
                            break;
                        case 17:
                            this.currentState = new HallwayState(this.assetManager, this.assets);
                            break;
                        case 18:
                            this.currentState = new OfficeState(this.assetManager, this.assets);
                            break;
                        case TimeUtils.HUNDRED_DAY_FIELD_LEN /* 19 */:
                            this.currentState = new VIPState(this.assetManager, this.assets);
                            break;
                        case 20:
                            this.currentState = new SuburbsState(this.assetManager, this.assets);
                            break;
                        case 21:
                            this.currentState = new BeachState(this.assetManager, this.assets);
                            break;
                        case 22:
                            this.currentState = new WelcomeState(this.assetManager, this.assets);
                            break;
                        case 23:
                            this.currentState = new RouletteState(this.assetManager, this.assets);
                            break;
                        case 24:
                            this.currentState = new ComputerState(this.assetManager, this.assets);
                            break;
                        case 25:
                            this.currentState = new TreeFortState(this.assetManager, this.assets);
                            break;
                        case 26:
                            this.currentState = new ParkState(this.assetManager, this.assets);
                            break;
                        case 27:
                            this.currentState = new HouseOneState(this.assetManager, this.assets);
                            break;
                        case 28:
                            this.currentState = new HouseTwoState(this.assetManager, this.assets);
                            break;
                        case 29:
                            this.currentState = new HouseThreeState(this.assetManager, this.assets);
                            break;
                        case 30:
                            this.currentState = new MallState(this.assetManager, this.assets);
                            break;
                        case 31:
                            this.currentState = new StoreState(this.assetManager, this.assets);
                            break;
                        case 32:
                            this.currentState = new TheaterState(this.assetManager, this.assets);
                            break;
                        case 33:
                            this.currentState = new LighthouseState(this.assetManager, this.assets);
                            break;
                        case 34:
                            this.currentState = new MazeState(this.assetManager, this.assets);
                            break;
                        case 35:
                            this.currentState = new CaveState(this.assetManager, this.assets);
                            break;
                        case 36:
                            this.currentState = new HuntingState(this.assetManager, this.assets);
                            break;
                        case 37:
                            this.currentState = new InventoryState(this.assetManager, this.assets, lastState);
                            break;
                        case 38:
                            this.currentState = new BugGameState(this.assetManager, this.assets);
                            break;
                        case 39:
                            this.currentState = new CrabGameState(this.assetManager, this.assets);
                            break;
                        case 40:
                            this.currentState = new OutroState(this.assetManager, this.assets);
                            break;
                        case 41:
                            this.currentState = new FinalState(this.assetManager, this.assets);
                            break;
                    }
                }
                long startTime = System.currentTimeMillis();
                this.currentState.update(this.touch, this.key);
                if (this.holder.getSurface().isValid()) {
                    Canvas canvas = this.holder.lockCanvas();
                    if (canvas != null) {
                        this.currentState.draw(canvas);
                        drawLetterbox(canvas);
                        this.holder.unlockCanvasAndPost(canvas);
                    }
                    long frameTime = System.currentTimeMillis() - startTime;
                    if (frameTime < perFrameTime) {
                        try {
                            Thread.sleep(perFrameTime - frameTime);
                        } catch (InterruptedException e) {
                        }
                    }
                }
            }
        }
    }

    public void drawLetterbox(Canvas canvas) {
        if (this.orientIsPortrait) {
            canvas.drawRect(0.0f, 0.0f, this.screenInfo.widthPixels, this.letterboxSize, this.letterboxPaint);
            canvas.drawRect(0.0f, this.screenInfo.heightPixels - this.letterboxSize, this.screenInfo.widthPixels, this.screenInfo.heightPixels, this.letterboxPaint);
            return;
        }
        canvas.drawRect(0.0f, 0.0f, this.letterboxSize, this.relScreenHeight, this.letterboxPaint);
        canvas.drawRect(this.screenInfo.widthPixels - this.letterboxSize, 0.0f, this.screenInfo.widthPixels, this.relScreenHeight, this.letterboxPaint);
    }

    public void drawTestText(Canvas canvas) {
        canvas.drawText("RW: " + Screen.relScreenWidth, 200.0f, 10.0f, this.testTextPaint);
        canvas.drawText("RH: " + Screen.relScreenHeight, 200.0f, 20.0f, this.testTextPaint);
        canvas.drawText("RX: " + Screen.relXZero, 200.0f, 30.0f, this.testTextPaint);
        canvas.drawText("RY: " + Screen.relYZero, 200.0f, 40.0f, this.testTextPaint);
        canvas.drawText("DPIX: " + this.screenInfo.xdpi, 200.0f, 50.0f, this.testTextPaint);
        canvas.drawText("DPIY: " + this.screenInfo.ydpi, 200.0f, 60.0f, this.testTextPaint);
        canvas.drawText("agentsLeft: " + SaveFile.agentsLeft, 200.0f, 70.0f, this.testTextPaint);
        canvas.drawText("beatHuntingGame: " + SaveFile.beatHuntingGame, 200.0f, 80.0f, this.testTextPaint);
        canvas.drawText("bribedGuard: " + SaveFile.bribedGuard, 200.0f, 90.0f, this.testTextPaint);
        canvas.drawText("cash: " + SaveFile.cash, 200.0f, 100.0f, this.testTextPaint);
        canvas.drawText("computerPassword: " + SaveFile.computerPassword, 200.0f, 110.0f, this.testTextPaint);
        canvas.drawText("hasBat: " + SaveFile.hasBat, 200.0f, 120.0f, this.testTextPaint);
        canvas.drawText("hasBrassKnuckles: " + SaveFile.hasBrassKnuckles, 200.0f, 130.0f, this.testTextPaint);
        canvas.drawText("hasDrugs: " + SaveFile.hasDrugs, 200.0f, 140.0f, this.testTextPaint);
        canvas.drawText("hasElevatorKey: " + SaveFile.hasElevatorKey, 200.0f, 150.0f, this.testTextPaint);
        canvas.drawText("hasEmail: " + SaveFile.hasEmail, 200.0f, 160.0f, this.testTextPaint);
        canvas.drawText("hasFancyClothes: " + SaveFile.hasFancyClothes, 200.0f, 170.0f, this.testTextPaint);
        canvas.drawText("hasLetter: " + SaveFile.hasLetter, 200.0f, 180.0f, this.testTextPaint);
        canvas.drawText("hasLight: " + SaveFile.hasLight, 200.0f, 190.0f, this.testTextPaint);
        canvas.drawText("hasLighthouseKey: " + SaveFile.hasLighthouseKey, 200.0f, 200.0f, this.testTextPaint);
        canvas.drawText("hasLunchbox: " + SaveFile.hasLunchbox, 200.0f, 210.0f, this.testTextPaint);
        canvas.drawText("hasMoney: " + SaveFile.hasMoney, 200.0f, 220.0f, this.testTextPaint);
        canvas.drawText("hasRacket: " + SaveFile.hasRacket, 200.0f, 230.0f, this.testTextPaint);
        canvas.drawText("hasVIPPass: " + SaveFile.hasVIPPass, 200.0f, 240.0f, this.testTextPaint);
        canvas.drawText("isFirstRun: " + SaveFile.isFirstRun, 200.0f, 250.0f, this.testTextPaint);
        canvas.drawText("lightkeeperLeft: " + SaveFile.lightkeeperLeft, 200.0f, 260.0f, this.testTextPaint);
        canvas.drawText("musicOn: " + SaveFile.musicOn, 200.0f, 270.0f, this.testTextPaint);
        canvas.drawText("soundOn: " + SaveFile.soundOn, 200.0f, 280.0f, this.testTextPaint);
        canvas.drawText("talkedToKid: " + SaveFile.talkedToKid, 200.0f, 290.0f, this.testTextPaint);
        canvas.drawText("wonBarFight: " + SaveFile.wonBarFight, 200.0f, 300.0f, this.testTextPaint);
        canvas.drawText("caveIsLit: " + SaveFile.caveIsLit, 200.0f, 310.0f, this.testTextPaint);
    }
}
