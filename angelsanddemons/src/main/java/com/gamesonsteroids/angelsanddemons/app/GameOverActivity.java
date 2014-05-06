package com.gamesonsteroids.angelsanddemons.app;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.gamesonsteroids.angelsanddemons.game.GameRules;
import com.gamesonsteroids.angelsanddemons.game.GameSession;


public class GameOverActivity extends GameActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        initHeader();


        TextView gameResult = (TextView)findViewById(R.id.game_over_result);

        if (GameSession.getCurrent().getMinorityScore() == GameRules.PointsToWin) {
            gameResult.setText(Html.fromHtml(getString(R.string.minority_won_game)));
        } else if (GameSession.getCurrent().getMajorityScore() == GameRules.PointsToWin) {
            gameResult.setText(Html.fromHtml(getString(R.string.majority_won_game)));
        } else if (GameSession.getCurrent().getConsecutiveDraws() == GameRules.DrawsToWin) {
            gameResult.setText(Html.fromHtml(getString(R.string.minority_won_game_draw)));
        }

    }


    public void onRestartClick(View view) {

        CharSequence[] playerNames = new String[GameSession.getCurrent().getPlayers().size()];

        for (int i = 0; i < playerNames.length; i++) {
            playerNames[i] = GameSession.getCurrent().getPlayers().get(i).getName();
        }

        GameSession.getCurrent().createGame(playerNames.length, playerNames);

        Intent intent = new Intent(this, AssignRolesActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);
    }


    public void onExitClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);
    }
}
