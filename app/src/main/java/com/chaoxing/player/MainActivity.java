package com.chaoxing.player;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.chaoxing.player.vitamio.VitamioListActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_vitamio).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vitamio();
            }
        });
        findViewById(R.id.btn_cx_player).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cxPlayer();
            }
        });
    }

    private void vitamio() {
        Intent intent = new Intent(this, VitamioListActivity.class);
        startActivity(intent);
    }

    private void cxPlayer() {
        Intent intent = new Intent(this, PlayerActivity.class);
        startActivity(intent);
    }

}
