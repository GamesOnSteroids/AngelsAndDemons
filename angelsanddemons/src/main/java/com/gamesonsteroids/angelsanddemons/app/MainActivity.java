package com.gamesonsteroids.angelsanddemons.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.gamesonsteroids.angelsanddemons.game.GameRules;
import com.gamesonsteroids.angelsanddemons.game.GameSession;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        final SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);

        final TextView textView = (TextView) findViewById(R.id.textView);

        textView.setText(MainActivity.this.getApplicationContext().getString(R.string.players, GameRules.MinPlayers + seekBar.getProgress()));

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                textView.setText(MainActivity.this.getApplicationContext().getString(R.string.players, GameRules.MinPlayers +seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    public void onHelpClick(View view) {
        Intent intent = new Intent(MainActivity.this, HelpActivity.class);
        this.startActivity(intent);
    }

    public void onPlayClick(View view) {
        final SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);

        int playerCount = GameRules.MinPlayers + seekBar.getProgress();

        GameSession.getCurrent().createGame(playerCount, GameRules.DefaultNames);

        Intent intent = new Intent(MainActivity.this, GameConfigurationActivity.class);
        this.startActivity(intent);
    }
}
