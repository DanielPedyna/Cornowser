package io.xdevs23.cornowser.browser.browser.modules.ui;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewPropertyAnimator;

import io.xdevs23.cornowser.browser.CornBrowser;

public class OmniboxAnimations {

    public static final int
            DEFAULT_ANIMATION_DURATION = 420
            ;

    public static boolean isBottom() {
        return OmniboxControl.isBottom();
    }

    public static boolean isTop() {
        return !isBottom();
    }

    protected static int getOmniHeight() {
        return OmniboxControl.getOmniboxHeight();
    }

    public static int getOmniboxPositionInt() {
        return OmniboxControl.getOmniboxPositionInt();
    }

    protected static ViewPropertyAnimator omniboxAnimate() {
        return CornBrowser.omnibox.animate().setDuration(DEFAULT_ANIMATION_DURATION);
    }

    protected static ViewPropertyAnimator webAnimate() {
        return CornBrowser.publicWebRenderLayout.animate().setDuration(DEFAULT_ANIMATION_DURATION);
    }

    public static void moveOmni(int posY) {
        if(isBottom()) return;
        float mov = (float) (posY);
        CornBrowser.omnibox.setTranslationY(mov);
        CornBrowser.publicWebRenderLayout
                .setTranslationY(mov + CornBrowser.omnibox.getHeight());
    }

    public static void animateOmni(int posY) {
        float mov = (float) posY;
        CornBrowser.omnibox.animate().translationY(mov + (isBottom() ? CornBrowser.omnibox.getHeight() : 0));
        if(isTop())
            CornBrowser.publicWebRenderLayout.animate()
                .setDuration(DEFAULT_ANIMATION_DURATION)
                .translationY((mov + CornBrowser.omnibox.getHeight()));
    }

    public static void resetOmni() {
        omniboxAnimate().translationY(0);
        if(isTop()) CornBrowser.publicWebRenderLayout.setTranslationY(getOmniHeight());
        else        CornBrowser.publicWebRenderLayout.setTranslationY(0);

    }

    // Main listener for controlling omnibox show/hide animations

    public static final View.OnTouchListener
            mainOnTouchListener = new View.OnTouchListener() {
        int
                oh = 0,
                cy = 0,
                ny = 0,
                opos = 0;

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch(motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    oh = CornBrowser.omnibox.getHeight();
                    if(-opos <= oh / 2) cy = (int)motionEvent.getRawY();
                    else cy = (int)motionEvent.getRawY() - opos;
                    break;
                case MotionEvent.ACTION_UP:
                    if(-opos > oh / 2) {
                        animateOmni( (isBottom() ? 0 : -oh) );
                        opos =       (isBottom() ? 0 : -oh)  ;
                    }
                    else animateOmni( (isBottom() ? -oh : 0) );
                    break;
                case MotionEvent.ACTION_MOVE:
                    ny = ((int)motionEvent.getRawY()) - cy;
                    opos = ny;
                    if(opos > 0  ) opos = 0;
                    if(opos < -oh) opos = -oh;

                    moveOmni(opos);
                    break;
                default: break;
            }
            return false;
        }

    };

}