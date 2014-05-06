package com.gamesonsteroids.angelsanddemons.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.gamesonsteroids.angelsanddemons.game.GameRules;
import com.gamesonsteroids.angelsanddemons.game.GameSession;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button helpButton = (Button) findViewById(R.id.action_help);
        helpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, HelpActivity.class);
                MainActivity.this.startActivity(intent);

            }
        });

        final SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);

        final Button playButton = (Button) findViewById(R.id.button);
        playButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                GameSession.getCurrent().createGame(GameRules.MinPlayers + seekBar.getProgress(), GameRules.DefaultNames);

                Intent intent = new Intent(MainActivity.this, GameConfigurationActivity.class);
                MainActivity.this.startActivity(intent);

            }
        });

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


}
