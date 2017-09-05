package com.chaoxing.player;

import io.vov.vitamio.MediaPlayer;

/**
 * Created by huwei on 2017/9/4.
 */

public interface PlayerCallback {

    void onVideoSizeChanged(int width, int height);

    void onPrepared();

    void onBufferingUpdate(int percent);

    boolean onInfo(int what, int extra);

    boolean onError(int what, int extra);

    void onSeekComplete();

    void onCompletion(MediaPlayer mediaPlayer);

}
