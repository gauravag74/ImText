package com.imtext.imtext.OCR;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import java.util.HashSet;
import java.util.Set;

public class BoundingBoxes<T extends BoundingBoxes.Graphic> extends View {
    private final Object mLock = new Object();
    private int width;
    private float widthScaleFactor = 1.0f;
    private int height;
    private float heightScaleFactor = 1.0f;
    private Set<T> mGraphics = new HashSet<>();

    public static abstract class Graphic {
        private BoundingBoxes mOverlay;

        public Graphic(BoundingBoxes overlay) {
            mOverlay = overlay;
        }

        public abstract void draw(Canvas canvas);

        public abstract boolean contains(float x, float y);

        public float scaleX(float horizontal) {
            return horizontal * mOverlay.widthScaleFactor;
        }

        public float scaleY(float vertical) {
            return vertical * mOverlay.heightScaleFactor;
        }

        public float translateX(float x) {
            return scaleX(x);
        }

        public float translateY(float y) {
            return scaleY(y);
        }

        public void postInvalidate() {
            mOverlay.postInvalidate();
        }
    }

    public BoundingBoxes(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void clear() {
        synchronized (mLock) {
            mGraphics.clear();
        }
        postInvalidate();
    }

    public void add(T graphic) {
        synchronized (mLock) {
            mGraphics.add(graphic);
        }
        postInvalidate();
    }

    public T getGraphicAtLocation(float rawX, float rawY) {
        synchronized (mLock) {
            int[] location = new int[2];
            this.getLocationOnScreen(location);
            for (T graphic : mGraphics) {
                if (graphic.contains(rawX - location[0], rawY - location[1])) {
                    return graphic;
                }
            }
            return null;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        synchronized (mLock) {
            if ((width != 0) && (height != 0)) {
                widthScaleFactor = (float) canvas.getWidth() / (float) width;
                heightScaleFactor = (float) canvas.getHeight() / (float) height;
            }
            for (Graphic graphic : mGraphics) {
                graphic.draw(canvas);
            }
        }
    }
}