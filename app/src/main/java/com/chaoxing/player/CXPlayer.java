package com.chaoxing.player;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;


/**
 * Created by HuWei on 2017/8/31.
 */

public class CXPlayer {

    private static final String TAG = "CXPlayer";

    private Context context;

    private MediaPlayer mediaPlayer;

    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;

    private PlayerCallback playerCallback;
    private boolean prepared;

    private CXPlayer(Context context) {
        this.context = context;
        initPlayer();
    }

    public static CXPlayer newInstance(Context context) {
        Vitamio.isInitialized(context.getApplicationContext());
        CXPlayer player = new CXPlayer(context);
        return player;
    }

    private void initPlayer() {
        mediaPlayer = new MediaPlayer(context);
        mediaPlayer.setOnVideoSizeChangedListener(onVideoSizeChangedListener);
        mediaPlayer.setOnPreparedListener(onPreparedListener);
        mediaPlayer.setOnBufferingUpdateListener(bufferingUpdateListener);
        mediaPlayer.setOnInfoListener(onInfoListener);
        mediaPlayer.setOnErrorListener(onErrorListener);
        mediaPlayer.setOnSeekCompleteListener(onSeekCompleteListener);
        mediaPlayer.setOnCompletionListener(onCompletionListener);
        mediaPlayer.setScreenOnWhilePlaying(true);
    }

    public void prepare(Video video) {
        if (video.getDataSource() != null && !video.getDataSource().trim().isEmpty()) {
            try {
                prepared = false;
                mediaPlayer.reset();
                mediaPlayer.setDataSource(video.getDataSource());
                if (surfaceHolder.isCreating()) {
                    mediaPlayer.setDisplay(surfaceHolder);
                }
                mediaPlayer.prepareAsync();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setVideoSurfaceView(SurfaceView surfaceView) {
        if (surfaceHolder != null) {
            surfaceHolder.removeCallback(surfaceCallback);
        }
        this.surfaceView = surfaceView;
        this.surfaceView.setZOrderMediaOverlay(true);
        surfaceHolder = this.surfaceView.getHolder();
        surfaceHolder.setFormat(PixelFormat.TRANSPARENT);
        surfaceHolder.addCallback(surfaceCallback);
    }

    public void clearVideoTextureView(SurfaceView surfaceView) {

    }

    public void setPlayerCallback(PlayerCallback playerCallback) {
        this.playerCallback = playerCallback;
    }

    private final SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            surfaceHolder = holder;
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            surfaceHolder = holder;
            if (mediaPlayer != null) {
                holder.setFormat(PixelFormat.TRANSPARENT);
                mediaPlayer.setDisplay(holder);
                mediaPlayer.setScreenOnWhilePlaying(true);
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            surfaceHolder = null;
            if (mediaPlayer != null) {
                mediaPlayer.setDisplay(null);
            }
        }
    };

    private MediaPlayer.OnVideoSizeChangedListener onVideoSizeChangedListener = new MediaPlayer.OnVideoSizeChangedListener() {
        @Override
        public void onVideoSizeChanged(MediaPlayer mediaPlayer, int width, int height) {
            Log.d(TAG, "onVideoSizeChanged() width : " + width + " height : " + height);
            if (playerCallback != null) {
                playerCallback.onVideoSizeChanged(width, height);
            }
        }
    };

    private MediaPlayer.OnPreparedListener onPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mediaPlayer) {
            prepared = true;
            mediaPlayer.start();
        }
    };

    private MediaPlayer.OnBufferingUpdateListener bufferingUpdateListener = new MediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(MediaPlayer mediaPlayer, int percent) {

        }
    };

    private MediaPlayer.OnInfoListener onInfoListener = new MediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(MediaPlayer mediaPlayer, int what, int extra) {
            switch (what) {
                case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                    Log.d(TAG, "onInfo() MEDIA_INFO_BUFFERING_START");
                    break;
                case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                    Log.d(TAG, "onInfo() MEDIA_INFO_BUFFERING_END");
                    break;
                default:
                    break;
            }
            return false;
        }
    };

    private MediaPlayer.OnErrorListener onErrorListener = new MediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {
            switch (what) {
                case MediaPlayer.MEDIA_ERROR_IO:
                    Log.e(TAG, "onError() MEDIA_ERROR_IO");
                    break;
                case MediaPlayer.MEDIA_ERROR_MALFORMED:
                    Log.e(TAG, "onError() MEDIA_ERROR_MALFORMED");
                    break;
                case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
                    Log.e(TAG, "onError() MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK");
                    break;
                case MediaPlayer.MEDIA_ERROR_TIMED_OUT:
                    Log.e(TAG, "onError() MEDIA_ERROR_TIMED_OUT");
                    break;
                case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                    Log.e(TAG, "onError() MEDIA_ERROR_UNKNOWN");
                    break;
                case MediaPlayer.MEDIA_ERROR_UNSUPPORTED:
                    Log.e(TAG, "onError() MEDIA_ERROR_UNSUPPORTED");
                    break;
                default:
                    Log.e(TAG, "onError() ~");
                    break;
            }
            return false;
        }
    };

    private MediaPlayer.OnSeekCompleteListener onSeekCompleteListener = new MediaPlayer.OnSeekCompleteListener() {
        @Override
        public void onSeekComplete(MediaPlayer mediaPlayer) {

        }
    };

    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {

        }
    };

    public void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void release() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        if (surfaceHolder != null) {
            surfaceHolder.removeCallback(surfaceCallback);
            surfaceHolder = null;
        }
    }
}
