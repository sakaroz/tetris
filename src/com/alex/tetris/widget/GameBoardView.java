package com.alex.tetris.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.alex.tetris.model.Piece;
import com.alex.tetris.util.Keys;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: alexandreduhem
 * Date: 19/10/13
 * Time: 16:40
 * To change this template use File | Settings | File Templates.
 */
public class GameBoardView extends View implements View.OnTouchListener {

    private static final int DEFAULT_BOARD_HEIGHT = 18;
    private static final int DEFAULT_BOARD_LENGTH = 10;

    //abcsisse maximale
    private int gameBoardLength;
    //ordonnée maximale
    private int gameBoardHeight;
    //taille en pixel d'une case
    private int boxSize;

    private Context context;

    private Piece currentPiece;

    ArrayList<Piece> pieces;

    Paint paint = new Paint();

    int sideMargin;

    public GameBoardView(Context context) {
        this(context, null);
    }

    public GameBoardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GameBoardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        currentPiece = new Piece(Piece.SHAPE_L);
        setOnTouchListener(this);
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldw, int oldh) {
        super.onSizeChanged(width, height, oldw, oldh);
        SharedPreferences pref = context.getSharedPreferences(Keys.PREF_KEY_GENERAL, Context.MODE_PRIVATE);
        gameBoardLength = pref.getInt(Keys.PREF_KEY_GAMEBOARD_LENGTH, DEFAULT_BOARD_LENGTH);
        gameBoardHeight = pref.getInt(Keys.PREF_KEY_GAMEBOARD_HEIGHT, DEFAULT_BOARD_HEIGHT);
        boxSize = height / gameBoardHeight;
        sideMargin = width - gameBoardLength* boxSize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(currentPiece.getColor());
        for (int i = 0; i < currentPiece.getCoordinates().length; i++) {
            canvas.drawRect(getRectForCoordinate(currentPiece.getCoordinates()[i]), paint);
        }

    }

    private Rect getRectForCoordinate(int[] coordinatesInGameBoard) {
        int x = coordinatesInGameBoard[0];
        //the origin of the plan is at the top left, to simulate a plan
        //with a bottom left origin (easier to imagine), with do the following operation to y
        int y = -coordinatesInGameBoard[1] +gameBoardHeight;
        int left = x * boxSize + (sideMargin/2);
        int bottom = y * boxSize;
        return new Rect(left, bottom - boxSize, left + boxSize, bottom);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
