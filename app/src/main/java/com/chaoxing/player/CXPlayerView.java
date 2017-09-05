package com.chaoxing.player;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by HuWei on 2017/8/31.
 */

public class CXPlayerView extends FrameLayout {

    private RatioFrameLayout contentFrame;
    private SurfaceView surfaceView;
    private TextView tvSubtitle;
    private PlaybackControlView controlView;

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
        controlView = findViewById(R.id.playback_control);
    }

    public void setPlayer(CXPlayer cxPlayer) {
        if (this.player == cxPlayer) {
            return;
        }
        if (this.player != null) {
            player.clearVideoTextureView(surfaceView);
        }
        this.player = cxPlayer;
        player.setVideoSurfaceView(surfaceView);
    }


}
