package com.chaoxing.player;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by HuWei on 2017/8/30.
 */

public class PlayerActivity extends AppCompatActivity {

//        private String dataSource = Environment.getExternalStorageDirectory() + File.separator + "media" + File.separator + "video.mp4";
    private String dataSource = "http://s1.ananas.chaoxing.com/video/32/84/73/c6ad1b62880ed08f99b62dfa044f3d5e/sd.mp4";

    private CXPlayerView playerView;

    private CXPlayer player;

    private boolean playing;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        playerView = findViewById(R.id.player_view);
    }

    protected void play() {
        if (playing) {
            player.resumePlay();
            return;
        }
        playing = true;
        Video mediaSource = new Video(dataSource, null);
        mediaSource.setDataSource(dataSource);
        player.prepare(mediaSource);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player == null) {
            player = CXPlayer.newInstance(this);
            playerView.setPlayer(player);
            play();
        } else {
            if (playing) {
                play();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (playing) {
            player.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
        }
    }
}
