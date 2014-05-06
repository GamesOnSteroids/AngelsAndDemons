package com.gamesonsteroids.angelsanddemons.app;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.gamesonsteroids.angelsanddemons.game.GameSession;
import com.gamesonsteroids.angelsanddemons.game.Role;
import com.gamesonsteroids.angelsanddemons.game.Round;


public class ActionResultActivity extends GameActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_result);



        initHeader();

        Round round = GameSession.getCurrent().getCurrentRound();


        TextView result = (TextView)findViewById(R.id.action_result_text);
        TextView votesText = (TextView)findViewById(R.id.action_result_votes);

        votesText.setText(Html.fromHtml(getString(R.string.result_votes, round.getMajorityVotes(), round.getMinorityVotes())));

        if (round.getWinner() == Role.Minority) {
            result.setText(Html.fromHtml(getString(R.string.result_minority_won)));
        } else {
            result.setText(Html.fromHtml(getString(R.string.result_majority_won)));
        }

    }


    public void onNextRoundClick(View view) {

        GameSession.getCurrent().nextRound();

        Intent intent = new Intent(this, CreateTeamActivity.class);
        startActivity(intent);
    }
}
