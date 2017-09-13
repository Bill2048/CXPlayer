package com.chaoxing.player;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import android.widget.TextView;

import io.vov.vitamio.MediaPlayer;

/**
 * Created by HuWei on 2017/8/31.
 */

public class CXPlayerView extends FrameLayout {

    private RatioFrameLayout contentFrame;
    private SurfaceView surfaceView;
    private TextView tvSubtitle;
    private PlaybackControlView controller;

    private PlayerViewCallbacks playerViewCallbacks;
    private CXPlayer player;

    public CXPlayerView(@NonNull Context context) {
        this(context, null);
    }

    public CXPlayerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CXPlayerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.cx_player_view, this);
        contentFrame = findViewById(R.id.content_frame);
        surfaceView = findViewById(R.id.surface_view);
        tvSubtitle = findViewById(R.id.tv_subtitle);
        controller = findViewById(R.id.playback_control);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            if (controller != null && player != null) {
                if (controller.isVisible()) {
                    controller.hide();
                } else {
                    controller.show();
                }
                return true;
            }
        }
        return false;
    }

    public void setPlayer(CXPlayer cxPlayer) {
        if (player == cxPlayer) {
            return;
        }
        if (player != null) {
            player.removePlayerCallback(playerCallback);
        }
        player = cxPlayer;
        player.setVideoSurfaceView(surfaceView);
        player.addPlayerCallback(playerCallback);
        controller.setPlayer(player);
    }

    private PlayerCallback playerCallback = new PlayerCallback() {

        @Override
        public void onVideoSizeChanged(int width, int height) {
            contentFrame.setResizeMode(RatioFrameLayout.RESIZE_MODE_FIT);
            contentFrame.setAspectRatio((float) width / (float) height);
        }

        @Override
        public void onPrepared() {

        }

        @Override
        public void onBufferingUpdate(int percent) {

        }

        @Override
        public boolean onInfo(int what, int extra) {
            return false;
        }

        @Override
        public boolean onError(int what, int extra) {
            return false;
        }

        @Override
        public void onSeekComplete() {

        }

        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {

        }
    };

    public interface PlayerViewCallbacks {
        void onControlViewVisible();
        void onControlViewInvisible();
    }

    public void setPlayerCallback(PlayerCallback playerCallback) {
        this.playerCallback = playerCallback;
    }

    public void setControllerVisibilityListener(PlaybackControlView.VisibilityListener listener) {
        controller.setVisibilityListener(listener);
    }
}
