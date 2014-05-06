package com.gamesonsteroids.angelsanddemons.app;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.gamesonsteroids.angelsanddemons.game.GameRules;
import com.gamesonsteroids.angelsanddemons.game.GameSession;
import com.gamesonsteroids.angelsanddemons.game.Player;
import com.gamesonsteroids.angelsanddemons.game.Role;

import java.util.Map;


public class ActionResultActivity extends GameActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_result);


        Map<Player, Role> votes = GameSession.getCurrent().getVotes();

        int minorityVotes = 0;
        int majorityVotes = 0;
        for (Role vote : votes.values()) {
            if (vote == Role.Minority) {
                minorityVotes++;
            } else {
                majorityVotes++;
            }
        }

        boolean minorityWon = minorityVotes >= GameRules.getNecessaryMinorityVotes(GameSession.getCurrent().getRound(), GameSession.getCurrent().getPlayers().size());


        initHeader();


        TextView result = (TextView)findViewById(R.id.action_result_text);
        TextView votesText = (TextView)findViewById(R.id.action_result_votes);

        votesText.setText(Html.fromHtml(getString(R.string.result_votes, majorityVotes, minorityVotes)));

        if (minorityWon) {
            result.setText(Html.fromHtml(getString(R.string.result_minority_won)));
        } else {
            result.setText(Html.fromHtml(getString(R.string.result_majority_won)));
        }

    }


    public void onNextRoundClick(View view) {

        GameSession.getCurrent().startRound(GameSession.getCurrent().getRound() + 1);

        Intent intent = new Intent(this, CreateTeamActivity.class);
        startActivity(intent);
    }
}
