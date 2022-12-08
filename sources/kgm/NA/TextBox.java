package kgm.NA;

import android.graphics.Canvas;
import android.graphics.Paint;

/* loaded from: classes.dex */
public class TextBox {
    String id;
    Paint linePaint;
    String[] lines;
    int numLines;
    Paint textPaint;
    int currentLine = 0;
    int currentPage = 0;
    int currentChar = 0;
    long updateTime = System.currentTimeMillis();
    boolean textFinished = false;
    boolean isFinished = false;
    boolean isPaused = true;
    float posX = Screen.relXZero + (Screen.relScreenWidth * 0.04f);
    float posY1 = Screen.relScreenHeight * 0.75f;
    float posY2 = Screen.relScreenHeight * 0.82f;
    float posY3 = Screen.relScreenHeight * 0.89f;
    Paint rectPaint = new Paint();

    public TextBox(String id, String[] lines, int numLines) {
        this.lines = lines;
        this.numLines = numLines - 1;
        this.id = id;
        this.rectPaint.setColor(-1);
        this.rectPaint.setStyle(Paint.Style.FILL);
        this.linePaint = new Paint();
        this.linePaint.setStyle(Paint.Style.STROKE);
        this.linePaint.setColor(-16777216);
        this.linePaint.setStrokeWidth(Screen.relScreenWidth * 0.02f);
        this.textPaint = new Paint();
        this.textPaint.setColor(-16777216);
        this.textPaint.setTextSize(Screen.relScreenWidth * 0.045f);
    }

    public void draw(Canvas canvas) {
        if (!this.isFinished) {
            canvas.drawRect(Screen.relXZero, Screen.relScreenHeight * 0.66f, Screen.relScreenWidth + Screen.relXZero, Screen.relScreenHeight, this.rectPaint);
            canvas.drawRect((this.linePaint.getStrokeWidth() / 2.0f) + Screen.relXZero, Screen.relScreenHeight * 0.66f, (Screen.relScreenWidth + Screen.relXZero) - (this.linePaint.getStrokeWidth() / 2.0f), Screen.relScreenHeight - (this.linePaint.getStrokeWidth() / 2.0f), this.linePaint);
            drawLines(canvas);
        }
        update();
    }

    public void update() {
        if (!this.isPaused) {
            this.currentChar++;
            if (this.lines.length > 2 && this.currentLine == 2 && this.currentChar == this.lines[2].length()) {
                this.isPaused = true;
            }
            if (this.lines.length > 5 && this.currentLine == 5 && this.currentChar == this.lines[5].length()) {
                this.isPaused = true;
            }
            if (this.lines.length > 8 && this.currentLine == 8 && this.currentChar == this.lines[8].length()) {
                this.isPaused = true;
            }
            if (!this.textFinished && this.currentChar > this.lines[this.currentLine].length()) {
                this.currentLine++;
                this.currentChar = 0;
                if (this.currentLine > this.numLines) {
                    this.currentLine = this.lines.length - 1;
                    this.currentChar = this.lines[this.lines.length - 1].length();
                    this.textFinished = true;
                    this.isPaused = true;
                }
            }
        }
    }

    public void start() {
        this.isFinished = false;
        this.textFinished = false;
        this.isPaused = false;
        this.currentLine = 0;
        this.currentChar = 0;
    }

    public void resume() {
        this.isPaused = false;
        if (this.textFinished) {
            this.isFinished = true;
        }
    }

    public void drawLines(Canvas canvas) {
        if (this.currentLine < 3) {
            if (this.currentLine == 0) {
                drawLine(canvas, 0, this.currentChar, this.posX, this.posY1);
            }
            if (this.currentLine == 1) {
                drawLine(canvas, 0, this.lines[0].length(), this.posX, this.posY1);
                drawLine(canvas, 1, this.currentChar, this.posX, this.posY2);
            }
            if (this.currentLine == 2) {
                drawLine(canvas, 0, this.lines[0].length(), this.posX, this.posY1);
                drawLine(canvas, 1, this.lines[1].length(), this.posX, this.posY2);
                drawLine(canvas, 2, this.currentChar, this.posX, this.posY3);
            }
        }
        if (this.currentLine >= 3 && this.currentLine <= 5) {
            if (this.currentLine == 3) {
                drawLine(canvas, 3, this.currentChar, this.posX, this.posY1);
            }
            if (this.currentLine == 4) {
                drawLine(canvas, 3, this.lines[3].length(), this.posX, this.posY1);
                drawLine(canvas, 4, this.currentChar, this.posX, this.posY2);
            }
            if (this.currentLine == 5) {
                drawLine(canvas, 3, this.lines[3].length(), this.posX, this.posY1);
                drawLine(canvas, 4, this.lines[4].length(), this.posX, this.posY2);
                drawLine(canvas, 5, this.currentChar, this.posX, this.posY3);
            }
        }
        if (this.currentLine > 5 && this.currentLine < 9) {
            if (this.currentLine == 6) {
                drawLine(canvas, 6, this.currentChar, this.posX, this.posY1);
            }
            if (this.currentLine == 7) {
                drawLine(canvas, 6, this.lines[6].length(), this.posX, this.posY1);
                drawLine(canvas, 7, this.currentChar, this.posX, this.posY2);
            }
            if (this.currentLine == 8) {
                drawLine(canvas, 6, this.lines[6].length(), this.posX, this.posY1);
                drawLine(canvas, 7, this.lines[7].length(), this.posX, this.posY2);
                drawLine(canvas, 8, this.currentChar, this.posX, this.posY3);
            }
        }
    }

    public void drawLine(Canvas canvas, int lineNum, int lastChar, float posX, float posY) {
        canvas.drawText(this.lines[lineNum], 0, this.lines[lineNum].length() - (this.lines[lineNum].length() - lastChar), posX, posY, this.textPaint);
    }
}
