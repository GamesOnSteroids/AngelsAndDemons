package com.gamesonsteroids.angelsanddemons.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gamesonsteroids.angelsanddemons.game.GameSession;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onHelpClick(View view) {
        Intent intent = new Intent(MainActivity.this, HelpActivity.class);
        this.startActivity(intent);
    }

    public void onPlayClick(View view) {

        GameSession.getCurrent().createGame();

        Intent intent = new Intent(MainActivity.this, GameConfigurationActivity.class);
        this.startActivity(intent);
    }
}
