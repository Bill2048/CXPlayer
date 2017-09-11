package com.chaoxing.player;

/**
 * Created by huwei on 2017/9/10.
 */

public interface PlayerEventListener {

    void onPlayWhenReady(boolean playWhenReady);

    void onPositionChanged(int currentPosition, int duration);

}
