package com.gamesonsteroids.angelsanddemons.app;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gamesonsteroids.angelsanddemons.game.GameRules;
import com.gamesonsteroids.angelsanddemons.game.GameSession;
import com.gamesonsteroids.angelsanddemons.game.Player;
import com.gamesonsteroids.angelsanddemons.game.Role;
import com.gamesonsteroids.angelsanddemons.game.Round;

import java.util.ArrayList;
import java.util.List;


public class ActionResultActivity extends AbstractGameActivity {

    private TextView actionResultText;
    private TextView actionVotesText;
    private TextView minorityPlayersText;
    private Button nextButton;
    private TextView gameOverText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_result);

        initHeader();

        gameOverText = (TextView)findViewById(R.id.action_result_game_over);
        actionResultText = (TextView)findViewById(R.id.action_result_text);
        actionVotesText = (TextView)findViewById(R.id.action_result_votes);
        minorityPlayersText = (TextView)findViewById(R.id.action_result_minority_names);
        nextButton = (Button)findViewById(R.id.action_result_next);

        Round round = GameSession.getCurrent().getCurrentRound();

        if (GameSession.getCurrent().isGameOver()) {

            gameOverText.setVisibility(View.VISIBLE);

            if (GameSession.getCurrent().getMinorityScore() == GameRules.PointsToWin) {
                actionResultText.setText(Html.fromHtml(getString(R.string.minority_won_game)));
                actionVotesText.setText(Html.fromHtml(getString(R.string.result_votes, round.getMajorityVotes(), round.getMinorityVotes())));
            } else if (GameSession.getCurrent().getMajorityScore() == GameRules.PointsToWin) {
                actionResultText.setText(Html.fromHtml(getString(R.string.majority_won_game)));
                actionVotesText.setText(Html.fromHtml(getString(R.string.result_votes, round.getMajorityVotes(), round.getMinorityVotes())));
            } else if (GameSession.getCurrent().getConsecutiveDraws() == GameRules.DrawsToWin) {
                actionResultText.setText(Html.fromHtml(getString(R.string.minority_won_game_draw)));
            }

            List<CharSequence> otherMinorityMember = new ArrayList<CharSequence>();
            for (Player otherPlayer : GameSession.getCurrent().getPlayers()) {
                if (otherPlayer.getRole().equals(Role.Minority)) {
                    otherMinorityMember.add(otherPlayer.getName());
                }
            }
            minorityPlayersText.setText(Html.fromHtml(getString(R.string.minority_players, TextUtils.join(", ", otherMinorityMember))));

            nextButton.setText(R.string.restart);

        } else {

            gameOverText.setVisibility(View.INVISIBLE);

            if (round.getWinner() == Role.Minority) {
                actionResultText.setText(Html.fromHtml(getString(R.string.result_minority_won)));
            } else {
                actionResultText.setText(Html.fromHtml(getString(R.string.result_majority_won)));
            }


            actionVotesText.setText(Html.fromHtml(getString(R.string.result_votes, round.getMajorityVotes(), round.getMinorityVotes())));

            nextButton.setText(R.string.next);
        }
    }


    public void onNextRoundClick(View view) {

        if (GameSession.getCurrent().isGameOver()) {

            GameSession.getCurrent().createGame();
            GameSession.getCurrent().startGame();

            Intent intent = new Intent(this, AssignRolesActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            this.startActivity(intent);

        } else {

            GameSession.getCurrent().nextRound();

            Intent intent = new Intent(this, CreateTeamActivity.class);
            startActivity(intent);

        }
    }
}
