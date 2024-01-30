package com.example.timemap.utils;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * A utility class for detecting swipe gestures on a View and providing callbacks for left and right swipes.
 */
public class OnSwipeTouchListener implements View.OnTouchListener {

    private final GestureDetector gestureDetector;

    /**
     * Constructor:
     * Initializes the OnSwipeTouchListener with a GestureDetector.
     *
     * @param context The context in which the touch listener is used.
     */
    public OnSwipeTouchListener(Context context) {
        gestureDetector = new GestureDetector(context, new GestureListener());
    }

    /**
     * Invoked when a left swipe gesture is detected. Override this method to provide specific functionality.
     */
    public void onSwipeLeft() {
    }

    /**
     * Invoked when a right swipe gesture is detected. Override this method to provide specific functionality.
     */
    public void onSwipeRight() {
    }

    /**
     * Handles touch events by passing them to the GestureDetector.
     *
     * @param v     The View that received the touch event.
     * @param event The MotionEvent object containing touch event data.
     * @return True if the touch event is consumed, false otherwise.
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    /**
     * Inner class implementing GestureDetector.SimpleOnGestureListener to handle swipe gestures.
     */
    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        /**
         * Invoked when a touch down event is detected. Always returns true to indicate that the event is handled.
         *
         * @param e The MotionEvent object representing the touch down event.
         * @return Always returns true.
         */
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        /**
         * Invoked when a fling gesture is detected. Determines the direction of the swipe and invokes the corresponding callback.
         *
         * @param e1        The MotionEvent object for the initial down event.
         * @param e2        The MotionEvent object for the up event.
         * @param velocityX The horizontal velocity of the fling.
         * @param velocityY The vertical velocity of the fling.
         * @return True if the event is consumed, false otherwise.
         */
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float diffX = e2.getX() - e1.getX();
            float diffY = e2.getY() - e1.getY();

            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        onSwipeRight();
                    } else {
                        onSwipeLeft();
                    }
                    return true;
                }
            }

            return false;
        }
    }
}