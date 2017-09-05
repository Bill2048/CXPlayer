package com.chaoxing.player;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by HuWei on 2017/8/31.
 */

public class PlaybackControlView extends FrameLayout {

    private ImageButton ibPrevious;
    private ImageButton ibPlay;
    private ImageButton ibPause;
    private ImageButton ibNext;
    private TextView tvPosition;
    private SeekBar sbProgress;
    private TextView tvDuration;

    public PlaybackControlView(@NonNull Context context) {
        this(context, null);
    }

    public PlaybackControlView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlaybackControlView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.playback_control_view, this);
        ibPrevious = (ImageButton) findViewById(R.id.ib_previous);
        ibPlay = (ImageButton) findViewById(R.id.ib_play);
        ibPause = (ImageButton) findViewById(R.id.ib_pause);
        ibNext = (ImageButton) findViewById(R.id.ib_next);
        tvPosition = (TextView) findViewById(R.id.tv_position);
        sbProgress = (SeekBar) findViewById(R.id.sb_progress);
        tvDuration = (TextView) findViewById(R.id.tv_duration);
    }

}
