package com.chaoxing.player;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import io.vov.vitamio.MediaPlayer;

/**
 * Created by HuWei on 2017/8/31.
 */

public class PlaybackControlView extends FrameLayout {

    // control view 默认显示时常
    public static final int DEFAULT_SHOW_TIMEOUT_MS = 5000;

    private ImageButton ibPrevious;
    private ImageButton ibPlay;
    private ImageButton ibPause;
    private ImageButton ibNext;
    private TextView tvPosition;
    private SeekBar sbProgress;
    private TextView tvDuration;

    private boolean isAttachedToWindow;

    private int showTimeoutMs = DEFAULT_SHOW_TIMEOUT_MS;

    private CXPlayer player;

    private final Runnable hideAction = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    public PlaybackControlView(@NonNull Context context) {
        this(context, null);
    }

    public PlaybackControlView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlaybackControlView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.playback_control_view, this);
        ibPrevious = findViewById(R.id.ib_previous);
        ibPrevious.setOnClickListener(onClickListener);
        ibPlay = findViewById(R.id.ib_play);
        ibPlay.setOnClickListener(onClickListener);
        ibPause = findViewById(R.id.ib_pause);
        ibPause.setOnClickListener(onClickListener);
        ibNext = findViewById(R.id.ib_next);
        ibNext.setOnClickListener(onClickListener);
        tvPosition = findViewById(R.id.tv_position);
        sbProgress = findViewById(R.id.sb_progress);
        tvDuration = findViewById(R.id.tv_duration);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        isAttachedToWindow = true;
        hideAfterTimeout();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isAttachedToWindow = false;
        removeCallbacks(hideAction);
    }

    public void setPlayer(CXPlayer player) {
        if (this.player == player) {
            return;
        }
        if (this.player != null) {
            this.player.removePlayerCallback(playerCallback);
        }
        this.player = player;
        this.player.addPlayerCallback(playerCallback);
    }

    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            if (id == R.id.ib_previous) {

            } else if (id == R.id.ib_play) {

            } else if (id == R.id.ib_pause) {

            } else if (id == R.id.ib_next) {

            }
        }
    };

    private PlayerCallback playerCallback = new PlayerCallback() {
        @Override
        public void onVideoSizeChanged(int width, int height) {

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

    public void show() {
        if (!isVisible()) {
            setVisibility(VISIBLE);
        }
        hideAfterTimeout();
    }

    public void hide() {
        if (isVisible()) {
            setVisibility(GONE);
            removeCallbacks(hideAction);
        }
    }

    public boolean isVisible() {
        return getVisibility() == VISIBLE;
    }

    private void hideAfterTimeout() {
        removeCallbacks(hideAction);
        if (showTimeoutMs > 0) {
            if (isAttachedToWindow) {
                postDelayed(hideAction, showTimeoutMs);
            }
        }
    }


}
