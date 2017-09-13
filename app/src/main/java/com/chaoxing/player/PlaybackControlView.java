package com.chaoxing.player;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSeekBar;
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

    public interface VisibilityListener {

        void onVisibilityChange(int visibility);

    }

    public interface ControlDispatcher {

        boolean dispatchSetPlayWhenReady(CXPlayer player, boolean playWhenReady);


        boolean dispatchSeekTo(CXPlayer player, int positionMs);

    }

    public static final ControlDispatcher DEFAULT_CONTROL_DISPATCHER = new ControlDispatcher() {

        @Override
        public boolean dispatchSetPlayWhenReady(CXPlayer player, boolean playWhenReady) {
            player.setPlayWhenReady(playWhenReady);
            return true;
        }

        @Override
        public boolean dispatchSeekTo(CXPlayer player, int positionMs) {
            player.seekTo(positionMs);
            return true;
        }
    };

    // control view 默认显示时常
    public static final int DEFAULT_SHOW_TIMEOUT_MS = 5000;

    private ImageButton ibPrevious;
    private ImageButton ibPlay;
    private ImageButton ibPause;
    private ImageButton ibNext;
    private TextView tvPosition;
    private AppCompatSeekBar sbProgress;
    private TextView tvDuration;

    private VisibilityListener visibilityListener;
    private ControlDispatcher controlDispatcher = DEFAULT_CONTROL_DISPATCHER;

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
        sbProgress.setOnSeekBarChangeListener(onSeekBarChangeListener);
        tvDuration = findViewById(R.id.tv_duration);
        reset();
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

    public void setControlDispatcher(ControlDispatcher controlDispatcher) {
        this.controlDispatcher = controlDispatcher;
    }

    public void reset() {
        ibPlay.setVisibility(View.VISIBLE);
        ibPause.setVisibility(View.GONE);
        setProgress(0, 0);
    }

    public void setProgress(int currentPosition, int duration) {
        if (draggingSeekBar) {
            return;
        }
        if (sbProgress.getMax() != duration) {
            sbProgress.setMax(duration);
        }
        sbProgress.setProgress(currentPosition);
        if (duration == 0) {
            sbProgress.setEnabled(false);
        } else {
            if (!sbProgress.isEnabled()) {
                sbProgress.setEnabled(true);
            }
        }
    }

    public void setPlayer(CXPlayer player) {
        if (this.player == player) {
            return;
        }
        if (this.player != null) {
            this.player.removePlayerCallback(playerCallback);
        }
        this.player = player;
        player.addPlayerCallback(playerCallback);
        player.addPlayerEventListener(playerEventListener);
    }

    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            show();
            int id = view.getId();
            if (id == R.id.ib_previous) {

            } else if (id == R.id.ib_play) {
                if (controlDispatcher != null) {
                    controlDispatcher.dispatchSetPlayWhenReady(player, true);
                }
            } else if (id == R.id.ib_pause) {
                if (controlDispatcher != null) {
                    controlDispatcher.dispatchSetPlayWhenReady(player, false);
                }
            } else if (id == R.id.ib_next) {

            }
        }
    };

    private boolean draggingSeekBar;
    private SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                draggingSeekBar = true;
                show();
            }
            String positionStr = PlayerUtils.formatTime(progress);
            String durationStr = PlayerUtils.formatTime(seekBar.getMax());
            String[] fmtTime = PlayerUtils.formatTime(positionStr, durationStr);
            tvPosition.setText(fmtTime[0]);
            tvDuration.setText(fmtTime[1]);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            if (controlDispatcher != null) {
                controlDispatcher.dispatchSeekTo(player, seekBar.getProgress());
            }
            draggingSeekBar = false;
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
            reset();
        }
    };

    private PlayerEventListener playerEventListener = new PlayerEventListener() {

        @Override
        public void onPlayWhenReady(boolean playWhenReady) {
            if (playWhenReady) {
                ibPlay.setVisibility(View.GONE);
                ibPause.setVisibility(View.VISIBLE);
            } else {
                ibPause.setVisibility(View.GONE);
                ibPlay.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPositionChanged(int currentPosition, int duration) {
            if (!isAttachedToWindow) {
                return;
            }
            setProgress(currentPosition, duration);
        }

    };

    public void setVisibilityListener(VisibilityListener visibilityListener) {
        this.visibilityListener = visibilityListener;
    }

    public void show() {
        if (!isVisible()) {
            setVisibility(VISIBLE);
            if(visibilityListener != null) {
                visibilityListener.onVisibilityChange(getVisibility());
            }
        }
        hideAfterTimeout();
    }

    public void hide() {
        if (isVisible()) {
            setVisibility(GONE);
            removeCallbacks(hideAction);
            if(visibilityListener != null) {
                visibilityListener.onVisibilityChange(getVisibility());
            }
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
